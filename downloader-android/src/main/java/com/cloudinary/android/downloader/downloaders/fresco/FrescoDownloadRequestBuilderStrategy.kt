package com.cloudinary.android.downloader.downloaders.fresco

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.cloudinary.android.downloader.DownloadRequestCallback
import com.cloudinary.android.downloader.downloaders.DownloadRequestBuilderStrategy
import com.cloudinary.android.downloader.downloaders.DownloadRequestStrategy
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.drawee.view.DraweeView
import com.facebook.imagepipeline.listener.BaseRequestListener
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder

internal class FrescoDownloadRequestBuilderStrategy(context: Context)
    : DownloadRequestBuilderStrategy {
    private val genericDraweeHierarchyBuilder: GenericDraweeHierarchyBuilder =
        GenericDraweeHierarchyBuilder.newInstance(context.resources)
    private var imageRequestBuilder: ImageRequestBuilder? = null

    override fun load(url: String): DownloadRequestBuilderStrategy = apply {
        imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
    }

    override fun load(resourceId: Int): DownloadRequestBuilderStrategy = apply {
        imageRequestBuilder = ImageRequestBuilder.newBuilderWithResourceId(resourceId)
    }

    override fun placeholder(resourceId: Int): DownloadRequestBuilderStrategy = apply {
        checkNotNull(imageRequestBuilder) { "Must call load before." }
        genericDraweeHierarchyBuilder.setPlaceholderImage(resourceId)
    }

    override fun callback(callback: DownloadRequestCallback): DownloadRequestBuilderStrategy = apply {
        checkNotNull(imageRequestBuilder) { "Must call load before." }
        imageRequestBuilder?.requestListener = object : BaseRequestListener() {

            override fun onRequestSuccess(request: ImageRequest, requestId: String, isPrefetch: Boolean) {
                callback.onSuccess()
                super.onRequestSuccess(request, requestId, isPrefetch)
            }

            override fun onRequestFailure(request: ImageRequest, requestId: String, throwable: Throwable, isPrefetch: Boolean) {
                callback.onFailure(throwable)
                super.onRequestFailure(request, requestId, throwable, isPrefetch)
            }
        }
    }

    override fun into(imageView: ImageView): DownloadRequestStrategy {
        checkNotNull(imageRequestBuilder) { "Must call load before." }
        require(imageView is DraweeView<*>) { "ImageView must be an instance of DraweeView." }

        val imageRequest = imageRequestBuilder?.build()
        val genericDraweeHierarchy = genericDraweeHierarchyBuilder.build()
        val draweeController: DraweeController = Fresco.newDraweeControllerBuilder()
            .setImageRequest(imageRequest)
            .build()
        draweeController.hierarchy = genericDraweeHierarchy
        imageView.controller = draweeController

        return FrescoDownloadRequestStrategy()
    }

}