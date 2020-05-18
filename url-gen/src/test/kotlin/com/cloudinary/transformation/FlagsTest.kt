package com.cloudinary.transformation

import com.cloudinary.cldAssert
import org.junit.Test

class FlagsTest {
    @Test
    fun testFlags() {
        cldAssert("fl_progressive", GenericAction(FlagsParam(Flag.Progressive())))
        cldAssert(
            "fl_progressive:none",
            GenericAction(FlagsParam(Flag.Progressive(ProgressiveMode.None)))
        )
        cldAssert(
            "fl_progressive:semi",
            GenericAction(FlagsParam(Flag.Progressive(ProgressiveMode.Semi)))
        )
        cldAssert(
            "fl_progressive:steep",
            GenericAction(FlagsParam(Flag.Progressive(ProgressiveMode.Steep)))
        )
    }
}