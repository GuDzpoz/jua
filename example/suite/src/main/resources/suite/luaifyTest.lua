-- Lua values returned as is
assert(java.luaify(1) == 1)
assert(java.luaify(true) == true)
assert(java.luaify(nil) == nil)
assert(java.luaify('s') == 's')
f = function() end
t = {a = 1}
assert(java.luaify(t) == t)
java.luaify(t).a = 2
assert(t.a == 2)

assert(type(java.luaify(java.import('java.lang.String')())) == 'string')
assert(type(java.luaify(java.import('java.util.HashMap')())) == 'table')

big = java.luaify(others.big)
assert(type(big) == 'userdata')
assert(big:getClass():getSimpleName() == 'BigInteger')

oArr = java.luaify(others.array1) -- Object[] of { Double[], Double[], Double }
assert(type(oArr) == 'table')
assert(type(oArr[1]) == 'table')
assert(type(oArr[2]) == 'table')
assert(oArr[3] == 7)
for i = 1, 3 do
    assert(oArr[1][i] == i)
    assert(oArr[2][i] == i + 3)
end

iArr = java.luaify(others.array2) -- int[]
assert(type(iArr) == 'table')
for i = 8, 12 do
    assert(iArr[i - 7] == i)
end

buffer = java.import('java.nio.ByteBuffer'):allocateDirect(3)
buffer:put(1, 1)
buffer:put(2, 2)
s = java.luaify(buffer)
assert(type(s) == 'string')
assert(#s == 3)
assert(s:byte(1) == 0)
assert(s:byte(2) == 1)
assert(s:byte(3) == 2)
