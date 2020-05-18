package com.cloudinary.transformation

import com.cloudinary.cldAssert
import org.junit.Test

class FlagsTest {
    @Test
    fun testFlags() {
        cldAssert("fl_progressive", ParamsAction(FlagsParam(Flag.Progressive())))
        cldAssert(
            "fl_progressive:none",
            ParamsAction(FlagsParam(Flag.Progressive(ProgressiveMode.None)))
        )
        cldAssert(
            "fl_progressive:semi",
            ParamsAction(FlagsParam(Flag.Progressive(ProgressiveMode.Semi)))
        )
        cldAssert(
            "fl_progressive:steep",
            ParamsAction(FlagsParam(Flag.Progressive(ProgressiveMode.Steep)))
        )
    }
}