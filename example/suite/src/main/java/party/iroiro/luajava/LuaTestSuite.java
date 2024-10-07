package party.iroiro.luajava;

import party.iroiro.luajava.LuaException.LuaError;
import party.iroiro.luajava.interfaces.*;
import party.iroiro.luajava.lua51.Lua51;
import party.iroiro.luajava.value.LuaFunction;
import party.iroiro.luajava.value.LuaValue;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;
import static party.iroiro.luajava.DefaultProxyTest.instanceOfLuaJ;
import static party.iroiro.luajava.Lua.Conversion.FULL;
import static party.iroiro.luajava.Lua.Conversion.SEMI;
import static party.iroiro.luajava.Lua.LuaType.*;

public class LuaTestSuite<T extends AbstractLua> {

    public static final long I_60_BITS = 1152921504606846976L;

    @SuppressWarnings("UnusedReturnValue")
    public static <S> S assertInstanceOf(Class<S> sClass, Object o) {
        assertTrue("Expecting " + sClass + ", got " + o.getClass(), sClass.isInstance(o));
        return sClass.cast(o);
    }

    public static void assertDoesNotThrow(LuaTestRunnable r) {
        try {
            r.run();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static LuaException assertThrowsLua(Lua L, String lua, LuaError error) {
        return assertThrowsLua(L, lua, error, (String) null);
    }

    public static LuaException assertThrowsLua(Lua L, String lua,
                                               LuaError error, Class<? extends Throwable> cause) {
        LuaException e = assertThrowsLua(L, lua, error);
        assertInstanceOf(cause, e.getCause());
        return e;
    }

    public static LuaException assertThrowsLua(Lua L, String lua, LuaError error, String message) {
        return assertThrowsLua(error, () -> L.run(lua), message);
    }

    public static LuaException assertThrowsLua(LuaError error, Runnable f) {
        return assertThrowsLua(error, f, null);
    }

    public static LuaException assertThrowsLua(LuaError error, Runnable f, String message) {
        try {
            f.run();
            fail("Expecting an exception thrown");
            //noinspection UnreachableCode
            return new LuaException(LuaError.UNKNOWN, "");
        } catch (LuaException e) {
            assertEquals(error, e.type);
            if (message != null) {
                assertTrue(e.getMessage(), e.getMessage().contains(message));
            }
            return e;
        }
    }

    public LuaTestSuite(T L, LuaTestSupplier<T> constructor) {
        this.L = L;
        this.constructor = constructor;
    }

    public void test() {
        L.openLibraries();
        LuaScriptSuite.addAssertThrows(L);
        test64BitInteger();
        testCompat();
        testCoroutineDeadlock();
        testDump();
        testException();
        testExternalLoader();
        testGc();
        testJavaToLuaConversions();
        testLuaToJavaConversions();
        testMeasurements();
        testMetatables();
        testNotSupported();
        testOthers();
        testOverflow();
        testPCall();
        testProxy();
        testPushChecks();
        testRawStrings();
        testRef();
        testRequire();
        testRunners();
        testStackOperations();
        testStackPositions();
        testTableOperations();
        testThreads();
    }

    private void testRawStrings() {
        byte[] array = {3, 2, 1, 0, -1, -2, -3};
        L.push(ByteBuffer.wrap(array));
        assertEquals(7, L.rawLength(-1));
        L.setGlobal("raw_str");
        for (int i = 0; i < 7; i++) {
            assertEquals(
                    Byte.toUnsignedInt(array[i]),
                    L.eval("return string.byte(raw_str, " + (i + 1) + ")")[0].toInteger()
            );
        }
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(array.length);
        byteBuffer.put(array).flip();
        L.push(byteBuffer);
        assertEquals(7, L.rawLength(-1));
        L.setGlobal("raw_str");
        for (int i = 0; i < 7; i++) {
            assertEquals(
                    Byte.toUnsignedInt(array[i]),
                    L.eval("return string.byte(raw_str, " + (i + 1) + ")")[0].toInteger()
            );
        }
    }

    private void testPCall() {
        try (T L = constructor.get()) {
            L.run("return function() error('err') end");
            L.pushValue(-1);
            assertEquals(
                    LuaError.HANDLER,
                    L.convertError(L.getLuaNatives().lua_pcall(L.getPointer(), 0, 0, -2))
            );
        }
    }

    private void testCompat() {
        L.run("return _VERSION");
        String version = L.toString(-1);
        assertNotNull(version);
        switch (version) {
            case "Lua 5.4": // LUA_COMPAT_5_3
            case "Lua 5.3": // LUA_COMPAT_5_2
                L.openLibrary("math");
                L.run("return math.pow(2, 2)");
                assertEquals(4, L.toInteger(-1));
                break;
            case "Lua 5.2": // LUA_COMPAT_ALL
                L.openLibrary("math");
                L.run("return math.log10(1)");
                assertEquals(0, L.toInteger(-1));
                break;
        }
    }

    private void testCoroutineDeadlock() {
        Thread t = new Thread(() -> {
            try (T L = constructor.get()) {
                synchronized (L.getMainState()) {
                    L.openLibrary("coroutine");
                    Runnable proxy = L.eval("return { run = function() end }")[0]
                            .toProxy(Runnable.class);
                    L.set("proxy", proxy);
                    L.run("\n" +
                            "c = coroutine.wrap(function()\n" +
                            "  proxy:run()\n" +
                            "end)\n" +
                            "return c()\n");
                }
            }
        });
        t.start();
        try {
            t.join(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (t.isAlive()) {
            t.interrupt();
            fail("Coroutine deadlocks");
        }
    }

    private void test64BitInteger() {
        try (T L = constructor.get()) {
            L.push((Number) I_60_BITS);
            assertNotEquals(0, L.toInteger(-1));
            assertNotEquals(0, L.toNumber(-1), 1);

            /*
             * Although I expected casting (long) to (double) should be a reproducible operation,
             * apparently it doesn't on some platforms, including the Android AVD emulator.
             * We use `approx` (below) to handle possible double comparisons.
             */
            L.run(
                    "function approx(a, b)\n" +
                            "if a == b then\n" +
                            "  return true\n" +
                            "end\n" +
                            "local offset = a / b - 1\n" +
                            "offset = offset < 0 and -offset or offset\n" +
                            "return offset < 0.000001\n" +
                            "end");

            L.run("return _VERSION");
            String version = L.toString(-1);
            L.run("pow_2_60 = 1152921504606846976\nreturn pow_2_60 ~= pow_2_60 + 1");
            boolean supports64BitInteger = L.toBoolean(-1);
            L.run("double_int = 1099511627776\nreturn double_int ~= double_int + 1");
            boolean supportsDouble = L.toBoolean(-1); // double_int = 2 ^ 40.
            if (!supportsDouble) {
                assertFalse(supports64BitInteger);
                return;
            }

            L.push(I_60_BITS);
            boolean truncatesTo32Bit = L.toInteger(-1) == 0;
            assertFalse(truncatesTo32Bit);

            /* Things seem rather complicated:
             * - (64-bit machine + Lua 5.1 ~ 5.2): L.push(I_60_BITS) -> an approximated double value
             * - (64-bit machine + Lua 5.3 ~ 5.4): L.push(I_60_BITS) -> exact integer value
             * - (32-bit machine + Lua 5.1 ~ 5.2): L.push(I_60_BITS) -> truncates to int (0), then to double (0)
             * - (32-bit machine + Lua 5.3 ~ 5.4): L.push(I_60_BITS) -> truncates to int (0), then to long (0)
             * All machines seem to use double.
             * In the JNI code, we try to ensure that no truncation ever happens.
             */

            if (version != null && (version.equals("Lua 5.4") || version.equals("Lua 5.3"))) {
                assertTrue(supports64BitInteger);
            }

            // Since pow_2_60 contains trailing zero bits,
            // for most 64-bit machines, (long) (double) pow_2_60 == pow_2_60, so we need a +1.
            long i60Bits = I_60_BITS;
            L.push(i60Bits);
            L.setGlobal("i_from_java");
            L.push(i60Bits + 1);
            L.setGlobal("i_plus_from_java");
            L.run("return approx(pow_2_60, i_from_java)");
            assertTrue(L.toBoolean(-1));
            L.run("return approx(pow_2_60 + 1, i_plus_from_java)");
            assertTrue(L.toBoolean(-1));
            L.run("return approx(i_from_java, i_plus_from_java)");
            assertTrue(L.toBoolean(-1));

            L.run("return pow_2_60 + 1, i_from_java, i_plus_from_java");
            if (supports64BitInteger) {
                assertFalse(L.equal(-3, -2));
                assertFalse(L.equal(-2, -1));
                assertTrue(L.equal(-3, -1));
            }

            long converted = L.toInteger(-1);
            assertEquals("actual: " + converted, supports64BitInteger, converted == i60Bits + 1);
            converted = L.toInteger(-3);
            assertEquals("actual: " + converted, supports64BitInteger, converted == i60Bits + 1);

            //noinspection Convert2Lambda
            L.push(new JFunction() {
                @Override
                public int __call(Lua L) {
                    L.push(L.toInteger(-1));
                    return 1;
                }
            });
            L.setGlobal("jfunc");
            L.run("return approx(pow_2_60 + 1, jfunc(pow_2_60 + 1))");
            assertTrue(L.toBoolean(-1));

            L.pushJavaClass(LuaTestSuite.class);
            L.setGlobal("suite");
            L.run("return approx(pow_2_60 + 1, suite:passAlong(pow_2_60 + 1))");
            assertTrue(L.toBoolean(-1));

            if (supports64BitInteger) {
                L.run("return " +
                        "pow_2_60 + 1, jfunc(pow_2_60 + 1), suite:passAlong(pow_2_60 + 1)");
                assertTrue(L.equal(-3, -2));
                assertTrue(L.equal(-2, -1));
            }

            L.run("return { passAlong = function(_, l) return l end }");
            Object o = L.createProxy(new Class[]{PasserAlong.class}, FULL);
            assertEquals(
                    supports64BitInteger,
                    I_60_BITS + 1 == ((PasserAlong) o).passAlong(I_60_BITS + 1)
            );

            if (supports64BitInteger) {
                L.push(I_60_BITS + 1);
                L.push(I_60_BITS + 1);
                LuaValue v = L.get();
                v.push(L);
                assertTrue(L.equal(-2, -1));
            }
        }
    }

    private void testStackPositions() {
        try (T L = constructor.get()) {
            assertThrows(IllegalArgumentException.class, () -> L.toAbsoluteIndex(0));
            assertEquals(
                    L.getLuaNatives().getRegistryIndex(),
                    L.toAbsoluteIndex(L.getLuaNatives().getRegistryIndex())
            );

            Random random = new Random();
            // Relative stack positions
            for (int i = 0; i < 1024; i++) {
                L.setTop(0);
                int top = random.nextInt(1024) + 512;
                int abs = random.nextInt(top) + 1;
                int rel = abs - top - 1;
                for (int j = 0; j < top; j++) {
                    if (j == abs - 1) {
                        L.push(top);
                    } else {
                        L.pushNil();
                    }
                }
                assertTrue(L.isNumber(abs));
                assertTrue(L.isNumber(rel));
                assertEquals(abs, L.toAbsoluteIndex(rel));
                assertEquals(top, L.toNumber(abs), 1e-10);
                assertEquals(top, L.toNumber(rel), 1e-10);
            }
            // Table operations with random indices
            for (int i = 0; i < 32; i++) {
                L.setTop(0);
                int top = random.nextInt(1024) + 512;
                for (int j = 0; j < top; j++) {
                    L.pushNil();
                }

                int tableIndex = -random.nextInt(top) - 1;
                int absTableIndex = L.toAbsoluteIndex(tableIndex);
                L.pop(1);
                L.createTable(0, 0);
                L.insert(tableIndex);
                assertTrue(L.isTable(tableIndex));
                assertTrue(L.isTable(absTableIndex));

                for (int j = 0; j < 16; j++) {
                    L.push(j + 1);
                    L.push(j + 1);
                    if (j % 2 == 0) {
                        L.setTable(absTableIndex);
                    } else {
                        L.setTable(tableIndex - 2);
                    }
                }
                for (int j = 0; j < 16; j++) {
                    L.push("key-" + j);
                    L.push("value-" + j);
                    if (j % 2 == 0) {
                        L.setTable(absTableIndex);
                    } else {
                        L.setTable(tableIndex - 2);
                    }
                }
                assertEquals(16, L.rawLength(absTableIndex));
                assertEquals(16, L.rawLength(tableIndex));

                for (List<?> l : new List[]{L.toList(absTableIndex), L.toList(tableIndex)}) {
                    assertNotNull(l);
                    for (int j = 0; j < l.size(); j++) {
                        assertEquals(j + 1, (int) (double) (Double) l.get(j));
                    }
                }

                for (Map<?, ?> m : new Map[]{L.toMap(absTableIndex), L.toMap(tableIndex)}) {
                    assertNotNull(m);
                    for (int j = 0; j < 16; j++) {
                        assertEquals(j + 1, (int) (double) (Double) m.get((double) j + 1));
                        assertEquals("value-" + j, m.get("key-" + j));
                    }
                }
            }
        }
    }

    private void testRequire() {
        try (T L = constructor.get()) {
            L.openLibrary("package");
            L.run("assert(1024 == require('party.iroiro.luajava.JavaLibTest.open').getNumber())");
        }
        try (T L = constructor.get()) {
            L.setExternalLoader(new ClassPathLoader());
            assertThrowsLua(
                    LuaError.RUNTIME,
                    () -> L.require("luajava.wrongLuaFile")
            );
            L.require("suite.luajava-compat");
            L.require("suite.empty");
        }
    }

    private void testDump() {
        try (T L = constructor.get();
             T J = constructor.get()) {
            // Simple functions
            L.run("return function(a, b) return a + b end");
            ByteBuffer add = L.dump();
            assertNotNull(add);
            for (int i = 0; i < 100; i += 17) {
                for (int j = 0; j < 100; j += 37) {
                    J.load(add, "addition.lua");
                    J.push(i);
                    J.push(j);
                    J.pCall(2, 1);
                    assertEquals(i + j, J.toNumber(-1), 0.000001);
                    J.pop(1);
                    add.position(0);
                }
            }
            L.pop(1);

            // toBuffer with string.dump
            L.openLibrary("string");
            L.run("return string.dump(function(a, b) return a + b end)");
            ByteBuffer dumpedAdd = L.toBuffer(-1);
            assertNotNull(dumpedAdd);
            for (int i = 0; i < 100; i += 17) {
                for (int j = 0; j < 100; j += 37) {
                    J.load(dumpedAdd, "stringDumpedAdd.lua");
                    J.push(i);
                    J.push(j);
                    J.pCall(2, 1);
                    assertEquals(i + j, J.toNumber(-1), 0.000001);
                    J.pop(1);
                    dumpedAdd.position(0);
                }
            }
            L.pop(1);

            // Non-functions
            L.createTable(0, 0);
            assertNull(L.dump());
            L.pop(1);
            L.pushNil();
            assertNull(L.dump());
            L.pop(1);

            // C functions
            L.run("return java.new");
            assertTrue(L.isFunction(-1));
            assertNull(L.dump());
            L.pop(1);

            // Functions with up-values
            L.run("value = 1000");
            L.run("return function(b) return b + value end");
            ByteBuffer upAdd = L.dump();
            assertNotNull(upAdd);
            J.load(upAdd, "upAdd.lua");
            J.push(24);
            assertThrowsLua(LuaError.RUNTIME, () -> J.pCall(1, 1));
            J.run("value = 1000");
            upAdd.position(0);
            J.load(upAdd, "upAdd.lua");
            J.push(24);
            J.pCall(1, 1);
            assertEquals(1024., J.toNumber(-1), 0.000001);
            L.pop(1);

            // Large string to buffer
            J.openLibrary("string");
            // Aiming 1 MB
            J.run("s = 's'; for i = 1, 11 do s = string.rep(s, 4) end");
            J.getGlobal("s");
            int size = 4 * 1024 * 1024;
            assertEquals(size, J.rawLength(-1));
            ByteBuffer buffer = J.toBuffer(-1);
            assertNotNull(buffer);
            assertEquals(size, buffer.capacity());
            assertEquals(0, buffer.position());
            assertEquals(size, buffer.limit());
            for (int i = 0; i < size; i++) {
                assertEquals('s', buffer.get(i));
            }
            ByteBuffer direct = J.toDirectBuffer(-1);
            assertNotNull(direct);
            assertTrue(direct.isDirect());
            assertTrue(direct.isReadOnly());
            assertEquals(0, direct.position());
            assertEquals(size, direct.limit());
            assertEquals(size, direct.capacity());
            J.pop(1);
            J.createTable(0, 0);
            assertNull(J.toDirectBuffer(-1));
            J.pop(1);
        }
    }

    private void testGc() {
        try (T L = constructor.get()) {
            L.createTable(0, 100000);
            L.setGlobal("a");
            L.run("for i = 1, 100000 do a[tostring(i)] = true end");
            LuaValue beforeGc = L.eval("return collectgarbage('count')")[0];
            L.pushNil();
            L.setGlobal("a");
            L.gc();
            L.gc();
            L.run("return collectgarbage('count')");
            beforeGc.push(L);
            if (!instanceOfLuaJ(L)) {
                // LuaJ uses Java GC
                assertTrue(L.lessThan(-2, -1));
            }
        }
    }

    private void testException() {
        L.push(new Object(), SEMI);
        L.setGlobal(Lua.GLOBAL_THROWABLE);
        assertNull(L.getJavaError());
        String message = "Unexpected exception";
        L.push(L -> {
            throw new RuntimeException(message);
        });
        String expected = "java.lang.RuntimeException: " + message;
        assertInstanceOf(RuntimeException.class,
                assertThrowsLua(LuaError.RUNTIME, () -> L.get().call(),
                        expected));
        L.run("java.import('java.lang.String')");
        assertNull(L.getJavaError());

        assertEquals(-1, L.error(new RuntimeException(message)));
        assertEquals(expected, L.toString(-1));
        L.error((Throwable) null);
        assertNull(L.getJavaError());
        L.pop(1);
    }

    private void testExternalLoader() {
        try (T t = constructor.get()) {
            LuaScriptSuite.addAssertThrows(t);
            assertThrowsLua(LuaError.RUNTIME, () -> t.loadExternal("some.module"));
            assertDoesNotThrow(
                    () -> t.setExternalLoader((module, L) -> ByteBuffer.allocate(0)));
            t.openLibrary("package");
            assertDoesNotThrow(
                    () -> t.setExternalLoader((module, L) -> ByteBuffer.allocate(0)));
            assertThrowsLua(LuaError.MEMORY, () -> t.loadExternal("some.module"));
            assertDoesNotThrow(
                    () -> t.setExternalLoader(new ClassPathLoader()));
            assertThrowsLua(LuaError.FILE, () -> t.loadExternal("some.module"));
            t.loadExternal("suite.importTest");
            t.pCall(0, Consts.LUA_MULTRET);
        }
    }

    private void testMetatables() {
        L.createTable(0, 0);
        assertEquals(0, L.getMetatable(-1));
        assertEquals(0, L.getMetaField(-1, "__call"));
        L.createTable(0, 0);
        L.setMetatable(-2);
        assertEquals(0, L.getMetaField(-1, "__call"));
        assertNotEquals(0, L.getMetatable(-1));
        L.pop(2);
    }

    public static final AtomicInteger proxyIntegerTest = new AtomicInteger(0);

    private void testProxy() {
        L.push(true);
        assertThrows(IllegalArgumentException.class, () -> L.createProxy(new Class[0], Lua.Conversion.NONE));
        L.push(true);
        assertThrows(IllegalArgumentException.class, () -> L.createProxy(new Class[]{Runnable.class}, Lua.Conversion.NONE));
        L.run("proxyMap = { run = function()\n" +
                "clazz = java.import('party.iroiro.luajava.LuaTestSuite')\n" +
                "clazz.proxyIntegerTest:set(-1024)\n" +
                "end" +
                "}");
        L.getGlobal("proxyMap");
        Object proxy = L.createProxy(new Class[]{Runnable.class}, Lua.Conversion.NONE);
        proxyIntegerTest.set(0);
        ((Runnable) proxy).run();
        assertEquals(-1024, proxyIntegerTest.get());

        L.run("return function() end");
        assertEquals("Expecting a table / function and interfaces",
                assertThrows(IllegalArgumentException.class,
                        () -> L.createProxy(new Class[0], Lua.Conversion.NONE))
                        .getMessage());

        L.run("return function() end");
        assertEquals("Unable to merge interfaces into a functional one",
                assertThrows(IllegalArgumentException.class,
                        () -> L.createProxy(new Class[]{Runnable.class, Comparator.class},
                                Lua.Conversion.NONE))
                        .getMessage());

        L.run("return function() end");
        assertInstanceOf(Runnable.class, L.createProxy(new Class[]{Runnable.class}, SEMI));

        new DefaultProxyTest(L).test();
    }

    private void testOthers() {
        L.openLibrary("math");
        L.run("assert(1.0 == math.abs(-1.0))");
        L.push(l -> {
            l.push("Hello");
            return 1;
        });
        L.setGlobal("testOthersFunction1");
        L.register("testOthersFunction2", (l, args) -> new LuaValue[]{l.from("Hello")});
        L.run("assert('Hello' == testOthersFunction1())");
        L.run("assert('Hello' == testOthersFunction2())");

        L.newRegisteredMetatable("myusertype");
        L.newRegisteredMetatable("myusertype");
        assertTrue(L.equal(-1, -2));
        L.pop(1);
        L.push(l -> {
            l.push(1);
            return 1;
        });
        L.setGlobal("userTypeFunc");
        /* Metamethods must be functions */
        L.run("function userTypeFuncWrap() return userTypeFunc() end");
        L.getGlobal("userTypeFuncWrap");
        L.setField(-2, "__index");
        L.pop(1);

        L.createTable(0, 0);
        L.getRegisteredMetatable("myusertype");
        L.setMetatable(-2);
        L.setGlobal("testOthersTable");
        L.run("assert(1 == testOthersTable.a)");
        L.run("assert(1 == testOthersTable.b)");
        L.run("assert(1 == testOthersTable.whatever)");
        L.run("assert(1 == testOthersTable.__index)");
        L.run("assert(1 == testOthersTable[10])");

        AbstractLua lua = new AbstractLua(L.getLuaNatives()) {
            @Override
            protected AbstractLua newThread(long L, int id, AbstractLua mainThread) {
                return null;
            }

            @Override
            public LuaError convertError(int code) {
                return null;
            }

            @Override
            public LuaType convertType(int code) {
                return null;
            }
        };
        lua.push(1);
        assertNull(lua.toObject(-1));
        lua.close();
    }

    private void testThreads() {
        L.openLibrary("coroutine");
        Lua sub = L.newThread();
        assertEquals(LuaError.OK, sub.status());
        sub.getGlobal("assert");
        sub.push(true);
        assertFalse(sub.resume(1));
        sub.run("function threadCoroutineTest()\n" +
                "coroutine.yield(1)\n" +
                "coroutine.yield(2)\n" +
                "end");
        sub.getGlobal("threadCoroutineTest");
        assertTrue(sub.resume(0));
        assertEquals(LuaError.YIELD, sub.status());
        assertEquals(1.0, sub.toNumber(-1), 0.000001);
        sub.pop(1);
        assertTrue(sub.resume(0));
        assertEquals(2.0, sub.toNumber(-1), 0.000001);
        sub.pop(1);
        sub.resume(0);
        sub.close();

        integer.set(0);
        L.run("i = java.import('party.iroiro.luajava.LuaTestSuite').integer");
        L.getGlobal("i");
        L.run("coroutine.resume(coroutine.create(\n" +
                "function() java.import('party.iroiro.luajava.LuaTestSuite').integer:set(1024) end\n" +
                "))");
        assertEquals(1024, integer.get());

        T O = constructor.get();
        AbstractLua P = O.newThread();
        O.close();
        P.close();
    }

    public static final AtomicInteger integer = new AtomicInteger(0);

    private void testRunners() {
        String s = "testRunnersString = 'Not OK'";
        L.load(s);
        L.pop(1);
        byte[] bytes = s.getBytes();
        ByteBuffer wrap = ByteBuffer.wrap(bytes);
        assertFalse(wrap.isDirect());
        assertThrowsLua(LuaError.MEMORY, () -> L.load(wrap, "notDirectBuffer"));
        assertThrowsLua(LuaError.MEMORY, () -> L.run(wrap, "notDirectBuffer"));
        L.run(getDirect(s), "directBuffer");
        assertThrowsLua(LuaError.RUNTIME, () -> L.run(getDirect("print("), "directBuffer"));
        L.getGlobal("testRunnersString");
        assertEquals("Not OK", L.toString(-1));
        L.pop(1);
        L.load(getDirect(s), "directBuffer");
        L.pCall(0, 0);
    }

    private Buffer getDirect(String s) {
        byte[] bytes = s.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bytes.length);
        byteBuffer.put(bytes);
        byteBuffer.flip();
        return byteBuffer;
    }

    private void testNotSupported() {
        assertThrows(UnsupportedOperationException.class, () -> L.yield(1));
        assertThrows(RuntimeException.class, () -> L.error(""));

    }

    private void testStackOperations() {
        try (Lua L = constructor.get()) {
            assertEquals(0, L.getTop());
            assertEquals(0, L.newThread().getTop());
            assertThrowsLua(LuaError.MEMORY, () -> L.pop(-1), "invalid number of items to pop");
            assertThrowsLua(LuaError.MEMORY, () -> L.pop(10), "invalid number of items to pop");
            L.setTop(0);
            assertThrowsLua(LuaError.MEMORY, () -> L.pop(1), "invalid number of items to pop");
        }

        Lua sub = L.newThread();
        int top = L.getTop();
        L.setTop(top + 1);
        assertEquals(NIL, L.type(-1));
        L.setTop(top);
        Enumeration<Object> objectEnumeration = new Enumeration<Object>() {
            @Override
            public boolean hasMoreElements() {
                return false;
            }

            @Override
            public Object nextElement() {
                throw new NoSuchElementException();
            }
        };
        L.push(objectEnumeration, Lua.Conversion.NONE);
        L.xMove(sub, 1);

        assertThrows(IllegalArgumentException.class, () -> L.xMove(new Lua51(), 1));
        L.run("return {}");
        Lua proxy = (Lua) L.createProxy(new Class[]{Lua.class}, SEMI);
        assertThrows(IllegalArgumentException.class, () -> L.xMove(proxy, 1));

        assertSame(objectEnumeration, sub.toObject(-1));
        assertEquals(top, L.getTop());

        L.push(1);
        L.push(2);
        L.push(3);
        L.push(4);
        L.insert(-3); // 1423
        L.remove(-1); // 142
        L.replace(-3); // 24
        L.concat(2);
        assertEquals("24", L.toString(-1));

        L.push("1");
        L.push("2");
        L.concat(2);
        assertEquals("12", L.toString(-1));
        L.concat(0);
        assertEquals("", L.toString(-1));
        L.pop(3);
    }

    private void testMeasurements() {
        L.push(1);
        L.push(2);
        assertTrue(L.equal(-1, -1));
        assertTrue(L.rawEqual(-1, -1));
        assertTrue(L.lessThan(-2, -1));
        assertFalse(L.equal(-1, -2));
        assertFalse(L.rawEqual(-1, -2));
        assertFalse(L.lessThan(-1, -2));
        assertThrowsLua(LuaError.RUNTIME, () -> L.run("a = 1 \n print(#a)"));
        L.push(Arrays.asList(1, 2, 3));
        assertEquals(3, L.rawLength(-1));
        L.pop(3);
    }

    private void testLuaToJavaConversions() {
        L.pushNil();
        int start = L.getTop();
        L.push(true);
        L.getGlobal("print");
        L.push(1, Lua.Conversion.NONE);
        L.push(1);
        L.push("S");
        L.push(new HashMap<>(), FULL);
        L.newThread();
        L.push(l -> 0);
        int end = L.getTop();
        ArrayList<LuaTestFunction<Integer, Boolean>> assersions = new ArrayList<>(Arrays.asList(
                L::isNil, L::isNone, L::isNoneOrNil, L::isBoolean, L::isFunction, L::isJavaObject,
                L::isNumber, L::isString, L::isTable, L::isThread, L::isUserdata
        ));
        int[][] expected = {
                {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0,}, // nil
                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0,}, // boolean
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,}, // function
                {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1,}, // Java object
                {0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0,}, // number (isstring == true)
                {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0,}, // string
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0,}, // table
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0,}, // thread
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,}, // JFunction
        };
        Lua.LuaType[] types = {NIL, BOOLEAN, FUNCTION, USERDATA, NUMBER, STRING, TABLE, THREAD, FUNCTION};
        for (int i = start; i <= end; i++) {
            for (int j = 0; j < assersions.size(); j++) {
                assertEquals(1 == expected[i - start][j], assersions.get(j).apply(i));
                assertEquals(types[i - start], L.type(i));
            }
        }
        for (int i = 1; i < 1000; i++) {
            assertTrue(L.isNone(i + end));
            assertTrue(L.isNoneOrNil(i + end));
            assertEquals(NONE, L.type(i + end));
        }
        L.pop(expected.length);

        LuaNatives luaNative = L.getLuaNatives();

        luaNative.lua_pushlightuserdata(L.getPointer(), 0);
        assertEquals(LIGHTUSERDATA, L.type(-1));
        String typename = L.getLuaNatives().luaL_typename(L.L, -1);
        assertTrue("lightuserdata".equals(typename) || "userdata".equals(typename));
        assertInstanceOf(LuaValue.class, Objects.requireNonNull(L.toObject(-1)));
        assertNull(L.toObject(-1, Void.class));
        assertNull(L.toObject(-1, Integer.class));
        L.pop(1);

        HashMap<Object, Object> map = new HashMap<>();
        L.push(map, Lua.Conversion.NONE);
        assertSame(map, L.toObject(-1, Map.class));
        assertNull(L.toObject(-1, List.class));
        L.pop(1);

        L.push(new BigInteger("127"), Lua.Conversion.NONE);
        ArrayList<Class<?>> classes = new ArrayList<>(Arrays.asList(
                byte.class, Byte.TYPE, Byte.class,
                short.class, Short.TYPE, Short.class,
                int.class, Integer.TYPE, Integer.class,
                long.class, Long.TYPE, Long.class,
                float.class, Float.TYPE, Float.class,
                double.class, Double.TYPE, Double.class
        ));
        for (int i = 0; i < classes.size(); i += 3) {
            for (int j = 0; j < 3; j++) {
                Object o = L.toObject(-1, classes.get(i + j));
                assertNotNull(o);
                assertInstanceOf(classes.get(i + 2), o);
                assertInstanceOf(Number.class, o);
                assertEquals(127.0, ((Number) Objects.requireNonNull(o)).doubleValue(), 0.000001);
            }
        }
        assertNull(L.toObject(-1, BigDecimal.class));
        L.pop(1);

        L.push("100");
        assertEquals(STRING, L.type(-1));
        Object buffer = L.toObject(-1, ByteBuffer.class);
        assertNotNull(buffer);
        assertEquals("100", StandardCharsets.UTF_8.decode(assertInstanceOf(ByteBuffer.class, buffer)).toString());
        L.pop(1);

        testToMap(luaNative);

        testToList();
    }

    private void testToList() {
        List<Object> l = Collections.emptyList();
        L.push(l, Lua.Conversion.NONE);
        assertSame(l, L.toList(-1));
        L.push(true);
        assertNull(L.toList(-1));
        L.run("testToListList = {1, 2, 3, 4, 5}");
        L.getGlobal("testToListList");
        assertIterableEquals(Arrays.asList(1., 2., 3., 4., 5.), Objects.requireNonNull(L.toList(-1)));
        L.pop(3);
    }

    private void assertIterableEquals(Iterable<?> a, Iterable<?> b) {
        Iterator<?> i = a.iterator();
        Iterator<?> j = b.iterator();
        while (true) {
            if (i.hasNext()) {
                assertTrue(j.hasNext());
                assertEquals(i.next(), j.next());
            } else {
                assertFalse(j.hasNext());
                return;
            }
        }
    }

    private void testToMap(LuaNatives luaNative) {
        L.push(true);
        assertNull(L.toMap(-1));
        HashMap<Object, Object> emptyMap = new HashMap<>();
        L.push(emptyMap, Lua.Conversion.NONE);
        assertSame(emptyMap, L.toMap(-1));
        L.createTable(0, 1);
        luaNative.lua_pushlightuserdata(L.getPointer(), 0);
        luaNative.lua_pushlightuserdata(L.getPointer(), 1);
        L.setTable(-3);
        luaNative.lua_pushlightuserdata(L.getPointer(), 2);
        L.push(true);
        L.setTable(-3);
        L.push(false);
        luaNative.lua_pushlightuserdata(L.getPointer(), 3);
        L.setTable(-3);
        assertEquals(3, Objects.requireNonNull(L.toMap(-1)).size());
        L.pop(3);
    }

    private void testRef() {
        L.createTable(0, 0);
        testRef(o -> {
            L.push(o, Lua.Conversion.NONE);
            return L.ref(-2);
        }, i -> {
            L.rawGetI(-1, i);
            Object o = L.toJavaObject(-1);
            L.pop(1);
            return o;
        }, i -> L.unRef(-1, i));
        L.pop(1);
        testRef(o -> {
            L.push(o, Lua.Conversion.NONE);
            return L.ref();
        }, i -> {
            L.refGet(i);
            Object o = L.toJavaObject(-1);
            L.pop(1);
            return o;
        }, L::unref);
    }

    private void testRef(LuaTestFunction<Object, Integer> ref,
                         LuaTestFunction<Integer, Object> refGet,
                         LuaTestConsumer<Integer> unref) {
        L.createTable(0, 0);
        int nilRef = ref.apply(null);
        assertEquals(nilRef, (long) ref.apply(null));
        HashMap<Integer, Integer> values = new HashMap<>();
        for (int i = 1000; i > 0; i--) {
            Integer j = i;
            assertNull(values.put(ref.apply(j), j));
        }
        assertEquals(1000, values.size());
        for (Map.Entry<Integer, Integer> entry : values.entrySet()) {
            assertEquals(refGet.apply(entry.getKey()), entry.getValue());
            unref.accept(entry.getKey());
        }
        for (Map.Entry<Integer, Integer> entry : values.entrySet()) {
            assertNull(refGet.apply(entry.getKey()));
        }
    }

    private void testTableOperations() {
        L.createTable(1000, 1000);
        Set<String> values = new HashSet<>();
        for (int i = 0; i < 2000; i++) {
            String v = UUID.randomUUID().toString();
            L.push(v);
            switch (i % 3) {
                case 0:
                    L.pushValue(-1);
                    L.setTable(-3);
                    break;
                case 1:
                    L.setField(-2, v);
                    break;
                case 2:
                    L.pushValue(-1);
                    L.rawSet(-3);
                    break;
            }
            values.add(v);
        }
        int i = 0;
        for (String v : values) {
            switch (i % 3) {
                case 0:
                    L.push(v);
                    L.getTable(-2);
                    break;
                case 1:
                    L.getField(-1, v);
                    break;
                case 2:
                    L.push(v);
                    L.rawGet(-2);
                    break;
            }
            assertEquals("i: " + i, v, L.toString(-1));
            L.pop(1);
            i++;
        }
        L.pushNil();
        while (L.next(-2) != 0) {
            assertTrue(L.equal(-2, -1));
            String v = L.toString(-1);
            assertTrue(values.contains(v));
            values.remove(v);
            L.pop(1);
        }
        assertEquals(0, values.size());
        for (int j = Integer.MAX_VALUE - 2000; j < Integer.MAX_VALUE; j++) {
            L.push(Integer.MAX_VALUE - j);
            L.rawSetI(-2, j);
        }
        for (int j = Integer.MAX_VALUE - 2000; j < Integer.MAX_VALUE; j++) {
            L.rawGetI(-1, j);
            assertEquals((double) Integer.MAX_VALUE - j, L.toNumber(-1), 0.000001);
            L.pop(1);
        }
        L.pop(1);

        L.newTable();
        assertEquals(TABLE, L.type(-1));
        L.pop(1);
    }

    @SuppressWarnings("ConstantConditions")
    private void testPushChecks() {
        Object n = null;
        assertThrows(IllegalArgumentException.class, () -> L.pushArray(1));
        assertThrows(IllegalArgumentException.class, () -> L.pushJavaArray(1));
        assertThrows(IllegalArgumentException.class, () -> L.pushJavaObject(new int[0]));
        assertThrows(NullPointerException.class, () -> L.pushArray(n));
        assertThrows(NullPointerException.class, () -> L.pushJavaArray(n));
        assertThrows(NullPointerException.class, () -> L.pushJavaObject(n));
        // assertThrows(NullPointerException.class, () -> L.push((JFunction) null));
    }

    private void testOverflow() {
        L.createTable(1, 1);
        int testTableI = L.getTop();
        L.push("S");
        L.setField(-2, "S");
        L.push("I");
        L.rawSetI(-2, 1);
        L.createTable(0, 1);
        L.push("F");
        L.setField(-2, "F");
        L.setMetatable(-2);
        L.pushValue(-1);
        int ref = L.ref();
        ArrayList<LuaTestConsumer<T>> stackIncrementingOperations = getLuaTestConsumers(ref, testTableI);
        for (LuaTestConsumer<T> t : stackIncrementingOperations) {
            assertThrows("No more stack space available", RuntimeException.class, () -> {
                double i = 1.0;
                t.accept(L);
                //noinspection InfiniteLoopStatement
                while (true) {
                    L.checkStack((int) i);
                    // TODO: Maybe cache global references?
                    // JNI ERROR (app bug): global reference table overflow (max=51200)
                    // Failed adding to JNI global ref table (51200 entries)
                    // So, instead of: t.accept(L);
                    L.pushValue(-1);
                    i *= 1.0001;
                }
            });
            L.setTop(testTableI);
        }
        L.pop(1);
    }

    private static <T extends AbstractLua> ArrayList<LuaTestConsumer<T>> getLuaTestConsumers(int ref,
                                                                                             int testTableI) {
        ArrayList<LuaTestConsumer<T>> stackIncrementingOperations = new ArrayList<>(Arrays.asList(
                L -> L.createTable(0, 0),
                L -> L.getGlobal("java"),
                L -> L.refGet(ref),
                L -> L.pushValue(testTableI),
                L -> L.getMetatable(testTableI),
                L -> L.getField(testTableI, "S"),
                L -> L.rawGetI(testTableI, 1),
                L -> L.getMetaField(testTableI, "F")
        ));
        for (Object[] data : DATA) {
            for (Lua.Conversion conv : Lua.Conversion.values()) {
                stackIncrementingOperations.add(L -> L.push(data[4], conv));
            }
        }
        return stackIncrementingOperations;
    }

    private void testJavaToLuaConversions() {
        Integer integer = 1 << 14;
        L.push(integer, Lua.Conversion.NONE);
        L.setGlobal("object1");
        L.push(integer, Lua.Conversion.NONE);
        L.setGlobal("object2");
        //noinspection BoxingBoxedValue
        Integer another = Integer.valueOf(integer);
        assertNotSame(integer, another);
        L.push(another, Lua.Conversion.NONE);
        L.setGlobal("object3");
        L.run("assert(object1 == object2)");
        L.run("assert(object2 ~= object3)");
        L.run("assert(object2 ~= 1)");
        L.run("return object1, object2");
        assertFalse(L.rawEqual(-2, -1));
        assertTrue(L.rawEqual(-1, -1));

        L.push(l -> {
            l.push(1024);
            return 1;
        });
        L.setGlobal("l2jConvTest");
        L.run("assert(1024 == l2jConvTest())");
        ConcurrentSkipListSet<Object> set = new ConcurrentSkipListSet<>();
        L.push(set, Lua.Conversion.NONE);
        LuaValue v = L.get();
        assertSame(set, v.toJavaObject());
        v.push(L);
        assertSame(set, ((LuaValue) Objects.requireNonNull(JuaAPI
                .convertFromLua(L, LuaValue.class, -1))).toJavaObject());
        L.pop(1);

        L.push("string");
        L.setGlobal("string1");
        L.push(ByteBuffer.wrap("string".getBytes()));
        L.setGlobal("string2");
        L.run("assert(string1 == string2)");

        for (Object[] data : DATA) {
            Lua.Conversion[] conversions = {Lua.Conversion.NONE, SEMI, FULL};
            for (int i = 0; i < conversions.length; i++) {
                for (int j = 4; j < data.length; j++) {
                    Object o = data[j];
                    Object expected = data[i + 1];
                    L.push(o, conversions[i]);
                    assertInstanceOf(Verifier.class, data[0]);
                    ((Verifier) data[0]).verify(L, o);
                    if (expected instanceof Lua.LuaType) {
                        assertEquals("Testing " + o, expected, L.type(-1));
                    } else if (expected instanceof Class) {
                        assertEquals("Testing " + o, USERDATA, L.type(-1));
                        assertTrue("Testing " + o, L.isJavaObject(-1));
                        Object actualValue = L.toJavaObject(-1);
                        assertInstanceOf(((Class<?>) expected), Objects.requireNonNull(actualValue));
                        assertEquals("Testing " + o, o, actualValue);
                    } else {
                        fail("Test data incorrect");
                    }
                    L.pop(1);
                }
            }
        }
    }

    private static class Verifier {
        private final LuaTestBiPredicate<Object, Object> verifier;

        public Verifier(LuaTestBiPredicate<Object, Object> verifier) {
            this.verifier = verifier;
        }

        public void verify(Lua L, Object original) {
            assertTrue(
                    original == null ? "null" : (original.getClass().getName() + " " + original),
                    verifier.test(original, L.toObject(-1))
            );
        }
    }

    private static Verifier V(LuaTestBiPredicate<Object, Object> verifier) {
        return new Verifier(verifier);
    }

    private final T L;
    private final LuaTestSupplier<T> constructor;
    private static final Object[][] DATA = {
            /* { Verifier,
             *   NONE_CONVERTED_TYPE,
             *   SEMI_CONVERTED_TYPE,
             *   FULL_CONVERTED_TYPE,
             *   testValue1, testValue2, ... }
             */
            {
                    V((o, l) -> l == null),
                    NIL, NIL, NIL, null
            },
            {
                    V(Object::equals),
                    USERDATA, BOOLEAN, BOOLEAN,
                    true, false
            },
            {
                    V((o, i) -> {
                        if (o.equals(i)) {
                            return true;
                        }
                        if (i instanceof Number) {
                            Number i1 = (Number) i;
                            if (o instanceof BigInteger) {
                                return !o.equals(i)
                                        && ((BigInteger) o).compareTo(
                                        BigInteger.valueOf(i1.longValue())) > 0;
                            } else {
                                if (o instanceof Number) {
                                    Number i2 = (Number) o;
                                    return (Math.abs(i2.doubleValue() - i1.doubleValue()) < 0.00000001)
                                            || i2.longValue() == i1.longValue()
                                            || i2.intValue() == i1.intValue();
                                } else if (o instanceof Character) {
                                    return i1.intValue() == (int) ((Character) o);
                                }
                            }
                        }
                        return false;
                    }),
                    USERDATA, NUMBER, NUMBER,
                    'c', (byte) 1, (short) 2, 3, 4L, 1.2, 2.3, 4.5f,
                    Long.MIN_VALUE, Long.MAX_VALUE
            },
            {
                    V(Object::equals),
                    USERDATA, STRING, STRING, "", "String"
            },
            {
                    V((o, l) -> {
                        ByteBuffer buffer = (ByteBuffer) o;
                        buffer.position(0); // resets
                        return o == l || l.toString().equals("a") || l.toString().length() == buffer.limit();
                    }),
                    USERDATA, USERDATA, STRING,
                    ByteBuffer.wrap(new byte[]{'a'}),
                    ByteBuffer.allocateDirect(0),
            },
            {
                    V((o, o2) -> o == null || o2 == null || o.equals(o2) || o2 instanceof LuaValue),
                    USERDATA, FUNCTION, FUNCTION,
                    (JFunction) l -> 0, (JFunction) l -> 1,
            },
            {
                    V((o, o2) -> o2 instanceof LuaValue
                            && ((LuaValue) o2).call(1)[0].toInteger() == 0xBEEF + 1),
                    FUNCTION, FUNCTION, FUNCTION,
                    (LuaFunction) (L, args) -> new LuaValue[]{L.from(args[0].toInteger() + 0xBEEF)},
            },
            {
                    V((o, list) -> {
                        if (o.getClass().isArray()) {
                            if (list instanceof Map) {
                                Map<?, ?> l = (Map<?, ?>) list;
                                return Array.getLength(o) == l.size();
                            } else if (list.getClass().isArray()) {
                                return Array.getLength(o) == Array.getLength(list);
                            }
                        }
                        return false;
                    }),
                    USERDATA, USERDATA, TABLE,
                    new int[]{}, new int[]{1, 2, 3, 4, 5, 6},
                    new String[]{"", "String"}
            },
            {
                    V((o, m) -> {
                        if (o instanceof Collection) {
                            if (m instanceof Collection) {
                                return ((Collection<?>) o).size() == ((Collection<?>) m).size();
                            } else if (m instanceof Map) {
                                return ((Collection<?>) o).size() == ((Map<?, ?>) m).size();
                            }
                        }
                        return false;
                    }),
                    USERDATA, USERDATA, TABLE,
                    Collections.synchronizedCollection(Collections.emptyList()),
                    Collections.singleton("A"),
                    Collections.singleton(Collections.singletonList("B")),
                    Arrays.asList(1, 2, 3, 4, 5)
            },
            {
                    V((o, m) -> {
                        if (o instanceof Map && m instanceof Map) {
                            return ((Map<?, ?>) o).size() == ((Map<?, ?>) m).size();
                        }
                        return false;
                    }),
                    USERDATA, USERDATA, TABLE,
                    Collections.emptyMap(),
                    Collections.singletonMap("A", "B"),
                    new HashMap<ArrayList<String>, String>()
            },
            {
                    V(Object::equals),
                    USERDATA, USERDATA, USERDATA,
                    Class.class, Integer.class
            },
            {
                    V(Object::equals),
                    USERDATA, USERDATA, USERDATA,
                    new BigInteger("9999999999999999999999999999999999999" +
                            "99999999999999999999999999999999999999999999999"),
                    new AtomicInteger(1),
                    System.out, Runtime.getRuntime(), new IllegalAccessError()
            },
    };

    @SuppressWarnings("unused")
    public static long passAlong(long value) {
        return value;
    }

    public interface PasserAlong {
        long passAlong(long value);
    }

}
