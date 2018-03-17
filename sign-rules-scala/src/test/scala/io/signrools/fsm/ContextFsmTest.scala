package io.signrools.fsm

import io.signrools.api._
import org.scalatest.{FunSpec, FunSuite}
import ru.yandex.qatools.fsm.impl.YatomataImpl

class ContextFsmTest extends FunSuite {
  test("test") {
    val docId = new DocId(1L)
    val context = Context(docId)
    val engine = new YatomataImpl(classOf[ContextFsm], new ContextFsm(context))
    engine.fire(DocUploaded(docId))
    assertResult(Uploaded())(engine.getCurrentState)

    engine.fire(SignRuleProvided(docId, Empty))
    assertResult(Signing())(engine.getCurrentState)

    engine.fire(SignRuleCompleted(docId, Empty))
    assertResult(Signing())(engine.getCurrentState)

    engine.fire(SignRuleCompleted(docId, Empty))
    assertResult(Signing())(engine.getCurrentState)

    engine.fire(DocSigned(docId))
    assertResult(Signed())(engine.getCurrentState)
  }
}
