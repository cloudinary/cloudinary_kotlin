package com.cloudinary

import org.junit.Assert

internal fun cldAssert(expected: String, actual: Any) =
    Assert.assertEquals(expected, actual.toString().also { println(it) })


internal fun cldAssert2(expected: String, actual: Any?) {
    val s1 = normalize(
        actual.toString().replace("http://res.cloudinary.com/nitzanj/image/upload/", "").replace("/sample", "")
            .replace("c_scale", "")
    )
    val s2 = normalize(expected)
    Assert.assertEquals(s2, s1)
}

private fun normalize(str: String) =
    str.replace("/", ",").split(",").filter { it.isNotBlank() }.sorted().joinToString(separator = ",")
