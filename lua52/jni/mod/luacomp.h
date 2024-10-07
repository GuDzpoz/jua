#ifndef LUACOMP_H
#define LUACOMP_H

#include "lua.hpp"

#include <cstring>

/**
 * Opens individual libraries when one does not want them all
 */
static inline void luaJ_openlib_call(lua_State * L, const char * libName, lua_CFunction loader) {
    luaL_requiref(L, libName, loader, 1);
    lua_pop(L, 1);
}

luaL_Reg allAvailableLibs[] = {
    { "",          luaopen_base },
    { "_G",        luaopen_base },
    { "package",   luaopen_package },
    { "coroutine", luaopen_coroutine },
    { "string",    luaopen_string },
    { "table",     luaopen_table },
    { "bit32",     luaopen_bit32 },
    { "math",      luaopen_math },
    { "io",        luaopen_io },
    { "os",        luaopen_os },
    { "debug",     luaopen_debug },
    { NULL,        NULL },
};

static void luaJ_openlib(lua_State * L, const char * libName) {
    const luaL_Reg *lib = allAvailableLibs;
    for (; lib->func != NULL; lib++) {
        if (std::strcmp(lib->name, libName) == 0) {
            luaJ_openlib_call(L, lib->name, lib->func);
            return;
        }
    }
}

static int luaJ_compare(lua_State * L, int index1, int index2, int op) {
    if (op < 0) {
        return lua_compare(L, index1, index2, LUA_OPLT);
    } else if (op == 0) {
        return lua_compare(L, index1, index2, LUA_OPEQ);
    } else {
        return lua_compare(L, index1, index2, LUA_OPLE);
    }
}

static int luaJ_len(lua_State * L, int index) {
    return lua_rawlen(L, index);
}

static int luaJ_resume(lua_State * L, int narg) {
    return lua_resume(L, NULL, narg);
}

static int luaJ_initloader(lua_State * L) {
  return luaJ_insertloader(L, "searchers");
}

static int luaJ_dump(lua_State * L, lua_Writer writer, void * data) {
  return lua_dump (L, writer, data);
}

static int luaJ_isinteger(lua_State * L, int index) {
  return 0;
}

#endif /* !LUACOMP_H */
