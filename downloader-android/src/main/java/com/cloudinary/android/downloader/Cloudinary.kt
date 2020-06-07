package com.cloudinary.android.downloader

import android.content.Context
import com.cloudinary.Cloudinary
import com.cloudinary.android.downloader.downloaders.fresco.FrescoDownloadRequestBuilderFactory
import com.cloudinary.android.downloader.downloaders.glide.GlideDownloadRequestBuilderFactory
import com.cloudinary.android.downloader.downloaders.picasso.PicassoDownloadRequestBuilderFactory

private const val DOWNLOAD_REQUEST_BUILDER_FACTORY_EXTENSION_NAME =
    "cld.downloader.DownloadRequestBuilderFactory"

var Cloudinary.downloadRequestBuilderFactory: DownloadRequestBuilderFactory
    get() = getExtension(
        DOWNLOAD_REQUEST_BUILDER_FACTORY_EXTENSION_NAME,
        instantiateDownloadRequestBuilderFactory()
    ) as DownloadRequestBuilderFactory
    set(value) = setExtension(DOWNLOAD_REQUEST_BUILDER_FACTORY_EXTENSION_NAME, value)

fun Cloudinary.download(context: Context): DownloadRequestBuilder {
    return downloadRequestBuilderFactory.createDownloadRequestBuilder(context, this)
}

private fun instantiateDownloadRequestBuilderFactory() = run {
    when {
        exists("com.facebook.drawee.backends.pipeline.Fresco") -> FrescoDownloadRequestBuilderFactory()
        exists("com.bumptech.glide.Glide") -> GlideDownloadRequestBuilderFactory()
        exists("com.squareup.picasso.Picasso") -> PicassoDownloadRequestBuilderFactory()
        else -> throw IllegalStateException("Must set a factory before downloading.")
    }
}

private fun exists(factoryCanonicalName: String): Boolean {
    return try {
        Class.forName(factoryCanonicalName)
        true
    } catch (ignored: Exception) {
        false
    }
}