package com.cloudinary.http

import com.cloudinary.upload.request.Payload

interface MultipartEntity {
    val parts: MutableList<Pair<String, Any>>
    fun addTextPart(name: String, value: String): MultipartEntity
    fun addPayloadPart(payload: Payload<*>, forcedName: String? = null): MultipartEntity
}

class MultipartEntityImpl : MultipartEntity {
    override val parts = mutableListOf<Pair<String, Any>>()

    override fun addTextPart(name: String, value: String): MultipartEntity {
        parts.add(Pair(name, value))
        return this
    }

    override fun addPayloadPart(payload: Payload<*>, forcedName: String?) = apply {
        payload.value?.let { value ->
            parts.add(Pair(forcedName ?: payload.name, value))
        }
    }
}