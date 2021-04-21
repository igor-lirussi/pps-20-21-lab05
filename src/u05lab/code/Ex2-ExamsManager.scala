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


//risultato esame con enum
sealed trait ExamResult {
  def getKind: Kind
  def getEvaluation: Option[Int]
  def cumLaude:Boolean
}

object ExamResult {

  def apply(): ExamResult = new ExamResultImpl()

  private class ExamResultImpl(kind: Kind= Kind.FAILED(), laude:Boolean=false, evaluation : Option[Int]=None ) extends ExamResult {
    override def getKind: Kind = kind
    override def cumLaude: Boolean = laude
    override def getEvaluation: Option[Int] = evaluation
  }

}
