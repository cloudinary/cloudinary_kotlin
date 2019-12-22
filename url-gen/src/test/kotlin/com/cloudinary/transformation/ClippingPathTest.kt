package com.cloudinary.transformation

import com.cloudinary.cldAssertEqualsAsString
import com.cloudinary.testParam
import org.junit.Test

class ClippingPathTest {
    @Test
    fun testClippingPath() {
        cldAssertEqualsAsString("fl_clip,test_param", clippingPath().add(testParam))
        cldAssertEqualsAsString("fl_clip", clippingPath())
        cldAssertEqualsAsString("fl_clip,pg_4", clippingPath { page(4) })
        cldAssertEqualsAsString("fl_clip,pg_id", clippingPath { path("id") })
        cldAssertEqualsAsString("fl_clip_evenodd", clippingPath { evenOdd(true) })
        cldAssertEqualsAsString("fl_clip_evenodd,pg_4", clippingPath { evenOdd(true).page(4) })
        cldAssertEqualsAsString("fl_clip_evenodd,pg_id", clippingPath { evenOdd(true).path("id") })
    }
}