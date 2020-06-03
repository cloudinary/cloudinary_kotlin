package com.cloudinary.android.uploader.request

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns.SIZE
import com.cloudinary.upload.request.Payload
import java.io.File
import java.io.InputStream

internal class LocalUriPayload(private val context: Context, internal val uri: Uri) : Payload<InputStream> {
    private val safeScheme: String
    private val safePath: String

    init {
        val scheme = uri.scheme
        val path = uri.path
        require(scheme != null) { "Uri must be absolute - scheme is null)" }
        require(path != null) { "Invalid URI - path is null" }
        safeScheme = scheme
        safePath = path
    }

    override fun asInputStream(): InputStream = context.contentResolver.openInputStream(uri)
        ?: throw IllegalArgumentException("Provided Uri cannot be read: $value")

    override val name: String = "file"
    override val length: Long by lazy {
        // this is called in the uploading thread, guaranteeing this will run in a background thread
        // if the upload itself is in a background thread.
        fetchFileSize(uri, context)
    }

    /**
     * Since the core uploader has no access to Android classes (such as URI) this beahves as an input-stream
     * payload in the underlying network infrastructure.
     * Note: This can cause issues when trying to use UriPayload with OkHttp - however OkHttp does not support URIs
     * to begin with, so it's not a limitation.
     */
    override val value: InputStream = asInputStream()

    private fun fetchFileSize(uri: Uri, context: Context): Long {
        return if (ContentResolver.SCHEME_FILE == safeScheme) {
            // get size from file
            File(safePath).length()
        } else {
            // try to get length from the uri query
            context.contentResolver.query(
                uri,
                arrayOf(SIZE),
                null,
                null,
                null
            ).use {
                if (it != null && it.moveToFirst()) {
                    it.getLong(it.getColumnIndex(SIZE))
                } else -1
            }
        }
    }
}