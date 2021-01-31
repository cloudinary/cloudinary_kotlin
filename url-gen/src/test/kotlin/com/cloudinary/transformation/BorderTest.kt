package com.cloudinary.transformation

import com.cloudinary.cldAssert
import org.junit.Test

class BorderTest {
    @Test
    fun testBorder() {
        cldAssert(
            "bo_4px_solid_black",
            Transformation().border {
                color(Color.BLACK)
                width(4)
            })

        cldAssert(
            "bo_3px_solid_rgb:00390b",
            Transformation().border(Border.solid {
                color(Color.rgb("00390b"))
                width(3)
            })
        )

        cldAssert(
            "bo_3px_solid_rgb:00390b",
            Transformation().border { color(Color.rgb("00390b")).width(3) })

        cldAssert(
            "bo_6px_solid_rgb:00390b60",
            Transformation().border { color(Color.rgb("00390b60")).width(6) })
    }

    @Test
    fun testBorderWithRoundCorners() {
        cldAssert(
            "bo_4px_solid_black,r_20:30",
            Transformation().border {
                color(Color.BLACK)
                width(4)
                roundCorners(RoundCorners.Companion.byRadius(20, 30))
            })

        cldAssert(
            "bo_4px_solid_black,r_20:30",
            Transformation().border {
                color(Color.BLACK)
                width(4)
                roundCorners(RoundCorners.Companion.byRadius(20, 30))
            })

        cldAssert(
            "bo_4px_solid_black,r_max",
            Transformation().border {
                color(Color.BLACK)
                width(4)
                roundCorners(RoundCorners.max())
            })
    }
}