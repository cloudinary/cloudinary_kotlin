package com.cloudinary.transformation

import com.cloudinary.cldAssert

import com.cloudinary.transformation.delivery.ColorSpace
import com.cloudinary.transformation.delivery.Delivery
import com.cloudinary.transformation.delivery.Dpr

import org.junit.Test

class DeliveryTest {

    @Test
    fun testFallbackImage() {
        cldAssert("d_sample", Delivery.fallbackImage("sample"))
    }

    @Test
    fun testDensity() {
        cldAssert("dn_100", Delivery.density(100))
    }

    @Test
    fun testDpr() {
        cldAssert("dpr_auto", Delivery.dpr(Dpr.auto()))
        cldAssert("dpr_2:3", Delivery.dpr("2:3"))
        cldAssert("dpr_1.5", Delivery.dpr(1.5f))
    }

    @Test
    fun testFps() {
        cldAssert("fps_20.2", Delivery.fps(20.2f))
        cldAssert("fps_20.2-30.0", Delivery.fps(20.2f, 30f))
        cldAssert("fps_5.2-", Delivery.fps { min(5.2) })
        cldAssert("fps_5-10", Delivery.fps {
            min(5)
            max(10)
        })
    }

    @Test
    fun testColorSpace() {
        cldAssert("cs_cmyk", Delivery.colorSpace(ColorSpace.Cmyk))
        cldAssert("cs_keep_cmyk", Delivery.colorSpace(ColorSpace.KeepCmyk))
        cldAssert("cs_no_cmyk", Delivery.colorSpace(ColorSpace.NoCmyk))
        cldAssert("cs_srgb", Delivery.colorSpace(ColorSpace.SRgb))
        cldAssert("cs_tinysrgb", Delivery.colorSpace(ColorSpace.TinySRgb))

        cldAssert(
            "cs_icc:file.extension",
            Delivery.colorSpace(ColorSpace.CsIcc("file.extension"))
        )
    }

    @Test
    fun testDefaultImage() {
        cldAssert("d_image_id.ext", Delivery.defaultImage("image_id.ext"))
    }

    @Test
    fun testKeyframeInterval() {
        cldAssert("ki_0.3", Delivery.keyframeInterval(.3f))
    }

    @Test
    fun testQuality() {

        cldAssert("q_100", Quality.level(100))
        cldAssert("q_100:420", Quality.level(100) {
            chromaSubSampling(ChromaSubSampling.C_420)
        })

        cldAssert("q_auto", Quality.auto())
        cldAssert("q_auto:low", Quality.low())

        cldAssert("q_70:qmax_80", Quality.level(70) {
            quantization(80)
        })

        cldAssert("q_jpegmini", Quality.jpegMini())
        cldAssert("q_jpegmini:0", Quality.jpegMini(JpegMini.BEST))
        cldAssert("q_jpegmini:1", Quality.jpegMini(JpegMini.HIGH))
        cldAssert("q_jpegmini:2", Quality.jpegMini(JpegMini.MEDIUM))
    }

    @Test
    fun testAnyFormat() {
        cldAssert("fl_any_format,q_auto", Quality.auto {
            anyFormat()
        })
    }
}