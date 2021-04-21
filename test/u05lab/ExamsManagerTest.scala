package u05lab

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions._
import u05lab.code.{ExamResult, ExamsManager, Kind}


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

  val examsManager = ExamsManager()
  @Test
  def testExamsManager(): Unit = {
    examsManager.createNewCall("gennaio")
    examsManager.createNewCall("febbraio")
    examsManager.createNewCall("marzo")


    examsManager.addStudentResult("gennaio", "rossi", ExamResult.failed()); // rossi -> fallito
    examsManager.addStudentResult("gennaio", "bianchi", ExamResult.retired()); // bianchi -> ritirato
    examsManager.addStudentResult("gennaio", "verdi", ExamResult.succeeded(28)); // verdi -> 28
    examsManager.addStudentResult("gennaio", "neri", ExamResult.succeededCumLaude()); // neri -> 30L

    examsManager.addStudentResult("febbraio", "rossi", ExamResult.failed)
    examsManager.addStudentResult("febbraio", "bianchi", ExamResult.succeeded(20))
    examsManager.addStudentResult("febbraio", "verdi", ExamResult.succeeded(30))

    examsManager.addStudentResult("marzo", "rossi", ExamResult.succeeded(25))
    examsManager.addStudentResult("marzo", "bianchi", ExamResult.succeeded(25))
    examsManager.addStudentResult("marzo", "viola", ExamResult.failed)
  }

  @Test
  def testExamsManager2(): Unit = {
    this.testExamsManager()

    // partecipanti agli appelli di gennaio e marzo// partecipanti agli appelli di gennaio e marzo
    assertEquals(examsManager.getAllStudentsFromCall("gennaio"), Set("rossi", "bianchi", "verdi", "neri"))
    assertEquals(examsManager.getAllStudentsFromCall("marzo"), Set("rossi", "bianchi", "viola"))

    // promossi di gennaio con voto
    assertEquals(examsManager.getEvaluationsMapFromCall("gennaio").size, 2)
    assertEquals(examsManager.getEvaluationsMapFromCall("gennaio").get("verdi").get.getEvaluation.get, 28)
    assertEquals(examsManager.getEvaluationsMapFromCall("gennaio")("neri").getEvaluation.get, 30)
    // promossi di febbraio con voto
    assertEquals(examsManager.getEvaluationsMapFromCall("febbraio").size, 2)
    assertEquals(examsManager.getEvaluationsMapFromCall("febbraio").get("bianchi").get.getEvaluation.get, 20)
    assertEquals(examsManager.getEvaluationsMapFromCall("febbraio").get("verdi").get.getEvaluation.get, 30)

  }


}
