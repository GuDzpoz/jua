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
import party.iroiro.luajava.value.LuaFunction;
import party.iroiro.luajava.value.LuaThread;
import party.iroiro.luajava.value.LuaValue;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * A {@code lua_State *} wrapper, representing a Lua thread
 *
 * <p>
 * Most methods in this interface are wrappers around the corresponding Lua C API functions,
 * and requires a certain degree of familiarity with the Lua C API.
 * If you are not that familiar with the Lua C API, you may want to read the Lua manual first
 * or try out {@link LuaValue}-related API at the {@link LuaThread} interface.
 * </p>
 */
public interface Lua extends AutoCloseable, LuaThread {
    String GLOBAL_THROWABLE = "__jthrowable__";

    /**
     * Ensures that there are at least {@code extra} free stack slots in the Lua stack
     *
     * <p>
     * It wraps {@code lua_checkstack}.
     * </p>
     *
     * @param extra the extra slots to ensure
     * @throws RuntimeException when unable to grow the stack
     */
    void checkStack(int extra) throws RuntimeException;

    /* Push-something functions */

    /**
     * Push an object onto the stack, converting according to {@link Conversion}.
     *
     * @param object the object to be pushed onto the stack
     * @param degree how the object is converted into lua values
     * @see Conversion
     */
    void push(@Nullable Object object, Conversion degree);

    /**
     * Pushes a {@code nil} value onto the stack
     */
    void pushNil();

    /**
     * Pushes a boolean value onto the stack
     *
     * @param bool the boolean value
     */
    void push(boolean bool);

    /**
     * Pushes a floating-point number onto the stack
     *
     * @param number the number, whose {@link Number#doubleValue()} will be pushed
     */
    void push(@NotNull Number number);

    /**
     * Pushes an integer onto the stack
     *
     * <p>
     * Please note that on some 32-bit platforms, 64-bit integers are likely to get
     * truncated instead of getting approximated into a floating-point number.
     * If you want to approximate an integer, cast it to double and use {@link #push(Number)}.
     * </p>
     *
     * @param integer the number
     */
    void push(long integer);

    /**
     * Pushes a string onto the stack.
     *
     * @param string the string
     */
    void push(@NotNull String string);

    /**
     * Pushes a buffer as a raw string onto the stack
     *
     * <p>
     * The pushed bytes are from buffer[buffer.position()] to buffer[buffer.limit() - 1].
     * So remember to call {@link ByteBuffer#flip()} or set the position and limit before pushing.
     * </p>
     *
     * @param buffer the buffer, which might contain invalid UTF-8 characters and zeros
     */
    void push(@NotNull ByteBuffer buffer);

    /**
     * Push the element onto the stack, converted to lua tables
     *
     * <p>
     * Inner elements are converted with {@link Conversion#FULL}.
     * </p>
     *
     * @param map the element to be pushed onto the stack
     */
    void push(@NotNull Map<?, ?> map);

    /**
     * Push the element onto the stack, converted to lua tables (index starting from 1)
     *
     * <p>
     * Inner elements are converted with {@link Conversion#FULL}.
     * </p>
     *
     * @param collection the element to be pushed onto the stack
     */
    void push(@NotNull Collection<?> collection);

    /**
     * Push an array onto the stack, converted to luatable
     *
     * @param array a array
     * @throws IllegalArgumentException when the object is not array
     */
    void pushArray(@NotNull Object array) throws IllegalArgumentException;

    /**
     * Push the function onto the stack, converted to a callable element
     *
     * <p>
     * The function is wrapped into a C closure, which means Lua will
     * treat the function as a C function. Checking {@link #isFunction(int)} on the pushed
     * element will return true.
     * </p>
     *
     * @param function the function to be pushed onto the stack
     */
    void push(@NotNull JFunction function);

    /**
     * Push a class onto the stack, which may be used with `java.new` on the lua side
     *
     * @param clazz the class
     */
    void pushJavaClass(@NotNull Class<?> clazz);

    /**
     * Push a {@link LuaValue} onto the stack, equivalent to {@link LuaValue#push(Lua)}
     *
     * @param value the value
     */
    void push(@NotNull LuaValue value);

    /**
     * Push the function onto the stack, converted to a callable element
     *
     * @param value the function
     * @see #push(JFunction)
     */
    void push(@NotNull LuaFunction value);

    /**
     * Push the element onto the stack, converted as is to Java objects
     *
     * @param object the element to be pushed onto the stack
     * @throws IllegalArgumentException when argument is {@code null} or an array
     */
    void pushJavaObject(@NotNull Object object);

