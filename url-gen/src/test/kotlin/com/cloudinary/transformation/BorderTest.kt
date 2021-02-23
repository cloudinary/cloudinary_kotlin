package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.RoundCorners.Companion.byRadius
import com.cloudinary.transformation.RoundCorners.Companion.max
import org.junit.Test

class BorderTest {
    @Test
    fun testBorder() {
        cldAssert(
            "bo_3px_solid_rgb:00390b",
            Transformation().border(Border.solid(3, Color.rgb("00390b")))
        )
    }

    @Test
    fun testBorderWithRoundCorners() {
        cldAssert(
            "bo_4px_solid_black,r_20:30",
            Transformation().border(Border.solid(4, Color.BLACK) {
                roundCorners(byRadius(20, 30))
            })
        )

        cldAssert(
            "bo_4px_solid_black,r_max",
            Transformation().border(Border.solid(4, Color.BLACK) {
                roundCorners(max())
            })
        )
    }
}