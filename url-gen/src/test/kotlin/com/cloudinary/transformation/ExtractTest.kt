package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.testParam
import com.cloudinary.transformation.Page.Companion.embedded
import com.cloudinary.transformation.Page.Companion.layers
import com.cloudinary.transformation.Page.Companion.pages
import org.junit.Test

class ExtractTest {
    @Test
    fun testExtract() {
        cldAssert("pg_1;4;7;8,test_param", pages(1, 4, 7, 8).add(testParam))
        cldAssert("pg_3;5-7;9-", pages { page(3).page(5, 7).page(9, null) })
        cldAssert("pg_-7;9-", pages { page(null, 7).page(9, null) })
        cldAssert("pg_name:first", layers("first"))
        cldAssert("pg_name:first;second", layers("first", "second"))
        cldAssert("pg_embedded:3", embedded(3))
        cldAssert("pg_embedded:name:first", embedded("first"))
    }
}