package party.iroiro.luajava.luaj;

import org.luaj.vm2.*;
import org.luaj.vm2.compiler.DumpState;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.*;
import party.iroiro.luajava.*;
import party.iroiro.luajava.luaj.values.JavaArray;
import party.iroiro.luajava.luaj.values.JavaClass;
import party.iroiro.luajava.luaj.values.JavaObject;
import party.iroiro.luajava.luaj.values.LightUserdata;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;

import static party.iroiro.luajava.luaj.JavaLib.checkOrError;
import static party.iroiro.luajava.luaj.LuaJConsts.*;


public class LuaJNatives implements LuaNatives {
    final static LuaJInstances instances = new LuaJInstances();

    @Override
    public void loadAsGlobal() {
        // Pointless
    }

    @Override
    public int getRegistryIndex() {
        return LuaJConsts.LUA_REGISTRYINDEX;
    }

    @Override
    public int lua_checkstack(long ptr, int extra) {
        // Pointless
        return 1;
    }

    @Override
    public void lua_close(long ptr) {
        instances.remove((int) ptr);
    }

    @Override
    public void lua_concat(long ptr, int n) {
        LuaJState L = instances.get((int) ptr);
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        if (n == 0) {
            L.push(LuaValue.valueOf(""));
            return;
        }
        if (n == 1) {
            return;
        }
        LuaValue value = L.toLuaValue(-n);
        for (int i = n - 1; i > 0; i--) {
            value = value.concat(L.toLuaValue(-i));
        }
        L.pop(n);
        L.push(value);
    }

    @Override
    public void lua_createtable(long ptr, int narr, int nrec) {
        LuaJState L = instances.get((int) ptr);
        L.push(LuaValue.tableOf(narr, nrec));
    }

    @Override
    public int lua_error(long ptr) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void luaJ_getfield(long ptr, int index, String k) {
        LuaJState L = instances.get((int) ptr);
        L.push(L.toLuaValue(index).get(k));
    }

    @Override
    public void luaJ_getglobal(long ptr, String name) {
        LuaJState L = instances.get((int) ptr);
        L.push(L.globals.get(name));
    }

    @Override
    public int lua_getmetatable(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(index);
        LuaValue metatable = value.getmetatable();
        if (metatable != null && metatable.istable()) {
            L.push(metatable);
            return 1;
        }
        return 0;
    }

    @Override
    public void luaJ_gettable(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(index);
        LuaValue key = L.toLuaValue(-1);
        L.pop(1);
        L.push(value.get(key));
    }

    @Override
    public int lua_gettop(long ptr) {
        LuaJState L = instances.get((int) ptr);
        return L.getTop();
    }

    @Override
    public void lua_insert(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(-1);
        index = L.toAbsoluteIndex(index);
        L.pop(1);
        L.insert(index, value);
    }

    @Override
    public int lua_isboolean(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(index);
        return value.isboolean() ? 1 : 0;
    }

    @Override
    public int lua_iscfunction(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(index);
        return value.isfunction() && !value.isclosure() ? 1 : 0;
    }

    @Override
    public int lua_isfunction(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(index);
        return value.isfunction() ? 1 : 0;
    }

    @Override
    public int lua_islightuserdata(long ptr, int index) {
        return lua_type(ptr, index) == LUA_TLIGHTUSERDATA ? 1 : 0;
    }

    @Override
    public int lua_isnil(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(index);
        return value.isnil() ? 1 : 0;
    }

    @Override
    public int lua_isnone(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(index);
        return value == LuaValue.NONE ? 1 : 0;
    }

    @Override
    public int lua_isnoneornil(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(index);
        return value == LuaValue.NONE || value.isnil() ? 1 : 0;
    }

    @Override
    public int lua_isnumber(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(index);
        return value.isnumber() ? 1 : 0;
    }

    @Override
    public int luaJ_isinteger(long ptr, int index) {
        return 0;
    }

    @Override
    public int lua_isstring(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(index);
        return value.isstring() ? 1 : 0;
    }

    @Override
    public int lua_istable(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(index);
        return value.istable() ? 1 : 0;
    }

    @Override
    public int lua_isthread(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(index);
        return value.isthread() ? 1 : 0;
    }

    @Override
    public int lua_isuserdata(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(index);
        return value.isuserdata() ? 1 : 0;
    }

    public void lua_newuserdata(long ptr, Object object) {
        LuaJState L = instances.get((int) ptr);
        L.push(LuaValue.userdataOf(object));
    }

