package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.background.Background
import com.cloudinary.transformation.background.GradientDirection
import org.junit.Test

class BackgroundTest {
    @Test
    fun testBackgroundColor() {
        cldAssert("blue", Background.color(Color.BLUE))
        cldAssert("rgb:9090ff", Background.color(Color.rgb("9090ff")))
    }

    @Test
    fun testBlurredBackground() {
        cldAssert("blurred:300:15", Background.blurred {
            brightness(15)
            intensity(300)
        })

        cldAssert("blurred:300", Background.blurred {
            intensity(300)
        })

        cldAssert("blurred", Background.blurred())
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
                gradientDirection(GradientDirection.diagonalDesc())
            })

        cldAssert("auto:predominant_gradient:4:diagonal_asc",
            Background.predominantGradient {
                gradientDirection(GradientDirection.diagonalAsc())
                gradientColors(4)
            })

        cldAssert("auto:predominant_gradient:4:horizontal:palette_red_green_blue",
            Background.predominantGradient {
                gradientDirection(GradientDirection.horizontal())
                gradientColors(4)

                palette(Color.RED, Color.GREEN, Color.BLUE)
            })

        cldAssert("auto:predominant_gradient:vertical:palette_red_green_blue",
            Background.predominantGradient {
                gradientDirection(GradientDirection.vertical())
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
                gradientDirection(GradientDirection.diagonalDesc())
            })

        cldAssert("auto:border_gradient:4:diagonal_desc",
            Background.borderGradient {
                gradientDirection(GradientDirection.diagonalDesc())
                gradientColors(4)
            })

        cldAssert("auto:border_gradient:4:diagonal_desc:palette_red_green_blue",
            Background.borderGradient {
                gradientDirection(GradientDirection.diagonalDesc())
                gradientColors(4)
                palette(Color.RED, Color.GREEN, Color.BLUE)
            })

        cldAssert("auto:border_gradient:vertical:palette_red_green_blue",
            Background.borderGradient {
                gradientDirection(GradientDirection.vertical())
                palette(Color.RED, Color.GREEN, Color.BLUE)
            })

        cldAssert("auto:border_gradient:palette_red_green_blue",
            Background.borderGradient {
                palette(Color.RED, Color.GREEN, Color.BLUE)
            })
    }
}