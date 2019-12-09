package com.cloudinary.transformation

import com.cloudinary.cldAssertEqualsAsString
import com.cloudinary.testParam
import org.junit.Test

class BackgroundTest {
    @Test
    fun testBackground() {
        cldAssertEqualsAsString("b_blue,test_param", Background.color { named("blue") }.add(testParam))
        cldAssertEqualsAsString("b_blue", Background.color { named("blue") })
        cldAssertEqualsAsString("b_rgb:9090ff", Background.color { fromRGB("9090ff") })
    }
}