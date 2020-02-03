package com.cloudinary.upload.request

import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream

internal abstract class Payload<T>(internal val value: T) {
    abstract fun asInputStream(): InputStream
    abstract val length: Long
    abstract val name: String
}

internal class FilePayload(file: File) : Payload<File>(file) {
    override fun asInputStream() = value.inputStream()

    override val length = value.length()
    override val name: String = value.name
}

internal class UrlPayload(remoteUrl: String) : Payload<String>(remoteUrl) {

    override val name: String = "file"
    override val length: Long = -1

    override fun asInputStream(): InputStream {
        throw UnsupportedOperationException("Remote url doesn't not support input streams")
    }
}

internal class StreamPayload(stream: InputStream) : Payload<InputStream>(stream) {
    override val name: String = "file"
    override val length: Long = -1
    override fun asInputStream() = value
}

internal class BytesPayload(
    bytes: ByteArray,
    override val name: String = "file",
    override val length: Long = bytes.size.toLong()
) : Payload<ByteArray>(bytes) {
    override fun asInputStream() = ByteArrayInputStream(value)
}