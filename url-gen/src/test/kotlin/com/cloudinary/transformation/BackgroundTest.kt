package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.background.Background
import com.cloudinary.transformation.background.GradientDirection
import com.cloudinary.transformation.background.PadBackground
import org.junit.Test

class BackgroundTest {
    @Test
    fun testBackgroundColor() {
        cldAssert("b_blue", Background.color(Color.BLUE))
        cldAssert("b_rgb:9090ff", Background.color(Color.Rgb("9090ff")))
    }

    @Test
    fun testBackgroundBorder() {
        cldAssert("b_auto:border", PadBackground.border())

        cldAssert(
            "b_auto:border_contrast", PadBackground.border {
                contrast()
            }
        )
    }

    @Test
    fun testBackgroundPredominant() {
        cldAssert("b_auto:predominant", PadBackground.predominant())
        cldAssert("b_auto:predominant_contrast", PadBackground.predominant {
            contrast()
        })
    }

    @Test
    fun testBackgroundGradient() {
        // predominant
        cldAssert("b_auto:predominant_gradient",
            PadBackground.predominant {
                gradient()
            }
        )

        cldAssert("b_auto:predominant_gradient:2",
            PadBackground.predominant {
                gradient {
                    colors(2)
                }
            }
        )

        cldAssert("b_auto:predominant_gradient:diagonal_desc",
            PadBackground.predominant {
                gradient {
                    direction(GradientDirection.DIAGONAL_DESC)
                }
            })

        cldAssert("b_auto:predominant_gradient:4:diagonal_asc",
            PadBackground.predominant {
                gradient {
                    direction(GradientDirection.DIAGONAL_ASC)
                    colors(4)
                }
            })

        cldAssert("b_auto:predominant_gradient:4:horizontal:palette_red_green_blue",
            PadBackground.predominant {
                gradient {
                    direction(GradientDirection.HORIZONTAL)
                    colors(4)
                }
                palette(Color.RED, Color.GREEN, Color.BLUE)
            })

        cldAssert("b_auto:predominant_gradient:vertical:palette_red_green_blue",
            PadBackground.predominant {
                gradient {
                    direction(GradientDirection.VERTICAL)
                }
                palette(Color.RED, Color.GREEN, Color.BLUE)
            })

        cldAssert("b_auto:predominant_gradient:palette_red_green_blue",
            PadBackground.predominant {
                gradient()
                palette(Color.RED, Color.GREEN, Color.BLUE)
            })


        // border
        cldAssert("b_auto:border_gradient",
            PadBackground.border {
                gradient()
            }
        )

        cldAssert("b_auto:border_gradient:2",
            PadBackground.border {
                gradient {
                    colors(2)
                }
            }
        )

        cldAssert("b_auto:border_gradient:diagonal_desc",
            PadBackground.border {
                gradient {
                    direction(GradientDirection.DIAGONAL_DESC)
                }
            })

        cldAssert("b_auto:border_gradient:4:diagonal_desc",
            PadBackground.border {
                gradient {
                    direction(GradientDirection.DIAGONAL_DESC)
                    colors(4)
                }
            })

        cldAssert("b_auto:border_gradient:4:diagonal_desc:palette_red_green_blue",
            PadBackground.border {
                gradient {
                    direction(GradientDirection.DIAGONAL_DESC)
                    colors(4)
                }
                palette(Color.RED, Color.GREEN, Color.BLUE)
            })

        cldAssert("b_auto:border_gradient:vertical:palette_red_green_blue",
            PadBackground.border {
                gradient {
                    direction(GradientDirection.VERTICAL)
                }
                palette(Color.RED, Color.GREEN, Color.BLUE)
            })

        cldAssert("b_auto:border_gradient:palette_red_green_blue",
            PadBackground.border {
                gradient()
                palette(Color.RED, Color.GREEN, Color.BLUE)
            })
    }
}