    /**
     * Push the element onto the stack, converted as is to Java arrays
     *
     * @param array the element to be pushed onto the stack
     * @throws IllegalArgumentException when argument is {@code null} or a non-array object
     */
    void pushJavaArray(@NotNull Object array);

    /* Convert-something (into Java) functions */

    /**
     * Converts the Lua value at the given acceptable index to a number
     *
     * <p>
     * The Lua value must be a number or a string convertible to a number; otherwise,
     * {@code lua_tonumber} returns 0.
     * </p>
     *
     * @param index the stack index
     * @return the converted value, zero if not convertible
     */
    double toNumber(int index);

    /**
     * Converts the Lua value at the given acceptable index to the signed integral type lua_Integer
     *
     * <p>
     * The Lua value must be a number or a string convertible to a number; otherwise,
     * {@code lua_tointeger} returns 0. If the number is not an integer, it is truncated
     * in some non-specified way.
     * </p>
     *
     * @param index the stack index
     * @return the converted value, zero if not convertible
     */
    long toInteger(int index);

    /**
     * Converts the Lua value at the given acceptable index to a boolean value
     *
     * <p>
     * Like all tests in Lua, {@code lua_toboolean} returns 1 for any Lua value different from
     * {@code false} and {@code nil}; otherwise it returns 0.
     * It also returns 0 when called with a non-valid index.
     * </p>
     *
     * @param index the stack index
     * @return the converted value, {@code false} with and only with {@code false}, {@code nil} or {@code TNONE}
     */
    boolean toBoolean(int index);

    /**
     * Automatically converts a value into a Java object
     *
     * <ol>
     * <li><strong><em>nil</em></strong> is converted to <code>null</code>.</li>
     * <li><strong><em>boolean</em></strong> converted to <code>boolean</code> or the boxed <code>Boolean</code>.</li>
     * <li><strong><em>integer</em></strong> / <strong><em>number</em></strong> to any of <code>char</code> <code>byte</code> <code>short</code> <code>int</code> <code>long</code> <code>float</code> <code>double</code> or their boxed alternative.</li>
     * <li><strong><em>string</em></strong> to <code>String</code>.</li>
     * <li><strong><em>table</em></strong> to <code>Map&lt;Object, Object&gt;</code>, converted recursively.</li>
     * <li><strong><em>jclass</em></strong> to <code>Class&lt;?&gt;</code>.</li>
     * <li><strong><em>jobject</em></strong> to the underlying Java object.</li>
     * <li>Other types are not converted and are <code>null</code> on the Java side.</li>
     * </ol>
     *
     * @param index the stack index
     * @return the converted object, {@code null} if unable to converted
     */
    @Nullable
    Object toObject(int index);

    /**
     * Converts a value at the stack index
     *
     * @param index the stack index
     * @param type  the target type
     * @return the converted value, {@code null} if unable to converted
     * @see #toObject(int)
     */
    @Nullable
    Object toObject(int index, Class<?> type);

    /**
     * Converts the Lua value at the given acceptable index to a string
     *
     * <p>
     * The Lua value must be a string or a number; otherwise, the function returns NULL.
     * If the value is a number, then lua_tolstring <i>also changes the actual value</i>
     * in the stack to a string.
     * </p>
     *
     * @param index the stack index
     * @return the converted string
     */
    @Nullable
    String toString(int index);

    /**
     * Creates a {@link java.nio.ByteBuffer} from the string at the specific index
     *
     * <p>
     * You may want to use this instead of {@link #toString(int)} when the string is binary
     * (e.g., those returned by {@code string.dump} and contains null characters).
     * </p>
     *
     * @param index the stack index
     * @return the created buffer
     */
    @Nullable
    ByteBuffer toBuffer(int index);

    /**
     * Creates a read-only direct {@link java.nio.ByteBuffer} from the string at the specific index
     *
     * <p>
     * The memory of this buffer is managed by Lua.
     * So you should never use the buffer after popping the corresponding value
     * from the Lua stack.
     * </p>
     *
     * @param index the stack index
     * @return the created read-only buffer
     */
    @Nullable
    ByteBuffer toDirectBuffer(int index);

    /**
     * Get the element at the specified stack position, if the element is a Java object / array / class
     *
     * @param index the stack position of the element
     * @return the Java object or null
     */
    @Nullable
    Object toJavaObject(int index);

