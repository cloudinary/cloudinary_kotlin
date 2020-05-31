package com.cloudinary.android.preprocess

import androidx.test.platform.app.InstrumentationRegistry
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

@Throws(IOException::class)
fun assetToFile(testImage: String?): File? {
    val context = InstrumentationRegistry.getInstrumentation().context
    val file = File(context!!.cacheDir.toString() + "/tempFile_" + System.currentTimeMillis())
    file.createNewFile()
    val fos = FileOutputStream(file)
    val inputStream = getAssetStream(testImage)
    val buffer = ByteArray(16276)
    var bytesRead: Int
    while (inputStream!!.read(buffer).also { bytesRead = it } != -1) {
        fos.write(buffer, 0, bytesRead)
    }
    fos.flush()
    inputStream.close()
    return file
}

@Throws(IOException::class)
fun getAssetStream(filename: String?): InputStream? {
    return InstrumentationRegistry.getInstrumentation()
        .context.assets.open(filename!!)
}