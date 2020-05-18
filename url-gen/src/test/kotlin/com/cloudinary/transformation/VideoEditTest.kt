package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.videoedit.VideoEdit
import org.junit.Test

class VideoEditTest {

    @Test
    fun testTrim() {
        // TODO test duration, end offset, combinations
        cldAssert("so_auto", VideoEdit.trim { startOffset("auto") })
        cldAssert("so_2.63", VideoEdit.trim { startOffset(2.63) })
        cldAssert("so_35.0p", VideoEdit.trim { startOffset("35.0p") })
    }
}