    /**
     * Get the element at the specified stack position, converted to a {@link Map}
     *
     * <p>
     * The element may be a Lua table or a Java {@link Map}, or else, it returns null.
     * </p>
     *
     * @param index the stack position of the element
     * @return the map or null
     */
    @Nullable
    Map<?, ?> toMap(int index);

    /**
     * Get the element at the specified stack position, converted to {@link List}
     *
     * <p>
     * The element may be a Lua table or a Java {@link List}, or else, it returns null.
     * </p>
     *
     * @param index the stack position of the element
     * @return the list or null
     */
    @Nullable
    List<?> toList(int index);

    /* Type-checking function */

    /**
     * Returns true if the value at the given index is a boolean, and false otherwise
     *
     * @param index the stack index
     * @return true if the value at the given index is a boolean, and false otherwise
     */
    boolean isBoolean(int index);

    /**
     * Returns true if the value at the given index is a function (either C or Lua), and false otherwise
     *
     * <p>
     * When one pushes a {@link JFunction} onto the stack using {@link #push(JFunction)},
     * the {@link JFunction} is wrapped into a C closure, so that it is treated as a C function in Lua.
     * </p>
     *
     * @param index the stack index
     * @return true if the value at the given index is a function (either C or Lua), and false otherwise
     */
    boolean isFunction(int index);

    /**
     * Checks if the element is a Java object
     *
     * <p>
     * Note that a {@link JFunction} pushed with {@link #push(JFunction)} is not a Java object
     * any more, but a C function.
     * </p>
     *
     * @param index the element to check type for
     * @return {@code true} if the element is a Java object, a Java class, or a Java array
     */
    boolean isJavaObject(int index);

    /**
     * Returns true if the value at the given index is nil, and false otherwise
     *
     * @param index the stack index
     * @return true if the value at the given index is nil, and false otherwise
     */
    boolean isNil(int index);

    /**
     * Returns true if the given index is not valid, and false otherwise
     *
     * @param index the stack index
     * @return true if the given index is not valid, and false otherwise
     */
    boolean isNone(int index);

    /**
     * Returns true if the given index is not valid or if the value at this index is nil, and false otherwise
     *
     * @param index the stack index
     * @return true if the given index is not valid or if the value at this index is nil, and false otherwise
     */
    boolean isNoneOrNil(int index);

    /**
     * Returns true if the value is a number or a string convertible to a number, and false otherwise
     *
     * @param index the stack index
     * @return true if the value is a number or a string convertible to a number, and false otherwise
     */
    boolean isNumber(int index);

    /**
     * Returns true if the value at the given index is an integer, and false otherwise
     *
     * <p>
     * (that is, the value is a number and is represented as an integer)
     * </p>
     *
     * @param index the stack index
     * @return true if the value is an integer, and false otherwise
     */
    boolean isInteger(int index);

    /**
     * Returns true if the value at the given index is a string or a number
     *
     * @param index the stack index
     * @return true if the value at the given index is a string or a number, and false otherwise.
     */
    boolean isString(int index);

    /**
     * Returns true if the value at the given index is a table, and false otherwise.
     *
     * @param index the stack index
     * @return Returns true if the value at the given index is a table, and false otherwise.
     */
    boolean isTable(int index);

    /**
     * Returns true if the value at the given index is a thread, and false otherwise.
     *
     * @param index the stack index
     * @return true if the value at the given index is a thread, and 0 otherwise.
     */
    boolean isThread(int index);

    /**
     * Returns true if the value at the given index is userdata (either full or light), and false otherwise.
     *
     * @param index the stack index
     * @return true if the value at the given index is userdata (either full or light), and false otherwise.
     */
    boolean isUserdata(int index);

    /**
     * @param index the element to inspect
     * @return the lua type of the element, {@code null} if unrecognized (in, for example, incompatible lua versions)
     */
    @Nullable
    LuaType type(int index);

    /* Measuring functions */

    /**
     * Returns true if the two values in acceptable indices i1 and i2 are equal
     *
     * <p>
     * Returns true if the two values in acceptable indices index1 and index2 are equal,
     * following the semantics of the Lua == operator (that is, may call metamethods).
     * Otherwise returns false. Also returns false if any of the indices is non valid.
     * </p>
     *
     * @param i1 the index of the first element
     * @param i2 the index of the second element
     * @return true if the two values in acceptable indices i1 and i2 are equal
     */
    boolean equal(int i1, int i2);

    /**
     * Returns the raw "length" of the value at the given index
     *
     * <p>
     * For strings, this is the string length;
     * for tables, this is the result of the length operator ('#') with no metamethods;
     * for userdata, this is the size of the block of memory allocated for the userdata.
     * For other values, this call returns 0.
     * </p>
     *
     * @param index the stack index
     * @return the raw length of the element
     */
    int rawLength(int index);

