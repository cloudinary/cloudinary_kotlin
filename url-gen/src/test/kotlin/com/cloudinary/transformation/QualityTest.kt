package com.cloudinary.transformation

import com.cloudinary.cldAssert
import org.junit.Test

class QualityTest {
    @Test
    fun testQuality() {
        cldAssert("q_80:qmax_20", Transformation().quality(Quality.level(80) {
            this.quantization(20)
        }))

        cldAssert("q_80:444", Transformation().quality(Quality.level(80) {
            chromaSubSampling(ChromaSubSampling.C_444)
        }))

        cldAssert("q_80", Transformation().quality(80))
        cldAssert("q_auto", Transformation().quality(Quality.auto()))
        cldAssert("q_auto:best", Transformation().quality(Quality.best()))
        cldAssert("q_jpegmini", Transformation().quality(Quality.jpegMini()))
    }
}