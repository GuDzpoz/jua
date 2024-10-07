assert(others ~= nil)

assert(others.i == 1)
assert(others.s == "2")
assertThrows('java.lang.IllegalArgumentException', function() others.i = "2" end)
assertThrows('java.lang.IllegalArgumentException', function() others.s = 1 end)
assert(others.i == 1)
assert(others.s == "2")
assert(type(myuserdata) == 'userdata')
assertThrows('java.lang.IllegalArgumentException', function() others.s = others.big end)
assert(others.s == "2")
assertThrows('java.lang.IllegalArgumentException', function() others.s = myuserdata end)
assert(others.s == "2")
assertThrows('java.lang.IllegalArgumentException', function() others.s = {1, 2, 3} end)
assert(others.s == "2")
others.s = java.import('java.lang.String')('Hello')
assert(others.s == "Hello")
assertThrows('java.lang.IllegalArgumentException', function() others.i = nil end)
others.s = nil
assert(others.i == 1)
assert(others.s == nil)

assert(others.collection == nil)
assertThrows('java.lang.IllegalArgumentException', function() others.collection = function() end end)
assert(others.collection == nil)
others.collection = {1, 2, 3}
assert(others.collection ~= nil)
assert(others.collection:size() == 3)

assert(others.array1 == nil)
others.array1 = {1, 2, 3}
assert(others.array1 ~= nil)
assert(#(others.array1) == 3)

assert(others.array2 == nil)
assertThrows('java.lang.IllegalArgumentException', function() others.array2 = {1, 2, 3} end)
assert(others.array2 == nil)

assert(others.map == nil)
others.map = {1, 2, 3}
assert(others.map ~= nil)

assert(others.annotation == nil)
assertThrows('java.lang.IllegalArgumentException', function() others.annotation = {} end)
assert(others.annotation == nil)

b = false
assert(others.intf == nil)
others.intf = { run = function() b = true end }
assert(others.intf ~= nil)
others.intf:run()
assert(b)

assert(others.buffer == nil)
s = '123'
others.buffer = s
assert(others.buffer ~= nil)
assert(others.buffer:limit() == 3)
assert(others.buffer:get(0) == s:byte(1))
assert(others.buffer:get(1) == s:byte(2))
assert(others.buffer:get(2) == s:byte(3))

assert(others.any == nil)
others.any = true
assert(others.any == true)
others.any = 1
assert(others.any == 1)
others.any = '2'
assert(others.any == '2')
others.any = {1, 2, 3}
others.any = function() end
