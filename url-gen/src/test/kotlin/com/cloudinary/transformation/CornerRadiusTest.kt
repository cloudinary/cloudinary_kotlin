package com.cloudinary.transformation

import com.cloudinary.cldAssertEqualsAsString
import com.cloudinary.testParam
import org.junit.Test

class CornerRadiusTest {
    @Test
    fun testCornerRadius() {
        cldAssertEqualsAsString("r_20,test_param", CornerRadius.Builder().pixels(20).build().add(testParam))
        cldAssertEqualsAsString("r_20", Transformation().cornersRadius { pixels(20) })
        cldAssertEqualsAsString("r_20:0", Transformation().cornersRadius { pixels(20, 0) })
        cldAssertEqualsAsString("r_max", Transformation().cornersRadius { max() })
    }
}