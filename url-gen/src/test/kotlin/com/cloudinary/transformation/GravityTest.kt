package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.gravity.Gravity
import com.cloudinary.transformation.gravity.GravityObject
import com.cloudinary.transformation.gravity.avoid
import com.cloudinary.transformation.gravity.weight
import org.junit.Test

class GravityTest {
    @Test
    fun testGravity() {
        cldAssert("cat:bird", Gravity.objects(GravityObject.CAT, GravityObject.BIRD))
        cldAssert("cat:bird:auto:microwave_30:bottle_avoid", Gravity.objects(GravityObject.CAT, GravityObject.BIRD) {
            fallbackGravity(Gravity.auto(GravityObject.MICROWAVE.weight(30), GravityObject.BOTTLE.avoid()))
        })
        cldAssert("west", Gravity.west())
        cldAssert("ocr_text", Gravity.ocrText())

    }

    @Test
    fun testAutoGravity() {
        cldAssert("auto", Gravity.auto())
        cldAssert("auto:face", Gravity.auto(GravityObject.FACE))
        cldAssert("auto:cat", Gravity.auto(GravityObject.CAT))
        cldAssert("auto:cat:dog", Gravity.auto(GravityObject.CAT, GravityObject.DOG))
        cldAssert(
            "auto:cat_30:dog_avoid:bird", Gravity.auto(
                GravityObject.CAT.weight(30),
                GravityObject.DOG.avoid(),
                GravityObject.BIRD
            )
        )
    }
}

