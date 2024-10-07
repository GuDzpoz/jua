/*
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

package party.iroiro.luajava.lua54;

import java.util.concurrent.atomic.AtomicReference;
import java.nio.Buffer;

import party.iroiro.luajava.LuaNatives;
import party.iroiro.luajava.util.GlobalLibraryLoader;

/**
 * Lua C API wrappers
 *
 * <p>
 * This file is programmatically generated from <a href="https://www.lua.org/manual/5.4/manual.html">the Lua 5.4 Reference Manual</a>.
 * </p>
 * <p>
 * The following functions are excluded:
 * <ul>
 * <li><code>luaL_addchar</code></li>
 * <li><code>luaL_addgsub</code></li>
 * <li><code>luaL_addlstring</code></li>
 * <li><code>luaL_addsize</code></li>
 * <li><code>luaL_addstring</code></li>
 * <li><code>luaL_addvalue</code></li>
 * <li><code>luaL_argcheck</code></li>
 * <li><code>luaL_argerror</code></li>
 * <li><code>luaL_argexpected</code></li>
 * <li><code>luaL_buffaddr</code></li>
 * <li><code>luaL_buffinit</code></li>
 * <li><code>luaL_buffinitsize</code></li>
 * <li><code>luaL_bufflen</code></li>
 * <li><code>luaL_buffsub</code></li>
 * <li><code>luaL_checkany</code></li>
 * <li><code>luaL_checkinteger</code></li>
 * <li><code>luaL_checklstring</code></li>
 * <li><code>luaL_checknumber</code></li>
 * <li><code>luaL_checkoption</code></li>
 * <li><code>luaL_checkstack</code></li>
 * <li><code>luaL_checkstring</code></li>
 * <li><code>luaL_checktype</code></li>
 * <li><code>luaL_checkudata</code></li>
 * <li><code>luaL_checkversion</code></li>
 * <li><code>luaL_dofile</code></li>
 * <li><code>luaL_error</code></li>
 * <li><code>luaL_loadbuffer</code></li>
 * <li><code>luaL_loadbufferx</code></li>
 * <li><code>luaL_loadfile</code></li>
 * <li><code>luaL_loadfilex</code></li>
 * <li><code>luaL_newlib</code></li>
 * <li><code>luaL_newlibtable</code></li>
 * <li><code>luaL_opt</code></li>
 * <li><code>luaL_optinteger</code></li>
 * <li><code>luaL_optlstring</code></li>
 * <li><code>luaL_optnumber</code></li>
 * <li><code>luaL_optstring</code></li>
 * <li><code>luaL_prepbuffer</code></li>
 * <li><code>luaL_prepbuffsize</code></li>
 * <li><code>luaL_pushresult</code></li>
 * <li><code>luaL_pushresultsize</code></li>
 * <li><code>luaL_requiref</code></li>
 * <li><code>luaL_setfuncs</code></li>
 * <li><code>luaL_typeerror</code></li>
 * <li><code>lua_atpanic</code></li>
 * <li><code>lua_call</code></li>
 * <li><code>lua_callk</code></li>
 * <li><code>lua_dump</code></li>
 * <li><code>lua_gc</code></li>
 * <li><code>lua_getallocf</code></li>
 * <li><code>lua_gethook</code></li>
 * <li><code>lua_getinfo</code></li>
 * <li><code>lua_getlocal</code></li>
 * <li><code>lua_getstack</code></li>
 * <li><code>lua_load</code></li>
 * <li><code>lua_newstate</code></li>
 * <li><code>lua_pcallk</code></li>
 * <li><code>lua_pushcclosure</code></li>
 * <li><code>lua_pushcfunction</code></li>
 * <li><code>lua_pushfstring</code></li>
 * <li><code>lua_pushliteral</code></li>
 * <li><code>lua_pushlstring</code></li>
 * <li><code>lua_pushvfstring</code></li>
 * <li><code>lua_register</code></li>
 * <li><code>lua_setallocf</code></li>
 * <li><code>lua_sethook</code></li>
 * <li><code>lua_setlocal</code></li>
 * <li><code>lua_setwarnf</code></li>
 * <li><code>lua_tocfunction</code></li>
 * <li><code>lua_tolstring</code></li>
 * <li><code>lua_yieldk</code></li>
 * </ul>
 */
@SuppressWarnings({"unused", "rawtypes"})
public class Lua54Natives implements LuaNatives {
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

