package party.iroiro.luajava;

import org.junit.jupiter.api.Test;
import party.iroiro.luajava.lua51.Lua51;
import party.iroiro.luajava.lua51.Lua51Consts;
import party.iroiro.luajava.lua52.Lua52;
import party.iroiro.luajava.lua52.Lua52Consts;
import party.iroiro.luajava.lua53.Lua53;
import party.iroiro.luajava.lua53.Lua53Consts;
import party.iroiro.luajava.lua54.Lua54;
import party.iroiro.luajava.lua54.Lua54Consts;
import party.iroiro.luajava.luaj.JavaMetatables;
import party.iroiro.luajava.luaj.LuaJ;
import party.iroiro.luajava.luaj.LuaJConsts;
import party.iroiro.luajava.luajit.LuaJit;
import party.iroiro.luajava.luajit.LuaJitConsts;
import party.iroiro.luajava.value.ImmutableLuaValue;
import party.iroiro.luajava.value.LuaValue;
import party.iroiro.luajava.value.AbstractRefLuaValue;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Meaningless tests used to "test" unachievable code, e.g., constructors in utility classes.
 */
public class FullCoverageJunkTest {
    @SuppressWarnings("unused")
    @Test
    public void fullCoverageJunk() throws NoSuchMethodException {
        Jua J = new Jua() {};
        Consts consts = new Consts() {};
        JuaAPI api = new JuaAPI() {};
        Lua51Consts lua51Consts = new Lua51Consts() {};
        Lua52Consts lua52Consts = new Lua52Consts() {};
        Lua53Consts lua53Consts = new Lua53Consts() {};
        Lua54Consts lua54Consts = new Lua54Consts() {};
        LuaJitConsts luaJitConsts = new LuaJitConsts() {};
        LuaJConsts luaJConsts = new LuaJConsts() {};

        Lua51 L = new Lua51();
        ImmutableLuaValue.NIL(L);
        L.createTable(0, 0);
        LuaValue v = L.get();
        assertInstanceOf(AbstractRefLuaValue.class, v);

        JavaMetatables metatables = new JavaMetatables() {};
    }

    @Test
    public void testLuaErrorWithoutMessage() {
        try (LuaForTests L = new LuaForTests()) {
            assertEquals(
                    "Lua-side error",
                    assertThrows(LuaException.class, () -> L.checkError(
                            Lua51Consts.LUA_ERRMEM
                    )).getMessage()
            );
        }
    }

    private static class LuaForTests extends Lua51 {
        public void checkError(int code) {
            super.checkError(code, false);
        }
    }

    @Test
    public void enumConversionTest() {
        //noinspection resource
        for (AbstractLua L : new AbstractLua[]{
                new Lua51(),
                new Lua52(),
                new Lua53(),
                new Lua54(),
                new LuaJit(),
                new LuaJ(),
        }) {
            assertThrows(LuaException.class, () -> L.convertError(10000));
            assertThrows(LuaException.class, () -> L.convertType(10000));
            L.close();
        }
    }
}
