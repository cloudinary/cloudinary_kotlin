package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.testParam
import com.cloudinary.transformation.Extract.Companion.frame
import com.cloudinary.transformation.Extract.Companion.layers
import com.cloudinary.transformation.Extract.Companion.pages
import com.cloudinary.transformation.Extract.Companion.smartObject
import org.junit.Test

class ExtractTest {
    @Test
    fun testExtract() {
        cldAssert("pg_1;4;7;8,test_param", pages(1, 4, 7, 8).add(testParam))
        cldAssert("pg_1;4;7;8,test_param", pages(1, 4, 7, 8).add(testParam))
        cldAssert("pg_3;5-7;9-", pages { page(3).page(5, 7).page(9, null) })
        cldAssert("pg_7", frame(7))
        cldAssert("pg_name:first", layers("first"))
        cldAssert("pg_name:first;second", layers("first", "second"))
        cldAssert("pg_embedded:3", smartObject(3))
        cldAssert("pg_embedded:name:first;second", smartObject("first", "second"))

    }
}