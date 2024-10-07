#!/usr/bin/env python
# coding: utf-8

# Use the following command to update boilerplates:
#
# for i in {1..4}; do python scripts/jnigen-lua.py 5.$i party.iroiro.luajava.lua5${i} lua5${i}/src/main/java/; done
#
# Do not forget to update LuaJit (by copying that of Lua5.1) and possibly LuaJ.
# Use some diff tools to help determine what changes to keep.

# In[1]:
import dataclasses
import os
import re
import requests
import sys
import typing

from lxml import html


# In[1]:
class ParseError(Exception):
    def __init__(self, name: str, message: str, culprit: str):
        super().__init__(message)
        self.name = name
        self.culprit = culprit

@dataclasses.dataclass
class FunctionSignature:
    returns: str

    params: list[tuple[str, str]]

    def __str__(self):
        return f"({self.params}) -> {self.returns}"

    def check(self, name: str):
        if self.returns is None or self.params is None or None in self.params:
            raise ParseError(name, "Invalid signature", f"{self}")

@dataclasses.dataclass
class LuaAPI:
    name: str

    signature: FunctionSignature

    description: str
    """HTML description."""

    declaration: str | None = None

    indicator: str | None = None
    """Each function has an indicator like this: [-o, +p, x]"""

    original: str | None = None

    def __str__(self):
        return f"{self.name}({self.signature})({self.indicator})"

    def check(self):
        if (
            self.name is None
            or self.signature is None
            or self.description is None
            or self.indicator is None
        ):
            raise ParseError(self.name, "Invalid function", f"{self}")
        self.signature.check(self.name)


# In[1]:
argSplit = re.compile('\\s*,\\s*')
paramSplit = re.compile('^(.+?)(\\w+)$')

dupNewline = re.compile('(\\s+\\n\\s+)+')
javaComment = re.compile(re.escape('*/'))
trailingNewline = re.compile('\\s+</p>')

class LuaDocumentation:
    luaVersion: str

    errors: list[str]

    def __init__(self, luaVersion):
        self.luaVersion = luaVersion
        self.errors = []

    @property
    def manualUrl(self):
        return f"https://www.lua.org/manual/{self.luaVersion}/manual.html"

    @property
    def rootUrl(self):
        return f"https://www.lua.org/manual/{self.luaVersion}/"

    def _fetch(self) -> html.HtmlElement:
        r = requests.get(self.manualUrl)
        if r.status_code == requests.codes.ok:
            return html.fromstring(r.text)
        raise Exception(f"Could not fetch {self.manualUrl}")

    def parseFunctions(self) -> list[LuaAPI]:
        dom = self._fetch()
        functions: list[LuaAPI] = []
        for indicator in dom.xpath('//span[@class="apii"]')[1:]:
            try:
                info = self.getMethodInfo(indicator)
                info.check()
                functions.append(info)
            except ParseError as e:
                assert any(expr in e.culprit for expr in [
                    '...',
                    'lst[]',
                    'l[]',
                    'L',
                ]), f"{e}: {e.culprit} @ {e.name}"
                self.errors.append(e.name)
        return functions

    def getMethodInfo(self, indicator: html.HtmlElement):
        parent: html.HtmlElement = indicator.getparent()
        heading: html.HtmlElement = parent.getprevious()
        funcDeclaration: html.HtmlElement = parent.getnext()
        if parent.tag == 'p' and heading.tag == 'h3' and funcDeclaration.tag == 'pre':
            name = heading.text_content()
            func = funcDeclaration.text_content()
            return LuaAPI(
                name=name,
                signature=self.parseSignature(name, func),
                declaration=func,
                description=self.getDescription(name, funcDeclaration),
                indicator=indicator.text_content(),
            )
        else:
            raise ParseError(f"{heading}", "Could not parse heading", f"{heading}")

    @classmethod
    def getParam(cls, name: str, paramString: str) -> tuple[str, str]:
        param = paramSplit.search(paramString)
        if param is not None:
            return (param.group(1).strip(), param.group(2))
        raise ParseError(name, "Could not parse param", paramString)

    def parseSignature(self, name: str, func: str):
        returnType, remaining = func.strip().split(name)
        remaining = remaining.strip()
        if remaining.startswith('(') and remaining.endswith(');'):
            remaining = remaining[1:-2]
            if remaining == 'void':
                params = []
            else:
                params = [self.getParam(name, param) for param in argSplit.split(remaining)]
            return FunctionSignature(returnType.strip(), params)
        raise ParseError(name, "Could not parse signature", func)

    def strippedPrettyPrint(self, name: str, e: html.HtmlElement):
        relative = self.manualUrl
        # Replaces relative links with absolute ones.
        for a in e.xpath('.//a'):
            if 'name' in a.attrib:
                del a.attrib['name']
            if 'href' in a.attrib:
                href = a.attrib['href']
                if href.startswith('#'):
                    a.attrib['href'] = relative + href
                else:
                    if not href.startswith(relative):
                        raise ParseError(name, "Unexpected href", href)
        s = typing.cast(bytes, html.tostring(e, pretty_print=True)).decode()
        return javaComment.sub(
            '*&#47;',
            trailingNewline.sub(
                '\n</p>',
                dupNewline.sub('\n', s),
            ),
        )

    def getDescription(self, name: str, pre: html.HtmlElement):
        e = pre
        description = ''
        while e.getnext().tag not in ['hr', 'h1']:
            e: html.HtmlElement = e.getnext()
            description += '\n'
            description += self.strippedPrettyPrint(name, e)
        return description


