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

package party.iroiro.luajava;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import party.iroiro.luajava.cleaner.LuaReferable;
import party.iroiro.luajava.cleaner.LuaReference;
import party.iroiro.luajava.util.ClassUtils;
import party.iroiro.luajava.util.Type;
import party.iroiro.luajava.value.*;

import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An implementation that relies on {@link LuaNatives} for most of the features independent of Lua versions
 */
public abstract class AbstractLua implements Lua {
    private final static Object[] EMPTY = new Object[0];
    protected final static LuaInstances<AbstractLua> instances = new LuaInstances<>();
    protected volatile ExternalLoader loader;
    protected volatile LuaValue requireFunction;
    protected final ReferenceQueue<LuaReferable> recyclableReferences;
    protected final ConcurrentHashMap<Integer, LuaReference<?>> recordedReferences;

    static AbstractLua getInstance(int lid) {
        return instances.get(lid);
    }

    protected final LuaNatives C;
    protected final long L;
    protected final int id;
    protected final AbstractLua mainThread;
    protected final List<Lua> subThreads;

    /**
     * Creates a new Lua (main) state
     *
     * @param luaNative the Lua native wrapper
     */
    protected AbstractLua(LuaNatives luaNative) {
        this.C = luaNative;
        id = instances.add(this);
        L = luaNative.luaL_newstate(id);
        mainThread = this;
        subThreads = new LinkedList<>();
        loader = null;
        requireFunction = null;
        recyclableReferences = new ReferenceQueue<>();
        recordedReferences = new ConcurrentHashMap<>();
    }

    /**
     * Adopts a Lua sub-state, wrapping it up with the Lua interface
     *
     * @param luaNative  the Lua native wrapper
     * @param L          the new Lua state pointer
     * @param id         the Lua state id (see {@link LuaInstances})
     * @param mainThread the main state of this sub-state
     */
    protected AbstractLua(LuaNatives luaNative, long L, int id, @NotNull AbstractLua mainThread) {
        loader = null;
        this.C = luaNative;
        this.L = L;
        this.mainThread = mainThread;
        this.id = id;
        subThreads = null;
        recyclableReferences = null;
        recordedReferences = null;
    }

    /**
     * Adopts a created sub-state
     *
     * @param mainId the main Lua state
     * @param ptr    the pointer to the newly created Lua state
     * @return the Lua state id for the new state
     */
    static int adopt(int mainId, long ptr) {
        AbstractLua lua = getInstance(mainId);
        LuaInstances.Token<AbstractLua> token = instances.add();
        AbstractLua child = lua.newThread(ptr, token.id, lua);
        lua.addSubThread(child);
        token.setter.accept(child);
        return token.id;
    }

    @Override
    public void checkStack(int extra) throws RuntimeException {
        recycleReferences();
        if (C.lua_checkstack(L, extra) == 0) {
            throw new RuntimeException("No more stack space available");
        }
    }

    @Override
    public void push(@Nullable Object object, Conversion degree) {
        checkStack(1);
        if (object == null) {
            pushNil();
        } else if (object instanceof LuaValue) {
            LuaValue value = (LuaValue) object;
            value.push(this);
        } else if (object instanceof LuaFunction) {
            LuaFunction function = (LuaFunction) object;
            this.push(function);
        } else if (degree == Conversion.NONE) {
            pushJavaObjectOrArray(object);
        } else {
            if (object instanceof Boolean) {
                push((boolean) object);
            } else if (object instanceof String) {
                push((String) object);
            } else if (object instanceof Integer || object instanceof Byte || object instanceof Short) {
                push(((Number) object).intValue());
            } else if (object instanceof Character) {
                push(((int) (Character) object));
            } else if (object instanceof Long) {
                push((long) object);
            } else if (object instanceof Float || object instanceof Double) {
                push((Number) object);
            } else if (object instanceof JFunction) {
                push(((JFunction) object));
            } else if (degree == Conversion.SEMI) {
                pushJavaObjectOrArray(object);
            } else /* if (degree == Conversion.FULL) */ {
                if (object instanceof Class) {
                    pushJavaClass(((Class<?>) object));
                } else if (object instanceof Map) {
                    push((Map<?, ?>) object);
                } else if (object instanceof Collection) {
                    push((Collection<?>) object);
                } else if (object.getClass().isArray()) {
                    pushArray(object);
                } else if (object instanceof ByteBuffer) {
                    push((ByteBuffer) object);
                } else {
                    pushJavaObject(object);
                }
            }
        }
    }

