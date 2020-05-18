package com.cloudinary.android.uploader

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
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

