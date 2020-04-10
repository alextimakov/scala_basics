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

// sets are iterables with no duplicate values
val mySet = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
mySet.size should be(4)

// only unique values
val mySet = Set("Michigan", "Ohio", "Wisconsin", "Michigan")
mySet.size should be(3)

// sets can be added easily
val mySet = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
val aNewSet = mySet + "Illinois"

aNewSet.contains("Illinois") should be(true)
mySet.contains("Illinois") should be(false)

// sets may be of mixed types
val mySet = Set("Michigan", "Ohio", 12)

mySet.contains(12) should be(true)
mySet.contains("MI") should be(false)

// sets may be checked for member existence
val mySet = Set("Michigan", "Ohio", 12)

mySet(12) should be(true)
mySet("MI") should be(false)

// set elements can be removed
val mySet = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
val aNewSet = mySet - "Michigan"

aNewSet.contains("Michigan") should be(false)
mySet.contains("Michigan") should be(true)

// set elements can be removed easily
val mySet = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
val aNewSet = mySet -- List("Michigan", "Ohio")

aNewSet.contains("Michigan") should be(false)
aNewSet.contains("Wisconsin") should be(true)
aNewSet.size should be(2)

// removal of nonexistent elements from a set is handled gracefully
val mySet = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
val aNewSet = mySet - "Minnesota"

aNewSet.equals(mySet) should be(true)

// two sets can be intersected
val mySet1 = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
val mySet2 = Set("Wisconsin", "Michigan", "Minnesota")
val aNewSet = mySet1 intersect mySet2

aNewSet.equals(Set("Michigan", "Wisconsin")) should be(true)

// set is either subsetOf another set or not
val mySet1 = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
val mySet2 = Set("Wisconsin", "Michigan", "Minnesota")
val mySet3 = Set("Wisconsin", "Michigan")

mySet2 subsetOf mySet1 should be(false)
mySet3 subsetOf mySet1 should be(true)

// difference between two sets may be found
val mySet1 = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
val mySet2 = Set("Wisconsin", "Michigan")
val aNewSet = mySet1 diff mySet2

aNewSet.equals(Set("Ohio", "Iowa")) should be(true)

// set equivalency is independent of order
val mySet1 = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
val mySet2 = Set("Wisconsin", "Michigan", "Ohio", "Iowa")

mySet1.equals(mySet2) should be(true)