package com.cloudinary.android.downloader

/**
 * A callback for the result of the download request.
 */
interface DownloadRequestCallback {
    /**
     * Called when a request completes successfully.
     */
    fun onSuccess()

    /**
     * Called when a request failed.
     * @param t The error containing the information about why the request failed.
     */
    fun onFailure(t: Throwable?)
}