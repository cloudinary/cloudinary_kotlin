package com.cloudinary.transformation

import com.cloudinary.cldAssertEqualsAsString
import com.cloudinary.testParam
import org.junit.Test

class BorderTest {
    @Test
    fun testBorder() {
        cldAssertEqualsAsString(
            "bo_4px_solid_black,test_param",
            Border.Builder().setColor(ColorValue.Builder().named("black").build()).setWidth(4).build().add(testParam)
        )

        cldAssertEqualsAsString(
            "bo_4px_solid_black",
            Transformation().border { setColor { named("black") }.setWidth(4) })

        cldAssertEqualsAsString(
            "bo_3px_solid_rgb:00390b",
            Transformation().border { setColor { fromRGB("00390b") }.setWidth(3) })

        cldAssertEqualsAsString(
            "bo_6px_solid_rgb:00390b60",
            Transformation().border { setColor { fromRGB("00390b60") }.setWidth(6) })
    }
}