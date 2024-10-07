#ifndef JUA_H
#define JUA_H

#include "lua.hpp"
#include "jni.h"

#define JAVA_STATE_INDEX "__jmainstate__"
#define GLOBAL_THROWABLE "__jthrowable__"

extern const char JAVA_CLASS_META_REGISTRY[];
extern const char JAVA_OBJECT_META_REGISTRY[];
extern const char JAVA_ARRAY_META_REGISTRY[];
extern const char JAVA_PACKAGE_META_REGISTRY[];

extern jclass    juaapi_class;
extern jmethodID juaapi_classnew;
extern jmethodID juaapi_classindex;
extern jmethodID juaapi_classinvoke;
extern jmethodID juaapi_classsiginvoke;
extern jmethodID juaapi_classnewindex;
extern jmethodID juaapi_objectindex;
extern jmethodID juaapi_objectinvoke;
extern jmethodID juaapi_objsiginvoke;
extern jmethodID juaapi_objectnewindex;
extern jmethodID juaapi_arraynew;
extern jmethodID juaapi_arraylen;
extern jmethodID juaapi_arrayindex;
extern jmethodID juaapi_arraynewindex;
extern jmethodID juaapi_freethreadid;
extern jmethodID juaapi_luaify;
extern jmethodID juaapi_import;
extern jmethodID juaapi_proxy;
extern jmethodID juaapi_unwrap;
extern jmethodID juaapi_load;
extern jmethodID juaapi_loadmodule;
extern jmethodID juaapi_loadlib;
extern jmethodID throwable_tostring;

int reopenAsGlobal(const char * file);

jclass bindJavaClass(JNIEnv * env, const char * name);
jmethodID bindJavaStaticMethod(JNIEnv * env, jclass c, const char * name, const char * sig);
jmethodID bindJavaMethod(JNIEnv * env, jclass c, const char * name, const char * sig);
int initBindings(JNIEnv * env);
int initBoxingBindings(JNIEnv * env);

void initMetaRegistry(lua_State * L);

int getStateIndex(lua_State * L);
void luaJ_removestateindex(lua_State * L);
JNIEnv * getJNIEnv(lua_State * L);

int fatalError(lua_State * L);

lua_State * luaJ_newthread(lua_State * L, int lid);
void luaJ_pushobject(JNIEnv * env, lua_State * L, jobject obj);
void luaJ_pushclass(JNIEnv * env, lua_State * L, jobject clazz);
void luaJ_pusharray(JNIEnv * env, lua_State * L, jobject array);
jobject luaJ_toobject(lua_State * L, int index);
int luaJ_isobject(lua_State * L, int index);
void luaJ_pushfunction(JNIEnv * env, lua_State * L, jobject func);
void luaJ_pushlstring(lua_State * L, unsigned char * buffer, int start, int size);

int luaJ_loadbuffer(lua_State * L, unsigned char * buffer, int start, int size, const char * name);
int luaJ_dobuffer(lua_State * L, unsigned char * buffer, int start, int size, const char * name);

jobject luaJ_dumptobuffer(lua_State * L);
jobject luaJ_tobuffer(lua_State * L, int i);
jobject luaJ_todirectbuffer(lua_State * L, int i);

int luaJ_insertloader(lua_State * L, const char * searchers);

int luaJ_invokespecial(JNIEnv * env, lua_State * L,
                       jclass clazz, const char * method, const char * sig,
                       jobject obj, const char * params);

void luaJ_gc(lua_State * L);

inline bool checkIfError (JNIEnv * env, lua_State * L) {
  jthrowable e = env->ExceptionOccurred();
  if (e == NULL) {
    return false;
  }
  env->ExceptionClear();
  jstring message = (jstring) env->CallObjectMethod(e, throwable_tostring);
  const char * str = env->GetStringUTFChars(message, NULL);
  lua_pushstring(L, str);
  env->ReleaseStringUTFChars(message, str);
  env->DeleteLocalRef((jobject) message);
  luaJ_pushobject(env, L, (jobject) e);
  lua_setglobal(L, GLOBAL_THROWABLE);
  // https://stackoverflow.com/q/33481144/17780636
  // env->DeleteLocalRef(e);
  return true;
}

inline int checkOrError (JNIEnv * env, lua_State * L, jint ret) {
  if (!checkIfError(env, L) && ret >= 0) {
    lua_pushnil(L);
    lua_setglobal(L, GLOBAL_THROWABLE);
    return (int) ret;
  }
  return lua_error(L);
}

#endif /* JUA_H! */
