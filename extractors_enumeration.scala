// patterns can be defined independently of case classes
// method named `unapply` is defined to yield a so-called extractor
// an extractor object `Twice` is defined below
object Twice {
  def apply(x: Int): Int = x * 2
  def unapply(z: Int): Option[Int] = if (z % 2 == 0) Some(z / 2) else None
}

object TwiceTest extends Application {
  val x = Twice(21)
  x match { case Twice(n) => Console.println(n) } // prints 21
}

// there are two syntactic conventions at work here
// pattern case `Twice(n)` will cause an invocation of `Twice.unapply`, 
// return value of the `unapply` signals whether the argument has matched or not, and any sub-values that can be used for further matching
// second option - `apply` method is not necessary for pattern matching, it is only used to mimick a constructor 
// `val x = Twice(21)` expands to `val x = Twice.apply(21)`
object TwiceTest extends Application {
  val x = Twice.apply(21)
  Twice.unapply(x) match { case Some(n) => Console.println(n) } // prints 21
}

// return type of an unapply should be chosen as follows
// if it is just a test, return a `Boolean`, e.g. instance `case even()`
// if it returns a single sub-value of type `T`, return a `Option[T]`
// if you want to return several sub-values `T1,...,Tn`, group them in an optional tuple `Option[(T1,...,Tn)]`
// when case class is created, it automatically can be used with pattern matching since it has an extractor
case class Employee(firstName: String, lastName: String)

val rob = new Employee("Robin", "Williams")
val result = rob match {
  case Employee("Robin", _) => "Where's Batman?"
  case _ => "No Batman Joke For You"
}

result should be("Where's Batman?")

// so, extractor is a method in any object called `unapply`
// that method is used to disassemble the object given by returning a tuple wrapped in an option
// extractors can be used to assign values
class Car(val make: String, val model: String, val year: Short, val topSpeed: Short)

object ChopShop {
  def unapply(x: Car) = Some((x.make, x.model, x.year, x.topSpeed))
}

val ChopShop(a, b, c, d) = new Car("Chevy", "Camaro", 1978, 120)

a should be("Chevy")
b should be("Camaro")
c should be(1978)
d should be(120)

// extractor can also be used in pattern matching
class Car(val make: String, val model: String, val year: Short, val topSpeed: Short)

object ChopShop {
  def unapply(x: Car) = Some((x.make, x.model, x.year, x.topSpeed))
}

val x = new Car("Chevy", "Camaro", 1978, 120) match {
  case ChopShop(s, t, _, _) => (s, t)
  case _ => ("Ford", "Edsel")
}

x._1 should be("Chevy")
x._2 should be("Camaro")

// as long as the method signatures aren't the same, you can have as many unapply methods as you want
class Car(val make: String, val model: String, val year: Short, val topSpeed: Short)
class Employee(val firstName: String, val middleName: Option[String], val lastName: String)

object Tokenizer {
  def unapply(x: Car) = Some((x.make, x.model, x.year, x.topSpeed))

  def unapply(x: Employee) = Some((x.firstName, x.lastName))
}

val result = new Employee("Kurt", None, "Vonnegut") match {
  case Tokenizer(c, d) => "c: %s, d: %s".format(c, d)
  case _ => "Not found"
}

result should be("c: Kurt, d: Vonnegut")

// extractor can be any stable object, including instantiated classes with an unapply method
class Car(val make: String, val model: String, val year: Short, val topSpeed: Short) {
  def unapply(x: Car) = Some((x.make, x.model))
}

val camaro = new Car("Chevy", "Camaro", 1978, 122)

val result = camaro match {
  case camaro(make, model) => "make: %s, model: %s".format(make, model)
  case _ => "unknown"
}

result should be("make: Chevy, model: Camaro")

// custom extractor is typically created in the companion object of the class
class Employee(
  val firstName: String,
  val middleName: Option[String],
  val lastName: String)

object Employee {
  //factory methods, extractors, apply
  //Extractor: Create tokens that represent your object
  def unapply(x: Employee) =
    Some((x.lastName, x.middleName, x.firstName))
}

val singri = new Employee("Singri", None, "Keerthi")

val Employee(a, b, c) = singri

a should be("Keerthi")
b should be(None)
c should be("Singri")

// same with pattern matching
class Employee(
  val firstName: String,
  val middleName: Option[String],
  val lastName: String)

object Employee {
  //factory methods, extractors, apply
  //Extractor: Create tokens that represent your object
  def unapply(x: Employee) =
    Some((x.lastName, x.middleName, x.firstName))
}

val singri = new Employee("Singri", None, "Keerthi")

