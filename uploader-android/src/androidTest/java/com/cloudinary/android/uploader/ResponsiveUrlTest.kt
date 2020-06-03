package com.cloudinary.android.uploader

import android.net.Uri
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.test.platform.app.InstrumentationRegistry
import com.cloudinary.Cloudinary
import com.cloudinary.Url
import com.cloudinary.android.uploader.responsive.Preset
import com.cloudinary.android.uploader.responsive.ResponsiveUrl
import com.cloudinary.config.AccountConfig
import com.cloudinary.config.ApiConfig
import com.cloudinary.config.Configuration
import com.cloudinary.config.UrlConfig
import com.cloudinary.transformation.Gravity
import com.cloudinary.transformation.Transformation
import com.cloudinary.transformation.resize.Resize
import com.nhaarman.mockitokotlin2.KArgumentCaptor
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class ResponsiveUrlTest {
    companion object {
        private const val TEST_PUBLIC_ID = "sample"
        private lateinit var cloudinary: Cloudinary
        private lateinit var cloudName: String

        @BeforeClass @JvmStatic
        fun setup() {
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            val uri = Uri.parse(cloudinaryUrlFromContext(context))
            cloudName = uri.host!!
            val configuration = Configuration(AccountConfig(cloudName = cloudName), UrlConfig(secure = true), ApiConfig())
            cloudinary = Cloudinary(configuration)
        }
    }

    private lateinit var sut: ResponsiveUrl

    private val callback: ResponsiveUrl.Callback = mock()
    private val generatedUrl: KArgumentCaptor<Url> = argumentCaptor()
    private val imageView = createImageView(220, 440)

    @Test
    fun testAutoFillPreset() {
        sut = Preset.AutoFill.get(cloudinary)
        val baseUrl = cloudinary.url { publicId(TEST_PUBLIC_ID) }

        sut.generate(baseUrl, imageView, callback)

        verify(callback).onUrlReady(generatedUrl.capture())
        val expectedUrl = "https://res.cloudinary.com/$cloudName/image/upload/c_fill,g_auto,h_600,w_400/$TEST_PUBLIC_ID"
        assertEquals(expectedUrl, generatedUrl.firstValue.generate())
    }

    @Test
    fun testAutoFillPresetWithExistingGradientTransformation() {
        sut = Preset.AutoFill.get(cloudinary)
        val baseUrl = cloudinary.url {
            publicId(TEST_PUBLIC_ID)
            transformation(Transformation().gradientFade { strength(10) })
        }

        sut.generate(baseUrl, imageView, callback)

        verify(callback).onUrlReady(generatedUrl.capture())
        val expectedUrl = "https://res.cloudinary.com/$cloudName/image/upload/e_gradient_fade:10/c_fill,g_auto,h_600,w_400/$TEST_PUBLIC_ID"
        assertEquals(expectedUrl, generatedUrl.firstValue.generate())
    }

    @Test
    fun testFitPreset() {
        sut = Preset.Fit.get(cloudinary)
        val baseUrl = cloudinary.url { publicId(TEST_PUBLIC_ID) }

        sut.generate(baseUrl, imageView, callback)

        verify(callback).onUrlReady(generatedUrl.capture())
        val expectedUrl = "https://res.cloudinary.com/$cloudName/image/upload/c_fit,g_center,h_600,w_400/$TEST_PUBLIC_ID"
        assertEquals(expectedUrl, generatedUrl.firstValue.generate())
    }

    @Test
    fun testCustomFillPadPreset() {
        sut = Preset.Custom(autoHeight = true, autoWidth = true, resizeAction = { width, height ->
            Resize.fillPad {
                width?.let{ width(it) }
                height?.let{ height(it) }
                gravity(Gravity.east())
            }
        }).get(cloudinary)
        val baseUrl = cloudinary.url { publicId(TEST_PUBLIC_ID) }

        sut.generate(baseUrl, imageView, callback)

        verify(callback).onUrlReady(generatedUrl.capture())
        val expectedUrl = "https://res.cloudinary.com/$cloudName/image/upload/c_fill_pad,g_east,h_600,w_400/$TEST_PUBLIC_ID"
        assertEquals(expectedUrl, generatedUrl.firstValue.generate())
    }

    @Test
    fun testCustomFillPadPresetWithoutAutoHeight() {
        sut = Preset.Custom(autoHeight = false, autoWidth = true, resizeAction = { width, height ->
            Resize.fillPad {
                width?.let{ width(it) }
                height?.let{ height(it) }
                gravity(Gravity.east())
            }
        }).get(cloudinary)
        val baseUrl = cloudinary.url { publicId(TEST_PUBLIC_ID) }

        sut.generate(baseUrl, imageView, callback)

        verify(callback).onUrlReady(generatedUrl.capture())
        val expectedUrl = "https://res.cloudinary.com/$cloudName/image/upload/c_fill_pad,g_east,w_400/$TEST_PUBLIC_ID"
        assertEquals(expectedUrl, generatedUrl.firstValue.generate())
    }

    @Test
    fun testCustomFillPadPresetWithoutAutoWidth() {
        sut = Preset.Custom(autoHeight = true, autoWidth = false, resizeAction = { width, height ->
            Resize.fillPad {
                width?.let{ width(it) }
                height?.let{ height(it) }
                gravity(Gravity.east())
            }
        }).get(cloudinary)
        val baseUrl = cloudinary.url { publicId(TEST_PUBLIC_ID) }

        sut.generate(baseUrl, imageView, callback)

        verify(callback).onUrlReady(generatedUrl.capture())
        val expectedUrl = "https://res.cloudinary.com/$cloudName/image/upload/c_fill_pad,g_east,h_600/$TEST_PUBLIC_ID"
        assertEquals(expectedUrl, generatedUrl.firstValue.generate())
    }

    @Test
    fun testCustomFillPadPresetWithoutWidthAndHeightAdjustments() {
        sut = Preset.Custom(autoHeight = false, autoWidth = false, resizeAction = { width, height ->
            Resize.fillPad {
                width?.let{ width(it) }
                height?.let{ height(it) }
                gravity(Gravity.east())
            }
        }).get(cloudinary)
        val baseUrl = cloudinary.url { publicId(TEST_PUBLIC_ID) }

        sut.generate(baseUrl, imageView, callback)

        verify(callback).onUrlReady(generatedUrl.capture())
        val expectedUrl = "https://res.cloudinary.com/$cloudName/image/upload/c_fill_pad,g_east/$TEST_PUBLIC_ID"
        assertEquals(expectedUrl, generatedUrl.firstValue.generate())
    }

}

private fun createImageView(width: Int, height: Int): ImageView {
    val context = InstrumentationRegistry.getInstrumentation().context
    val linearLayout = LinearLayout(context)
    val imageView = ImageView(context)

    linearLayout.layout(0, 0, width, height)
    imageView.layout(0, 0, width, height)
    linearLayout.addView(imageView)

    return imageView
}
