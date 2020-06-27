package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.transcode.*
import com.cloudinary.transformation.transcode.Transcode.Companion.videoCodec
import com.cloudinary.transformation.transcode.VideoCodec.Companion.h264
import org.junit.Test

class TranscodeTest {
    @Test
    fun testAudioCodec() {
        cldAssert("ac_aac", Transcode.audioCodec(AudioCodec.AAC))
    }

    @Test
    fun testAudioFrequency() {
        cldAssert("af_8000", Transcode.audioFrequency(AudioFrequency.FREQ8000))
    }

    @Test
    fun testStreamingProfile() {
        cldAssert("sp_example", Transcode.streamingProfile("example"))
        cldAssert("sp_full_hd", Transcode.streamingProfile(StreamingProfile.FULL_HD))
    }

    @Test
    fun toAnimated() {
        cldAssert("vs_3", Transcode.toAnimated(3))
        cldAssert("dl_100,vs_3", Transcode.toAnimated {
            delay(100)
            videoSampling(3)
        })
    }

    @Test
    fun testVideoCodec() {

        cldAssert("vc_vp8", videoCodec(VideoCodec.vp8()))

        cldAssert("vc_h264:baseline", videoCodec(h264 {
            profile(VideoCodecProfile.BASELINE)
        }))

        cldAssert("vc_h264:high:3.1", videoCodec(h264 {
            profile(VideoCodecProfile.HIGH)
            level(VideoCodecLevel.VCL_31)
        }))
    }

    @Test
    fun testBitRate() {
        cldAssert("br_250k", Transcode.bitRate("250k"))
    }
}