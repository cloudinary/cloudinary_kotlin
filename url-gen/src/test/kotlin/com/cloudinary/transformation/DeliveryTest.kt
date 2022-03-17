package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.delivery.*
import org.junit.Test

class DeliveryTest {

    @Test
    fun testDefaultImage() {
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
        cldAssert("cs_cmyk", Delivery.colorSpace(ColorSpace.cmyk()))
        cldAssert("cs_keep_cmyk", Delivery.colorSpace(ColorSpace.keepCmyk()))
        cldAssert("cs_no_cmyk", Delivery.colorSpace(ColorSpace.noCmyk()))
        cldAssert("cs_srgb", Delivery.colorSpace(ColorSpace.srgb()))
        cldAssert("cs_tinysrgb", Delivery.colorSpace(ColorSpace.tinySRgb()))

        cldAssert(
            "cs_icc:file.extension",
            Delivery.colorSpaceFromICC("file.extension")
        )
    }

    @Test
    fun testQuality() {

        cldAssert("q_100", Delivery.quality(100))
        cldAssert("q_100:420", Delivery.quality(100) {
            chromaSubSampling(ChromaSubSampling.chroma420())
        })

        cldAssert("q_auto", Delivery.quality(Quality.auto()))
        cldAssert("q_auto:low", Delivery.quality(Quality.autoLow()))
        cldAssert("q_auto:low:444", Delivery.quality(Quality.autoLow()) {
            this.chromaSubSampling(ChromaSubSampling.chroma444())
        })

        cldAssert("q_70:qmax_80", Delivery.quality(70) {
            quantization(80)
        })

        cldAssert("q_jpegmini:0", Delivery.quality(Quality.jpegminiBest()))
        cldAssert("q_jpegmini:1", Delivery.quality(Quality.jpegminiHigh()))
        cldAssert("q_jpegmini:2", Delivery.quality(Quality.jpegminiMedium()))
    }

    @Test
    fun testAnyFormat() {
        cldAssert("fl_any_format,q_auto", Delivery.quality(Quality.auto()) {
            anyFormat()
        })
    }

    @Test
    fun testFormat() {
        cldAssert(
            "f_png",
            Delivery.format(Format.png())
        )

        cldAssert("f_jpg,fl_progressive:semi",
            Delivery.format(Format.jpg()) {
                progressive(Progressive.semi())
            })

        cldAssert("f_jpg,fl_lossy,fl_preserve_transparency,fl_progressive,fl_ignore_mask_channels",
            Delivery.format(Format.jpg()) {
                lossy()
                progressive(Progressive.progressive())
                preserveTransparency()
                ignoreMaskChannels()
            })
    }
}