package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.extract.Extract
import org.junit.Test

class ExtractTest {

    @Test
    fun testGetPage() {
        cldAssert("pg_3", Extract.getFrame { byNumber(3) })
        cldAssert("pg_1-3", Extract.getFrame { byRange(1, 3) })
        cldAssert("pg_1-3;5;7-", Extract.getFrame { byRange(1, 3).byNumber(5).byRange(7, null) })
    }

    @Test
    fun testGetFrame() {
        cldAssert("pg_3", Extract.getPage { byNumber(3) })
        cldAssert("pg_1-3", Extract.getPage { byRange(1, 3) })
        cldAssert("pg_1-3;5;7-", Extract.getPage { byRange(1, 3).byNumber(5).byRange(7, null) })
    }
}