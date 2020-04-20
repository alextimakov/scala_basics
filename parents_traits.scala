// traits are used to define object types by specifying the signature of the supported methods
// in traits methods can have default implementations; in contrast to classes, traits may not have constructor parameters
// classes that integrate trait below only have to provide a concrete implementation for `isSimilar` (behavior for `isNotSimilar` gets inherited from trait)
trait Similarity {
  def isSimilar(x: Any): Boolean
  def isNotSimilar(x: Any): Boolean = !isSimilar(x)
}

// class uses the extends keyword to mixin a trait if it is the only relationship the class inherits
case class Event(name: String)

trait EventListener {
  def listen(event: Event): String
}

class MyListener extends EventListener {
  def listen(event: Event): String = {
    event match {
      case Event("Moose Stampede") =>
        "An unfortunate moose stampede occurred"
      case _ => "Nothing of importance occurred"
    }
  }
}

val evt = Event("Moose Stampede")
val myListener = new MyListener
myListener.listen(evt) should be("An unfortunate moose stampede occurred")

// class can only extend from one class or trait, any subsequent extension should use the keyword `with`
case class Event(name: String)

trait EventListener {
  def listen(event: Event): String
}

class OurListener

class MyListener extends OurListener with EventListener {
  def listen(event: Event): String = {
    event match {
      case Event("Woodchuck Stampede") =>
        "An unfortunate woodchuck stampede occurred"
      case _ => "Nothing of importance occurred"
    }
  }
}

val evt = Event("Woodchuck Stampede")
val myListener = new MyListener
myListener.listen(evt) should be("An unfortunate woodchuck stampede occurred")

// traits are polymorphic and any type can be referred to by another type if related by extension
case class Event(name: String)

trait EventListener {
  def listen(event: Event): String
}

class MyListener extends EventListener {
  def listen(event: Event): String = {
    event match {
      case Event("Moose Stampede") =>
        "An unfortunate moose stampede occurred"
      case _ => "Nothing of importance occurred"
    }
  }
}

val myListener = new MyListener

myListener.isInstanceOf[MyListener] should be(true)
myListener.isInstanceOf[EventListener] should be(true)
myListener.isInstanceOf[Any] should be(true)
myListener.isInstanceOf[AnyRef] should be(true)

// traits also can use self-types, which lists the required dependencies for mixing in the trait 
// when mixing in the main trait, all self-type dependencies of that trait must also be mixed in, otherwise a compile-time error is thrown
// those dependencies can't have identical method/property names or else you'll get an illegal inheritance error
trait B {
  def bId = 2
}

trait A { self: B =>

  def aId = 1
}

//val a = new A  //***does not compile!!!***
val obj = new A with B
(obj.aId + obj.bId) should be(3)


// all values in Scala are objects (including numerical values and functions) and are instances of a class
// class hierarchy is linear, a class can only extend from one parent class
class Soldier(val firstName: String, val lastName: String) {}
class Pilot(override val firstName: String, override val lastName: String, val squadron: Long)
  extends Soldier(firstName, lastName)
val pilot = new Pilot("John", "Yossarian", 256)
pilot.firstName should be("John")
pilot.lastName should be("Yossarian")

// class that extends from another is polymorphic
class Soldier(val firstName: String, val lastName: String) {}
class Pilot(override val firstName: String, override val lastName: String, val squadron: Long)
  extends Soldier(firstName, lastName)

val pilot = new Pilot("John", "Yossarian", 256)
val soldier: Soldier = pilot

soldier.firstName should be("John")
soldier.lastName should be("Yossarian")

// abstract class, as in Java, cannot be instantiated and only inherited
abstract class Soldier(val firstName: String, val lastName: String) {}
val soldier = new Soldier  // this line will fail compilation

// class can be placed inside an abstract class just like in Java
abstract class Soldier(val firstName: String, val lastName: String) {

  class Catch(val number: Long) {
    // nothing to do here, it should just compiles
  }

}
class Pilot(override val firstName: String, override val lastName: String, val squadron: Long)
  extends Soldier(firstName, lastName)

val pilot = new Pilot("John", "Yossarian", 256)
val catchNo = new pilot.Catch(22)  // using the pilot instance's path, create an catch object for it.
catchNo.number should be(22)