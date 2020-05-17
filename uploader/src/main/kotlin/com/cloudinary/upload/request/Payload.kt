package com.cloudinary.upload.request

import com.cloudinary.http.Payload
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream

internal class FilePayload(override val value: File) : Payload<File> {
    override fun asInputStream() = value.inputStream()

    override val length = value.length()
    override val name: String = value.name
}

internal class UrlPayload(override val value: String) : Payload<String> {

    override val name: String = "file"
    override val length: Long = -1

    override fun asInputStream(): InputStream {
        throw UnsupportedOperationException("Remote url doesn't not support input streams")
    }
}

internal class StreamPayload(override val value: InputStream) : Payload<InputStream> {
    override val name: String = "file"
    override val length: Long = -1
    override fun asInputStream() = value
}

internal class BytesPayload(
    override val value: ByteArray,
    override val name: String = "file"
) : Payload<ByteArray> {
    override val length: Long = value.size.toLong()
    override fun asInputStream() = ByteArrayInputStream(value)
}