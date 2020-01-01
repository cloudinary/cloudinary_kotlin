package com.cloudinary.transformation

import com.cloudinary.cldAssert
import org.junit.Test

class FlagsTest {
    @Test
    fun testFlags() {
        cldAssert("fl_progressive", GenericAction(FlagsParam(FlagKey.PROGRESSIVE())))
        cldAssert(
            "fl_progressive:none",
            GenericAction(FlagsParam(FlagKey.PROGRESSIVE(ProgressiveMode.NONE)))
        )
        cldAssert(
            "fl_progressive:semi",
            GenericAction(FlagsParam(FlagKey.PROGRESSIVE(ProgressiveMode.SEMI)))
        )
        cldAssert(
            "fl_progressive:steep",
            GenericAction(FlagsParam(FlagKey.PROGRESSIVE(ProgressiveMode.STEEP)))
        )
    }
}