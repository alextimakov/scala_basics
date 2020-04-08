// maps are iterables consisting of key->value (or (key, value)) pairs
val myMap =
  Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "IA" -> "Iowa")
myMap.size should be(4)

// maps doesn't contain duplicates
val myMap = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "MI" -> "Michigan")
myMap.size should be(3)

// maps can be added
val myMap = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "MI" -> "Michigan")
val aNewMap = myMap + ("IL" -> "Illinois")
aNewMap.contains("IL") should be(true)

// map values can be iterated
val myMap = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "MI" -> "Michigan")

val mapValues = myMap.values
mapValues.size should be(3)

// maps can be accessed
val myMap =
  Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "IA" -> "Iowa")
myMap("MI") should be("Michigan")
myMap("IA") should be("Iowa")

// maps insertion with duplicate key updates previous entry with subsequent value
val myMap = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "MI" -> "Meechigan")
val mapValues = myMap.values
mapValues.size should be(3)
myMap("MI") should be("Meechigan")

// map keys may be of mixed type
val myMap = Map("Ann Arbor" -> "MI", 49931 -> "MI")
myMap("Ann Arbor") should be("MI")
myMap(49931) should be("MI")

// nonexisting keys can be handled with getOrElse or with DefaultValue (for all map)
val myMap =
  Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "IA" -> "Iowa")
intercept[NoSuchElementException] {
  myMap("TX")
}
myMap.getOrElse("TX", "missing data") should be("missing data")

val myMap2 = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "IA" -> "Iowa") withDefaultValue "missing data"
myMap2("TX") should be("missing data")

// map elements can be removed
val myMap =
  Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "IA" -> "Iowa")
val aNewMap = myMap - "MI"
aNewMap.contains("MI") should be(false)
myMap.contains("MI") should be(true)

// map elements can be removed in multiple
val myMap =
  Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "IA" -> "Iowa")
val aNewMap = myMap -- List("MI", "OH")

aNewMap.contains("MI") should be(false)
myMap.contains("MI") should be(true)

aNewMap.contains("WI") should be(true)
aNewMap.size should be(2)
myMap.size should be(4)

// removals of non-exsiting elements are handled gracefully
val myMap =
  Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "IA" -> "Iowa")
val aNewMap = myMap - "MN"

aNewMap.equals(myMap) should be(true)

// map equivalency is independent of order
val myMap1 =
  Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "IA" -> "Iowa")
val myMap2 =
  Map("WI" -> "Wisconsin", "MI" -> "Michigan", "IA" -> "Iowa", "OH" -> "Ohio")

myMap1.equals(myMap2) should be(true)