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

import org.jetbrains.annotations.NotNull;
import party.iroiro.luajava.Lua;

import java.nio.ByteBuffer;
import java.util.AbstractMap;
import java.util.Objects;
import java.util.Set;

/**
 * Basic implementation of a {@link LuaValue} on some Lua thread
 *
 * @param <T> the Lua thread type
 */
public abstract class AbstractLuaValue<T extends Lua>
        extends AbstractMap<LuaValue, LuaValue>
        implements LuaValue {
    protected final T L;
    protected final Lua.LuaType type;

    protected AbstractLuaValue(T L, Lua.LuaType type) {
        this.L = L;
        this.type = type;
    }

    @Override
    public Lua.LuaType type() {
        return type;
    }

    @Override
    public Lua state() {
        return L;
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }

    @NotNull
    @Override
    public Set<Entry<LuaValue, LuaValue>> entrySet() {
        throw new UnsupportedOperationException(type.toString());
    }

    @Override
    public int length() {
        throw new UnsupportedOperationException(type.toString());
    }

    @Override
    public LuaValue get(int i) {
        throw new UnsupportedOperationException(type.toString());
    }

    @Override
    public LuaValue get(String key) {
        throw new UnsupportedOperationException(type.toString());
    }

    @Override
    public LuaValue get(LuaValue key) {
        throw new UnsupportedOperationException(type.toString());
    }

    @Override
    public LuaValue set(int key, Object value) {
        throw new UnsupportedOperationException(type.toString());
    }

    @Override
    public LuaValue set(Object key, Object value) {
        throw new UnsupportedOperationException(type.toString());
    }

    @Override
    public LuaValue[] call(Object... parameters) {
        throw new UnsupportedOperationException(type.toString());
    }

    @Override
    public double toNumber() {
        return ((Number) Objects.requireNonNull(toJavaObject())).doubleValue();
    }

    @Override
    public long toInteger() {
        return ((Number) Objects.requireNonNull(toJavaObject())).longValue();
    }

    @Override
    public boolean toBoolean() {
        return toInteger() != 0;
    }

    @Override
    public ByteBuffer toBuffer() {
        push(L);
        ByteBuffer buffer = L.toBuffer(-1);
        L.pop(1);
        return buffer;
    }

    @Override
    public <I> I toProxy(Class<I> targetInterface) {
        @SuppressWarnings("unchecked")
        I proxy = (I) toProxy(new Class[]{targetInterface}, Lua.Conversion.SEMI);
        return proxy;
    }

    @Override
    public Object toProxy(Class<?>[] interfaces, Lua.Conversion degree) {
        push(L);
        return L.createProxy(interfaces, degree);
    }
}
