package com.cloudinary.android.downloader.downloaders.fresco

import com.cloudinary.android.downloader.downloaders.DownloadRequestStrategy

internal class FrescoDownloadRequestStrategy : DownloadRequestStrategy {

    override fun cancel() =
        throw UnsupportedOperationException("Fresco doesn't support cancellation of download requests.")
}