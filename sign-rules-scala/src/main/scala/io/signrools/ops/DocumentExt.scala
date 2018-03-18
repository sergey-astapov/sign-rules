package io.signrools.ops

import io.signrools.api.Signer
import io.signrools.model.{Document, Signature}
import com.roundeights.hasher.Implicits._

object DocumentExt {

  private def hashFunc: Array[Byte] => Array[Byte] = a => a.sha512.hash.bytes

  implicit class DocumentOps(doc: Document) {
    def hash: Array[Byte] = {
      val list = List(s"${doc.id.value}_${doc.name}_${doc.fileName}".getBytes(), doc.content)
        .map(DocumentExt.hashFunc) ++ doc.signatures.map(s => s.value)
      val size = list.foldLeft(0)((total, array) => total + array.length)
      val array = list.foldLeft((new Array[Byte](size), 0)) {
        (acc, array) => {
          Array.copy(array, 0, acc._1, acc._2, array.length)
          (acc._1, acc._2 + array.length)
        }
      }._1
      hashFunc(array)
    }

    def sign(signer: Signer): Document =
      doc.copy(signatures = doc.signatures ++ List(Signature(signer, doc.hash)))
  }

}