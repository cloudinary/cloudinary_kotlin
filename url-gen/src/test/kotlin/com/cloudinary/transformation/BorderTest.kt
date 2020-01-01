package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.testParam
import org.junit.Test

class BorderTest {
    @Test
    fun testBorder() {
        cldAssert(
            "bo_4px_solid_black,test_param",
            Border.Builder().color(ColorValue.Builder().named("black").build()).width(4).build().add(testParam)
        )

        cldAssert(
            "bo_4px_solid_black",
            Transformation().border { color { named("black") }.width(4) })

        cldAssert(
            "bo_3px_solid_rgb:00390b",
            Transformation().border { color { fromRGB("00390b") }.width(3) })

        cldAssert(
            "bo_6px_solid_rgb:00390b60",
            Transformation().border { color { fromRGB("00390b60") }.width(6) })
    }
}