    @Override
    public void lua_newtable(long ptr) {
        LuaJState L = instances.get((int) ptr);
        L.push(LuaValue.tableOf());
    }

    @Override
    public long lua_newthread(long ptr) {
        LuaJState L = instances.get((int) ptr);
        LuaThread thread = new LuaThread(L.globals, new FunctionInvoker());
        LuaInstances.Token<LuaJState> handle = instances.add();
        handle.setter.accept(new LuaJState(handle.id, L.lid, L.globals, thread, L));
        L.push(thread);
        return handle.id;
    }

    protected LuaThread lua_create_thread(long ptr, LuaValue func) {
        LuaJState L = instances.get((int) ptr);
        long J = lua_newthread(ptr);
        LuaThread thread = (LuaThread) L.toLuaValue(-1);
        L.pop(1);
        FunctionInvoker.setFunction(instances.get((int) J), func.checkfunction());
        return thread;
    }

    protected void lua_require_coroutine(long ptr) {
        LuaJState L = instances.get((int) ptr);
        L.globals.load(new CoroutineLib() {
            @Override
            public LuaValue call(LuaValue modname, LuaValue env) {
                LuaValue table = super.call(modname, env);
                table.set("create", new LibFunction() {
                    @Override
                    public LuaValue call(LuaValue func) {
                        return lua_create_thread(ptr, func);
                    }
                });
                table.set("wrap", new LibFunction() {
                    @Override
                    public LuaValue call(LuaValue func) {
                        LuaThread thread = lua_create_thread(ptr, func);
                        return new VarArgFunction() {
                            @Override
                            public Varargs invoke(Varargs args) {
                                // Copied from CoroutineLib.java from LuaJ
                                Varargs result = thread.resume(args);
                                if (result.arg1().toboolean()) {
                                    return result.subargs(2);
                                } else {
                                    return error(result.arg(2).tojstring());
                                }
                            }
                        };
                    }
                });
                return table;
            }
        });
    }

    @Override
    public int lua_next(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue table = L.toLuaValue(index);
        LuaValue key = L.toLuaValue(-1);
        L.pop(1);
        Varargs next = table.next(key);
        if (next.narg() != 2) {
            return 0;
        }
        L.push(next.arg(1));
        L.push(next.arg(2));
        return 1;
    }

    @Override
    public int lua_pcall(long ptr, int nargs, int nresults, int errfunc) {
        LuaJState L = instances.get((int) ptr);
        LuaValue f = L.toLuaValue(-nargs - 1);
        LuaValue[] args = new LuaValue[nargs];
        for (int i = 0; i < nargs; i++) {
            args[i] = L.toLuaValue(-nargs + i);
        }
        Varargs results;
        LuaValue errorCallback = errfunc == 0 ? null : L.toLuaValue(errfunc);
        L.setError(null);
        try {
            results = f.invoke(args);
        } catch (Exception luaError) {
            Throwable err = LuaJState.unwrapLuaError(luaError);
            L.setError(err);
            LuaValue message = LuaValue.valueOf(err.toString());
            if (errorCallback != null) {
                try {
                    message = errorCallback.call(message);
                } catch (Exception innerLuaError) {
                    err = LuaJState.unwrapLuaError(innerLuaError);
                    L.setError(innerLuaError);
                    L.pop(nargs + 1);
                    L.push(LuaValue.valueOf(err.toString()));
                    return LUA_ERRERR;
                }
            }
            L.pop(nargs + 1);
            L.push(message);
            return LUA_ERRRUN;
        }
        L.pop(nargs + 1);
        if (nresults == LUA_MULTRET) {
            nresults = results.narg();
        }
        for (int i = 0; i < nresults; i++) {
            if (i < results.narg()) {
                L.push(results.arg(i + 1));
            } else {
                L.push(LuaValue.NIL);
            }
        }
        return 0;
    }

    @Override
    public void lua_pop(long ptr, int n) {
        LuaJState L = instances.get((int) ptr);
        L.pop(n);
    }

    @Override
    public void lua_pushboolean(long ptr, int b) {
        LuaJState L = instances.get((int) ptr);
        L.push(LuaValue.valueOf(b != 0));
    }

    @Override
    public void lua_pushinteger(long ptr, long n) {
        LuaJState L = instances.get((int) ptr);
        L.push(LuaInteger.valueOf(n));
    }