    protected Lua54Natives() throws IllegalStateException {
        synchronized (loaded) {
            if (loaded.get() != null) { return; }
            try {
                GlobalLibraryLoader.register(Lua54Natives.class, false);
                String file = GlobalLibraryLoader.load("lua54");
                if (initBindings() != 0) {
                    throw new RuntimeException("Unable to init bindings");
                }
                loaded.set(file);
            } catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /**
     * Exposes the symbols in the natives to external libraries.
     *
     * <p>
     *     Users are only allowed load one instance of natives if they want it global.
     *     Otherwise, the JVM might just crash due to identical symbol names in different binaries.
     * </p>
     */
    public void loadAsGlobal() {
        GlobalLibraryLoader.register(this.getClass(), true);
        reopenGlobal(loaded.get());
    }

    private native int reopenGlobal(String file); /*
        return (jint) reopenAsGlobal((const char *) file);
    */

    private native static int initBindings() throws Exception; /*
        return (jint) initLua54Bindings(env);
    */

    /**
     * Get <code>LUA_REGISTRYINDEX</code>, which is a computed compile time constant
     */
    public native int getRegistryIndex(); /*
        return LUA_REGISTRYINDEX;
    */

    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_absindex"><code>lua_absindex</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_absindex (lua_State *L, int idx);
     * </code></pre>
     *
     * <p>
     * Converts the acceptable index <code>idx</code>
     * into an equivalent absolute index
     * (that is, one that does not depend on the stack size).
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param idx the stack position
     * @return see description
     */
    public native int lua_absindex(long ptr, int idx); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_absindex((lua_State *) L, (int) idx);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_arith"><code>lua_arith</code></a>
     *
     * <pre><code>
     * [-(2|1), +1, e]
     * </code></pre>
     *
     * <pre><code>
     * void lua_arith (lua_State *L, int op);
     * </code></pre>
     *
     * <p>
     * Performs an arithmetic or bitwise operation over the two values
     * (or one, in the case of negations)
     * at the top of the stack,
     * with the value on the top being the second operand,
     * pops these values, and pushes the result of the operation.
     * The function follows the semantics of the corresponding Lua operator
     * (that is, it may call metamethods).
     * </p>
     *
     * <p>
     * The value of <code>op</code> must be one of the following constants:
     * </p>
     *
     * <ul>
     *
     * <li>
     * <b><a><code>LUA_OPADD</code></a>: </b> performs addition (<code>+</code>)</li>
     * <li>
     * <b><a><code>LUA_OPSUB</code></a>: </b> performs subtraction (<code>-</code>)</li>
     * <li>
     * <b><a><code>LUA_OPMUL</code></a>: </b> performs multiplication (<code>*</code>)</li>
     * <li>
     * <b><a><code>LUA_OPDIV</code></a>: </b> performs float division (<code>/</code>)</li>
     * <li>
     * <b><a><code>LUA_OPIDIV</code></a>: </b> performs floor division (<code>//</code>)</li>
     * <li>
     * <b><a><code>LUA_OPMOD</code></a>: </b> performs modulo (<code>%</code>)</li>
     * <li>
     * <b><a><code>LUA_OPPOW</code></a>: </b> performs exponentiation (<code>^</code>)</li>
     * <li>
     * <b><a><code>LUA_OPUNM</code></a>: </b> performs mathematical negation (unary <code>-</code>)</li>
     * <li>
     * <b><a><code>LUA_OPBNOT</code></a>: </b> performs bitwise NOT (<code>~</code>)</li>
     * <li>
     * <b><a><code>LUA_OPBAND</code></a>: </b> performs bitwise AND (<code>&amp;</code>)</li>
     * <li>
     * <b><a><code>LUA_OPBOR</code></a>: </b> performs bitwise OR (<code>|</code>)</li>
     * <li>
     * <b><a><code>LUA_OPBXOR</code></a>: </b> performs bitwise exclusive OR (<code>~</code>)</li>
     * <li>
     * <b><a><code>LUA_OPSHL</code></a>: </b> performs left shift (<code>&lt;&lt;</code>)</li>
     * <li>
     * <b><a><code>LUA_OPSHR</code></a>: </b> performs right shift (<code>&gt;&gt;</code>)</li>
     *
     * </ul>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param op the operator
     */
    public native void lua_arith(long ptr, int op); /*
        lua_State * L = (lua_State *) ptr;

        lua_arith((lua_State *) L, (int) op);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_checkstack"><code>lua_checkstack</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_checkstack (lua_State *L, int n);
     * </code></pre>
     *
     * <p>
     * Ensures that the stack has space for at least <code>n</code> extra elements,
     * that is, that you can safely push up to <code>n</code> values into it.
     * It returns false if it cannot fulfill the request,
     * either because it would cause the stack
     * to be greater than a fixed maximum size
     * (typically at least several thousand elements) or
     * because it cannot allocate memory for the extra space.
     * This function never shrinks the stack;
     * if the stack already has space for the extra elements,
     * it is left unchanged.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param n the number of elements
     * @return see description
     */
    public native int lua_checkstack(long ptr, int n); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_checkstack((lua_State *) L, (int) n);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_close"><code>lua_close</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * void lua_close (lua_State *L);
     * </code></pre>
     *
     * <p>
     * Close all active to-be-closed variables in the main thread,
     * release all objects in the given Lua state
     * (calling the corresponding garbage-collection metamethods, if any),
     * and frees all dynamic memory used by this state.
     * </p>
     *
     * <p>
     * On several platforms, you may not need to call this function,
     * because all resources are naturally released when the host program ends.
     * On the other hand, long-running programs that create multiple states,
     * such as daemons or web servers,
     * will probably need to close states as soon as they are not needed.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     */
    public native void lua_close(long ptr); /*
        lua_State * L = (lua_State *) ptr;

        lua_close((lua_State *) L);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_closeslot"><code>lua_closeslot</code></a>
     *
     * <pre><code>
     * [-0, +0, e]
     * </code></pre>
     *
     * <pre><code>
     * void lua_closeslot (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Close the to-be-closed slot at the given index and set its value to <b>nil</b>.
     * The index must be the last index previously marked to be closed
     * (see <a href="https://www.lua.org/manual/5.4/manual.html#lua_toclose"><code>lua_toclose</code></a>) that is still active (that is, not closed yet).
     * </p>
     *
     * <p>
     * A <code>__close</code> metamethod cannot yield
     * when called through this function.
     * </p>
     *
     * <p>
     * (This function was introduced in release&#160;5.4.3.)
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     */
    public native void lua_closeslot(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        lua_closeslot((lua_State *) L, (int) index);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_closethread"><code>lua_closethread</code></a>
     *
     * <pre><code>
     * [-0, +?, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_closethread (lua_State *L, lua_State *from);
     * </code></pre>
     *
     * <p>
     * Resets a thread, cleaning its call stack and closing all pending
     * to-be-closed variables.
     * Returns a status code:
     * <a href="https://www.lua.org/manual/5.4/manual.html#pdf-LUA_OK"><code>LUA_OK</code></a> for no errors in the thread
     * (either the original error that stopped the thread or
     * errors in closing methods),
     * or an error status otherwise.
     * In case of error,
     * leaves the error object on the top of the stack.
     * </p>
     *
     * <p>
     * The parameter <code>from</code> represents the coroutine that is resetting <code>L</code>.
     * If there is no such coroutine,
     * this parameter can be <code>NULL</code>.
     * </p>
     *
     * <p>
     * (This function was introduced in release&#160;5.4.6.)
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param from a thread
     * @return see description
     */
    public native int lua_closethread(long ptr, long from); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_closethread((lua_State *) L, (lua_State *) from);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_compare"><code>lua_compare</code></a>
     *
     * <pre><code>
     * [-0, +0, e]
     * </code></pre>
     *
     * <pre><code>
     * int lua_compare (lua_State *L, int index1, int index2, int op);
     * </code></pre>
     *
     * <p>
     * Compares two Lua values.
     * Returns 1 if the value at index <code>index1</code> satisfies <code>op</code>
     * when compared with the value at index <code>index2</code>,
     * following the semantics of the corresponding Lua operator
     * (that is, it may call metamethods).
     * Otherwise returns&#160;0.
     * Also returns&#160;0 if any of the indices is not valid.
     * </p>
     *
     * <p>
     * The value of <code>op</code> must be one of the following constants:
     * </p>
     *
     * <ul>
     *
     * <li>
     * <b><a><code>LUA_OPEQ</code></a>: </b> compares for equality (<code>==</code>)</li>
     * <li>
     * <b><a><code>LUA_OPLT</code></a>: </b> compares for less than (<code>&lt;</code>)</li>
     * <li>
     * <b><a><code>LUA_OPLE</code></a>: </b> compares for less or equal (<code>&lt;=</code>)</li>
     *
     * </ul>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index1 the stack position of the first element
     * @param index2 the stack position of the second element
     * @param op the operator
     * @return see description
     */
    public native int lua_compare(long ptr, int index1, int index2, int op); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_compare((lua_State *) L, (int) index1, (int) index2, (int) op);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_concat"><code>lua_concat</code></a>
     *
     * <pre><code>
     * [-n, +1, e]
     * </code></pre>
     *
     * <pre><code>
     * void lua_concat (lua_State *L, int n);
     * </code></pre>
     *
     * <p>
     * Concatenates the <code>n</code> values at the top of the stack,
     * pops them, and leaves the result on the top.
     * If <code>n</code>&#160;is&#160;1, the result is the single value on the stack
     * (that is, the function does nothing);
     * if <code>n</code> is 0, the result is the empty string.
     * Concatenation is performed following the usual semantics of Lua
     * (see <a href="https://www.lua.org/manual/5.4/manual.html#3.4.6">&#167;3.4.6</a>).
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param n the number of elements
     */
    public native void lua_concat(long ptr, int n); /*
        lua_State * L = (lua_State *) ptr;

        lua_concat((lua_State *) L, (int) n);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_copy"><code>lua_copy</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * void lua_copy (lua_State *L, int fromidx, int toidx);
     * </code></pre>
     *
     * <p>
     * Copies the element at index <code>fromidx</code>
     * into the valid index <code>toidx</code>,
     * replacing the value at that position.
     * Values at other positions are not affected.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param fromidx a stack position
     * @param toidx another stack position
     */
    public native void lua_copy(long ptr, int fromidx, int toidx); /*
        lua_State * L = (lua_State *) ptr;

        lua_copy((lua_State *) L, (int) fromidx, (int) toidx);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_createtable"><code>lua_createtable</code></a>
     *
     * <pre><code>
     * [-0, +1, m]
     * </code></pre>
     *
     * <pre><code>
     * void lua_createtable (lua_State *L, int narr, int nrec);
     * </code></pre>
     *
     * <p>
     * Creates a new empty table and pushes it onto the stack.
     * Parameter <code>narr</code> is a hint for how many elements the table
     * will have as a sequence;
     * parameter <code>nrec</code> is a hint for how many other elements
     * the table will have.
     * Lua may use these hints to preallocate memory for the new table.
     * This preallocation may help performance when you know in advance
     * how many elements the table will have.
     * Otherwise you can use the function <a href="https://www.lua.org/manual/5.4/manual.html#lua_newtable"><code>lua_newtable</code></a>.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param narr the number of pre-allocated array elements
     * @param nrec the number of pre-allocated non-array elements
     */
    public native void lua_createtable(long ptr, int narr, int nrec); /*
        lua_State * L = (lua_State *) ptr;

        lua_createtable((lua_State *) L, (int) narr, (int) nrec);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_error"><code>lua_error</code></a>
     *
     * <pre><code>
     * [-1, +0, v]
     * </code></pre>
     *
     * <pre><code>
     * int lua_error (lua_State *L);
     * </code></pre>
     *
     * <p>
     * Raises a Lua error,
     * using the value on the top of the stack as the error object.
     * This function does a long jump,
     * and therefore never returns
     * (see <a href="https://www.lua.org/manual/5.4/manual.html#luaL_error"><code>luaL_error</code></a>).
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @return see description
     */
    public native int lua_error(long ptr); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_error((lua_State *) L);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_getfield"><code>lua_getfield</code></a>
     *
     * <pre><code>
     * [-0, +1, e]
     * </code></pre>
     *
     * <pre><code>
     * int lua_getfield (lua_State *L, int index, const char *k);
     * </code></pre>
     *
     * <p>
     * Pushes onto the stack the value <code>t[k]</code>,
     * where <code>t</code> is the value at the given index.
     * As in Lua, this function may trigger a metamethod
     * for the "index" event (see <a href="https://www.lua.org/manual/5.4/manual.html#2.4">&#167;2.4</a>).
     * </p>
     *
     * <p>
     * Returns the type of the pushed value.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @param k the field name
     * @return see description
     */
    public native int lua_getfield(long ptr, int index, String k); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_getfield((lua_State *) L, (int) index, (const char *) k);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_getfield"><code>lua_getfield</code></a>
     *
     * <pre><code>
     * [-0, +1, e]
     * </code></pre>
     *
     * <pre><code>
     * int lua_getfield (lua_State *L, int index, const char *k);
     * </code></pre>
     *
     * <p>
     * Pushes onto the stack the value <code>t[k]</code>,
     * where <code>t</code> is the value at the given index.
     * As in Lua, this function may trigger a metamethod
     * for the "index" event (see <a href="https://www.lua.org/manual/5.4/manual.html#2.4">&#167;2.4</a>).
     * </p>
     *
     * <p>
     * Returns the type of the pushed value.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @param k the field name
     */
    public native void luaJ_getfield(long ptr, int index, String k); /*
        lua_State * L = (lua_State *) ptr;

        lua_getfield((lua_State *) L, (int) index, (const char *) k);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_getextraspace"><code>lua_getextraspace</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * void *lua_getextraspace (lua_State *L);
     * </code></pre>
     *
     * <p>
     * Returns a pointer to a raw memory area associated with the
     * given Lua state.
     * The application can use this area for any purpose;
     * Lua does not use it for anything.
     * </p>
     *
     * <p>
     * Each new thread has this area initialized with a copy
     * of the area of the main thread.
     * </p>
     *
     * <p>
     * By default, this area has the size of a pointer to void,
     * but you can recompile Lua with a different size for this area.
     * (See <code>LUA_EXTRASPACE</code> in <code>luaconf.h</code>.)
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @return see description
     */
    public native long lua_getextraspace(long ptr); /*
        lua_State * L = (lua_State *) ptr;

        jlong returnValueReceiver = (jlong) lua_getextraspace((lua_State *) L);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_getglobal"><code>lua_getglobal</code></a>
     *
     * <pre><code>
     * [-0, +1, e]
     * </code></pre>
     *
     * <pre><code>
     * int lua_getglobal (lua_State *L, const char *name);
     * </code></pre>
     *
     * <p>
     * Pushes onto the stack the value of the global <code>name</code>.
     * Returns the type of that value.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param name the name
     * @return see description
     */
    public native int lua_getglobal(long ptr, String name); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_getglobal((lua_State *) L, (const char *) name);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_getglobal"><code>lua_getglobal</code></a>
     *
     * <pre><code>
     * [-0, +1, e]
     * </code></pre>
     *
     * <pre><code>
     * int lua_getglobal (lua_State *L, const char *name);
     * </code></pre>
     *
     * <p>
     * Pushes onto the stack the value of the global <code>name</code>.
     * Returns the type of that value.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param name the name
     */
    public native void luaJ_getglobal(long ptr, String name); /*
        lua_State * L = (lua_State *) ptr;

        lua_getglobal((lua_State *) L, (const char *) name);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_geti"><code>lua_geti</code></a>
     *
     * <pre><code>
     * [-0, +1, e]
     * </code></pre>
     *
     * <pre><code>
     * int lua_geti (lua_State *L, int index, lua_Integer i);
     * </code></pre>
     *
     * <p>
     * Pushes onto the stack the value <code>t[i]</code>,
     * where <code>t</code> is the value at the given index.
     * As in Lua, this function may trigger a metamethod
     * for the "index" event (see <a href="https://www.lua.org/manual/5.4/manual.html#2.4">&#167;2.4</a>).
     * </p>
     *
     * <p>
     * Returns the type of the pushed value.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @param i i
     * @return see description
     */
    public native int lua_geti(long ptr, int index, long i); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_geti((lua_State *) L, (int) index, (lua_Integer) i);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_geti"><code>lua_geti</code></a>
     *
     * <pre><code>
     * [-0, +1, e]
     * </code></pre>
     *
     * <pre><code>
     * int lua_geti (lua_State *L, int index, lua_Integer i);
     * </code></pre>
     *
     * <p>
     * Pushes onto the stack the value <code>t[i]</code>,
     * where <code>t</code> is the value at the given index.
     * As in Lua, this function may trigger a metamethod
     * for the "index" event (see <a href="https://www.lua.org/manual/5.4/manual.html#2.4">&#167;2.4</a>).
     * </p>
     *
     * <p>
     * Returns the type of the pushed value.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @param i i
     */
    public native void luaJ_geti(long ptr, int index, long i); /*
        lua_State * L = (lua_State *) ptr;

        lua_geti((lua_State *) L, (int) index, (lua_Integer) i);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_getmetatable"><code>lua_getmetatable</code></a>
     *
     * <pre><code>
     * [-0, +(0|1), –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_getmetatable (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * If the value at the given index has a metatable,
     * the function pushes that metatable onto the stack and returns&#160;1.
     * Otherwise,
     * the function returns&#160;0 and pushes nothing on the stack.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native int lua_getmetatable(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_getmetatable((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_gettable"><code>lua_gettable</code></a>
     *
     * <pre><code>
     * [-1, +1, e]
     * </code></pre>
     *
     * <pre><code>
     * int lua_gettable (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Pushes onto the stack the value <code>t[k]</code>,
     * where <code>t</code> is the value at the given index
     * and <code>k</code> is the value on the top of the stack.
     * </p>
     *
     * <p>
     * This function pops the key from the stack,
     * pushing the resulting value in its place.
     * As in Lua, this function may trigger a metamethod
     * for the "index" event (see <a href="https://www.lua.org/manual/5.4/manual.html#2.4">&#167;2.4</a>).
     * </p>
     *
     * <p>
     * Returns the type of the pushed value.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native int lua_gettable(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_gettable((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_gettable"><code>lua_gettable</code></a>
     *
     * <pre><code>
     * [-1, +1, e]
     * </code></pre>
     *
     * <pre><code>
     * int lua_gettable (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Pushes onto the stack the value <code>t[k]</code>,
     * where <code>t</code> is the value at the given index
     * and <code>k</code> is the value on the top of the stack.
     * </p>
     *
     * <p>
     * This function pops the key from the stack,
     * pushing the resulting value in its place.
     * As in Lua, this function may trigger a metamethod
     * for the "index" event (see <a href="https://www.lua.org/manual/5.4/manual.html#2.4">&#167;2.4</a>).
     * </p>
     *
     * <p>
     * Returns the type of the pushed value.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     */
    public native void luaJ_gettable(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        lua_gettable((lua_State *) L, (int) index);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_gettop"><code>lua_gettop</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_gettop (lua_State *L);
     * </code></pre>
     *
     * <p>
     * Returns the index of the top element in the stack.
     * Because indices start at&#160;1,
     * this result is equal to the number of elements in the stack;
     * in particular, 0&#160;means an empty stack.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @return see description
     */
    public native int lua_gettop(long ptr); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_gettop((lua_State *) L);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_getiuservalue"><code>lua_getiuservalue</code></a>
     *
     * <pre><code>
     * [-0, +1, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_getiuservalue (lua_State *L, int index, int n);
     * </code></pre>
     *
     * <p>
     * Pushes onto the stack the <code>n</code>-th user value associated with the
     * full userdata at the given index and
     * returns the type of the pushed value.
     * </p>
     *
     * <p>
     * If the userdata does not have that value,
     * pushes <b>nil</b> and returns <a href="https://www.lua.org/manual/5.4/manual.html#pdf-LUA_TNONE"><code>LUA_TNONE</code></a>.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @param n the number of elements
     * @return see description
     */
    public native int lua_getiuservalue(long ptr, int index, int n); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_getiuservalue((lua_State *) L, (int) index, (int) n);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_insert"><code>lua_insert</code></a>
     *
     * <pre><code>
     * [-1, +1, –]
     * </code></pre>
     *
     * <pre><code>
     * void lua_insert (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Moves the top element into the given valid index,
     * shifting up the elements above this index to open space.
     * This function cannot be called with a pseudo-index,
     * because a pseudo-index is not an actual stack position.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     */
    public native void lua_insert(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        lua_insert((lua_State *) L, (int) index);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_isboolean"><code>lua_isboolean</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_isboolean (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the value at the given index is a boolean,
     * and 0&#160;otherwise.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native int lua_isboolean(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_isboolean((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_iscfunction"><code>lua_iscfunction</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_iscfunction (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the value at the given index is a C&#160;function,
     * and 0&#160;otherwise.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native int lua_iscfunction(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_iscfunction((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_isfunction"><code>lua_isfunction</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_isfunction (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the value at the given index is a function
     * (either C or Lua), and 0&#160;otherwise.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native int lua_isfunction(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_isfunction((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_isinteger"><code>lua_isinteger</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_isinteger (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the value at the given index is an integer
     * (that is, the value is a number and is represented as an integer),
     * and 0&#160;otherwise.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native int lua_isinteger(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_isinteger((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_islightuserdata"><code>lua_islightuserdata</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_islightuserdata (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the value at the given index is a light userdata,
     * and 0&#160;otherwise.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native int lua_islightuserdata(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_islightuserdata((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_isnil"><code>lua_isnil</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_isnil (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the value at the given index is <b>nil</b>,
     * and 0&#160;otherwise.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native int lua_isnil(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_isnil((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_isnone"><code>lua_isnone</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_isnone (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the given index is not valid,
     * and 0&#160;otherwise.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native int lua_isnone(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_isnone((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_isnoneornil"><code>lua_isnoneornil</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_isnoneornil (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the given index is not valid
     * or if the value at this index is <b>nil</b>,
     * and 0&#160;otherwise.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native int lua_isnoneornil(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_isnoneornil((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_isnumber"><code>lua_isnumber</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_isnumber (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the value at the given index is a number
     * or a string convertible to a number,
     * and 0&#160;otherwise.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native int lua_isnumber(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_isnumber((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_isstring"><code>lua_isstring</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_isstring (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the value at the given index is a string
     * or a number (which is always convertible to a string),
     * and 0&#160;otherwise.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native int lua_isstring(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_isstring((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_istable"><code>lua_istable</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_istable (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the value at the given index is a table,
     * and 0&#160;otherwise.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native int lua_istable(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_istable((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_isthread"><code>lua_isthread</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_isthread (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the value at the given index is a thread,
     * and 0&#160;otherwise.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native int lua_isthread(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_isthread((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_isuserdata"><code>lua_isuserdata</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_isuserdata (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the value at the given index is a userdata
     * (either full or light), and 0&#160;otherwise.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native int lua_isuserdata(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_isuserdata((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_isyieldable"><code>lua_isyieldable</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_isyieldable (lua_State *L);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the given coroutine can yield,
     * and 0&#160;otherwise.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @return see description
     */
    public native int lua_isyieldable(long ptr); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_isyieldable((lua_State *) L);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_len"><code>lua_len</code></a>
     *
     * <pre><code>
     * [-0, +1, e]
     * </code></pre>
     *
     * <pre><code>
     * void lua_len (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns the length of the value at the given index.
     * It is equivalent to the '<code>#</code>' operator in Lua (see <a href="https://www.lua.org/manual/5.4/manual.html#3.4.7">&#167;3.4.7</a>) and
     * may trigger a metamethod for the "length" event (see <a href="https://www.lua.org/manual/5.4/manual.html#2.4">&#167;2.4</a>).
     * The result is pushed on the stack.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     */
    public native void lua_len(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        lua_len((lua_State *) L, (int) index);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_newtable"><code>lua_newtable</code></a>
     *
     * <pre><code>
     * [-0, +1, m]
     * </code></pre>
     *
     * <pre><code>
     * void lua_newtable (lua_State *L);
     * </code></pre>
     *
     * <p>
     * Creates a new empty table and pushes it onto the stack.
     * It is equivalent to <code>lua_createtable(L, 0, 0)</code>.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     */
    public native void lua_newtable(long ptr); /*
        lua_State * L = (lua_State *) ptr;

        lua_newtable((lua_State *) L);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_newthread"><code>lua_newthread</code></a>
     *
     * <pre><code>
     * [-0, +1, m]
     * </code></pre>
     *
     * <pre><code>
     * lua_State *lua_newthread (lua_State *L);
     * </code></pre>
     *
     * <p>
     * Creates a new thread, pushes it on the stack,
     * and returns a pointer to a <a href="https://www.lua.org/manual/5.4/manual.html#lua_State"><code>lua_State</code></a> that represents this new thread.
     * The new thread returned by this function shares with the original thread
     * its global environment,
     * but has an independent execution stack.
     * </p>
     *
     * <p>
     * Threads are subject to garbage collection,
     * like any Lua object.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @return see description
     */
    public native long lua_newthread(long ptr); /*
        lua_State * L = (lua_State *) ptr;

        jlong returnValueReceiver = (jlong) lua_newthread((lua_State *) L);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_newuserdatauv"><code>lua_newuserdatauv</code></a>
     *
     * <pre><code>
     * [-0, +1, m]
     * </code></pre>
     *
     * <pre><code>
     * void *lua_newuserdatauv (lua_State *L, size_t size, int nuvalue);
     * </code></pre>
     *
     * <p>
     * This function creates and pushes on the stack a new full userdata,
     * with <code>nuvalue</code> associated Lua values, called <code>user values</code>,
     * plus an associated block of raw memory with <code>size</code> bytes.
     * (The user values can be set and read with the functions
     * <a href="https://www.lua.org/manual/5.4/manual.html#lua_setiuservalue"><code>lua_setiuservalue</code></a> and <a href="https://www.lua.org/manual/5.4/manual.html#lua_getiuservalue"><code>lua_getiuservalue</code></a>.)
     * </p>
     *
     * <p>
     * The function returns the address of the block of memory.
     * Lua ensures that this address is valid as long as
     * the corresponding userdata is alive (see <a href="https://www.lua.org/manual/5.4/manual.html#2.5">&#167;2.5</a>).
     * Moreover, if the userdata is marked for finalization (see <a href="https://www.lua.org/manual/5.4/manual.html#2.5.3">&#167;2.5.3</a>),
     * its address is valid at least until the call to its finalizer.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param size size
     * @param nuvalue number of associated Lua values (user values)
     * @return see description
     */
    public native long lua_newuserdatauv(long ptr, long size, int nuvalue); /*
        lua_State * L = (lua_State *) ptr;

        jlong returnValueReceiver = (jlong) lua_newuserdatauv((lua_State *) L, (size_t) size, (int) nuvalue);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_next"><code>lua_next</code></a>
     *
     * <pre><code>
     * [-1, +(2|0), v]
     * </code></pre>
     *
     * <pre><code>
     * int lua_next (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Pops a key from the stack,
     * and pushes a key&#8211;value pair from the table at the given index,
     * the "next" pair after the given key.
     * If there are no more elements in the table,
     * then <a href="https://www.lua.org/manual/5.4/manual.html#lua_next"><code>lua_next</code></a> returns&#160;0 and pushes nothing.
     * </p>
     *
     * <p>
     * A typical table traversal looks like this:
     * </p>
     *
     * <pre>
     *      /* table is in the stack at index 't' *&#47;
     *      lua_pushnil(L);  /* first key *&#47;
     *      while (lua_next(L, t) != 0) {
     *        /* uses 'key' (at index -2) and 'value' (at index -1) *&#47;
     *        printf("%s - %s\n",
     *               lua_typename(L, lua_type(L, -2)),
     *               lua_typename(L, lua_type(L, -1)));
     *        /* removes 'value'; keeps 'key' for next iteration *&#47;
     *        lua_pop(L, 1);
     *      }
     * </pre>
     *
     * <p>
     * While traversing a table,
     * avoid calling <a href="https://www.lua.org/manual/5.4/manual.html#lua_tolstring"><code>lua_tolstring</code></a> directly on a key,
     * unless you know that the key is actually a string.
     * Recall that <a href="https://www.lua.org/manual/5.4/manual.html#lua_tolstring"><code>lua_tolstring</code></a> may change
     * the value at the given index;
     * this confuses the next call to <a href="https://www.lua.org/manual/5.4/manual.html#lua_next"><code>lua_next</code></a>.
     * </p>
     *
     * <p>
     * This function may raise an error if the given key
     * is neither <b>nil</b> nor present in the table.
     * See function <a href="https://www.lua.org/manual/5.4/manual.html#pdf-next"><code>next</code></a> for the caveats of modifying
     * the table during its traversal.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native int lua_next(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_next((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_pcall"><code>lua_pcall</code></a>
     *
     * <pre><code>
     * [-(nargs + 1), +(nresults|1), –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_pcall (lua_State *L, int nargs, int nresults, int msgh);
     * </code></pre>
     *
     * <p>
     * Calls a function (or a callable object) in protected mode.
     * </p>
     *
     * <p>
     * Both <code>nargs</code> and <code>nresults</code> have the same meaning as
     * in <a href="https://www.lua.org/manual/5.4/manual.html#lua_call"><code>lua_call</code></a>.
     * If there are no errors during the call,
     * <a href="https://www.lua.org/manual/5.4/manual.html#lua_pcall"><code>lua_pcall</code></a> behaves exactly like <a href="https://www.lua.org/manual/5.4/manual.html#lua_call"><code>lua_call</code></a>.
     * However, if there is any error,
     * <a href="https://www.lua.org/manual/5.4/manual.html#lua_pcall"><code>lua_pcall</code></a> catches it,
     * pushes a single value on the stack (the error object),
     * and returns an error code.
     * Like <a href="https://www.lua.org/manual/5.4/manual.html#lua_call"><code>lua_call</code></a>,
     * <a href="https://www.lua.org/manual/5.4/manual.html#lua_pcall"><code>lua_pcall</code></a> always removes the function
     * and its arguments from the stack.
     * </p>
     *
     * <p>
     * If <code>msgh</code> is 0,
     * then the error object returned on the stack
     * is exactly the original error object.
     * Otherwise, <code>msgh</code> is the stack index of a
     * <em>message handler</em>.
     * (This index cannot be a pseudo-index.)
     * In case of runtime errors,
     * this handler will be called with the error object
     * and its return value will be the object
     * returned on the stack by <a href="https://www.lua.org/manual/5.4/manual.html#lua_pcall"><code>lua_pcall</code></a>.
     * </p>
     *
     * <p>
     * Typically, the message handler is used to add more debug
     * information to the error object, such as a stack traceback.
     * Such information cannot be gathered after the return of <a href="https://www.lua.org/manual/5.4/manual.html#lua_pcall"><code>lua_pcall</code></a>,
     * since by then the stack has unwound.
     * </p>
     *
     * <p>
     * The <a href="https://www.lua.org/manual/5.4/manual.html#lua_pcall"><code>lua_pcall</code></a> function returns one of the following status codes:
     * <a href="https://www.lua.org/manual/5.4/manual.html#pdf-LUA_OK"><code>LUA_OK</code></a>, <a href="https://www.lua.org/manual/5.4/manual.html#pdf-LUA_ERRRUN"><code>LUA_ERRRUN</code></a>, <a href="https://www.lua.org/manual/5.4/manual.html#pdf-LUA_ERRMEM"><code>LUA_ERRMEM</code></a>, or <a href="https://www.lua.org/manual/5.4/manual.html#pdf-LUA_ERRERR"><code>LUA_ERRERR</code></a>.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param nargs the number of arguments that you pushed onto the stack
     * @param nresults the number of results, or <code>LUA_MULTRET</code>
     * @param msgh stack position of message handler
     * @return see description
     */
    public native int lua_pcall(long ptr, int nargs, int nresults, int msgh); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_pcall((lua_State *) L, (int) nargs, (int) nresults, (int) msgh);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_pop"><code>lua_pop</code></a>
     *
     * <pre><code>
     * [-n, +0, e]
     * </code></pre>
     *
     * <pre><code>
     * void lua_pop (lua_State *L, int n);
     * </code></pre>
     *
     * <p>
     * Pops <code>n</code> elements from the stack.
     * It is implemented as a macro over <a href="https://www.lua.org/manual/5.4/manual.html#lua_settop"><code>lua_settop</code></a>.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param n the number of elements
     */
    public native void lua_pop(long ptr, int n); /*
        lua_State * L = (lua_State *) ptr;

        lua_pop((lua_State *) L, (int) n);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_pushboolean"><code>lua_pushboolean</code></a>
     *
     * <pre><code>
     * [-0, +1, –]
     * </code></pre>
     *
     * <pre><code>
     * void lua_pushboolean (lua_State *L, int b);
     * </code></pre>
     *
     * <p>
     * Pushes a boolean value with value <code>b</code> onto the stack.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param b boolean
     */
    public native void lua_pushboolean(long ptr, int b); /*
        lua_State * L = (lua_State *) ptr;

        lua_pushboolean((lua_State *) L, (int) b);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_pushglobaltable"><code>lua_pushglobaltable</code></a>
     *
     * <pre><code>
     * [-0, +1, –]
     * </code></pre>
     *
     * <pre><code>
     * void lua_pushglobaltable (lua_State *L);
     * </code></pre>
     *
     * <p>
     * Pushes the global environment onto the stack.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     */
    public native void lua_pushglobaltable(long ptr); /*
        lua_State * L = (lua_State *) ptr;

        lua_pushglobaltable((lua_State *) L);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_pushinteger"><code>lua_pushinteger</code></a>
     *
     * <pre><code>
     * [-0, +1, –]
     * </code></pre>
     *
     * <pre><code>
     * void lua_pushinteger (lua_State *L, lua_Integer n);
     * </code></pre>
     *
     * <p>
     * Pushes an integer with value <code>n</code> onto the stack.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param n the number / the number of elements
     */
    public native void lua_pushinteger(long ptr, long n); /*
        lua_State * L = (lua_State *) ptr;
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
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_pushlightuserdata"><code>lua_pushlightuserdata</code></a>
     *
     * <pre><code>
     * [-0, +1, –]
     * </code></pre>
     *
     * <pre><code>
     * void lua_pushlightuserdata (lua_State *L, void *p);
     * </code></pre>
     *
     * <p>
     * Pushes a light userdata onto the stack.
     * </p>
     *
     * <p>
     * Userdata represent C&#160;values in Lua.
     * A <em>light userdata</em> represents a pointer, a <code>void*</code>.
     * It is a value (like a number):
     * you do not create it, it has no individual metatable,
     * and it is not collected (as it was never created).
     * A light userdata is equal to "any"
     * light userdata with the same C&#160;address.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param p the pointer
     */
    public native void lua_pushlightuserdata(long ptr, long p); /*
        lua_State * L = (lua_State *) ptr;

        lua_pushlightuserdata((lua_State *) L, (void *) p);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_pushnil"><code>lua_pushnil</code></a>
     *
     * <pre><code>
     * [-0, +1, –]
     * </code></pre>
     *
     * <pre><code>
     * void lua_pushnil (lua_State *L);
     * </code></pre>
     *
     * <p>
     * Pushes a nil value onto the stack.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     */
    public native void lua_pushnil(long ptr); /*
        lua_State * L = (lua_State *) ptr;

        lua_pushnil((lua_State *) L);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_pushnumber"><code>lua_pushnumber</code></a>
     *
     * <pre><code>
     * [-0, +1, –]
     * </code></pre>
     *
     * <pre><code>
     * void lua_pushnumber (lua_State *L, lua_Number n);
     * </code></pre>
     *
     * <p>
     * Pushes a float with value <code>n</code> onto the stack.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param n the number / the number of elements
     */
    public native void lua_pushnumber(long ptr, double n); /*
        lua_State * L = (lua_State *) ptr;

        lua_pushnumber((lua_State *) L, (lua_Number) n);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_pushstring"><code>lua_pushstring</code></a>
     *
     * <pre><code>
     * [-0, +1, m]
     * </code></pre>
     *
     * <pre><code>
     * const char *lua_pushstring (lua_State *L, const char *s);
     * </code></pre>
     *
     * <p>
     * Pushes the zero-terminated string pointed to by <code>s</code>
     * onto the stack.
     * Lua will make or reuse an internal copy of the given string,
     * so the memory at <code>s</code> can be freed or reused immediately after
     * the function returns.
     * </p>
     *
     * <p>
     * Returns a pointer to the internal copy of the string (see <a href="https://www.lua.org/manual/5.4/manual.html#4.1.3">&#167;4.1.3</a>).
     * </p>
     *
     * <p>
     * If <code>s</code> is <code>NULL</code>, pushes <b>nil</b> and returns <code>NULL</code>.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param s the string
     * @return see description
     */
    public native String lua_pushstring(long ptr, String s); /*
        lua_State * L = (lua_State *) ptr;

        const char * returnValueReceiver = (const char *) lua_pushstring((lua_State *) L, (const char *) s);
        return env->NewStringUTF(returnValueReceiver);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_pushstring"><code>lua_pushstring</code></a>
     *
     * <pre><code>
     * [-0, +1, m]
     * </code></pre>
     *
     * <pre><code>
     * const char *lua_pushstring (lua_State *L, const char *s);
     * </code></pre>
     *
     * <p>
     * Pushes the zero-terminated string pointed to by <code>s</code>
     * onto the stack.
     * Lua will make or reuse an internal copy of the given string,
     * so the memory at <code>s</code> can be freed or reused immediately after
     * the function returns.
     * </p>
     *
     * <p>
     * Returns a pointer to the internal copy of the string (see <a href="https://www.lua.org/manual/5.4/manual.html#4.1.3">&#167;4.1.3</a>).
     * </p>
     *
     * <p>
     * If <code>s</code> is <code>NULL</code>, pushes <b>nil</b> and returns <code>NULL</code>.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param s the string
     */
    public native void luaJ_pushstring(long ptr, String s); /*
        lua_State * L = (lua_State *) ptr;

        lua_pushstring((lua_State *) L, (const char *) s);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_pushthread"><code>lua_pushthread</code></a>
     *
     * <pre><code>
     * [-0, +1, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_pushthread (lua_State *L);
     * </code></pre>
     *
     * <p>
     * Pushes the thread represented by <code>L</code> onto the stack.
     * Returns 1 if this thread is the main thread of its state.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @return see description
     */
    public native int lua_pushthread(long ptr); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_pushthread((lua_State *) L);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_pushvalue"><code>lua_pushvalue</code></a>
     *
     * <pre><code>
     * [-0, +1, –]
     * </code></pre>
     *
     * <pre><code>
     * void lua_pushvalue (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Pushes a copy of the element at the given index
     * onto the stack.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     */
    public native void lua_pushvalue(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        lua_pushvalue((lua_State *) L, (int) index);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_rawequal"><code>lua_rawequal</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_rawequal (lua_State *L, int index1, int index2);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the two values in indices <code>index1</code> and
     * <code>index2</code> are primitively equal
     * (that is, equal without calling the <code>__eq</code> metamethod).
     * Otherwise returns&#160;0.
     * Also returns&#160;0 if any of the indices are not valid.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index1 the stack position of the first element
     * @param index2 the stack position of the second element
     * @return see description
     */
    public native int lua_rawequal(long ptr, int index1, int index2); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_rawequal((lua_State *) L, (int) index1, (int) index2);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_rawget"><code>lua_rawget</code></a>
     *
     * <pre><code>
     * [-1, +1, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_rawget (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Similar to <a href="https://www.lua.org/manual/5.4/manual.html#lua_gettable"><code>lua_gettable</code></a>, but does a raw access
     * (i.e., without metamethods).
     * The value at <code>index</code> must be a table.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native int lua_rawget(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_rawget((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_rawget"><code>lua_rawget</code></a>
     *
     * <pre><code>
     * [-1, +1, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_rawget (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Similar to <a href="https://www.lua.org/manual/5.4/manual.html#lua_gettable"><code>lua_gettable</code></a>, but does a raw access
     * (i.e., without metamethods).
     * The value at <code>index</code> must be a table.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     */
    public native void luaJ_rawget(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        lua_rawget((lua_State *) L, (int) index);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_rawgeti"><code>lua_rawgeti</code></a>
     *
     * <pre><code>
     * [-0, +1, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_rawgeti (lua_State *L, int index, lua_Integer n);
     * </code></pre>
     *
     * <p>
     * Pushes onto the stack the value <code>t[n]</code>,
     * where <code>t</code> is the table at the given index.
     * The access is raw,
     * that is, it does not use the <code>__index</code> metavalue.
     * </p>
     *
     * <p>
     * Returns the type of the pushed value.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @param n the number / the number of elements
     * @return see description
     */
    public native int lua_rawgeti(long ptr, int index, long n); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_rawgeti((lua_State *) L, (int) index, (lua_Integer) n);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_rawgeti"><code>lua_rawgeti</code></a>
     *
     * <pre><code>
     * [-0, +1, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_rawgeti (lua_State *L, int index, lua_Integer n);
     * </code></pre>
     *
     * <p>
     * Pushes onto the stack the value <code>t[n]</code>,
     * where <code>t</code> is the table at the given index.
     * The access is raw,
     * that is, it does not use the <code>__index</code> metavalue.
     * </p>
     *
     * <p>
     * Returns the type of the pushed value.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @param n the number of elements
     */
    public native void luaJ_rawgeti(long ptr, int index, int n); /*
        lua_State * L = (lua_State *) ptr;

        lua_rawgeti((lua_State *) L, (int) index, (lua_Integer) n);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_rawgetp"><code>lua_rawgetp</code></a>
     *
     * <pre><code>
     * [-0, +1, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_rawgetp (lua_State *L, int index, const void *p);
     * </code></pre>
     *
     * <p>
     * Pushes onto the stack the value <code>t[k]</code>,
     * where <code>t</code> is the table at the given index and
     * <code>k</code> is the pointer <code>p</code> represented as a light userdata.
     * The access is raw;
     * that is, it does not use the <code>__index</code> metavalue.
     * </p>
     *
     * <p>
     * Returns the type of the pushed value.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @param p the lightuserdata
     * @return see description
     */
    public native int lua_rawgetp(long ptr, int index, long p); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_rawgetp((lua_State *) L, (int) index, (const void *) p);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_rawlen"><code>lua_rawlen</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * lua_Unsigned lua_rawlen (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns the raw "length" of the value at the given index:
     * for strings, this is the string length;
     * for tables, this is the result of the length operator ('<code>#</code>')
     * with no metamethods;
     * for userdata, this is the size of the block of memory allocated
     * for the userdata.
     * For other values, this call returns&#160;0.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native long lua_rawlen(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jlong returnValueReceiver = (jlong) lua_rawlen((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_rawset"><code>lua_rawset</code></a>
     *
     * <pre><code>
     * [-2, +0, m]
     * </code></pre>
     *
     * <pre><code>
     * void lua_rawset (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Similar to <a href="https://www.lua.org/manual/5.4/manual.html#lua_settable"><code>lua_settable</code></a>, but does a raw assignment
     * (i.e., without metamethods).
     * The value at <code>index</code> must be a table.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     */
    public native void lua_rawset(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        lua_rawset((lua_State *) L, (int) index);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_rawseti"><code>lua_rawseti</code></a>
     *
     * <pre><code>
     * [-1, +0, m]
     * </code></pre>
     *
     * <pre><code>
     * void lua_rawseti (lua_State *L, int index, lua_Integer i);
     * </code></pre>
     *
     * <p>
     * Does the equivalent of <code>t[i] = v</code>,
     * where <code>t</code> is the table at the given index
     * and <code>v</code> is the value on the top of the stack.
     * </p>
     *
     * <p>
     * This function pops the value from the stack.
     * The assignment is raw,
     * that is, it does not use the <code>__newindex</code> metavalue.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @param i i
     */
    public native void lua_rawseti(long ptr, int index, int i); /*
        lua_State * L = (lua_State *) ptr;

        lua_rawseti((lua_State *) L, (int) index, (lua_Integer) i);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_rawsetp"><code>lua_rawsetp</code></a>
     *
     * <pre><code>
     * [-1, +0, m]
     * </code></pre>
     *
     * <pre><code>
     * void lua_rawsetp (lua_State *L, int index, const void *p);
     * </code></pre>
     *
     * <p>
     * Does the equivalent of <code>t[p] = v</code>,
     * where <code>t</code> is the table at the given index,
     * <code>p</code> is encoded as a light userdata,
     * and <code>v</code> is the value on the top of the stack.
     * </p>
     *
     * <p>
     * This function pops the value from the stack.
     * The assignment is raw,
     * that is, it does not use the <code>__newindex</code> metavalue.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @param p the lightuserdata
     */
    public native void lua_rawsetp(long ptr, int index, long p); /*
        lua_State * L = (lua_State *) ptr;

        lua_rawsetp((lua_State *) L, (int) index, (const void *) p);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_remove"><code>lua_remove</code></a>
     *
     * <pre><code>
     * [-1, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * void lua_remove (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Removes the element at the given valid index,
     * shifting down the elements above this index to fill the gap.
     * This function cannot be called with a pseudo-index,
     * because a pseudo-index is not an actual stack position.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     */
    public native void lua_remove(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        lua_remove((lua_State *) L, (int) index);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_replace"><code>lua_replace</code></a>
     *
     * <pre><code>
     * [-1, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * void lua_replace (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Moves the top element into the given valid index
     * without shifting any element
     * (therefore replacing the value at that given index),
     * and then pops the top element.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     */
    public native void lua_replace(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        lua_replace((lua_State *) L, (int) index);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_resetthread"><code>lua_resetthread</code></a>
     *
     * <pre><code>
     * [-0, +?, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_resetthread (lua_State *L);
     * </code></pre>
     *
     * <p>
     * This function is deprecated;
     * it is equivalent to <a href="https://www.lua.org/manual/5.4/manual.html#lua_closethread"><code>lua_closethread</code></a> with
     * <code>from</code> being <code>NULL</code>.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @return see description
     */
    public native int lua_resetthread(long ptr); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_resetthread((lua_State *) L);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_resume"><code>lua_resume</code></a>
     *
     * <pre><code>
     * [-?, +?, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_resume (lua_State *L, lua_State *from, int nargs,
     *                           int *nresults);
     * </code></pre>
     *
     * <p>
     * Starts and resumes a coroutine in the given thread <code>L</code>.
     * </p>
     *
     * <p>
     * To start a coroutine,
     * you push the main function plus any arguments
     * onto the empty stack of the thread.
     * then you call <a href="https://www.lua.org/manual/5.4/manual.html#lua_resume"><code>lua_resume</code></a>,
     * with <code>nargs</code> being the number of arguments.
     * This call returns when the coroutine suspends or finishes its execution.
     * When it returns,
     * <code>*nresults</code> is updated and
     * the top of the stack contains
     * the <code>*nresults</code> values passed to <a href="https://www.lua.org/manual/5.4/manual.html#lua_yield"><code>lua_yield</code></a>
     * or returned by the body function.
     * <a href="https://www.lua.org/manual/5.4/manual.html#lua_resume"><code>lua_resume</code></a> returns
     * <a href="https://www.lua.org/manual/5.4/manual.html#pdf-LUA_YIELD"><code>LUA_YIELD</code></a> if the coroutine yields,
     * <a href="https://www.lua.org/manual/5.4/manual.html#pdf-LUA_OK"><code>LUA_OK</code></a> if the coroutine finishes its execution
     * without errors,
     * or an error code in case of errors (see <a href="https://www.lua.org/manual/5.4/manual.html#4.4.1">&#167;4.4.1</a>).
     * In case of errors,
     * the error object is on the top of the stack.
     * </p>
     *
     * <p>
     * To resume a coroutine,
     * you remove the <code>*nresults</code> yielded values from its stack,
     * push the values to be passed as results from <code>yield</code>,
     * and then call <a href="https://www.lua.org/manual/5.4/manual.html#lua_resume"><code>lua_resume</code></a>.
     * </p>
     *
     * <p>
     * The parameter <code>from</code> represents the coroutine that is resuming <code>L</code>.
     * If there is no such coroutine,
     * this parameter can be <code>NULL</code>.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param from a thread
     * @param nargs the number of arguments that you pushed onto the stack
     * @param nresults pointer to the number of results
     * @return see description
     */
    public native int lua_resume(long ptr, long from, int nargs, long nresults); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_resume((lua_State *) L, (lua_State *) from, (int) nargs, (int *) nresults);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_rotate"><code>lua_rotate</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * void lua_rotate (lua_State *L, int idx, int n);
     * </code></pre>
     *
     * <p>
     * Rotates the stack elements between the valid index <code>idx</code>
     * and the top of the stack.
     * The elements are rotated <code>n</code> positions in the direction of the top,
     * for a positive <code>n</code>,
     * or <code>-n</code> positions in the direction of the bottom,
     * for a negative <code>n</code>.
     * The absolute value of <code>n</code> must not be greater than the size
     * of the slice being rotated.
     * This function cannot be called with a pseudo-index,
     * because a pseudo-index is not an actual stack position.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param idx the stack position
     * @param n the number of elements
     */
    public native void lua_rotate(long ptr, int idx, int n); /*
        lua_State * L = (lua_State *) ptr;

        lua_rotate((lua_State *) L, (int) idx, (int) n);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_setfield"><code>lua_setfield</code></a>
     *
     * <pre><code>
     * [-1, +0, e]
     * </code></pre>
     *
     * <pre><code>
     * void lua_setfield (lua_State *L, int index, const char *k);
     * </code></pre>
     *
     * <p>
     * Does the equivalent to <code>t[k] = v</code>,
     * where <code>t</code> is the value at the given index
     * and <code>v</code> is the value on the top of the stack.
     * </p>
     *
     * <p>
     * This function pops the value from the stack.
     * As in Lua, this function may trigger a metamethod
     * for the "newindex" event (see <a href="https://www.lua.org/manual/5.4/manual.html#2.4">&#167;2.4</a>).
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @param k the field name
     */
    public native void lua_setfield(long ptr, int index, String k); /*
        lua_State * L = (lua_State *) ptr;

        lua_setfield((lua_State *) L, (int) index, (const char *) k);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_setglobal"><code>lua_setglobal</code></a>
     *
     * <pre><code>
     * [-1, +0, e]
     * </code></pre>
     *
     * <pre><code>
     * void lua_setglobal (lua_State *L, const char *name);
     * </code></pre>
     *
     * <p>
     * Pops a value from the stack and
     * sets it as the new value of global <code>name</code>.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param name the name
     */
    public native void lua_setglobal(long ptr, String name); /*
        lua_State * L = (lua_State *) ptr;

        lua_setglobal((lua_State *) L, (const char *) name);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_seti"><code>lua_seti</code></a>
     *
     * <pre><code>
     * [-1, +0, e]
     * </code></pre>
     *
     * <pre><code>
     * void lua_seti (lua_State *L, int index, lua_Integer n);
     * </code></pre>
     *
     * <p>
     * Does the equivalent to <code>t[n] = v</code>,
     * where <code>t</code> is the value at the given index
     * and <code>v</code> is the value on the top of the stack.
     * </p>
     *
     * <p>
     * This function pops the value from the stack.
     * As in Lua, this function may trigger a metamethod
     * for the "newindex" event (see <a href="https://www.lua.org/manual/5.4/manual.html#2.4">&#167;2.4</a>).
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @param n the number / the number of elements
     */
    public native void lua_seti(long ptr, int index, long n); /*
        lua_State * L = (lua_State *) ptr;

        lua_seti((lua_State *) L, (int) index, (lua_Integer) n);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_setiuservalue"><code>lua_setiuservalue</code></a>
     *
     * <pre><code>
     * [-1, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_setiuservalue (lua_State *L, int index, int n);
     * </code></pre>
     *
     * <p>
     * Pops a value from the stack and sets it as
     * the new <code>n</code>-th user value associated to the
     * full userdata at the given index.
     * Returns 0 if the userdata does not have that value.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @param n the number of elements
     * @return see description
     */
    public native int lua_setiuservalue(long ptr, int index, int n); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_setiuservalue((lua_State *) L, (int) index, (int) n);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_setmetatable"><code>lua_setmetatable</code></a>
     *
     * <pre><code>
     * [-1, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_setmetatable (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Pops a table or <b>nil</b> from the stack and
     * sets that value as the new metatable for the value at the given index.
     * (<b>nil</b> means no metatable.)
     * </p>
     *
     * <p>
     * (For historical reasons, this function returns an <code>int</code>,
     * which now is always 1.)
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native int lua_setmetatable(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_setmetatable((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_setmetatable"><code>lua_setmetatable</code></a>
     *
     * <pre><code>
     * [-1, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_setmetatable (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Pops a table or <b>nil</b> from the stack and
     * sets that value as the new metatable for the value at the given index.
     * (<b>nil</b> means no metatable.)
     * </p>
     *
     * <p>
     * (For historical reasons, this function returns an <code>int</code>,
     * which now is always 1.)
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     */
    public native void luaJ_setmetatable(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        lua_setmetatable((lua_State *) L, (int) index);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_settable"><code>lua_settable</code></a>
     *
     * <pre><code>
     * [-2, +0, e]
     * </code></pre>
     *
     * <pre><code>
     * void lua_settable (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Does the equivalent to <code>t[k] = v</code>,
     * where <code>t</code> is the value at the given index,
     * <code>v</code> is the value on the top of the stack,
     * and <code>k</code> is the value just below the top.
     * </p>
     *
     * <p>
     * This function pops both the key and the value from the stack.
     * As in Lua, this function may trigger a metamethod
     * for the "newindex" event (see <a href="https://www.lua.org/manual/5.4/manual.html#2.4">&#167;2.4</a>).
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     */
    public native void lua_settable(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        lua_settable((lua_State *) L, (int) index);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_settop"><code>lua_settop</code></a>
     *
     * <pre><code>
     * [-?, +?, e]
     * </code></pre>
     *
     * <pre><code>
     * void lua_settop (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Accepts any index, or&#160;0,
     * and sets the stack top to this index.
     * If the new top is greater than the old one,
     * then the new elements are filled with <b>nil</b>.
     * If <code>index</code> is&#160;0, then all stack elements are removed.
     * </p>
     *
     * <p>
     * This function can run arbitrary code when removing an index
     * marked as to-be-closed from the stack.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     */
    public native void lua_settop(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        lua_settop((lua_State *) L, (int) index);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_status"><code>lua_status</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_status (lua_State *L);
     * </code></pre>
     *
     * <p>
     * Returns the status of the thread <code>L</code>.
     * </p>
     *
     * <p>
     * The status can be <a href="https://www.lua.org/manual/5.4/manual.html#pdf-LUA_OK"><code>LUA_OK</code></a> for a normal thread,
     * an error code if the thread finished the execution
     * of a <a href="https://www.lua.org/manual/5.4/manual.html#lua_resume"><code>lua_resume</code></a> with an error,
     * or <a href="https://www.lua.org/manual/5.4/manual.html#pdf-LUA_YIELD"><code>LUA_YIELD</code></a> if the thread is suspended.
     * </p>
     *
     * <p>
     * You can call functions only in threads with status <a href="https://www.lua.org/manual/5.4/manual.html#pdf-LUA_OK"><code>LUA_OK</code></a>.
     * You can resume threads with status <a href="https://www.lua.org/manual/5.4/manual.html#pdf-LUA_OK"><code>LUA_OK</code></a>
     * (to start a new coroutine) or <a href="https://www.lua.org/manual/5.4/manual.html#pdf-LUA_YIELD"><code>LUA_YIELD</code></a>
     * (to resume a coroutine).
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @return see description
     */
    public native int lua_status(long ptr); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_status((lua_State *) L);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_stringtonumber"><code>lua_stringtonumber</code></a>
     *
     * <pre><code>
     * [-0, +1, –]
     * </code></pre>
     *
     * <pre><code>
     * size_t lua_stringtonumber (lua_State *L, const char *s);
     * </code></pre>
     *
     * <p>
     * Converts the zero-terminated string <code>s</code> to a number,
     * pushes that number into the stack,
     * and returns the total size of the string,
     * that is, its length plus one.
     * The conversion can result in an integer or a float,
     * according to the lexical conventions of Lua (see <a href="https://www.lua.org/manual/5.4/manual.html#3.1">&#167;3.1</a>).
     * The string may have leading and trailing whitespaces and a sign.
     * If the string is not a valid numeral,
     * returns 0 and pushes nothing.
     * (Note that the result can be used as a boolean,
     * true if the conversion succeeds.)
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param s the string
     * @return see description
     */
    public native long lua_stringtonumber(long ptr, String s); /*
        lua_State * L = (lua_State *) ptr;

        jlong returnValueReceiver = (jlong) lua_stringtonumber((lua_State *) L, (const char *) s);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_toboolean"><code>lua_toboolean</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_toboolean (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Converts the Lua value at the given index to a C&#160;boolean
     * value (0&#160;or&#160;1).
     * Like all tests in Lua,
     * <a href="https://www.lua.org/manual/5.4/manual.html#lua_toboolean"><code>lua_toboolean</code></a> returns true for any Lua value
     * different from <b>false</b> and <b>nil</b>;
     * otherwise it returns false.
     * (If you want to accept only actual boolean values,
     * use <a href="https://www.lua.org/manual/5.4/manual.html#lua_isboolean"><code>lua_isboolean</code></a> to test the value's type.)
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native int lua_toboolean(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_toboolean((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_toclose"><code>lua_toclose</code></a>
     *
     * <pre><code>
     * [-0, +0, v]
     * </code></pre>
     *
     * <pre><code>
     * void lua_toclose (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Marks the given index in the stack as a
     * to-be-closed slot (see <a href="https://www.lua.org/manual/5.4/manual.html#3.3.8">&#167;3.3.8</a>).
     * Like a to-be-closed variable in Lua,
     * the value at that slot in the stack will be closed
     * when it goes out of scope.
     * Here, in the context of a C function,
     * to go out of scope means that the running function returns to Lua,
     * or there is an error,
     * or the slot is removed from the stack through
     * <a href="https://www.lua.org/manual/5.4/manual.html#lua_settop"><code>lua_settop</code></a> or <a href="https://www.lua.org/manual/5.4/manual.html#lua_pop"><code>lua_pop</code></a>,
     * or there is a call to <a href="https://www.lua.org/manual/5.4/manual.html#lua_closeslot"><code>lua_closeslot</code></a>.
     * A slot marked as to-be-closed should not be removed from the stack
     * by any other function in the API except <a href="https://www.lua.org/manual/5.4/manual.html#lua_settop"><code>lua_settop</code></a> or <a href="https://www.lua.org/manual/5.4/manual.html#lua_pop"><code>lua_pop</code></a>,
     * unless previously deactivated by <a href="https://www.lua.org/manual/5.4/manual.html#lua_closeslot"><code>lua_closeslot</code></a>.
     * </p>
     *
     * <p>
     * This function raises an error if the value at the given slot
     * neither has a <code>__close</code> metamethod nor is a false value.
     * </p>
     *
     * <p>
     * This function should not be called for an index
     * that is equal to or below an active to-be-closed slot.
     * </p>
     *
     * <p>
     * Note that, both in case of errors and of a regular return,
     * by the time the <code>__close</code> metamethod runs,
     * the C&#160;stack was already unwound,
     * so that any automatic C&#160;variable declared in the calling function
     * (e.g., a buffer) will be out of scope.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     */
    public native void lua_toclose(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        lua_toclose((lua_State *) L, (int) index);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_tointeger"><code>lua_tointeger</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * lua_Integer lua_tointeger (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Equivalent to <a href="https://www.lua.org/manual/5.4/manual.html#lua_tointegerx"><code>lua_tointegerx</code></a> with <code>isnum</code> equal to <code>NULL</code>.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native long lua_tointeger(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;
        // See lua_pushinteger for comments.
        if (sizeof(lua_Integer) == 4) {
          return (jlong) lua_tonumber(L, index);
        } else {
          return (jlong) lua_tointeger(L, index);
        }
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_tointegerx"><code>lua_tointegerx</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * lua_Integer lua_tointegerx (lua_State *L, int index, int *isnum);
     * </code></pre>
     *
     * <p>
     * Converts the Lua value at the given index
     * to the signed integral type <a href="https://www.lua.org/manual/5.4/manual.html#lua_Integer"><code>lua_Integer</code></a>.
     * The Lua value must be an integer,
     * or a number or string convertible to an integer (see <a href="https://www.lua.org/manual/5.4/manual.html#3.4.3">&#167;3.4.3</a>);
     * otherwise, <code>lua_tointegerx</code> returns&#160;0.
     * </p>
     *
     * <p>
     * If <code>isnum</code> is not <code>NULL</code>,
     * its referent is assigned a boolean value that
     * indicates whether the operation succeeded.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @param isnum pointer to a boolean to be assigned
     * @return see description
     */
    public native long lua_tointegerx(long ptr, int index, long isnum); /*
        lua_State * L = (lua_State *) ptr;

        jlong returnValueReceiver = (jlong) lua_tointegerx((lua_State *) L, (int) index, (int *) isnum);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_tonumber"><code>lua_tonumber</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * lua_Number lua_tonumber (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Equivalent to <a href="https://www.lua.org/manual/5.4/manual.html#lua_tonumberx"><code>lua_tonumberx</code></a> with <code>isnum</code> equal to <code>NULL</code>.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native double lua_tonumber(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jdouble returnValueReceiver = (jdouble) lua_tonumber((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_tonumberx"><code>lua_tonumberx</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * lua_Number lua_tonumberx (lua_State *L, int index, int *isnum);
     * </code></pre>
     *
     * <p>
     * Converts the Lua value at the given index
     * to the C&#160;type <a href="https://www.lua.org/manual/5.4/manual.html#lua_Number"><code>lua_Number</code></a> (see <a href="https://www.lua.org/manual/5.4/manual.html#lua_Number"><code>lua_Number</code></a>).
     * The Lua value must be a number or a string convertible to a number
     * (see <a href="https://www.lua.org/manual/5.4/manual.html#3.4.3">&#167;3.4.3</a>);
     * otherwise, <a href="https://www.lua.org/manual/5.4/manual.html#lua_tonumberx"><code>lua_tonumberx</code></a> returns&#160;0.
     * </p>
     *
     * <p>
     * If <code>isnum</code> is not <code>NULL</code>,
     * its referent is assigned a boolean value that
     * indicates whether the operation succeeded.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @param isnum pointer to a boolean to be assigned
     * @return see description
     */
    public native double lua_tonumberx(long ptr, int index, long isnum); /*
        lua_State * L = (lua_State *) ptr;

        jdouble returnValueReceiver = (jdouble) lua_tonumberx((lua_State *) L, (int) index, (int *) isnum);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_topointer"><code>lua_topointer</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * const void *lua_topointer (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Converts the value at the given index to a generic
     * C&#160;pointer (<code>void*</code>).
     * The value can be a userdata, a table, a thread, a string, or a function;
     * otherwise, <code>lua_topointer</code> returns <code>NULL</code>.
     * Different objects will give different pointers.
     * There is no way to convert the pointer back to its original value.
     * </p>
     *
     * <p>
     * Typically this function is used only for hashing and debug information.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native long lua_topointer(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jlong returnValueReceiver = (jlong) lua_topointer((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_tostring"><code>lua_tostring</code></a>
     *
     * <pre><code>
     * [-0, +0, m]
     * </code></pre>
     *
     * <pre><code>
     * const char *lua_tostring (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Equivalent to <a href="https://www.lua.org/manual/5.4/manual.html#lua_tolstring"><code>lua_tolstring</code></a> with <code>len</code> equal to <code>NULL</code>.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native String lua_tostring(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        const char * returnValueReceiver = (const char *) lua_tostring((lua_State *) L, (int) index);
        return env->NewStringUTF(returnValueReceiver);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_tothread"><code>lua_tothread</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * lua_State *lua_tothread (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Converts the value at the given index to a Lua thread
     * (represented as <code>lua_State*</code>).
     * This value must be a thread;
     * otherwise, the function returns <code>NULL</code>.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native long lua_tothread(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jlong returnValueReceiver = (jlong) lua_tothread((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_touserdata"><code>lua_touserdata</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * void *lua_touserdata (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * If the value at the given index is a full userdata,
     * returns its memory-block address.
     * If the value is a light userdata,
     * returns its value (a pointer).
     * Otherwise, returns <code>NULL</code>.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native long lua_touserdata(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jlong returnValueReceiver = (jlong) lua_touserdata((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_type"><code>lua_type</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_type (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns the type of the value in the given valid index,
     * or <code>LUA_TNONE</code> for a non-valid but acceptable index.
     * The types returned by <a href="https://www.lua.org/manual/5.4/manual.html#lua_type"><code>lua_type</code></a> are coded by the following constants
     * defined in <code>lua.h</code>:
     * <a><code>LUA_TNIL</code></a>,
     * <a><code>LUA_TNUMBER</code></a>,
     * <a><code>LUA_TBOOLEAN</code></a>,
     * <a><code>LUA_TSTRING</code></a>,
     * <a><code>LUA_TTABLE</code></a>,
     * <a><code>LUA_TFUNCTION</code></a>,
     * <a><code>LUA_TUSERDATA</code></a>,
     * <a><code>LUA_TTHREAD</code></a>,
     * and
     * <a><code>LUA_TLIGHTUSERDATA</code></a>.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native int lua_type(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_type((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_typename"><code>lua_typename</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * const char *lua_typename (lua_State *L, int tp);
     * </code></pre>
     *
     * <p>
     * Returns the name of the type encoded by the value <code>tp</code>,
     * which must be one the values returned by <a href="https://www.lua.org/manual/5.4/manual.html#lua_type"><code>lua_type</code></a>.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param tp type id
     * @return see description
     */
    public native String lua_typename(long ptr, int tp); /*
        lua_State * L = (lua_State *) ptr;

        const char * returnValueReceiver = (const char *) lua_typename((lua_State *) L, (int) tp);
        return env->NewStringUTF(returnValueReceiver);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_upvalueindex"><code>lua_upvalueindex</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_upvalueindex (int i);
     * </code></pre>
     *
     * <p>
     * Returns the pseudo-index that represents the <code>i</code>-th upvalue of
     * the running function (see <a href="https://www.lua.org/manual/5.4/manual.html#4.2">&#167;4.2</a>).
     * <code>i</code> must be in the range <em>[1,256]</em>.
     * </p>
     *
     * @param i i
     * @return see description
     */
    public native int lua_upvalueindex(int i); /*
        jint returnValueReceiver = (jint) lua_upvalueindex((int) i);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_version"><code>lua_version</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * lua_Number lua_version (lua_State *L);
     * </code></pre>
     *
     * <p>
     * Returns the version number of this core.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @return see description
     */
    public native double lua_version(long ptr); /*
        lua_State * L = (lua_State *) ptr;

        jdouble returnValueReceiver = (jdouble) lua_version((lua_State *) L);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_warning"><code>lua_warning</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * void lua_warning (lua_State *L, const char *msg, int tocont);
     * </code></pre>
     *
     * <p>
     * Emits a warning with the given message.
     * A message in a call with <code>tocont</code> true should be
     * continued in another call to this function.
     * </p>
     *
     * <p>
     * See <a href="https://www.lua.org/manual/5.4/manual.html#pdf-warn"><code>warn</code></a> for more details about warnings.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param msg a message
     * @param tocont continue or not
     */
    public native void lua_warning(long ptr, String msg, int tocont); /*
        lua_State * L = (lua_State *) ptr;

        lua_warning((lua_State *) L, (const char *) msg, (int) tocont);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_xmove"><code>lua_xmove</code></a>
     *
     * <pre><code>
     * [-?, +?, –]
     * </code></pre>
     *
     * <pre><code>
     * void lua_xmove (lua_State *from, lua_State *to, int n);
     * </code></pre>
     *
     * <p>
     * Exchange values between different threads of the same state.
     * </p>
     *
     * <p>
     * This function pops <code>n</code> values from the stack <code>from</code>,
     * and pushes them onto the stack <code>to</code>.
     * </p>
     *
     * @param from a thread
     * @param to another thread
     * @param n the number of elements
     */
    public native void lua_xmove(long from, long to, int n); /*
        lua_xmove((lua_State *) from, (lua_State *) to, (int) n);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_yield"><code>lua_yield</code></a>
     *
     * <pre><code>
     * [-?, +?, v]
     * </code></pre>
     *
     * <pre><code>
     * int lua_yield (lua_State *L, int nresults);
     * </code></pre>
     *
     * <p>
     * This function is equivalent to <a href="https://www.lua.org/manual/5.4/manual.html#lua_yieldk"><code>lua_yieldk</code></a>,
     * but it has no continuation (see <a href="https://www.lua.org/manual/5.4/manual.html#4.5">&#167;4.5</a>).
     * Therefore, when the thread resumes,
     * it continues the function that called
     * the function calling <code>lua_yield</code>.
     * To avoid surprises,
     * this function should be called only in a tail call.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param nresults the number of results, or <code>LUA_MULTRET</code>
     * @return see description
     */
    public native int lua_yield(long ptr, int nresults); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_yield((lua_State *) L, (int) nresults);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_gethookcount"><code>lua_gethookcount</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_gethookcount (lua_State *L);
     * </code></pre>
     *
     * <p>
     * Returns the current hook count.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @return see description
     */
    public native int lua_gethookcount(long ptr); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_gethookcount((lua_State *) L);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_gethookmask"><code>lua_gethookmask</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * int lua_gethookmask (lua_State *L);
     * </code></pre>
     *
     * <p>
     * Returns the current hook mask.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @return see description
     */
    public native int lua_gethookmask(long ptr); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_gethookmask((lua_State *) L);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_getupvalue"><code>lua_getupvalue</code></a>
     *
     * <pre><code>
     * [-0, +(0|1), –]
     * </code></pre>
     *
     * <pre><code>
     * const char *lua_getupvalue (lua_State *L, int funcindex, int n);
     * </code></pre>
     *
     * <p>
     * Gets information about the <code>n</code>-th upvalue
     * of the closure at index <code>funcindex</code>.
     * It pushes the upvalue's value onto the stack
     * and returns its name.
     * Returns <code>NULL</code> (and pushes nothing)
     * when the index <code>n</code> is greater than the number of upvalues.
     * </p>
     *
     * <p>
     * See <a href="https://www.lua.org/manual/5.4/manual.html#pdf-debug.getupvalue"><code>debug.getupvalue</code></a> for more information about upvalues.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param funcindex the stack position of the closure
     * @param n the index in the upvalue
     * @return see description
     */
    public native String lua_getupvalue(long ptr, int funcindex, int n); /*
        lua_State * L = (lua_State *) ptr;

        const char * returnValueReceiver = (const char *) lua_getupvalue((lua_State *) L, (int) funcindex, (int) n);
        return env->NewStringUTF(returnValueReceiver);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_setupvalue"><code>lua_setupvalue</code></a>
     *
     * <pre><code>
     * [-(0|1), +0, –]
     * </code></pre>
     *
     * <pre><code>
     * const char *lua_setupvalue (lua_State *L, int funcindex, int n);
     * </code></pre>
     *
     * <p>
     * Sets the value of a closure's upvalue.
     * It assigns the value on the top of the stack
     * to the upvalue and returns its name.
     * It also pops the value from the stack.
     * </p>
     *
     * <p>
     * Returns <code>NULL</code> (and pops nothing)
     * when the index <code>n</code> is greater than the number of upvalues.
     * </p>
     *
     * <p>
     * Parameters <code>funcindex</code> and <code>n</code> are as in
     * the function <a href="https://www.lua.org/manual/5.4/manual.html#lua_getupvalue"><code>lua_getupvalue</code></a>.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param funcindex the stack position of the closure
     * @param n the index in the upvalue
     * @return see description
     */
    public native String lua_setupvalue(long ptr, int funcindex, int n); /*
        lua_State * L = (lua_State *) ptr;

        const char * returnValueReceiver = (const char *) lua_setupvalue((lua_State *) L, (int) funcindex, (int) n);
        return env->NewStringUTF(returnValueReceiver);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_upvalueid"><code>lua_upvalueid</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * void *lua_upvalueid (lua_State *L, int funcindex, int n);
     * </code></pre>
     *
     * <p>
     * Returns a unique identifier for the upvalue numbered <code>n</code>
     * from the closure at index <code>funcindex</code>.
     * </p>
     *
     * <p>
     * These unique identifiers allow a program to check whether different
     * closures share upvalues.
     * Lua closures that share an upvalue
     * (that is, that access a same external local variable)
     * will return identical ids for those upvalue indices.
     * </p>
     *
     * <p>
     * Parameters <code>funcindex</code> and <code>n</code> are as in
     * the function <a href="https://www.lua.org/manual/5.4/manual.html#lua_getupvalue"><code>lua_getupvalue</code></a>,
     * but <code>n</code> cannot be greater than the number of upvalues.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param funcindex the stack position of the closure
     * @param n the index in the upvalue
     * @return see description
     */
    public native long lua_upvalueid(long ptr, int funcindex, int n); /*
        lua_State * L = (lua_State *) ptr;

        jlong returnValueReceiver = (jlong) lua_upvalueid((lua_State *) L, (int) funcindex, (int) n);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#lua_upvaluejoin"><code>lua_upvaluejoin</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * void lua_upvaluejoin (lua_State *L, int funcindex1, int n1,
     *                                     int funcindex2, int n2);
     * </code></pre>
     *
     * <p>
     * Make the <code>n1</code>-th upvalue of the Lua closure at index <code>funcindex1</code>
     * refer to the <code>n2</code>-th upvalue of the Lua closure at index <code>funcindex2</code>.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param funcindex1 the stack position of the closure
     * @param n1 n1
     * @param funcindex2 the stack position of the closure
     * @param n2 n2
     */
    public native void lua_upvaluejoin(long ptr, int funcindex1, int n1, int funcindex2, int n2); /*
        lua_State * L = (lua_State *) ptr;

        lua_upvaluejoin((lua_State *) L, (int) funcindex1, (int) n1, (int) funcindex2, (int) n2);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#luaL_callmeta"><code>luaL_callmeta</code></a>
     *
     * <pre><code>
     * [-0, +(0|1), e]
     * </code></pre>
     *
     * <pre><code>
     * int luaL_callmeta (lua_State *L, int obj, const char *e);
     * </code></pre>
     *
     * <p>
     * Calls a metamethod.
     * </p>
     *
     * <p>
     * If the object at index <code>obj</code> has a metatable and this
     * metatable has a field <code>e</code>,
     * this function calls this field passing the object as its only argument.
     * In this case this function returns true and pushes onto the
     * stack the value returned by the call.
     * If there is no metatable or no metamethod,
     * this function returns false without pushing any value on the stack.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param obj the stack position of the object
     * @param e field name
     * @return see description
     */
    public native int luaL_callmeta(long ptr, int obj, String e); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) luaL_callmeta((lua_State *) L, (int) obj, (const char *) e);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#luaL_dostring"><code>luaL_dostring</code></a>
     *
     * <pre><code>
     * [-0, +?, –]
     * </code></pre>
     *
     * <pre><code>
     * int luaL_dostring (lua_State *L, const char *str);
     * </code></pre>
     *
     * <p>
     * Loads and runs the given string.
     * It is defined as the following macro:
     * </p>
     *
     * <pre>
     *      (luaL_loadstring(L, str) || lua_pcall(L, 0, LUA_MULTRET, 0))
     * </pre>
     *
     * <p>
     * It returns&#160;0 (<a href="https://www.lua.org/manual/5.4/manual.html#pdf-LUA_OK"><code>LUA_OK</code></a>) if there are no errors,
     * or 1 in case of errors.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param str string
     * @return see description
     */
    public native int luaL_dostring(long ptr, String str); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) luaL_dostring((lua_State *) L, (const char *) str);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#luaL_execresult"><code>luaL_execresult</code></a>
     *
     * <pre><code>
     * [-0, +3, m]
     * </code></pre>
     *
     * <pre><code>
     * int luaL_execresult (lua_State *L, int stat);
     * </code></pre>
     *
     * <p>
     * This function produces the return values for
     * process-related functions in the standard library
     * (<a href="https://www.lua.org/manual/5.4/manual.html#pdf-os.execute"><code>os.execute</code></a> and <a href="https://www.lua.org/manual/5.4/manual.html#pdf-io.close"><code>io.close</code></a>).
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param stat (I have no idea)
     * @return see description
     */
    public native int luaL_execresult(long ptr, int stat); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) luaL_execresult((lua_State *) L, (int) stat);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#luaL_fileresult"><code>luaL_fileresult</code></a>
     *
     * <pre><code>
     * [-0, +(1|3), m]
     * </code></pre>
     *
     * <pre><code>
     * int luaL_fileresult (lua_State *L, int stat, const char *fname);
     * </code></pre>
     *
     * <p>
     * This function produces the return values for
     * file-related functions in the standard library
     * (<a href="https://www.lua.org/manual/5.4/manual.html#pdf-io.open"><code>io.open</code></a>, <a href="https://www.lua.org/manual/5.4/manual.html#pdf-os.rename"><code>os.rename</code></a>, <a href="https://www.lua.org/manual/5.4/manual.html#pdf-file:seek"><code>file:seek</code></a>, etc.).
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param stat (I have no idea)
     * @param fname the filename
     * @return see description
     */
    public native int luaL_fileresult(long ptr, int stat, String fname); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) luaL_fileresult((lua_State *) L, (int) stat, (const char *) fname);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#luaL_getmetafield"><code>luaL_getmetafield</code></a>
     *
     * <pre><code>
     * [-0, +(0|1), m]
     * </code></pre>
     *
     * <pre><code>
     * int luaL_getmetafield (lua_State *L, int obj, const char *e);
     * </code></pre>
     *
     * <p>
     * Pushes onto the stack the field <code>e</code> from the metatable
     * of the object at index <code>obj</code> and returns the type of the pushed value.
     * If the object does not have a metatable,
     * or if the metatable does not have this field,
     * pushes nothing and returns <code>LUA_TNIL</code>.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param obj the stack position of the object
     * @param e field name
     * @return see description
     */
    public native int luaL_getmetafield(long ptr, int obj, String e); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) luaL_getmetafield((lua_State *) L, (int) obj, (const char *) e);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#luaL_getmetatable"><code>luaL_getmetatable</code></a>
     *
     * <pre><code>
     * [-0, +1, m]
     * </code></pre>
     *
     * <pre><code>
     * int luaL_getmetatable (lua_State *L, const char *tname);
     * </code></pre>
     *
     * <p>
     * Pushes onto the stack the metatable associated with the name <code>tname</code>
     * in the registry (see <a href="https://www.lua.org/manual/5.4/manual.html#luaL_newmetatable"><code>luaL_newmetatable</code></a>),
     * or <b>nil</b> if there is no metatable associated with that name.
     * Returns the type of the pushed value.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param tname type name
     * @return see description
     */
    public native int luaL_getmetatable(long ptr, String tname); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) luaL_getmetatable((lua_State *) L, (const char *) tname);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#luaL_getmetatable"><code>luaL_getmetatable</code></a>
     *
     * <pre><code>
     * [-0, +1, m]
     * </code></pre>
     *
     * <pre><code>
     * int luaL_getmetatable (lua_State *L, const char *tname);
     * </code></pre>
     *
     * <p>
     * Pushes onto the stack the metatable associated with the name <code>tname</code>
     * in the registry (see <a href="https://www.lua.org/manual/5.4/manual.html#luaL_newmetatable"><code>luaL_newmetatable</code></a>),
     * or <b>nil</b> if there is no metatable associated with that name.
     * Returns the type of the pushed value.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param tname type name
     */
    public native void luaJ_getmetatable(long ptr, String tname); /*
        lua_State * L = (lua_State *) ptr;

        luaL_getmetatable((lua_State *) L, (const char *) tname);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#luaL_getsubtable"><code>luaL_getsubtable</code></a>
     *
     * <pre><code>
     * [-0, +1, e]
     * </code></pre>
     *
     * <pre><code>
     * int luaL_getsubtable (lua_State *L, int idx, const char *fname);
     * </code></pre>
     *
     * <p>
     * Ensures that the value <code>t[fname]</code>,
     * where <code>t</code> is the value at index <code>idx</code>,
     * is a table,
     * and pushes that table onto the stack.
     * Returns true if it finds a previous table there
     * and false if it creates a new table.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param idx the stack position
     * @param fname the filename
     * @return see description
     */
    public native int luaL_getsubtable(long ptr, int idx, String fname); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) luaL_getsubtable((lua_State *) L, (int) idx, (const char *) fname);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#luaL_gsub"><code>luaL_gsub</code></a>
     *
     * <pre><code>
     * [-0, +1, m]
     * </code></pre>
     *
     * <pre><code>
     * const char *luaL_gsub (lua_State *L,
     *                        const char *s,
     *                        const char *p,
     *                        const char *r);
     * </code></pre>
     *
     * <p>
     * Creates a copy of string <code>s</code>,
     * replacing any occurrence of the string <code>p</code>
     * with the string <code>r</code>.
     * Pushes the resulting string on the stack and returns it.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param s the string
     * @param p the replaced sequence
     * @param r the replacing string
     * @return see description
     */
    public native String luaL_gsub(long ptr, String s, String p, String r); /*
        lua_State * L = (lua_State *) ptr;

        const char * returnValueReceiver = (const char *) luaL_gsub((lua_State *) L, (const char *) s, (const char *) p, (const char *) r);
        return env->NewStringUTF(returnValueReceiver);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#luaL_len"><code>luaL_len</code></a>
     *
     * <pre><code>
     * [-0, +0, e]
     * </code></pre>
     *
     * <pre><code>
     * lua_Integer luaL_len (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns the "length" of the value at the given index
     * as a number;
     * it is equivalent to the '<code>#</code>' operator in Lua (see <a href="https://www.lua.org/manual/5.4/manual.html#3.4.7">&#167;3.4.7</a>).
     * Raises an error if the result of the operation is not an integer.
     * (This case can only happen through metamethods.)
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native long luaL_len(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jlong returnValueReceiver = (jlong) luaL_len((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#luaL_loadstring"><code>luaL_loadstring</code></a>
     *
     * <pre><code>
     * [-0, +1, –]
     * </code></pre>
     *
     * <pre><code>
     * int luaL_loadstring (lua_State *L, const char *s);
     * </code></pre>
     *
     * <p>
     * Loads a string as a Lua chunk.
     * This function uses <a href="https://www.lua.org/manual/5.4/manual.html#lua_load"><code>lua_load</code></a> to load the chunk in
     * the zero-terminated string <code>s</code>.
     * </p>
     *
     * <p>
     * This function returns the same results as <a href="https://www.lua.org/manual/5.4/manual.html#lua_load"><code>lua_load</code></a>.
     * </p>
     *
     * <p>
     * Also as <a href="https://www.lua.org/manual/5.4/manual.html#lua_load"><code>lua_load</code></a>, this function only loads the chunk;
     * it does not run it.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param s the string
     * @return see description
     */
    public native int luaL_loadstring(long ptr, String s); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) luaL_loadstring((lua_State *) L, (const char *) s);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#luaL_newmetatable"><code>luaL_newmetatable</code></a>
     *
     * <pre><code>
     * [-0, +1, m]
     * </code></pre>
     *
     * <pre><code>
     * int luaL_newmetatable (lua_State *L, const char *tname);
     * </code></pre>
     *
     * <p>
     * If the registry already has the key <code>tname</code>,
     * returns 0.
     * Otherwise,
     * creates a new table to be used as a metatable for userdata,
     * adds to this new table the pair <code>__name = tname</code>,
     * adds to the registry the pair <code>[tname] = new table</code>,
     * and returns 1.
     * </p>
     *
     * <p>
     * In both cases,
     * the function pushes onto the stack the final value associated
     * with <code>tname</code> in the registry.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param tname type name
     * @return see description
     */
    public native int luaL_newmetatable(long ptr, String tname); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) luaL_newmetatable((lua_State *) L, (const char *) tname);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#luaL_newstate"><code>luaL_newstate</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * lua_State *luaL_newstate (void);
     * </code></pre>
     *
     * <p>
     * Creates a new Lua state.
     * It calls <a href="https://www.lua.org/manual/5.4/manual.html#lua_newstate"><code>lua_newstate</code></a> with an
     * allocator based on the ISO&#160;C allocation functions
     * and then sets a warning function and a panic function (see <a href="https://www.lua.org/manual/5.4/manual.html#4.4">&#167;4.4</a>)
     * that print messages to the standard error output.
     * </p>
     *
     * <p>
     * Returns the new state,
     * or <code>NULL</code> if there is a memory allocation error.
     * </p>
     *
     * @param lid the id of the Lua state, to be used to identify between Java and Lua
     * @return see description
     */
    public native long luaL_newstate(int lid); /*
        lua_State* L = luaL_newstate();
        luaJavaSetup(L, env, lid);
        return (jlong) L;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#luaL_openlibs"><code>luaL_openlibs</code></a>
     *
     * <pre><code>
     * [-0, +0, e]
     * </code></pre>
     *
     * <pre><code>
     * void luaL_openlibs (lua_State *L);
     * </code></pre>
     *
     * <p>
     * Opens all standard Lua libraries into the given state.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     */
    public native void luaL_openlibs(long ptr); /*
        lua_State * L = (lua_State *) ptr;

        luaL_openlibs((lua_State *) L);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#luaL_pushfail"><code>luaL_pushfail</code></a>
     *
     * <pre><code>
     * [-0, +1, –]
     * </code></pre>
     *
     * <pre><code>
     * void luaL_pushfail (lua_State *L);
     * </code></pre>
     *
     * <p>
     * Pushes the <b>fail</b> value onto the stack (see <a href="https://www.lua.org/manual/5.4/manual.html#6">&#167;6</a>).
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     */
    public native void luaL_pushfail(long ptr); /*
        lua_State * L = (lua_State *) ptr;

        luaL_pushfail((lua_State *) L);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#luaL_ref"><code>luaL_ref</code></a>
     *
     * <pre><code>
     * [-1, +0, m]
     * </code></pre>
     *
     * <pre><code>
     * int luaL_ref (lua_State *L, int t);
     * </code></pre>
     *
     * <p>
     * Creates and returns a <em>reference</em>,
     * in the table at index <code>t</code>,
     * for the object on the top of the stack (and pops the object).
     * </p>
     *
     * <p>
     * A reference is a unique integer key.
     * As long as you do not manually add integer keys into the table <code>t</code>,
     * <a href="https://www.lua.org/manual/5.4/manual.html#luaL_ref"><code>luaL_ref</code></a> ensures the uniqueness of the key it returns.
     * You can retrieve an object referred by the reference <code>r</code>
     * by calling <code>lua_rawgeti(L, t, r)</code>.
     * The function <a href="https://www.lua.org/manual/5.4/manual.html#luaL_unref"><code>luaL_unref</code></a> frees a reference.
     * </p>
     *
     * <p>
     * If the object on the top of the stack is <b>nil</b>,
     * <a href="https://www.lua.org/manual/5.4/manual.html#luaL_ref"><code>luaL_ref</code></a> returns the constant <a><code>LUA_REFNIL</code></a>.
     * The constant <a><code>LUA_NOREF</code></a> is guaranteed to be different
     * from any reference returned by <a href="https://www.lua.org/manual/5.4/manual.html#luaL_ref"><code>luaL_ref</code></a>.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param t the stack index
     * @return see description
     */
    public native int luaL_ref(long ptr, int t); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) luaL_ref((lua_State *) L, (int) t);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#luaL_setmetatable"><code>luaL_setmetatable</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * void luaL_setmetatable (lua_State *L, const char *tname);
     * </code></pre>
     *
     * <p>
     * Sets the metatable of the object on the top of the stack
     * as the metatable associated with name <code>tname</code>
     * in the registry (see <a href="https://www.lua.org/manual/5.4/manual.html#luaL_newmetatable"><code>luaL_newmetatable</code></a>).
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param tname type name
     */
    public native void luaL_setmetatable(long ptr, String tname); /*
        lua_State * L = (lua_State *) ptr;

        luaL_setmetatable((lua_State *) L, (const char *) tname);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#luaL_testudata"><code>luaL_testudata</code></a>
     *
     * <pre><code>
     * [-0, +0, m]
     * </code></pre>
     *
     * <pre><code>
     * void *luaL_testudata (lua_State *L, int arg, const char *tname);
     * </code></pre>
     *
     * <p>
     * This function works like <a href="https://www.lua.org/manual/5.4/manual.html#luaL_checkudata"><code>luaL_checkudata</code></a>,
     * except that, when the test fails,
     * it returns <code>NULL</code> instead of raising an error.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param arg function argument index
     * @param tname type name
     * @return see description
     */
    public native long luaL_testudata(long ptr, int arg, String tname); /*
        lua_State * L = (lua_State *) ptr;

        jlong returnValueReceiver = (jlong) luaL_testudata((lua_State *) L, (int) arg, (const char *) tname);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#luaL_tolstring"><code>luaL_tolstring</code></a>
     *
     * <pre><code>
     * [-0, +1, e]
     * </code></pre>
     *
     * <pre><code>
     * const char *luaL_tolstring (lua_State *L, int idx, size_t *len);
     * </code></pre>
     *
     * <p>
     * Converts any Lua value at the given index to a C&#160;string
     * in a reasonable format.
     * The resulting string is pushed onto the stack and also
     * returned by the function (see <a href="https://www.lua.org/manual/5.4/manual.html#4.1.3">&#167;4.1.3</a>).
     * If <code>len</code> is not <code>NULL</code>,
     * the function also sets <code>*len</code> with the string length.
     * </p>
     *
     * <p>
     * If the value has a metatable with a <code>__tostring</code> field,
     * then <code>luaL_tolstring</code> calls the corresponding metamethod
     * with the value as argument,
     * and uses the result of the call as its result.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param idx the stack position
     * @param len pointer to length
     * @return see description
     */
    public native String luaL_tolstring(long ptr, int idx, long len); /*
        lua_State * L = (lua_State *) ptr;

        const char * returnValueReceiver = (const char *) luaL_tolstring((lua_State *) L, (int) idx, (size_t *) len);
        return env->NewStringUTF(returnValueReceiver);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#luaL_traceback"><code>luaL_traceback</code></a>
     *
     * <pre><code>
     * [-0, +1, m]
     * </code></pre>
     *
     * <pre><code>
     * void luaL_traceback (lua_State *L, lua_State *L1, const char *msg,
     *                      int level);
     * </code></pre>
     *
     * <p>
     * Creates and pushes a traceback of the stack <code>L1</code>.
     * If <code>msg</code> is not <code>NULL</code>, it is appended
     * at the beginning of the traceback.
     * The <code>level</code> parameter tells at which level
     * to start the traceback.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param L1 a <code>lua_State*</code> pointer
     * @param msg a message
     * @param level the running level
     */
    public native void luaL_traceback(long ptr, long L1, String msg, int level); /*
        lua_State * L = (lua_State *) ptr;

        luaL_traceback((lua_State *) L, (lua_State *) L1, (const char *) msg, (int) level);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#luaL_typename"><code>luaL_typename</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * const char *luaL_typename (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns the name of the type of the value at the given index.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native String luaL_typename(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        const char * returnValueReceiver = (const char *) luaL_typename((lua_State *) L, (int) index);
        return env->NewStringUTF(returnValueReceiver);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#luaL_unref"><code>luaL_unref</code></a>
     *
     * <pre><code>
     * [-0, +0, –]
     * </code></pre>
     *
     * <pre><code>
     * void luaL_unref (lua_State *L, int t, int ref);
     * </code></pre>
     *
     * <p>
     * Releases the reference <code>ref</code> from the table at index <code>t</code>
     * (see <a href="https://www.lua.org/manual/5.4/manual.html#luaL_ref"><code>luaL_ref</code></a>).
     * The entry is removed from the table,
     * so that the referred object can be collected.
     * The reference <code>ref</code> is also freed to be used again.
     * </p>
     *
     * <p>
     * If <code>ref</code> is <a href="https://www.lua.org/manual/5.4/manual.html#pdf-LUA_NOREF"><code>LUA_NOREF</code></a> or <a href="https://www.lua.org/manual/5.4/manual.html#pdf-LUA_REFNIL"><code>LUA_REFNIL</code></a>,
     * <a href="https://www.lua.org/manual/5.4/manual.html#luaL_unref"><code>luaL_unref</code></a> does nothing.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param t the stack index
     * @param ref the reference
     */
    public native void luaL_unref(long ptr, int t, int ref); /*
        lua_State * L = (lua_State *) ptr;

        luaL_unref((lua_State *) L, (int) t, (int) ref);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.4/manual.html#luaL_where"><code>luaL_where</code></a>
     *
     * <pre><code>
     * [-0, +1, m]
     * </code></pre>
     *
     * <pre><code>
     * void luaL_where (lua_State *L, int lvl);
     * </code></pre>
     *
     * <p>
     * Pushes onto the stack a string identifying the current position
     * of the control at level <code>lvl</code> in the call stack.
     * Typically this string has the following format:
     * </p>
     *
     * <pre>
     *      <em>chunkname</em>:<em>currentline</em>:
     * </pre>
     *
     * <p>
     * Level&#160;0 is the running function,
     * level&#160;1 is the function that called the running function,
     * etc.
     * </p>
     *
     * <p>
     * This function is used to build a prefix for error messages.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param lvl the running level
     */
    public native void luaL_where(long ptr, int lvl); /*
        lua_State * L = (lua_State *) ptr;

        luaL_where((lua_State *) L, (int) lvl);
    */


    /**
     * A wrapper function
     *
     * <p>
     * Open a library indivisually, alternative to <code>luaL_openlibs</code>
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param lib library name
     */
    public native void luaJ_openlib(long ptr, String lib); /*
        lua_State * L = (lua_State *) ptr;

        luaJ_openlib((lua_State *) L, (const char *) lib);
    */


    /**
     * A wrapper function
     *
     * <p>
     * See <code>lua_compare</code>
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index1 the stack position of the first element
     * @param index2 the stack position of the second element
     * @param op the operator
     * @return see description
     */
    public native int luaJ_compare(long ptr, int index1, int index2, int op); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) luaJ_compare((lua_State *) L, (int) index1, (int) index2, (int) op);
        return returnValueReceiver;
    */


    /**
     * A wrapper function
     *
     * <p>
     * Wrapper of <code>lua_(obj)len</code>
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native int luaJ_len(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) luaJ_len((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * A wrapper function
     *
     * <p>
     * Load a direct buffer
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param buffer the buffer (expecting direct)
     * @param start the starting index
     * @param size size
     * @param name the name
     * @return see description
     */
    public native int luaJ_loadbuffer(long ptr, Buffer buffer, int start, int size, String name); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) luaJ_loadbuffer((lua_State *) L, (unsigned char *) buffer, (int) start, (int) size, (const char *) name);
        return returnValueReceiver;
    */


    /**
     * A wrapper function
     *
     * <p>
     * Run a direct buffer
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param buffer the buffer (expecting direct)
     * @param start the starting index
     * @param size size
     * @param name the name
     * @return see description
     */
    public native int luaJ_dobuffer(long ptr, Buffer buffer, int start, int size, String name); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) luaJ_dobuffer((lua_State *) L, (unsigned char *) buffer, (int) start, (int) size, (const char *) name);
        return returnValueReceiver;
    */


    /**
     * A wrapper function
     *
     * <p>
     * Resume a coroutine
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param nargs the number of arguments that you pushed onto the stack
     * @return see description
     */
    public native int luaJ_resume(long ptr, int nargs); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) luaJ_resume((lua_State *) L, (int) nargs);
        return returnValueReceiver;
    */


    /**
     * A wrapper function
     *
     * <p>
     * Push a Java object
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param obj the Java object
     */
    public native void luaJ_pushobject(long ptr, Object obj); /*
        lua_State * L = (lua_State *) ptr;

        luaJ_pushobject((JNIEnv *) env, (lua_State *) L, (jobject) obj);
    */


    /**
     * A wrapper function
     *
     * <p>
     * Push a Java class
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param clazz the Java class
     */
    public native void luaJ_pushclass(long ptr, Object clazz); /*
        lua_State * L = (lua_State *) ptr;

        luaJ_pushclass((JNIEnv *) env, (lua_State *) L, (jobject) clazz);
    */


    /**
     * A wrapper function
     *
     * <p>
     * Push a Java array
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param array the Java array
     */
    public native void luaJ_pusharray(long ptr, Object array); /*
        lua_State * L = (lua_State *) ptr;

        luaJ_pusharray((JNIEnv *) env, (lua_State *) L, (jobject) array);
    */


    /**
     * A wrapper function
     *
     * <p>
     * Push a JFunction
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param func the function object
     */
    public native void luaJ_pushfunction(long ptr, Object func); /*
        lua_State * L = (lua_State *) ptr;

        luaJ_pushfunction((JNIEnv *) env, (lua_State *) L, (jobject) func);
    */


    /**
     * A wrapper function
     *
     * <p>
     * Push a buffer as a raw Lua string
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param buffer the buffer (expecting direct)
     * @param start the starting index
     * @param size size
     */
    public native void luaJ_pushlstring(long ptr, Buffer buffer, int start, int size); /*
        lua_State * L = (lua_State *) ptr;

        luaJ_pushlstring((lua_State *) L, (unsigned char *) buffer, (int) start, (int) size);
    */


    /**
     * A wrapper function
     *
     * <p>
     * Is a Java object (including object, array or class)
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native int luaJ_isobject(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) luaJ_isobject((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * A wrapper function
     *
     * <p>
     * Convert to Java object if it is one
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native Object luaJ_toobject(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jobject returnValueReceiver = (jobject) luaJ_toobject((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * A wrapper function
     *
     * <p>
     * Create a new thread
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param lid the id of the Lua state, to be used to identify between Java and Lua
     * @return see description
     */
    public native long luaJ_newthread(long ptr, int lid); /*
        lua_State * L = (lua_State *) ptr;

        jlong returnValueReceiver = (jlong) luaJ_newthread((lua_State *) L, (int) lid);
        return returnValueReceiver;
    */


    /**
     * A wrapper function
     *
     * <p>
     * Append a searcher loading from Java side into <code>package.searchers / loaders</code>
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @return see description
     */
    public native int luaJ_initloader(long ptr); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) luaJ_initloader((lua_State *) L);
        return returnValueReceiver;
    */


    /**
     * A wrapper function
     *
     * <p>
     * Runs {@code CallNonvirtual<type>MethodA}. See AbstractLua for usages.
     * Parameters should be boxed and pushed on stack.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param clazz the Java class
     * @param method the method name
     * @param sig the method signature used in {@code GetMethodID}
     * @param obj the Java object
     * @param params encoded parameter types
     * @return see description
     */
    public native int luaJ_invokespecial(long ptr, Class clazz, String method, String sig, Object obj, String params); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) luaJ_invokespecial((JNIEnv *) env, (lua_State *) L, (jclass) clazz, (const char *) method, (const char *) sig, (jobject) obj, (const char *) params);
        return returnValueReceiver;
    */


    /**
     * A wrapper function
     *
     * <p>
     * See <code>lua_isinteger</code>
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native int luaJ_isinteger(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) luaJ_isinteger((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * A wrapper function
     *
     * <p>
     * Removes the thread from the global registry, thus allowing it to get garbage collected
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     */
    public native void luaJ_removestateindex(long ptr); /*
        lua_State * L = (lua_State *) ptr;

        luaJ_removestateindex((lua_State *) L);
    */


    /**
     * A wrapper function
     *
     * <p>
     * Performs a full garbage-collection cycle
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     */
    public native void luaJ_gc(long ptr); /*
        lua_State * L = (lua_State *) ptr;

        luaJ_gc((lua_State *) L);
    */


    /**
     * A wrapper function
     *
     * <p>
     * See <code>lua_dump</code>
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @return see description
     */
    public native Object luaJ_dumptobuffer(long ptr); /*
        lua_State * L = (lua_State *) ptr;

        jobject returnValueReceiver = (jobject) luaJ_dumptobuffer((lua_State *) L);
        return returnValueReceiver;
    */


    /**
     * A wrapper function
     *
     * <p>
     * See <code>lua_tolstring</code>
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native Object luaJ_tobuffer(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jobject returnValueReceiver = (jobject) luaJ_tobuffer((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * A wrapper function
     *
     * <p>
     * See <code>lua_tolstring</code>
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native Object luaJ_todirectbuffer(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jobject returnValueReceiver = (jobject) luaJ_todirectbuffer((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


}
