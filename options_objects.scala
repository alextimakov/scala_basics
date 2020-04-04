// options used to represent values which may not be present 
val someValue: Option[String] = Some("I am wrapped in something")
someValue should be(Some("I am wrapped in something"))

val emptyValue: Option[String] = None
emptyValue should be(None)


// function that may or may not return string
def maybeItWillReturnSomething(flag: Boolean): Option[String] = {
  if (flag) Some("Found value") else None
}

val value1 = maybeItWillReturnSomething(true)
val value2 = maybeItWillReturnSomething(false)

value1 getOrElse "No value" should be("Found value")
value2 getOrElse "No value" should be("No value")
value2 getOrElse {
  "default function"
} should be("default function")

val value1 = maybeItWillReturnSomething(true)
val value2 = maybeItWillReturnSomething(false)

value1.isEmpty should be(false)
value2.isEmpty should be(true)

// use options for pattern matching
val someValue: Option[Double] = Some(20.0)
val value = someValue match {
  case Some(v) => v
  case None => 0.0
}
value should be(20.0)

val noValue: Option[Double] = None
val value1 = noValue match {
  case Some(v) => v
  case None => 0.0
}
value1 should be(0.0)


// collection style operations
val number: Option[Int] = Some(3)
val noNumber: Option[Int] = None
val result1 = number.map(_ * 1.5)
val result2 = noNumber.map(_ * 1.5)

result1 should be(Some(4.5))
result2 should be(None)

// collection style operation with fold - provides default value if None
val number: Option[Int] = Some(3)
val noNumber: Option[Int] = None
val result1 = number.fold(1)(_ * 3)
val result2 = noNumber.fold(1)(_ * 3)

result1 should be(9)
result2 should be(1)


// objects in Scala are singletons, not static methods
object Greeting {
  def english = "Hi"

  def espanol = "Hola"
}

val x = Greeting
val y = x

x eq y should be(true) //eq checks for reference

val z = Greeting

x eq z should be(true)

// object that has the same name as a class is called a companion object of the class
class Movie(val name: String, val year: Short)

object Movie {
  def academyAwardBestMoviesForYear(x: Short) = {
    x match {
      case 1930 => Some(new Movie("All Quiet On the Western Front", 1930))
      case 1931 => Some(new Movie("Cimarron", 1931))
      case 1932 => Some(new Movie("Grand Hotel", 1932))
      case _ => None
    }
  }
}

Movie.academyAwardBestMoviesForYear(1932).get.name should be("Grand Hotel")

// companion object can also see private values and variables of the corresponding classes' instantiated objects
class Person(val name: String, private val superheroName: String)

object Person {
  def showMeInnerSecret(x: Person) = x.superheroName
}

val clark = new Person("Clark Kent", "Superman")
val peter = new Person("Peter Parker", "Spider-Man")

Person.showMeInnerSecret(clark) should be("Superman")
Person.showMeInnerSecret(peter) should be("Spider-Man")