    /**
     * Returns true if the value at acceptable index i1 is smaller than the value at i2
     *
     * <p>
     * It follows the semantics of the Lua &lt; operator (that is, may call metamethods).
     * Otherwise returns false. Also returns false if any of the indices is non valid.
     * </p>
     *
     * @param i1 the index of the first element
     * @param i2 the index of the second element
     * @return true if the value at acceptable index i1 is smaller than the value at i2
     */
    boolean lessThan(int i1, int i2);

    /**
     * Returns true if the two values in acceptable indices i1 and i2 are primitively equal
     *
     * <p>
     * It does not call metamethods.
     * Otherwise returns false. Also returns false if any of the indices are non valid.
     * </p>
     *
     * @param i1 the index of the first element
     * @param i2 the index of the second element
     * @return true if the two values in acceptable indices i1 and i2 are primitively equal
     */
    boolean rawEqual(int i1, int i2);

    /* Other stack manipulation functions */

    /**
     * Returns the index of the top element in the stack
     *
     * <p>
     * Because indices start at 1, this result is equal to the number of elements in the stack
     * (and so 0 means an empty stack).
     * </p>
     *
     * @return the index of the top element in the stack
     */
    int getTop();

    /**
     * Accepts any index, or 0, and sets the stack top to this index
     *
     * <p>
     * If the new top is greater than the old one, then the new elements are filled with nil.
     * If index is 0, then all stack elements are removed.
     * </p>
     *
     * @param index the new top element index
     */
    void setTop(int index);

    /**
     * Moves the top element into the given valid index, shifting up the elements above this index
     *
     * <p>
     * Moves the top element into the given valid index,
     * shifting up the elements above this index to open space.
     * Cannot be called with a pseudo-index,
     * because a pseudo-index is not an actual stack position.
     * </p>
     *
     * @param index the non-pseudo index
     * @see #pushValue(int)
     * @see #replace(int)
     */
    void insert(int index);

    /**
     * Pops n elements from the stack
     *
     * @param n the number of elements to pop
     */
    void pop(int n);

    /**
     * Pushes a copy of the element at the given valid index onto the stack
     *
     * @param index the index of the element to be copied
     * @see #insert(int)
     * @see #replace(int)
     */
    void pushValue(int index);

    /**
     * Pushes the current thread onto the stack
     */
    void pushThread();

    /**
     * Removes the element at the given valid index
     *
     * <p>
     * Removes the element at the given valid index,
     * shifting down the elements above this index to fill the gap.
     * Cannot be called with a pseudo-index,
     * because a pseudo-index is not an actual stack position.
     * </p>
     *
     * @param index the index of the element to be removed
     */
    void remove(int index);

    /**
     * Moves the top element into the given position (and pops it)
     *
     * <p>
     * Moves the top element into the given position (and pops it),
     * without shifting any element (therefore replacing the value at the given position).
     * </p>
     *
     * @param index the index to move to
     * @see #insert(int)
     * @see #pushValue(int)
     */
    void replace(int index);

    /**
     * Exchange values between different threads of the same global state
     *
     * <p>
     * This function pops n values from the stack of this thread,
     * and pushes them onto the stack of the other thread
     * </p>
     *
     * @param other the thread to move the n values to
     * @param n     the number of elements to move
     * @throws IllegalArgumentException when the two threads do not belong to the same global state
     */
    void xMove(Lua other, int n) throws IllegalArgumentException;

    /* Executing functions */

    /**
     * Loads a string as a Lua chunk
     *
     * <p>
     * This function eventually uses {@code lua_load} to load the chunk in the string {@code script}.
     * <strong>Also as lua_load, this function only loads the chunk; it does not run it.</strong>
     * </p>
     *
     * @param script the Lua chunk
     * @see #run(String)
     * @see #pCall(int, int)
     */
    void load(String script) throws LuaException;

    /**
     * Loads a buffer as a Lua chunk
     *
     * <p>
     * This function eventually uses {@code lua_load} to load the chunk in the string {@code script}.
     * <strong>Also as lua_load, this function only loads the chunk; it does not run it.</strong>
     * </p>
     *
     * <p>
     * The used contents are from buffer[buffer.position()] to buffer[buffer.limit() - 1].
     * So remember to call {@link ByteBuffer#flip()} or set the position and limit before pushing.
     * </p>
     *
     * @param buffer the buffer, must be a direct buffer
     * @param name   the chunk name, used for debug information and error messages
     * @see #run(Buffer, String)
     * @see #pCall(int, int)
     */
    void load(Buffer buffer, String name) throws LuaException;

