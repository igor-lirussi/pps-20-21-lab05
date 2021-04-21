package u05lab.code

import scala.collection.mutable.{Map => MutMap}
import scala.collection.Set

//enum
//object Kind extends Enumeration {
//  type Kind = Value
//  val RETIRED, FAILED, SUCCEEDED = Value
//}

sealed trait Kind

object Kind {
  case class RETIRED() extends Kind
  case class FAILED() extends Kind
  case class SUCCEEDED() extends Kind
}


//risultato esame usando enum
sealed trait ExamResult {
  def getKind: Kind
  def getEvaluation: Option[Int]
  def cumLaude:Boolean
}

object ExamResult {

  def failed(): ExamResult = new ExamResultImpl()
  def succeeded(mark:Int): ExamResult = new ExamResultImpl(Kind.SUCCEEDED(), Option(mark))
  def succeededCumLaude(): ExamResult = new ExamResultImpl(Kind.SUCCEEDED(), Option(30), true)
  def retired(): ExamResult = new ExamResultImpl(Kind.RETIRED())

  private class ExamResultImpl(kind: Kind = Kind.FAILED(), evaluation: Option[Int] = None, laude: Boolean = false) extends ExamResult {
    if (evaluation.isDefined) if (evaluation.get > 30) throw new IllegalArgumentException

    override def getKind: Kind = kind
    override def cumLaude: Boolean = laude && evaluation.contains(30)
    override def getEvaluation: Option[Int] = evaluation
  }
}

sealed trait ExamsManager {
  def createNewCall(call: String)

  def addStudentResult(call: String, student: String, examResult: ExamResult)

  def getAllStudentsFromCall(call: String): Set[String]

  def getEvaluationsMapFromCall(call: String): Map[String, Int]

  def getResultsMapFromStudent(student: String): Map[String, String]

  def getBestResultFromStudent(student: String): Option[Int]
}

object ExamsManager {

  def apply(): ExamsManager = new ExamsManagerImpl()

  private class ExamsManagerImpl() extends ExamsManager {
    val calls : MutMap[String, MutMap[String, ExamResult]] = MutMap[String, MutMap[String, ExamResult]]()
    override def createNewCall(call: String): Unit = calls.addOne(call -> MutMap[String, ExamResult]())

    override def addStudentResult(call: String, student: String, examResult: ExamResult): Unit = {
      if (!calls.contains(call)) throw new IllegalArgumentException
      calls.get(call).get.addOne(student -> examResult)
    }

    override def getAllStudentsFromCall(call: String): Set[String] = {
      if (!calls.contains(call)) throw new IllegalArgumentException
      calls.get(call).get.keySet
    }

    override def getEvaluationsMapFromCall(call: String): Map[String, Int] = ???

    override def getResultsMapFromStudent(student: String): Map[String, String] = ???

    override def getBestResultFromStudent(student: String): Option[Int] = ???
  }



}
