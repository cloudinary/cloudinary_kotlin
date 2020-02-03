package com.cloudinary.upload.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class StatusResult(val result: String)