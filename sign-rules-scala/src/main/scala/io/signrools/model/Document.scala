package io.signrools.model

import java.io.File
import java.nio.file.{Files, Paths}

import io.signrools.api.Signer

class DocId(val value: Long) extends AnyVal

case class Signature(signer: Signer, value: Array[Byte])

case class Document(id: DocId,
                    name: String,
                    fileName: String,
                    content: Array[Byte],
                    signatures: List[Signature] = Nil)

object Document {
  def apply(file: File): Document = Document(null,
    file.getName,
    file.getAbsolutePath,
    Files.readAllBytes(Paths.get(file.toURI)))
}

