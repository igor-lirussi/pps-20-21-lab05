package u05lab

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions._
import u05lab.code.{ExamResult, Kind}


class ExamsManagerTest {

  @Test
  def testKind() {
    val k1 = Kind.SUCCEEDED
    assertEquals(k1, Kind.SUCCEEDED)
    val k2 = Kind.FAILED
    assertEquals(k2, Kind.FAILED)
  }

  @Test
  def testResults(): Unit ={
    //default fallito
    val examRes = ExamResult.failed()
    assertFalse(examRes.cumLaude )
    assertEquals(examRes.getEvaluation, None )
    assertEquals(examRes.getKind, Kind.FAILED() )

    //default ritirato
    val examRit = ExamResult.retired()
    assertFalse(examRit.cumLaude)
    assertEquals(examRit.getEvaluation, None )
    assertEquals(examRit.getKind, Kind.RETIRED() )

    //con lode
    val examLau = ExamResult.succeededCumLaude()
    assertTrue(examLau.cumLaude)
    assertEquals(examLau.getEvaluation, Option(30) )
    assertEquals(examLau.getKind, Kind.SUCCEEDED() )


    //passato
    val examOk = ExamResult.succeeded(28)
    assertFalse(examOk.cumLaude)
    assertEquals(examOk.getEvaluation, Option(28) )
    assertEquals(examOk.getKind, Kind.SUCCEEDED() )


  }

  @Test
  def testMarkTooHigh(): Unit = {
    try {
      val examIpm = ExamResult.succeeded(33)
      assert(false)
    } catch {
      case e: IllegalArgumentException => assert(true)
    }
  }
}