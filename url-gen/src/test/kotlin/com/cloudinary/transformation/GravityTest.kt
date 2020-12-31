package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.gravity.*
import org.junit.Test

class GravityTest {
    @Test
    fun testGravity() {
        cldAssert("cat:bird", Gravity.focusOn(FocusOn.CAT, FocusOn.BIRD))
        cldAssert("cat:bird:auto:microwave_30:bottle_avoid", Gravity.focusOn(FocusOn.CAT, FocusOn.BIRD) {
            fallbackGravity(Gravity.auto(FocusOn.MICROWAVE.weight(30), FocusOn.BOTTLE.avoid()))
        })
        cldAssert("west", Gravity.west())
        cldAssert("ocr_text", Gravity.ocr())
        cldAssert("ocr_text_avoid", Gravity.ocr { avoid() })

    }

    @Test
    fun testAutoGravity() {
        cldAssert("auto", Gravity.auto())
        cldAssert("auto:face", Gravity.auto(FocusOn.FACE))
        cldAssert("auto:cat", Gravity.auto(FocusOn.CAT))
        cldAssert("auto:cat:dog", Gravity.auto(FocusOn.CAT, FocusOn.DOG))
        cldAssert(
            "auto:cat_30:dog_avoid:bird", Gravity.auto(
                FocusOn.CAT.weight(30),
                FocusOn.DOG.avoid(),
                FocusOn.BIRD
            )
        )
        cldAssert("auto:cat_30:dog_avoid",
            Gravity.auto(FocusOn.CAT.weight(30)) {
                autoFocus(AutoFocus.focusOn(FocusOn.DOG) { avoid() })
            }
        )
    }
}

