package lectures.part1as

object PatternMatching extends App {

  val numbers = List(1)
  val description = numbers match {
    case head :: Nil => println(s"the only ement is $head.")
    case _ =>
  }

  /*
   - costants
   - wildcards
   - case classes
   - tuples
   - some special magic like above
   */

  class Person(val name: String, val age: Int)

  object Person { // could have any name, like `PersonPattern`. the important thing is the unapply method
    // def unapply(person: Person): Option[(String, Int)] = Some((person.name, person.age))

    def unapply(person: Person): Option[(String, Int)] =
      if (person.age < 21) None
      else Some((person.name, person.age))

    def unapply(age: Int): Option[String] =
      if (age < 21) Some("minor")
      else Some("adult")
  }

  val bob = new Person("Bob", 25)
  val greeting = bob match {
    case Person(n,a) => s"Hi, my name is $n, and I am $a years old."
  }
  println(greeting)

  val legalStatus = bob.age match {
    case Person(status) => s"Legal status is $status"
  }
  println(legalStatus)

  /*
    Exercise.
   */
//  object singleDigit {
//    def unapply(x: Int): Option[Boolean] =
//      if (x > -10 && x < 10) Some(true)
//      else None
//  }
//  object evenNumber {
//    def unapply(x: Int): Option[Boolean] =
//      if (x % 2 == 0) Some(true)
//      else None
//  }

  object singleDigit {
    def unapply(x: Int) = x > -10 && x < 10
  }
  object evenNumber {
    def unapply(x: Int) = x % 2 == 0
  }

  val n: Int = 42
  // val mathProperty = n match {
  //  case x if x < 10 => "single digit"
  //  case x if x % 2 == 0 => "even number"
  //  case _ => "no property"
  // }
  val mathProperty = n match {
    case singleDigit() => "single digit"
    case evenNumber() => "even number"
    case _ => "no property"
  }
  println(mathProperty)

  // infix patterns
  case class Or[A, B](a: A, b: B) // like Either
  val either = Or(2, "two")
  val humanDescription = either match {
  //  case Or(number, string) => s"$number is written as $string"
    case number Or string => s"$number is written as $string"
  }
  println(humanDescription)

  // decomposing sequences
  val vararg = numbers match {
    case List(1, _*) => "starting with 1"
  }

  abstract class MyList[+A] {
    def head: A = ???
    def tail: MyList[A] = ???
  }
  case object Empty extends MyList[Nothing]
  case class Cons[+A](override val head: A, override val tail: MyList[A]) extends MyList[A]

  object MyList {
    def unapplySeq[A](list: MyList[A]): Option[Seq[A]] =
      if (list == Empty) Some(Seq.empty)
      else unapplySeq(list.tail) map { list.head +: _ }
  }

  val myList: MyList[Int] = Cons(1, Cons(2, Cons(3, Empty)))
  val decomposed = myList match {
    case MyList(1, 2, _*) => "starting with 1, 2"
    case MyList(1, 2, _*) => "starting with 1, 2"
  }
  println(decomposed)

  // custom return types for unapply
  // only things that you need are isEmpty: Boolean, get: something
  abstract class Wrapper[T] {
    def isEmpty: Boolean
    def get: T
  }
  object PersonWrapper {
    def unapply(person: Person) = new Wrapper[String] {
      def isEmpty = false
      def get = person.name
    }
  }

  println(bob match {
    case PersonWrapper(n) => s"This person's name is $n"
    case _ => "an alien"
  })
}
