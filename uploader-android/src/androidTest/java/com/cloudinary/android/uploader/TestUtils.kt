package com.cloudinary.android.uploader

import android.content.Context
import android.content.pm.PackageManager
import androidx.test.platform.app.InstrumentationRegistry
import com.cloudinary.Cloudinary
import kotlinx.coroutines.delay
import java.io.File
import java.io.FileOutputStream

fun assetToFile(assetPath: String): File {
    val context: Context = InstrumentationRegistry.getInstrumentation().context
    val file = File(context.cacheDir.toString() + "/tempFile_" + System.currentTimeMillis())
    file.createNewFile()
    FileOutputStream(file).use {
        InstrumentationRegistry.getInstrumentation().context.assets.open(assetPath).copyTo(it)
    }

    return file
}

fun cloudinaryUrlFromContext(context: Context): String? {
    var url: String? = ""
    try {
        val packageManager: PackageManager = context.packageManager
        val info =
            packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
        if (info.metaData != null) {
            url = info.metaData["CLOUDINARY_URL"] as String?
        }
    } catch (e: PackageManager.NameNotFoundException) {
        // No metadata found
    }
    return url
}

fun getCloudinary(): Cloudinary {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val cloudinaryUrl = cloudinaryUrlFromContext(context)!!
    return Cloudinary(cloudinaryUrl)
}

suspend fun waitUntil(timeoutSeconds: Long = 10, operation: () -> Boolean): Long {
    val start = System.currentTimeMillis()
    while (!operation() && (System.currentTimeMillis() - start) < timeoutSeconds * 1000) {
        delay(250)
    }

    return System.currentTimeMillis() - start
}