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
