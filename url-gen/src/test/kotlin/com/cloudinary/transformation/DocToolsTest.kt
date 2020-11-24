package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.doctools.DocTools
import org.junit.Test

class DocToolsTest {
    @Test
    fun testGetPage() {
        cldAssert("pg_1;4;8", DocTools.getPage {
            byNumber(1)
            byNumber(4)
            byNumber(8)
        })

        cldAssert("pg_1;4-9;8;10-", DocTools.getPage {
            byNumber(1)
            byRange(4, 9)
            byNumber(8)
            byRange(10, null)
        })
    }
}