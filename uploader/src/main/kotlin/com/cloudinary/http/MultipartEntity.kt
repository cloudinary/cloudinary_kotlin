package com.cloudinary.http

class MultipartEntityImpl : MultipartEntity {
    override val parts = sortedMapOf<String, Any>()

    override fun addTextPart(name: String, value: String): MultipartEntity {
        parts[name] = value
        return this
    }

    override fun addPayloadPart(payload: Payload<*>, forcedName: String?) = apply {
        payload.value?.let { value ->
            parts[forcedName ?: payload.name] = value
        }
    }

    data class Part(val name: String, val value: Any)
}