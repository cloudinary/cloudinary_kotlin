package com.cloudinary.android.downloader.downloaders.glide

import android.content.Context
import com.cloudinary.Cloudinary
import com.cloudinary.android.downloader.DownloadRequestBuilderFactory
import com.cloudinary.android.downloader.downloaders.DownloadRequestBuilderImpl

class GlideDownloadRequestBuilderFactory :
    DownloadRequestBuilderFactory {

    override fun createDownloadRequestBuilder(context: Context, cloudinary: Cloudinary): DownloadRequestBuilderImpl {
        return DownloadRequestBuilderImpl(
            context,
            cloudinary,
            GlideDownloadRequestBuilderStrategy(context)
        )
    }
}