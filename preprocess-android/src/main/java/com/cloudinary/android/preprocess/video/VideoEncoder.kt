package com.cloudinary.android.preprocess.video

import android.content.Context
import android.net.Uri
import com.cloudinary.android.preprocess.ResourceEncoder

/**
 * Returns the encoded video target file. Note: It doesn't do the actual encoding process.
 */
class VideoEncoder : ResourceEncoder<Uri> {
    override fun encode(context: Context, resource: Uri): String {
        return resource.toString()
    }
}