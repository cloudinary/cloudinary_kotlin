package com.cloudinary.android.downloader.downloaders

import android.widget.ImageView
import com.cloudinary.android.downloader.DownloadRequest
import java.lang.ref.WeakReference

/**
 * A wrapper for the [DownloadRequestStrategy] which basically doesn't start when a request does not have a url prior starting.
 * Once the url is set, the download will start if [.start] was called beforehand.
 *
 *
 * Note: This class is intended for a one time use. Therefore, subsequent calls to [.setSource] and [.start]
 * will not alter the request's state nor it will not be restarted.
 */
internal class DownloadRequestImpl internal constructor(
    private val downloadRequestBuilderStrategy: DownloadRequestBuilderStrategy,
    imageView: ImageView
) : DownloadRequest {
    private var downloadRequestStrategy: DownloadRequestStrategy? = null
    private val imageViewRef: WeakReference<ImageView> = WeakReference(imageView)
    private var source: Any? = null
    private var shouldStart = false
    private var isCancelled = false

    @Synchronized
    fun setSource(source: Any?) {
        if (this.source == null) {
            when (source) {
                is String -> downloadRequestBuilderStrategy.load(source)
                is Int -> downloadRequestBuilderStrategy.load(source)
            }

            this.source = source
            if (shouldStart && !isCancelled) {
                doStart()
            }
        }
    }

    @Synchronized
    fun start(): DownloadRequestImpl {
        if (!isCancelled && downloadRequestStrategy == null) {
            shouldStart = true
            source?.let { doStart() }
        }
        return this
    }

    private fun doStart() {
        imageViewRef.get()?.let {
            downloadRequestStrategy = downloadRequestBuilderStrategy.into(it)
        }
    }

    @Synchronized
    override fun cancel() {
        downloadRequestStrategy?.cancel()
        isCancelled = true
    }
}