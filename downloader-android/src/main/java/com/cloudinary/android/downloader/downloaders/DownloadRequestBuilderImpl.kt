package com.cloudinary.android.downloader.downloaders

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import com.cloudinary.Cloudinary
import com.cloudinary.android.downloader.DownloadRequest
import com.cloudinary.android.downloader.DownloadRequestBuilder
import com.cloudinary.android.downloader.DownloadRequestCallback
import com.cloudinary.android.downloader.isRemoteUrl
import com.cloudinary.transformation.Transformation

/**
 * A [DownloadRequestBuilder] implementation that holds all the logic of turning
 * the cloudinary params into an actual url, passing it along to a [DownloadRequestBuilderStrategy].
 */
class DownloadRequestBuilderImpl(
    private val context: Context,
    private val cloudinary: Cloudinary,
    private val downloadRequestBuilderStrategy: DownloadRequestBuilderStrategy
) : DownloadRequestBuilder {
    private var source: Any? = null
    private var transformation: Transformation? = null
    private var placeholder = 0
    private var isCloudinaryPublicIdSource = false
    private var callback: DownloadRequestCallback? = null

    override fun load(source: String): DownloadRequestBuilder = apply {
        isCloudinaryPublicIdSource = !source.isRemoteUrl()
        this.source = source
    }

    override fun load(@IdRes resource: Int): DownloadRequestBuilder = apply {
        source = resource
    }

    override fun transformation(transformation: Transformation): DownloadRequestBuilder = apply {
        this.transformation = transformation
    }

    override fun placeholder(@DrawableRes resourceId: Int): DownloadRequestBuilder = apply {
        placeholder = resourceId
    }

    override fun callback(callback: DownloadRequestCallback): DownloadRequestBuilder = apply {
        this.callback = callback
    }

    override fun into(imageView: ImageView): DownloadRequest {
        checkNotNull(source) { "Source is null." }
        val downloadRequestImpl = DownloadRequestImpl(downloadRequestBuilderStrategy, imageView)

        source = when (source) {
            is String -> {
                if (isCloudinaryPublicIdSource) {
                    cloudinary.url {
                        publicId(source as String)
                        transformation?.let { transformation(it) }
                    }.generate()
                } else {
                    source
                }
            }
            is Int -> source
            else -> throw IllegalArgumentException("Load source is not an instance of a correct type.")
        }
        downloadRequestImpl.setSource(source)

        if (placeholder != 0) {
            downloadRequestBuilderStrategy.placeholder(placeholder)
        }

        callback?.let { downloadRequestBuilderStrategy.callback(it) }

        return downloadRequestImpl.start()
    }
}