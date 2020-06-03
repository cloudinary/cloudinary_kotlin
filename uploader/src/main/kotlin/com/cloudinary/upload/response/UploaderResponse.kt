package com.cloudinary.upload.response

open class UploaderResponse<T>(val responseCode: Int, val data: T?, val error: UploadError?, val rawResponse: String?)