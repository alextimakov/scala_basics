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

// empty values types
// Scala's `null` is the same as in Java - any reference type can be `null`, like Strings, Objects, or your own classes
// just like Java, value types like Ints can't be `null`
// `Null` is a trait whose only instance is `null`, It is a subtype of all reference types, but not of value types 
// its purpose in existing is to make it so reference types can be assigned null and value types can't
// `Nothing` is a trait that is guaranteed to have zero instances. It is a subtype of all other types. It has two main reasons for existing: 
// to provide a return type for methods that never return normally (i.e. a method that always throws an exception). 
// to provide a type for `Nil`
// `Unit` in Scala is the equivalent of `void` in Java
// It's used in a function's signature when that function doesn't return a value
// `Nil` is just an empty list, exactly like the result of `List()`. It is of type `List[Nothing]`. 
// And since we know there are no instances of `Nothing`, we now have a list that is statically verifiable as empty

// An empty list can be represented by another nothing value: `Nil`
List() === Nil shouldBe true

// `None` is the counterpart to `Some`, used when you're using Scala's `Option` class to help avoid `null` references
None === None shouldBe true
None eq None shouldBe true

// `None` can be converted to string or an empty list
assert(None.toString === "None")
None.toList === Nil shouldBe true

// `None` is considered empty
assert(None.isEmpty === true)

// `None` can be cast to `Any`, `AnyRef` or `AnyVal`
None.asInstanceOf[Any] === None shouldBe true
None.asInstanceOf[AnyRef] === None shouldBe true
None.asInstanceOf[AnyVal] === None shouldBe true

// `None` can be used with `Option` instead of null references
val optional: Option[String] = None
assert(optional.isEmpty === true)
assert(optional === None)

// `Some` is the opposite of `None` for `Option` types
val optional: Option[String] = Some("Some Value")
assert((optional == None) === false, "Some(value) should not equal None")
assert(optional.isEmpty === false, "Some(value) should not be empty")

// `Option.getOrElse` can be used to provide a default in the case of `None`
val optional: Option[String] = Some("Some Value")
val optional2: Option[String] = None
assert(optional.getOrElse("No Value") === "Some Value", "Should return the value in the option")
assert(optional2.getOrElse("No Value") === "No Value", "Should return the specified default value")