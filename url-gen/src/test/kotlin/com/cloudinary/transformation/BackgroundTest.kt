package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.testParam
import org.junit.Test

class BackgroundTest {
    @Test
    fun testBackground() {
        cldAssert("b_blue,test_param", Background.color { named("blue") }.add(testParam))
        cldAssert("b_blue", Background.color { named("blue") })
        cldAssert("b_rgb:9090ff", Background.color { fromRGB("9090ff") })
    }
}