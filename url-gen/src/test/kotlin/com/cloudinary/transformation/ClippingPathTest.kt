package com.cloudinary.transformation

import com.cloudinary.cldAssert
import org.junit.Test

class ClippingPathTest {
    @Test
    fun testClippingPath() {
        cldAssert("fl_clip", clippingPath())
        cldAssert("fl_clip,pg_4", clippingPath { index(4) })
        cldAssert("fl_clip,pg_id", clippingPath { path("id") })
        cldAssert("fl_clip_evenodd", clippingPath { evenOdd(true) })
        cldAssert("fl_clip_evenodd,pg_4", clippingPath { evenOdd(true).index(4) })
        cldAssert("fl_clip_evenodd,pg_id", clippingPath { evenOdd(true).path("id") })
    }
}