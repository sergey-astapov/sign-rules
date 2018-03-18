package io.signrools.api

import io.signrools.model.DocId

sealed abstract class Event extends Serializable {
  val id: DocId
}

case class DocUploaded(id: DocId) extends Event

case class SignRuleProvided(id: DocId, rule: SignRule) extends Event

case class SignRuleUpdated(id: DocId, rule: SignRule) extends Event

case class SignRuleCompleted(id: DocId, rule: SignRule) extends Event

case class DocSigned(id: DocId) extends Event