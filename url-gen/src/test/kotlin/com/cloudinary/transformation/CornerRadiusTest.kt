package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.expression.Expression
import org.junit.Test

class CornerRadiusTest {
    @Test
    fun testCornerRadius() {
        cldAssert("r_20", Transformation().roundCorners(20))
        cldAssert("r_20:30", Transformation().roundCorners(20, 30))
        cldAssert("r_20:30", Transformation().roundCorners(RoundCorners.Companion.byRadius(20, 30)))
        cldAssert("r_20:30:40", Transformation().roundCorners(20, 30, 40))
        cldAssert("r_20:30:40:50", Transformation().roundCorners(20, 30, 40, 50))
        cldAssert("r_max", Transformation().roundCorners(RoundCorners.max()))
        cldAssert("r_w_mul_2", Transformation().roundCorners(RoundCorners.Companion.byRadius(Expression("width * 2"))))
    }
}