package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.testParam
import com.cloudinary.transformation.delivery.AudioCodecType.AAC
import com.cloudinary.transformation.delivery.AudioFrequencyType.HZ_8000
import com.cloudinary.transformation.delivery.AutoQuality.LOW
import com.cloudinary.transformation.delivery.ChromaSubSampling.C_420
import com.cloudinary.transformation.delivery.ColorSpaceType
import com.cloudinary.transformation.delivery.Delivery
import com.cloudinary.transformation.delivery.Delivery.Companion.quality
import com.cloudinary.transformation.delivery.Delivery.Companion.videoCodec
import com.cloudinary.transformation.delivery.QualityType
import com.cloudinary.transformation.delivery.QualityType.JPEG_MINI
import com.cloudinary.transformation.delivery.VideoCodecProfile.VCP_BASELINE
import com.cloudinary.transformation.delivery.VideoCodecProfile.VCP_HIGH
import com.cloudinary.transformation.delivery.VideoCodecType.H264
import com.cloudinary.transformation.delivery.VideoCodecType.VP8
import org.junit.Test

class DeliveryTest {
    @Test
    fun testAudioCodec() {
        cldAssert("ac_aac,test_param", Delivery.audioCodec(AAC).add(testParam))
        cldAssert("ac_aac", Delivery.audioCodec(AAC))
    }

    @Test
    fun testAudioFrequency() {
        cldAssert("af_8000", Delivery.audioFrequency(HZ_8000))
    }

    @Test
    fun testColorSpace() {
        cldAssert("cs_cmyk", Delivery.colorSpace(ColorSpaceType.CMYK))

        cldAssert(
            "cs_icc:file.extension",
            Delivery.colorSpace(ColorSpaceType.CsIcc("file.extension"))
        )
    }

    @Test
    fun testDefaultImage() {
        cldAssert("d_image_id.ext", Delivery.defaultImage("image_id.ext"))
    }

    @Test
    fun testVideoCodec() {
        Transformation().delivery(videoCodec(H264) {
            profile(VCP_BASELINE)
            level(3.1f)
        })

        cldAssert("vc_vp8", videoCodec(VP8))

        cldAssert("vc_h264:baseline", videoCodec(H264) {
            profile(VCP_BASELINE)
        })

        cldAssert("vc_h264:high:3.1", videoCodec(H264) {
            profile(VCP_HIGH)
            level(3.1f)
        })
    }

    @Test
    fun testFps() {
        cldAssert("fps_5", Delivery.fps(5))
        cldAssert("fps_5-", Delivery.fps(5, null))
        cldAssert("fps_5-10", Delivery.fps(5, 10))

        cldAssert("fps_5.2-", Delivery.fps(5.2, max = null))
        cldAssert("fps_5.2-10", Delivery.fps(5.2, 10))
    }

    @Test
    fun testQuality() {

        cldAssert("q_100", quality(100))
        cldAssert("q_100:420", quality(100) {
            chromaSubSampling(C_420)
        })

        cldAssert("q_auto", quality(QualityType.AUTO))
        cldAssert(
            "q_auto:low", quality(QualityType.AUTO) {
                preset(LOW)
            })

        cldAssert("q_70:qmax_80", quality(70) {
            maxQuantization(80)
        })

        cldAssert("q_jpegmini", quality(JPEG_MINI))
    }

    @Test
    fun testKeyframeInterval() {
        cldAssert("ki_0.3", Delivery.keyframeInterval(.3f))
    }

    @Test
    fun testStreamingProfile() {
        cldAssert("sp_example", Delivery.streamingProfile("example"))
    }

    @Test
    fun testAnyFormat() {
        cldAssert("fl_any_format,q_auto", quality(QualityType.AUTO) {
            anyFormat(true)
        })
        cldAssert("q_auto", quality(QualityType.AUTO) {
            anyFormat(false)
        })
    }
}