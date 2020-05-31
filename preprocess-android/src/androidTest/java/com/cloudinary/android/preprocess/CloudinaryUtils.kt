package com.cloudinary.android.preprocess

import android.content.Context
import android.content.pm.PackageManager
import androidx.test.platform.app.InstrumentationRegistry
import com.cloudinary.Cloudinary

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

fun getCloudinary(): Cloudinary {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val cloudinaryUrl = cloudinaryUrlFromContext(context)
    return Cloudinary(cloudinaryUrl)
}