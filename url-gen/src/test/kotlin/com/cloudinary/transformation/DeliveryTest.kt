package com.cloudinary.transformation

import com.cloudinary.cldAssertEqualsAsString
import com.cloudinary.testParam
import com.cloudinary.transformation.delivery.*
import com.cloudinary.transformation.delivery.AudioCodecType.*
import com.cloudinary.transformation.delivery.AudioFrequencyType.*
import com.cloudinary.transformation.delivery.QualityType.*
import org.junit.Test

class DeliveryTest {
    @Test
    fun testAudioCodec() {
        cldAssertEqualsAsString("ac_aac,test_param", Delivery.audioCodec(AAC).add(testParam))
        cldAssertEqualsAsString("ac_aac", Delivery.audioCodec(AAC))
        cldAssertEqualsAsString("ac_none", Delivery.audioCodec(NONE))
        cldAssertEqualsAsString("ac_mp3", Delivery.audioCodec(MP3))
        cldAssertEqualsAsString("ac_vorbis", Delivery.audioCodec(VORBIS))
    }

    @Test
    fun testAudioFrequency() {
        cldAssertEqualsAsString("af_8000", Delivery.audioFrequency(HZ_8000))
        cldAssertEqualsAsString("af_11025", Delivery.audioFrequency(HZ_11025))
        cldAssertEqualsAsString("af_16000", Delivery.audioFrequency(HZ_16000))
        cldAssertEqualsAsString("af_22050", Delivery.audioFrequency(HZ_22050))
        cldAssertEqualsAsString("af_32000", Delivery.audioFrequency(HZ_32000))
        cldAssertEqualsAsString("af_37800", Delivery.audioFrequency(HZ_37800))
        cldAssertEqualsAsString("af_44056", Delivery.audioFrequency(HZ_44056))
        cldAssertEqualsAsString("af_44100", Delivery.audioFrequency(HZ_44100))
        cldAssertEqualsAsString("af_47250", Delivery.audioFrequency(HZ_47250))
        cldAssertEqualsAsString("af_48000", Delivery.audioFrequency(HZ_48000))
        cldAssertEqualsAsString("af_88200", Delivery.audioFrequency(HZ_88200))
        cldAssertEqualsAsString("af_96000", Delivery.audioFrequency(HZ_96000))
        cldAssertEqualsAsString("af_176400", Delivery.audioFrequency(HZ_176400))
        cldAssertEqualsAsString("af_192000", Delivery.audioFrequency(HZ_192000))
    }

    @Test
    fun testColorSpace() {
        cldAssertEqualsAsString(
            "cs_cmyk", Delivery.colorSpace(
                ColorSpaceType.CMYK
            )
        )
        cldAssertEqualsAsString(
            "cs_srgb", Delivery.colorSpace(
                ColorSpaceType.SRGB
            )
        )
        cldAssertEqualsAsString(
            "cs_tinysrgb", Delivery.colorSpace(
                ColorSpaceType.TINYSRGB
            )
        )
        cldAssertEqualsAsString(
            "cs_no_cmyk", Delivery.colorSpace(
                ColorSpaceType.NO_CMYK
            )
        )
        cldAssertEqualsAsString(
            "cs_keep_cmyk", Delivery.colorSpace(
                ColorSpaceType.KEEP_CMYK
            )
        )
        cldAssertEqualsAsString(
            "cs_icc:file.extension", Delivery.colorSpace(
                ColorSpaceType.CS_ICC("file.extension")
            )
        )
    }

    @Test
    fun testDefaultImage() {
        cldAssertEqualsAsString("d_image_id.ext", Delivery.defaultImage("image_id.ext"))
    }

    @Test
    fun testVideoCodec() {
        cldAssertEqualsAsString(
            "vc_vp8", Delivery.videoCodec(
                VideoCodecType.VP8
            )
        )
        cldAssertEqualsAsString(
            "vc_vp9", Delivery.videoCodec(
                VideoCodecType.VP9
            )
        )
        cldAssertEqualsAsString(
            "vc_prores", Delivery.videoCodec(
                VideoCodecType.PRORES
            )
        )
        cldAssertEqualsAsString(
            "vc_h264", Delivery.videoCodec(
                VideoCodecType.H264
            )
        )
        cldAssertEqualsAsString(
            "vc_h265", Delivery.videoCodec(
                VideoCodecType.H265
            )
        )
        cldAssertEqualsAsString(
            "vc_theora", Delivery.videoCodec(
                VideoCodecType.THEORA
            )
        )
        cldAssertEqualsAsString(
            "vc_auto", Delivery.videoCodec(
                VideoCodecType.AUTO
            )
        )
        cldAssertEqualsAsString("vc_h264:baseline", Delivery.videoCodec(
            VideoCodecType.H264
        ) { setProfile("baseline") })
        cldAssertEqualsAsString("vc_h264:baseline:3.1", Delivery.videoCodec(
            VideoCodecType.H264
        ) { setProfile("baseline").setLevel(3.1f) })
    }

    @Test
    fun testFps() {
        cldAssertEqualsAsString("fps_5", Delivery.fps(5))
    }

    @Test
    fun testFpsRange() {
        cldAssertEqualsAsString("fps_5-", Delivery.fps(5, max = null))
        cldAssertEqualsAsString("fps_5-10", Delivery.fps(5, 10))
    }

    @Test
    fun testQuality() {
        cldAssertEqualsAsString("q_100", Delivery.quality(Fixed(100)))
        cldAssertEqualsAsString("q_100:420", Delivery.quality(Fixed(100, ChromaSubSampling.C_420)))
        cldAssertEqualsAsString("q_auto", Delivery.quality(Auto()))
        cldAssertEqualsAsString(
            "q_auto:low", Delivery.quality(
                Auto(
                    AutoQuality.LOW
                )
            )
        )

        cldAssertEqualsAsString("q_70:qmax_80", Delivery.quality(FixedVideo(70, 80)))

        cldAssertEqualsAsString("q_jpegmini", Delivery.quality(JpegMini()))
    }

    @Test
    fun testKeyframeInterval() {
        cldAssertEqualsAsString("ki_0.3", Delivery.keyframeInterval(.3f))
    }

    @Test
    fun testStreamingProfile() {
        cldAssertEqualsAsString("sp_example", Delivery.streamingProfile("example"))
    }
}