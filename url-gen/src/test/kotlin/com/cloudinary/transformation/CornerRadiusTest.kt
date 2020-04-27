package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.testParam
import org.junit.Test

class CornerRadiusTest {
    @Test
    fun testCornerRadius() {
        cldAssert("r_20,test_param", RoundCorners.Builder().pixels(20).build().add(testParam))
        cldAssert("r_20", Transformation().roundCorners(20))
        // TODO - how are arrays handles in this syntax?
//        cldAssert("r_20:0", Transformation().cornersRadius { pixels(20, 0) })
//        cldAssert("r_max", Transformation().cornersRadius { max() })
    }
}