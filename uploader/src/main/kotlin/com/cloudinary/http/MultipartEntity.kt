package com.cloudinary.http

import com.cloudinary.upload.request.Payload

class MultipartEntity {
    internal val parts = ArrayList<Part>()

    internal fun addTextPart(name: String, value: String): MultipartEntity {
        parts.add(Part(name, value))
        return this
    }

    internal fun addPayloadPart(payload: Payload<*>, forcedName: String? = null) = apply {
        payload.value?.let { value ->
            parts.add(Part(forcedName ?: payload.name, value))
        }
    }

    internal data class Part(val name: String, val value: Any)
}