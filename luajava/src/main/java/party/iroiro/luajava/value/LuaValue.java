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
import party.iroiro.luajava.LuaException;

import java.nio.ByteBuffer;

/**
 * A simple wrapper of references to Lua values
 */
public interface LuaValue extends LuaTableTrait {
    /**
     * @return the type of the Lua value
     */
    Lua.LuaType type();

    /**
     * @return the Lua state where this Lua value lives
     */
    Lua state();

    /**
     * Pushes the Lua value onto the Lua stack of another thread sharing the same main state
     *
     * @param L another thread
     */
    void push(Lua L) throws LuaException;

    /**
     * Performs {@code thisLuaValue(parameter1, parameter2, ...)}
     *
     * @param parameters the parameters
     * @return the return values, {@code null} on error
     */
    LuaValue[] call(Object... parameters);

    /**
     * @return a Java value converted from this Lua value
     * @see Lua#toObject(int)
     */
    @Nullable
    Object toJavaObject();

    boolean toBoolean();

    long toInteger();

    double toNumber();

    String toString();

    ByteBuffer toBuffer();

    /**
     * Creates a proxy from this value with {@link Lua#createProxy(Class[], Lua.Conversion)}.
     *
     * @param targetInterface the interfaces the proxy should implement.
     * @return the proxy object
     */
    <T> T toProxy(Class<T> targetInterface);

    /**
     * Creates a proxy from this value with {@link Lua#createProxy(Class[], Lua.Conversion)}.
     *
     * @param interfaces the interfaces the proxy should implement.
     * @param degree the conversion used
     * @return the proxy object
     */
    Object toProxy(Class<?>[] interfaces, Lua.Conversion degree);

}
