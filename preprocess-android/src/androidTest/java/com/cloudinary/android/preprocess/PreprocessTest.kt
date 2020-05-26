package com.cloudinary.android.preprocess

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.net.Uri
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.cloudinary.Cloudinary
import com.cloudinary.android.preprocess.image.*
import com.cloudinary.android.preprocess.video.Parameters
import com.cloudinary.android.preprocess.video.Transcode
import com.cloudinary.android.preprocess.video.VideoPreprocessChain
import com.cloudinary.upload.request.FilePayload
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.io.IOException

@RunWith(AndroidJUnit4ClassRunner::class)
class PreprocessTest {
    private val assetFile: File = assetToFile(TEST_IMAGE)!!

    companion object {
        private const val TEST_IMAGE = "images/old_logo.png"
        private lateinit var cloudinary: Cloudinary

        @BeforeClass @JvmStatic
        @Throws(IOException::class)
        fun setup() {
            cloudinary = getCloudinary()
        }
    }

    /**
     * Test that the full decode->steps->encode chain mechanism works
     *
     * @throws PreprocessException
     * @throws PayloadNotFoundException
     * @throws IOException
     */
    @Test
    @Throws(PreprocessException::class, PayloadNotFoundException::class, IOException::class)
    fun testChain() {
        val payload = FilePayload(assetFile)
        val chain = ImagePreprocessChain.limitDimensionsChain(40, 40)
        val filePath = chain.execute(InstrumentationRegistry.getInstrumentation().targetContext, payload)
        val fileInputStream = InstrumentationRegistry.getInstrumentation().targetContext.openFileInput(filePath)
        val bitmap = BitmapFactory.decodeStream(fileInputStream)
        Assert.assertEquals(40, bitmap!!.width.toLong())
        Assert.assertEquals(8, bitmap.height.toLong())
    }

    @Test
    fun testLimit() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        var bitmap = Bitmap.createScaledBitmap(
            BitmapFactory.decodeFile(assetFile.absolutePath),
            1000,
            500,
            false
        )
        bitmap = Limit(100, 100)
            .execute(context!!, bitmap!!)
        Assert.assertEquals(100, bitmap.width.toLong())
        Assert.assertEquals(50, bitmap.height.toLong())

        bitmap = Bitmap.createScaledBitmap(bitmap, 500, 1000, false)
        bitmap = Limit(100, 100)
            .execute(context, bitmap)
        Assert.assertEquals(50, bitmap.width.toLong())
        Assert.assertEquals(100, bitmap.height.toLong())

        bitmap = Bitmap.createScaledBitmap(bitmap, 500, 600, false)
        bitmap = Limit(1000, 1000)
            .execute(context, bitmap)
        Assert.assertEquals(500, bitmap.width.toLong())
        Assert.assertEquals(600, bitmap.height.toLong())

        bitmap = Bitmap.createScaledBitmap(bitmap, 500, 600, false)
        bitmap = Limit(510, 360)
            .execute(context, bitmap)
        Assert.assertEquals(300, bitmap.width.toLong())
        Assert.assertEquals(360, bitmap.height.toLong())

