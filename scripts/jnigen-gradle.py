#!/bin/python

# for i in {1..4}; do python scripts/jnigen-gradle.py lua5${i} > lua5${i}/build.gradle; done

import sys

jnigen_version = "2.5.1"
lua_version = sys.argv[1]
assert lua_version in ["lua51", "lua52", "lua53", "lua54"]
header_dirs = {
    "lua51": "'lua51/src', 'lua51/etc'",
    "lua52": "'lua52'",
    "lua53": "'lua53'",
    "lua54": "'lua54'",
}

script = f"""\
buildscript {{
    repositories {{
        mavenLocal()
        mavenCentral()
    }}
    dependencies {{
        classpath 'com.badlogicgames.gdx:gdx-jnigen-gradle:{jnigen_version}'
    }}
}}

plugins {{
    id 'java'
    id 'java-library'
}}

repositories {{
    mavenCentral()
}}

group = rootProject.group
version = rootProject.version

configurations {{
    desktopNatives {{
        canBeConsumed = true
        canBeResolved = false
    }}

    instrumentedJars {{
        canBeConsumed = true
        canBeResolved = false
        extendsFrom api, implementation, runtimeOnly
    }}
}}

dependencies {{
    api project(':luajava')
}}

apply plugin: 'com.badlogicgames.gdx.gdx-jnigen'

jnigen {{
    sharedLibName = '{lua_version}'

    all {{
        headerDirs = ['../../jni/luajava', 'mod', {header_dirs[lua_version]}]
        cppExcludes = ['{lua_version}/**/*']
        cExcludes = ['{lua_version}/**/*']
        libraries = ' -lm '
    }}

    add(Windows, x32)
    add(Windows, x64)
    // add(Windows, x64, ARM) // TODO: Add to CI after GCC adds aarch64-w64-mingw32 target

    add(Linux, x32)
    add(Linux, x64)
    add(Linux, x32, ARM)
    add(Linux, x64, ARM)
    each({{ it.os == Linux }}) {{
        String linuxFlags = ' -D_FORTIFY_SOURCE=0 -DLUA_USE_DLOPEN '
        cFlags += linuxFlags
        cppFlags += linuxFlags
    }}

    add(MacOsX, x64)
    add(MacOsX, x64, ARM)
    each({{ it.os == MacOsX }}) {{
        String macFlags = ' -DLUA_USE_DLOPEN '
        libraries = ''
        cFlags += macFlags
        cppFlags += macFlags
    }}

    add(Android) {{
        String androidFlags = ' -D_FORTIFY_SOURCE=1 -DLUA_USE_DLOPEN '
        cFlags += androidFlags
        cppFlags += androidFlags
        androidApplicationMk = [
                'APP_PLATFORM := android-21',
                "APP_CFLAG :=$androidFlags",
        ]
    }}

    robovm {{
        forceLinkClasses "java.lang.Class", "java.lang.Throwable", "party.iroiro.luajava.JuaAPI"
    }}
    add(IOS, x64) {{
        libraries = ''
        xcframeworkBundleIdentifier = "party.iroiro.luajava.{lua_version}"
        minIOSVersion = "11.0"
    }}
}}

artifacts {{
    instrumentedJars(jar)
    desktopNatives(jnigenJarNativesDesktop)
}}

tasks.named('jar') {{
    manifest {{
        attributes('Automatic-Module-Name': 'party.iroiro.luajava.{lua_version}')
    }}
}}

tasks.jnigen.dependsOn(tasks.build)
"""

print(script, end="")
