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

package party.iroiro.luajava.lua51;

import java.util.concurrent.atomic.AtomicReference;
import java.nio.Buffer;

import party.iroiro.luajava.LuaNatives;
import party.iroiro.luajava.util.GlobalLibraryLoader;

/**
 * Lua C API wrappers
 *
 * <p>
 * This file is programmatically generated from <a href="https://www.lua.org/manual/5.1/manual.html">the Lua 5.1 Reference Manual</a>.
 * </p>
 * <p>
 * The following functions are excluded:
 * <ul>
 * <li><code>luaL_addchar</code></li>
 * <li><code>luaL_addlstring</code></li>
 * <li><code>luaL_addsize</code></li>
 * <li><code>luaL_addstring</code></li>
 * <li><code>luaL_addvalue</code></li>
 * <li><code>luaL_argcheck</code></li>
 * <li><code>luaL_argerror</code></li>
 * <li><code>luaL_buffinit</code></li>
 * <li><code>luaL_checkany</code></li>
 * <li><code>luaL_checkint</code></li>
 * <li><code>luaL_checkinteger</code></li>
 * <li><code>luaL_checklong</code></li>
 * <li><code>luaL_checklstring</code></li>
 * <li><code>luaL_checknumber</code></li>
 * <li><code>luaL_checkoption</code></li>
 * <li><code>luaL_checkstack</code></li>
 * <li><code>luaL_checkstring</code></li>
 * <li><code>luaL_checktype</code></li>
 * <li><code>luaL_checkudata</code></li>
 * <li><code>luaL_dofile</code></li>
 * <li><code>luaL_error</code></li>
 * <li><code>luaL_loadbuffer</code></li>
 * <li><code>luaL_loadfile</code></li>
 * <li><code>luaL_optint</code></li>
 * <li><code>luaL_optinteger</code></li>
 * <li><code>luaL_optlong</code></li>
 * <li><code>luaL_optlstring</code></li>
 * <li><code>luaL_optnumber</code></li>
 * <li><code>luaL_optstring</code></li>
 * <li><code>luaL_prepbuffer</code></li>
 * <li><code>luaL_pushresult</code></li>
 * <li><code>luaL_register</code></li>
 * <li><code>lua_atpanic</code></li>
 * <li><code>lua_call</code></li>
 * <li><code>lua_cpcall</code></li>
 * <li><code>lua_dump</code></li>
 * <li><code>lua_getallocf</code></li>
 * <li><code>lua_gethook</code></li>
 * <li><code>lua_getinfo</code></li>
 * <li><code>lua_getlocal</code></li>
 * <li><code>lua_getstack</code></li>
 * <li><code>lua_load</code></li>
 * <li><code>lua_newstate</code></li>
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
 * <li><code>lua_tocfunction</code></li>
 * <li><code>lua_tolstring</code></li>
 * </ul>
 */
@SuppressWarnings({"unused", "rawtypes"})
public class Lua51Natives implements LuaNatives {
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

