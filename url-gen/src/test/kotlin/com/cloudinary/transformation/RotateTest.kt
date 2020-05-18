package com.cloudinary.transformation

import com.cloudinary.cldAssert
import org.junit.Test

class RotateTest {
    @Test
    fun testRotate() {
        cldAssert(
            "a_50",
            Transformation().rotate(Rotate.Builder().angle(50).build())
        )
        cldAssert("a_50", Transformation().rotate { angle(50) })
        cldAssert(
            "a_50.30.vflip",
            Transformation().rotate { angle(50).angle(30).mode(Rotate.Mode.VFLIP) })
        cldAssert("a_-50", Transformation().rotate { angle(-50) })
        cldAssert("a_auto_left", Transformation().rotate { mode(Rotate.Mode.AUTO_LEFT) })
    }
}

