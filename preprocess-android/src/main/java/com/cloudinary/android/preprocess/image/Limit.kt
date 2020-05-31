package com.cloudinary.android.preprocess.image

import android.content.Context
import android.graphics.Bitmap
import com.cloudinary.android.preprocess.Preprocess
import com.cloudinary.android.preprocess.PreprocessChain
import kotlin.math.roundToInt

/**
 * Preprocess implementation for resizing. Send an instance to [PreprocessChain]
 * to scale down any image larger then [height]/[width]. The scaling retains aspect ratio while
 * making sure the height and width are within the requested maximum bounds. If the original image is smaller
 * than [height] and [width], it will be returned unchanged.
 *
 * @constructor Create a new Resize preprocess
 * @param width  Maximum allowed width for the image. Will scale down to comply.
 * @param height Maximum allowed height for the image. Will scale down to comply.
 */
class Limit(private val width: Int, private val height: Int) : Preprocess<Bitmap> {

    /**
     * Execute the preprocessing phase. This will check the dimensions and scale down the image if needed, making sure
     * both height and width are within maximum bounds.
     *
     * @param context  Android context
     * @param resource The Bitmap to resize
     * @return The scaled down bitmap (or the original bitmap if it's within bounds).
     */
    override fun execute(context: Context, resource: Bitmap): Bitmap {
        if (resource.width > width || resource.height > height) {
            val widthRatio = width.toDouble() / resource.width
            val heightRatio = height.toDouble() / resource.height
            return if (heightRatio > widthRatio) {
                Bitmap.createScaledBitmap(resource, width, (widthRatio * resource.height).roundToInt(), true)
            } else {
                Bitmap.createScaledBitmap(resource, (heightRatio * resource.width).roundToInt(), height, true)
            }
        }
        return resource
    }

}