// partially applied function is a function you don't apply any or all the arguments, creating another function
// example of partially applied function which doesn't apply all arguments
def sum(a: Int, b: Int, c: Int) = a + b + c
val sum3 = sum _
sum3(1, 9, 7) should be(17)
sum(4, 5, 6) should be(15)

// PAF can replace any number of arguments
def sum(a: Int, b: Int, c: Int) = a + b + c
val sumC = sum(1, 10, _: Int)
sumC(4) should be(15)
sum(4, 5, 6) should be(15)

// currying is a technique to transform a function with multiple parameters into multiple functions which each take one parameter
def multiply(x: Int, y: Int) = x * y
(multiply _).isInstanceOf[Function2[_, _, _]] should be(true)
val multiplyCurried = (multiply _).curried
multiply(4, 5) should be(20)
multiplyCurried(3)(2) should be(6)
val multiplyCurriedFour = multiplyCurried(4)
multiplyCurriedFour(2) should be(8)
multiplyCurriedFour(4) should be(16)

// currying allows to create specialized versions of generalized functions
def customFilter(f: Int => Boolean)(xs: List[Int]) =
  xs filter f
def onlyEven(x: Int) = x % 2 == 0
val xs = List(12, 11, 5, 20, 3, 13, 2)
customFilter(onlyEven)(xs) should be(List(12, 20, 2))

val onlyEvenFilter = customFilter(onlyEven) _
onlyEvenFilter(xs) should be(List(12, 20, 2))

// partial function is a trait that when implemented can be used as building blocks to determine a solution
// trait `PartialFunction` requires that the method `isDefinedAt` and `apply` be implemented
val doubleEvens: PartialFunction[Int, Int] =
  new PartialFunction[Int, Int] {
    // States that this partial function will take on the task
    def isDefinedAt(x: Int) = x % 2 == 0

    // What we do if this partial function matches
    def apply(v1: Int) = v1 * 2
  }

val tripleOdds: PartialFunction[Int, Int] = new PartialFunction[Int, Int] {
  def isDefinedAt(x: Int) = x % 2 != 0

  def apply(v1: Int) = v1 * 3
}

val whatToDo = doubleEvens orElse tripleOdds  // chain the partial functions together

whatToDo(3) should be(9)
whatToDo(4) should be(8)

// case statements are quick way to create partial functions (`apply` and `isDefinedAt` methods are created automatically)
val doubleEvens: PartialFunction[Int, Int] = {
  case x if (x % 2) == 0 => x * 2
}
val tripleOdds: PartialFunction[Int, Int] = {
  case x if (x % 2) != 0 => x * 3
}

val whatToDo = doubleEvens orElse tripleOdds
whatToDo(3) should be(9)
whatToDo(4) should be(8)

// result of partial functions can have `andThen` function added to the end of the chain
val doubleEvens: PartialFunction[Int, Int] = {
  case x if (x % 2) == 0 => x * 2
}
val tripleOdds: PartialFunction[Int, Int] = {
  case x if (x % 2) != 0 => x * 3
}

val addFive = (x: Int) => x + 5
val whatToDo = doubleEvens orElse tripleOdds andThen addFive //Here we chain the partial functions together
whatToDo(3) should be(14)
whatToDo(4) should be(13)

// `andThen` can be used to continue onto another chain of logic
val doubleEvens: PartialFunction[Int, Int] = {
  case x if (x % 2) == 0 => x * 2
}
val tripleOdds: PartialFunction[Int, Int] = {
  case x if (x % 2) != 0 => x * 3
}

val printEven: PartialFunction[Int, String] = {
  case x if (x % 2) == 0 => "Even"
}
val printOdd: PartialFunction[Int, String] = {
  case x if (x % 2) != 0 => "Odd"
}

val whatToDo = doubleEvens orElse tripleOdds andThen (printEven orElse printOdd)

whatToDo(3) should be("Odd")
whatToDo(4) should be("Even")