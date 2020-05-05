// `for` expressions can nest, with later generators varying more rapidly than earlier ones
val xValues = 1 to 4
val yValues = 1 to 2
val coordinates = for {
  x <- xValues
  y <- yValues
} yield (x, y)
coordinates(4) should be((3, 1))

// `for` is used to write more readable code
val nums = List(List(1), List(2), List(3), List(4), List(5))

val result = for {
  numList <- nums
  num <- numList
  if (num % 2 == 0)
} yield (num)

result should be(List(2, 4))

// Which is the same as
nums.flatMap(numList => numList).filter(_ % 2 == 0) should be(result)

// or the same as
nums.flatten.filter(_ % 2 == 0) should be(result)

// `traversable` is a trait with only one abstract operation `foreach`, it's at the top of the collection hierarchy
def foreach[U](f: Elem => U)

// Collection classes that implement `Traversable` need to define this method; all other methods can be inherited from `Traversable`
// `foreach` method is meant to traverse all elements of the collection, and apply the given operation, `f`, to each element
// The type of the operation is `Elem => U`, where `Elem` is the type of the collection's elements and `U` is an arbitrary result type
// `Traversable` is superclass of `List`, `Array`, `Map`, `Set`, `Stream` and more
// methods involved can be applied to each other in a different type. ++ appends two `Traversables` together. The resulting `Traversable` is the same type of the first element.
val set = Set(1, 9, 10, 22)
val list = List(3, 4, 5, 10)
val result = set ++ list
result.size should be(7)

val result2 = list ++ set
result2.size should be(8)

// `map` will apply the given function on all elements of a `Traversable` and return a new collection of the result
val set = Set(1, 3, 4, 6)
val result = set.map(_ * 4)
result.lastOption should be(Option(24))

// `flatten` will "pack" all child `Traversables` into a single `Traversable`
val list = List(List(1), List(2, 3, 4), List(5, 6, 7), List(8, 9, 10))
list.flatten should be(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))

// `flatMap` will apply the given function on all elements of `Traversable` and all elements within the elements and `flatten` the results
val list = List(List(1), List(2, 3, 4), List(5, 6, 7), List(8, 9, 10))
val result = list.flatMap(_.map(_ * 4))
result should be(List(4, 8, 12, 16, 20, 24, 28, 32, 36, 40))

// `flatMap` of `Options` will filter out all `None`s but keep the `Some`s
val list = List(1, 2, 3, 4, 5)
val result = list.flatMap(it => if (it % 2 == 0) Some(it) else None)
result should be(List(2, 4))

// `collect` will apply a partial function to all elements of a `Traversable` and return a different collection
val list = List(4, 6, 7, 8, 9, 13, 14)
val result = list.collect {
  case x: Int if (x % 2 == 0) => x * 3
}
result should be(List(12, 18, 24, 42))

// two `case` fragments can be chained
val list = List(4, 6, 7, 8, 9, 13, 14)
val partialFunction1: PartialFunction[Int, Int] = {
  case x: Int if x % 2 == 0 => x * 3
}
val partialFunction2: PartialFunction[Int, Int] = {
  case y: Int if y % 2 != 0 => y * 4
}
val result = list.collect(partialFunction1 orElse partialFunction2)
result should be(List(12, 18, 28, 24, 36, 52, 42))

// `foreach` will apply a function to all elements of a `Traversable`
// unlike the `map` function, it will not return anything since the return type is `Unit`
val list = List(4, 6, 7, 8, 9, 13, 14)
list.foreach(num => println(num * 4))
list should be(List(4, 6, 7, 8, 9, 13, 14))

// `toArray` will convert any `Traversable` to an `Array`, which is a special wrapper around a primitive Java array
val set = Set(4, 6, 7, 8, 9, 13, 14)
val result = set.toArray
result.isInstanceOf[Array[Int]] should be(true)

// `toList` will convert any `Traversable` to a `List`
val set = Set(4, 6, 7, 8, 9, 13, 14)
val result = set.toList