# In[1]:
paramTypedDescriptions = {
    'ptr': {'lua_State *': 'the <code>lua_State*</code> pointer'},
    'L': {'lua_State *': 'the <code>lua_State*</code> pointer'},
    'L1': {'lua_State *': 'a <code>lua_State*</code> pointer'},
    'arg': {'int': 'function argument index'},
    'array': {'jobject': 'the Java array'},
    'b': {'int': 'boolean'},
    'buffer': {'unsigned char *': 'the buffer (expecting direct)'},
    'clazz': {'jobject': 'the Java class',
              'jclass': 'the Java class'},
    'ctx': {'int *': 'the context storage'},
    'data': {'int': 'data'},
    'e': {'const char *': 'field name'},
    'errfunc': {'int': '0 or the stack index of an error handler function'},
    'extra': {'int': 'extra slots'},
    'fname': {'const char *': 'the filename'},
    'from': {'lua_State *': 'a thread'},
    'fromidx': {'int': 'a stack position'},
    'func': {'jobject': 'the function object'},
    'funcindex': {'int': 'the stack position of the closure'},
    'funcindex1': {'int': 'the stack position of the closure'},
    'funcindex2': {'int': 'the stack position of the closure'},
    'i': {'int': 'i',
          'lua_Integer': 'i'},
    'idx': {'int': 'the stack position'},
    'index': {'int': 'the stack position of the element'},
    'index1': {'int': 'the stack position of the first element'},
    'index2': {'int': 'the stack position of the second element'},
    'isnum': {'int *': 'pointer to a boolean to be assigned'},
    'k': {'const char *': 'the field name'},
    'len': {'size_t *': 'pointer to length'},
    'level': {'int': 'the running level'},
    'lib': {'const char *': 'library name'},
    'lid': {'int': 'the id of the Lua state, '
            + 'to be used to identify between Java and Lua'},
    'lvl': {'int': 'the running level'},
    'method': {'const char *': 'the method name'},
    'msgh': {'int': 'stack position of message handler'},
    'msg': {'const char *': 'a message'},
    'n': {'int': 'the number of elements',
          'lua_Integer': 'the number / the number of elements',
          'lua_Number': 'the number / the number of elements',
          'lua_Unsigned': 'the value n'},
    'n1': {'int': 'n1'},
    'n2': {'int': 'n2'},
    'name': {'const char *': 'the name'},
    'narg': {'int': 'the number of arguments'},
    'nargs': {'int': 'the number of arguments that you pushed onto the stack'},
    'narr': {'int': 'the number of pre-allocated array elements'},
    'nrec': {'int': 'the number of pre-allocated non-array elements'},
    'nresults': {'int': 'the number of results, or <code>LUA_MULTRET</code>',
                 'int *': 'pointer to the number of results'},
    'nuvalue': {'int': 'number of associated Lua values (user values)'},
    'obj': {'int': 'the stack position of the object',
            'jobject': 'the Java object'},
    'op': {'int': 'the operator'},
    'p': {'const char *': 'the replaced sequence',
          'void *': 'the pointer',
          'const void *': 'the lightuserdata'},
    'params': {'const char *': 'encoded parameter types'},
    'ref': {'int': 'the reference'},
    'r': {'const char *': 'the replacing string'},
    's': {'const char *': 'the string'},
    'sig': {'const char *': 'the method signature used in {@code GetMethodID}'},
    'size': {'size_t': 'size', 'int': 'size'},
    'start': {'int': 'the starting index'},
    'stat': {'int': '(I have no idea)'},
    'str': {'const char *': 'string'},
    't': {'int': 'the stack index'},
    'tname': {'const char *': 'type name'},
    'to': {'lua_State *': 'another thread'},
    'tocont': {'int': 'continue or not'},
    'toidx': {'int': 'another stack position'},
    'tp': {'int': 'type id'},
    'what': {'int': 'what'},
}
returnTypes = {
    'const char *': 'String',
    'unsigned char *': 'Buffer',
    'void': 'void',
    'int': 'int',
    'lua_State *': 'long',
    'void *': 'long',
    'size_t': 'long',
    'lua_Integer': 'long',
    'lua_Number': 'double',
    'const void *': 'long',
    'int *': 'long',
    'lua_Unsigned': 'long',
    'const lua_Number *': 'long',
    'size_t *': 'long',
    'jobject': 'Object',
    'jclass': 'Class',
}
overrideFunctions = {
    'luaL_newstate': [
        'long luaL_newstate(int lid)',
        '''lua_State* L = luaL_newstate();
luaJavaSetup(L, env, lid);
return (jlong) L;''',
        'int', 'lid',
    ],
    'lua_pushinteger': [
        'void lua_pushinteger(long ptr, long n)',
        '''lua_State * L = (lua_State *) ptr;
// What we want to achieve here is:
// Pushing any Java number (long or double) always results in an approximated number on the stack,
// unless the number is a Java long integer and the Lua version supports 64-bit integer,
// when we just push an 64-bit integer instead.
// The two cases either produce an approximated number or the exact integer value.

// The following code ensures that no truncation can happen,
// and the pushed number is either approximated or precise.

// If the compiler is smart enough, it will optimize
// the following code into a branch-less single push.
if (sizeof(lua_Integer) == 4) {
  lua_pushnumber((lua_State *) L, (lua_Number) n);
} else {
  lua_pushinteger((lua_State *) L, (lua_Integer) n);
}
''',
        'lua_State *', 'L',
        'lua_Integer', 'n',
    ],
    'lua_tointeger': [
        'long lua_tointeger(long ptr, int index)',
        '''lua_State * L = (lua_State *) ptr;
// See lua_pushinteger for comments.
if (sizeof(lua_Integer) == 4) {
  return (jlong) lua_tonumber(L, index);
} else {
  return (jlong) lua_tointeger(L, index);
}
''',
        'lua_State *', 'L',
        'int', 'index',
    ],
}
paramNormalizations = {
    'luaJ_rawgeti': {
        'i': 'int',
        'n': 'int',
    },
    'lua_rawseti': {
        'i': 'int',
        'n': 'int',
    },
}


