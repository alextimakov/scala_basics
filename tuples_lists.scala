// tuples are one-indexed and immutable
val tuple = ("apple", "dog")
// syntactic sugar for: new Tuple2(1, 2)
val fruit = tuple._1
val animal = tuple._2

fruit should be("apple")
animal should be("dog")

// tuples may be of mixed type
val tuple5 = ("a", 1, 2.2, new Date(), "five")

tuple5._2 should be(1)
tuple5._5 should be("five")

// multiple variables may be assigned to tuple
val student = ("Sean Rogers", 21, 3.5)
val (name, age, gpa) = student

name should be("Sean Rogers")
age should be(21)
gpa should be(3.5)

// elements in tuple may be swapped
val tuple = ("apple", 3).swap
tuple._1 should be(3)
tuple._2 should be("apple")

// lists are immutable linked lists and contains elements of type T (as in List[T])
val a = List(1, 2, 3)
val b = List(1, 2, 3)
(a eq b) should be(false)  // eq tests identity (same object)
(a == b) should be(true)  // == tests equality (same content)

// Nil is a more idiomatic way to create lists in Scala
val a: List[String] = Nil
val b: List[Int] = Nil

(a == Nil) should be(true)
(a eq Nil) should be(true)

(b == Nil) should be(true)
(b eq Nil) should be(true)

(a == b) should be(true)
(a eq b) should be(true)

// list creaton is quite straightforward
val a = List(1, 2, 3)
a should equal(List(1, 2, 3))

// access lists with head, headOption and tail
val a = List(1, 2, 3)
a.headOption should equal(Some(1))
a.tail should equal(List(2, 3))

// lists can be accessed by index
val a = List(1, 3, 5, 7, 9)
a(0) should equal(1)
a(2) should equal(5)
a(4) should equal(9)

intercept[IndexOutOfBoundsException] {
  println(a(5))
}

val a = List(1, 3, 5, 7, 9)
val b = a.filterNot(v => v == 5) // remove where value is 5

a should equal(List(1, 3, 5, 7, 9))
b should equal(List(1, 3, 7, 9))

// get the length of the list
a.length should equal(5)

// reverse the list
a.reverse should equal(List(9, 7, 5, 3, 1))

// map a function to double the numbers over the list
a.map { v =>
  v * 2
} should equal(List(2, 6, 10, 14, 18))

// filter any values divisible by 3 in the list
a.filter { v =>
  v % 3 == 0
} should equal(List(3, 9))

// functions over lists can user _ as shorthand
val a = List(1, 2, 3)

a.map {
  _ * 2
} should equal(List(2, 4, 6))

a.filter {
  _ % 2 == 0
} should equal(List(2))

// functions over lists can use () instead of {}
a.map(_ * 2) should equal(List(2, 4, 6))
a.filter(_ % 2 != 0) should equal(List(1, 3))

// lists can be reduced with mathematical operations
val a = List(1, 3, 5, 7)
a.reduceLeft(_ + _) should equal(16)
a.reduceLeft(_ * _) should equal(105)

// foldLeft us like reduce, but with explicit starting value, and uses currying
a.foldLeft(0)(_ + _) should equal(16)
a.foldLeft(10)(_ + _) should equal(26)
a.foldLeft(1)(_ * _) should equal(105)
a.foldLeft(0)(_ * _) should equal(0)

// it is possible to create list from range
val a = (1 to 5).toList
a should be(List(1, 2, 3, 4, 5))

// it is possible to prepend elements to list
val a = List(1, 3, 5, 7)
0 :: a should be(List(0, 1, 3, 5, 7))

// lists can be concatenated
val head = List(1, 3)
val tail = List(5, 7)

head ::: tail should be(List(1, 3, 5, 7))
head ::: Nil should be(List(1, 3))

// lists reuse their tails
val d = Nil
val c = 3 :: d
val b = 2 :: c
val a = 1 :: b

a should be(List(1, 2, 3))
a.tail should be(List(2, 3))
b.tail should be(List(3))
c.tail should be(Nil)