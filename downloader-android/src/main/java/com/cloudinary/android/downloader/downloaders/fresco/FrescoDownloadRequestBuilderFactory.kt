package com.cloudinary.android.downloader.downloaders.fresco

import android.content.Context
import com.cloudinary.Cloudinary
import com.cloudinary.android.downloader.DownloadRequestBuilderFactory
import com.cloudinary.android.downloader.downloaders.DownloadRequestBuilderImpl

class FrescoDownloadRequestBuilderFactory : DownloadRequestBuilderFactory {

    override fun createDownloadRequestBuilder(context: Context, cloudinary: Cloudinary) =
        DownloadRequestBuilderImpl(
            context,
            cloudinary,
            FrescoDownloadRequestBuilderStrategy(context)
        )
}