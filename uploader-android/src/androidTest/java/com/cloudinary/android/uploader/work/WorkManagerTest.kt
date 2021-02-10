package com.cloudinary.android.uploader.work

import android.net.Uri
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.WorkManager
import com.cloudinary.Cloudinary
import com.cloudinary.android.uploader.*
import com.cloudinary.android.uploader.request.*
import com.cloudinary.config.DEFAULT_CHUNK_SIZE
import com.cloudinary.transformation.Transformation
import com.cloudinary.transformation.effect.Effect
import com.cloudinary.upload.EagerTransformation
import com.cloudinary.upload.request.params.UploadParams
import com.cloudinary.upload.response.UploadResult
import com.cloudinary.uploader
import junit.framework.Assert.assertNotNull
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName


class WorkManagerTest {

    val assetFile = assetToFile(TEST_IMAGE)
    val largeAssetFile = assetToFile(TEST_IMAGE_LARGE)

    private val assetUri: Uri = Uri.fromFile(assetFile)
    private val largeAssetUri: Uri = Uri.fromFile(largeAssetFile)

    private val assetFileSize = assetFile.length()
    private val largeAssetFileSize = largeAssetFile.length()

    private val cloudinary = getCloudinary()

    @Rule
    @JvmField
    val testName = TestName()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    companion object {

        @BeforeClass
        @JvmStatic
        fun setUpClass() {
            val appContext = InstrumentationRegistry.getInstrumentation().targetContext
            WorkManager.getInstance(appContext).cancelAllWork()
            val cloudinary = getCloudinary()

            if (cloudinary.config.cloudName.isNullOrBlank()) {
                System.err.println("Please setup gradle-local.properties for tests to run")
            }

            Cloudinary.init(cloudinary)
        }

        @AfterClass
        @JvmStatic
        fun tearDownClass() {
            val appContext = InstrumentationRegistry.getInstrumentation().targetContext
            WorkManager.getInstance(appContext).cancelAllWork()
        }
    }

    @Test
    fun testUpload() = runBlocking<Unit> {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val result = cloudinary.uploader().uploadAsync(largeAssetUri, appContext) {
            options {
                chunkSize = 5 * 1024 * 1024
            }
        }

        var uploadResult: UploadResult? = null

        result.getResult(appContext).observeForever {
            uploadResult = it?.data
        }

        result.progress(appContext).observeForever {
            Log.i("WorkManagerTest", "uploaded ${it.bytes}/${it.totalBytes}")
        }

        val took = waitUntil(60) {
            uploadResult != null
        }

        assertNotNull(uploadResult)
        Log.i("WorkManagerTest", "${testName.methodName} took $took millis.")
    }

    @Test
    fun testDataSerialization() {
        val request = buildUploadRequest()
        val workManagerRequest = request.toUploadWorkRequest("tag", 7, 1000, DEFAULT_CHUNK_SIZE)
        assertNotNull(workManagerRequest)
        val workSpec = workManagerRequest.workSpec
        val data = workSpec.input.keyValueMap
        val constraints = workSpec.constraints
        assertEquals("abc", data["dispatcher_param:auto_tagging"])
        assertEquals("e_sepia:12", data["dispatcher_param:eager"])
        assertEquals(7, data["index"])
        assertEquals(1000L, data["total_length"])
        assertEquals(11, data["maxErrorRetries"])
        assertEquals(androidx.work.NetworkType.NOT_ROAMING, constraints.requiredNetworkType)
        assertEquals(true, constraints.requiresCharging())
        assertEquals(true, constraints.requiresBatteryNotLow())
        assertEquals(false, constraints.requiresDeviceIdle())
        assertEquals(androidx.work.BackoffPolicy.LINEAR, workSpec.backoffPolicy)
        assertEquals(120000, workSpec.initialDelay)
        assertEquals(240000, workSpec.backoffDelayDuration)
    }

    private fun buildUploadRequest(): UploadRequest {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val paramsBuilder = UploadParams.Builder()
        paramsBuilder.autoTagging = "abc"
        paramsBuilder.eager = listOf(
            EagerTransformation(Transformation().effect(Effect.sepia()))
        )

        val backgroundUploadConfig = AsyncUploadConfig.Builder()
            .backoffMillis(240000)
            .initialDelay(120000)
            .maxErrorRetries(11)
            .networkType(NetworkType.NOT_ROAMING)
            .requiresBatteryNotLow(true)
            .requiresCharging(true)
            .backoffPolicy(BackoffPolicy.LINEAR)
            .build()

        val requestBuilder =
            UploadRequest.Builder(LocalUriPayload(appContext, assetUri), cloudinary.uploader())
        requestBuilder.params = paramsBuilder.build()
        requestBuilder.options {
            this.filename = "namefile"
            this.headers = mapOf("custom_header" to "custom_value")
        }
        requestBuilder.uploadPolicy(backgroundUploadConfig)

        return requestBuilder.build()
    }
}