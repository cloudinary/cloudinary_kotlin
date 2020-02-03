package com.cloudinary.http

data class HttpResponse(
    internal val httpStatusCode: Int,
    internal val content: String?,
    internal val headers: Map<String, String>
)

