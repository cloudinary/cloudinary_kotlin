package com.cloudinary.android.downloader

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import com.cloudinary.transformation.Transformation

/**
 * Builds a download request.
 */
interface DownloadRequestBuilder {
    /**
     * Load the request with a resource id.
     * @return Itself for chaining.
     */
    fun load(@IdRes resource: Int): DownloadRequestBuilder

    /**
     * Load the request with a String source. The source can either be a remote url, or a cloudinary publicId.
     * In the case of a remote url, all cloudinary related builder options will not take place.
     * @return Itself for chaining.
     */
    fun load(source: String): DownloadRequestBuilder

    /**
     * Set a [Transformation] that will be used to generate the url with.
     * Only applies if [.load] was called with a cloudinary publicId.
     * @return Itself for chaining.
     */
    fun transformation(transformation: Transformation): DownloadRequestBuilder

    /**
     * Sets an Android resource id for a [Drawable] resource to display while a resource is
     * loading.
     * @param resourceId The id of the resource to use as a placeholder
     * @return Itself for chaining.
     */
    fun placeholder(@DrawableRes resourceId: Int): DownloadRequestBuilder

    /**
     * Set a callback to be called for the result of the download request.
     * @param callback The callback to be called for the result of the download request.
     * @return Itself for chaining.
     */
    fun callback(callback: DownloadRequestCallback): DownloadRequestBuilder

    /**
     * Set the target [ImageView] to load the resource into and start the operation.
     * @param imageView The [ImageView] the resource will be loaded into.
     * @return The dispatched request.
     */
    fun into(imageView: ImageView): DownloadRequest
}
