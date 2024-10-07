package party.iroiro.luajava.docs;

import org.junit.jupiter.api.Test;
import party.iroiro.luajava.ClassPathLoader;
import party.iroiro.luajava.Lua;
import party.iroiro.luajava.lua51.Lua51;
import party.iroiro.luajava.value.LuaValue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static party.iroiro.luajava.Lua.LuaType.FUNCTION;

public class ModuleSnippetTest {
    @Test
    public void classPathLoaderTest() {
// #region classPathLoaderTest
try (Lua L = new Lua51()) {
    L.openLibrary("package");
    L.setExternalLoader(new ClassPathLoader());
    // Lua#require is equivalent to `require` in Lua.
    LuaValue compat = L.require("suite.luajava-compat");
    assertEquals(FUNCTION, compat.get("newInstance").type());
}
// #endregion classPathLoaderTest
    }

    @Test
    public void javaSideModuleTest() {
// #region javaSideModuleTest
try (Lua L = new Lua51()) {
    L.openLibrary("package");
    L.run("local LuaLib = require('party.iroiro.luajava.docs.JavaSideExampleModule.open');" +
    "assert(1024 == LuaLib.getNumber())");
}
// #endregion javaSideModuleTest
    }
}
