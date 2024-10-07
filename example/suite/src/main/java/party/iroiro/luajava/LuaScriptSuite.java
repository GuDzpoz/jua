package party.iroiro.luajava;

import party.iroiro.luajava.interfaces.LuaTestConsumer;
import party.iroiro.luajava.lua51.Lua51Natives;
import party.iroiro.luajava.lua52.Lua52Natives;
import party.iroiro.luajava.lua53.Lua53Natives;
import party.iroiro.luajava.lua54.Lua54Natives;
import party.iroiro.luajava.luajit.LuaJitNatives;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.Buffer;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.*;
import static party.iroiro.luajava.DefaultProxyTest.instanceOfLuaJ;
import static party.iroiro.luajava.LuaTestSuite.assertThrowsLua;

public class LuaScriptSuite<T extends AbstractLua> {
    private static final String LUA_ASSERT_THROWS = "\n" +
            "function assertThrows(message, fun, ...)\n" +
            "  ok, msg = pcall(fun, ...)\n" +
            "  messages = type(message) == 'string' and { message } or message\n" +
            "  message = '\"' .. table.concat(messages, ', ') .. '\"'\n" +
            "  assert(not ok, debug.traceback('No error while expecting ' .. message))\n" +
            "  assert(type(msg) == 'string', debug.traceback('Expecting error message on top of the stack'))\n" +
            "  for _, m in ipairs(messages) do\n" +
            "    if string.find(msg, m) ~= nil then\n" +
            "      return\n" +
            "    end\n" +
            "  end\n" +
            "  assert(false, debug.traceback('Expecting ' .. message .. ': Received \"' .. msg .. '\"'))\n" +
            "end";
    private final T L;

    public LuaScriptSuite(T L) {
        this.L = L;
        addAssertThrows(L);
        L.openLibrary("package");
        L.setExternalLoader(new ClassPathLoader());
    }

    public static void addAssertThrows(Lua L) {
        L.openLibrary("string");
        L.openLibrary("debug");
        L.openLibrary("table");
        L.run(LUA_ASSERT_THROWS);
        L.push(DefaultProxyTest.isDefaultAvailable() && !(instanceOfLuaJ(L)));
        L.setGlobal("JAVA8");
        L.push(instanceOfLuaJ(L));
        L.setGlobal("LUAJ");

        // Android: desugar: default methods are separated into another class, failing the tests
        L.push(isAndroid());
        L.setGlobal("ANDROID");
    }

