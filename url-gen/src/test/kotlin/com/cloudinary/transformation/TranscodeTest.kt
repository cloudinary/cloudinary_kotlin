package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.transcode.*
import com.cloudinary.transformation.transcode.Transcode.Companion.videoCodec
import org.junit.Test

class TranscodeTest {
    @Test
    fun testAudioCodec() {
        cldAssert("ac_aac", Transcode.audioCodec(AudioCodecType.AAC))
    }

    @Test
    fun testAudioFrequency() {
        cldAssert("af_8000", Transcode.audioFrequency(AudioFrequencyType.FREQ8000))
    }

    @Test
    fun testStreamingProfile() {
        cldAssert("sp_full_hd", Transcode.streamingProfile(StreamingProfileType.FULL_HD))
    }

    @Test
    fun toAnimated() {
        cldAssert("f_webp,fl_awebp", Transcode.toAnimated("webp"))
        cldAssert("dl_100,f_gif,vs_3", Transcode.toAnimated("gif") {
            delay(100)
            videoSampling(3)
        })
    }

    @Test
    fun testVideoCodec() {

        cldAssert("vc_vp8", videoCodec(VideoCodecType.VP8))

        cldAssert("vc_h264:baseline", videoCodec(VideoCodecType.H264) {
            profile(VideoCodecProfile.BASELINE)
        })

        cldAssert("vc_h264:high:3.1", videoCodec(VideoCodecType.H264) {
            profile(VideoCodecProfile.HIGH)
            level(VideoCodecLevel.VCL_31)
        })
    }

    @Test
    fun testBitRate() {
        cldAssert("br_250k", Transcode.bitRate("250k"))
    }

    @Test
    fun testKeyframeInterval() {
        cldAssert("ki_0.3", Transcode.keyframeInterval(.3f))
    }

    @Test
    fun testFps() {
        cldAssert("fps_20.2", Transcode.fps(20.2f))
        cldAssert("fps_20.2-30.0", Transcode.fps(20.2f, 30f))
        cldAssert("fps_5.2-", Transcode.fps { min(5.2) })
        cldAssert("fps_5-10", Transcode.fps {
            min(5)
            max(10)
        })
    }
}