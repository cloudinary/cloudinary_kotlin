package com.cloudinary.transformation

import com.cloudinary.cldAssert
import org.junit.Test

class RotateTest {
    @Test
    fun testRotate() {
        cldAssert(
            "a_50",
            Transformation().rotate(Rotate.angle(50))
        )

        cldAssert(
            "a_50.hflip",
            Transformation().rotate(Rotate.angle(50) {
                horizontalFlip()
            })
        )

        cldAssert("a_-50", Transformation().rotate(Rotate.angle(-50)))
        cldAssert("a_auto_left", Transformation().rotate(Rotate.autoLeft()))

        cldAssert("a_180.hflip.vflip.45", Transformation().rotate(Rotate.angle(180) {
            horizontalFlip()
            verticalFlip()
            angle(45)
        }))
    }
}