result.isInstanceOf[List[_]] should be(true)

// `toList`, as well as other conversion methods such as `toSet` and `toArray`, will not convert if the collection type is the same
val list = List(5, 6, 7, 8, 9)
val result = list.toList
result eq list should be(true)

// `toIterable` will convert any `Traversable` to an `Iterable` 
// This is a base trait for all Scala collections that define an iterator method to iterate through the collection's elements
val set = Set(4, 6, 7, 8, 9, 13, 14)
val result = set.toIterable
result.isInstanceOf[Iterable[_]] should be(true)

// `toSeq` will convert any `Traversable` to a `Seq` which is an ordered `Iterable` and the superclass to `List`, `Queue`, `Vector`
// `Sequences` provide a method apply for indexing, Indices range from 0 up to the length of a sequence
val set = Set(4, 6, 7, 8, 9, 13, 14)
val result = set.toSeq
result.isInstanceOf[Seq[_]] should be(true)

// `toIndexedSeq` will convert any `Traversable` to an `IndexedSeq` which is an indexed sequence used in `Vectors` and `Strings`
val set = Set(4, 6, 7, 8, 9, 13, 14)
val result = set.toIndexedSeq
result.isInstanceOf[IndexedSeq[_]] should be(true)

// `toStream` will convert any `Traversable` to a `LazyList` where elements are evaluated as they are needed
val list = List(4, 6, 7, 8, 9, 13, 14)
val result = list.to(LazyList)
result.isInstanceOf[LazyList[_]] should be(true)
(result take 3) should be(LazyList(4, 6, 7))

// `toSet` will convert any `Traversable` to a `Set` which is a collection of unordered, unique values
val list = List(4, 6, 7, 8, 9, 13, 14)
val result = list.toSet
result.isInstanceOf[Set[_]] should be(true)

// `toMap` will convert any `Traversable` to a `Map` 
// if it's a `List` or `Seq`, it should be of parameterized type `Tuple2`
val list = List("Phoenix" -> "Arizona", "Austin" -> "Texas")
val result = list.toMap
result.isInstanceOf[Map[_, _]] should be(true)

// `toMap` will also convert a `Set` to a `Map`; it should be of parameterized type `Tuple2`
val set = Set("Phoenix" -> "Arizona", "Austin" -> "Texas")
val result = set.toMap
result.isInstanceOf[Map[_, _]] should be(true)

// `isEmpty` and `nonEmpty` are pretty self-evident
val map = Map("Phoenix" -> "Arizona", "Austin" -> "Texas")
map.isEmpty should be(false)
map.nonEmpty should be(true)

val set = Set()
set.isEmpty should be(true)
set.nonEmpty should be(false)

// `size` provides the size of `Traversable`
val map = Map("Phoenix" -> "Arizona", "Austin" -> "Texas")
map.size should be(2)

// `knownSize` will return the number of elements if `Traversable` has a finite end, otherwise `-1`
val map = Map("Phoenix" -> "Arizona", "Austin" -> "Texas")
map.knownSize should be(2)

val stream = cons(0, cons(1, LazyList.empty))
stream.knownSize should be(-1)

// `head` will return the first element of an ordered collection
// or some random element if order is not defined like in `Set` or `Map`
val list = List(10, 19, 45, 1, 22)
val list2 = List()

list.head should be(10)

// `headOption` will return the first element as `Option` of an ordered collection, or some random element if order is not defined
// If a first element is not available, then `None` is returned
list.headOption should be(Option(10))
list2.headOption should be(None)

// same with `last` and `lastOption`
list.last should be(22)
list.lastOption should be(Option(22))

list2.lastOption should be(None)

// `find` will locate the first item that matches the predicate `p` as `Some`, or `None` if an element is not found
val list = List(10, 19, 45, 1, 22)
list.find(_ % 2 != 0) should be(Option(19))

val list2 = List(4, 8, 16)
list2.find(_ % 2 != 0) should be(None)

// `tail` will return the rest of the collection without the head
list.tail should be(List(19, 45, 1, 22))

