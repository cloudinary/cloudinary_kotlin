package com.cloudinary.android.uploader

import android.content.Context
import android.net.Uri
import com.cloudinary.android.uploader.request.LocalUriPayload
import com.cloudinary.android.uploader.work.UploadAsyncResult
import com.cloudinary.upload.Uploader
import com.cloudinary.upload.request.FilePayload
import com.cloudinary.upload.request.UploadRequest
import com.cloudinary.upload.response.UploadResult
import com.cloudinary.upload.response.UploaderResponse
import java.io.File
import com.cloudinary.android.uploader.request.UploadRequest as AndroidUploadRequest

fun Uploader.upload(
    uri: Uri,
    context: Context,
    request: (UploadRequest.Builder.() -> Unit)? = null
): UploaderResponse<UploadResult> {
    val payload = LocalUriPayload(context, uri)
    val builder = UploadRequest.Builder(payload, this)

    request?.let { builder.request() }

    val build = builder.build()
    return build.execute()
}

fun Uploader.uploadAsync(
    uri: Uri,
    context: Context,
    request: (AndroidUploadRequest.Builder.() -> Unit)? = null
): UploadAsyncResult {
    val payload = LocalUriPayload(context, uri)
    val builder = AndroidUploadRequest.Builder(payload, this)
    request?.let { builder.request() }

    return uploadAsync(context, builder.build())
}

fun Uploader.uploadAsync(
    file: File,
    context: Context,
    request: (AndroidUploadRequest.Builder.() -> Unit)? = null
): UploadAsyncResult {
    val payload = FilePayload(file)
    val builder = AndroidUploadRequest.Builder(payload, this)
    request?.let { builder.request() }

    return uploadAsync(context, builder.build())
}

internal fun Uploader.uploadAsync(
    context: Context,
    request: AndroidUploadRequest
): UploadAsyncResult {
    return cloudinary.asyncWorkDispatcher.uploadAsync(context, request)
}