    @Override
    public void lua_pushlightuserdata(long ptr, long p) {
        LuaJState L = instances.get((int) ptr);
        L.push(new LightUserdata(p));
    }

    @Override
    public void lua_pushnil(long ptr) {
        LuaJState L = instances.get((int) ptr);
        L.push(LuaValue.NIL);
    }

    @Override
    public void lua_pushnumber(long ptr, double n) {
        LuaJState L = instances.get((int) ptr);
        L.push(LuaValue.valueOf(n));
    }

    @Override
    public void luaJ_pushstring(long ptr, String s) {
        LuaJState L = instances.get((int) ptr);
        L.push(LuaValue.valueOf(s));
    }

    @Override
    public int lua_pushthread(long ptr) {
        LuaJState L = instances.get((int) ptr);
        if (L.thread != null) {
            L.push(L.thread);
            return 0;
        } else {
            L.push(new LuaThread(L.globals));
            return 1;
        }
    }

    @Override
    public void lua_pushvalue(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(index);
        L.push(value);
    }

    @Override
    public int lua_rawequal(long ptr, int index1, int index2) {
        LuaJState L = instances.get((int) ptr);
        LuaValue v1 = L.toLuaValue(index1);
        LuaValue v2 = L.toLuaValue(index2);
        return v1.raweq(v2) ? 1 : 0;
    }

    @Override
    public void luaJ_rawget(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(index);
        LuaValue key = L.toLuaValue(-1);
        L.pop(1);
        L.push(value.rawget(key));
    }

