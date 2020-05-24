package com.cloudinary.android.downloader

import android.content.Context
import android.content.pm.PackageManager
import com.cloudinary.Cloudinary

private var downloadRequestBuilderFactory: DownloadRequestBuilderFactory? = null

fun Cloudinary.setDownloadRequestBuilderFactory(factory: DownloadRequestBuilderFactory) {
    downloadRequestBuilderFactory = factory
}

fun Cloudinary.download(context: Context): DownloadRequestBuilder {
    return downloadRequestBuilderFactory?.createDownloadRequestBuilder(context, this) ?:
            throw IllegalStateException("Must set a factory before downloading.")
}

fun cloudinaryUrlFromContext(context: Context): String {
    var url = ""
    try {
        val packageManager = context.packageManager
        val info = packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
        if (info.metaData != null) {
            url = info.metaData["CLOUDINARY_URL"] as String
        }
    } catch (e: PackageManager.NameNotFoundException) {
        // No metadata found
    }
    return url
}