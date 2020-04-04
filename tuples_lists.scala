// tuples are one-indexed and immutable
val tuple = ("apple", "dog")
// syntactic sugar for: new Tuple2(1, 2)
val fruit = tuple._1
val animal = tuple._2

fruit should be("apple")
animal should be("dog")