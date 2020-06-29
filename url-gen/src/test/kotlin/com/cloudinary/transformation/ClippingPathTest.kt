package com.cloudinary.transformation

import com.cloudinary.cldAssert
import org.junit.Test

class ClippingPathTest {
    @Test
    fun testClippingPath() {
        cldAssert("fl_clip", clippingPath())
        cldAssert("fl_clip,pg_4", clippingPath { number(4) })
        cldAssert("fl_clip,pg_name:id", clippingPath { name("id") })
        cldAssert("fl_clip_evenodd", clippingPath { evenOdd() })
        cldAssert("fl_clip_evenodd,pg_4", clippingPath { evenOdd().number(4) })
        cldAssert("fl_clip_evenodd,pg_name:id", clippingPath { evenOdd().name("id") })
    }
}