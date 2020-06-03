package com.cloudinary

import com.cloudinary.util.splitToFiles
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File


private val testFile = File(UploaderTest::class.java.getResource("/old_logo.png").file)


class UtilTest {
    @Rule
    @JvmField
    val folder = TemporaryFolder()


    @Test
    fun testSplitFile() {

        val path = folder.newFolder()
        val originalLength = testFile.length().toInt()
        val files = testFile.inputStream().splitToFiles(512, path)
        val partsLength = files.sumBy { it.length().toInt() }

        assertEquals(originalLength, partsLength)
    }
}