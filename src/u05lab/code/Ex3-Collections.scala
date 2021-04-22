package u05lab.code

import java.util.concurrent.TimeUnit

import u05lab.code.PerformanceUtils.measure

import scala.concurrent.duration.FiniteDuration

object PerformanceUtils {
  case class MeasurementResults[T](result: T, duration: FiniteDuration) extends Ordered[MeasurementResults[_]] {
    override def compare(that: MeasurementResults[_]): Int = duration.toNanos.compareTo(that.duration.toNanos)
  }

  def measure[T](msg: String)(expr: => T): MeasurementResults[T] = {
    val startTime = System.nanoTime()
    val res = expr
    val duration = FiniteDuration(System.nanoTime()-startTime, TimeUnit.NANOSECONDS)
    if(!msg.isEmpty) println(msg + " -- " + duration.toNanos + " nanos; " + duration.toMillis + "ms")
    MeasurementResults(res, duration)
  }

  def measure[T](expr: => T): MeasurementResults[T] = measure("")(expr)
}


object CollectionsTest extends App {
  /* Linear sequences: List, ListBuffer */
  println("--List")
  import scala.collection.immutable.List //altrimente prende l'implementazione dell es1
  val l :List[String] = List("a", "b", "c") //create
  println (l. isInstanceOf [ scala.collection.immutable.List[_] ])
  println(l.last + " in position " + l.size) //read
  println( "d" :: l)
  //update delete impossibli

  println("--ListBuffer") //lista mutabile
  import scala.collection.mutable.ListBuffer
  val numbers = ListBuffer [Int](0,14,5) //create mutable
  println(numbers(2))
  numbers += 1 //update
  numbers.+=(2)
  numbers.+=(3)
  numbers.-=(2) //delete
  println(numbers)

  /* Indexed sequences: Vector, Array, ArrayBuffer */
  println("--Vector") //seq elementi immutabile
  val v :Vector[String] = Vector("ciao", "test", "fine") //create
  println(v.size + " " + v(0)) //read
  //update delete impossibili

  println("--Array") //mutabili solo elementi
  val arr : Array[String] = Array("ciao", "test", "fine") //create
  println(arr.size + " " + arr(1)) //read
  arr(1) = "modificato"   //update
  println(arr.size + " " + arr(1))
  //delete impossible

  println("--ArrayBuffer") //sequ indicizzata mutabile solo in lunghezza
  import scala.collection.mutable.ArrayBuffer
  val arrBuf : ArrayBuffer[String] = ArrayBuffer("Mario") //create
  println(arrBuf(0)) //read
  arrBuf(0) = "Igor" //update
  arrBuf += ("Lirussi", "Typo")
  println(arrBuf)
  arrBuf -= ("Typo") //delete
  println(arrBuf)


  /* Sets */
  println("--Sets") //immutabile e non c'è ripetizione
  var set : Set[Int] = Set(1,2,3,4,5,6,7) //create
  println(set(3)) //read contains
  println(set & Set(2)) //intersection
  //update delete impossibili
  set += 8 //riasegnato un nuovo set con aggiunto 8

  import collection.mutable.{Set => MutSet}
  val mutSet : MutSet[Int] = MutSet(1,2,3,4,5,6,7) //create
  println(mutSet(3)) //read contains
  mutSet += 8 //update
  mutSet -= 8 //delete




  /* Maps */
  println("--Maps") //immutable map
  var map: Map[String, String] = Map("name" -> "Igor", "surname" -> "Lirussi") //crete
  println(map.get("name")) //read
  map += ("test"-> "value") //riassegamento

  //mutable map
  import collection.mutable.{Map => MutMap}
  val mutMap: MutMap[String, String] = MutMap("name" -> "Igor", "surname" -> "Lirussi") //crete
  println(mutMap.get("name")) //read
  mutMap += ("name" -> "mario") //update
  println(mutMap)


  /* Comparison */
  println("---COMPARISON---")
  import PerformanceUtils._
  val lst = (1 to 1000000).toList
  val vec = (1 to 1000000).toVector
  assert( measure("lst last"){ lst.last } > measure("vec last"){ vec.last } )

  val arr1 = (1 to 1000000).toArray
  val buff = (1 to 1000000).toBuffer
  //assert( measure("arr last"){ arr1.last } < measure("buff last"){ buff.last } )

  val lstMut = ListBuffer.range(1, 1000000)
  assert( measure("lst last"){ lst.last } > measure("lstMut last"){ lstMut.last } )

  val stream : LazyList[Int] = LazyList.range(0,1000000)
  assert( measure("lst last"){ lst.last } < measure("lazLst last"){ stream.last } )

  val arrBuff : ArrayBuffer[Int] = ArrayBuffer.range(0,1000000)
  assert( measure("arr last"){ arr1.last } > measure("Arrbuff last"){ arrBuff.last } )


  var set2 : Set[Int] = Set.range(0,1000000)
  val mutSet2 : MutSet[Int] = MutSet.range(0,1000000)
  assert( measure("Set last"){ set2.last } < measure("mutSet last"){ mutSet2.last } )



}