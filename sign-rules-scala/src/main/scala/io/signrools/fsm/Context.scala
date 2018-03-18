package io.signrools.fsm

import io.signrools.api.{Empty, SignRule}
import io.signrools.model.DocId

import scala.util.{Success, Try}

case class Context(id: DocId, state: ContextState = Idle(), signRule: SignRule = Empty) {
  def update(newState: ContextState): Try[Context] = Success(copy(state = newState))
}
