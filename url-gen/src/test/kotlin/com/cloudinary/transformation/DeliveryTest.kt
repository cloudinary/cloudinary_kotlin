package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.delivery.*

import org.junit.Test

class DeliveryTest {

    @Test
    fun testFallbackImage() {
        cldAssert("d_sample", Delivery.defaultImage("sample"))
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
    fun testColorSpace() {
        cldAssert("cs_cmyk", Delivery.colorSpace(ColorSpaceEnum.Cmyk()))
        cldAssert("cs_keep_cmyk", Delivery.colorSpace(ColorSpaceEnum.KeepCmyk()))
        cldAssert("cs_no_cmyk", Delivery.colorSpace(ColorSpaceEnum.NoCmyk()))
        cldAssert("cs_srgb", Delivery.colorSpace(ColorSpaceEnum.SRgb()))
        cldAssert("cs_tinysrgb", Delivery.colorSpace(ColorSpaceEnum.TinySRgb()))

        cldAssert(
            "cs_icc:file.extension",
            Delivery.colorSpace(ColorSpaceEnum.CsIcc("file.extension"))
        )
    }

    @Test
    fun testDefaultImage() {
        cldAssert("d_image_id.ext", Delivery.defaultImage("image_id.ext"))
    }

    @Test
    fun testQuality() {

        cldAssert("q_100", Delivery.quality(100))
        cldAssert("q_100:420", Delivery.quality(100) {
            chromaSubSampling(ChromaSubSampling.C_420)
        })

        cldAssert("q_auto", Quality.auto())
        cldAssert("q_auto:low", Quality.low())

        cldAssert("q_70:qmax_80", Delivery.quality(70) {
            quantization(80)
        })

        cldAssert("q_jpegmini:0", Delivery.quality(Quality.jpegminiBest()))
        cldAssert("q_jpegmini:1", Delivery.quality(Quality.jpegminiHigh()))
        cldAssert("q_jpegmini:2", Delivery.quality(Quality.jpegminiMedium()))
    }

    @Test
    fun testAnyFormat() {
        cldAssert("fl_any_format,q_auto", Quality.auto {
            anyFormat()
        })
    }
}