package io.signrools.ops

import io.signrools.api.Signer
import io.signrools.model.{Document, Signature}
import com.roundeights.hasher.Implicits._

object DocumentExt {

  implicit class DocumentOps(doc: Document) {
    def hash: Array[Byte] = {
      val list = List(s"${doc.id.value}_${doc.name}_${doc.fileName}".getBytes(), doc.content)
        .map(ba => ba.sha512.hash.bytes) ++ doc.signatures.map(s => s.value)
      val size = list.foldLeft(0)((total, array) => total + array.length)
      list.foldLeft((new Array[Byte](size), 0)) {
        (acc, array) => {
          Array.copy(array, 0, acc._1, acc._2, array.length)
          (acc._1, acc._2 + array.length)
        }
      }._1.sha512.hash.bytes
    }

    def sign(signer: Signer): Document =
      doc.copy(signatures = doc.signatures ++ List(Signature(signer, doc.hash)))
  }

}