package com.cloudinary.android.preprocess.image

import android.content.Context
import android.graphics.Bitmap
import com.cloudinary.android.preprocess.Preprocess
import com.cloudinary.android.preprocess.ValidationException

/**
 * A preprocess step for validating the dimensions of a given bitmap.
 *
 * @constructor Create an instance with the chosen parameters. Anything outside the specified bounds will fail
 * validation.
 * @param minWidth  Minimum allowed width
 * @param minHeight Minimum allowed height
 * @param maxWidth  Maximum allowed width
 * @param maxHeight Maximum allowed height
 */
class DimensionsValidator(
    private val minWidth: Int,
    private val minHeight: Int,
    private val maxWidth: Int,
    private val maxHeight: Int
) : Preprocess<Bitmap> {

    @Throws(ValidationException::class)
    override fun execute(context: Context, resource: Bitmap): Bitmap {
        if (resource.width > maxWidth || resource.width < minWidth ||
            resource.height > maxHeight || resource.height < minHeight
        ) throw ValidationException("Resource dimensions are invalid")
        return resource
    }

}