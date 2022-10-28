package party.iroiro.luajava;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import party.iroiro.luajava.lua51.Lua51;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testable
public class CoroutineTest {
    @Test
    public void coroutineTest() throws IOException {
        Lua L = new Lua51();
        ResourceLoader loader = new ResourceLoader();
        loader.load("/tests/coTest.lua", L);
        L.pCall(0, Consts.LUA_MULTRET);
        Lua coL = L.newThread();
        int ignored = L.ref();
        coL.getGlobal("main");
        int i = 1, j = 1;
        for (int l = 0; l < 36; l++) {
            assertEquals(Lua.LuaError.YIELD, coL.resume(0));
            assertEquals(i, coL.toNumber(-1));
            coL.pop(1);
            int k = i + j;
            i = j;
            j = k;
        }
        L.close();
    }
}
