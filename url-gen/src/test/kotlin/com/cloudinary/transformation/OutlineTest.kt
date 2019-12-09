package com.cloudinary.transformation

import com.cloudinary.cldAssertEqualsAsString
import com.cloudinary.testParam
import org.junit.Test

class OutlineTest {
    @Test
    fun testOutline() {
        cldAssertEqualsAsString("e_outline", Transformation().outline())
        cldAssertEqualsAsString(
            "e_outline,test_param",
            Transformation().outline(Outline.Builder().build().add(testParam))
        )
        cldAssertEqualsAsString("e_outline:inner_fill", Transformation().outline { setMode(OutlineMode.INNER_FILL) })
        cldAssertEqualsAsString(
            "co_red,e_outline:inner_fill:5",
            Transformation().outline {
                setMode(OutlineMode.INNER_FILL)
                setWidth(5)
                setColor(color { named("red") })
            })

        cldAssertEqualsAsString(
            "co_red,e_outline:inner_fill:5:200",
            Transformation().outline {
                setMode(OutlineMode.INNER_FILL)
                setBlur(200)
                setWidth(5)
                setColor(color { named("red") })
            })

        cldAssertEqualsAsString(
            "co_red,e_outline:inner_fill:5:200",
            Transformation().outline {
                setMode(OutlineMode.INNER_FILL)
                setBlur(200)
                setWidth(5)
                setColor("red")
            })
    }
}