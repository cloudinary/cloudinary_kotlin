package com.cloudinary.android.preprocess.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.cloudinary.android.preprocess.PayloadDecodeException
import com.cloudinary.android.preprocess.PayloadNotFoundException
import com.cloudinary.android.preprocess.ResourceDecoder
import com.cloudinary.http.Payload
import java.io.File
import java.io.IOException
import java.io.InputStream

/**
 * Decodes a bitmap from a payload. If given width and height the decoding process will take them
 * into account to decode the bitmap efficiently but will not necessarily resize the bitmap.
 *
 * @constructor Create a new decoder taking the required width and height into account to decode the bitmap efficiently.
 * @param width  Required width
 * @param height Required height
*/
class BitmapDecoder @JvmOverloads constructor(
    private val width: Int = Int.MAX_VALUE,
    private val height: Int = Int.MAX_VALUE
) : ResourceDecoder<Bitmap> {

    /**
     * Decodes a bitmap from the given payload. If the bitmap is at least two times larger than the required
     * dimensions it will decode a version scaled down by a factor. Note: The dimensions of the decoded bitmap
     * will not necessarily be equal to [width] and [height]. For
     * exact resizing combine this decoder with [Limit] processing step, or use
     * [ImagePreprocessChain.limitDimensionsChain].
     *
     * @param context Android context.
     * @param payload Payload to extract the bitmap from
     * @return The decoded bitmap
     * @throws PayloadNotFoundException if the payload is not found
     * @throws PayloadDecodeException if the payload exists but cannot be decoded
     */
    @Throws(PayloadNotFoundException::class, PayloadDecodeException::class)
    override fun decode(context: Context, payload: Payload<*>): Bitmap {
        return bitmapFromPayload(context, payload, width, height)
    }

    @Throws(PayloadNotFoundException::class, PayloadDecodeException::class)
    private fun bitmapFromPayload(
        context: Context?,
        payload: Payload<*>,
        width: Int,
        height: Int
    ): Bitmap {
        val resource: Any = payload.asInputStream()
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        var bitmap: Bitmap? = null

        when (resource) {
            is File -> {
                BitmapFactory.decodeFile(resource.path, options)
                options.inSampleSize = calculateInSampleSize(options, width, height)
                options.inJustDecodeBounds = false
                bitmap = BitmapFactory.decodeFile(resource.path, options)
            }
            is InputStream -> {
                var inputStream: InputStream? = null
                try {
                    BitmapFactory.decodeStream(resource, null, options)
                    options.inSampleSize = calculateInSampleSize(options, width, height)
                    options.inJustDecodeBounds = false
                    inputStream = payload.asInputStream()
                    bitmap = BitmapFactory.decodeStream(inputStream, null, options)
                } finally {
                    try {
                        resource.close()
                    } catch (ignored: IOException) {
                    }
                    try {
                        inputStream?.close()
                    } catch (ignored: IOException) {
                    }
                }
            }
            is ByteArray -> {
                BitmapFactory.decodeByteArray(resource, 0, resource.size, options)
                options.inSampleSize = calculateInSampleSize(options, width, height)
                options.inJustDecodeBounds = false
                bitmap = BitmapFactory.decodeByteArray(resource, 0, resource.size, options)
            }
        }
        if (bitmap == null) {
            throw PayloadDecodeException()
        }
        return bitmap
    }

    companion object {
        // Google reference method, see https://developer.android.com/topic/performance/graphics/load-bitmap.html
        private fun calculateInSampleSize(
            options: BitmapFactory.Options,
            reqWidth: Int,
            reqHeight: Int
        ): Int {
            // Raw height and width of image
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1
            if (height > reqHeight || width > reqWidth) {
                val halfHeight = height / 2
                val halfWidth = width / 2

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while (halfHeight / inSampleSize >= reqHeight
                    && halfWidth / inSampleSize >= reqWidth
                ) {
                    inSampleSize *= 2
                }
            }
            return inSampleSize
        }
    }

}