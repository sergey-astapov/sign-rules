package io.signrools.fsm

import io.signrools.api.{DocId, Empty, SignRule}

case class Context(id: DocId, state: ContextState = Idle(), signRule: SignRule = Empty) {
  def update(newState: ContextState): Context = copy(state = newState)
}