    /**
     * Loads and runs the given string
     *
     * <p>
     * It is equivalent to first calling {@link #load(String)} and then {@link #pCall(int, int)} the loaded chunk.
     * </p>
     *
     * @param script the Lua chunk
     */
    void run(String script) throws LuaException;

    /**
     * Loads and runs a buffer
     *
     * <p>
     * It is equivalent to first calling {@link #load(Buffer, String)} and then {@link #pCall(int, int)} the loaded chunk.
     * </p>
     *
     * <p>
     * The used contents are from buffer[buffer.position()] to buffer[buffer.limit() - 1].
     * So remember to call {@link ByteBuffer#flip()} or set the position and limit before pushing.
     * </p>
     *
     * @param buffer the buffer, must be a direct buffer
     * @param name   the chunk name, used for debug information and error messages
     */
    void run(Buffer buffer, String name) throws LuaException;

    /**
     * Dumps a function as a binary chunk
     *
     * <p>
     * Receives a Lua function on the top of the stack
     * and produces a binary chunk that,
     * if loaded again, results in a function equivalent to the one dumped.
     * </p>
     *
     * @return the binary chunk, null if an error occurred
     */
    @Nullable
    ByteBuffer dump();

    /**
     * Calls a function in protected mode
     *
     * <p>
     * To call a function you must use the following protocol:
     * first, the function to be called is pushed onto the stack;
     * then, the arguments to the function are pushed in direct order;
     * that is, the first argument is pushed first.
     * Finally you call lua_call; nargs is the number of arguments that you pushed onto the stack.
     * All arguments and the function value are popped from the stack when the function is called.
     * The function results are pushed onto the stack when the function returns.
     * The number of results is adjusted to nresults, unless nresults is LUA_MULTRET.
     * In this case, all results from the function are pushed.
     * Lua takes care that the returned values fit into the stack space.
     * The function results are pushed onto the stack in direct order (the first result is pushed first),
     * so that after the call the last result is on the top of the stack.
     * </p>
     *
     * @param nArgs    the number of arguments that you pushed onto the stack
     * @param nResults the number of results to adjust to
     */
    void pCall(int nArgs, int nResults) throws LuaException;

    /* Thread functions */

    /**
     * Creates a new thread, pushes it on the stack
     *
     * <p>
     * The new state returned by this function shares with the original state
     * all global objects (such as tables), but has an independent execution stack.
     * </p>
     *
     * @return a new thread
     */
    Lua newThread();

    /**
     * Starts and resumes a coroutine in a given thread
     *
     * <p>
     * To start a coroutine, you first create a new thread (see lua_newthread or {@link #newThread()});
     * then you push onto its stack the main function plus any arguments;
     * then you call resume, with narg being the number of arguments.
     * This call returns when the coroutine suspends or finishes its execution.
     * When it returns, the stack contains all values passed to lua_yield,
     * or all values returned by the body function.
     * lua_resume returns LUA_YIELD if the coroutine yields,
     * 0 if the coroutine finishes its execution without errors,
     * or an error code in case of errors (see lua_pcall).
     * In case of errors, the stack is not unwound,
     * so you can use the debug API over it.
     * The error message is on the top of the stack.
     * To restart a coroutine, you put on its stack only the values to be passed as results from yield,
     * and then call lua_resume.
     * </p>
     *
     * @param nArgs the number of arguments
     * @return {@code true} if the thread yielded, or {@code false} if it ended execution
     */
    boolean resume(int nArgs) throws LuaException;

    /**
     * Returns the status of the thread
     *
     * @return the status of the thread
     */
    LuaException.LuaError status();

    /**
     * Yields a coroutine
     *
     * <p>
     * This is not implemented because we have no way to resume execution from a Java stack through a C stack
     * back to a Java stack.
     * </p>
     *
     * @param n the number of values from the stack that are passed as results to lua_resume
     * @throws UnsupportedOperationException always
     */
    void yield(int n);

    /* Table functions */

    /**
     * Creates a new empty table and pushes it onto the stack
     *
     * <p>
     * The new table has space pre-allocated for narr array elements and nrec non-array elements.
     * This pre-allocation is useful when you know exactly how many elements the table will have.
     * </p>
     *
     * @param nArr pre-allocated array elements
     * @param nRec pre-allocated non-array elements
     */
    void createTable(int nArr, int nRec);

