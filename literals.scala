// boolean literals are either true or false
val a = true
val b = false
val c = 1 > 2
val d = 1 < 2
val e = a == c
val f = b == d
a should be(true)
b should be(false)
c should be(false)
d should be(true)
e should be(false)
f should be(false)

// integer literals are 32-bit and can be created from decimals as well as hexadecimals
val a = 2
val b = 31
val c = 0x30F
val e = 0
val f = -2
val g = -31
val h = -0x30F
a should be(2)
b should be(31)
c should be(783) 
e should be(0)
f should be(-2)
g should be(-31)
h should be(-783)

// long literals are 64-bit and are specified by appending an `L` at the end of the declaration
val a = 2L
val b = 31L
val c = 0x30FL
val e = 0L
val f = -2L
val g = -31L
val h = -0x30FL
a should be(2)
b should be(31)
c should be(783) 
e should be(0)
f should be(-2)
g should be(-31)
h should be(-783)

// floats are 32-bit and can be defined using a `f` or `F` suffix, while doubles are 64-bit and use a `d` or `D` suffix 
// exponents are specified using `e` or `E`
val a = 3.0
val b = 3.00
val c = 2.73
val d = 3f
val e = 3.22d
val f = 93e-9
val g = 93E-9
val h = 0.0
val i = 9.23E-9D

a should be(3.0)
b should be(3.00)
c should be(2.73)
d should be(3.0)
e should be(3.22)
f should be(93e-9)
g should be(93E-9)
h should be(0.0)
i should be(9.23E-9D)

// character literals are quoted with single quotes
val a = 'a'
val b = 'B'

a.toString should be("a")
b.toString should be("B")

// character literals can use hexadecimal Unicode
val c = 'a' //unicode for a

c.toString should be("a")

// character literals can use escape sequences
val e = '\"'
val f = '\\'

e.toString should be("\"")
f.toString should be("\\")

// one-line String literals are surrounded by quotation marks
val a = "To be or not to be"
a should be("To be or not to be")