    protected void pushJavaObjectOrArray(Object object) {
        checkStack(1);
        if (object.getClass().isArray()) {
            pushJavaArray(object);
        } else {
            pushJavaObject(object);
        }
    }

    @Override
    public void pushNil() {
        checkStack(1);
        C.lua_pushnil(L);
    }

    @Override
    public void push(boolean bool) {
        checkStack(1);
        C.lua_pushboolean(L, bool ? 1 : 0);
    }

    @Override
    public void push(@NotNull Number number) {
        checkStack(1);
        C.lua_pushnumber(L, number.doubleValue());
    }

    @Override
    public void push(long integer) {
        checkStack(1);
        C.lua_pushinteger(L, integer);
    }

    @Override
    public void push(@NotNull String string) {
        checkStack(1);
        C.luaJ_pushstring(L, string);
    }

    @Override
    public void push(@NotNull ByteBuffer buffer) {
        checkStack(1);
        if (!buffer.isDirect()) {
            ByteBuffer directBuffer = ByteBuffer.allocateDirect(buffer.remaining());
            directBuffer.put(buffer);
            directBuffer.flip();
            buffer = directBuffer;
        }
        C.luaJ_pushlstring(L, buffer, buffer.position(), buffer.remaining());
    }

    @Override
    public void push(@NotNull Map<?, ?> map) {
        checkStack(3);
        C.lua_createtable(L, 0, map.size());
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            push(entry.getKey(), Conversion.FULL);
            push(entry.getValue(), Conversion.FULL);
            C.lua_rawset(L, -3);
        }
    }

    @Override
    public void push(@NotNull Collection<?> collection) {
        checkStack(2);
        C.lua_createtable(L, collection.size(), 0);
        int i = 1;
        for (Object o : collection) {
            push(o, Conversion.FULL);
            C.lua_rawseti(L, -2, i);
            i++;
        }
    }

    @Override
    public void pushArray(@NotNull Object array) throws IllegalArgumentException {
        checkStack(2);
        if (array.getClass().isArray()) {
            int len = Array.getLength(array);
            C.lua_createtable(L, len, 0);
            for (int i = 0; i != len; ++i) {
                push(Array.get(array, i), Conversion.FULL);
                C.lua_rawseti(L, -2, i + 1);
            }
        } else {
            throw new IllegalArgumentException("Not an array");
        }
    }

    @Override
    public void push(@NotNull JFunction function) {
        checkStack(1);
        C.luaJ_pushfunction(L, function);
    }

    @Override
    public void push(@NotNull LuaValue value) {
        checkStack(1);
        value.push(this);
    }

    @Override
    public void push(@NotNull LuaFunction function) {
        checkStack(1);
        push(new LuaFunctionWrapper(function));
    }

    @Override
    public void pushJavaObject(@NotNull Object object) throws IllegalArgumentException {
        if (object.getClass().isArray()) {
            throw new IllegalArgumentException("Expecting non-array argument");
        } else {
            checkStack(1);
            C.luaJ_pushobject(L, object);
        }
    }

    @Override
    public void pushJavaArray(@NotNull Object array) throws IllegalArgumentException {
        if (array.getClass().isArray()) {
            checkStack(1);
            C.luaJ_pusharray(L, array);
        } else {
            throw new IllegalArgumentException("Expecting non-array argument");
        }
    }

    @Override
    public void pushJavaClass(@NotNull Class<?> clazz) {
        checkStack(1);
        C.luaJ_pushclass(L, clazz);
    }

    /**
     * Converts a stack index into an absolute index.
     *
     * @param index a stack index
     * @return an absolute positive stack index
     */
    public int toAbsoluteIndex(int index) {
        if (index > 0) {
            return index;
        }
        if (index <= C.getRegistryIndex()) {
            return index;
        }
        if (index == 0) {
            throw new IllegalArgumentException("Stack index should not be 0");
        }
        return getTop() + 1 + index;
    }

    @Override
    public double toNumber(int index) {
        return C.lua_tonumber(L, index);
    }

    @Override
    public long toInteger(int index) {
        return C.lua_tointeger(L, index);
    }

    @Override
    public boolean toBoolean(int index) {
        return C.lua_toboolean(L, index) != 0;
    }

    @Override
    public @Nullable Object toObject(int index) {
        LuaType type = type(index);
        if (type == null) {
            return null;
        }
        switch (type) {
            case NIL:
            case NONE:
                return null;
            case BOOLEAN:
                return toBoolean(index);
            case NUMBER:
                return toNumber(index);
            case STRING:
                return toString(index);
            case TABLE:
                return toMap(index);
            case USERDATA:
                return toJavaObject(index);
        }
        pushValue(index);
        return get();
    }

    @Override
    public @Nullable Object toObject(int index, Class<?> type) {
        try {
            return JuaAPI.convertFromLua(this, type, index);
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }

    @Override
    public @Nullable String toString(int index) {
        return C.lua_tostring(L, index);
    }

    @Override
    public @Nullable ByteBuffer toBuffer(int index) {
        return (ByteBuffer) C.luaJ_tobuffer(L, index);
    }

    @Override
    public @Nullable ByteBuffer toDirectBuffer(int index) {
        ByteBuffer buffer = (ByteBuffer) C.luaJ_todirectbuffer(L, index);
        if (buffer == null) {
            return null;
        } else {
            return buffer.asReadOnlyBuffer();
        }
    }

    @Override
    public @Nullable Object toJavaObject(int index) {
        return C.luaJ_toobject(L, index);
    }

    @Override
    public @Nullable Map<?, ?> toMap(int index) {
        Object obj = toJavaObject(index);
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj);
        }
        checkStack(2);
        index = toAbsoluteIndex(index);
        if (C.lua_istable(L, index) == 1) {
            C.lua_pushnil(L);
            Map<Object, Object> map = new HashMap<>();
            while (C.lua_next(L, index) != 0) {
                Object k = toObject(-2);
                Object v = toObject(-1);
                map.put(k, v);
                pop(1);
            }
            return map;
        }
        return null;
    }

    @Override
    public @Nullable List<?> toList(int index) {
        Object obj = toJavaObject(index);
        if (obj instanceof List) {
            return ((List<?>) obj);
        }
        checkStack(1);
        if (C.lua_istable(L, index) == 1) {
            int length = rawLength(index);
            ArrayList<Object> list = new ArrayList<>();
            list.ensureCapacity(length);
            for (int i = 1; i <= length; i++) {
                C.luaJ_rawgeti(L, index, i);
                list.add(toObject(-1));
                pop(1);
            }
            return list;
        }
        return null;
    }

    @Override
    public boolean isBoolean(int index) {
        return C.lua_isboolean(L, index) != 0;
    }

    @Override
    public boolean isFunction(int index) {
        return C.lua_isfunction(L, index) != 0;
    }

    @Override
    public boolean isJavaObject(int index) {
        return C.luaJ_isobject(L, index) != 0;
    }

    @Override
    public boolean isNil(int index) {
        return C.lua_isnil(L, index) != 0;
    }

    @Override
    public boolean isNone(int index) {
        return C.lua_isnone(L, index) != 0;
    }

    @Override
    public boolean isNoneOrNil(int index) {
        return C.lua_isnoneornil(L, index) != 0;
    }

    @Override
    public boolean isNumber(int index) {
        return C.lua_isnumber(L, index) != 0;
    }

    @Override
    public boolean isInteger(int index) {
        return C.luaJ_isinteger(L, index) != 0;
    }

    @Override
    public boolean isString(int index) {
        return C.lua_isstring(L, index) != 0;
    }

    @Override
    public boolean isTable(int index) {
        return C.lua_istable(L, index) != 0;
    }

    @Override
    public boolean isThread(int index) {
        return C.lua_isthread(L, index) != 0;
    }

    @Override
    public boolean isUserdata(int index) {
        return C.lua_isuserdata(L, index) != 0;
    }

    @Override
    public @Nullable LuaType type(int index) {
        return convertType(C.lua_type(L, index));
    }

    @Override
    public boolean equal(int i1, int i2) {
        return C.luaJ_compare(L, i1, i2, 0) != 0;
    }

    @Override
    public int rawLength(int index) {
        /* luaJ_len might push the length on stack then pop it. */
        checkStack(1);
        return C.luaJ_len(L, index);
    }

    @Override
    public boolean lessThan(int i1, int i2) {
        return C.luaJ_compare(L, i1, i2, -1) != 0;
    }

    @Override
    public boolean rawEqual(int i1, int i2) {
        return C.lua_rawequal(L, i1, i2) != 0;
    }

    @Override
    public int getTop() {
        return C.lua_gettop(L);
    }

    @Override
    public void setTop(int index) {
        C.lua_settop(L, index);
    }

    @Override
    public void insert(int index) {
        C.lua_insert(L, index);
    }

    @Override
    public void pop(int n) {
        if (n < 0 || getTop() < n) {
            throw new LuaException(
                    LuaException.LuaError.MEMORY,
                    "invalid number of items to pop"
            );
        }
        C.lua_pop(L, n);
    }

    @Override
    public void pushValue(int index) {
        checkStack(1);
        C.lua_pushvalue(L, index);
    }

    @Override
    public void pushThread() {
        checkStack(1);
        C.lua_pushthread(L);
    }

    @Override
    public void remove(int index) {
        C.lua_remove(L, index);
    }

    @Override
    public void replace(int index) {
        C.lua_replace(L, index);
    }

    @Override
    public void xMove(Lua other, int n) throws IllegalArgumentException {
        if (other instanceof AbstractLua && ((AbstractLua) other).mainThread == mainThread) {
            other.checkStack(n);
            C.lua_xmove(L, other.getPointer(), n);
        } else {
            throw new IllegalArgumentException("Not sharing same global state");
        }
    }

    @Override
    public void load(String script) throws LuaException {
        checkStack(1);
        checkError(C.luaL_loadstring(L, script), false);
    }

    @Override
    public void load(Buffer buffer, String name) throws LuaException {
        if (buffer.isDirect()) {
            checkStack(1);
            checkError(C.luaJ_loadbuffer(L, buffer, buffer.position(), buffer.remaining(), name), false);
        } else {
            throw new LuaException(LuaException.LuaError.MEMORY, "Expecting a direct buffer");
        }
    }

    @Override
    public void run(String script) throws LuaException {
        checkStack(1);
        checkError(C.luaL_dostring(L, script), true);
    }

    @Override
    public void run(Buffer buffer, String name) throws LuaException {
        if (buffer.isDirect()) {
            checkStack(1);
            checkError(C.luaJ_dobuffer(L, buffer, buffer.position(), buffer.remaining(), name), true);
        } else {
            throw new LuaException(LuaException.LuaError.MEMORY, "Expecting a direct buffer");
        }
    }

    @Override
    public ByteBuffer dump() {
        return (ByteBuffer) C.luaJ_dumptobuffer(L);
    }

    @Override
    public void pCall(int nArgs, int nResults) throws LuaException {
        checkStack(Math.max(nResults - nArgs - 1, 0));
        checkError(C.lua_pcall(L, nArgs, nResults, 0), false);
    }

    @Override
    public AbstractLua newThread() {
        checkStack(1);
        LuaInstances.Token<AbstractLua> token = instances.add();
        long K = C.luaJ_newthread(L, token.id);
        AbstractLua lua = newThread(K, token.id, this.mainThread);
        mainThread.addSubThread(lua);
        token.setter.accept(lua);
        return lua;
    }

    protected void addSubThread(Lua lua) {
        synchronized (subThreads) {
            subThreads.add(lua);
        }
    }

    protected abstract AbstractLua newThread(long L, int id, AbstractLua mainThread);

    @Override
    public boolean resume(int nArgs) throws LuaException {
        int code = C.luaJ_resume(L, nArgs);
        if (convertError(code) == LuaException.LuaError.YIELD) {
            return true;
        }
        checkError(code, false);
        return false;
    }

    @Override
    public LuaException.LuaError status() {
        return convertError(C.lua_status(L));
    }

    @Override
    public void yield(int n) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void newTable() {
        createTable(0, 0);
    }

    @Override
    public void createTable(int nArr, int nRec) {
        checkStack(1);
        C.lua_createtable(L, nArr, nRec);
    }

    @Override
    public void getField(int index, String key) {
        checkStack(1);
        C.luaJ_getfield(L, index, key);
    }

    @Override
    public void setField(int index, String key) {
        C.lua_setfield(L, index, key);
    }

    @Override
    public void getTable(int index) {
        C.luaJ_gettable(L, index);
    }

    @Override
    public void setTable(int index) {
        C.lua_settable(L, index);
    }

    @Override
    public int next(int n) {
        checkStack(1);
        return C.lua_next(L, n);
    }

    @Override
    public void rawGet(int index) {
        C.luaJ_rawget(L, index);
    }

    @Override
    public void rawGetI(int index, int n) {
        checkStack(1);
        C.luaJ_rawgeti(L, index, n);
    }

    @Override
    public void rawSet(int index) {
        C.lua_rawset(L, index);
    }

    @Override
    public void rawSetI(int index, int n) {
        C.lua_rawseti(L, index, n);
    }

    @Override
    public int ref(int index) {
        return C.luaL_ref(L, index);
    }

    @Override
    public void unRef(int index, int ref) {
        C.luaL_unref(L, index, ref);
    }

    @Override
    public void getGlobal(String name) {
        checkStack(1);
        C.luaJ_getglobal(L, name);
    }

    @Override
    public void setGlobal(String name) {
        C.lua_setglobal(L, name);
    }

    @Override
    public int getMetatable(int index) {
        checkStack(1);
        return C.lua_getmetatable(L, index);
    }

    @Override
    public void setMetatable(int index) {
        C.luaJ_setmetatable(L, index);
    }

    @Override
    public int getMetaField(int index, String field) {
        checkStack(1);
        return C.luaL_getmetafield(L, index, field);
    }

    @Override
    public void getRegisteredMetatable(String typeName) {
        checkStack(1);
        C.luaJ_getmetatable(L, typeName);
    }

    @Override
    public int newRegisteredMetatable(String typeName) {
        checkStack(1);
        return C.luaL_newmetatable(L, typeName);
    }

    @Override
    public void openLibraries() {
        checkStack(1);
        C.luaL_openlibs(L);
        C.luaJ_initloader(L);
    }

    @Override
    public void openLibrary(String name) {
        checkStack(1);
        C.luaJ_openlib(L, name);
        if ("package".equals(name)) {
            C.luaJ_initloader(L);
        }
    }

    @Override
    public void concat(int n) {
        if (n == 0) {
            checkStack(1);
        }
        C.lua_concat(L, n);
    }

    @Override
    public void gc() {
        recycleReferences();
        C.luaJ_gc(L);
    }

    @Override
    public void error(String message) {
        throw new RuntimeException(message);
    }

    @Override
    public Object createProxy(Class<?>[] interfaces, Conversion degree)
            throws IllegalArgumentException {
        if (interfaces.length >= 1) {
            switch (Objects.requireNonNull(type(-1))) {
                case FUNCTION:
                    String name = ClassUtils.getLuaFunctionalDescriptor(interfaces);
                    if (name == null) {
                        pop(1);
                        throw new IllegalArgumentException("Unable to merge interfaces into a functional one");
                    }
                    createTable(0, 1);
                    insert(getTop() - 1);
                    setField(-2, name);
                    // Fall through
                case TABLE:
                    try {
                        LuaProxy proxy = new LuaProxy(ref(), this, degree, interfaces);
                        mainThread.recordedReferences.put(proxy.getReference(),
                                new LuaReference<>(proxy, mainThread.recyclableReferences));
                        return Proxy.newProxyInstance(
                                ClassUtils.getDefaultClassLoader(),
                                interfaces,
                                proxy
                        );
                    } catch (Throwable e) {
                        throw new IllegalArgumentException(e);
                    }
                default:
                    break;
            }
        }
        pop(1);
        throw new IllegalArgumentException("Expecting a table / function and interfaces");
    }

    @Override
    public void register(String name, LuaFunction function) {
        push(function);
        setGlobal(name);
    }

    @Override
    public void setExternalLoader(ExternalLoader loader) {
        mainThread.loader = loader;
    }

    @Override
    public void loadExternal(String module) throws LuaException {
        ExternalLoader loader = mainThread.loader;
        if (loader == null) {
            throw new LuaException(LuaException.LuaError.RUNTIME, "External loader not set");
        }
        Buffer buffer = loader.load(module, this);
        if (buffer == null) {
            throw new LuaException(LuaException.LuaError.FILE, "Loader returned null");
        }
        load(buffer, module);
    }

    @Override
    public LuaNatives getLuaNatives() {
        return C;
    }

    @Override
    public AbstractLua getMainState() {
        return mainThread;
    }

    @Override
    public long getPointer() {
        return L;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public @Nullable Throwable getJavaError() {
        getGlobal(GLOBAL_THROWABLE);
        Object o = toJavaObject(-1);
        pop(1);
        if (o instanceof Throwable) {
            return (Throwable) o;
        } else {
            return null;
        }
    }

    @Override
    public int error(@Nullable Throwable e) {
        if (e == null) {
            pushNil();
            setGlobal(GLOBAL_THROWABLE);
            return 0;
        }
        pushJavaObject(e);
        setGlobal(GLOBAL_THROWABLE);
        push(e.toString());
        return -1;
    }

    /**
     * Calls a method on an object, equivalent to <a href="https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.invokespecial">invokespecial</a>
     *
     * <p>
     * Internally it uses {@link LuaNatives#luaJ_invokespecial(long, Class, String, String, Object, String)} which then uses
     * {@code CallNonvirtual<Type>MethodA} functions to avoid tons of restrictions imposed by the JVM.
     * </p>
     *
     * @param object the {@code this} object
     * @param method the method
     * @param params the parameters
     * @return the return value
     * @throws Throwable whenever the method call throw exceptions
     */
    protected @Nullable Object invokeSpecial(Object object, Method method, @Nullable Object[] params) throws
            Throwable {
        if (!ClassUtils.isDefault(method)) {
            throw new IncompatibleClassChangeError("Unable to invoke non-default method");
        }
        if (params == null) {
            params = EMPTY;
        }
        for (int i = params.length - 1; i >= 0; i--) {
            Object param = params[i];
            if (param == null) {
                pushNil();
            } else {
                pushJavaObject(param);
            }
        }
        StringBuilder customSignature = new StringBuilder(params.length + 1);
        for (Class<?> type : method.getParameterTypes()) {
            appendCustomDescriptor(type, customSignature);
        }
        appendCustomDescriptor(method.getReturnType(), customSignature);
        if (C.luaJ_invokespecial(
                L,
                method.getDeclaringClass(),
                method.getName(),
                Type.getMethodDescriptor(method),
                object,
                customSignature.toString()
        ) == -1) {
            Throwable javaError = getJavaError();
            pop(1);
            throw Objects.requireNonNull(javaError);
        }
        if (method.getReturnType() == Void.TYPE) {
            return null;
        }
        Object ret = toJavaObject(-1);
        pop(1);
        return ret;
    }

    private void appendCustomDescriptor(Class<?> type, StringBuilder customSignature) {
        if (type.isPrimitive()) {
            customSignature.append(Type.getPrimitiveDescriptor(type));
        } else {
            customSignature.append("_");
        }
    }

    @Override
    public void close() {
        if (mainThread == this) {
            synchronized (subThreads) {
                for (Lua lua : subThreads) {
                    instances.remove(lua.getId());
                }
                subThreads.clear();
                instances.remove(id);
                C.lua_close(L);
            }
        } else {
            synchronized (mainThread.subThreads) {
                if (mainThread.subThreads.remove(this)) {
                    C.luaJ_removestateindex(L);
                    instances.remove(getId());
                }
            }
        }
    }

    @Override
    public int ref() {
        return ref(C.getRegistryIndex());
    }

    @Override
    public void refGet(int ref) {
        rawGetI(C.getRegistryIndex(), ref);
    }

    @Override
    public void unref(int ref) {
        unRef(C.getRegistryIndex(), ref);
    }

    /**
     * Throws {@link LuaException} if the code is not {@link LuaException.LuaError#OK}.
     *
     * <p>
     * Most Lua C API functions attaches along an error message on the stack.
     * If this method finds a string on the top of the stack, it pops the string
     * and uses it as the exception message.
     * </p>
     *
     * @param code    the error code returned by Lua C API
     * @param runtime if {@code true}, treat non-zero code values as runtime errors
     */
    protected void checkError(int code, boolean runtime) throws LuaException {
        LuaException.LuaError error = runtime
                ? (code == 0 ? LuaException.LuaError.OK : LuaException.LuaError.RUNTIME)
                : convertError(code);
        if (error == LuaException.LuaError.OK) {
            return;
        }
        String message;
        if (type(-1) == LuaType.STRING) {
            message = toString(-1);
            pop(1);
        } else {
            message = "Lua-side error";
        }
        LuaException e = new LuaException(error, message);
        Throwable javaError = getJavaError();
        if (javaError != null) {
            e.initCause(javaError);
            error((Throwable) null);
        }
        throw e;
    }

    public abstract LuaException.LuaError convertError(int code);

    public abstract LuaType convertType(int code);

    @Override
    public LuaValue get(String globalName) {
        getGlobal(globalName);
        return get();
    }

    @Override
    public void set(String key, Object value) {
        push(value, Conversion.SEMI);
        setGlobal(key);
    }

    @Override
    public LuaValue[] eval(String command) throws LuaException {
        load(command);
        return get().call();
    }

    @Override
    public LuaValue require(String module) throws LuaException {
        LuaValue req = requireFunction;
        if (req == null) {
            req = get("require");
            if (req.type() != LuaType.FUNCTION) {
                openLibrary("package");
                req = get("require");
                requireFunction = req;
            }
        }
        LuaValue[] results = req.call(module);
        return results[0];
    }

    @Override
    public LuaValue get() {
        LuaType type = type(-1);
        switch (Objects.requireNonNull(type)) {
            case NIL:
            case NONE:
                pop(1);
                return fromNull();
            case BOOLEAN:
                boolean b = toBoolean(-1);
                pop(1);
                return from(b);
            case NUMBER:
                LuaValue value = isInteger(-1)
                        ? from(toInteger(-1))
                        : from(toNumber(-1));
                pop(1);
                return value;
            case STRING:
                ByteBuffer s = toBuffer(-1);
                pop(1);
                return from(s);
            default:
                AbstractRefLuaValue ref = type == LuaType.TABLE
                        ? new LuaTableValue(this, type)
                        : new RefLuaValue(this, type);
                mainThread.recordedReferences.put(ref.getReference(),
                        new LuaReference<>(ref, mainThread.recyclableReferences));
                return ref;
        }
    }

    @Override
    public LuaValue fromNull() {
        return ImmutableLuaValue.NIL(this);
    }

    @Override
    public LuaValue from(boolean b) {
        return b ? ImmutableLuaValue.TRUE(this) : ImmutableLuaValue.FALSE(this);
    }

    @Override
    public LuaValue from(double n) {
        return ImmutableLuaValue.NUMBER(this, n);
    }

    @Override
    public LuaValue from(long n) {
        return ImmutableLuaValue.LONG(this, n);
    }

    @Override
    public LuaValue from(String s) {
        return ImmutableLuaValue.STRING(this, s);
    }

    @Override
    public LuaValue from(ByteBuffer buffer) {
        return ImmutableLuaValue.BUFFER(this, buffer);
    }

    /**
     * Do {@link #unref(int)} on all references in {@link #recyclableReferences}
     */
    private void recycleReferences() {
        LuaReference<?> ref = (LuaReference<?>) mainThread.recyclableReferences.poll();
        while (ref != null) {
            mainThread.recordedReferences.remove(ref.getReference());
            unref(ref.getReference());
            ref = (LuaReference<?>) mainThread.recyclableReferences.poll();
        }
    }

    /**
     * A method specifically for working around deadlocks caused by LuaJ.
     *
     * <p>
     * In LuaJ bindings, without this work-around, deadlocks can happen when:
     * </p>
     * <pre><code>
     * 1. (Thread#A) The user synchronizes on mainThread as is required by LuaJava when used
     *    in multi-threaded environment.
     * 2. (Thread#A) The user calls {@link #run(String)} for example, to run a Lua snippet.
     * 3. The snippet creates a coroutine, mandating LuaJ to create a Java thread (#B).
     * 4. Inside the coroutine (i.e., the Java thread#B), the code calls a Lua proxy object.
     * 5. (Thread#B) {@link LuaProxy#invoke(Object, Method, Object[])} tries to synchronizes
     *    on mainThread.
     * 6. Since thread#A is already inside a synchronization block, the two threads deadlocks.
     * </code></pre>
     * <p>
     * This work-around asks {@link LuaProxy#invoke(Object, Method, Object[])} to avoid synchronization
     * when it detects that it is called from a coroutine thread created by LuaJ.
     * </p>
     *
     * @return {@code false} only when invoke within a coroutine thread created by LuaJ
     */
    protected boolean shouldSynchronize() {
        return true;
    }

    private static class LuaFunctionWrapper implements JFunction {
        private final @NotNull LuaFunction function;

        public LuaFunctionWrapper(@NotNull LuaFunction function) {
            this.function = function;
        }

        @Override
        public int __call(Lua L) {
            LuaValue[] args = new LuaValue[L.getTop()];
            for (int i = 0; i < args.length; i++) {
                args[args.length - i - 1] = L.get();
            }
            LuaValue[] results = function.call(L, args);
            if (results != null) {
                for (LuaValue result : results) {
                    L.push(result);
                }
            }
            return results == null ? 0 : results.length;
        }
    }
}
