package com.cloudinary.android.preprocess.video

import android.content.Context
import android.media.MediaCodecInfo
import android.media.MediaExtractor
import android.media.MediaFormat
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.cloudinary.android.preprocess.Preprocess
import com.cloudinary.android.preprocess.PreprocessException
import com.linkedin.android.litr.MediaTransformer
import com.linkedin.android.litr.TransformationListener
import com.linkedin.android.litr.analytics.TrackTransformationInfo
import java.io.File
import java.io.IOException
import java.util.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * Preprocess implementation for video transcoding.
 */
class Transcode(private val parameters: Parameters) : Preprocess<Uri> {
    private var isTransformationFinished = false
    private val lock = ReentrantLock()
    private val condition = lock.newCondition()
    private var transcodingError: Throwable? = null

    @Throws(PreprocessException::class)
    override fun execute(context: Context, resource: Uri): Uri {
        val targetFilePath = context.filesDir.toString() + File.separator + UUID.randomUUID().toString()
        val outputVideoFile = File(targetFilePath)
        try {
            outputVideoFile.createNewFile()
        } catch (e: IOException) {
            throw PreprocessException("Cannot create output video file.")
        }
        val targetVideoFormat = createTargetVideoFormat(
            parameters.width,
            parameters.height,
            parameters.targetVideoBitrateKbps,
            parameters.keyFramesInterval,
            parameters.frameRate
        )
        val targetAudioFormat = createTargetAudioFormat(context, resource, parameters.targetAudioBitrateKbps)
        val transformationListener: TransformationListener = VideoTransformationListener()
        val mediaTransformer = MediaTransformer(context.applicationContext)
        mediaTransformer.transform(
            parameters.requestId,
            resource,
            targetFilePath,
            targetVideoFormat,
            targetAudioFormat,
            transformationListener,
            MediaTransformer.GRANULARITY_DEFAULT,
            null
        )

        lock.withLock {
            if (!isTransformationFinished) {
                condition.await()
            }
        }
        if (transcodingError != null) {
            val errorMessage = transcodingError!!.message
            transcodingError = null
            throw PreprocessException(
                errorMessage
            )
        }

        return Uri.parse(targetFilePath)
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private fun createTargetVideoFormat(
        width: Int,
        height: Int,
        bitrate: Int,
        keyFrameInterval: Int,
        frameRate: Int
    ): MediaFormat {
        val targetVideoFormat = MediaFormat()
        targetVideoFormat.setString(MediaFormat.KEY_MIME, "video/avc")
        targetVideoFormat.setInteger(MediaFormat.KEY_WIDTH, width)
        targetVideoFormat.setInteger(MediaFormat.KEY_HEIGHT, height)
        targetVideoFormat.setInteger(MediaFormat.KEY_BIT_RATE, bitrate)
        targetVideoFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, keyFrameInterval)
        targetVideoFormat.setInteger(MediaFormat.KEY_FRAME_RATE, frameRate)
        targetVideoFormat.setInteger(
            MediaFormat.KEY_COLOR_FORMAT,
            MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface
        )
        return targetVideoFormat
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private fun createTargetAudioFormat(
        context: Context,
        sourceVideoUri: Uri,
        targetBitrateKbps: Int
    ): MediaFormat? {
        var audioMediaFormat: MediaFormat? = null
        try {
            val mediaExtractor = MediaExtractor()
            mediaExtractor.setDataSource(context, sourceVideoUri, null)
            val trackCount = mediaExtractor.trackCount
            for (track in 0 until trackCount) {
                val mediaFormat = mediaExtractor.getTrackFormat(track)
                var mimeType: String? = null
                if (mediaFormat.containsKey(MediaFormat.KEY_MIME)) {
                    mimeType = mediaFormat.getString(MediaFormat.KEY_MIME)
                }
                if (mimeType != null && mimeType.startsWith("audio")) {
                    audioMediaFormat = MediaFormat()
                    audioMediaFormat.setString(
                        MediaFormat.KEY_MIME,
                        mediaFormat.getString(MediaFormat.KEY_MIME)
                    )
                    audioMediaFormat.setInteger(
                        MediaFormat.KEY_CHANNEL_COUNT,
                        mediaFormat.getInteger(MediaFormat.KEY_CHANNEL_COUNT)
                    )
                    audioMediaFormat.setInteger(
                        MediaFormat.KEY_SAMPLE_RATE,
                        mediaFormat.getInteger(MediaFormat.KEY_SAMPLE_RATE)
                    )
                    audioMediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, targetBitrateKbps * 1000)
                }
            }
        } catch (ex: IOException) {
            Log.e(TAG, "Failed to extract audio track metadata: $ex")
        }
        return audioMediaFormat
    }

    private inner class VideoTransformationListener : TransformationListener {
        override fun onStarted(id: String) {}
        override fun onProgress(id: String, progress: Float) {}
        override fun onCompleted(
            id: String,
            trackTransformationInfos: List<TrackTransformationInfo?>?
        ) {
            lock.withLock {
                isTransformationFinished = true
                condition.signal()
            }
        }

        override fun onCancelled(
            id: String,
            trackTransformationInfos: List<TrackTransformationInfo?>?
        ) {
            lock.withLock {
                isTransformationFinished = true
                condition.signal()
            }
        }

        override fun onError(
            id: String,
            cause: Throwable?,
            trackTransformationInfos: List<TrackTransformationInfo?>?
        ) {
            lock.withLock {
                isTransformationFinished = true
                transcodingError = cause
                condition.signal()
            }
        }
    }

    companion object {
        private val TAG = Transcode::class.java.simpleName
    }

}