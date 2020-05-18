package com.cloudinary.transformation

import com.cloudinary.cldAssert
import org.junit.Test

class GradientFadeTest {
    @Test
    fun testGradientFade() {
        cldAssert(
            "e_gradient_fade:40,y_0.8",
            Transformation().gradientFade(GradientFade.Builder().strength(40).y(0.8).build())
        )
        cldAssert(
            "e_gradient_fade:40,y_0.8",
            Transformation().gradientFade { strength(40).y(0.8f) })
        cldAssert("e_gradient_fade:50,x_25", Transformation().gradientFade { strength(50).x(25) })
        cldAssert("e_gradient_fade:10", Transformation().gradientFade { strength(10) })
        cldAssert("e_gradient_fade,x_0.3", Transformation().gradientFade { x(0.3f) })
        cldAssert("e_gradient_fade,y_30", Transformation().gradientFade { y(30) })

        cldAssert(
            "e_gradient_fade:symmetric:40,y_0.8",
            Transformation().gradientFade { symmetric(true).strength(40).y(0.8f) })
        cldAssert(
            "e_gradient_fade:symmetric:50,x_25",
            Transformation().gradientFade { symmetric(true).strength(50).x(25) })
        cldAssert(
            "e_gradient_fade:symmetric:10",
            Transformation().gradientFade { symmetric(true).strength(10) })
        cldAssert(
            "e_gradient_fade:symmetric,x_0.3",
            Transformation().gradientFade { symmetric(true).x(0.3f) })
        cldAssert(
            "e_gradient_fade:symmetric,y_30",
            Transformation().gradientFade { symmetric(true).y(30) })
    }


}