package com.cloudinary

import com.cloudinary.transformation.Param
import com.cloudinary.transformation.ParamValue
import org.junit.Assert

internal fun cldAssert(expected: String, actual: Any) =
    Assert.assertEquals(expected, actual.toString().also { println(it) })

internal val testParam = Param("test_param", "test", ParamValue("param"))