    /**
     * Creates a new empty table and pushes it onto the stack
     *
     * <p>
     * It is equivalent to {@link #createTable(int, int) createTable(0, 0)}.
     * </p>
     */
    void newTable();

    /**
     * Pushes onto the stack the value t[key]
     *
     * <p>
     * Pushes onto the stack the value t[key], where t is the value at the given valid index.
     * As in Lua, this function may trigger a metamethod for the "index" event.
     * </p>
     *
     * @param index the index of the table-like element
     * @param key   the key to look up
     */
    void getField(int index, String key);

    /**
     * Does the equivalent to t[key] = v
     *
     * <p>
     * Does the equivalent to t[key] = v,
     * where t is the value at the given valid index and v is the value at the top of the stack.
     * This function pops the value from the stack.
     * As in Lua, this function may trigger a metamethod for the "newindex" event.
     * </p>
     *
     * @param index the index of the table-like element
     * @param key   the key to assign to
     */
    void setField(int index, String key);

    /**
     * Pushes onto the stack the value t[k]
     *
     * <p>
     * Pushes onto the stack the value t[k],
     * where t is the value at the given valid index and k is the value at the top of the stack.
     * This function pops the key from the stack (putting the resulting value in its place).
     * As in Lua, this function may trigger a metamethod for the "index" event.
     * </p>
     *
     * @param index the index of the table-like element
     */
    void getTable(int index);

    /**
     * Does the equivalent to t[k] = v
     *
     * <p>
     * Does the equivalent to t[k] = v, where t is the value at the given valid index,
     * v is the value at the top of the stack, and k is the value just below the top.
     * This function pops both the key and the value from the stack.
     * As in Lua, this function may trigger a metamethod for the "newindex" event.
     * </p>
     *
     * @param index the index of the table-like element
     */
    void setTable(int index);

    /**
     * Pops a key from the stack, and pushes a key-value pair from the table at the given index
     *
     * <p>
     * Pops a key from the stack, and pushes a key-value pair from the table at the given index
     * (the "next" pair after the given key). If there are no more elements in the table,
     * then lua_next returns 0 (and pushes nothing).
     * </p>
     *
     * <p>
     * A typical traversal looks like this:
     * </p>
     *
     * <pre><code>
     *      /* table is in the stack at index 't' *&#47;
     *      lua_pushnil(L);  /* first key *&#47;
     *      while (lua_next(L, t) != 0) {
     *          /* uses 'key' (at index -2) and 'value' (at index -1) *&#47;
     *          printf("%s - %s\n",
     *          lua_typename(L, lua_type(L, -2)),
     *          lua_typename(L, lua_type(L, -1)));
     *          /* removes 'value'; keeps 'key' for next iteration *&#47;
     *          lua_pop(L, 1);
     *      }
     * </code></pre>
     *
     * <p>
     * While traversing a table, do not call {@link #toString(int)} directly on a key,
     * unless you know that the key is actually a string.
     * Recall that {@link #toString(int)} changes the value at the given index;
     * this confuses the next call to lua_next.
     * </p>
     *
     * @param n the index of the table
     * @return 0 if there are no more elements
     */
    int next(int n);

    /**
     * Similar to {@link #getTable(int)}, but does a raw access (i.e., without metamethods)
     *
     * @param index the index of the table
     */
    void rawGet(int index);

    /**
     * Pushes onto the stack the value t[n], where t is the value at the given valid index
     *
     * <p>
     * The access is raw; that is, it does not invoke metamethods.
     * </p>
     *
     * @param index the index of the table
     * @param n     the key
     */
    void rawGetI(int index, int n);

    /**
     * Similar to {@link #setTable(int)}, but does a raw assignment (i.e., without metamethods)
     *
     * @param index the index of the table
     */
    void rawSet(int index);

    /**
     * Does the equivalent of t[n] = v
     *
     * <p>
     * Does the equivalent of t[n] = v,
     * where t is the value at the given valid index and v is the value at the top of the stack.
     * </p><p>
     * This function pops the value from the stack. The assignment is raw;
     * that is, it does not invoke metamethods.
     * </p>
     *
     * @param index the index of the table
     * @param n     the key
     */
    void rawSetI(int index, int n);

    /**
     * Creates and returns a reference, in the table at index {@code index}
     *
     * <p>
     * Creates and returns a reference, in the table at index t, for the object at the top of the stack (and pops the object).
     * </p>
     * <p>
     * A reference is a unique integer key.
     * As long as you do not manually add integer keys into table t,
     * luaL_ref ensures the uniqueness of the key it returns.
     * You can retrieve an object referred by reference r by calling {@link #rawGetI(int, int)}.
     * Function {@link #unRef(int, int)} frees a reference and its associated object.
     * </p>
     *
     * @param index the index of the table
     * @return the created reference
     */
    int ref(int index);