    protected Lua51Natives() throws IllegalStateException {
        synchronized (loaded) {
            if (loaded.get() != null) { return; }
            try {
                GlobalLibraryLoader.register(Lua51Natives.class, false);
                String file = GlobalLibraryLoader.load("lua51");
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
        return (jint) initLua51Bindings(env);
    */

    /**
     * Get <code>LUA_REGISTRYINDEX</code>, which is a computed compile time constant
     */
    public native int getRegistryIndex(); /*
        return LUA_REGISTRYINDEX;
    */

    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_checkstack"><code>lua_checkstack</code></a>
     *
     * <pre><code>
     * [-0, +0, m]
     * </code></pre>
     *
     * <pre><code>
     * int lua_checkstack (lua_State *L, int extra);
     * </code></pre>
     *
     * <p>
     * Ensures that there are at least <code>extra</code> free stack slots in the stack.
     * It returns false if it cannot grow the stack to that size.
     * This function never shrinks the stack;
     * if the stack is already larger than the new size,
     * it is left unchanged.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param extra extra slots
     * @return see description
     */
    public native int lua_checkstack(long ptr, int extra); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_checkstack((lua_State *) L, (int) extra);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_close"><code>lua_close</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * void lua_close (lua_State *L);
     * </code></pre>
     *
     * <p>
     * Destroys all objects in the given Lua state
     * (calling the corresponding garbage-collection metamethods, if any)
     * and frees all dynamic memory used by this state.
     * On several platforms, you may not need to call this function,
     * because all resources are naturally released when the host program ends.
     * On the other hand, long-running programs,
     * such as a daemon or a web server,
     * might need to release states as soon as they are not needed,
     * to avoid growing too large.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     */
    public native void lua_close(long ptr); /*
        lua_State * L = (lua_State *) ptr;

        lua_close((lua_State *) L);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_concat"><code>lua_concat</code></a>
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
     * pops them, and leaves the result at the top.
     * If <code>n</code>&#160;is&#160;1, the result is the single value on the stack
     * (that is, the function does nothing);
     * if <code>n</code> is 0, the result is the empty string.
     * Concatenation is performed following the usual semantics of Lua
     * (see <a href="https://www.lua.org/manual/5.1/manual.html#2.5.4">&#167;2.5.4</a>).
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_createtable"><code>lua_createtable</code></a>
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
     * The new table has space pre-allocated
     * for <code>narr</code> array elements and <code>nrec</code> non-array elements.
     * This pre-allocation is useful when you know exactly how many elements
     * the table will have.
     * Otherwise you can use the function <a href="https://www.lua.org/manual/5.1/manual.html#lua_newtable"><code>lua_newtable</code></a>.
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_equal"><code>lua_equal</code></a>
     *
     * <pre><code>
     * [-0, +0, e]
     * </code></pre>
     *
     * <pre><code>
     * int lua_equal (lua_State *L, int index1, int index2);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the two values in acceptable indices <code>index1</code> and
     * <code>index2</code> are equal,
     * following the semantics of the Lua <code>==</code> operator
     * (that is, may call metamethods).
     * Otherwise returns&#160;0.
     * Also returns&#160;0 if any of the indices is non valid.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index1 the stack position of the first element
     * @param index2 the stack position of the second element
     * @return see description
     */
    public native int lua_equal(long ptr, int index1, int index2); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_equal((lua_State *) L, (int) index1, (int) index2);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_error"><code>lua_error</code></a>
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
     * Generates a Lua error.
     * The error message (which can actually be a Lua value of any type)
     * must be on the stack top.
     * This function does a long jump,
     * and therefore never returns.
     * (see <a href="https://www.lua.org/manual/5.1/manual.html#luaL_error"><code>luaL_error</code></a>).
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_gc"><code>lua_gc</code></a>
     *
     * <pre><code>
     * [-0, +0, e]
     * </code></pre>
     *
     * <pre><code>
     * int lua_gc (lua_State *L, int what, int data);
     * </code></pre>
     *
     * <p>
     * Controls the garbage collector.
     * </p>
     *
     * <p>
     * This function performs several tasks,
     * according to the value of the parameter <code>what</code>:
     * </p>
     *
     * <ul>
     *
     * <li>
     * <b><code>LUA_GCSTOP</code>:</b>
     * stops the garbage collector.
     * </li>
     *
     * <li>
     * <b><code>LUA_GCRESTART</code>:</b>
     * restarts the garbage collector.
     * </li>
     *
     * <li>
     * <b><code>LUA_GCCOLLECT</code>:</b>
     * performs a full garbage-collection cycle.
     * </li>
     *
     * <li>
     * <b><code>LUA_GCCOUNT</code>:</b>
     * returns the current amount of memory (in Kbytes) in use by Lua.
     * </li>
     *
     * <li>
     * <b><code>LUA_GCCOUNTB</code>:</b>
     * returns the remainder of dividing the current amount of bytes of
     * memory in use by Lua by 1024.
     * </li>
     *
     * <li>
     * <b><code>LUA_GCSTEP</code>:</b>
     * performs an incremental step of garbage collection.
     * The step "size" is controlled by <code>data</code>
     * (larger values mean more steps) in a non-specified way.
     * If you want to control the step size
     * you must experimentally tune the value of <code>data</code>.
     * The function returns 1 if the step finished a
     * garbage-collection cycle.
     * </li>
     *
     * <li>
     * <b><code>LUA_GCSETPAUSE</code>:</b>
     * sets <code>data</code> as the new value
     * for the <em>pause</em> of the collector (see <a href="https://www.lua.org/manual/5.1/manual.html#2.10">&#167;2.10</a>).
     * The function returns the previous value of the pause.
     * </li>
     *
     * <li>
     * <b><code>LUA_GCSETSTEPMUL</code>:</b>
     * sets <code>data</code> as the new value for the <em>step multiplier</em> of
     * the collector (see <a href="https://www.lua.org/manual/5.1/manual.html#2.10">&#167;2.10</a>).
     * The function returns the previous value of the step multiplier.
     * </li>
     *
     * </ul>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param what what
     * @param data data
     * @return see description
     */
    public native int lua_gc(long ptr, int what, int data); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_gc((lua_State *) L, (int) what, (int) data);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_getfenv"><code>lua_getfenv</code></a>
     *
     * <pre><code>
     * [-0, +1, -]
     * </code></pre>
     *
     * <pre><code>
     * void lua_getfenv (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Pushes onto the stack the environment table of
     * the value at the given index.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     */
    public native void lua_getfenv(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        lua_getfenv((lua_State *) L, (int) index);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_getfield"><code>lua_getfield</code></a>
     *
     * <pre><code>
     * [-0, +1, e]
     * </code></pre>
     *
     * <pre><code>
     * void lua_getfield (lua_State *L, int index, const char *k);
     * </code></pre>
     *
     * <p>
     * Pushes onto the stack the value <code>t[k]</code>,
     * where <code>t</code> is the value at the given valid index.
     * As in Lua, this function may trigger a metamethod
     * for the "index" event (see <a href="https://www.lua.org/manual/5.1/manual.html#2.8">&#167;2.8</a>).
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @param k the field name
     */
    public native void lua_getfield(long ptr, int index, String k); /*
        lua_State * L = (lua_State *) ptr;

        lua_getfield((lua_State *) L, (int) index, (const char *) k);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_getfield"><code>lua_getfield</code></a>
     *
     * <pre><code>
     * [-0, +1, e]
     * </code></pre>
     *
     * <pre><code>
     * void lua_getfield (lua_State *L, int index, const char *k);
     * </code></pre>
     *
     * <p>
     * Pushes onto the stack the value <code>t[k]</code>,
     * where <code>t</code> is the value at the given valid index.
     * As in Lua, this function may trigger a metamethod
     * for the "index" event (see <a href="https://www.lua.org/manual/5.1/manual.html#2.8">&#167;2.8</a>).
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_getglobal"><code>lua_getglobal</code></a>
     *
     * <pre><code>
     * [-0, +1, e]
     * </code></pre>
     *
     * <pre><code>
     * void lua_getglobal (lua_State *L, const char *name);
     * </code></pre>
     *
     * <p>
     * Pushes onto the stack the value of the global <code>name</code>.
     * It is defined as a macro:
     * </p>
     *
     * <pre>
     *      #define lua_getglobal(L,s)  lua_getfield(L, LUA_GLOBALSINDEX, s)
     * </pre>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param name the name
     */
    public native void lua_getglobal(long ptr, String name); /*
        lua_State * L = (lua_State *) ptr;

        lua_getglobal((lua_State *) L, (const char *) name);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_getglobal"><code>lua_getglobal</code></a>
     *
     * <pre><code>
     * [-0, +1, e]
     * </code></pre>
     *
     * <pre><code>
     * void lua_getglobal (lua_State *L, const char *name);
     * </code></pre>
     *
     * <p>
     * Pushes onto the stack the value of the global <code>name</code>.
     * It is defined as a macro:
     * </p>
     *
     * <pre>
     *      #define lua_getglobal(L,s)  lua_getfield(L, LUA_GLOBALSINDEX, s)
     * </pre>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param name the name
     */
    public native void luaJ_getglobal(long ptr, String name); /*
        lua_State * L = (lua_State *) ptr;

        lua_getglobal((lua_State *) L, (const char *) name);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_getmetatable"><code>lua_getmetatable</code></a>
     *
     * <pre><code>
     * [-0, +(0|1), -]
     * </code></pre>
     *
     * <pre><code>
     * int lua_getmetatable (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Pushes onto the stack the metatable of the value at the given
     * acceptable index.
     * If the index is not valid,
     * or if the value does not have a metatable,
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_gettable"><code>lua_gettable</code></a>
     *
     * <pre><code>
     * [-1, +1, e]
     * </code></pre>
     *
     * <pre><code>
     * void lua_gettable (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Pushes onto the stack the value <code>t[k]</code>,
     * where <code>t</code> is the value at the given valid index
     * and <code>k</code> is the value at the top of the stack.
     * </p>
     *
     * <p>
     * This function pops the key from the stack
     * (putting the resulting value in its place).
     * As in Lua, this function may trigger a metamethod
     * for the "index" event (see <a href="https://www.lua.org/manual/5.1/manual.html#2.8">&#167;2.8</a>).
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     */
    public native void lua_gettable(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        lua_gettable((lua_State *) L, (int) index);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_gettable"><code>lua_gettable</code></a>
     *
     * <pre><code>
     * [-1, +1, e]
     * </code></pre>
     *
     * <pre><code>
     * void lua_gettable (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Pushes onto the stack the value <code>t[k]</code>,
     * where <code>t</code> is the value at the given valid index
     * and <code>k</code> is the value at the top of the stack.
     * </p>
     *
     * <p>
     * This function pops the key from the stack
     * (putting the resulting value in its place).
     * As in Lua, this function may trigger a metamethod
     * for the "index" event (see <a href="https://www.lua.org/manual/5.1/manual.html#2.8">&#167;2.8</a>).
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_gettop"><code>lua_gettop</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * int lua_gettop (lua_State *L);
     * </code></pre>
     *
     * <p>
     * Returns the index of the top element in the stack.
     * Because indices start at&#160;1,
     * this result is equal to the number of elements in the stack
     * (and so 0&#160;means an empty stack).
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_insert"><code>lua_insert</code></a>
     *
     * <pre><code>
     * [-1, +1, -]
     * </code></pre>
     *
     * <pre><code>
     * void lua_insert (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Moves the top element into the given valid index,
     * shifting up the elements above this index to open space.
     * Cannot be called with a pseudo-index,
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_isboolean"><code>lua_isboolean</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * int lua_isboolean (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the value at the given acceptable index has type boolean,
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_iscfunction"><code>lua_iscfunction</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * int lua_iscfunction (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the value at the given acceptable index is a C&#160;function,
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_isfunction"><code>lua_isfunction</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * int lua_isfunction (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the value at the given acceptable index is a function
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_islightuserdata"><code>lua_islightuserdata</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * int lua_islightuserdata (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the value at the given acceptable index is a light userdata,
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_isnil"><code>lua_isnil</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * int lua_isnil (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the value at the given acceptable index is <b>nil</b>,
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_isnone"><code>lua_isnone</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * int lua_isnone (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the given acceptable index is not valid
     * (that is, it refers to an element outside the current stack),
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_isnoneornil"><code>lua_isnoneornil</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * int lua_isnoneornil (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the given acceptable index is not valid
     * (that is, it refers to an element outside the current stack)
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_isnumber"><code>lua_isnumber</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * int lua_isnumber (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the value at the given acceptable index is a number
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_isstring"><code>lua_isstring</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * int lua_isstring (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the value at the given acceptable index is a string
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_istable"><code>lua_istable</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * int lua_istable (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the value at the given acceptable index is a table,
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_isthread"><code>lua_isthread</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * int lua_isthread (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the value at the given acceptable index is a thread,
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_isuserdata"><code>lua_isuserdata</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * int lua_isuserdata (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the value at the given acceptable index is a userdata
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_lessthan"><code>lua_lessthan</code></a>
     *
     * <pre><code>
     * [-0, +0, e]
     * </code></pre>
     *
     * <pre><code>
     * int lua_lessthan (lua_State *L, int index1, int index2);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the value at acceptable index <code>index1</code> is smaller
     * than the value at acceptable index <code>index2</code>,
     * following the semantics of the Lua <code>&lt;</code> operator
     * (that is, may call metamethods).
     * Otherwise returns&#160;0.
     * Also returns&#160;0 if any of the indices is non valid.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index1 the stack position of the first element
     * @param index2 the stack position of the second element
     * @return see description
     */
    public native int lua_lessthan(long ptr, int index1, int index2); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_lessthan((lua_State *) L, (int) index1, (int) index2);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_newtable"><code>lua_newtable</code></a>
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_newthread"><code>lua_newthread</code></a>
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
     * and returns a pointer to a <a href="https://www.lua.org/manual/5.1/manual.html#lua_State"><code>lua_State</code></a> that represents this new thread.
     * The new state returned by this function shares with the original state
     * all global objects (such as tables),
     * but has an independent execution stack.
     * </p>
     *
     * <p>
     * There is no explicit function to close or to destroy a thread.
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_newuserdata"><code>lua_newuserdata</code></a>
     *
     * <pre><code>
     * [-0, +1, m]
     * </code></pre>
     *
     * <pre><code>
     * void *lua_newuserdata (lua_State *L, size_t size);
     * </code></pre>
     *
     * <p>
     * This function allocates a new block of memory with the given size,
     * pushes onto the stack a new full userdata with the block address,
     * and returns this address.
     * </p>
     *
     * <p>
     * Userdata represent C&#160;values in Lua.
     * A <em>full userdata</em> represents a block of memory.
     * It is an object (like a table):
     * you must create it, it can have its own metatable,
     * and you can detect when it is being collected.
     * A full userdata is only equal to itself (under raw equality).
     * </p>
     *
     * <p>
     * When Lua collects a full userdata with a <code>gc</code> metamethod,
     * Lua calls the metamethod and marks the userdata as finalized.
     * When this userdata is collected again then
     * Lua frees its corresponding memory.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param size size
     * @return see description
     */
    public native long lua_newuserdata(long ptr, long size); /*
        lua_State * L = (lua_State *) ptr;

        jlong returnValueReceiver = (jlong) lua_newuserdata((lua_State *) L, (size_t) size);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_next"><code>lua_next</code></a>
     *
     * <pre><code>
     * [-1, +(2|0), e]
     * </code></pre>
     *
     * <pre><code>
     * int lua_next (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Pops a key from the stack,
     * and pushes a key-value pair from the table at the given index
     * (the "next" pair after the given key).
     * If there are no more elements in the table,
     * then <a href="https://www.lua.org/manual/5.1/manual.html#lua_next"><code>lua_next</code></a> returns 0 (and pushes nothing).
     * </p>
     *
     * <p>
     * A typical traversal looks like this:
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
     * do not call <a href="https://www.lua.org/manual/5.1/manual.html#lua_tolstring"><code>lua_tolstring</code></a> directly on a key,
     * unless you know that the key is actually a string.
     * Recall that <a href="https://www.lua.org/manual/5.1/manual.html#lua_tolstring"><code>lua_tolstring</code></a> <em>changes</em>
     * the value at the given index;
     * this confuses the next call to <a href="https://www.lua.org/manual/5.1/manual.html#lua_next"><code>lua_next</code></a>.
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_objlen"><code>lua_objlen</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * size_t lua_objlen (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns the "length" of the value at the given acceptable index:
     * for strings, this is the string length;
     * for tables, this is the result of the length operator ('<code>#</code>');
     * for userdata, this is the size of the block of memory allocated
     * for the userdata;
     * for other values, it is&#160;0.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native long lua_objlen(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jlong returnValueReceiver = (jlong) lua_objlen((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_pcall"><code>lua_pcall</code></a>
     *
     * <pre><code>
     * [-(nargs + 1), +(nresults|1), -]
     * </code></pre>
     *
     * <pre><code>
     * int lua_pcall (lua_State *L, int nargs, int nresults, int errfunc);
     * </code></pre>
     *
     * <p>
     * Calls a function in protected mode.
     * </p>
     *
     * <p>
     * Both <code>nargs</code> and <code>nresults</code> have the same meaning as
     * in <a href="https://www.lua.org/manual/5.1/manual.html#lua_call"><code>lua_call</code></a>.
     * If there are no errors during the call,
     * <a href="https://www.lua.org/manual/5.1/manual.html#lua_pcall"><code>lua_pcall</code></a> behaves exactly like <a href="https://www.lua.org/manual/5.1/manual.html#lua_call"><code>lua_call</code></a>.
     * However, if there is any error,
     * <a href="https://www.lua.org/manual/5.1/manual.html#lua_pcall"><code>lua_pcall</code></a> catches it,
     * pushes a single value on the stack (the error message),
     * and returns an error code.
     * Like <a href="https://www.lua.org/manual/5.1/manual.html#lua_call"><code>lua_call</code></a>,
     * <a href="https://www.lua.org/manual/5.1/manual.html#lua_pcall"><code>lua_pcall</code></a> always removes the function
     * and its arguments from the stack.
     * </p>
     *
     * <p>
     * If <code>errfunc</code> is 0,
     * then the error message returned on the stack
     * is exactly the original error message.
     * Otherwise, <code>errfunc</code> is the stack index of an
     * <em>error handler function</em>.
     * (In the current implementation, this index cannot be a pseudo-index.)
     * In case of runtime errors,
     * this function will be called with the error message
     * and its return value will be the message returned on the stack by <a href="https://www.lua.org/manual/5.1/manual.html#lua_pcall"><code>lua_pcall</code></a>.
     * </p>
     *
     * <p>
     * Typically, the error handler function is used to add more debug
     * information to the error message, such as a stack traceback.
     * Such information cannot be gathered after the return of <a href="https://www.lua.org/manual/5.1/manual.html#lua_pcall"><code>lua_pcall</code></a>,
     * since by then the stack has unwound.
     * </p>
     *
     * <p>
     * The <a href="https://www.lua.org/manual/5.1/manual.html#lua_pcall"><code>lua_pcall</code></a> function returns 0 in case of success
     * or one of the following error codes
     * (defined in <code>lua.h</code>):
     * </p>
     *
     * <ul>
     *
     * <li>
     * <b><a><code>LUA_ERRRUN</code></a>:</b>
     * a runtime error.
     * </li>
     *
     * <li>
     * <b><a><code>LUA_ERRMEM</code></a>:</b>
     * memory allocation error.
     * For such errors, Lua does not call the error handler function.
     * </li>
     *
     * <li>
     * <b><a><code>LUA_ERRERR</code></a>:</b>
     * error while running the error handler function.
     * </li>
     *
     * </ul>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param nargs the number of arguments that you pushed onto the stack
     * @param nresults the number of results, or <code>LUA_MULTRET</code>
     * @param errfunc 0 or the stack index of an error handler function
     * @return see description
     */
    public native int lua_pcall(long ptr, int nargs, int nresults, int errfunc); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_pcall((lua_State *) L, (int) nargs, (int) nresults, (int) errfunc);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_pop"><code>lua_pop</code></a>
     *
     * <pre><code>
     * [-n, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * void lua_pop (lua_State *L, int n);
     * </code></pre>
     *
     * <p>
     * Pops <code>n</code> elements from the stack.
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_pushboolean"><code>lua_pushboolean</code></a>
     *
     * <pre><code>
     * [-0, +1, -]
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_pushinteger"><code>lua_pushinteger</code></a>
     *
     * <pre><code>
     * [-0, +1, -]
     * </code></pre>
     *
     * <pre><code>
     * void lua_pushinteger (lua_State *L, lua_Integer n);
     * </code></pre>
     *
     * <p>
     * Pushes a number with value <code>n</code> onto the stack.
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_pushlightuserdata"><code>lua_pushlightuserdata</code></a>
     *
     * <pre><code>
     * [-0, +1, -]
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
     * A <em>light userdata</em> represents a pointer.
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_pushnil"><code>lua_pushnil</code></a>
     *
     * <pre><code>
     * [-0, +1, -]
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_pushnumber"><code>lua_pushnumber</code></a>
     *
     * <pre><code>
     * [-0, +1, -]
     * </code></pre>
     *
     * <pre><code>
     * void lua_pushnumber (lua_State *L, lua_Number n);
     * </code></pre>
     *
     * <p>
     * Pushes a number with value <code>n</code> onto the stack.
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_pushstring"><code>lua_pushstring</code></a>
     *
     * <pre><code>
     * [-0, +1, m]
     * </code></pre>
     *
     * <pre><code>
     * void lua_pushstring (lua_State *L, const char *s);
     * </code></pre>
     *
     * <p>
     * Pushes the zero-terminated string pointed to by <code>s</code>
     * onto the stack.
     * Lua makes (or reuses) an internal copy of the given string,
     * so the memory at <code>s</code> can be freed or reused immediately after
     * the function returns.
     * The string cannot contain embedded zeros;
     * it is assumed to end at the first zero.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param s the string
     */
    public native void lua_pushstring(long ptr, String s); /*
        lua_State * L = (lua_State *) ptr;

        lua_pushstring((lua_State *) L, (const char *) s);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_pushstring"><code>lua_pushstring</code></a>
     *
     * <pre><code>
     * [-0, +1, m]
     * </code></pre>
     *
     * <pre><code>
     * void lua_pushstring (lua_State *L, const char *s);
     * </code></pre>
     *
     * <p>
     * Pushes the zero-terminated string pointed to by <code>s</code>
     * onto the stack.
     * Lua makes (or reuses) an internal copy of the given string,
     * so the memory at <code>s</code> can be freed or reused immediately after
     * the function returns.
     * The string cannot contain embedded zeros;
     * it is assumed to end at the first zero.
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_pushthread"><code>lua_pushthread</code></a>
     *
     * <pre><code>
     * [-0, +1, -]
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_pushvalue"><code>lua_pushvalue</code></a>
     *
     * <pre><code>
     * [-0, +1, -]
     * </code></pre>
     *
     * <pre><code>
     * void lua_pushvalue (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Pushes a copy of the element at the given valid index
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_rawequal"><code>lua_rawequal</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * int lua_rawequal (lua_State *L, int index1, int index2);
     * </code></pre>
     *
     * <p>
     * Returns 1 if the two values in acceptable indices <code>index1</code> and
     * <code>index2</code> are primitively equal
     * (that is, without calling metamethods).
     * Otherwise returns&#160;0.
     * Also returns&#160;0 if any of the indices are non valid.
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_rawget"><code>lua_rawget</code></a>
     *
     * <pre><code>
     * [-1, +1, -]
     * </code></pre>
     *
     * <pre><code>
     * void lua_rawget (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Similar to <a href="https://www.lua.org/manual/5.1/manual.html#lua_gettable"><code>lua_gettable</code></a>, but does a raw access
     * (i.e., without metamethods).
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     */
    public native void lua_rawget(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        lua_rawget((lua_State *) L, (int) index);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_rawget"><code>lua_rawget</code></a>
     *
     * <pre><code>
     * [-1, +1, -]
     * </code></pre>
     *
     * <pre><code>
     * void lua_rawget (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Similar to <a href="https://www.lua.org/manual/5.1/manual.html#lua_gettable"><code>lua_gettable</code></a>, but does a raw access
     * (i.e., without metamethods).
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_rawgeti"><code>lua_rawgeti</code></a>
     *
     * <pre><code>
     * [-0, +1, -]
     * </code></pre>
     *
     * <pre><code>
     * void lua_rawgeti (lua_State *L, int index, int n);
     * </code></pre>
     *
     * <p>
     * Pushes onto the stack the value <code>t[n]</code>,
     * where <code>t</code> is the value at the given valid index.
     * The access is raw;
     * that is, it does not invoke metamethods.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @param n the number of elements
     */
    public native void lua_rawgeti(long ptr, int index, int n); /*
        lua_State * L = (lua_State *) ptr;

        lua_rawgeti((lua_State *) L, (int) index, (int) n);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_rawgeti"><code>lua_rawgeti</code></a>
     *
     * <pre><code>
     * [-0, +1, -]
     * </code></pre>
     *
     * <pre><code>
     * void lua_rawgeti (lua_State *L, int index, int n);
     * </code></pre>
     *
     * <p>
     * Pushes onto the stack the value <code>t[n]</code>,
     * where <code>t</code> is the value at the given valid index.
     * The access is raw;
     * that is, it does not invoke metamethods.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @param n the number of elements
     */
    public native void luaJ_rawgeti(long ptr, int index, int n); /*
        lua_State * L = (lua_State *) ptr;

        lua_rawgeti((lua_State *) L, (int) index, (int) n);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_rawset"><code>lua_rawset</code></a>
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
     * Similar to <a href="https://www.lua.org/manual/5.1/manual.html#lua_settable"><code>lua_settable</code></a>, but does a raw assignment
     * (i.e., without metamethods).
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_rawseti"><code>lua_rawseti</code></a>
     *
     * <pre><code>
     * [-1, +0, m]
     * </code></pre>
     *
     * <pre><code>
     * void lua_rawseti (lua_State *L, int index, int n);
     * </code></pre>
     *
     * <p>
     * Does the equivalent of <code>t[n] = v</code>,
     * where <code>t</code> is the value at the given valid index
     * and <code>v</code> is the value at the top of the stack.
     * </p>
     *
     * <p>
     * This function pops the value from the stack.
     * The assignment is raw;
     * that is, it does not invoke metamethods.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @param n the number of elements
     */
    public native void lua_rawseti(long ptr, int index, int n); /*
        lua_State * L = (lua_State *) ptr;

        lua_rawseti((lua_State *) L, (int) index, (int) n);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_remove"><code>lua_remove</code></a>
     *
     * <pre><code>
     * [-1, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * void lua_remove (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Removes the element at the given valid index,
     * shifting down the elements above this index to fill the gap.
     * Cannot be called with a pseudo-index,
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_replace"><code>lua_replace</code></a>
     *
     * <pre><code>
     * [-1, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * void lua_replace (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Moves the top element into the given position (and pops it),
     * without shifting any element
     * (therefore replacing the value at the given position).
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_resume"><code>lua_resume</code></a>
     *
     * <pre><code>
     * [-?, +?, -]
     * </code></pre>
     *
     * <pre><code>
     * int lua_resume (lua_State *L, int narg);
     * </code></pre>
     *
     * <p>
     * Starts and resumes a coroutine in a given thread.
     * </p>
     *
     * <p>
     * To start a coroutine, you first create a new thread
     * (see <a href="https://www.lua.org/manual/5.1/manual.html#lua_newthread"><code>lua_newthread</code></a>);
     * then you push onto its stack the main function plus any arguments;
     * then you call <a href="https://www.lua.org/manual/5.1/manual.html#lua_resume"><code>lua_resume</code></a>,
     * with <code>narg</code> being the number of arguments.
     * This call returns when the coroutine suspends or finishes its execution.
     * When it returns, the stack contains all values passed to <a href="https://www.lua.org/manual/5.1/manual.html#lua_yield"><code>lua_yield</code></a>,
     * or all values returned by the body function.
     * <a href="https://www.lua.org/manual/5.1/manual.html#lua_resume"><code>lua_resume</code></a> returns
     * <a href="https://www.lua.org/manual/5.1/manual.html#pdf-LUA_YIELD"><code>LUA_YIELD</code></a> if the coroutine yields,
     * 0 if the coroutine finishes its execution
     * without errors,
     * or an error code in case of errors (see <a href="https://www.lua.org/manual/5.1/manual.html#lua_pcall"><code>lua_pcall</code></a>).
     * In case of errors,
     * the stack is not unwound,
     * so you can use the debug API over it.
     * The error message is on the top of the stack.
     * To restart a coroutine, you put on its stack only the values to
     * be passed as results from <code>yield</code>,
     * and then call <a href="https://www.lua.org/manual/5.1/manual.html#lua_resume"><code>lua_resume</code></a>.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param narg the number of arguments
     * @return see description
     */
    public native int lua_resume(long ptr, int narg); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_resume((lua_State *) L, (int) narg);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_setfenv"><code>lua_setfenv</code></a>
     *
     * <pre><code>
     * [-1, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * int lua_setfenv (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Pops a table from the stack and sets it as
     * the new environment for the value at the given index.
     * If the value at the given index is
     * neither a function nor a thread nor a userdata,
     * <a href="https://www.lua.org/manual/5.1/manual.html#lua_setfenv"><code>lua_setfenv</code></a> returns 0.
     * Otherwise it returns 1.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param index the stack position of the element
     * @return see description
     */
    public native int lua_setfenv(long ptr, int index); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) lua_setfenv((lua_State *) L, (int) index);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_setfield"><code>lua_setfield</code></a>
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
     * where <code>t</code> is the value at the given valid index
     * and <code>v</code> is the value at the top of the stack.
     * </p>
     *
     * <p>
     * This function pops the value from the stack.
     * As in Lua, this function may trigger a metamethod
     * for the "newindex" event (see <a href="https://www.lua.org/manual/5.1/manual.html#2.8">&#167;2.8</a>).
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_setglobal"><code>lua_setglobal</code></a>
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
     * It is defined as a macro:
     * </p>
     *
     * <pre>
     *      #define lua_setglobal(L,s)   lua_setfield(L, LUA_GLOBALSINDEX, s)
     * </pre>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param name the name
     */
    public native void lua_setglobal(long ptr, String name); /*
        lua_State * L = (lua_State *) ptr;

        lua_setglobal((lua_State *) L, (const char *) name);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_setmetatable"><code>lua_setmetatable</code></a>
     *
     * <pre><code>
     * [-1, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * int lua_setmetatable (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Pops a table from the stack and
     * sets it as the new metatable for the value at the given
     * acceptable index.
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_setmetatable"><code>lua_setmetatable</code></a>
     *
     * <pre><code>
     * [-1, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * int lua_setmetatable (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Pops a table from the stack and
     * sets it as the new metatable for the value at the given
     * acceptable index.
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_settable"><code>lua_settable</code></a>
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
     * where <code>t</code> is the value at the given valid index,
     * <code>v</code> is the value at the top of the stack,
     * and <code>k</code> is the value just below the top.
     * </p>
     *
     * <p>
     * This function pops both the key and the value from the stack.
     * As in Lua, this function may trigger a metamethod
     * for the "newindex" event (see <a href="https://www.lua.org/manual/5.1/manual.html#2.8">&#167;2.8</a>).
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_settop"><code>lua_settop</code></a>
     *
     * <pre><code>
     * [-?, +?, -]
     * </code></pre>
     *
     * <pre><code>
     * void lua_settop (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Accepts any acceptable index, or&#160;0,
     * and sets the stack top to this index.
     * If the new top is larger than the old one,
     * then the new elements are filled with <b>nil</b>.
     * If <code>index</code> is&#160;0, then all stack elements are removed.
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_status"><code>lua_status</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
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
     * The status can be 0 for a normal thread,
     * an error code if the thread finished its execution with an error,
     * or <a><code>LUA_YIELD</code></a> if the thread is suspended.
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_toboolean"><code>lua_toboolean</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * int lua_toboolean (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Converts the Lua value at the given acceptable index to a C&#160;boolean
     * value (0&#160;or&#160;1).
     * Like all tests in Lua,
     * <a href="https://www.lua.org/manual/5.1/manual.html#lua_toboolean"><code>lua_toboolean</code></a> returns 1 for any Lua value
     * different from <b>false</b> and <b>nil</b>;
     * otherwise it returns 0.
     * It also returns 0 when called with a non-valid index.
     * (If you want to accept only actual boolean values,
     * use <a href="https://www.lua.org/manual/5.1/manual.html#lua_isboolean"><code>lua_isboolean</code></a> to test the value's type.)
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_tointeger"><code>lua_tointeger</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * lua_Integer lua_tointeger (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Converts the Lua value at the given acceptable index
     * to the signed integral type <a href="https://www.lua.org/manual/5.1/manual.html#lua_Integer"><code>lua_Integer</code></a>.
     * The Lua value must be a number or a string convertible to a number
     * (see <a href="https://www.lua.org/manual/5.1/manual.html#2.2.1">&#167;2.2.1</a>);
     * otherwise, <a href="https://www.lua.org/manual/5.1/manual.html#lua_tointeger"><code>lua_tointeger</code></a> returns&#160;0.
     * </p>
     *
     * <p>
     * If the number is not an integer,
     * it is truncated in some non-specified way.
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_tonumber"><code>lua_tonumber</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * lua_Number lua_tonumber (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Converts the Lua value at the given acceptable index
     * to the C&#160;type <a href="https://www.lua.org/manual/5.1/manual.html#lua_Number"><code>lua_Number</code></a> (see <a href="https://www.lua.org/manual/5.1/manual.html#lua_Number"><code>lua_Number</code></a>).
     * The Lua value must be a number or a string convertible to a number
     * (see <a href="https://www.lua.org/manual/5.1/manual.html#2.2.1">&#167;2.2.1</a>);
     * otherwise, <a href="https://www.lua.org/manual/5.1/manual.html#lua_tonumber"><code>lua_tonumber</code></a> returns&#160;0.
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_topointer"><code>lua_topointer</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * const void *lua_topointer (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Converts the value at the given acceptable index to a generic
     * C&#160;pointer (<code>void*</code>).
     * The value can be a userdata, a table, a thread, or a function;
     * otherwise, <a href="https://www.lua.org/manual/5.1/manual.html#lua_topointer"><code>lua_topointer</code></a> returns <code>NULL</code>.
     * Different objects will give different pointers.
     * There is no way to convert the pointer back to its original value.
     * </p>
     *
     * <p>
     * Typically this function is used only for debug information.
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_tostring"><code>lua_tostring</code></a>
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
     * Equivalent to <a href="https://www.lua.org/manual/5.1/manual.html#lua_tolstring"><code>lua_tolstring</code></a> with <code>len</code> equal to <code>NULL</code>.
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_tothread"><code>lua_tothread</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * lua_State *lua_tothread (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Converts the value at the given acceptable index to a Lua thread
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_touserdata"><code>lua_touserdata</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * void *lua_touserdata (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * If the value at the given acceptable index is a full userdata,
     * returns its block address.
     * If the value is a light userdata,
     * returns its pointer.
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_type"><code>lua_type</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * int lua_type (lua_State *L, int index);
     * </code></pre>
     *
     * <p>
     * Returns the type of the value in the given acceptable index,
     * or <code>LUA_TNONE</code> for a non-valid index
     * (that is, an index to an "empty" stack position).
     * The types returned by <a href="https://www.lua.org/manual/5.1/manual.html#lua_type"><code>lua_type</code></a> are coded by the following constants
     * defined in <code>lua.h</code>:
     * <code>LUA_TNIL</code>,
     * <code>LUA_TNUMBER</code>,
     * <code>LUA_TBOOLEAN</code>,
     * <code>LUA_TSTRING</code>,
     * <code>LUA_TTABLE</code>,
     * <code>LUA_TFUNCTION</code>,
     * <code>LUA_TUSERDATA</code>,
     * <code>LUA_TTHREAD</code>,
     * and
     * <code>LUA_TLIGHTUSERDATA</code>.
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_typename"><code>lua_typename</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * const char *lua_typename  (lua_State *L, int tp);
     * </code></pre>
     *
     * <p>
     * Returns the name of the type encoded by the value <code>tp</code>,
     * which must be one the values returned by <a href="https://www.lua.org/manual/5.1/manual.html#lua_type"><code>lua_type</code></a>.
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_xmove"><code>lua_xmove</code></a>
     *
     * <pre><code>
     * [-?, +?, -]
     * </code></pre>
     *
     * <pre><code>
     * void lua_xmove (lua_State *from, lua_State *to, int n);
     * </code></pre>
     *
     * <p>
     * Exchange values between different threads of the <em>same</em> global state.
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_yield"><code>lua_yield</code></a>
     *
     * <pre><code>
     * [-?, +?, -]
     * </code></pre>
     *
     * <pre><code>
     * int lua_yield  (lua_State *L, int nresults);
     * </code></pre>
     *
     * <p>
     * Yields a coroutine.
     * </p>
     *
     * <p>
     * This function should only be called as the
     * return expression of a C&#160;function, as follows:
     * </p>
     *
     * <pre>
     *      return lua_yield (L, nresults);
     * </pre>
     *
     * <p>
     * When a C&#160;function calls <a href="https://www.lua.org/manual/5.1/manual.html#lua_yield"><code>lua_yield</code></a> in that way,
     * the running coroutine suspends its execution,
     * and the call to <a href="https://www.lua.org/manual/5.1/manual.html#lua_resume"><code>lua_resume</code></a> that started this coroutine returns.
     * The parameter <code>nresults</code> is the number of values from the stack
     * that are passed as results to <a href="https://www.lua.org/manual/5.1/manual.html#lua_resume"><code>lua_resume</code></a>.
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_gethookcount"><code>lua_gethookcount</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_gethookmask"><code>lua_gethookmask</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_getupvalue"><code>lua_getupvalue</code></a>
     *
     * <pre><code>
     * [-0, +(0|1), -]
     * </code></pre>
     *
     * <pre><code>
     * const char *lua_getupvalue (lua_State *L, int funcindex, int n);
     * </code></pre>
     *
     * <p>
     * Gets information about a closure's upvalue.
     * (For Lua functions,
     * upvalues are the external local variables that the function uses,
     * and that are consequently included in its closure.)
     * <a href="https://www.lua.org/manual/5.1/manual.html#lua_getupvalue"><code>lua_getupvalue</code></a> gets the index <code>n</code> of an upvalue,
     * pushes the upvalue's value onto the stack,
     * and returns its name.
     * <code>funcindex</code> points to the closure in the stack.
     * (Upvalues have no particular order,
     * as they are active through the whole function.
     * So, they are numbered in an arbitrary order.)
     * </p>
     *
     * <p>
     * Returns <code>NULL</code> (and pushes nothing)
     * when the index is greater than the number of upvalues.
     * For C&#160;functions, this function uses the empty string <code>""</code>
     * as a name for all upvalues.
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#lua_setupvalue"><code>lua_setupvalue</code></a>
     *
     * <pre><code>
     * [-(0|1), +0, -]
     * </code></pre>
     *
     * <pre><code>
     * const char *lua_setupvalue (lua_State *L, int funcindex, int n);
     * </code></pre>
     *
     * <p>
     * Sets the value of a closure's upvalue.
     * It assigns the value at the top of the stack
     * to the upvalue and returns its name.
     * It also pops the value from the stack.
     * Parameters <code>funcindex</code> and <code>n</code> are as in the <a href="https://www.lua.org/manual/5.1/manual.html#lua_getupvalue"><code>lua_getupvalue</code></a>
     * (see <a href="https://www.lua.org/manual/5.1/manual.html#lua_getupvalue"><code>lua_getupvalue</code></a>).
     * </p>
     *
     * <p>
     * Returns <code>NULL</code> (and pops nothing)
     * when the index is greater than the number of upvalues.
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#luaL_callmeta"><code>luaL_callmeta</code></a>
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
     * this function calls this field and passes the object as its only argument.
     * In this case this function returns 1 and pushes onto the
     * stack the value returned by the call.
     * If there is no metatable or no metamethod,
     * this function returns 0 (without pushing any value on the stack).
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#luaL_dostring"><code>luaL_dostring</code></a>
     *
     * <pre><code>
     * [-0, +?, m]
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
     * It returns 0 if there are no errors
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#luaL_getmetafield"><code>luaL_getmetafield</code></a>
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
     * of the object at index <code>obj</code>.
     * If the object does not have a metatable,
     * or if the metatable does not have this field,
     * returns 0 and pushes nothing.
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#luaL_getmetatable"><code>luaL_getmetatable</code></a>
     *
     * <pre><code>
     * [-0, +1, -]
     * </code></pre>
     *
     * <pre><code>
     * void luaL_getmetatable (lua_State *L, const char *tname);
     * </code></pre>
     *
     * <p>
     * Pushes onto the stack the metatable associated with name <code>tname</code>
     * in the registry (see <a href="https://www.lua.org/manual/5.1/manual.html#luaL_newmetatable"><code>luaL_newmetatable</code></a>).
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param tname type name
     */
    public native void luaL_getmetatable(long ptr, String tname); /*
        lua_State * L = (lua_State *) ptr;

        luaL_getmetatable((lua_State *) L, (const char *) tname);
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#luaL_getmetatable"><code>luaL_getmetatable</code></a>
     *
     * <pre><code>
     * [-0, +1, -]
     * </code></pre>
     *
     * <pre><code>
     * void luaL_getmetatable (lua_State *L, const char *tname);
     * </code></pre>
     *
     * <p>
     * Pushes onto the stack the metatable associated with name <code>tname</code>
     * in the registry (see <a href="https://www.lua.org/manual/5.1/manual.html#luaL_newmetatable"><code>luaL_newmetatable</code></a>).
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#luaL_gsub"><code>luaL_gsub</code></a>
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
     * Creates a copy of string <code>s</code> by replacing
     * any occurrence of the string <code>p</code>
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#luaL_loadstring"><code>luaL_loadstring</code></a>
     *
     * <pre><code>
     * [-0, +1, m]
     * </code></pre>
     *
     * <pre><code>
     * int luaL_loadstring (lua_State *L, const char *s);
     * </code></pre>
     *
     * <p>
     * Loads a string as a Lua chunk.
     * This function uses <a href="https://www.lua.org/manual/5.1/manual.html#lua_load"><code>lua_load</code></a> to load the chunk in
     * the zero-terminated string <code>s</code>.
     * </p>
     *
     * <p>
     * This function returns the same results as <a href="https://www.lua.org/manual/5.1/manual.html#lua_load"><code>lua_load</code></a>.
     * </p>
     *
     * <p>
     * Also as <a href="https://www.lua.org/manual/5.1/manual.html#lua_load"><code>lua_load</code></a>, this function only loads the chunk;
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#luaL_newmetatable"><code>luaL_newmetatable</code></a>
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
     * adds it to the registry with key <code>tname</code>,
     * and returns 1.
     * </p>
     *
     * <p>
     * In both cases pushes onto the stack the final value associated
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#luaL_newstate"><code>luaL_newstate</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * lua_State *luaL_newstate (void);
     * </code></pre>
     *
     * <p>
     * Creates a new Lua state.
     * It calls <a href="https://www.lua.org/manual/5.1/manual.html#lua_newstate"><code>lua_newstate</code></a> with an
     * allocator based on the standard&#160;C <code>realloc</code> function
     * and then sets a panic function (see <a href="https://www.lua.org/manual/5.1/manual.html#lua_atpanic"><code>lua_atpanic</code></a>) that prints
     * an error message to the standard error output in case of fatal
     * errors.
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#luaL_openlibs"><code>luaL_openlibs</code></a>
     *
     * <pre><code>
     * [-0, +0, m]
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#luaL_ref"><code>luaL_ref</code></a>
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
     * for the object at the top of the stack (and pops the object).
     * </p>
     *
     * <p>
     * A reference is a unique integer key.
     * As long as you do not manually add integer keys into table <code>t</code>,
     * <a href="https://www.lua.org/manual/5.1/manual.html#luaL_ref"><code>luaL_ref</code></a> ensures the uniqueness of the key it returns.
     * You can retrieve an object referred by reference <code>r</code>
     * by calling <code>lua_rawgeti(L, t, r)</code>.
     * Function <a href="https://www.lua.org/manual/5.1/manual.html#luaL_unref"><code>luaL_unref</code></a> frees a reference and its associated object.
     * </p>
     *
     * <p>
     * If the object at the top of the stack is <b>nil</b>,
     * <a href="https://www.lua.org/manual/5.1/manual.html#luaL_ref"><code>luaL_ref</code></a> returns the constant <a><code>LUA_REFNIL</code></a>.
     * The constant <a><code>LUA_NOREF</code></a> is guaranteed to be different
     * from any reference returned by <a href="https://www.lua.org/manual/5.1/manual.html#luaL_ref"><code>luaL_ref</code></a>.
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#luaL_typename"><code>luaL_typename</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#luaL_typerror"><code>luaL_typerror</code></a>
     *
     * <pre><code>
     * [-0, +0, v]
     * </code></pre>
     *
     * <pre><code>
     * int luaL_typerror (lua_State *L, int narg, const char *tname);
     * </code></pre>
     *
     * <p>
     * Generates an error with a message like the following:
     * </p>
     *
     * <pre>
     *      <em>location</em>: bad argument <em>narg</em> to '<em>func</em>' (<em>tname</em> expected, got <em>rt</em>)
     * </pre>
     *
     * <p>
     * where <code><em>location</em></code> is produced by <a href="https://www.lua.org/manual/5.1/manual.html#luaL_where"><code>luaL_where</code></a>,
     * <code><em>func</em></code> is the name of the current function,
     * and <code><em>rt</em></code> is the type name of the actual argument.
     * </p>
     *
     * @param ptr the <code>lua_State*</code> pointer
     * @param narg the number of arguments
     * @param tname type name
     * @return see description
     */
    public native int luaL_typerror(long ptr, int narg, String tname); /*
        lua_State * L = (lua_State *) ptr;

        jint returnValueReceiver = (jint) luaL_typerror((lua_State *) L, (int) narg, (const char *) tname);
        return returnValueReceiver;
    */


    /**
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#luaL_unref"><code>luaL_unref</code></a>
     *
     * <pre><code>
     * [-0, +0, -]
     * </code></pre>
     *
     * <pre><code>
     * void luaL_unref (lua_State *L, int t, int ref);
     * </code></pre>
     *
     * <p>
     * Releases reference <code>ref</code> from the table at index <code>t</code>
     * (see <a href="https://www.lua.org/manual/5.1/manual.html#luaL_ref"><code>luaL_ref</code></a>).
     * The entry is removed from the table,
     * so that the referred object can be collected.
     * The reference <code>ref</code> is also freed to be used again.
     * </p>
     *
     * <p>
     * If <code>ref</code> is <a href="https://www.lua.org/manual/5.1/manual.html#pdf-LUA_NOREF"><code>LUA_NOREF</code></a> or <a href="https://www.lua.org/manual/5.1/manual.html#pdf-LUA_REFNIL"><code>LUA_REFNIL</code></a>,
     * <a href="https://www.lua.org/manual/5.1/manual.html#luaL_unref"><code>luaL_unref</code></a> does nothing.
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
     * Wrapper of <a href="https://www.lua.org/manual/5.1/manual.html#luaL_where"><code>luaL_where</code></a>
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
