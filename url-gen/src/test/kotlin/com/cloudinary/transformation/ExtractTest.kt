package com.cloudinary.transformation

import com.cloudinary.cldAssertEqualsAsString
import com.cloudinary.testParam
import org.junit.Test

class ExtractTest {
    @Test
    fun testExtract() {
        cldAssertEqualsAsString("pg_1;4;7;8,test_param", pages(1, 4, 7, 8).add(testParam))
        cldAssertEqualsAsString("pg_3;5-7;9-", pages { page(3).page(5, 7).page(9, null) })
        cldAssertEqualsAsString("pg_-7;9-", pages { page(null, 7).page(9, null) })
        cldAssertEqualsAsString("pg_name:first", layers("first"))
        cldAssertEqualsAsString("pg_name:first;second", layers("first", "second"))
        cldAssertEqualsAsString("pg_embedded:3", embedded(3))
        cldAssertEqualsAsString("pg_embedded:name:first", embedded("first"))
    }
}