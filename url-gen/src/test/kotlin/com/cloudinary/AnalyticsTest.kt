package com.cloudinary

import org.junit.Test
import kotlin.test.assertEquals

class AnalyticsTest {
    @Test
    fun testAnalyticsStringGeneration() {
        var s = generateAnalyticsSignature("1.24.0", KotlinVersion(12, 0))
        assertEquals("DAHAlhAMZAA0", s)

        s = generateAnalyticsSignature("1.24.0-beta.6", KotlinVersion(12, 0))
        assertEquals("DAHAlhAMZAA0", s)

        s = generateAnalyticsSignature("1.24.0-beta.6", KotlinVersion(12, 0), "A", "android13")
        assertEquals("DAHAlhAMANA0", s)

    }
}