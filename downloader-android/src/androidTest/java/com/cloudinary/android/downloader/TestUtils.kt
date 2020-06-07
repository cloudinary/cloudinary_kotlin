package com.cloudinary.android.downloader

import android.content.Context
import android.content.pm.PackageManager
import com.cloudinary.Cloudinary

internal fun Cloudinary.Companion.cloudinaryUrlFromContext(context: Context): String {
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