    public static boolean isAndroid() {
        try {
            Class.forName("android.os.Build");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static final ScriptTester[] TESTERS = {
            new ScriptTester("/suite/numberConvTest.lua", L -> {
                L.push(new Numbers(), Lua.Conversion.NONE);
                L.setGlobal("numbers");
            }),
            new ScriptTester("/suite/otherConvTest.lua", L -> {
                L.push(new OtherTypes(), Lua.Conversion.NONE);
                L.setGlobal("others");
                LuaNatives C = L.getLuaNatives();
                if (C instanceof Lua51Natives) {
                    ((Lua51Natives) C).lua_newuserdata(L.getPointer(), 1024);
                } else if (C instanceof Lua52Natives) {
                    ((Lua52Natives) C).lua_newuserdata(L.getPointer(), 1024);
                } else if (C instanceof Lua53Natives) {
                    ((Lua53Natives) C).lua_newuserdata(L.getPointer(), 1024);
                } else if (C instanceof Lua54Natives) {
                    ((Lua54Natives) C).lua_newuserdatauv(L.getPointer(), 1024, 0);
                } else if (C instanceof LuaJitNatives) {
                    ((LuaJitNatives) C).lua_newuserdata(L.getPointer(), 1024);
                } else if (C.getClass().getName().endsWith("LuaJNatives")) {
                    // This is tested instead in NativesTest,
                    // because our Android build has trouble desugaring and cannot run LuaJ.
                    L.pushJavaObject(new Object());
                } else {
                    fail("Not a supported natives");
                }
                L.setGlobal("myuserdata");
            }),
            new ScriptTester("/suite/proxyTest.lua", L -> {
            }),
            new ScriptTester("/suite/importTest.lua", L -> {
                OtherTypes others = new OtherTypes();
                others.array1 = new Object[] {
                        new Object[] { 1., 2., 3. },
                        new Object[] { 4., 5., 6. },
                        7.,
                };
                others.array2 = new int[] {
                        8, 9, 10, 11, 12,
                };
                L.set("others", others);
            }),
            new ScriptTester("/suite/luaifyTest.lua", L -> {
            }),
            new ScriptTester("/suite/threadSimpleTest.lua", L -> {
            }),
            new ScriptTester("/suite/arrayTest.lua", L -> {
                L.pushJavaArray(new int[]{1, 2, 3, 4, 5});
                L.setGlobal("arr");
                assertEquals(-1, JuaAPI.arrayNewIndex(L.getId(), null, 0));
                assertEquals(-1, JuaAPI.arrayLength(""));
            }),
            new ScriptTester("/suite/invokeTest.lua", L -> {
                Object o = null;
                //noinspection ConstantConditions
                assertEquals(-1, JuaAPI.objectInvoke(L.getId(), o, null, 0));
                assertTrue(Objects.requireNonNull(L.toString(-1)).contains("expecting a JFunction"));
                L.pushJavaClass(AbstractClass.class);
                L.setGlobal("Abstract");
                L.pushJavaClass(PrivateClass.class);
                L.setGlobal("Private");
                L.pushJavaClass(ThrowsClass.class);
                L.setGlobal("Throws");
                try {
                    assertEquals(-1, JuaAPI.methodInvoke(L,
                            PrivateClass.class.getDeclaredMethod("privateFunc"), null, null));
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }),
            new ScriptTester("/suite/signatureTest.lua", L -> {
            }),
            new ScriptTester("/suite/indexTest.lua", L -> {
                L.pushJavaClass(StaticClass.class);
                L.setGlobal("Static");
            }),
            new ScriptTester("/suite/moduleTest.lua", L -> {
                // Re-opening `package` no more resets our external loader
                L.openLibrary("package");
                L.openLibraries();
                L.setExternalLoader(new ClassPathLoader());
                assertThrowsLua(L, "require('suite.not.a.module')", LuaException.LuaError.RUNTIME);
            }),
            new ScriptTester("/suite/apiTest.lua", L -> {
                L.pushThread();
                L.setGlobal("currentThread");
            }),
            new ScriptTester("/suite/mapProxy.lua", L -> {
            }),
            new ScriptTester("/suite/glibcTest.lua", L -> {
                L.openLibrary("math");
                L.run("return math.pow");
                if (L.isNil(-1)) {
                    // Lua 5.3, Lua 5.4 compatibility
                    L.run("math.pow = function(x, y) return x ^ y end");
                }
                L.pop(1);
            }),
            new ScriptTester("/suite/compatTest.lua", L ->
                    L.setExternalLoader(new ClassPathLoader())),
            new ScriptTester("/suite/coroutineTest.lua", L -> {}),
    };

    public void test() {
        L.openLibrary("coroutine");
        for (ScriptTester tester : TESTERS) {
            try {
                tester.test(L);
            } catch (Throwable e) {
                throw new RuntimeException(tester.file + "\n" + L.toString(-1), e);
            }
        }
    }

    public static class ScriptTester {
        public final String file;
        private final LuaTestConsumer<AbstractLua> init;

        public ScriptTester(String file, LuaTestConsumer<AbstractLua> init) {
            this.file = file;
            this.init = init;
        }

        public void test(AbstractLua L) throws IOException {
            init.accept(L);
            assertTrue(file.endsWith(".lua"));
            L.loadExternal(file.substring(1, file.length() - 4).replace('/', '.'));
            try {
                L.pCall(0, Consts.LUA_MULTRET);
            } catch (Throwable e) {
                throw new RuntimeException(L.toString(-1), e);
            }
        }
    }

    @SuppressWarnings("unused")
    public static class Numbers {
        public char c = 'c';
        public byte b = 1;
        public short s = 2;
        public int i = 3;
        public long l = 4;
        public float f = 5;
        public double d = 6;
        public Character cc = 'C';
        public Byte bb = 7;
        public Short ss = 8;
        public Integer ii = 9;
        public Long ll = 10L;
        public Float ff = 11.f;
        public Double dd = 12.;
        public boolean bool = false;
        public Boolean BOOL = false;
        public BigInteger big = new BigInteger("1024");
    }

    @SuppressWarnings("unused")
    public static class OtherTypes {
        public int i = 1;
        public String s = "2";
        public BigInteger big = new BigInteger("1024");
        public Collection<Object> collection = null;
        public Object[] array1 = null;
        public int[] array2 = null;
        public Map<Object, Object> map = null;
        public Override annotation;
        public Runnable intf;
        public Buffer buffer;

        public Object any;
    }

    public static abstract class AbstractClass {
        @SuppressWarnings("unused")
        public static Object returnsNull() {
            return null;
        }
    }

    public static class PrivateClass {
        private PrivateClass() {
        }

        private static void privateFunc() {
        }
    }

    public static class ThrowsClass {
        public ThrowsClass() throws Exception {
            throw new Exception();
        }

        @SuppressWarnings("unused")
        public static void throwsFunc() throws Exception {
            throw new Exception();
        }
    }

    public static class StaticClass {
        public static int i = 0;
    }

    public static void memoryTest(Lua L) {
        L.openLibrary("package");
        L.openLibrary("coroutine");
        L.openLibrary("string");
        L.setExternalLoader(new ClassPathLoader());
        L.loadExternal("luajava.testMemory");
        L.pCall(0, Consts.LUA_MULTRET);
    }
}
