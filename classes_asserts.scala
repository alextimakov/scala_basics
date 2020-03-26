// define variable with 'val'
val left = 2
val right = 1

// assertions can be made with assert(bool) \ assertResult \ intercept
assert(left==right)

// assertion also can be made with should (Domain Specific Language)
val result = 3
result should equal(3)  // can customize equality
result should ===(3)  // can customize equality and enforce type constraints
result should be(3)  // cannot customize equality, so fastest to compile
result shouldEqual 3  // can customize equality, no parentheses required
result shouldBe 3  // cannot customize equality, so fastest to compile, no parentheses required

true should be (true)
left shouldEqual 2

// classes are static templates, defined with 'class'
class User  // class with default constuctor

class Point(x: Int, y: Int) = {
    override def toString(): String = "(" + x + ", " + y + ")"
}  // primary constructor is in the class signature

// classes are instantianed with 'new' primitive
object Classes {
  def main(args: Array[String]) = {
    val pt = new Point(1, 2)
    println(pt)
  }
}