    @Override
    public void luaJ_rawgeti(long ptr, int index, int n) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(index);
        L.push(value.rawget(n));
    }

    @Override
    public void lua_rawset(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue table = L.toLuaValue(index);
        LuaValue key = L.toLuaValue(-2);
        LuaValue value = L.toLuaValue(-1);
        L.pop(2);
        table.rawset(key, value);
    }

    @Override
    public void lua_rawseti(long ptr, int index, int n) {
        LuaJState L = instances.get((int) ptr);
        LuaValue table = L.toLuaValue(index);
        LuaValue value = L.toLuaValue(-1);
        L.pop(1);
        table.rawset(n, value);
    }

    @Override
    public void lua_remove(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        L.remove(index);
    }

    @Override
    public void lua_replace(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(-1);
        index = L.toAbsoluteIndex(index);
        L.pop(1);
        L.replace(index, value);
    }

    @Override
    public void lua_setfield(long ptr, int index, String k) {
        LuaJState L = instances.get((int) ptr);
        LuaValue table = L.toLuaValue(index);
        LuaValue value = L.toLuaValue(-1);
        L.pop(1);
        table.set(k, value);
    }

    @Override
    public void lua_setglobal(long ptr, String name) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(-1);
        L.pop(1);
        L.globals.set(name, value);
    }

    @Override
    public void luaJ_setmetatable(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(index);
        LuaValue meta = L.toLuaValue(-1);
        L.pop(1);
        value.setmetatable(meta);
    }

    @Override
    public void lua_settable(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue table = L.toLuaValue(index);
        LuaValue key = L.toLuaValue(-2);
        LuaValue value = L.toLuaValue(-1);
        L.pop(2);
        table.set(key, value);
    }

    @Override
    public void lua_settop(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        L.setTop(index);
    }

    @Override
    public int lua_status(long ptr) {
        LuaJState L = instances.get((int) ptr);
        if (L.thread == null) {
            return 0;
        }
        if (L.thread.state.status == LuaThread.STATUS_SUSPENDED) {
            return LUA_YIELD;
        }
        return 0;
    }

    @Override
    public int lua_toboolean(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(index);
        return value.toboolean() ? 1 : 0;
    }

    @Override
    public long lua_tointeger(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(index);
        return value.tolong();
    }

    @Override
    public double lua_tonumber(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(index);
        return value.todouble();
    }

    @Override
    public long lua_topointer(long ptr, int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String lua_tostring(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(index);
        if (value.isnumber()) {
            value = value.tostring();
            L.replace(index, value);
        }
        return value.tojstring();
    }

    @Override
    public long lua_tothread(long ptr, int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long lua_touserdata(long ptr, int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lua_type(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(index);
        // LuaJ: NONE.type() == NIL.type()... What.
        return value == LuaValue.NONE ? LuaValue.TNONE : value.type();
    }

    @Override
    public String lua_typename(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(index);
        return value.typename();
    }

    @Override
    public void lua_xmove(long from, long to, int n) {
        LuaJState L = instances.get((int) from);
        LuaJState J = instances.get((int) to);
        for (int i = 0; i < n; i++) {
            J.push(L.toLuaValue(-n + i));
        }
        L.pop(n);
    }

    @Override
    public int lua_yield(long ptr, int nresults) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lua_gethookcount(long ptr) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lua_gethookmask(long ptr) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String lua_getupvalue(long ptr, int funcindex, int n) {
        LuaJState L = instances.get((int) ptr);
        LuaClosure value = (LuaClosure) L.toLuaValue(funcindex);
        L.push(value.upValues[n].getValue());
        return "";
    }

    @Override
    public String lua_setupvalue(long ptr, int funcindex, int n) {
        LuaJState L = instances.get((int) ptr);
        LuaClosure up = (LuaClosure) L.toLuaValue(funcindex);
        LuaValue value = L.toLuaValue(-1);
        L.pop(1);
        up.upValues[n].setValue(value);
        return "";
    }

    @Override
    public int luaL_callmeta(long ptr, int obj, String e) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(obj);
        LuaValue metatable = value.getmetatable();
        if (metatable == null) {
            return 0;
        }
        LuaValue meta = metatable.get(e);
        if (!meta.isfunction()) {
            return 0;
        }
        L.push(meta.call(value));
        return 1;
    }

    @Override
    public int luaL_dostring(long ptr, String str) {
        LuaJState L = instances.get((int) ptr);
        try {
            L.push(L.globals.load(str));
        } catch (LuaError e) {
            L.push(LuaValue.valueOf(e.getMessage()));
            return LUA_ERRSYNTAX;
        }
        return lua_pcall(ptr, 0, LUA_MULTRET, 0);
    }

    @Override
    public int luaL_getmetafield(long ptr, int obj, String e) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(obj);
        LuaValue metatable = value.getmetatable();
        if (metatable == null) {
            return 0;
        }
        LuaValue meta = metatable.get(e);
        if (meta.isnil()) {
            return 0;
        }
        L.push(value);
        return 1;

    }

    @Override
    public void luaJ_getmetatable(long ptr, String tname) {
        LuaJState L = instances.get((int) ptr);
        L.push(L.getRegistry(tname));
    }

    @Override
    public int luaL_loadstring(long ptr, String s) {
        LuaJState L = instances.get((int) ptr);
        try {
            L.push(L.globals.load(s));
        } catch (Throwable e) {
            L.push(LuaValue.valueOf(e.toString()));
            return LUA_ERRSYNTAX;
        }
        return 0;
    }

    @Override
    public int luaL_newmetatable(long ptr, String tname) {
        LuaJState L = instances.get((int) ptr);
        if (L.getRegistry(tname).isnil()) {
            LuaTable value = LuaValue.tableOf();
            L.setRegistry(tname, value);
            L.push(value);
            return 1;
        }
        L.push(L.getRegistry(tname));
        return 0;
    }

    @Override
    public long luaL_newstate(int lid) {
        LuaInstances.Token<LuaJState> handle = instances.add();
        Globals globals = new Globals();
        globals.load(new BaseLib());
        LuaC.install(globals);
        LoadState.install(globals);
        globals.load(new JavaLib(handle.id));
        LuaJState state = new LuaJState(handle.id, lid, globals, null, null);
        handle.setter.accept(state);
        return handle.id;
    }

    @Override
    public void luaL_openlibs(long ptr) {
        LuaJState L = instances.get((int) ptr);
        L.globals.load(new PackageLib());

        lua_require_coroutine(ptr);
        L.globals.load(new DebugLib());
        L.globals.load(new JseIoLib());
        L.globals.load(new MoreMathLib());
        L.globals.load(new OsLib());
        L.globals.load(new StringLib());
        L.globals.load(new TableLib());
    }

    @Override
    public int luaL_ref(long ptr, int t) {
        LuaJState L = instances.get((int) ptr);
        LuaValue referee = L.toLuaValue(-1);
        if (referee.isnil()) {
            L.pop(1);
            return LUA_REFNIL;
        }
        LuaTable value = (LuaTable) L.toLuaValue(t);
        LuaValue i = value.rawget(0);
        int next = i.toint() + 1;
        value.rawset(0, LuaValue.valueOf(next));
        value.rawset(next, referee);
        L.pop(1);
        return next;
    }

    @Override
    public String luaL_typename(long ptr, int index) {
        return lua_typename(ptr, index);
    }

    @Override
    public void luaL_unref(long ptr, int t, int ref) {
        LuaJState L = instances.get((int) ptr);
        LuaTable value = (LuaTable) L.toLuaValue(t);
        value.rawset(ref, LuaValue.NIL);
    }

    @Override
    public void luaL_where(long ptr, int lvl) {
        LuaJState L = instances.get((int) ptr);
        L.push(LuaValue.valueOf(""));
    }

    @Override
    public void luaJ_openlib(long ptr, String lib) {
        LuaJState L = instances.get((int) ptr);
        switch (lib) {
            case "":
                L.globals.load(new BaseLib());
                break;
            case "package":
                L.globals.load(new PackageLib());
                break;
            case "coroutine":
                lua_require_coroutine(ptr);
                break;
            case "debug":
                L.globals.load(new DebugLib());
                break;
            case "io":
                L.globals.load(new JseIoLib());
                break;
            case "math":
                L.globals.load(new MoreMathLib());
                break;
            case "os":
                L.globals.load(new OsLib());
                break;
            case "string":
                L.globals.load(new StringLib());
                break;
            case "table":
                L.globals.load(new TableLib());
                break;
        }
    }

    @Override
    public int luaJ_compare(long ptr, int index1, int index2, int op) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value1 = L.toLuaValue(index1);
        LuaValue value2 = L.toLuaValue(index2);
        LuaValue v;
        if (op < 0) {
            v = value1.lt(value2);
        } else if (op == 0) {
            v = value1.eq(value2);
        } else {
            v = value1.lt(value2).or(value1.eq(value2));
        }
        return v.toboolean() ? 1 : 0;
    }

    @Override
    public int luaJ_len(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(index);
        return value.length();
    }

    @Override
    public int luaJ_loadbuffer(long ptr, Buffer buffer, int start, int size, String name) {
        LuaJState L = instances.get((int) ptr);
        ByteBuffer bytes = ((ByteBuffer) buffer).duplicate();
        bytes.position(start).limit(start + size);
        try {
            L.push(
                    L.globals.load(new InputStream() {
                        @Override
                        public int read() {
                            return bytes.hasRemaining() ? bytes.get() & 0xFF : -1;
                        }
                    }, name, "bt", L.globals)
            );
        } catch (LuaError e) {
            L.push(LuaValue.valueOf(e.toString()));
            return LUA_ERRRUN;
        }
        return 0;
    }

    @Override
    public int luaJ_dobuffer(long ptr, Buffer buffer, int start, int size, String name) {
        luaJ_loadbuffer(ptr, buffer, start, size, name);
        return lua_pcall(ptr, 0, LUA_MULTRET, 0);
    }

    @Override
    public int luaJ_resume(long ptr, int nargs) {
        LuaJState L = instances.get((int) ptr);
        if (L.thread == null) {
            throw new LuaException(LuaException.LuaError.RUNTIME,
                    "resuming the main thread is not supported with luaj");
        }
        LuaValue[] args = new LuaValue[nargs];
        for (int i = 0; i < nargs; i++) {
            args[i] = L.toLuaValue(-nargs + i);
        }
        L.pop(nargs);
        if (L.thread.state.status == LuaThread.STATUS_DEAD) {
            L.thread.state.status = LuaThread.STATUS_INITIAL;
        }
        if (L.thread.state.status == LuaThread.STATUS_INITIAL) {
            LuaValue func = L.toLuaValue(-nargs - 1);
            FunctionInvoker.setFunction(L, func);
            L.pop(1);
        }
        Varargs results = L.thread.resume(LuaValue.varargsOf(args));
        for (int i = 1; i <= results.narg(); i++) {
            L.push(results.arg(i));
        }
        return L.thread.state.status == LuaThread.STATUS_SUSPENDED ? LUA_YIELD : 0;
    }

    @Override
    public void luaJ_pushobject(long ptr, Object obj) {
        LuaJState L = instances.get((int) ptr);
        L.push(new JavaObject(obj, L.jObjectMetatable, L.address));
    }

    @Override
    public void luaJ_pushclass(long ptr, Object clazz) {
        LuaJState L = instances.get((int) ptr);
        L.push(new JavaClass((Class<?>) clazz, L.jClassMetatable, L.address));
    }

    @Override
    public void luaJ_pusharray(long ptr, Object array) {
        LuaJState L = instances.get((int) ptr);
        L.push(new JavaArray(array, L.jArrayMetatable, L.address));
    }

    @Override
    public void luaJ_pushfunction(long ptr, Object func) {
        LuaJState L = instances.get((int) ptr);
        JFunction f = (JFunction) func;
        L.push(new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                L.setError(null);
                L.pushFrame();
                L.pushAll(args);
                return checkOrError(L, f.__call(Jua.get(L.lid)));
            }
        });
    }

    @Override
    public void luaJ_pushlstring(long ptr, Buffer buffer, int start, int size) {
        LuaJState L = instances.get((int) ptr);
        byte[] bytes = new byte[size];
        ByteBuffer duplicate = ((ByteBuffer) buffer).duplicate();
        duplicate.position(start).limit(start + size);
        duplicate.get(bytes);
        L.push(LuaValue.valueOf(bytes));
    }

    @Override
    public int luaJ_isobject(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(index);
        return (value instanceof JavaObject) ? 1 : 0;
    }

    @Override
    public Object luaJ_toobject(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(index);
        if (value instanceof JavaObject) {
            return value.touserdata();
        }
        return null;
    }

    @Override
    public long luaJ_newthread(long ptr, int lid) {
        return lua_newthread(ptr);
    }

    @Override
    public int luaJ_initloader(long ptr) {
        LuaJState L = instances.get((int) ptr);
        LuaValue table = L.globals.get("package");
        if (!table.istable()) {
            return 0;
        }
        LuaValue searchers = table.get("searchers");
        if (!searchers.istable()) {
            return 0;
        }
        searchers.rawset(searchers.rawlen() + 1, new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                L.pushFrame();
                return checkOrError(L, JuaAPI.load(L.lid, arg.checkjstring())).arg1();
            }
        });
        searchers.rawset(searchers.rawlen() + 1, new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                L.pushFrame();
                return checkOrError(L, JuaAPI.loadModule(L.lid, arg.checkjstring())).arg1();
            }
        });
        return 0;
    }

    @Override
    public int luaJ_invokespecial(long ptr, @SuppressWarnings("rawtypes") Class clazz, String method, String sig, Object obj, String params) {
        throw new UnsupportedOperationException("invokespecial not available without JNI");
    }

    @Override
    public void luaJ_removestateindex(long ptr) {
        // ignored
    }

    @Override

    public void luaJ_gc(long ptr) {
        Runtime.getRuntime().gc();
    }

    @Override
    public Object luaJ_dumptobuffer(long ptr) {
        LuaJState L = instances.get((int) ptr);
        LuaValue func = L.toLuaValue(-1);
        if (!func.isclosure()) {
            return null;
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            Prototype p = ((LuaClosure) func).p;
            DumpState.dump(p, output, true);
            byte[] byteArray = output.toByteArray();
            ByteBuffer buffer = ByteBuffer.allocateDirect(output.size());
            buffer.put(byteArray);
            buffer.flip();
            return buffer;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Object luaJ_tobuffer(long ptr, int index) {
        LuaJState L = instances.get((int) ptr);
        LuaValue value = L.toLuaValue(index);
        if (!value.isstring()) {
            return null;
        }
        if (!(value instanceof LuaString)) {
            value = value.tostring();
            L.replace(index, value);
        }
        byte[] bytes = ((LuaString) value).m_bytes;
        ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        return buffer;
    }

    @Override
    public Object luaJ_todirectbuffer(long ptr, int index) {
        return luaJ_tobuffer(ptr, index);
    }

    public static class FunctionInvoker extends VarArgFunction {
        public final static ThreadLocal<Boolean> insideCoroutine = new ThreadLocal<>();

        public static boolean isInsideCoroutine() {
            Boolean b = insideCoroutine.get();
            return b != null && b;
        }

        LuaValue function;

        @Override
        public Varargs invoke(Varargs args) {
            insideCoroutine.set(true);
            return function.invoke(args);
        }

        public static void setFunction(LuaJState L, LuaValue func) {
            ((FunctionInvoker) L.thread.state.function).function = func;
        }
    }

    private static class MoreMathLib extends MathLib {
        @Override
        public LuaValue call(LuaValue modname, LuaValue env) {
            LuaValue math = super.call(modname, env);
            math.set("log", new TwoArgFunction() {
                @Override
                public LuaValue call(LuaValue x, LuaValue base) {
                    double baseValue = base.isnumber() ? base.todouble() : Math.E;
                    return LuaValue.valueOf(Math.log(x.checkdouble()) / Math.log(baseValue));
                }
            });
            return math;
        }
    }
}
