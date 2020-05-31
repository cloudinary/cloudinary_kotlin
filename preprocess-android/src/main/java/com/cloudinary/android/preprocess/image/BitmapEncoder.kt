package com.cloudinary.android.preprocess.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import com.cloudinary.android.preprocess.ResourceCreationException
import com.cloudinary.android.preprocess.ResourceEncoder
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

/**
 * Encodes the bitmap to a file. Allows configuration of quality and format.
 *
 * @constructor Create a new bitmap encoder with the given specifications.
 * @param format  The format to encode the bitmap
 * @param quality The quality to use when encoding the bitmap
 */
class BitmapEncoder @JvmOverloads constructor(
    private val format: Format = DEFAULT_FORMAT,
    private val quality: Int = DEFAULT_QUALITY
) : ResourceEncoder<Bitmap> {

    /**
     * Encodes the given bitmap to a file using the supplied format and quality settings.
     * If no configuration is supplied, default settings will be used.
     *
     * @param context  Android context
     * @param resource The resource (after processing) to save to file.
     * @return
     * @throws ResourceCreationException if the resource cannot be saved to a file
     */
    @Throws(ResourceCreationException::class)
    override fun encode(context: Context, resource: Bitmap): String? {
        return saveFile(context, resource, quality, format)
    }

    @Throws(ResourceCreationException::class)
    private fun saveFile(
        context: Context,
        resource: Bitmap,
        quality: Int,
        format: Format
    ): String? {
        var fos: FileOutputStream? = null
        val fileName = UUID.randomUUID().toString()
        var file: String? = null
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            resource.compress(adaptFormat(format), quality, fos)
            resource.recycle()
            file = fileName
        } catch (e: FileNotFoundException) {
            throw ResourceCreationException("Could not create new file")
        } finally {
            if (fos != null) {
                try {
                    fos.close()
                    file?.let {
                        if (it.isBlank()) {
                            // failed, delete the file just in case it's there:
                            context.deleteFile(fileName)
                        }
                    }
                } catch (ignored: IOException) {
                }
            }
        }
        return file
    }

    private fun adaptFormat(format: Format): CompressFormat {
        return when (format) {
            Format.WEBP -> CompressFormat.WEBP
            Format.JPEG -> CompressFormat.JPEG
            Format.PNG -> CompressFormat.PNG
            else -> CompressFormat.PNG
        }
    }

    enum class Format {
        WEBP, JPEG, PNG
    }

    companion object {
        private val DEFAULT_FORMAT = Format.WEBP
        private const val DEFAULT_QUALITY = 100
    }

}