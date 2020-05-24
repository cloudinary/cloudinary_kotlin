package com.cloudinary.android.downloader.downloaders.glide

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.cloudinary.android.downloader.DownloadRequestCallback
import com.cloudinary.android.downloader.downloaders.DownloadRequestBuilderStrategy
import com.cloudinary.android.downloader.downloaders.DownloadRequestStrategy

@SuppressLint("CheckResult")
internal class GlideDownloadRequestBuilderStrategy(context: Context)
    : DownloadRequestBuilderStrategy {
    private val requestBuilder = Glide.with(context).asDrawable()

    override fun load(url: String): DownloadRequestBuilderStrategy {
        requestBuilder.load(url)
        return this
    }

    override fun load(resourceId: Int): DownloadRequestBuilderStrategy {
        requestBuilder.load(resourceId)
        return this
    }

    override fun placeholder(resourceId: Int): DownloadRequestBuilderStrategy {
        requestBuilder.placeholder(resourceId)
        return this
    }

    override fun callback(callback: DownloadRequestCallback): DownloadRequestBuilderStrategy {
        requestBuilder.listener(object : RequestListener<Drawable?> {

            override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable?>,
                                      isFirstResource: Boolean): Boolean {
                callback.onFailure(e)
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any, target: Target<Drawable?>,
                                         dataSource: DataSource, isFirstResource: Boolean): Boolean {
                callback.onSuccess()
                return false
            }
        })
        return this
    }

    override fun into(imageView: ImageView): DownloadRequestStrategy {
        val target = requestBuilder.into(imageView)

        return GlideDownloadRequestStrategy(target)
    }
}