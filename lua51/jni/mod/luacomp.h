#ifndef LUACOMP_H
#define LUACOMP_H

#include "lua.hpp"

#include <cstring>

/**
 * Opens individual libraries when one does not want them all
 */
static inline void luaJ_openlib_call(lua_State * L, const char * libName, lua_CFunction loader) {
    lua_pushcfunction(L, loader);
    lua_pushstring(L, libName);
    lua_call(L, 1, 0);
}

static void luaL_setmetatable(lua_State * L, const char * tname) {
    luaL_getmetatable(L, tname);
    lua_setmetatable(L, -2);
}

luaL_Reg allAvailableLibs[] = {
    { "",        luaopen_base },
    { "package", luaopen_package },
    { "string",  luaopen_string },
    { "table",   luaopen_table },
    { "math",    luaopen_math },
    { "io",      luaopen_io },
    { "os",      luaopen_os },
    { "debug",   luaopen_debug },
    { NULL,      NULL },
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
        return lua_lessthan(L, index1, index2);
    } else if (op == 0) {
        return lua_equal(L, index1, index2);
    } else {
        return lua_lessthan(L, index1, index2) || lua_equal(L, index1, index2);
    }
}

static int luaJ_len(lua_State * L, int index) {
    return lua_objlen(L, index);
}

static int luaJ_resume(lua_State * L, int narg) {
    return lua_resume(L, narg);
}

static void *luaL_testudata(lua_State * L, int ud, const char * tname) {
  void *p = lua_touserdata(L, ud);
  if (p != NULL) {  /* value is a userdata? */
    if (lua_getmetatable(L, ud)) {  /* does it have a metatable? */
      luaL_getmetatable(L, tname);  /* get correct metatable */
      if (!lua_rawequal(L, -1, -2))  /* not the same? */
        p = NULL;  /* value is a userdata with wrong metatable */
      lua_pop(L, 2);  /* remove both metatables */
      return p;
    }
  }
  return NULL;  /* value is not a userdata with a metatable */
}

static int luaJ_initloader(lua_State * L) {
  return luaJ_insertloader(L, "loaders");
}

static int luaJ_dump(lua_State * L, lua_Writer writer, void * data) {
  return lua_dump (L, writer, data);
}

static int luaJ_isinteger(lua_State * L, int index) {
  return 0;
}

#endif /* !LUACOMP_H */
