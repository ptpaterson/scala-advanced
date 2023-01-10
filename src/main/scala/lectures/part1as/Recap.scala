package lectures.part1as

import scala.annotation.tailrec

object Recap extends App {

  val aCondition = false
  val aConditionedVal = if (aCondition) 42 else 65

  // Unit
  val theUnit = println("Hello!")

  //function
  def aFunction(x: Int): Int = x + 1

  // recursion stack and tail
  @tailrec
  def factorial (acc: Int, n: Int): Int =
    if (n <= 0) acc
    else factorial(acc * n, n - 1)

  // object orientation
  class Animal
  class Dog extends Animal
  val aDog: Animal = new Dog // subtyping polymorphism

  trait Carnivore {
    def eat(a: Animal): Unit
  }

  class Crocodile extends Animal with Carnivore {
    override def eat(a: Animal): Unit = println("crunch!")
  }

  // method notations
  val aCroc = new Crocodile
  aCroc.eat(aDog)
  aCroc eat aDog // natural language (infix notation).

  // operators as methods!
  1.+(2)

  // anonymous classes
  val aCarnivore = new Carnivore {
    override def eat(a: Animal): Unit = println("roar!")
  }

  // generics
  abstract class MyList[+A] // variance and variance problems
  // singleton objects and companions
  object MyList // companion

  // case classes
  case class Person(name: String, age: Int)

  // exceptions and try/catch expressions
  val throwsEx = throw new RuntimeException // type = Nothing
  val aPotFailure = try {
    throw new RuntimeException
  } catch {
    case e: Exception => "I got it"
  } finally {
    println("some stuff")
  }

  // packaging and imports

  // functional programming
  // functions are just instances of classes with "apply" methods
  val incrementer = new Function1[Int, Int] {
    override def apply(v1: Int): Int = v1 + 1
  }
  incrementer(1)

  val ananymousInc = (x: Int) => x + 1
  List(1, 2, 3).map(ananymousInc) // HOF
  // map, flatMap, filter

  // for-comprehension
  val pairs = for {
    num <- List(1,2,3) // if condition can make this a filter
    char <- List('a', 'b', 'c')
  } yield num + "-" + char

  // Scala scollection: Seqs, Arrays, Lists, Vectors, Maps, Tuples
  val aMap = Map(
    "Daniel" -> 42,
    "Paul" -> 555
  )

  // "collections": Options, Try (= Monad)
  val anOption = Some(2) // or None

  // pattern matching
  val x = 2
  val order = x match {
    case 1 => "first"
    case 2 => "second"
    case _ => x + "th"
  }

  val bob = Person("Bob", 22)
  val greeting = bob match {
    case Person(n, _) => s"Hello $n"
  }

  // all the patterns!!!
}