    /**
     * Calls {@link #ref(int)} with the pseudo-index {@code LUA_REGISTRYINDEX}
     *
     * @return the created reference
     */
    int ref();

    /**
     * Calls {@link #rawGetI(int, int)} with the pseudo-index {@code LUA_REGISTRYINDEX} and the given {@code ref}
     *
     * @param ref the reference on {@code LUA_REGISTRYINDEX} table
     */
    void refGet(int ref);

    /**
     * Releases reference ref from the table at index {@code index}
     *
     * <p>
     * The entry is removed from the table, so that the referred object can be collected.
     * The reference ref is also freed to be used again.
     * </p>
     *
     * @param index the index of the table
     * @param ref   the reference to be freed
     */
    void unRef(int index, int ref);

    /**
     * Calls {@link #unRef(int, int)} with the pseudo-index {@code LUA_REGISTRYINDEX} and the given {@code ref}
     *
     * @param ref the reference to be freed
     */
    void unref(int ref);

    /* Meta functions */

    /**
     * Pushes onto the stack the value of the global {@code name}
     *
     * @param name the global name
     */
    void getGlobal(String name);

    /**
     * Pops a value from the stack and sets it as the new value of global {@code name}
     *
     * @param name the global name
     */
    void setGlobal(String name);

    /**
     * Pushes onto the stack the metatable of the value at the given acceptable index
     *
     * <p>
     * Pushes onto the stack the metatable of the value at the given acceptable index.
     * If the index is not valid, or if the value does not have a metatable,
     * the function returns 0 and pushes nothing on the stack.
     * </p>
     *
     * @param index the index of the element
     * @return 0 if the value does not have a metatable
     */
    int getMetatable(int index);

    /**
     * Pops a table from the stack and sets it as the new metatable for the value at the given acceptable index
     *
     * @param index the index of the element
     */
    void setMetatable(int index);

    /**
     * Pushes onto the stack the field {@code field} from the metatable of the object at index {@code index}
     *
     * <p>
     * If the object does not have a metatable,
     * or if the metatable does not have this field, returns 0 and pushes nothing.
     * </p>
     *
     * @param index the index of the element
     * @param field the meta field
     * @return 0 if no such field
     */
    int getMetaField(int index, String field);

    /**
     * Pushes onto the stack the metatable associated with name tname in the registry
     *
     * @param typeName the name of the user-defined type
     * @see #newRegisteredMetatable(String)
     */
    void getRegisteredMetatable(String typeName);

    /**
     * Creates a new table to be used as a metatable for userdata, adds it to the registry
     *
     * <p>
     * If the registry already has the key {@code typeName}, returns 0.
     * Otherwise, creates a new table to be used as a metatable for userdata,
     * adds it to the registry with key {@code typeName}, and returns 1.
     * </p>
     * <p>
     * In both cases pushes onto the stack the final value associated with tname in the registry.
     * </p>
     *
     * @param typeName the name of the user-defined type
     * @return 1 if added to registry, 0 if already registered
     */
    int newRegisteredMetatable(String typeName);

    /* Libraries */

    /**
     * Opens all standard Lua libraries into the given state
     */
    void openLibraries();

    /**
     * Opens a specific library into the given state
     *
     * @param name the library name
     */
    void openLibrary(String name);

    /**
     * Concatenates the n values at the top of the stack, pops them, and leaves the result at the top
     *
     * <p>
     * If n is 1, the result is the single value on the stack (that is, the function does nothing);
     * if n is 0, the result is the empty string.
     * Concatenation is performed following the usual semantics of Lua.
     * </p>
     *
     * @param n the number of values on top of the stack to concatenate
     */
    void concat(int n);

    /**
     * Performs a full garbage-collection cycle
     *
     * <p>
     * This also removes unneeded references created by finalized proxies and Lua values.
     * </p>
     */
    void gc();

    /**
     * Throws an error inside a Lua environment
     *
     * <p>
     * It currently just throws a {@link RuntimeException}.
     * </p>
     *
     * @param message the error message
     */
    void error(String message);

