package com.cloudinary.android.downloader.downloaders.picasso

import android.content.Context
import android.widget.ImageView
import com.cloudinary.android.downloader.DownloadRequestCallback
import com.cloudinary.android.downloader.downloaders.DownloadRequestBuilderStrategy
import com.cloudinary.android.downloader.downloaders.DownloadRequestStrategy
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

internal class PicassoDownloadRequestBuilderStrategy internal constructor(context: Context)
    : DownloadRequestBuilderStrategy {
    private val picasso: Picasso = Picasso.Builder(context).build()
    private var requestCreator: RequestCreator? = null
    private var callback: DownloadRequestCallback? = null

    override fun load(url: String): DownloadRequestBuilderStrategy = apply {
        requestCreator = picasso.load(url)
    }

    override fun load(resourceId: Int): DownloadRequestBuilderStrategy = apply {
        requestCreator = picasso.load(resourceId)
    }

    override fun placeholder(resourceId: Int): DownloadRequestBuilderStrategy = apply {
        requestCreator?.placeholder(resourceId) ?: throw IllegalStateException("Must call load before.")
    }

    override fun callback(callback: DownloadRequestCallback): DownloadRequestBuilderStrategy = apply {
        this.callback = callback
    }

    override fun into(imageView: ImageView): DownloadRequestStrategy {
        callback?.let {
            requestCreator?.into(imageView, object : Callback {
                override fun onSuccess() {
                    it.onSuccess()
                }

                override fun onError(e: Exception) {
                    it.onFailure(e)
                }
            })
        } ?: requestCreator?.into(imageView)

        return PicassoDownloadRequestStrategy(picasso, imageView)
    }
}