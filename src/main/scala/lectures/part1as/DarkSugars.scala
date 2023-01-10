package lectures.part1as

import scala.util.Try

object DarkSugars extends App {
  // number 1
  // methods with single param
  def singleArgMethod(arg: Int): String = s"$arg little ducks..."

  val description = singleArgMethod {
    // write some code
    42
  }

  val aTryInstance = Try { // like javas try {...}
    throw new RuntimeException
  }

  List(1,2,3).map { x =>
    x + 1
  }

  // sugar #2: single abstract method
  trait Action {
    def act(x: Int): Int
  }

  val anInstance: Action = new Action { // want you to convert to "Single Abstract Method"
    override def act(x: Int): Int = x + 1
  }

  val aFunkyInstance: Action = (x:Int) => x + 1 // magic to determine that this is the single method

  // example: Runnables, instance of a trait that can be passed to threads
  val aThread = new Thread(new Runnable {
    override def run(): Unit = println("hello")
  })

  val aFunkyThread = new Thread(() => println("hello"))

  abstract class AnAbstractType {
    def implemented: Int = 23
    def f(a: Int): Unit
  }

  val anAbstractInstance: AnAbstractType = (a: Int) => println("sweet")

  // sugar #3: the :: and #:: methods are special

  val prependedList = 2 :: List(3, 4)
  // normally like 2.::(List(3, 4))
  // actually like
  List(3, 4).::(2)

  // scala specification associativity is determined by operators last character. ends in a colon it is right-associative
  1 :: 2 :: 3 :: List(4, 5)
  // actually
  List(4, 5).::(3).::(2).::(1)

  class MyStream[T] {
    def -->:(value: T): MyStream[T] = this // actual implementation here
  }

  val myStream = 1 -->: 2 -->: 3 -->: new MyStream

  // sugar #4: multiword method naming
  class TeenGirl(name: String) {
    def `and then said`(gossip: String) = println(s"$name said $gossip")
  }

  val Lilly = new TeenGirl("Lilly")
  Lilly `and then said` "Scala is awesome"

  // sugar #5: infix types
  class Composite[A, B]
  val composite: Int Composite String = ???

  class -->[A,B]
  val towards: Int --> String = ???

  // sugar #6: update() is very special, much like apply()

  val anArray = Array(1,2,3)
  anArray(2) = 7 // rewritten to anArray.update(2, 7)
  // used in mutable collections
  // remember apply() and update()

  // sugar #7: setters for mutable containers
  class Mutable {
    private var internalMember: Int = 0 // private for OO
    def member = internalMember // "getter"
    def member_=(value: Int): Unit = internalMember = value // "setter"
  }
  val aMutable = new Mutable
  aMutable.member = 42 // aMutable.member_=(42)
}
