package com.cloudinary.android.preprocess.video

import android.content.Context
import android.net.Uri
import com.cloudinary.android.preprocess.PayloadDecodeException
import com.cloudinary.android.preprocess.PayloadNotFoundException
import com.cloudinary.android.preprocess.ResourceDecoder
import com.cloudinary.android.uploader.request.LocalUriPayload
import com.cloudinary.http.Payload
import com.cloudinary.upload.request.FilePayload

/**
 * Returns the decoded video uri from a given payload. Payloads must be either [LocalUriPayload] or [FilePayload].
 * Note: It doesn't do the actual decoding process.
 */
class VideoDecoder : ResourceDecoder<Uri> {
    /**
     * Returns the video uri.
     *
     * @param context Android context.
     * @param payload Payload to extract the resource from
     * @throws PayloadDecodeException if the payload is neither a [LocalUriPayload] nor [FilePayload]
     * @throws PayloadNotFoundException if the payload's resource cannot be found.
     */
    @Throws(PayloadDecodeException::class, PayloadNotFoundException::class)
    override fun decode(context: Context, payload: Payload<*>): Uri {
        return when (payload) {
            is LocalUriPayload -> { payload.value }
            is FilePayload -> { Uri.fromFile(payload.value) }
            else -> { throw PayloadDecodeException() }
        }
    }
}