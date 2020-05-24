package com.cloudinary.android.downloader.downloaders.glide

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ViewTarget
import com.cloudinary.android.downloader.downloaders.DownloadRequestStrategy

internal class GlideDownloadRequestStrategy(private val target: ViewTarget<ImageView, Drawable>) :
    DownloadRequestStrategy {

    override fun cancel() {
        val view = target.view
        Glide.with(view).clear(view)
    }
}