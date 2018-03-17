package io.signrools.fsm

import io.signrools.api._

import scala.util._

sealed trait ContextState extends Serializable {
  def unsupported(e: Event): Try[Event] = {
    Failure(new IllegalAccessException(s"Unsupported event: $e"))
  }

  def validate(event: Event): Try[Event]
}

case class Idle() extends ContextState {
  override def validate(event: Event): Try[Event] = event match {
    case e @ DocUploaded(_) => Success(e)
    case e @ _ => unsupported(e)
  }
}

case class Uploaded() extends ContextState {
  override def validate(event: Event): Try[Event] = event match {
    case e @ SignRuleProvided(_, _) => Success(e)
    case e @ _ => unsupported(e)
  }
}

case class Signing() extends ContextState {
  override def validate(event: Event): Try[Event] = event match {
    case e @ SignRuleUpdated(_, _) => Success(e)
    case e @ SignRuleCompleted(_, _) => Success(e)
    case e @ DocSigned(_) => Success(e)
    case e @ _ => unsupported(e)
  }
}

case class Signed() extends ContextState {
  override def validate(event: Event): Try[Event] = event match {
    case e @ _ => unsupported(e)
  }
}

