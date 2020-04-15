// formatting
// string can be placed in format
val s = "Hello World"
"Application %s".format(s) should be("Application Hello World")

// character literals can be a single character 


// character literals can be an escape sequence


// formatting can include numbers
val j = 190
"%d bottles of beer on the wall" format j - 100 should be("90 bottles of beer on the wall")

// buil-in pattern matching with first-match policy
object MatchTest1 extends App {
  def matchTest(x: Int): String = x match {
    case 1 => "one"
    case 2 => "two"
    case _ => "many" // case _ will trigger if all other cases fail.
  }
  println(matchTest(3)) // prints "many"
}

// pattern matching uses case classes and returs something
val stuff = "blue"

val myStuff = stuff match {
  case "red" =>
    println("RED"); 1
  case "blue" =>
    println("BLUE"); 2
  case "green" =>
    println("GREEN"); 3
  case _ =>
    println(stuff); 0 // case _ will trigger if all other cases fail.
}

myStuff should be(2)

// pattern matching can return complex values
val stuff = "blue"

val myStuff = stuff match {
  case "red" => (255, 0, 0)
  case "green" => (0, 255, 0)
  case "blue" => (0, 0, 255)
  case _ => println(stuff); 0
}

myStuff should be((0, 0, 255))

// pattern matching can match complex expressions
def goldilocks(expr: Any) = expr match {
  case ("porridge", "Papa") => "Papa eating porridge"
  case ("porridge", "Mama") => "Mama eating porridge"
  case ("porridge", "Baby") => "Baby eating porridge"
  case _ => "what?"
}

goldilocks(("porridge", "Mama")) should be("Mama eating porridge")

// // pattern matching can wildcard parts of expression
def goldilocks(expr: Any) = expr match {
  case ("porridge", _) => "eating"
  case ("chair", "Mama") => "sitting"
  case ("bed", "Baby") => "sleeping"
  case _ => "what?"
}

goldilocks(("porridge", "Papa")) should be("eating")
goldilocks(("chair", "Mama")) should be("sitting")

// pattern matching can substitute parts of expression
def goldilocks(expr: (String, String)) = expr match {
  case ("porridge", bear) =>
    bear + " said someone's been eating my porridge"
  case ("chair", bear) => bear + " said someone's been sitting in my chair"
  case ("bed", bear) => bear + " said someone's been sleeping in my bed"
  case _ => "what?"
}

goldilocks(("porridge", "Papa")) should be("Papa said someone's been eating my porridge")
goldilocks(("chair", "Mama")) should be("Mama said someone's been sitting in my chair")

// 