package com.cloudinary.upload.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class UploadError(@Json(name = "error") var error: Error?) {
    constructor() : this(null)
}

@JsonClass(generateAdapter = true)
class Error(@Json(name = "message") var message: String?) {
    constructor() : this(null)
}
