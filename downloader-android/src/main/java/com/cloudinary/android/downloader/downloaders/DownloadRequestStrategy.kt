package com.cloudinary.android.downloader.downloaders

/**
 * Strategy interface to be implemented by libraries to represent the in-progress
 * download request.
 */
interface DownloadRequestStrategy {
    /**
     * Cancel the download request.
     */
    fun cancel()
}