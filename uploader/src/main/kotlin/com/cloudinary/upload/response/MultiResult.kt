package com.cloudinary.upload.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class MultiResult(
    val url: String? = null,
    @Json(name = "secure_url")
    val secureUrl: String? = null,
    @Json(name = "public_id")
    val publicId: String? = null,
    val version: Int? = null,
    val status: String? = null
)