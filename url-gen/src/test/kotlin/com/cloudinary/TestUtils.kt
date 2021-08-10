package com.cloudinary

import org.junit.Assert
import kotlin.test.fail

internal fun cldAssert(expected: String, actual: Any) =
    Assert.assertEquals(expected, actual.toString().also { println(it) })

internal fun cldAssertContains(mainString: String, strToContain: String) {
    if (!mainString.contains(strToContain)) {
        fail("$strToContain not contained within $mainString");
    }
}
