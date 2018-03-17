package io.signrools.api

class DocId(val value: Long) extends AnyVal

sealed abstract class Event extends Serializable {
  val id: DocId
}

case class DocUploaded(id: DocId) extends Event

case class SignRuleProvided(id: DocId, rule: SignRule) extends Event

case class SignRuleUpdated(id: DocId, rule: SignRule) extends Event

case class SignRuleCompleted(id: DocId, rule: SignRule) extends Event

case class DocSigned(id: DocId) extends Event