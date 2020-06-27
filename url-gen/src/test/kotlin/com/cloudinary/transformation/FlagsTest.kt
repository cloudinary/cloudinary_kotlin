package com.cloudinary.transformation

import com.cloudinary.cldAssert
import org.junit.Test

class FlagsTest {
    @Test
    fun testFlags() {
        cldAssert("fl_progressive", ParamsAction(FlagsParam(Flag.progressive())))
        cldAssert(
            "fl_progressive:none",
            ParamsAction(FlagsParam(Flag.progressive(ProgressiveMode.None)))
        )
        cldAssert(
            "fl_progressive:semi",
            ParamsAction(FlagsParam(Flag.progressive(ProgressiveMode.Semi)))
        )
        cldAssert(
            "fl_progressive:steep",
            ParamsAction(FlagsParam(Flag.progressive(ProgressiveMode.Steep)))
        )
    }
}