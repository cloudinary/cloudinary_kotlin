package com.cloudinary.android.uploader

import android.content.Context
import android.net.Uri
import com.cloudinary.android.uploader.request.LocalUriPayload
import com.cloudinary.upload.Uploader
import com.cloudinary.upload.request.UploadRequest
import com.cloudinary.upload.response.UploadResult
import com.cloudinary.upload.response.UploaderResponse

fun Uploader.upload(
    uri: Uri,
    context: Context,
    request: (UploadRequest.Builder.() -> Unit)? = null
): UploaderResponse<UploadResult> {
    val payload = LocalUriPayload(context, uri)
    val builder = UploadRequest.Builder(payload, this)

    request?.let { builder.request() }

    return builder.build().execute()
}

