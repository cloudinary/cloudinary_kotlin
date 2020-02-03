package com.cloudinary.upload.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class GenerateSpriteResult(
    @Json(name = "css_url")
    val cssUrl: String? = null,
    @Json(name = "image_url")
    val imageUrl: String? = null,
    @Json(name = "secure_css_url")
    val secureCssUrl: String? = null,
    @Json(name = "secure_image_url")
    val secureImageUrl: String? = null,
    @Json(name = "json_url")
    val jsonUrl: String? = null,
    @Json(name = "secure_json_url")
    val secureJsonUrl: String? = null,
    val version: Int? = null,
    @Json(name = "public_id")
    val publicId: String? = null,
    @Json(name = "image_infos")
    val imageInfos: Map<String, ResultSprite> = emptyMap(),
    val status: String? = null
)

@JsonClass(generateAdapter = true)
class ResultSprite {
    val width: Int = 0
    val height: Int = 0
    val x: Int = 0
    val y: Int = 0
}