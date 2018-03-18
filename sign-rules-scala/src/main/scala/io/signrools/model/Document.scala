package io.signrools.model

import io.signrools.api.Signer

class DocId(val value: Long) extends AnyVal

case class Signature(signer: Signer, value: Array[Byte])

case class Document(id: DocId,
                    name: String,
                    fileName: String,
                    content: Array[Byte],
                    signatures: List[Signature])

