package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.gravity.*
import org.junit.Test

class GravityTest {
    @Test
    fun testGravity() {
        cldAssert("cat:bird", Gravity.focusOn(FocusOn.cat(), FocusOn.bird()))
        cldAssert("cat:bird:auto:microwave_30:bottle_avoid", Gravity.focusOn(FocusOn.cat(), FocusOn.bird()) {
            fallbackGravity(Gravity.autoGravity(FocusOn.microwave().weight(30), FocusOn.bottle().avoid()))
        })
        cldAssert("west", Gravity.west())
    }

    @Test
    fun testAutoGravity() {
        cldAssert("auto", Gravity.autoGravity())
        cldAssert("auto:face", Gravity.autoGravity(FocusOn.face()))
        cldAssert("auto:cat", Gravity.autoGravity(FocusOn.cat()))
        cldAssert("auto:cat:dog", Gravity.autoGravity(FocusOn.cat(), FocusOn.dog()))
        cldAssert(
            "auto:cat_30:dog_avoid:bird", Gravity.autoGravity(
                FocusOn.cat().weight(30),
                FocusOn.dog().avoid(),
                FocusOn.bird()
            )
        )
        cldAssert("auto:cat_30:dog_avoid",
            Gravity.autoGravity(FocusOn.cat().weight(30)) {
                autoFocus(AutoFocus.focusOn(FocusOn.dog()) { avoid() })
            }
        )
    }
}

