package io.signrools.fsm

import io.signrools.api.{DocId, Empty, SignRule}

import scala.util.{Success, Try}

case class Context(id: DocId, state: ContextState = Idle(), signRule: SignRule = Empty) {
  def update(newState: ContextState): Try[Context] = Success(copy(state = newState))
}