        bitmap = Bitmap.createScaledBitmap(bitmap, 500, 600, false)
        bitmap = Limit(300, 630)
            .execute(context, bitmap)
        Assert.assertEquals(300, bitmap.width.toLong())
        Assert.assertEquals(360, bitmap.height.toLong())
    }

    @Test
    @Throws(PreprocessException::class)
    fun testCrop() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val originalBitmap = Bitmap.createBitmap(BitmapFactory.decodeFile(assetFile.absolutePath))

        var p1 = Point(0, 0)
        var p2 = Point(originalBitmap!!.width, originalBitmap.height)
        var bitmap = Crop(p1, p2)
            .execute(context!!, originalBitmap)
        Assert.assertEquals(bitmap.width.toLong(), originalBitmap.width.toLong())
        Assert.assertEquals(bitmap.height.toLong(), originalBitmap.height.toLong())
        Assert.assertEquals(bitmap, originalBitmap)

        p1 = Point(100, 50)
        p2 = Point(originalBitmap.width, originalBitmap.height)
        bitmap = Crop(p1, p2)
            .execute(context, originalBitmap)
        Assert.assertEquals(bitmap.width.toLong(), originalBitmap.width - 100.toLong())
        Assert.assertEquals(bitmap.height.toLong(), originalBitmap.height - 50.toLong())

        p1 = Point(0, 0)
        p2 = Point(originalBitmap.width + 1, originalBitmap.height)
        try {
            Crop(p1, p2)
                .execute(context, originalBitmap)
            Assert.fail("Out of bound exception should have been thrown")
        } catch (t: Throwable) {
            Assert.assertTrue(t is PreprocessException)
        }

        p1 = Point(0, 0)
        p2 = Point(0, originalBitmap.height)
        try {
            Crop(p1, p2)
                .execute(context, originalBitmap)
            Assert.fail("Points do not make a diagonal exception should have been thrown")
        } catch (t: Throwable) {
            Assert.assertTrue(t is PreprocessException)
        }
    }

    @Test
    fun testRotate() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val originalBitmap = Bitmap.createBitmap(BitmapFactory.decodeFile(assetFile.absolutePath))

        var bitmap = Rotate(90)
            .execute(context!!, originalBitmap!!)
        Assert.assertEquals(bitmap.width.toLong(), originalBitmap.height.toLong())
        Assert.assertEquals(bitmap.height.toLong(), originalBitmap.width.toLong())

        bitmap = Rotate(180)
            .execute(context, originalBitmap)
        Assert.assertEquals(bitmap.width.toLong(), originalBitmap.width.toLong())
        Assert.assertEquals(bitmap.height.toLong(), originalBitmap.height.toLong())

        bitmap = Rotate(360)
            .execute(context, originalBitmap)
        Assert.assertEquals(bitmap.generationId.toLong(), originalBitmap.generationId.toLong())
    }

    @Test
    fun testDimensionsValidator() {
        var errors = 0
        val validator =
            DimensionsValidator(
                100,
                100,
                1000,
                1000
            )
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        var bitmap = Bitmap.createScaledBitmap(
            BitmapFactory.decodeFile(assetFile.absolutePath),
            1000,
            500,
            false
        )
        errors += getError(context, validator, bitmap)
        Assert.assertEquals(0, errors.toLong())
        bitmap = Bitmap.createScaledBitmap(bitmap!!, 100, 100, false)
        errors += getError(context, validator, bitmap)
        Assert.assertEquals(0, errors.toLong())
        bitmap = Bitmap.createScaledBitmap(bitmap, 1000, 1000, false)
        errors += getError(context, validator, bitmap)
        Assert.assertEquals(0, errors.toLong())
        bitmap = Bitmap.createScaledBitmap(bitmap, 50, 500, false)
        errors += getError(context, validator, bitmap)
        Assert.assertEquals(1, errors.toLong())
        bitmap = Bitmap.createScaledBitmap(bitmap, 50, 500, false)
        errors += getError(context, validator, bitmap)
        Assert.assertEquals(2, errors.toLong())
        bitmap = Bitmap.createScaledBitmap(bitmap, 50, 50, false)
        errors += getError(context, validator, bitmap)
        Assert.assertEquals(3, errors.toLong())
        bitmap = Bitmap.createScaledBitmap(bitmap, 1500, 500, false)
        errors += getError(context, validator, bitmap)
        Assert.assertEquals(4, errors.toLong())
        bitmap = Bitmap.createScaledBitmap(bitmap, 500, 1500, false)
        errors += getError(context, validator, bitmap)
        Assert.assertEquals(5, errors.toLong())
        bitmap = Bitmap.createScaledBitmap(bitmap, 1500, 1500, false)
        errors += getError(context, validator, bitmap)
        Assert.assertEquals(6, errors.toLong())
    }

    // TODO: Fix transcoding tests in travis
    @Ignore
    @Test
    @Throws(IOException::class, PreprocessException::class)
    fun testTranscode() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val file = assetToFile("videos/test_video.mp4")
        val videoUri = Uri.fromFile(file)
        val parameters = Parameters(
            requestId = "test_request_id",
            frameRate = 30,
            width = 1280,
            height = 720,
            keyFramesInterval = 3,
            targetAudioBitrateKbps = 128,
            targetVideoBitrateKbps = (3.3 * 1024 * 1024).toInt()
        )
        val outputVideoUri = Transcode(
            parameters
        ).execute(context, videoUri)
        val targetVideoFile = File(outputVideoUri.path!!)
        Assert.assertTrue(targetVideoFile.length() > 0)
    }

    // TODO: Fix transcoding tests in travis
    @Ignore
    @Test
    @Throws(IOException::class, PreprocessException::class, PayloadNotFoundException::class)
    fun testVideoChain() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val file = assetToFile("videos/test_video.mp4")
        val payload = FilePayload(file!!)
        val parameters = Parameters(
            requestId = "test_request_id",
            frameRate = 30,
            width = 1280,
            height = 720,
            keyFramesInterval = 3,
            targetAudioBitrateKbps = 128,
            targetVideoBitrateKbps = (3.3 * 1024 * 1024).toInt()
        )
        val chain = VideoPreprocessChain.videoTranscodingChain(parameters)
        val outputVideoPath = chain.execute(context, payload)
        val targetVideoFile = File(outputVideoPath)
        Assert.assertTrue(targetVideoFile.length() > 0)
    }

    private fun getError(context: Context, validator: DimensionsValidator, bitmap: Bitmap?): Int {
        try {
            validator.execute(context, bitmap!!)
        } catch (e: ValidationException) {
            return 1
        }
        return 0
    }
}