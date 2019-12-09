package com.cloudinary.transformation

import com.cloudinary.cldAssertEqualsAsString
import com.cloudinary.testParam
import org.junit.Test

class RotateTest {
    @Test
    fun testRotate() {
        cldAssertEqualsAsString(
            "a_50,test_param",
            Transformation().rotate(Rotate.Builder().angle(50).build().add(testParam))
        )
        cldAssertEqualsAsString("a_50", Transformation().rotate { angle(50) })
        cldAssertEqualsAsString(
            "a_50.30.vflip",
            Transformation().rotate { angle(50).angle(30).mode(Rotate.Mode.VFLIP) })
        cldAssertEqualsAsString("a_-50", Transformation().rotate { angle(-50) })
        cldAssertEqualsAsString("a_auto_left", Transformation().rotate { mode(Rotate.Mode.AUTO_LEFT) })
    }
}

