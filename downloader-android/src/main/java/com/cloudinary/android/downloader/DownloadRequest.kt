package com.cloudinary.android.downloader

/**
 * Represents an active download request (in progress).
 */
interface DownloadRequest {
    /**
     * Cancel the download request.
     */
    fun cancel()
}