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

package party.iroiro.luajava.value;

import org.jetbrains.annotations.Nullable;
import party.iroiro.luajava.Lua;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Basic {@link LuaValue} implementation wrapping immutable values
 * @param <T> the value type
 */
public abstract class ImmutableLuaValue<T> extends AbstractLuaValue<Lua> {
    protected final T value;

    protected ImmutableLuaValue(Lua L, Lua.LuaType type, T value) {
        super(L, type);
        this.value = value;
    }

    @Override
    public String toString() {
        if (value == null) {
            return "nil";
        }
        return value.toString();
    }

    @Override
    public @Nullable Object toJavaObject() {
        return value;
    }

    public static LuaValue NIL(Lua L) {
        return new ImmutableLuaValue<Void>(L, Lua.LuaType.NIL, null) {
            @Override
            public void push(Lua L) {
                L.pushNil();
            }
        };
    }

    private static final class ImmutableBoolean extends ImmutableLuaValue<Boolean> {
        private ImmutableBoolean(Lua L, Boolean value) {
            super(L, Lua.LuaType.BOOLEAN, value);
        }

        @Override
        public void push(Lua L) {
            L.push(value);
        }
    }

    public static LuaValue TRUE(Lua L) {
        return new ImmutableBoolean(L, true);
    }

    public static LuaValue FALSE(Lua L) {
        return new ImmutableBoolean(L, false);
    }

    private static final class ImmutableNumber extends ImmutableLuaValue<Double> {
        private ImmutableNumber(Lua L, Double value) {
            super(L, Lua.LuaType.NUMBER, value);
        }

        @Override
        public void push(Lua L) {
            L.push(value);
        }
    }

    private static final class ImmutableLong extends ImmutableLuaValue<Long> {
        private ImmutableLong(Lua L, Long value) {
            super(L, Lua.LuaType.NUMBER, value);
        }

        @Override
        public void push(Lua L) {
            L.push((long) value);
        }

        @Override
        public Object toJavaObject() {
            return value.doubleValue();
        }
    }

    private static final class ImmutableString extends ImmutableLuaValue<ByteBuffer> {
        @Nullable
        private String javaString;

        private static ByteBuffer wrap(byte[] bytes) {
            ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length);
            buffer.put(bytes);
            buffer.flip();
            return buffer.asReadOnlyBuffer();
        }

        private ImmutableString(Lua L, ByteBuffer value) {
            super(L, Lua.LuaType.STRING, value);
            javaString = null;
        }

        private ImmutableString(Lua L, String value) {
            super(L, Lua.LuaType.STRING, wrap(value.getBytes(StandardCharsets.UTF_8)));
            javaString = value;
        }

        @Override
        public void push(Lua L) {
            L.push(value);
        }

        @Override
        public int length() {
            return value.limit();
        }

        @Override
        public Object toJavaObject() {
            return toString();
        }

        @Override
        public String toString() {
            if (javaString == null) {
                javaString = StandardCharsets.UTF_8.decode(value.duplicate()).toString();
            }
            return javaString;
        }

        @Override
        public ByteBuffer toBuffer() {
            return value.duplicate();
        }
    }

    public static LuaValue NUMBER(Lua L, double n) {
        return new ImmutableNumber(L, n);
    }

    public static LuaValue LONG(Lua L, long n) {
        return new ImmutableLong(L, n);
    }

    public static LuaValue STRING(Lua L, String s) {
        return new ImmutableString(L, s);
    }

    public static LuaValue BUFFER(Lua L, ByteBuffer buffer) {
        ByteBuffer directBuffer = ByteBuffer.allocateDirect(buffer.limit());
        buffer.position(0);
        directBuffer.put(buffer);
        directBuffer.flip();
        return new ImmutableString(L, directBuffer.asReadOnlyBuffer());
    }
}
