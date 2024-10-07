# Type Conversions

Exchanging values between Java and Lua, we allow users to control how values are converted back and forth. You might want to read [#Extra Lua Types](./api.md#extra-lua-types) first.

## Java to Lua

When pushing a Java value onto the Lua stack, you can choose from doing a `FULL` conversion, a `SEMI` conversion  or `NONE` of the conversions by supplying a second parameter of type `Lua.Conversion`.

| Java Types                    | [`NONE`](#lua-conversion-none) | [`SEMI`](#lua-conversion-semi) | [`FULL`](#lua-conversion-full) | Conversion (if ever)               |
|:------------------------------|:------------------------------:|:------------------------------:|:------------------------------:|------------------------------------|
| `null`                        | :white_check_mark:             | :white_check_mark:             | :white_check_mark:             | Lua `nil`                          |
| `LuaValue`                    | :orange_square:                | :orange_square:                | :orange_square:                | Converted if sharing main state    |
| `Boolean`                     |                                | :white_check_mark:             | :white_check_mark:             | Lua booleans                       |
| `Character`                   |                                | :white_check_mark:             | :white_check_mark:             | `lua_Integer` or `lua_Number`      |
| Boxed numerics (`Integer`...) |                                | :white_check_mark:             | :white_check_mark:             | `lua_Integer` or `lua_Number`      |
| `String`                      |                                | :white_check_mark:             | :white_check_mark:             | Lua strings                        |
| `JFunction`                   |                                | :white_check_mark:             | :white_check_mark:             | A Lua closure                      |
| `ByteBuffer`                  |                                |                                | :white_check_mark:             | Lua raw strings                    |
| Java arrays                   |                                |                                | :white_check_mark:             | Lua tables (index starting from 1) |
| `Collections<?>`              |                                |                                | :white_check_mark:             | Lua tables (index starting from 1) |
| `Map<?, ?>`                   |                                |                                | :white_check_mark:             | Lua tables                         |
| `Class<?>`                    |                                |                                | :white_check_mark:             | `jclass`                           |
| Others                        |                                |                                |                                | Proxied to the Java side           |
| Example: `BigInteger`         |                                |                                |                                | Proxied to the Java side           |
| Example: `AtomicInteger`      |                                |                                |                                | Proxied to the Java side           |

::: tip
For a `SEMI`-conversion, roughly speaking, **immutable** types are converted,
while mutable types, as well as those types not having Lua counterparts, are not.

For a `FULL`-conversion, all values are **recursively** converted if possible.
Note that we ignore entries in `Map<?>` with a `null` key or a `null` value.

When calling Java methods from Lua, we `SEMI`-convert the return value.
Currently, there is no way to specify how you want the return value converted.
:::

::: warning Examples
- **`NONE`**:

  You cannot add `jobject` types up, even if their underlying class is `Integer`.

  <<< ../example/src/test/java/party/iroiro/luajava/docs/ConversionExampleTest.java#noAutoUnboxingTest{5}

- **`FULL`**:

  Changes in converted Lua objects are not propagated back to the original Java object.

  <<< ../example/src/test/java/party/iroiro/luajava/docs/ConversionExampleTest.java#fullConversionTest{5-6}

:::

## Lua to Java

1. ***nil*** is converted to `null`.
2. ***boolean*** converted to `boolean` or the boxed `Boolean`.
3. ***integer*** / ***number*** to any of `boolean` `char` `byte` `short` `int` `long` `float` `double` or their boxed alternative. (`Double` is preferred.)

   Trying to convert a number into an `Object` will always yield a boxed `Double`.
   So pay attention when you use `Object::equals` for example.

4. ***string*** to `String` or `ByteBuffer`.
5. ***table*** to `Map<Object, Object>`, `List<Object&gt;`, `Object[]`, (converted recursively with `Map<Object, Object>` preferred) or any interfaces.

   To convert tables into any interface,
   we call [`party.iroiro.luajava.Lua#createProxy`](./javadoc/party/iroiro/luajava/Lua.html#createProxy(java.lang.Class%5B%5D,party.iroiro.luajava.Lua.Conversion)){target="_self"}
   with [`party.iroiro.luajava.Lua.Conversion#SEMI`](./javadoc/party/iroiro/luajava/Lua.Conversion.html#SEMI){target="_self"}.

6. ***jclass*** to `Class<?>`.
7. ***jobject*** to the underlying Java object.
8. ***function*** to a [functional interface](https://docs.oracle.com/javase/specs/jls/se8/html/jls-9.html#jls-9.8).

   Actually, if the abstract methods in the target interfaces are all of the same name, the library also does the wrapping for you.

   The wrapping is done by creating an intermediate Lua table and then calling
   [`party.iroiro.luajava.Lua#createProxy`](./javadoc/party/iroiro/luajava/Lua.html#createProxy(java.lang.Class%5B%5D,party.iroiro.luajava.Lua.Conversion)){target="_self"}
   with [`party.iroiro.luajava.Lua.Conversion#SEMI`](./javadoc/party/iroiro/luajava/Lua.Conversion.html#SEMI){target="_self"}.

9. Any type can get wrapped into a `LuaValue`.
10. If all the above is not applicable, the result is `null` on the Java side.

::: warning
Currently, you cannot convert a C closure back to a `JFunction`, even if the closure simply wraps around `JFunction`.
:::

## Raw Strings in Lua

Unlike Java, Lua allows using strings as raw bytes, which means you can have null bytes,
invalid UTF-8 sequences, or just arbitrary binary data in a string.
(See [`lua_pushlstring`](https://www.lua.org/manual/5.1/manual.html#lua_pushlstring) for more information.)
Java, on the other hand, does not allow this and instead often uses `byte[]` for this purpose.

This poses a challenge when converting between Java `byte[]` and Lua strings:
the library does not know if the user wishes to interpret the `byte[]` data as an array (mapped to Lua tables) of bytes,
or as a raw Lua string. Currently, the library assumes the former and does not plan to change until the next major version.
And before that, you will probably need to write a wrapper yourself with
[`party.iroiro.luajava.Lua#pushString`](./javadoc/party/iroiro/luajava/Lua.html#push(java.nio.ByteBuffer)){target="_self"}
and
[`party.iroiro.luajava.Lua#toBuffer`](./javadoc/party/iroiro/luajava/Lua.html#toBuffer(int)){target="_self"} .

## 64-Bit Integers

To ensure compatibility across Lua versions, this library uses `double` for most numbers.
However, [Lua 5.3](https://www.lua.org/manual/5.3/manual.html#8.1) introduced an integer subtype
for numbers, which allows usage of 64-bit integers in Lua (on 64-bit machines mostly).
This library ensures that no truncation ever happens when casting between `long` and `double`
(which can happen on 32-bit machines where `long` values get truncated to 32-bit Lua integers).
To retrieve or push integer values that exceed the safe integer range of `double` numbers,
you will need to use
[`party.iroiro.luajava.Lua#push(long)`](./javadoc/party/iroiro/luajava/Lua.html#push(long)){target="_self"}
and
[`party.iroiro.luajava.Lua#toInteger`](./javadoc/party/iroiro/luajava/Lua.html#toInteger(int)){target="_self"}.

Also, when passing values via proxies or Java calls on the Lua side,
the values will get auto converted to ensure maximal precision.
For example, the following Lua snippet passes `2^60 + 1` around correctly
(which cannot fit into a `double`) when running with 64-bit Lua 5.3:

<<< ../example/src/test/resources/docs/conversions64BitExample.lua