val result = singri match {
  case Employee("Singri", None, x) =>
    "Yay, Singri %s! with no middle name!".format(x)
  case Employee("Singri", Some(x), _) =>
    "Yay, Singri with a middle name of %s".format(x)
  case _ => "I don't care, going on break"
}

result should be("I don't care, going on break")

// to create an enumeration, create an object that extends the abstract class `Enumeration`, 
// and set a val variable to the method `Value` - it is a trick to give values to each `val`
// `Value` assigns a numerical value to fields
object Planets extends Enumeration {
  val Mercury = Value
  val Venus = Value
  val Earth = Value
  val Mars = Value
  val Jupiter = Value
  val Saturn = Value
  val Uranus = Value
  val Neptune = Value
  val Pluto = Value
}

Planets.Mercury.id should be(0)
Planets.Venus.id should be(1)

Planets.Mercury.toString should be("Mercury") //How does it get the name? by Reflection.
Planets.Venus.toString should be("Venus")

(Planets.Earth == Planets.Earth) should be(true)
(Planets.Neptune == Planets.Jupiter) should be(false)

// you can create an enumeration with your own index and your own Strings
object GreekPlanets extends Enumeration {

  val Mercury = Value(1, "Hermes")
  val Venus = Value(2, "Aphrodite")
  val Earth = Value(3, "Gaia")
  val Mars = Value(4, "Ares")
  val Jupiter = Value(5, "Zeus")
  val Saturn = Value(6, "Cronus")
  val Uranus = Value(7, "Ouranus")
  val Neptune = Value(8, "Poseidon")
  val Pluto = Value(9, "Hades")
}

GreekPlanets.Mercury.id should be(1)
GreekPlanets.Venus.id should be(2)

GreekPlanets.Mercury.toString should be("Hermes")
GreekPlanets.Venus.toString should be("Aphrodite")

(GreekPlanets.Earth == GreekPlanets.Earth) should be(true)
(GreekPlanets.Neptune == GreekPlanets.Jupiter) should be(false)

// Enumerations can be declared in one line if merely setting variables to `Value`
object Planets extends Enumeration {
  val Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune, Pluto = Value
}

Planets.Mercury.id should be(0)
Planets.Venus.id should be(1)

Planets.Mercury.toString should be("Mercury")
Planets.Venus.toString should be("Venus")

(Planets.Earth == Planets.Earth) should be(true)
(Planets.Neptune == Planets.Jupiter) should be(false)

// Enumerations can be declared with a string value only
object GreekPlanets extends Enumeration {

  val Mercury = Value("Hermes")
  val Venus = Value("Aphrodite")
  val Earth = Value("Gaia")
  val Mars = Value("Ares")
  val Jupiter = Value("Zeus")
  val Saturn = Value("Cronus")
  val Uranus = Value("Ouranus")
  val Neptune = Value("Poseidon")
  val Pluto = Value("Hades")
}

GreekPlanets.Mercury.id should be(0)
GreekPlanets.Venus.id should be(1)

GreekPlanets.Mercury.toString should be("Hermes")
GreekPlanets.Venus.toString should be("Aphrodite")

(GreekPlanets.Earth == GreekPlanets.Earth) should be(true)
(GreekPlanets.Neptune == GreekPlanets.Jupiter) should be(false)

// `Enumeration` can be extended by extending the `Value` class
object Planets extends Enumeration {

  val G = 6.67300E-11

  class PlanetValue(val i: Int, val name: String, val mass: Double, val radius: Double)
    extends Val(i: Int, name: String) {

    def surfaceGravity = G * mass / (radius * radius)

    def surfaceWeight(otherMass: Double) = otherMass * surfaceGravity

    def compare(that: PlanetValue) = this.i - that.i
  }

  val Mercury = new PlanetValue(0, "Mercury", 3.303e+23, 2.4397e6)
  val Venus = new PlanetValue(1, "Venus", 4.869e+24, 6.0518e6)
  val Earth = new PlanetValue(2, "Earth", 5.976e+24, 6.37814e6)
  val Mars = new PlanetValue(3, "Mars", 6.421e+23, 3.3972e6)
  val Jupiter = new PlanetValue(4, "Jupiter", 1.9e+27, 7.1492e7)
  val Saturn = new PlanetValue(5, "Saturn", 5.688e+26, 6.0268e7)
  val Uranus = new PlanetValue(6, "Uranus", 8.686e+25, 2.5559e7)
  val Neptune = new PlanetValue(7, "Neptune", 1.024e+26, 2.4746e7)
  val Pluto = new PlanetValue(8, "Pluto", 1.27e+22, 1.137e6)

}

Planets.Earth.mass should be(5.976e+24)
Planets.Earth.radius should be(6.37814e6)