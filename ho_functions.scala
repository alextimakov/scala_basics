// express functions in function literal syntax and those functions can be represented by objects called function values
// lambdas a.k.a. anonymous functions
def lambda = { x: Int =>
  x + 1
}
def lambda2 = (x: Int) => x + 2
val lambda3 = (x: Int) => x + 3

val lambda4 = new Function1[Int, Int] {
  def apply(v1: Int): Int = v1 - 1
}

def lambda5(x: Int) = x + 1

val result = lambda(3)
val `result1andhalf` = lambda.apply(3)

val result2 = lambda2(3)
val result3 = lambda3(3)
val result4 = lambda4(3)
val result5 = lambda5(3)

result should be(4)
result1andhalf should be(4)
result2 should be(5)
result3 should be(6)
result4 should be(2)
result5 should be(4)

// closure functions can maintain a reference to one or more variables outside of the function scope
var incrementer = 1

def closure = { x: Int =>
  x + incrementer
}

val result1 = closure(10)
result1 should be(11)

incrementer = 2

val result2 = closure(10)
result2 should be(12)

// Higher Order Functions are functions that take functions as arguments and/or return functions
def summation(x: Int, y: Int => Int) = y(x)

var incrementer = 3
def closure = (x: Int) => x + incrementer

val result = summation(10, closure)
result should be(13)

incrementer = 4
val result2 = summation(10, closure)
result2 should be(14)

// Higher Order Functions returning function
def addWithoutSyntaxSugar(x: Int): Function1[Int, Int] = {
  new Function1[Int, Int]() {
    def apply(y: Int): Int = x + y
  }
}
addWithoutSyntaxSugar(1).isInstanceOf[Function1[Int, Int]] should be(true)

addWithoutSyntaxSugar(2)(3) should be(5)

def fiveAdder: Function1[Int, Int] = addWithoutSyntaxSugar(5)
fiveAdder(5) should be(10)

// HOF may use lambdas as well
def addWithSyntaxSugar(x: Int) = (y: Int) => x + y

addWithSyntaxSugar(1).isInstanceOf[Function1[Int, Int]] should be(true)
addWithSyntaxSugar(2)(3) should be(5)

def fiveAdder = addWithSyntaxSugar(5)
fiveAdder(5) should be(10)

// isinstanceOf do the same as in Python
// although parameter types can be *blanked out* using existential types with a single underline since are unknown at runtime
def addWithSyntaxSugar(x: Int) = (y: Int) => x + y

addWithSyntaxSugar(1).isInstanceOf[Function1[_, _]] should be(true)

// HOF taking another function as a parameter
def makeUpper(xs: List[String]) = xs map {
  _.toUpperCase
}

def makeWhatEverYouLike(xs: List[String], sideEffect: String => String) =
  xs map sideEffect  // map method applies the function to each element of a list

makeUpper(List("abc", "xyz", "123")) should be(List("ABC", "XYZ", "123"))

makeWhatEverYouLike(List("ABC", "XYZ", "123"), { x =>
  x.toLowerCase
}) should be(List("abc", "xyz", "123"))

//using it inline
val myName = (name: String) => s"My name is $name"
makeWhatEverYouLike(List("John", "Mark"), myName) should be(List("My name is John", "My name is Mark"))

List("Scala", "Erlang", "Clojure") map (_.length) should be(List(5, 6, 7))