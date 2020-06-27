package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.Transformation.Companion.transformation
import com.cloudinary.transformation.layer.Source
import com.cloudinary.transformation.videoedit.VideoEdit
import com.cloudinary.transformation.videoedit.VideoEdit.Companion.concatenate
import com.cloudinary.transformation.videoedit.VideoEdit.Companion.trim
import com.cloudinary.transformation.videoedit.Volume
import org.junit.Test

class VideoEditTest {

    @Test
    fun testTrim() {
        cldAssert("so_auto", trim { startOffset("auto") })
        cldAssert("so_2.63", trim { startOffset(2.63f) })
        cldAssert("so_35.0p", trim { startOffset("35.0p") })

        cldAssert("eo_auto", trim { endOffset("auto") })
        cldAssert("eo_2.63", trim { endOffset(2.63f) })
        cldAssert("eo_35.0p", trim { endOffset("35.0p") })

        cldAssert("du_2.63", trim { duration(2.63f) })
        cldAssert("du_35.0p", trim { duration("35.0p") })

        cldAssert("du_2.63,so_3", trim {
            startOffset(3)
            duration(2.63f)
        })
        cldAssert("du_35.0p,so_auto", trim {
            startOffset("auto")
            duration("35.0p")
        })
    }

    @Test
    fun testConcatenate() {
        cldAssert("l_video:dog/du_5,so_0/fl_layer_apply.splice",
            transformation {
                videoEdit(concatenate(Source.video("dog")) {
                    transformation {
                        videoEdit(trim {
                            startOffset(0)
                            duration(5)
                        })
                    }
                })
            })
    }

    @Test
    fun testVolume() {
        cldAssert("e_volume:50", VideoEdit.volume(50))
        cldAssert("e_volume:50", VideoEdit.volume(Volume.level(50)))
        cldAssert("e_volume:mute", VideoEdit.volume(Volume.mute()))
    }
}