    /**
     * Creates a proxy object, implementing all the specified interfaces, with a Lua table / function on top of the stack
     *
     * <p>
     * This method pops the value on top on the stack and creates reference to it with {@link #ref()}.
     * </p>
     *
     * <p>
     * When invoking methods, the created Java object, instead of the backing Lua table, is passed
     * as the first parameter to the Lua function.
     * </p>
     *
     * @param interfaces the interfaces to implement
     * @param degree     the conversion degree when passing parameters and return values
     * @return a proxy object, calls to which are proxied to the underlying Lua table
     * @throws IllegalArgumentException if not all classes are interfaces
     */
    Object createProxy(Class<?>[] interfaces, Conversion degree) throws IllegalArgumentException;

    /**
     * Sets a {@link ExternalLoader} for the main state
     *
     * <p>
     * The provided external loader will be integrated into Lua's module resolution progress.
     * See <a href="https://www.lua.org/manual/5.2/manual.html#pdf-require">require (modname)</a>
     * for an overview.
     * </p>
     * <p>
     * We will register a new searcher by appending to <code>package.searchers</code> (or
     * <code>package.loaders</code> for Lua 5.1) to load Lua files with this {@link ExternalLoader}.
     * </p>
     * <p>
     * You need to load the <code>package</code> library to make the external loader effective.
     * </p>
     *
     * @param loader the loader that will be used to find files
     */
    void setExternalLoader(ExternalLoader loader);

    /**
     * Loads a chunk from a {@link ExternalLoader} set by {@link #setExternalLoader(ExternalLoader)}
     *
     * @param module the module
     */
    void loadExternal(String module) throws LuaException;

    /**
     * @return the underlying {@link LuaNatives} natives
     */
    LuaNatives getLuaNatives();

    /**
     * @return the main Lua state
     */
    Lua getMainState();

    /**
     * @return the pointer to the internal {@code lua_State}
     */
    long getPointer();

    /**
     * @return the unique identifier to the Lua thread
     */
    int getId();

    /**
     * Fetches the most recent Java {@link Throwable} passed to Lua
     *
     * @return value of the Lua global {@link #GLOBAL_THROWABLE}
     */
    @Nullable
    Throwable getJavaError();

    /**
     * Sets the Lua global {@link #GLOBAL_THROWABLE} to the throwable
     *
     * <p>
     * If the exception is {@code null}, it clears the global exception and pushes nothing.
     * Otherwise, it sets the Lua global {@link #GLOBAL_THROWABLE} to the throwable,
     * and pushes {@link Throwable#toString()} onto the stack.
     * </p>
     *
     * @param e the exception
     * @return 0 if e is null, -1 otherwise
     */
    int error(@Nullable Throwable e);

    /**
     * Closes the thread
     *
     * <p>
     * You need to make sure that you call this method no more than once,
     * or else the Lua binary may / will very likely just crash.
     * </p>
     */
    @Override
    void close();

    /**
     * Pops the value on top of the stack and return a LuaValue referring to it
     *
     * @return a reference to the value
     */
    LuaValue get();

    /**
     * Controls the degree of conversion from Java to Lua
     */
    enum Conversion {
        /**
         * Converts everything possible, including the following classes:
         *
         * <ul>
         *     <li>Boolean -&gt; boolean</li>
         *     <li>String -&gt; string</li>
         *     <li>ByteBuffer -&gt; string</li>
         *     <li>Number -&gt; lua_Number</li>
         *     <li>Map / Collection / Array -&gt; table (recursive)</li>
         *     <li>Object -&gt; Java object wrapped by a metatable {@link #pushJavaObject}</li>
         * </ul>
         *
         * <p>
         * Note that this means luatable changes on the lua side will not get reflected
         * to the Java side.
         * </p>
         */
        FULL,
        /**
         * Converts immutable types, including:
         * <ul>
         *     <li>Boolean</li>
         *     <li>String</li>
         *     <li>Number</li>
         * </ul>
         *
         * <p>
         *     {@link Map}, {@link Collection}, etc. are pushed with {@link #pushJavaObject(Object)}.
         *     Arrays are pushed with {@link #pushJavaArray(Object)}.
         * </p>
         */
        SEMI,
        /**
         * All objects, including {@link Integer}, for example, are pushed as either
         * Java objects (with {@link #pushJavaObject(Object)}) or Java arrays
         * (with {@link #pushJavaArray(Object)}).
         */
        NONE
    }

    /**
     * Lua data types
     */
    enum LuaType {
        BOOLEAN(),
        FUNCTION(),
        LIGHTUSERDATA(),
        NIL(),
        NONE(),
        NUMBER(),
        STRING(),
        TABLE(),
        THREAD(),
        USERDATA()
    }
}