# In[1]:
emptyPattern = re.compile('(     \\*( )?\n){2,}')

class JavaBoilerplate:
    luaApis: list[LuaAPI]

    doc: LuaDocumentation

    errors: list[str]

    def __init__(self, doc: LuaDocumentation, luaApis: list[LuaAPI]) -> None:
        self.luaApis = luaApis
        self.doc = doc
        self.errors = doc.errors

    def formatJavadoc(self, f: LuaAPI):
        luaWrapper = (
            'A wrapper function' if 'luaJ' in f.name and f.original is None
            else f'''Wrapper of <a href="{self.doc.manualUrl}#{
                f.original or f.name
            }"><code>{f.original or f.name}</code></a>'''
        )
        quote = "" if f.indicator is None else f"<pre><code>\n{f.indicator}\n</code></pre>"
        pre = "" if f.declaration is None else f"<pre><code>\n{f.declaration}\n</code></pre>"
        description = (
            f.description if '<p>' in f.description
            else f'<p>\n{f.description}\n</p>'
        )
        return emptyPattern.sub('     *\n', (
f"""    /**
{self._quote_javadoc(luaWrapper)}
     *
{self._quote_javadoc(quote)}
     *
{self._quote_javadoc(pre)}
     *
{self._quote_javadoc(description)}
     *
{self._quote_javadoc(self._javadocSignature(f))}
     */
{self._javaSignature(f)} /*
{self._jniGen(f)}
    */
"""
        )).replace('* \n', '*\n')

    @classmethod
    def _quote_javadoc(cls, s: str):
        return '\n'.join(('     * ' + line) for line in s.strip().split('\n'))

    def _getParamDescription(self, paramType: str, paramName: str, f: LuaAPI):
        if 'upvalue' in f.name:
            if paramName == 'n':
                return 'the index in the upvalue'
        desc = paramTypedDescriptions.get(paramName, {}).get(paramType)
        if desc is None:
            raise Exception(f'Unknown param {paramType} {paramName}')
        return desc

    def _normalizeParams(self, f: LuaAPI):
        if f.name in overrideFunctions:
            p = overrideFunctions[f.name][2:]
            fParams = list(zip(p[::2], p[1::2]))
        else:
            fParams = f.signature.params
        params = []
        for paramType, name in fParams:
            if paramType == 'JNIEnv *':
                continue
            if name == "L":
                name = "ptr"
            normedType = paramNormalizations.get(f.name, {}).get(name)
            if normedType is not None:
                paramType = normedType
            params.append((paramType, name))
        return params

    def _javadocSignature(self, f: LuaAPI):
        doc = "\n".join(
            f"@param {name} {self._getParamDescription(paramType, name, f)}"
            for paramType, name in self._normalizeParams(f)
        )
        if f.signature.returns != "void":
            doc += f"\n@return see description"
        return doc

    def _javaSignature(self, f: LuaAPI):
        if f.name in overrideFunctions:
            return f"    public native {overrideFunctions[f.name][0]};"
        params = ', '.join(
            f"{returnTypes[paramType]} {name}"
            for paramType, name in self._normalizeParams(f)
        )
        returns = returnTypes[f.signature.returns]
        return f"    public native {returns} {f.name}({params});"

    def _jniGen(self, f: LuaAPI):
        if f.name in overrideFunctions:
            jni = overrideFunctions[f.name][1]
        else:
            jni = ""
            if any(t == 'lua_State *' and n == 'L' for t, n in f.signature.params):
                jni += "lua_State * L = (lua_State *) ptr;\n"
            params = ', '.join(f"({t}) {n}" for t, n in f.signature.params)
            call = f"{f.original or f.name}({params});"
            if f.signature.returns != "void":
                returns = returnTypes[f.signature.returns]
                if returns == 'Object':
                    returns = 'jobject'
                elif returns == 'String':
                    returns = 'const char *'
                else:
                    returns = f"j{returns}"
                call = f"{returns} returnValueReceiver = ({returns}) {call}\n"
                if returns == 'const char *':
                    call += f"return env->NewStringUTF(returnValueReceiver);"
                else:
                    call += f"return returnValueReceiver;"
            jni += f"\n{call}"
        return '\n'.join(
            f"        {l}" for l in jni.strip().split('\n')
        ).replace('\n        \n', '\n\n')

    def filter(self, predicate: typing.Callable[[LuaAPI], bool]):
        self.errors.extend(f.name for f in self.luaApis if not predicate(f))
        self.luaApis = [f for f in self.luaApis if predicate(f)]

    def extend(self, functions: list[LuaAPI]):
        self.luaApis.extend(functions)

    def generate(self):
        return [self.formatJavadoc(f) for f in self.luaApis]


