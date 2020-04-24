// any method which takes a single parameter can be used as an infix operator
val g: Int = 3
(g + 4) should be(7) // + is an infix operator
g.+(4) should be(7)

// Infix operators do not work if an object has a method that takes two parameters
val g: String = "Check out the big brains on Brad!"
g indexOf 'o' should be(6) //indexOf(Char) can be used as an infix operator
// g indexOf 'o', 4 should be (6) //indexOf(Char, Int) cannot be used as an infix operator
g.indexOf('o', 7) should be(25)

// any method which does not require a parameter can be used as a postfix operator
// postfix operators have lower precedence than infix operators
val g: Int = 31
(g toHexString) should be("1f")

// prefix operators work if an object has a method name that starts with `unary_`
val g: Int = 31
(-g) should be(-31)

// prefix operator can be created for our own class
// the only identifiers that can be used as prefix operators are +, -, !, and ~
class Stereo {
  def unary_+ = "on"

  def unary_- = "off"
}

val stereo = new Stereo
(+stereo) should be("on")
(-stereo) should be("off")

// infix type `T1 op T2` consists of an infix operator `op` which gets applied to two type operands `T1` and `T2`
// infix operator `op` may be an arbitrary identifier
// type infix is a type that can be displayed in complement between two types in order to make a readable declaration
case class Person(name: String)
class Loves[A, B](val a: A, val b: B)

def announceCouple(couple: Person Loves Person) =
  couple.a.name + " is in love with " + couple.b.name

val romeo = new Person("Romeo")
val juliet = new Person("drinking games")

announceCouple(new Loves(romeo, juliet)) should be("Romeo is in love with drinking games")

// same result can be achieved by creating an infix operator method to use with infix type
case class Person(name: String) {
  def loves(person: Person) = new Loves(this, person)
}

class Loves[A, B](val a: A, val b: B)

def announceCouple(couple: Person Loves Person) =
  couple.a.name + " is in love with " + couple.b.name

val romeo = new Person("Romeo")
val juliet = new Person("drinking games")

announceCouple(romeo loves juliet) should be("Romeo is in love with drinking games")