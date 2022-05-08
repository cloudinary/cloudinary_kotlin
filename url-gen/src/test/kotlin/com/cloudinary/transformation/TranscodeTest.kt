package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.transcode.*
import com.cloudinary.transformation.transcode.Transcode.Companion.videoCodec
import org.junit.Test

class TranscodeTest {
    @Test
    fun testAudioCodec() {
        cldAssert("ac_aac", Transcode.audioCodec(AudioCodec.aac()))
    }

    @Test
    fun testAudioFrequency() {
        cldAssert("af_8000", Transcode.audioFrequency(AudioFrequency.freq8000()))
        cldAssert("af_iaf", Transcode.audioFrequency(AudioFrequency.original()))
    }

    @Test
    fun testStreamingProfile() {
        cldAssert("sp_full_hd", Transcode.streamingProfile(StreamingProfile.fullHd()))
    }

    @Test
    fun toAnimated() {
        cldAssert("f_webp,fl_animated,fl_awebp", Transcode.toAnimated("webp"))
        cldAssert("dl_100,f_gif,fl_animated,vs_3", Transcode.toAnimated("gif") {
            delay(100)
            sampling(3)
        })
        cldAssert("dl_100,f_webp,fl_animated,fl_awebp,vs_3", Transcode.toAnimated(AnimatedFormat.webp()) {
            delay(100)
            sampling(3)
        })
    }

    @Test
    fun testVideoCodec() {

        cldAssert("vc_vp8", videoCodec(VideoCodec.vp8()))

        cldAssert("vc_h264:baseline", videoCodec(VideoCodec.h264 {
            profile(VideoCodecProfile.baseline())
        }))

        cldAssert("vc_h264:high:3.1", videoCodec(VideoCodec.h264 {
            profile(VideoCodecProfile.high())
            level(VideoCodecLevel.vcl31())
        }))

        cldAssert("vc_h265", videoCodec(VideoCodec.h265()))
    }

    @Test
    fun testDisableBFrames() {
        cldAssert("vc_h265:auto:auto:bframes_no", videoCodec(VideoCodec.h265 { bFrames(false) }))
        cldAssert("vc_h265", videoCodec(VideoCodec.h265 { bFrames(true) }))
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
        cldAssert("fps_20.2-", Transcode.fpsRange(20.2f))
        cldAssert("fps_20.2-30.0", Transcode.fpsRange(20.2f, 30f))
    }
}