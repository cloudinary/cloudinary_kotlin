package com.cloudinary.http

interface MultipartEntity {
    val parts: Map<String, Any>
    fun addTextPart(name: String, value: String): MultipartEntity
    fun addPayloadPart(payload: Payload<*>, forcedName: String? = null): MultipartEntity

}
