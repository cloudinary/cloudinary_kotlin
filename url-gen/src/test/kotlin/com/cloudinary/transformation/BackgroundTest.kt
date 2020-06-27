package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.background.Background
import org.junit.Test

class BackgroundTest {
    @Test
    fun testBackgroundColor() {
        cldAssert("b_blue", Background.color(Color.BLUE))
        cldAssert("b_rgb:9090ff", Background.color(Color.Rgb("9090ff")))
    }

    @Test
    fun testBackgroundBorder() {
        cldAssert("b_auto:border", Background.border())

        cldAssert(
            "b_auto:border_contrast", Background.border
            {
                contrast(true)
            }
        )
    }

    @Test
    fun testBackgroundPredominant() {
        cldAssert("b_auto:predominant", Background.predominant())
        cldAssert("b_auto:predominant_contrast", Background.predominant {
            contrast(true)
        })
    }
}