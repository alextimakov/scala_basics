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
