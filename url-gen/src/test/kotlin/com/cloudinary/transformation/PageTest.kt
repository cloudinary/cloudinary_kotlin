package com.cloudinary.transformation


import com.cloudinary.cldAssert
import com.cloudinary.transformation.SmartObject.Companion.number
import org.junit.Test

class PageTest {
    @Test
    fun testExtract() {
        cldAssert("pg_1;4;7;8", Transformation().getPage("1;4;7;8"))
        cldAssert("pg_7", Transformation().getPage(7))
        cldAssert("pg_name:first", Transformation().getLayer(PsdLayer.names("first")))
        cldAssert("pg_name:first;second", Transformation().getLayer(PsdLayer.names("first", "second")))
        cldAssert("pg_embedded:3", Transformation().getSmartObject(number(3)))
        cldAssert(
            "pg_embedded:name:first;second",
            Transformation().getSmartObject(SmartObject.names("first", "second"))
        )
    }
}