package com.cloudinary.android.downloader.downloaders.picasso

import android.widget.ImageView
import com.cloudinary.android.downloader.downloaders.DownloadRequestStrategy
import com.squareup.picasso.Picasso

internal class PicassoDownloadRequestStrategy(
    private val picasso: Picasso,
    private val imageView: ImageView
) : DownloadRequestStrategy {

    override fun cancel() = picasso.cancelRequest(imageView)
}