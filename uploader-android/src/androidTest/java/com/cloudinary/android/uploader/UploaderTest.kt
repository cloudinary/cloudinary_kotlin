package com.cloudinary.android.uploader

import android.net.Uri
import androidx.test.platform.app.InstrumentationRegistry
import com.cloudinary.transformation.effect.Effect.Companion.sepia
import com.cloudinary.upload.response.UploaderResponse
import com.cloudinary.uploader
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test


const val TEST_IMAGE = "old_logo.png"
const val TEST_IMAGE_LARGE = "large.png"

class UploaderTest {
    private val assetUri: Uri = Uri.fromFile(assetToFile(TEST_IMAGE))
    private val cloudinary = getCloudinary()

    companion object {

        @BeforeClass
        @JvmStatic
        fun setUpClass() {
            val cloudinary = getCloudinary()

            if (cloudinary.config.cloudName.isNullOrBlank()) {
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
        val response = cloudinary.uploader().upload(assetUri, appContext) {
            params {
                transformation {
                    effect(sepia())
                }
            }
        }
        val data = response.resultOrThrow()
        assert(!data.publicId.isNullOrBlank())
    }

    private fun <T> UploaderResponse<T>.resultOrThrow(): T {
        return data ?: error("Error result: ${error?.error?.message ?: "Unknown error"}")
    }
}

