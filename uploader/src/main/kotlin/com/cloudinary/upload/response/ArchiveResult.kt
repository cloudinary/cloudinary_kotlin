package com.cloudinary.upload.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
class ArchiveResult(
    @Json(name = "public_id")
    val publicId: String?,
    @Json(name = "version")
    val version: Int?,
    @Json(name = "signature")
    val signature: String?,
    @Json(name = "resource_type")
    val resourceType: String?,
    @Json(name = "created_at")
    val createdAt: String?,
    @Json(name = "tags")
    val tags: List<Any>?,
    @Json(name = "bytes")
    val bytes: Int?,
    @Json(name = "type")
    val type: String?,
    @Json(name = "etag")
    val etag: String?,
    @Json(name = "placeholder")
    val placeholder: Boolean?,
    @Json(name = "url")
    val url: String?,
    @Json(name = "secure_url")
    val secureUrl: String?,
    @Json(name = "access_mode")
    val accessMode: String?,
    @Json(name = "resource_count")
    val resourceCount: Int?,
    @Json(name = "file_count")
    val fileCount: Int?
)