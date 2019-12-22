package com.cloudinary.transformation

import com.cloudinary.cldAssertEqualsAsString
import com.cloudinary.testParam
import org.junit.Test

class GradientFadeTest {
    @Test
    fun testGradientFade() {
        cldAssertEqualsAsString(
            "e_gradient_fade:40,test_param,y_0.8",
            Transformation().gradientFade(GradientFade.Builder().strength(40).y(0.8).build().add(testParam))
        )
        cldAssertEqualsAsString(
            "e_gradient_fade:40,y_0.8",
            Transformation().gradientFade { strength(40).y(0.8f) })
        cldAssertEqualsAsString("e_gradient_fade:50,x_25", Transformation().gradientFade { strength(50).x(25) })
        cldAssertEqualsAsString("e_gradient_fade:10", Transformation().gradientFade { strength(10) })
        cldAssertEqualsAsString("e_gradient_fade,x_0.3", Transformation().gradientFade { x(0.3f) })
        cldAssertEqualsAsString("e_gradient_fade,y_30", Transformation().gradientFade { y(30) })

        cldAssertEqualsAsString(
            "e_gradient_fade:symmetric:40,y_0.8",
            Transformation().gradientFade { symmetric(true).strength(40).y(0.8f) })
        cldAssertEqualsAsString(
            "e_gradient_fade:symmetric:50,x_25",
            Transformation().gradientFade { symmetric(true).strength(50).x(25) })
        cldAssertEqualsAsString(
            "e_gradient_fade:symmetric:10",
            Transformation().gradientFade { symmetric(true).strength(10) })
        cldAssertEqualsAsString(
            "e_gradient_fade:symmetric,x_0.3",
            Transformation().gradientFade { symmetric(true).x(0.3f) })
        cldAssertEqualsAsString(
            "e_gradient_fade:symmetric,y_30",
            Transformation().gradientFade { symmetric(true).y(30) })
    }


}