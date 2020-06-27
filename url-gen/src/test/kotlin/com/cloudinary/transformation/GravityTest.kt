package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.gravity.*
import org.junit.Test

class GravityTest {
    @Test
    fun testGravity() {
        cldAssert("g_cat:bird", Gravity.objects(ObjectGravity.CAT, ObjectGravity.BIRD))
        cldAssert("g_face", Gravity.face())
        cldAssert("g_west", Gravity.west())
        cldAssert("g_ocr_text", Gravity.ocrText())

    }

    @Test
    fun testAutoGravity() {
        cldAssert("g_auto", Gravity.auto())
        cldAssert("g_auto:classic", Gravity.autoClassic())
        cldAssert("g_auto:face", Gravity.auto(FocalPoint.FACE))
        cldAssert("g_auto:cat", Gravity.auto(ObjectGravity.CAT))
        cldAssert("g_auto:cat:dog", Gravity.auto(ObjectGravity.CAT, ObjectGravity.DOG))
        cldAssert(
            "g_auto:cat_30:dog_avoid:bird", Gravity.auto(
                ObjectGravity.CAT.weight(30),
                ObjectGravity.DOG.avoid(),
                ObjectGravity.BIRD
            )
        )
    }
}

