package com.cloudinary.transformation

import com.cloudinary.cldAssertEqualsAsString
import com.cloudinary.testParam
import org.junit.Test

class GradientFadeTest {
    @Test
    fun testGradientFade() {
        cldAssertEqualsAsString(
            "e_gradient_fade:40,test_param,y_0.8",
            Transformation().gradientFade(GradientFade.Builder().setStrength(40).setY(0.8).build().add(testParam))
        )
        cldAssertEqualsAsString(
            "e_gradient_fade:40,y_0.8",
            Transformation().gradientFade { setStrength(40).setY(0.8f) })
        cldAssertEqualsAsString("e_gradient_fade:50,x_25", Transformation().gradientFade { setStrength(50).setX(25) })
        cldAssertEqualsAsString("e_gradient_fade:10", Transformation().gradientFade { setStrength(10) })
        cldAssertEqualsAsString("e_gradient_fade,x_0.3", Transformation().gradientFade { setX(0.3f) })
        cldAssertEqualsAsString("e_gradient_fade,y_30", Transformation().gradientFade { setY(30) })

        cldAssertEqualsAsString(
            "e_gradient_fade:symmetric:40,y_0.8",
            Transformation().gradientFade { setSymmetric(true).setStrength(40).setY(0.8f) })
        cldAssertEqualsAsString(
            "e_gradient_fade:symmetric:50,x_25",
            Transformation().gradientFade { setSymmetric(true).setStrength(50).setX(25) })
        cldAssertEqualsAsString(
            "e_gradient_fade:symmetric:10",
            Transformation().gradientFade { setSymmetric(true).setStrength(10) })
        cldAssertEqualsAsString(
            "e_gradient_fade:symmetric,x_0.3",
            Transformation().gradientFade { setSymmetric(true).setX(0.3f) })
        cldAssertEqualsAsString(
            "e_gradient_fade:symmetric,y_30",
            Transformation().gradientFade { setSymmetric(true).setY(30) })
    }


}