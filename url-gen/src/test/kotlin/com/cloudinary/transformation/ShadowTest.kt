package com.cloudinary.transformation

import com.cloudinary.cldAssertEqualsAsString
import com.cloudinary.testParam
import org.junit.Test

class ShadowTest {
    @Test
    fun testShadow() {
        cldAssertEqualsAsString("e_shadow,test_param", Transformation().shadow(Shadow.Builder().build().add(testParam)))
        cldAssertEqualsAsString("e_shadow", Transformation().shadow())
        cldAssertEqualsAsString("e_shadow:50", Transformation().shadow { strength(50) })
        cldAssertEqualsAsString(
            "co_green,e_shadow:50",
            Transformation().shadow { strength(50).color(color { named("green") }) })
        cldAssertEqualsAsString(
            "co_green,e_shadow:50,x_20,y_-20",
            Transformation().shadow { strength(50).color(color { named("green") }).x(20).y(-20) }
        )
    }
}
