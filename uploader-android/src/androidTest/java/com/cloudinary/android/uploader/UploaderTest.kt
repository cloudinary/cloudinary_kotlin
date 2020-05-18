package com.cloudinary.android.uploader

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.test.platform.app.InstrumentationRegistry
import com.cloudinary.Cloudinary
import com.cloudinary.upload.response.UploaderResponse
import com.cloudinary.uploader
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test


const val TEST_IMAGE = "old_logo.png"

class UploaderTest {
    private val assetUri: Uri = Uri.fromFile(assetToFile(TEST_IMAGE))
    private val cloudinary = getCloudinary()

    companion object {

        @BeforeClass
        @JvmStatic
        fun setUpClass() {
            val cloudinary = getCloudinary()

            if (cloudinary.config.cloudName.isBlank()) {
                System.err.println("Please setup gradle-local.properties for tests to run")
            }
        }

        @AfterClass
        @JvmStatic
        fun tearDownClass() {
        }
    }

    @Test
    fun testUploadUri() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val response = cloudinary.uploader().upload(assetUri, appContext)
        val data = response.resultOrThrow()
        assert(!data.publicId.isNullOrBlank())
    }

    private fun <T> UploaderResponse<T>.resultOrThrow(): T {
        return data ?: error("Error result: ${error?.error?.message ?: "Unknown error"}")
    }
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
