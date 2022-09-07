# Java-Side Modules

To understand the following, you are expected to know [how Lua's `require` works](https://www.lua.org/manual/5.4/manual.html#6.3).

## Classpath Lua Modules

With Java, in many cases you put your resources on the [classpath](https://en.wikipedia.org/wiki/Classpath), which may be a path accessible to Lua, or one lying in a [JAR](https://en.wikipedia.org/wiki/JAR_(file_format)).

Of course, it is possible to load any Lua files manually by `Class::getResourceAsStream` and then reading it into a (direct) buffer and then `Lua::load(buffer, name)`.

Alternatively, we provide a `ClassPathLoader`. One just initializes the Lua state by setting a external loader, and then they can forget about the Java side classpath and just `require` any classpath Lua modules from Lua.

For more flexibility, just extend `ClassPathLoader` or write your own `ExternalLoader`.

:::: code-group
::: code-group-item Java Side
```java
Lua L = new Lua51();
L.openLibrary("package");
L.setExternalLoader(new ClassPathLoader());
```
:::
::: code-group-item Lua Side
```lua
-- Loads classpath:/lua/MyModule.lua
local MyModule = require('lua.MyModule')
```
:::
::::

## Java Method Modules

This provides an approach to loading libraries written in Java, similar to Lua's
`package.loadlib`. It does the following:

1. Split the module name by the last dot (`.`) into `FullyQualifiedClassName.staticMethodName`.
   - The method is expected to be of the form `public static int method(Lua L);`.
     See the Javadoc of `JFunction` for more info.
2. Call `java.loadlib` to encapsulate the Java static method into a C function to be used in Lua.
3. We leave the rest to Lua's `require`:
   - `require` calls the loader with two arguments: `modname` and an extra value.
   - The extra value is `nil` for LuaJava.

:::: code-group
::: code-group-item Java Library
```java
package com.example;

public class LuaLib {
    public static int open(Lua L) {
        L.createTable(0, 1);
        L.push(l -> {
            l.push(1024);
            return 1;
        });
        L.setField(-2, "getNumber");
        return 1;
    }
}
```
:::
::: code-group-item Java Side
```java
Lua L = new Lua51();
L.openLibrary("package");
```
:::
::: code-group-item Lua Side
```lua
local LuaLib = require('com.example.LuaLib.open')
assert(1024 == LuaLib.getNumber())
```
:::
::::
