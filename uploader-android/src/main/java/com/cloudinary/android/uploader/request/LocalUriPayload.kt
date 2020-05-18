package com.cloudinary.android.uploader.request

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns.SIZE
import com.cloudinary.http.Payload
import java.io.InputStream

internal class LocalUriPayload(private val context: Context, override val value: Uri) : Payload<Uri> {
    override fun asInputStream(): InputStream = context.contentResolver.openInputStream(value)
        ?: throw IllegalArgumentException("Provided Uri cannot be read: $value")

    override val name: String = "file"
    override val length: Long by lazy {
        // this is called in the uploading thread, guaranteeing this will run in a background thread
        // if the upload itself is in a background thread.
        value.fetchFileSize(context)
    }
}

private fun Uri.fetchFileSize(context: Context): Long {
    return context.contentResolver.query(
        this,
        arrayOf(SIZE),
        null,
        null,
        null
    ).use { cursor ->
        if (cursor?.moveToFirst() == true) {
            with(cursor) { getLong(getColumnIndex(SIZE)) }
        } else -1
    }
}