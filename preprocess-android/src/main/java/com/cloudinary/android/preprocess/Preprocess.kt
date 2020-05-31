package com.cloudinary.android.preprocess

import android.content.Context

/**
 * Preprocess to run on a resource before uploading to Cloudinary. Pass an implementation of this interface to [PreprocessChain.addStep]
 * to run preprocessing and validation steps on a resource.
 *
 * @param <T> The class representing the resource - This is the type on which the preprocessing will run (e.g. [android.graphics.Bitmap])
*/
interface Preprocess<T> {
    /**
     * Execute the given preprocess on the resource
     *
     * @param context  Android context
     * @param resource the resource as prepared by a [ResourceDecoder]
     * @return A resource of type T after processing.
     * @throws PreprocessException if something fails during preprocess, or in case of a [ValidationException]
     */
    @Throws(PreprocessException::class)
    fun execute(context: Context, resource: T): T
}