package com.cloudinary.upload.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ContextResult(
    @Json(name = "public_ids")
    val publicIds: List<String>
)
