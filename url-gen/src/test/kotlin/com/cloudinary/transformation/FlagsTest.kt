package com.cloudinary.transformation

import com.cloudinary.cldAssertEqualsAsString
import org.junit.Test

class FlagsTest {
    @Test
    fun testFlags() {
        cldAssertEqualsAsString("fl_progressive", GenericAction(FlagsParam(FlagKey.PROGRESSIVE())))
        cldAssertEqualsAsString(
            "fl_progressive:none",
            GenericAction(FlagsParam(FlagKey.PROGRESSIVE(ProgressiveMode.NONE)))
        )
        cldAssertEqualsAsString(
            "fl_progressive:semi",
            GenericAction(FlagsParam(FlagKey.PROGRESSIVE(ProgressiveMode.SEMI)))
        )
        cldAssertEqualsAsString(
            "fl_progressive:steep",
            GenericAction(FlagsParam(FlagKey.PROGRESSIVE(ProgressiveMode.STEEP)))
        )
    }
}