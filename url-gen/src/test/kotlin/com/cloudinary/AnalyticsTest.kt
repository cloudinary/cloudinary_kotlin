package com.cloudinary

import org.junit.Test
import kotlin.test.assertEquals

class AnalyticsTest {
    @Test
    fun testAnalyticsStringGeneration() {
        var s = generateAnalyticsSignature("1.24.0", KotlinVersion(12, 0))
        assertEquals("AHAlhAM0", s)

        s = generateAnalyticsSignature("1.24.0-beta.6", KotlinVersion(12, 0))
        assertEquals("AHAlhAM0", s)
    }
}