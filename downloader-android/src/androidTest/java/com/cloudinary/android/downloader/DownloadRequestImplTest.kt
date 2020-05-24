package com.cloudinary.android.downloader

import android.widget.ImageView
import com.cloudinary.android.downloader.downloaders.DownloadRequestBuilderStrategy
import com.cloudinary.android.downloader.downloaders.DownloadRequestImpl
import com.cloudinary.android.downloader.downloaders.DownloadRequestStrategy
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DownloadRequestImplTest {

    companion object {
        private const val url = "url"
    }

    private val imageView: ImageView = mock()
    private val downloadRequestBuilderStrategy: DownloadRequestBuilderStrategy = mock()
    private lateinit var sut: DownloadRequestImpl

    @Before
    fun setup() {
        sut = DownloadRequestImpl(downloadRequestBuilderStrategy, imageView)
    }

    @Test
    fun testDownloadStartWithUrl() {
        sut.setSource(url)
        sut.start()

        verify(downloadRequestBuilderStrategy, times(1)).into(imageView)
    }

    @Test
    fun testDownloadStartRightAfterUrlIsSet() {
        sut.start()
        sut.setSource(url)

        verify(downloadRequestBuilderStrategy, times(1)).into(imageView)
    }

    @Test
    fun testDownloadDoesNotStartWithoutUrl() {
        sut.start()

        verify(downloadRequestBuilderStrategy, never()).into(imageView)
    }

    @Test
    fun testDownloadDoesNotStartIfCancelled() {
        sut.setSource(url)
        sut.cancel()
        sut.start()

        verify(downloadRequestBuilderStrategy, never()).into(imageView)
    }

    @Test
    fun testDownloadStartOnlyOnce() {
        val downloadRequestStrategy: DownloadRequestStrategy = mock()
        whenever(downloadRequestBuilderStrategy.into(imageView)).thenReturn(downloadRequestStrategy)

        sut.start()
        sut.setSource(url)
        sut.setSource(url)
        sut.start()

        verify(downloadRequestBuilderStrategy, times(1)).into(imageView)
    }
}