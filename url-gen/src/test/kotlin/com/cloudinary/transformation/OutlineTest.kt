package com.cloudinary.transformation

import com.cloudinary.cldAssert
import org.junit.Test

class OutlineTest {
    @Test
    fun testOutline() {
        cldAssert("e_outline", Transformation().outline())
        cldAssert(
            "e_outline",
            Transformation().outline(Outline.Builder().build())
        )
        cldAssert("e_outline:inner_fill", Transformation().outline { mode(OutlineMode.INNER_FILL) })
        cldAssert(
            "co_red,e_outline:inner_fill:5",
            Transformation().outline {
                mode(OutlineMode.INNER_FILL)
                width(5)
                color(Color.RED)
            })

        cldAssert(
            "co_red,e_outline:inner_fill:5:200",
            Transformation().outline {
                mode(OutlineMode.INNER_FILL)
                blur(200)
                width(5)
                color(Color.RED)
            })

        cldAssert(
            "co_red,e_outline:inner_fill:5:200",
            Transformation().outline {
                mode(OutlineMode.INNER_FILL)
                blur(200)
                width(5)
                color("red")
            })
    }
}