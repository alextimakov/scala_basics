// when calling methods and functions, you can use the name of the variables explicitly in the call
def printName(first: String, last: String) = {
  println(first + " " + last)
}

printName("John", "Smith") // Prints "John Smith"
printName(first = "John", last = "Smith") // Prints "John Smith"
printName(last = "Smith", first = "John") // Prints "John Smith"

// once you are using parameter names in your calls, the order doesn't matter, so long as all parameters are named
// this feature works well with default parameter values
def printName(first: String = "John", last: String = "Smith") = {
  println(first + " " + last)
}
printName(last = "Jones") // Prints "John Jones"

// with the following classes
class WithoutClassParameters() = {
def addColors(red: Int, green: Int, blue: Int) = {
(red, green, blue)
}

def addColorsWithDefaults(red: Int = 0, green: Int = 0, blue: Int = 0) = {
(red, green, blue)
}
}

class WithClassParameters(val defaultRed: Int, val defaultGreen: Int, val defaultBlue: Int) = {
def addColors(red: Int, green: Int, blue: Int) = {
(red + defaultRed, green + defaultGreen, blue + defaultBlue)
}

def addColorsWithDefaults(red: Int = 0, green: Int = 0, blue: Int = 0) = {
(red + defaultRed, green + defaultGreen, blue + defaultBlue)
}
}

class WithClassParametersInClassDefinition(val defaultRed: Int = 0, val defaultGreen: Int = 255, val defaultBlue: Int = 100) {
def addColors(red: Int, green: Int, blue: Int) = {
(red + defaultRed, green + defaultGreen, blue + defaultBlue)
}

def addColorsWithDefaults(red: Int = 0, green: Int = 0, blue: Int = 0) = {
(red + defaultRed, green + defaultGreen, blue + defaultBlue)
}
}

// can specify arguments in any order if you use their names
val me = new WithoutClassParameters()
val myColor = me.addColors(green = 0, red = 255, blue = 0)
myColor should equal((255, 0, 0))

// can default arguments if you leave them off
val myColor = me.addColorsWithDefaults(green = 255)
myColor should equal((0, 255, 0))

// can access class parameters and specify arguments in any order if you use their names
val me = new WithClassParameters(40, 50, 60)
val myColor = me.addColors(green = 50, red = 60, blue = 40)

myColor should equal((100, 100, 100))

// can access class parameters and default arguments if you leave them off
val me = new WithClassParameters(10, 20, 30)
val myColor = me.addColorsWithDefaults(green = 70)

myColor should equal((10, 90, 30))

// can default class parameters and have default arguments too
val me = new WithClassParametersInClassDefinition()
val myColor = me.addColorsWithDefaults(green = 70)

myColor should equal((0, 325, 100))

// default parameters can be functions too
def reduce(a: Int, f: (Int, Int) => Int = _ + _): Int = f(a, a)

reduce(5) should equal(10)
reduce(5, _ * _) should equal(25)

// `() => Int` is a Function type that takes a `Unit` type (`Unit` is known as void to a Java programmer)
// you can place this as a method parameter so that you can you use it as a block, but still it doesn't look quite right
def calc(x: () => Int): Either[Throwable, Int] = {
  try {
    Right(x()) //An explicit call of the x function
  } catch {
    case b: Throwable => Left(b)
  }
}

val y = calc { () => //Having explicitly declaring that Unit is a parameter with ()
  14 + 15
}

y should be(Right(29))

// a by-name parameter does the same thing as the previous koan but there is no need to explicitly handle `Unit` or `()`
def calc(x: => Int): Either[Throwable, Int] = {
  //x is a call by name parameter
  try {
    Right(x)
  } catch {
    case b: Throwable => Left(b)
  }
}

val y = calc {
  //This looks like a natural block
  println("Here we go!") //Some superfluous call
  val z = List(1, 2, 3, 4) //Another superfluous call
  49 + 20
}

y should be(Right(69))

// by-name parameters can also be used with object and apply to make interesting block-like calls
object PigLatinizer {
  def apply(x: => String) = x.tail + x.head + "ay"
}

val result = PigLatinizer {
  val x = "pret"
  val z = "zel"
  x ++ z //concatenate the strings
}

result should be("retzelpay")

// a repeated parameter must be the last parameter and this will let you add as many extra parameters as needed
def repeatedParameterMethod(x: Int, y: String, z: Any*) = {
  "%d %ss can give you %s".format(x, y, z.mkString(", "))
}

repeatedParameterMethod(3, "egg", "a delicious sandwich", "protein", "high cholesterol") should be(
    "3 eggs can give you a delicious sandwich, protein, high cholesterol")

// a repeated parameter can accept a collection as the last parameter but will be considered a single object
repeatedParameterMethod(3, "egg", List("a delicious sandwich", "protein", "high cholesterol")) should be(
    "3 eggs can give you List(a delicious sandwich, protein, high cholesterol)")

// a repeated parameter can accept a collection - if you want it expanded, add :`_*`
repeatedParameterMethod(
  3,
  "egg",
  List("a delicious sandwich", "protein", "high cholesterol"): _*) should be(
      "3 eggs can give you a delicious sandwich, protein, high cholesterol")