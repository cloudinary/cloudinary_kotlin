package com.cloudinary.transformation

import com.cloudinary.cldAssert
import org.junit.Test

class RotateTest {
    @Test
    fun testRotate() {
        cldAssert(
            "a_50",
            Transformation().rotate(Rotate.byAngle(50))
        )

        cldAssert(
            "a_50",
            Transformation().rotate(Rotate.byAngle(50))
        )

        cldAssert("a_-50", Transformation().rotate(Rotate.byAngle(-50)))
        cldAssert("a_hflip", Transformation().rotate(Rotate.mode(RotationMode.horizontalFlip())))
    }
}