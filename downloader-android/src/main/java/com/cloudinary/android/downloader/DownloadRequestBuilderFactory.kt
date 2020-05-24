package com.cloudinary.android.downloader

import android.content.Context
import com.cloudinary.Cloudinary

/**
 * Constructs a [DownloadRequestBuilder] instance to be used for creating download requests.
 */
interface DownloadRequestBuilderFactory {

    /**
     * Create a [DownloadRequestBuilder] that will create the download requests.
     * @param context Android context.
     * @return The created [DownloadRequestBuilder]
     */
    fun createDownloadRequestBuilder(context: Context, cloudinary: Cloudinary) : DownloadRequestBuilder
}