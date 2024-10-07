package party.iroiro.luajava.value;

import party.iroiro.luajava.Lua;
import party.iroiro.luajava.LuaException;
import party.iroiro.luajava.lua51.Lua51;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

import static org.junit.Assert.*;
import static party.iroiro.luajava.Lua.LuaType.*;
import static party.iroiro.luajava.LuaTestSuite.assertThrowsLua;

public class LuaValueSuite<T extends Lua> {
    private final T L;

    public LuaValueSuite(T L) {
        this.L = L;
    }

    public void test() {
        L.openLibraries();
        assertEquals(0, L.getTop());
        equalityTest(L);
        assertEquals(0, L.getTop());
        Lua J = L.newThread();
        L.pop(1);
        equalityTest(J);
        assertEquals(0, L.getTop());
        try (Lua K = new Lua51()) {
            equalityTest(K);
        }
        assertEquals(0, L.getTop());
        differentThreadTest();
        assertEquals(0, L.getTop());
        tableTest();
        assertEquals(0, L.getTop());
        tableMapTest();
        assertEquals(0, L.getTop());
        nilTest();
        assertEquals(0, L.getTop());
        stringTest();
        assertEquals(0, L.getTop());
        toStringTest();
        assertEquals(0, L.getTop());
        callTest();
        assertEquals(0, L.getTop());
        luaStateTest();
        assertEquals(0, L.getTop());
        bufferTest();
        assertEquals(0, L.getTop());
    }

