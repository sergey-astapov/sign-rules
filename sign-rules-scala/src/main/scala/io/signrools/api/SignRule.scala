package io.signrools.api

sealed trait SignRule

case class SignBranch(left: SignRule, right: SignRule) extends SignRule

case class SignList(list: List[SignRule]) extends SignRule

class UserId(val value: Long) extends AnyVal

case class Signer(id: UserId, firstName: String, lastName: String, email: String) extends SignRule

case object Empty extends SignRule