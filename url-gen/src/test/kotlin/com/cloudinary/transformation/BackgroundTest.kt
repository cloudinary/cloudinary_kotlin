package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.background.Background
import com.cloudinary.transformation.background.GradientDirection
import org.junit.Test

class BackgroundTest {
    @Test
    fun testBackgroundColor() {
        cldAssert("blue", Background.color(Color.BLUE))
        cldAssert("rgb:9090ff", Background.color(Color.Rgb("9090ff")))
    }

    @Test
    fun testBackgroundBorder() {
        cldAssert("auto:border", Background.border())

        cldAssert(
            "auto:border_contrast", Background.border {
                contrast()
            }
        )
    }

    @Test
    fun testBackgroundPredominant() {
        cldAssert("auto:predominant", Background.predominant())
        cldAssert("auto:predominant_contrast", Background.predominant {
            contrast()
        })
    }

    @Test
    fun testBackgroundGradient() {
        // predominant
        cldAssert(
            "auto:predominant_gradient",
            Background.predominantGradient()
        )

        cldAssert("auto:predominant_gradient:2",
            Background.predominantGradient {
                gradientColors(2)
            }
        )

        cldAssert("auto:predominant_gradient:diagonal_desc",
            Background.predominantGradient {
                gradientDirection(GradientDirection.DIAGONAL_DESC)
            })

        cldAssert("auto:predominant_gradient:4:diagonal_asc",
            Background.predominantGradient {
                gradientDirection(GradientDirection.DIAGONAL_ASC)
                gradientColors(4)
            })

        cldAssert("auto:predominant_gradient:4:horizontal:palette_red_green_blue",
            Background.predominantGradient {
                gradientDirection(GradientDirection.HORIZONTAL)
                gradientColors(4)

                palette(Color.RED, Color.GREEN, Color.BLUE)
            })

        cldAssert("auto:predominant_gradient:vertical:palette_red_green_blue",
            Background.predominantGradient {
                gradientDirection(GradientDirection.VERTICAL)
                palette(Color.RED, Color.GREEN, Color.BLUE)
            })

        cldAssert("auto:predominant_gradient:palette_red_green_blue",
            Background.predominantGradient {
                palette(Color.RED, Color.GREEN, Color.BLUE)
            })


        // border
        cldAssert(
            "auto:border_gradient",
            Background.borderGradient()
        )

        cldAssert("auto:border_gradient:2",
            Background.borderGradient {
                gradientColors(2)
            }
        )

        cldAssert("auto:border_gradient:diagonal_desc",
            Background.borderGradient {
                gradientDirection(GradientDirection.DIAGONAL_DESC)
            })

        cldAssert("auto:border_gradient:4:diagonal_desc",
            Background.borderGradient {
                gradientDirection(GradientDirection.DIAGONAL_DESC)
                gradientColors(4)
            })

        cldAssert("auto:border_gradient:4:diagonal_desc:palette_red_green_blue",
            Background.borderGradient {
                gradientDirection(GradientDirection.DIAGONAL_DESC)
                gradientColors(4)
                palette(Color.RED, Color.GREEN, Color.BLUE)
            })

        cldAssert("auto:border_gradient:vertical:palette_red_green_blue",
            Background.borderGradient {
                gradientDirection(GradientDirection.VERTICAL)
                palette(Color.RED, Color.GREEN, Color.BLUE)
            })

        cldAssert("auto:border_gradient:palette_red_green_blue",
            Background.borderGradient {
                palette(Color.RED, Color.GREEN, Color.BLUE)
            })
    }
}