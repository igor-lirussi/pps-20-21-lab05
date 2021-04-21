package u05lab.code

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