// `init` will return the rest of the collection without the last
list.init should be(List(10, 19, 45, 1))

// Given `from` index, and `to` index, `slice` will return the part of the collection including `from`, and excluding `to`
list.slice(1, 3) should be(List(19, 45))

// `take` will return the first number of elements given
list.take(3) should be(List(10, 19, 45))

// `take` is used often with `LazyList`, since they are also `Traversable`
def makeLazyList(v: Int): LazyList[Int] = cons(v, makeLazyList(v + 1))
val a = makeLazyList(2)
(a take 3 toList) should be(List(2, 3, 4))

// `drop` will take the rest of the `Traversable` except the number of elements given
((a drop 6) take 3).toList should be(List(8, 9, 10))

// `takeWhile` will continually accumulate elements until a predicate is no longer satisfied
val list = List(87, 44, 5, 4, 200, 10, 39, 100)
list.takeWhile(_ < 100) should be(List(87, 44, 5, 4))

// `dropWhile` will continually drop elements until a predicate is no longer satisfied
list.dropWhile(_ < 100) should be(List(200, 10, 39, 100))

// `filter` will take out all elements that don't satisfy a predicate
// `Array` is also `Traversable`
val array = Array(87, 44, 5, 4, 200, 10, 39, 100)
array.filter(_ < 100) should be(Array(87, 44, 5, 4, 10, 39))

// `filterNot` will take out all elements that satisfy a predicate
array.filterNot(_ < 100) should be(Array(200, 100))

// `splitAt` will split a `Traversable` at a position, returning a 2 product `Tuple` 
// `splitAt` is also defined as (`xs take n, xs drop n`)
val result = array splitAt 3
result._1 should be(Array(87, 44, 5))
result._2 should be(Array(4, 200, 10, 39, 100))

// `span` will split a `Traversable` according to a predicate, returning a 2 product `Tuple`
// `span` is also defined as (`xs takeWhile p, xs dropWhile p`)
val result = array span (_ < 100)
result._1 should be(Array(87, 44, 5, 4))
result._2 should be(Array(200, 10, 39, 100))

// `partition` will split a `Traversable` according to a predicate, returning a 2 product `Tuple`
// `partition` is also defined as (`xs filter p, xs filterNot p`)
val result = array partition (_ < 100)
result._1 should be(Array(87, 44, 5, 4, 10, 39))
result._2 should be(Array(200, 100))

// `groupBy` will categorize a `Traversable` according to a given function and return a `map` with the results
val array = Array(87, 44, 5, 4, 200, 10, 39, 100)

val oddAndSmallPartial: PartialFunction[Int, String] = {
  case x: Int if x % 2 != 0 && x < 100 => "Odd and less than 100"
}

val evenAndSmallPartial: PartialFunction[Int, String] = {
  case x: Int if x != 0 && x % 2 == 0 && x < 100 => "Even and less than 100"
}

val negativePartial: PartialFunction[Int, String] = {
  case x: Int if x < 0 => "Negative Number"
}

val largePartial: PartialFunction[Int, String] = {
  case x: Int if x > 99 => "Large Number"
}

val zeroPartial: PartialFunction[Int, String] = {
  case x: Int if x == 0 => "Zero"
}

val result = array groupBy {
  oddAndSmallPartial orElse
    evenAndSmallPartial orElse
    negativePartial orElse
    largePartial orElse
    zeroPartial
}

(result("Even and less than 100") size) should be(3)
(result("Large Number") size) should be(2)

// `forall` will determine if a predicate is valid for all members of a `Traversable`
val list = List(87, 44, 5, 4, 200, 10, 39, 100)
val result = list forall (_ < 100)
result should be(false)

// `exists` will determine if a predicate is valid for some members of a `Traversable`
val result = list exists (_ < 100)
result should be(true)

// `count` will count the number of elements that satisfy a predicate in a `Traversable`
val result = list count (_ < 100)
result should be(6)