# In[1]:
filtered = [
    'lua_pushlstring',
    'lua_tolstring',
    'lua_call',
    'luaL_argcheck',
    'luaL_argerror',
    'luaL_dofile',
    'luaL_loadbuffer',
    'luaL_loadbufferx',
    'luaL_loadfile',
    'luaL_loadfilex',
    'lua_pushliteral',
    'luaL_typeerror',
]

def functionHas(f: LuaAPI, noGo: str):
    if noGo in f.signature.returns or any(
        (noGo in t or noGo in n) for t, n in f.signature.params
    ):
        return True
    return False

def filterFunction(f: LuaAPI):
    if (
        functionHas(f, 'lua_CFunction')
        or functionHas(f, 'lua_Reader')
        or functionHas(f, 'lua_Writer')
        or functionHas(f, 'lua_Alloc')
        or functionHas(f, 'luaL_Buffer')
        or functionHas(f, 'luaL_Reg')
        or functionHas(f, 'lua_Debug')
        or functionHas(f, 'lua_Hook')
        or functionHas(f, 'lua_KFunction')
        or functionHas(f, 'lua_WarnFunction')
        or functionHas(f, 'fmt')
        or f.name in filtered
        or f.name.startswith('luaL_check')
        or f.name.startswith('luaL_opt')
        or f.name.startswith('luaL_arg')
    ):
        return False
    else:
        return True


