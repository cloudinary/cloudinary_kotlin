package com.cloudinary.android.preprocess.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import com.cloudinary.android.preprocess.Preprocess
import com.cloudinary.android.preprocess.PreprocessException
import com.cloudinary.android.preprocess.PreprocessChain

/**
 * Preprocess for cropping. Send an instance to [PreprocessChain.addStep] to crop an image.
 * Points must form a diagonal within the bounds of the image.
 * If the points form the same diagonal size as the original image, it will be returned unchanged.
 */
class Crop(private val p1: Point, private val p2: Point) : Preprocess<Bitmap> {

    /**
     * Execute the preprocessing phase. This will crop the image if needed, making sure that the points form a
     * diagonal within the bounds of the bitmap.
     *
     * @param context  Android context
     * @param resource The Bitmap to crop
     * @return The cropped bitmap (or the original bitmap if the points form the same diagonal size).
     */
    @Throws(PreprocessException::class)
    override fun execute(context: Context, resource: Bitmap): Bitmap {
        checkDiagonal()
        val startX: Int
        val startY: Int
        val width: Int
        val height: Int
        if (p1.x < p2.x) {
            startX = p1.x
            width = p2.x - p1.x
        } else {
            startX = p2.x
            width = p1.x - p2.x
        }
        if (p1.y < p2.y) {
            startY = p1.y
            height = p2.y - p1.y
        } else {
            startY = p2.y
            height = p1.y - p2.y
        }
        checkOutOfBounds(startX, startY, width, height, resource)
        return Bitmap.createBitmap(resource, startX, startY, width, height)
    }

    @Throws(PreprocessException::class)
    private fun checkDiagonal() {
        if (p1.x == p2.x || p1.y == p2.y) {
            throw PreprocessException("Points do not make a diagonal")
        }
    }

    @Throws(PreprocessException::class)
    private fun checkOutOfBounds(
        startX: Int,
        startY: Int,
        width: Int,
        height: Int,
        resource: Bitmap
    ) {
        var isOutOfBounds = false
        if (startX < 0 || startX > resource.width) {
            isOutOfBounds = true
        } else if (width > resource.width || startX + width > resource.width) {
            isOutOfBounds = true
        } else if (startY < 0 || startY > resource.height) {
            isOutOfBounds = true
        } else if (height > resource.height || startY + height > resource.height) {
            isOutOfBounds = true
        }
        if (isOutOfBounds) {
            throw PreprocessException("Out of bounds")
        }
    }

}