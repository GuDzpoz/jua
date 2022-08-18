package party.iroiro.luajava;

import org.junit.jupiter.api.Test;
import party.iroiro.luajava.value.ImmutableLuaValue;
import party.iroiro.luajava.value.LuaValue;
import party.iroiro.luajava.value.RefLuaValue;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;

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
        assertNull(JuaAPI.CONSTRUCTOR_WRAPPER.getName(FullCoverageJunkTest.class.getConstructor()));

        Lua51 L = new Lua51();
        //noinspection deprecation
        ImmutableLuaValue.NIL(L).close();
        L.createTable(0, 0);
        LuaValue v = L.get();
        assertInstanceOf(RefLuaValue.class, v);
        //noinspection deprecation
        v.close();
    }
}