# In[1]:
extraFunctions = [
    LuaAPI(
        name='luaJ_openlib',
        description='Open a library indivisually, alternative to <code>luaL_openlibs</code>',
        signature=FunctionSignature(
            returns='void',
            params=[
                ('lua_State *', 'L'),
                ('const char *', 'lib'),
            ],
        ),
    ),
    LuaAPI(
        name='luaJ_compare',
        description='See <code>lua_compare</code>',
        signature=FunctionSignature(
            returns='int',
            params=[
                ('lua_State *', 'L'),
                ('int', 'index1'),
                ('int', 'index2'),
                ('int', 'op'),
            ],
        ),
    ),
    LuaAPI(
        name='luaJ_len',
        description='Wrapper of <code>lua_(obj)len</code>',
        signature=FunctionSignature(
            returns='int',
            params=[
                ('lua_State *', 'L'),
                ('int', 'index'),
            ],
        ),
    ),
    LuaAPI(
        name='luaJ_loadbuffer',
        description='Load a direct buffer',
        signature=FunctionSignature(
            returns='int',
            params=[
                ('lua_State *', 'L'),
                ('unsigned char *', 'buffer'),
                ('int', 'start'),
                ('int', 'size'),
                ('const char *', 'name'),
            ],
        ),
    ),
    LuaAPI(
        name='luaJ_dobuffer',
        description='Run a direct buffer',
        signature=FunctionSignature(
            returns='int',
            params=[
                ('lua_State *', 'L'),
                ('unsigned char *', 'buffer'),
                ('int', 'start'),
                ('int', 'size'),
                ('const char *', 'name'),
            ],
        ),
    ),
    LuaAPI(
        name='luaJ_resume',
        description='Resume a coroutine',
        signature=FunctionSignature(
            returns='int',
            params=[
                ('lua_State *', 'L'),
                ('int', 'nargs'),
            ],
        ),
    ),
    LuaAPI(
        name='luaJ_pushobject',
        description='Push a Java object',
        signature=FunctionSignature(
            returns='void',
            params=[
                ('JNIEnv *', 'env'),
                ('lua_State *', 'L'),
                ('jobject', 'obj'),
            ],
        ),
    ),
    LuaAPI(
        name='luaJ_pushclass',
        description='Push a Java class',
        signature=FunctionSignature(
            returns='void',
            params=[
                ('JNIEnv *', 'env'),
                ('lua_State *', 'L'),
                ('jobject', 'clazz'),
            ],
        ),
    ),
    LuaAPI(
        name='luaJ_pusharray',
        description='Push a Java array',
        signature=FunctionSignature(
            returns='void',
            params=[
                ('JNIEnv *', 'env'),
                ('lua_State *', 'L'),
                ('jobject', 'array'),
            ],
        ),
    ),
    LuaAPI(
        name='luaJ_pushfunction',
        description='Push a JFunction',
        signature=FunctionSignature(
            returns='void',
            params=[
                ('JNIEnv *', 'env'),
                ('lua_State *', 'L'),
                ('jobject', 'func'),
            ],
        ),
    ),
    LuaAPI(
        name='luaJ_pushlstring',
        description='Push a buffer as a raw Lua string',
        signature=FunctionSignature(
            returns='void',
            params=[
                ('lua_State *', 'L'),
                ('unsigned char *', 'buffer'),
                ('int', 'start'),
                ('int', 'size'),
            ],
        ),
    ),
    LuaAPI(
        name='luaJ_isobject',
        description='Is a Java object (including object, array or class)',
        signature=FunctionSignature(
            returns='int',
            params=[
                ('lua_State *', 'L'),
                ('int', 'index'),
            ],
        ),
    ),
    LuaAPI(
        name='luaJ_toobject',
        description='Convert to Java object if it is one',
        signature=FunctionSignature(
            returns='jobject',
            params=[
                ('lua_State *', 'L'),
                ('int', 'index'),
            ],
        ),
    ),
    LuaAPI(
        name='luaJ_newthread',
        description='Create a new thread',
        signature=FunctionSignature(
            returns='lua_State *',
            params=[
                ('lua_State *', 'L'),
                ('int', 'lid'),
            ],
        ),
    ),
    LuaAPI(
        name='luaJ_initloader',
        description='Append a searcher loading from Java side into <code>package.searchers / loaders</code>',
        signature=FunctionSignature(
            returns='int',
            params=[
                ('lua_State *', 'L'),
            ],
        ),
    ),
    LuaAPI(
        name='luaJ_invokespecial',
        description='Runs {@code CallNonvirtual<type>MethodA}. See AbstractLua for usages.\nParameters should be boxed and pushed on stack.',
        signature=FunctionSignature(
            returns='int',
            params=[
                ('JNIEnv *', 'env'),
                ('lua_State *', 'L'),
                ('jclass', 'clazz'),
                ('const char *', 'method'),
                ('const char *', 'sig'),
                ('jobject', 'obj'),
                ('const char *', 'params'),
            ],
        ),
    ),
    LuaAPI(
        name='luaJ_isinteger',
        description='See <code>lua_isinteger</code>',
        signature=FunctionSignature(
            returns='int',
            params=[
                ('lua_State *', 'L'),
                ('int', 'index'),
            ],
        ),
    ),
    LuaAPI(
        name='luaJ_removestateindex',
        description='Removes the thread from the global registry, thus allowing it to get garbage collected',
        signature=FunctionSignature(
            returns='void',
            params=[
                ('lua_State *', 'L'),
            ],
        ),
    ),
    LuaAPI(
        name='luaJ_gc',
        description='Performs a full garbage-collection cycle',
        signature=FunctionSignature(
            returns='void',
            params=[
                ('lua_State *', 'L'),
            ],
        ),
    ),
    LuaAPI(
        name='luaJ_dumptobuffer',
        description='See <code>lua_dump</code>',
        signature=FunctionSignature(
            returns='jobject',
            params=[
                ('lua_State *', 'L'),
            ],
        ),
    ),
    LuaAPI(
        name='luaJ_tobuffer',
        description='See <code>lua_tolstring</code>',
        signature=FunctionSignature(
            returns='jobject',
            params=[
                ('lua_State *', 'L'),
                ('int', 'index'),
            ],
        ),
    ),
    LuaAPI(
        name='luaJ_todirectbuffer',
        description='See <code>lua_tolstring</code>',
        signature=FunctionSignature(
            returns='jobject',
            params=[
                ('lua_State *', 'L'),
                ('int', 'index'),
            ],
        ),
    ),
]
returnValueInconsistencies = [
    'lua_getfield',
    'lua_getglobal',
    'lua_geti',
    'lua_rawget',
    'lua_rawgeti',
    'lua_setmetatable',
    'lua_pushstring',
    'lua_gettable',
    'luaL_getmetatable',
]

