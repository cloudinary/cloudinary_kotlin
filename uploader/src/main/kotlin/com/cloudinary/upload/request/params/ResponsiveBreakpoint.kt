package com.cloudinary.upload.request.params

import com.cloudinary.transformation.Transformation
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponsiveBreakpoint(
    @Json(name = "create_derived")
    val createDerived: Boolean,

    val transformation: Transformation? = null,
    val format: String? = null,

    @Json(name = "max_width")
    val maxWidth: Int? = null,

    @Json(name = "min_width")
    val minWidth: Int? = null,

    @Json(name = "byte_step")
    val byteStep: Int? = null,

    @Json(name = "max_images")
    val maxImages: Int? = null
)