    private void bufferTest() {
        ByteBuffer buffer = L.from(100).toBuffer();
        assertNotNull(buffer);
        assertEquals(0, buffer.position());
        assertEquals(3, buffer.remaining());
        assertEquals("100", StandardCharsets.UTF_8.decode(buffer).toString());
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    private void tableMapTest() {
        LuaValue map = L.eval("t = { 1, 2, 3, 4, a = 5, b = 6, c = 7, d = 8 }; return t")[0];
        assertEquals(8, map.entrySet().size());
        assertEquals(4, map.length());
        assertEquals(0, L.getTop());
        assertTrue(map.containsKey("a"));
        map.entrySet().removeIf(next -> next.getKey().toString().equals("a"));
        assertEquals(7, map.entrySet().size());
        assertEquals(4, map.length());
        assertEquals(0, L.getTop());
        L.run("assert(not t.a)");
        assertFalse(map.containsKey("a"));
        assertEquals(7, map.remove("c").toInteger());
        assertEquals(6, Objects.requireNonNull(map.put(L.from("b"), L.from(10))).toInteger());
        assertEquals(10, map.get("b").toInteger());
        assertEquals(1, map.get((Number) 1).toInteger());
        assertEquals(0, L.getTop());

        assertThrows(NoSuchElementException.class, () -> L.eval("return {}")[0].entrySet().iterator().next());
        assertThrows(IllegalStateException.class, () -> L.eval("return {}")[0].entrySet().iterator().remove());

        L.push(1);
        LuaValue i = L.get();
        assertThrows(UnsupportedOperationException.class, i::entrySet);
        assertThrows(UnsupportedOperationException.class, i::length);
        assertThrows(UnsupportedOperationException.class, () -> i.get(1));
        assertThrows(UnsupportedOperationException.class, () -> i.get("a"));
        assertThrows(UnsupportedOperationException.class, () -> i.get(L.from(1)));
        assertThrows(UnsupportedOperationException.class, () -> i.set(1, new Object()));
        assertThrows(UnsupportedOperationException.class, () -> i.set("", new Object()));
        assertThrows(UnsupportedOperationException.class, i::call);
        assertEquals(0, L.getTop());

        assertEquals(1, i.toNumber(), 0.000001);
        assertEquals(1, i.toInteger());
        assertTrue(i.toBoolean());
        L.push(0);
        LuaValue j = L.get();
        assertFalse(j.toBoolean());
        assertEquals(0, L.getTop());
    }

    private void toStringTest() {
        L.pushNil();
        assertEquals("nil", L.get().toString());
        L.push("string");
        assertEquals("string", L.get().toString());
        L.push((L, args) -> null);
        LuaValue v = L.get();
        assertTrue(v.toString(), v.toString().startsWith("RefLuaValue$FUNCTION@party.iroiro.luajava.lua"));

        assertEquals("test", L.from(ByteBuffer.wrap("test".getBytes(StandardCharsets.UTF_8))).toString());
    }

    private void stringTest() {
        LuaValue value = L.get("_VERSION");
        //noinspection SizeReplaceableByIsEmpty
        assertTrue(value.length() > 0);
        ByteBuffer buffer = value.toBuffer();
        byte[] bytes = new byte[buffer.limit()];
        buffer.get(bytes);
        assertArrayEquals(
                value.toString().getBytes(StandardCharsets.UTF_8),
                bytes
        );

        L.push(10);
        LuaValue i = L.get();
        assertThrows(UnsupportedOperationException.class, i::length);

        L.openLibrary("string");
        assertEquals('t', L.require("string").get("byte").call("test", 1)[0].toInteger());

        assertEquals("sss", L.from("sss").toString());
    }

    private void luaStateTest() {
        LuaValue value = L.get("java");
        assertEquals(TABLE, value.type());
        assertThrowsLua(LuaException.LuaError.SYNTAX, () -> L.eval("("));
    }

    private void callTest() {
        L.push((l) -> {
            int top = l.getTop();
            int sum = 0;
            for (int i = 1; i <= top; i++) {
                sum += (int) l.toNumber(i);
            }
            l.push(sum);
            return 1;
        });
        LuaValue func = L.get();
        LuaValue[] results = func.call(1, "2", 3., L.from(4), L.from("5"));
        assertEquals(1, results.length);
        assertEquals(NUMBER, results[0].type());
        assertEquals(15., results[0].toJavaObject());
        L.push((l) -> {
            l.push("Some error");
            return -1;
        });
        LuaValue func2 = L.get();
        assertThrowsLua(LuaException.LuaError.RUNTIME, func2::call, "Some error");
    }

    private void nilTest() {
        L.pushNil();
        LuaValue value = L.get();
        assertEquals(NIL, value.type());
        L.pushNil();
        value.push(L);
        assertTrue(L.equal(-1, -2));
        L.pop(2);
    }

    private void tableTest() {
        int top = L.getTop();
        assertEquals(0, L.getTop());
        LuaValue[] values = L.eval("return {1, 2, 3, a = 'b', c = 'd'}, 1024");
        assertEquals(2, values.length);
        LuaValue value = values[0];

        Map<?, ?> map = (Map<?, ?>) Objects.requireNonNull(value.toJavaObject());
        assertEquals(5, map.size());
        assertEquals(1., map.get(1.));
        assertEquals(2., map.get(2.));
        assertEquals(3., map.get(3.));
        assertEquals("b", map.get("a"));
        assertEquals("d", map.get("c"));
        assertEquals(0, L.getTop());

        assertEquals(top, L.getTop());
        assertEquals(1., value.get(1).toJavaObject());
        assertEquals(2., value.get(2).toJavaObject());
        assertEquals(3., value.get(3).toJavaObject());
        assertEquals("b", value.get("a").toJavaObject());
        assertEquals("d", value.get(L.from("c")).toJavaObject());
        value = L.eval("return {}")[0];
        value.set(1, L.from(1));
        value.set(2, L.from(2));
        value.set(L.from(3), L.from(3));
        value.set("a", L.from("b"));
        value.set(L.from("c"), L.from("d"));
        value.set(L.from(true), L.from(false));
        assertEquals(1., value.get(1).toJavaObject());
        assertEquals(2., value.get(2).toJavaObject());
        assertEquals(3., value.get(3).toJavaObject());
        assertEquals("b", value.get("a").toJavaObject());
        assertEquals("d", value.get(L.from("c")).toJavaObject());
        assertEquals(false, value.get(L.from(true)).toJavaObject());
        assertNotEquals(L.from(1), value);

        assertEquals(top, L.getTop());
    }

    private void differentThreadTest() {
        try (Lua K = new Lua51()) {
            L.pushNil();
            K.pushNil();
            assertThrows(UnsupportedOperationException.class, () -> L.get().get(K.get()));
            K.pushNil();
            K.get().push(L);
            assertEquals(NIL, L.type(-1));
            L.pop(1);

            K.pushNil();
            LuaValue nil = K.get();
            L.push(nil, Lua.Conversion.NONE);
            assertTrue(L.isNil(-1));
            L.pop(1);
            Lua J = L.newThread();
            L.pop(1);
            J.pushNil();
            L.push(J.get(), Lua.Conversion.NONE);
            assertTrue(L.isNil(-1));
            L.push(L.get(), Lua.Conversion.NONE);
            assertTrue(L.isNil(-1));
            L.pop(1);
        }
        L.createTable(0, 0);
        LuaValue value = L.get();
        value.push(L);
        value.push(L);
        assertTrue(L.equal(-1, -2));
        L.pop(2);
        try (Lua K = new Lua51()) {
            assertThrowsLua(LuaException.LuaError.MEMORY, () -> value.push(K),
                    "Unable to pass Lua values between different Lua states");
        }
    }

    private void equalityTest(Lua K) {
        int top = L.getTop();
        L.pushNil();
        assertNotEquals(ImmutableLuaValue.NIL(K), L.get());
        L.push(true);
        assertNotEquals(ImmutableLuaValue.TRUE(K), L.get());
        L.push(false);
        assertNotEquals(ImmutableLuaValue.FALSE(K), L.get());
        L.push(100);
        assertNotEquals(ImmutableLuaValue.NUMBER(K, 100), L.get());
        L.push("string content");
        assertNotEquals(ImmutableLuaValue.STRING(K, "string content"), L.get());

        assertNotEquals(K.from(1), L.from(false));
        assertNotEquals(L.from(1), L.from(2));
        assertNotEquals(K.from(1), new Object());

        AbstractLuaValue<Lua> mock = new AbstractLuaValue<Lua>(L, NUMBER) {
            @Override
            public void push(Lua L) {
                L.push(2);
            }

            @Override
            public Object toJavaObject() {
                return null;
            }

            @Override
            public Lua.LuaType type() {
                return NUMBER;
            }
        };
        assertNotEquals(L.from(1), mock);

        L.push(Collections.emptyList());
        K.push(Collections.emptyList());
        LuaValue l = L.get();
        LuaValue k = K.get();
        l.push(L);
        LuaValue j = L.get();
        assertNotEquals(l, j);
        assertNotEquals(j, k);
        assertNotEquals(l, L.from(1));
        AbstractLuaValue<Lua> mock1 = new AbstractLuaValue<Lua>(L, TABLE) {
            @Override
            public void push(Lua L) {
                L.push(Collections.emptyList());
            }

            @Override
            public Object toJavaObject() {
                return null;
            }

            @Override
            public Lua.LuaType type() {
                return TABLE;
            }
        };
        assertNotEquals(l, mock1);
        //noinspection EqualsWithItself
        assertEquals(l, l);
        assertEquals(top, L.getTop());
    }
}
