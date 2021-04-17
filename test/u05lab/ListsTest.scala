package u05lab.code

import java.lang.UnsupportedOperationException

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions._



class ListsTest {


  val lAlph = List("a", "b", "c", "d", "e", "f")
  val lNum = List(1, 2, 3, 4, 5,  6)
  val lBool = List(true, true, true, false, false)

  @Test
  def testZipRight() {
    assertEquals(List.nil, List.nil.zipRight)
    assertEquals( List( ("a", 0), ("b",1), ("c",2),("d",3),("e",4),("f",5) ) , lAlph.zipRight)
  }


  @Test
  def testPartition() {

    //assertEquals( (List.nil, List.nil) , List.nil.partition(_!= null) ) //non capisco cosa mettere nella partition per testare la lista vuota

    //partiziono tra quelli che sono "c" e quelli che non lo sono
    assertEquals( (List("c"),List("a", "b", "d", "e", "f")) , lAlph.partition(_=="c"))

    //partiziono tra quelli pari e quelli che non lo sono
    assertEquals( (List(2,4,6),List(1,3,5)) , lNum.partition(_%2==0))
  }

  @Test
  def testSpan() {
    //spezzo quando elemento non più uguale a "a"
    assertEquals( (List("a"),List("b", "c", "d", "e", "f")) , lAlph.span(_=="a"))

    //spezzo quando elemento non più pari
    assertEquals( (List(),List(1,2,3,4,5,6)) , lNum.span(_%2==0))

    //spezzo quando elemento non più minore di tre
    assertEquals( (List(1,2,3),List(4,5,6)) , lNum.span(_<=3))

    assertEquals( (List(true, true, true),List(false, false)) , lBool.span(_==true))

    //lista vuota
    assertEquals( (List(1,2,3,4,5,6), List.nil) , lNum.span(_<=9))
    assertEquals( (List.nil,List(1,2,3,4,5,6)) , lNum.span(_>=9))
  }

  @Test
  def testReduce(): Unit ={
    assertEquals(21, lNum.reduce(_+_))
    assertEquals(false, lBool.reduce(_&&_))
    //test exception
    try{
      val emptyIntList: List[Int] = List.nil
      emptyIntList.reduce(_+_)
      assert(false)
    } catch {
      case e: UnsupportedOperationException => println("exception ok")
    }
  }


  @Test
  def testTakeRight(): Unit ={
    assertEquals( List("b", "c", "d", "e", "f"), lAlph.takeRight(5))
    assertEquals( List( "f"), lAlph.takeRight(1))
    //nessuno
    assertEquals( List(), lAlph.takeRight(0))
    //tutti
    assertEquals( List(1,2,3,4,5,6) , lNum.takeRight(9))
  }
}