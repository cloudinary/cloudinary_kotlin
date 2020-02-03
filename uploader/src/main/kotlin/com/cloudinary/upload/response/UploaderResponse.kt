package com.cloudinary.upload.response

open class UploaderResponse<T>(val data: T?, val error: UploadError?)