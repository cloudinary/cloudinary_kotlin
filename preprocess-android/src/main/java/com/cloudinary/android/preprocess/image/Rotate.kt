package com.cloudinary.android.preprocess.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import com.cloudinary.android.preprocess.Preprocess
import com.cloudinary.android.preprocess.PreprocessChain

/**
 * Preprocess for rotating. Send an instance to [PreprocessChain.addStep] to rotate an image.
 */
class Rotate(private val rotationAngle: Int) : Preprocess<Bitmap> {

    /**
     * Execute the preprocessing phase. This will rotate the image by the rotation angle property.
     * If the rotation angle is a multiple of 360, then the original resource bitmap will be returned.
     *
     * @param context  Android context.
     * @param resource The bitmap to rotate.
     * @return The rotated bitmap.
     */
    override fun execute(context: Context, resource: Bitmap): Bitmap {
        val matrix = Matrix()
        matrix.setRotate(rotationAngle.toFloat())
        return Bitmap.createBitmap(resource, 0, 0, resource.width, resource.height, matrix, false)
    }

}