def gatherFunctions(existing: list[LuaAPI]):
    functions = []
    for f in existing:
        functions.append(f)
        if f.name in returnValueInconsistencies:
            functions.append(LuaAPI(
                name=f.name.replace('lua_', 'luaJ_').replace('luaL_', 'luaJ_'),
                declaration=f.declaration,
                description=f.description,
                signature=FunctionSignature(
                    returns='void',
                    params=f.signature.params,
                ),
                indicator=f.indicator,
                original=f.name,
            ))
    return functions

def getWhole(luaVersion: str, package: str):
    className = f"Lua{luaVersion.replace('.', '')}Natives"
    inner = (
        f'''@SuppressWarnings({{"unused", "rawtypes"}})
public class {className} implements LuaNatives {{
        /*JNI
            #include "luacustomamalg.h"

            #include "lua.hpp"
            #include "jni.h"

            #include "jua.h"

            #include "luacomp.h"

            #include "juaapi.h"
            #include "jualib.h"
            #include "juaamalg.h"

            #include "luacustom.h"
         */

    private final static AtomicReference<String> loaded = new AtomicReference<>(null);

    protected {className}() throws IllegalStateException {{
        synchronized (loaded) {{
            if (loaded.get() != null) {{ return; }}
            try {{
                GlobalLibraryLoader.register(Lua{luaVersion.replace('.', '')}Natives.class, false);
                String file = GlobalLibraryLoader.load("lua{luaVersion.replace('.', '')}");
                if (initBindings() != 0) {{
                    throw new RuntimeException("Unable to init bindings");
                }}
                loaded.set(file);
            }} catch (Throwable e) {{
                throw new IllegalStateException(e);
            }}
        }}
    }}

    /**
     * Exposes the symbols in the natives to external libraries.
     *
     * <p>
     *     Users are only allowed load one instance of natives if they want it global.
     *     Otherwise, the JVM might just crash due to identical symbol names in different binaries.
     * </p>
     */
    public void loadAsGlobal() {{
        GlobalLibraryLoader.register(this.getClass(), true);
        reopenGlobal(loaded.get());
    }}

    private native int reopenGlobal(String file); /*
        return (jint) reopenAsGlobal((const char *) file);
    */

    private native static int initBindings() throws Exception; /*
        return (jint) initLua{luaVersion.replace('.', '')}Bindings(env);
    */

    /**
     * Get <code>LUA_REGISTRYINDEX</code>, which is a computed compile time constant
     */
    public native int getRegistryIndex(); /*
        return LUA_REGISTRYINDEX;
    */

'''
    )
    doc = LuaDocumentation(luaVersion)
    java = JavaBoilerplate(doc, doc.parseFunctions())
    java.luaApis = gatherFunctions(java.luaApis)
    java.extend(extraFunctions)
    java.filter(filterFunction)
    inner += "\n\n".join(java.generate())
    inner += "\n\n}\n"
    newline = "\n"
    comment = (
f"""/*
 * Copyright (C) 2022 the original author or authors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package {package};

import java.util.concurrent.atomic.AtomicReference;
import java.nio.Buffer;

import party.iroiro.luajava.LuaNatives;
import party.iroiro.luajava.util.GlobalLibraryLoader;

/**
 * Lua C API wrappers
 *
 * <p>
 * This file is programmatically generated from <a href="{doc.manualUrl}">the Lua {luaVersion} Reference Manual</a>.
 * </p>
 * <p>
 * The following functions are excluded:
 * <ul>
{newline.join(f' * <li><code>{name}</code></li>' for name in sorted(java.errors))}
 * </ul>
 */"""
    )
    output = comment + '\n' + inner
    return className, output


# In[1]:


if len(sys.argv) != 4:
    print(f'Usage: {sys.argv[0]} luaVersion javaPackage outputFolder')
else:
    name, output = getWhole(sys.argv[1], sys.argv[2])
    directory = os.path.join(sys.argv[3], *(sys.argv[2].split('.')))
    out = os.path.join(directory, name + '.java')
    if input(f'Writing to {out}, continue? (yes/no) ') != None:
        os.makedirs(directory, exist_ok=True)
        f = open(out, 'w')
        f.write(output)
        f.close()
        print('Written')
    else:
        print('Cancelled')
