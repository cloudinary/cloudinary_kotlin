package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.effect.Effect
import com.cloudinary.transformation.effect.GradientFade
import org.junit.Test

class GradientFadeTest {
    @Test
    fun testGradientFade() {
        cldAssert(
            "e_gradient_fade:40,y_0.8",
            Effect.gradientFade {
                strength(40)
                verticalStartPoint(0.8)
            }
        )
        cldAssert(
            "e_gradient_fade:50,x_0.4",
            Effect.gradientFade {
                strength(50)
                horizontalStartPoint(0.4)
            }
        )
        cldAssert(
            "e_gradient_fade:30",
            Effect.gradientFade {
                strength(30)
            }
        )

        cldAssert(
            "e_gradient_fade:symmetric,y_0.8",
            Effect.gradientFade {
                type(GradientFade.symmetric())
                verticalStartPoint(0.8)

            }
        )

        cldAssert(
            "e_gradient_fade:symmetric:40,y_0.8",
            Effect.gradientFade {
                type(GradientFade.symmetric())
                strength(40)
                verticalStartPoint(0.8f)
            })
        cldAssert(
            "e_gradient_fade:symmetric:50,x_25",
            Effect.gradientFade {
                type(GradientFade.symmetric())
                strength(50)
                horizontalStartPoint(25)
            })
    }
}