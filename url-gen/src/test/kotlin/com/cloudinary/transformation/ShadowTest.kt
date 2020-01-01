package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.testParam
import org.junit.Test

class ShadowTest {
    @Test
    fun testShadow() {
        cldAssert("e_shadow,test_param", Transformation().shadow(Shadow.Builder().build().add(testParam)))
        cldAssert("e_shadow", Transformation().shadow())
        cldAssert("e_shadow:50", Transformation().shadow { strength(50) })
        cldAssert(
            "co_green,e_shadow:50",
            Transformation().shadow { strength(50).color(color { named("green") }) })
        cldAssert(
            "co_green,e_shadow:50,x_20,y_-20",
            Transformation().shadow { strength(50).color(color { named("green") }).x(20).y(-20) }
        )
    }
}
