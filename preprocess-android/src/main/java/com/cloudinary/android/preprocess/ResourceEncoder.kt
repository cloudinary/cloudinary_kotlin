package com.cloudinary.android.preprocess

import android.content.Context

/**
 * Implement this interface to pass to [PreprocessChain.saveWith] for custom resource encoding behavior.
 *
 * @param <T> The concrete type this encoder needs to encode
*/
interface ResourceEncoder<T> {
    /**
     * Finalizes the preprocess - This method saves the new resource T to a file.
     *
     * @param context  Android context
     * @param resource The resource (after processing) to save to file.
     * @return The filepath of the newly created file.
     * @throws ResourceCreationException if the processed resource cannot be saved
     */
    @Throws(ResourceCreationException::class)
    fun encode(context: Context, resource: T): String?
}