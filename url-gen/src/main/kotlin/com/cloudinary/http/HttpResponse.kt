package com.cloudinary.http

data class HttpResponse(
    val httpStatusCode: Int,
    val content: String?,
    val headers: Map<String, String>
)

