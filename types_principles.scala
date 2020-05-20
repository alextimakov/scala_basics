// uniform access principle states that variables and parameterless functions should be accessed using the same syntax
// Scala supports this principle by allowing parentheses to not be placed at call sites of parameterless functions
class Test1(val age: Int = 10)
class Test2(_age: Int) {
  def age: Int = _age
}

new Test1(10).age should be(10)
new Test2(11).age should be(11)

// scala provides type inference: infers constraints, and attempts to unify a type
// using type inference the type that you instantiate will be the `val` or `var` reference type
class MyContainer[A](val a: A)(implicit manifest: scala.reflect.Manifest[A]) {
  def contents = manifest.runtimeClass.getSimpleName
}

val fruitBasket = new MyContainer(new Orange())
fruitBasket.contents should be("Orange")

// you can explicitly declare the type variable of the object during instantiation
class MyContainer[A](val a: A)(implicit manifest: scala.reflect.Manifest[A]) {
  def contents = manifest.runtimeClass.getSimpleName
}

val fruitBasket = new MyContainer[Fruit](new Orange())
fruitBasket.contents should be("Fruit")

// you can coerce your object to a type
class MyContainer[A](val a: A)(implicit manifest: scala.reflect.Manifest[A]) {
  def contents = manifest.runtimeClass.getSimpleName
}

val fruitBasket: MyContainer[Fruit] = new MyContainer(new Orange())
fruitBasket.contents should be("Fruit")

// scala's type system has to account for class hierarchies together with polymorphism
// class hierarchies allow the expression of subtype relationships
// variance annotations allow to express the following relationships between class hierarchies & polymorphic types
// those are Covariant ([+T]), Contravariant ([-T]) and Invariant ([T]) 

// Covariant allows to set the container to either a variable with the same type or parent type
class MyContainer[+A](val a: A)(implicit manifest: scala.reflect.Manifest[A]) {
  def contents = manifest.runtimeClass.getSimpleName
}

val fruitBasket: MyContainer[Fruit] = new MyContainer[Orange](new Orange())
fruitBasket.contents should be("Orange")


// The problem with covariance is that object can't be mutated, set or changed since it has to guarantee that what it is a valid type
class NavelOrange extends Orange //Creating a subtype to prove a point
val navelOrangeBasket: MyContainer[NavelOrange] = new MyContainer[Orange](new Orange()) //Bad!
val tangeloBasket: MyContainer[Tangelo] = new MyContainer[Orange](new Orange()) //Bad!

// Contravariance allows to apply any container with a certain type to a container with a superclass of that type
class MyContainer[-A](a: A)(implicit manifest: scala.reflect.Manifest[A]) { //Can't receive a val because it would be in a covariant position
  def contents = manifest.runtimeClass.getSimpleName
}

val citrusBasket: MyContainer[Citrus] = new MyContainer[Citrus](new Orange)
citrusBasket.contents should be("Citrus")
val orangeBasket: MyContainer[Orange] =
  new MyContainer[Citrus](new Tangelo)
orangeBasket.contents should be("Citrus")
val tangeloBasket: MyContainer[Tangelo] =
  new MyContainer[Citrus](new Orange)
tangeloBasket.contents should be("Citrus")
val bananaBasket: MyContainer[Banana] = new MyContainer[Fruit](new Apple)
bananaBasket.contents should be("Fruit")

// a superclass variable reference (contravariant position) or a subclass variable reference (covariant position) 
// of type Invariant can't be created and used  
class MyContainer[A](val a: A)(implicit manifest: scala.reflect.Manifest[A]) {
  def contents = manifest.runtimeClass.getSimpleName
}

val citrusBasket: MyContainer[Citrus] = new MyContainer[Citrus](new Orange)
citrusBasket.contents should be("Citrus")

// method's type signature comprises its name; the number, order, and types of its parameters, if any; and its result type
// type signature of a class, trait, or singleton object comprises its name, the type signatures of all of its members and constructors, 
// and its declared inheritance and mixin relations
// in Java you declare a generic type within a <>, in Scala [] is used
val z: List[String] = "Do" :: "Re" :: "Mi" :: "Fa" :: "So" :: "La" :: "Te" :: "Do" :: Nil

// most of the time, Scala will infer the type and [] are optional
val z = "Do" :: "Re" :: "Mi" :: "Fa" :: "So" :: "La" :: "Te" :: "Do" :: Nil 
//Infers that the list assigned to variable is of type List[String]

// trait can be declared containing a type, where a concrete implementer will satisfy the type
trait Randomizer[A] {
  def draw(): A
}

class IntRandomizer extends Randomizer[Int] {
  def draw() = {
    import util.Random
    Random.nextInt()
  }
}

val intRand = new IntRandomizer
(intRand.draw <= Int.MaxValue) should be(true)

// class meta-information can be retrieved by class name by using `classOf[className]`
classOf[String].getCanonicalName should be("java.lang.String")
classOf[String].getSimpleName should be("String")

// class meta-information can be derived from an object reference using `getClass()`
val zoom = "zoom"
zoom.isInstanceOf[String] should be(true)
zoom.getClass.getCanonicalName should be("java.lang.String")
zoom.getClass.getSimpleName should be("String")

// `isInstanceOf[className]` is used to determine if an object reference is an instance of a given class
trait Randomizer[A] {
  def draw(): A
}

class IntRandomizer extends Randomizer[Int] {
  def draw() = {
    import util.Random
    Random.nextInt()
  }
}

val intRand = new IntRandomizer
intRand.isInstanceOf[IntRandomizer] should be(true)
intRand.isInstanceOf[Randomizer[Int]] should be(true)
intRand.draw.isInstanceOf[Int] should be(true)