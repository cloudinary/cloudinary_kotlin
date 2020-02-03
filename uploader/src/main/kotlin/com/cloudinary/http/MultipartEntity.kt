package com.cloudinary.http

import com.cloudinary.upload.request.Payload
import com.cloudinary.util.cldIsRemoteUrl
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream

class MultipartEntity {
    internal val parts = ArrayList<Part>()

    internal fun addTextPart(name: String, value: String): MultipartEntity {
        parts.add(Part(name, value))
        return this
    }

    internal fun addFilePart(name: String, file: InputStream): MultipartEntity {
        parts.add(Part(name, file))
        return this
    }

    internal fun addFilePart(name: String, file: File): MultipartEntity {
        parts.add(Part(name, file))
        return this
    }

    internal fun addPayloadPart(payload: Payload<out Any?>, forcedName: String? = null): MultipartEntity {
        val name = forcedName ?: payload.name
        when (payload.value) {
            is String -> if (payload.value.cldIsRemoteUrl()) addTextPart(payload.name, payload.value)
            else addFilePart(name, File(payload.value))
            is File -> addFilePart(name, payload.value)
            is InputStream -> addFilePart(name, payload.value)
            is ByteArray -> addFilePart(name, ByteArrayInputStream(payload.value))
        }
        return this
    }

    internal data class Part(val name: String, val value: Any)
}