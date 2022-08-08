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

import java.util.Objects;

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
    public void close() {
        // nothing
    }

    @Override
    public @Nullable Object toJavaObject() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && o instanceof ImmutableLuaValue
                && Objects.equals(value, ((ImmutableLuaValue<?>) o).value);
    }

    public static LuaValue NIL(Lua L) {
        return new ImmutableLuaValue<Void>(L, Lua.LuaType.NIL, null) {
            @Override
            public void push() {
                L.pushNil();
            }
        };
    }

    private static class ImmutableBoolean extends ImmutableLuaValue<Boolean> {
        private ImmutableBoolean(Lua L, Boolean value) {
            super(L, Lua.LuaType.BOOLEAN, value);
        }

        @Override
        public void push() {
            L.push(value);
        }
    }

    public static LuaValue TRUE(Lua L) {
        return new ImmutableBoolean(L, true);
    }

    public static LuaValue FALSE(Lua L) {
        return new ImmutableBoolean(L, false);
    }

    private static class ImmutableNumber extends ImmutableLuaValue<Double> {
        private ImmutableNumber(Lua L, Double value) {
            super(L, Lua.LuaType.NUMBER, value);
        }

        @Override
        public void push() {
            L.push(value);
        }
    }

    private static class ImmutableString extends ImmutableLuaValue<String> {
        private ImmutableString(Lua L, String value) {
            super(L, Lua.LuaType.STRING, value);
        }

        @Override
        public void push() {
            L.push(value);
        }
    }

    public static LuaValue NUMBER(Lua L, double n) {
        return new ImmutableNumber(L, n);
    }

    public static LuaValue STRING(Lua L, String s) {
        return new ImmutableString(L, s);
    }
}
