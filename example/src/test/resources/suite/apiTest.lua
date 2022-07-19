--[[
 Module `java`
  ]]--
assert(java ~= nil)
methods = { 'import', 'new', 'proxy', 'luaify', 'method', 'array' }
for i = 1, #methods do
    assert(type(java[methods[i]]) == 'function')
end

--[[
  java.import(className)
  ]]--
Integer = java.import('java.lang.Integer')
assert(type(Integer) == 'userdata')
assertThrows('java.lang.ClassNotFoundException: java.lang.NonExistentClass',
             java.import, 'java.lang.NonExistentClass')
assertThrows('java.lang.ClassNotFoundException: java lang Integer',
             java.import, 'java lang Integer')
assertThrows('java.lang.ClassNotFoundException: java&&&&&&&&&&&&/',
             java.import, 'java&&&&&&&&&&&&/')
-- java.import(package)
lang = java.import('java.lang.*')
assert(type(lang) == 'table')
assert(type(lang.String) == 'userdata')
assert(type(lang.Integer) == 'userdata')
assert(Integer(1024):equals(lang.Integer(1024)) == true)
-- Throws: Type: not string
assertThrows('bad argument #1 to', java.import)
assertThrows('bad argument #1 to', java.import, nil)
assertThrows('bad argument #1 to', java.import, {})
-- Nil: Type: convertible to string
assertThrows('java.lang.ClassNotFoundException: 100',
             java.import, 100)

--[[
  java.new
  ]]--
assert(type(java.new(Integer, 10)) == 'userdata')
-- Throws: Type: Not jobject nor jclass
assertThrows('bad argument #1 to \'java.new\': __jclass__ or __jobject__ expected', java.new, nil)
assertThrows('bad argument #1 to \'java.new\': __jclass__ or __jobject__ expected', java.new, 'java.lang.String')
assertThrows('bad argument #1 to \'java.new\': __jclass__ or __jobject__ expected', java.new, {})
assertThrows('bad argument #1 to \'java.new\': __jclass__ or __jobject__ expected', java.new, 100)
-- Nil: Type: jobject, but is not Class<?>
assert(java.new(Integer(1024)) == nil)
assert(java.new(Integer(1024):getClass(), 1024):equals(Integer(1024)))
-- Nil: Construction exceptions
assert(java.new(Integer, 1, 1, 1) == nil)
assert(java.new(Integer) == nil)
assert(java.new(Integer, '') == nil)

--[[
  java.proxy
  ]]--
Runnable = java.import('java.lang.Runnable')
run = { run = function() end }
assert(java.proxy(Runnable, run) ~= nil)
assert(java.proxy(Runnable.class, run) ~= nil)
assert(java.proxy('java.lang.Runnable', run) ~= nil)
assertThrows('bad argument #1 to', java.proxy, Integer, run)
assertThrows('bad argument #1 to', java.proxy, Integer(10), run)
assertThrows('(expecting an interface)', java.proxy, '', run)
assertThrows('(expecting an interface)', java.proxy, '', {})
assertThrows('(expecting an interface)', java.proxy, {}, {})

--[[
  java.array
  ]]--
assertThrows('bad argument #1 to \'java.array\':', java.array)
assertThrows('bad argument #1 to \'java.array\':', java.array, 1)
assertThrows('bad argument #1 to \'java.array\':', java.array, 1, 2)
assert(java.array(Integer(1), 2) == nil)
assertThrows('bad argument #2 to \'java.array\':', java.array, Integer)
assert(java.array(java.import('java.lang.Void').TYPE, 1, 1, 1) == nil)
i = java.import('int')
array = java.array(i, 2)
assert(#array == 2)
array = java.array(i.class, 2)
assert(#array == 2)
array = java.array(i, 2, 2)
assert(#array == 2)
assert(#array[1] == 2)
assert(#array[2] == 2)
assert(java.array(i, 2, 3, {}, 4) == nil)
assert(java.array(i, 2, 3, -4) == nil)
