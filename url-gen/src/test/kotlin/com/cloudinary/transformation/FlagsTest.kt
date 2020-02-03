package com.cloudinary.transformation

import com.cloudinary.cldAssert
import org.junit.Test

class FlagsTest {
    @Test
    fun testFlags() {
        cldAssert("fl_progressive", GenericAction(FlagsParam(FlagKey.Progressive())))
        cldAssert(
            "fl_progressive:none",
            GenericAction(FlagsParam(FlagKey.Progressive(ProgressiveMode.None)))
        )
        cldAssert(
            "fl_progressive:semi",
            GenericAction(FlagsParam(FlagKey.Progressive(ProgressiveMode.Semi)))
        )
        cldAssert(
            "fl_progressive:steep",
            GenericAction(FlagsParam(FlagKey.Progressive(ProgressiveMode.Steep)))
        )
    }
}