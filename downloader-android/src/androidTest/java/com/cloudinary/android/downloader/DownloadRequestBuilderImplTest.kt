package com.cloudinary.android.downloader

import android.net.Uri
import android.widget.ImageView
import androidx.test.platform.app.InstrumentationRegistry
import com.cloudinary.Cloudinary
import com.cloudinary.android.downloader.test.R
import com.cloudinary.config.AccountConfig
import com.cloudinary.config.ApiConfig
import com.cloudinary.config.Configuration
import com.cloudinary.config.UrlConfig
import com.cloudinary.android.downloader.downloaders.DownloadRequestBuilderImpl
import com.cloudinary.android.downloader.downloaders.DownloadRequestBuilderStrategy
import com.cloudinary.transformation.resize.Resize
import com.cloudinary.transformation.transformation
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DownloadRequestBuilderImplTest {

    companion object {
        private const val TEST_PUBLIC_ID = "sample"
        private var cloudName: String? = null
        private lateinit var cloudinary: Cloudinary

        @BeforeClass @JvmStatic
        fun setup() {
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            val uri = Uri.parse(Cloudinary.cloudinaryUrlFromContext(context))
            cloudName = uri.host
            val configuration = Configuration(AccountConfig(cloudName = cloudName!!), UrlConfig(secure = true), ApiConfig())
            cloudinary =
                Cloudinary(configuration)
        }
    }

    private val downloadRequestBuilderStrategy: DownloadRequestBuilderStrategy = mock()
    private val imageView: ImageView = mock()
    private lateinit var sut: DownloadRequestBuilderImpl

    @Before
    fun initSut() {
        val context = InstrumentationRegistry.getInstrumentation().context
        sut = DownloadRequestBuilderImpl(context,
            cloudinary, downloadRequestBuilderStrategy)
    }

    @Test
    fun testLoadResource() {
        val resource: Int = R.drawable.old_logo

        sut.load(resource)
        sut.into(imageView)

        verify(downloadRequestBuilderStrategy, times(1)).load(eq(resource))
        verify(downloadRequestBuilderStrategy, times(1)).into(imageView)
    }

    @Test
    fun testLoadWithRemoteUrl() {
        val remoteUrl = "https://res.cloudinary.com/$cloudName/image/upload/$TEST_PUBLIC_ID"

        sut.load(remoteUrl)
        sut.into(imageView)

        verify(downloadRequestBuilderStrategy, times(1)).load(eq(remoteUrl))
        verify(downloadRequestBuilderStrategy, times(1)).into(imageView)
    }

    @Test
    fun testLoadWithGeneratedCloudinaryUrlSource() {
        sut.load(TEST_PUBLIC_ID)
        sut.into(imageView)

        val expectedUrl = "https://res.cloudinary.com/$cloudName/image/upload/$TEST_PUBLIC_ID"
        verify(downloadRequestBuilderStrategy, times(1)).load(eq(expectedUrl))
        verify(downloadRequestBuilderStrategy, times(1)).into(imageView)
    }

    @Test
    fun testLoadWithGeneratedCloudinaryUrlSourceWithTransformation() {
        sut.load(TEST_PUBLIC_ID)
        sut.transformation(transformation {
            resize(Resize.scale {
                width(200)
                height(400)
            })
        })
        sut.into(imageView)

        val expectedUrl = "https://res.cloudinary.com/$cloudName/image/upload/c_scale,h_400,w_200/$TEST_PUBLIC_ID"
        verify(downloadRequestBuilderStrategy, times(1)).load(eq(expectedUrl))
        verify(downloadRequestBuilderStrategy, times(1)).into(imageView)
    }

    @Test
    fun testRequestBuiltWithPlaceholder() {
        val placeholder: Int = R.drawable.old_logo

        sut.load(TEST_PUBLIC_ID)
        sut.placeholder(placeholder)
        sut.into(imageView)

        verify(downloadRequestBuilderStrategy, times(1)).placeholder(eq(placeholder))
        verify(downloadRequestBuilderStrategy, times(1)).into(imageView)
    }
}