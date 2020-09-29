package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.background.AutoBackground.GradientDirection
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
            "b_auto:border_contrast", Background.border {
                contrast()
            }
        )
    }

    @Test
    fun testBackgroundPredominant() {
        cldAssert("b_auto:predominant", Background.predominant())
        cldAssert("b_auto:predominant_contrast", Background.predominant {
            contrast()
        })
    }

    @Test
    fun testBackgroundGradient() {
        // predominant
        cldAssert("b_auto:predominant_gradient",
            Background.predominant {
                gradient()
            }
        )

        cldAssert("b_auto:predominant_gradient:2",
            Background.predominant {
                gradient {
                    colors(2)
                }
            }
        )

        cldAssert("b_auto:predominant_gradient:diagonal_desc",
            Background.predominant {
                gradient {
                    direction(GradientDirection.DIAGONAL_DESC)
                }
            })

        cldAssert("b_auto:predominant_gradient:4:diagonal_asc",
            Background.predominant {
                gradient {
                    direction(GradientDirection.DIAGONAL_ASC)
                    colors(4)
                }
            })

        cldAssert("b_auto:predominant_gradient:4:horizontal:palette_red_green_blue",
            Background.predominant {
                gradient {
                    direction(GradientDirection.HORIZONTAL)
                    colors(4)
                }
                palette(Color.RED, Color.GREEN, Color.BLUE)
            })

        cldAssert("b_auto:predominant_gradient:vertical:palette_red_green_blue",
            Background.predominant {
                gradient {
                    direction(GradientDirection.VERTICAL)
                }
                palette(Color.RED, Color.GREEN, Color.BLUE)
            })

        cldAssert("b_auto:predominant_gradient:palette_red_green_blue",
            Background.predominant {
                gradient()
                palette(Color.RED, Color.GREEN, Color.BLUE)
            })


        // border
        cldAssert("b_auto:border_gradient",
            Background.border {
                gradient()
            }
        )

        cldAssert("b_auto:border_gradient:2",
            Background.border {
                gradient {
                    colors(2)
                }
            }
        )

        cldAssert("b_auto:border_gradient:diagonal_desc",
            Background.border {
                gradient {
                    direction(GradientDirection.DIAGONAL_DESC)
                }
            })

        cldAssert("b_auto:border_gradient:4:diagonal_desc",
            Background.border {
                gradient {
                    direction(GradientDirection.DIAGONAL_DESC)
                    colors(4)
                }
            })

        cldAssert("b_auto:border_gradient:4:diagonal_desc:palette_red_green_blue",
            Background.border {
                gradient {
                    direction(GradientDirection.DIAGONAL_DESC)
                    colors(4)
                }
                palette(Color.RED, Color.GREEN, Color.BLUE)
            })

        cldAssert("b_auto:border_gradient:vertical:palette_red_green_blue",
            Background.border {
                gradient {
                    direction(GradientDirection.VERTICAL)
                }
                palette(Color.RED, Color.GREEN, Color.BLUE)
            })

        cldAssert("b_auto:border_gradient:palette_red_green_blue",
            Background.border {
                gradient()
                palette(Color.RED, Color.GREEN, Color.BLUE)
            })
    }
}