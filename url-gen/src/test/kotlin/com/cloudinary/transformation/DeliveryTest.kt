package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.testParam
import com.cloudinary.transformation.AutoQuality.LOW
import com.cloudinary.transformation.delivery.AudioCodecType.AAC
import com.cloudinary.transformation.delivery.AudioFrequencyType.HZ_8000
import com.cloudinary.transformation.delivery.ColorSpaceType
import com.cloudinary.transformation.delivery.Delivery
import com.cloudinary.transformation.delivery.Delivery.Companion.videoCodec
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
    fun testKeyframeInterval() {
        cldAssert("ki_0.3", Delivery.keyframeInterval(.3f))
    }

    @Test
    fun testStreamingProfile() {
        cldAssert("sp_example", Delivery.streamingProfile("example"))
    }

    @Test
    fun testQuality() {

        cldAssert("q_100", Quality.fixed(100))
        cldAssert("q_100:420", Quality.fixed(100) {
            chromaSubSampling(ChromaSubSampling.C_420)
        })

        cldAssert("q_auto", Quality.auto())
        cldAssert(
            "q_auto:low", Quality.auto {
                preset(LOW)
            })

        cldAssert("q_70:qmax_80", Quality.fixed(70) {
            maxQuantization(80)
        })

        cldAssert("q_jpegmini", Quality.jpegmini())
    }

    @Test
    fun testAnyFormat() {
        cldAssert("fl_any_format,q_auto", Quality.auto {
            anyFormat(true)
        })
        cldAssert("q_auto", Quality.auto {
            anyFormat(false)
        })
    }
}