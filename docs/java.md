# Java API

The Java API is mostly designed around
the [`party.iroiro.luajava.Lua`](./javadoc/party/iroiro/luajava/Lua.html){target="_self"} interface
and the [`party.iroiro.luajava.value.LuaValue`](./javadoc/party/iroiro/luajava/value/LuaValue.html){target="_self"} interface.

To start, simply acquire a `Lua` state with any of the following constructors:
- [`new Lua51()`](./javadoc/party/iroiro/luajava/lua51/Lua51.html){target="_self"}
- [`new Lua52()`](./javadoc/party/iroiro/luajava/lua52/Lua52.html){target="_self"}
- [`new Lua53()`](./javadoc/party/iroiro/luajava/lua53/Lua53.html){target="_self"}
- [`new Lua54()`](./javadoc/party/iroiro/luajava/lua54/Lua54.html){target="_self"}
- [`new LuaJit()`](./javadoc/party/iroiro/luajava/luajit/LuaJit.html){target="_self"}
- [`new LuaJ()`](./javadoc/party/iroiro/luajava/luaj/LuaJ.html){target="_self"}

## Close the state

Just like the C API, you will need to [`close`](./javadoc/party/iroiro/luajava/Lua.html#close()){target="_self"} the state after you are done with it:

<<< ../example/src/test/java/party/iroiro/luajava/docs/JavaApiExampleTest.java#closableTest

::: warning
Currently the sub-threads (created with [`mainState.newThread()`](./javadoc/party/iroiro/luajava/Lua.html#newThread()){target="_self"})
are only cleaned up after you close the main state.
This shouldn't be a problem unless you have millions of sub-threads.
:::

## Open libraries

By default, the `Lua` state is only loaded with the [`java`](./api.md) library.
To use Lua libraries like `string`, `table` or `coroutine`, you will need to explicitly open the libraries:

- [openLibraries](./javadoc/party/iroiro/luajava/Lua.html#openLibraries()){target="_self"}:
  Opens all available libraries.

- [openLibrary](./javadoc/party/iroiro/luajava/Lua.html#openLibrary(java.lang.String)){target="_self"}:
  Opens a specific library.

## Running Lua Files

Lua C API provides a [`luaL_loadfile`](https://www.lua.org/manual/5.4/manual.html#luaL_loadfile){target="_self"} function,
which loads a Lua file from the file system. 
However, we deem it less portable to load files from the file system, and have chose to base our API on
Java classpath loading and Lua's built-in `require` mechanism instead.
(Actually, you can run any file by loading it into a `ByteBuffer` and running it with
[`run`](./javadoc/party/iroiro/luajava/Lua.html#run(java.nio.Buffer,java.lang.String)){target="_self"} .)

See [Java-Side Modules](./examples/modules.md) for an example. Basically, to run a file from Java classpath:
1. Use `L.setExternalLoader(new ClassPathLoader());` to have the `require` function load files from the classpath.
2. Either run `require('path.to.module')` as Lua code or
   use [`Lua::require`](./javadoc/party/iroiro/luajava/Lua.html#require(java.lang.String)){target="_self"} to run the file.

<<< ../../example/src/test/java/party/iroiro/luajava/docs/ModuleSnippetTest.java#javaSideModuleTest

## Interact with Lua values

To interact with Lua values, you can use the [`LuaValue`](#luavalue-interface) interface
or [make use of the Lua C API bindings directly](./c-api.md).

## `LuaValue` <Badge>interface</Badge>

The [`party.iroiro.luajava.value.LuaValue`](./javadoc/party/iroiro/luajava/value/LuaValue.html){target="_self"} interface
is a wrapper around a Lua value.
Internally, for complex values (for example, Lua tables),
it uses [references](#creating-references) to refer to the Lua value,
garbage collected after the `LuaValue` instance becomes a phantom reference.

<<< ../example/src/test/java/party/iroiro/luajava/docs/JavaApiExampleTest.java#luaValueTest

[The JavaDoc of `LuaValue`](./javadoc/party/iroiro/luajava/value/LuaValue.html){target="_self"} should be quite self-explanatory.
The following texts list several commonly used patterns to help you get familiar with the API.

### Get global variables

Every `Lua` state implementation implements
the [`LuaThread`](./javadoc/party/iroiro/luajava/value/LuaThread.html){target="_self"} interface,
providing a convenient way to retrieve and use `LuaValues`:

To obtain a `LuaValue` from a Lua global variable,
use [`Lua::get(String)`](./javadoc/party/iroiro/luajava/value/LuaThread.html#get(java.lang.String)){target="_self"}:

<<< ../example/src/test/java/party/iroiro/luajava/docs/JavaApiExampleTest.java#luaValueFromGlobalTest

### Set global variables

To set a Lua global variable to any `LuaValue` or any arbitrary Java object,
use [`Lua::set(String, Object)`](./javadoc/party/iroiro/luajava/value/LuaThread.html#set(java.lang.String,java.lang.Object)){target="_self"}:

<<< ../example/src/test/java/party/iroiro/luajava/docs/JavaApiExampleTest.java#setGlobalTest

### `LuaValues` from Java values

To create a `LuaValue` from a simple Java value,
use [`Lua::from(boolean/double/long/String)`](./javadoc/party/iroiro/luajava/value/LuaThread.html#from(boolean)){target="_self"}
or `Lua::fromNull()`.

<<< ../example/src/test/java/party/iroiro/luajava/docs/JavaApiExampleTest.java#setGlobalTest{2}

### `LuaValues` from Lua evaluation

To run Lua code and obtain the returned values,
use [`Lua::eval(String)`](./javadoc/party/iroiro/luajava/value/LuaThread.html#eval(java.lang.String)){target="_self"}:

<<< ../example/src/test/java/party/iroiro/luajava/docs/JavaApiExampleTest.java#luaValueEvalTest

::: tip
With `Lua::eval`, you will need an explicit Lua `return` statement to have `Lua::eval` return the values.
:::

### Manipulate Lua tables

`LuaValue`s implements the Java `Map` interface and allow direct manipulation of Lua tables.

<<< ../example/src/test/java/party/iroiro/luajava/docs/JavaApiExampleTest.java#luaValueTableTest

### Call Lua functions

Use [`LuaValue::call(...)`](./javadoc/party/iroiro/luajava/value/LuaValue.html#call(java.lang.Object...)){target="_self"}
to call a function `LuaValue` and receive its return values as `LuaValue`s:

<<< ../example/src/test/java/party/iroiro/luajava/docs/JavaApiExampleTest.java#luaValueCallTest

### Create Java proxies

Create proxies with [`LuaValue::toProxy`](./javadoc/party/iroiro/luajava/value/LuaValue.html#toProxy(java.lang.Class)){target="_self"}:

<<< ../example/src/test/java/party/iroiro/luajava/docs/JavaApiExampleTest.java#luaValueProxyTest
