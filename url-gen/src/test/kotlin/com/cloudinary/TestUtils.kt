package com.cloudinary

import org.junit.Assert

internal fun cldAssert(expected: String, actual: Any) =
    Assert.assertEquals(expected, actual.toString().also { println(it) })