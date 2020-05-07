// `Implicit` wrap around existing classes to provide extra functionality
// This is similar to monkey patching in Ruby and meta-programming in Groovy
// Creating a method `isOdd` for `Int`, which doesn't exist
class KoanIntWrapper(val original: Int) {
  def isOdd = original % 2 != 0
}

implicit def thisMethodNameIsIrrelevant(value: Int) =
  new KoanIntWrapper(value)

19.isOdd should be(true)
20.isOdd should be(false)

// The actual arguments that are eligible to be passed to an implicit parameter fall into two categories:
// all identifiers x that can be accessed at the point of the method call without a prefix and that denote implicit definition / implicit parameter
// all members of companion modules of the implicit parameter's type that are labeled implicit
// implicit values can not be top-level, they have to be members of a template
abstract class SemiGroup[A] {
  def add(x: A, y: A): A
}
abstract class Monoid[A] extends SemiGroup[A] {
  def unit: A
}
object ImplicitTest extends App {
  implicit object StringMonoid extends Monoid[String] {
    def add(x: String, y: String): String = x concat y
    def unit: String = ""
  }
  implicit object IntMonoid extends Monoid[Int] {
    def add(x: Int, y: Int): Int = x + y
    def unit: Int = 0
  }
  def sum[A](xs: List[A])(implicit m: Monoid[A]): A =
    if (xs.isEmpty) m.unit
    else m.add(xs.head, sum(xs.tail))
  println(sum(List(1, 2, 3)))  // 6
  println(sum(List("a", "b", "c")))  // "abc"
}

// Implicits rules can be imported into your scope with an import
object MyPredef {

  class KoanIntWrapper(val original: Int) {
    def isOdd = original % 2 != 0

    def isEven = !isOdd
  }

  implicit def thisMethodNameIsIrrelevant(value: Int) =
    new KoanIntWrapper(value)
}

import MyPredef._
//imported implicits come into effect within this scope
19.isOdd should be(true)
20.isOdd should be(false)

// Implicits can be used to automatically convert a value's type to another
import java.math.BigInteger
import scala.language.implicitConversions
implicit def Int2BigIntegerConvert(value: Int): BigInteger =
  new BigInteger(value.toString)

def add(a: BigInteger, b: BigInteger) = a.add(b)

add(Int2BigIntegerConvert(3), Int2BigIntegerConvert(6)) == Int2BigIntegerConvert(9) should be(true)

add(3, 6) == 9 should be(false)
add(3, 6) == Int2BigIntegerConvert(9) should be(true)

add(3, 6) == (9: BigInteger) should be(true)
add(3, 6).intValue == 9 should be(true)

// Implicits can be used to declare a value to be provided as a default as long as an implicit value is set with in the scope
// These are called Implicit Function Parameters
def howMuchCanIMake_?(hours: Int)(implicit dollarsPerHour: BigDecimal) =
  dollarsPerHour * hours

implicit val hourlyRate = BigDecimal(34)

howMuchCanIMake_?(30) should be(1020)

// Implicit Function Parameters can contain a list of implicits
def howMuchCanIMake_?(hours: Int)(implicit amount: BigDecimal, currencyName: String) =
  (amount * hours).toString() + " " + currencyName

implicit val hourlyRate = BigDecimal(34)
implicit val currencyName = "Dollars"

howMuchCanIMake_?(30) should be("1020 Dollars")

// Default arguments, though, are preferred to Implicit Function Parameters
def howMuchCanIMake_?(hours: Int, amount: BigDecimal = 34, currencyName: String = "Dollars") =
  (amount * hours).toString() + " " + currencyName

howMuchCanIMake_?(30) should be("1020 Dollars")

howMuchCanIMake_?(30, 95) should be("2850 Dollars")