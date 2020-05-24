package com.cloudinary.android.downloader.downloaders

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.cloudinary.android.downloader.DownloadRequestCallback

/**
 * Strategy interface to be implemented by libraries to build a download request.
 */
interface DownloadRequestBuilderStrategy {
    /**
     * Load the request with a url.
     * @return Itself for chaining.
     */
    fun load(url: String): DownloadRequestBuilderStrategy

    /**
     * Load the request with a resource id.
     * @return Itself for chaining.
     */
    fun load(resourceId: Int): DownloadRequestBuilderStrategy

    /**
     * Set an Android resource id for a [Drawable] resource to display while a resource is
     * loading.
     * @param resourceId The id of the resource to use as a placeholder
     * @return Itself for chaining.
     */
    fun placeholder(@DrawableRes resourceId: Int): DownloadRequestBuilderStrategy

    /**
     * Set a callback to be called for the result of the download request.
     * @param callback The callback to be called for the result of the download request.
     * @return Itself for chaining.
     */
    fun callback(callback: DownloadRequestCallback): DownloadRequestBuilderStrategy

    /**
     * Set the target [ImageView] to load the resource into and start the operation.
     * @param imageView The [ImageView] the resource will be loaded into.
     * @return The dispatched request.
     */
    fun into(imageView: ImageView): DownloadRequestStrategy
}