// `foldLeft` will combine an operation starting with a seed and combining from the left
// `foldLeft` takes as a first parameter the initial value of the fold 
// Once the fold is established, you provide a function that takes two arguments
val list = List(5, 4, 3, 2, 1)
val result = list.foldLeft(0) { (`running total`, `next element`) =>
  `running total` - `next element`
}
result should be(-15)

val result2 = list.foldLeft(0)(_ - _) //Short hand
result2 should be(-15)

(((((0 - 5) - 4) - 3) - 2) - 1) should be(-15)

// `foldRight` will combine an operation starting with a seed and combining from the right
// Given a `Traversable` (x1, x2, x3, x4), an initial value of `init`, an operation `op`
// `foldRight` is defined as: `x1 op (x2 op (x3 op (x4 op init)))`
val list = List(5, 4, 3, 2, 1)
val result = list.foldRight(0) { (`next element`, `running total`) =>
  `next element` - `running total`
}
result should be(3)

val result2 = list.foldRight(0)(_ - _) //Short hand
result2 should be(3)

(5 - (4 - (3 - (2 - (1 - 0))))) should be(3)

// `reduceLeft` is similar to `foldLeft`, except that the seed is the head value
val intList = List(5, 4, 3, 2, 1)
intList.reduceLeft {
  _ + _
} should be(15)

val stringList = List("Do", "Re", "Me", "Fa", "So", "La", "Te", "Do")
stringList.reduceLeft {
  _ + _
} should be("DoReMeFaSoLaTeDo")

// `reduceRight` is similar to `foldRight`, except that the seed is the last value
val intList = List(5, 4, 3, 2, 1)
intList.reduceRight {
  _ + _
} should be(15)

val stringList = List("Do", "Re", "Me", "Fa", "So", "La", "Te", "Do")
stringList.reduceRight {
  _ + _
} should be("DoReMeFaSoLaTeDo")

// There are some methods that take much of the folding work out by providing basic functionality. 
// `sum` will add all the elements, `product` will multiply, `min` would determine the smallest element, and `max` the largest
val intList = List(5, 4, 3, 2, 1)
intList.sum should be(15)
intList.product should be(120)
intList.max should be(5)
intList.min should be(1)

// The naive recursive implementation of `reduceRight` is not tail recursive and would lead to a stack overflow if used on larger traversables
// However, `reduceLeft` can be implemented with tail recursion
// To avoid the potential stack overflow with the naive implementation of `reduceRight` it can be easily implemented based on `reduceLeft` by reverting the list and the inverting the `reduce` function
// There is also a `reduce` (and `fold`) available, which works exactly like `reduceLeft` (and `foldLeft`) 
// It should be the prefered method to call unless there is a strong reason to use `reduceRight` (or `foldRight`)
val intList = List(5, 4, 3, 2, 1)
intList.reduceRight((x, y) => x - y) should be(3)
intList.reverse.reduceLeft((x, y) => y - x) should be(3)
intList.reverse.reduce((x, y) => y - x) should be(3)

// `transpose` will take a traversable of traversables and group them by their position in it's own traversable
val list = List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9))
list.transpose should be(List(List(1, 4, 7), List(2, 5, 8), List(3, 6, 9)))

val list2 = List(List(1), List(4))
list2.transpose should be(List(List(1, 4)))

// `mkString` will format a `Traversable` using a given string as the delimiter
val list = List(1, 2, 3, 4, 5)
list.mkString(",") should be("1,2,3,4,5")

// `mkString` will also take a beginning and ending string to surround the list
val list = List(1, 2, 3, 4, 5)
list.mkString(">", ",", "<") should be(">1,2,3,4,5<")

// `addString` will take a StringBuilder to add the contents of list into the builder
val stringBuilder = new StringBuilder()
val list = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
stringBuilder.append("I want all numbers 6-12: ")
list.filter(it => it > 5 && it < 13).addString(stringBuilder, ",")
stringBuilder.mkString should be("I want all numbers 6-12: 6,7,8,9,10,11,12")