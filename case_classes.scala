// case classes are regular classes which export their constructor parameters
abstract class Term
case class Var(name: String) extends Term
case class Fun(arg: String, body: Term) extends Term
case class App(f: Term, v: Term) extends Term

// to facilitate construction of case class there is no need in `new` primitive
Fun("x", Fun("y", App(Var("x"), Var("y"))))

// constructor parameters are treated as public values and can be accessed directly
val x = Var("x")
println(x.name)

// For every case class the Scala compiler generates `equals` method which implements structural equality and `toString` method
val x1 = Var("x")
val x2 = Var("x")
val y1 = Var("y")
println("" + x1 + " == " + x2 + " => " + (x1 == x2))
println("" + x1 + " == " + y1 + " => " + (x1 == y1))

// case classes may be used with deep patterns and guards
object TermTest extends Application {
  def printTerm(term: Term) = {
    term match {
      case Var(n) =>
        print(n)
      case Fun(x, b) =>
        print("^" + x + ".")
        printTerm(b)
      case App(f, v) =>
        print("(")
        printTerm(f)
        print(" ")
        printTerm(v)
        print(")")
    }
  }
  def isIdentityFun(term: Term): Boolean = term match {
    case Fun(x, Var(y)) if x == y => true
    case _ => false
  }
  val id = Fun("x", Var("x"))
  val t = Fun("x", Fun("y", App(Var("x"), Var("y"))))
  printTerm(t)
  println
  println(isIdentityFun(id))
  println(isIdentityFun(t))
}

// case classes have implemented equals method
case class Person(first: String, last: String)

val p1 = new Person("Fred", "Jones")
val p2 = new Person("Shaggy", "Rogers")
val p3 = new Person("Fred", "Jones")

(p1 == p2) should be(false)
(p1 == p3) should be(true)

(p1 eq p2) should be(false)
(p1 eq p3) should be(false)

// case classes have automatics hashcode method
case class Person(first: String, last: String)

val p1 = new Person("Fred", "Jones")
val p2 = new Person("Shaggy", "Rogers")
val p3 = new Person("Fred", "Jones")

(p1.hashCode == p2.hashCode) should be(false)
(p1.hashCode == p3.hashCode) should be(true)

// case classes can be created without `new` primitive
case class Dog(name: String, breed: String)

val d1 = Dog("Scooby", "Doberman")
val d2 = Dog("Rex", "Custom")
val d3 = new Dog("Scooby", "Doberman") // the old way of creating using new

(d1 == d3) should be(true)
(d1 == d2) should be(false)
(d2 == d3) should be(false)

// case classes have toString method defined
case class Dog(name: String, breed: String)
val d1 = Dog("Scooby", "Doberman")
d1.toString should be("Dog(Scooby,Doberman)")

// case classes have automatic properties
case class Dog(name: String, breed: String)

val d1 = Dog("Scooby", "Doberman")
d1.name should be("Scooby")
d1.breed should be("Doberman")

// case classes can have mutable properties though it's discouraged
case class Dog(var name: String, breed: String) // var defines mutability of property (vs default val)
val d1 = Dog("Scooby", "Doberman")

d1.name should be("Scooby")
d1.breed should be("Doberman")

d1.name = "Scooby Doo"

d1.name should be("Scooby Doo")
d1.breed should be("Doberman")

// there are safer alternatives to alter case class
case class Dog(name: String, breed: String) // Doberman

val d1 = Dog("Scooby", "Doberman")

val d2 = d1.copy(name = "Scooby Doo") // copy the case class but change the name in the copy

d1.name should be("Scooby") // original left alone
d1.breed should be("Doberman")

d2.name should be("Scooby Doo")
d2.breed should be("Doberman")

// case classes can have default and named parameters
case class Person(first: String, last: String, age: Int = 0, ssn: String = "")
val p1 = Person("Fred", "Jones", 23, "111-22-3333")
val p2 = Person("Samantha", "Jones") // note missing age and ssn
val p3 = Person(last = "Jones", first = "Fred", ssn = "111-22-3333") // note the order can change, and missing age
val p4 = p3.copy(age = 23)

p1.first should be("Fred")
p1.last should be("Jones")
p1.age should be(23)
p1.ssn should be("111-22-3333")

p2.first should be("Samantha")
p2.last should be("Jones")
p2.age should be(0)
p2.ssn should be("")

p3.first should be("Fred")
p3.last should be("Jones")
p3.age should be(0)
p3.ssn should be("111-22-3333")

(p1 == p4) should be(true)

// case classes can be disassembled to parts as a tuple
case class Person(first: String, last: String, age: Int = 0, ssn: String = "")
val p1 = Person("Fred", "Jones", 23, "111-22-3333")

val parts = Person.unapply(p1).get

parts._1 should be("Fred")
parts._2 should be("Jones")
parts._3 should be(23)
parts._4 should be("111-22-3333")

// case classes are serializable
case class PersonCC(firstName: String, lastName: String)
val indy = PersonCC("Indiana", "Jones")

indy.isInstanceOf[Serializable] should be(true)

class Person(firstName: String, lastName: String)
val junior = new Person("Indiana", "Jones")

junior.isInstanceOf[Serializable] should be(false)