package com.cloudinary.android.downloader.downloaders

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import com.cloudinary.Cloudinary
import com.cloudinary.Url
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

    override fun load(source: String): DownloadRequestBuilder {
        isCloudinaryPublicIdSource = !source.isRemoteUrl()
        this.source = source
        return this
    }

    override fun load(@IdRes resource: Int): DownloadRequestBuilder {
        source = resource
        return this
    }

    override fun transformation(transformation: Transformation): DownloadRequestBuilder {
        this.transformation = transformation
        return this
    }

    override fun placeholder(@DrawableRes resourceId: Int): DownloadRequestBuilder {
        placeholder = resourceId
        return this
    }

    override fun callback(callback: DownloadRequestCallback): DownloadRequestBuilder {
        this.callback = callback
        return this
    }

    override fun into(imageView: ImageView): DownloadRequest {
        checkNotNull(source) { "Source is null." }
        val downloadRequestImpl = DownloadRequestImpl(
            downloadRequestBuilderStrategy,
            imageView
        )

        when (source) {
            is String -> {
                if (isCloudinaryPublicIdSource) {
                    val url: Url = cloudinary.url {
                        publicId(source as String)
                        transformation?.let { transformation(it) }
                    }

                    downloadRequestImpl.setSource(url.generate())
                } else {
                    downloadRequestImpl.setSource(source)
                }
            }
            is Int -> downloadRequestImpl.setSource(source)
            else -> throw IllegalArgumentException("Load source is not an instance of a correct type.")
        }

        if (placeholder != 0) {
            downloadRequestBuilderStrategy.placeholder(placeholder)
        }

        callback?.let { downloadRequestBuilderStrategy.callback(it) }

        return downloadRequestImpl.start()
    }
}