// array stores a fixed-size sequential collection of same-type elements = collection of variables
// list can be converted to array
val l = List(1, 2, 3)
val a = l.toArray
a should equal(Array(1, 2, 3))

// sequences are special cases of iterable collections of class `Iterable` and always have a defined order of elements
// any sequence can be converted to list
val a = Array(1, 2, 3)
val s = a.toSeq
val l = s.toList
l should equal(List(1, 2, 3)) 

// sequence can be created from `for` loop
val s = for (v <- 1 to 4) yield v
s.toList should be(List(1, 2, 3, 4))

// any sequence can be filter based on predicate
val s = Seq("hello", "to", "you")
val filtered = s.filter(_.length > 2)
filtered should be(Seq("hello", "you"))

// arrays can be filtered in the same manner
val a = Array("hello", "to", "you", "again")
val filtered = a.filter(_.length > 3)
filtered should be(Array("hello", "again"))

// values in a sequence can be mapped through a function
val s = Seq("hello", "world")
val r = s map {
  _.reverse
}

r should be(Seq("olleh", "dlrow"))

// range is an ordered sequence of integers that are equally spaced apart
// most operations on ranges are extremely fast
//range's upper bound is not inclusive
val someNumbers = Range(0, 10)
val second = someNumbers(1)
val last = someNumbers.last

someNumbers.size should be(10)
second should be(1)
last should be(9)

// ranges can be specified using `until`
val someNumbers = Range(0, 10)
val otherRange = 0 until 10
val anotherRange = 0 to 10  // inclusive

(someNumbers == otherRange) should be(true)
(someNumbers == anotherRange) should be(false)

// range can specify a step for an increment
val someNumbers = Range(2, 10, 3)  // same as `2 until 10 by 3`
val second = someNumbers(1)
val last = someNumbers.last

someNumbers.size should be(3)
second should be(5)
last should be(8)

val someNumbers = Range(0, 34, 2)
someNumbers.contains(33) should be(false)
someNumbers.contains(32) should be(true)
someNumbers.contains(34) should be(false)

// range can specify to include upper bound value
val someNumbers = Range(0, 34).inclusive

someNumbers.contains(34) should be(true)

val someNumbers = Range(0, 34).inclusive
val otherRange = 0 to 34

(someNumbers == otherRange) should be(true)