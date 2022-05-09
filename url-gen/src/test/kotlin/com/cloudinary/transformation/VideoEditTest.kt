package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.effect.Effect
import com.cloudinary.transformation.expression.Expression
import com.cloudinary.transformation.videoedit.Concatenate
import com.cloudinary.transformation.videoedit.Transition
import com.cloudinary.transformation.videoedit.VideoEdit
import com.cloudinary.transformation.videoedit.VideoEdit.Companion.concatenate
import com.cloudinary.transformation.videoedit.VideoEdit.Companion.trim
import com.cloudinary.transformation.videoedit.Volume
import org.junit.Test

class VideoEditTest {

    @Test
    fun testTrim() {
        cldAssert("so_2.63", trim {
            startOffset(2.63f)
        })
        cldAssert("so_35.0p", trim { startOffset("35.0p") })

        cldAssert("eo_auto", trim { endOffset("auto") })
        cldAssert("eo_2.63", trim { endOffset(2.63f) })
        cldAssert("eo_35.0p", trim { endOffset("35.0p") })
        cldAssert("so_du_div_3", VideoEdit.trim {
            startOffset(Expression("duration / 3"))
        })
        cldAssert("eo_du_div_3", VideoEdit.trim {
            endOffset(Expression("duration / 3"))
        })
        cldAssert("du_2.63", trim { duration(2.63f) })
        cldAssert("du_35.0p", trim { duration("35.0p") })

        cldAssert("du_2.63,so_3.0", trim {
            startOffset(3.0f)
            duration(2.63f)
        })
        cldAssert("du_35.0p,so_auto", trim {
            startOffset("auto")
            duration("35.0p")
        })
    }

    @Test
    fun testPreview() {
        cldAssert("e_preview:duration_12.0:max_seg_3:min_seg_dur_3", VideoEdit.preview {
            duration(12f)
            maximumSegments(3)
            minimumSegmentDuration(3)
        })
    }

    @Test
    fun testPreviewAsExpression() {
        cldAssert("e_preview:duration_2.0", Expression.expression(VideoEdit.preview {
            duration(2f)
        }.toString()))
    }

    @Test
    fun testConcatenate() {

        cldAssert(
            "fl_splice,l_video:dog/e_noise:30/du_7.0/fl_layer_apply",
            concatenate(Concatenate.videoSource("dog") {
                transformation {
                    effect(Effect.noise {
                        level(30)
                    })
                    videoEdit(trim { duration(7f) })
                }
            })
        )

        cldAssert("fl_splice,l_video:dog/fl_layer_apply,so_0",
            concatenate(Concatenate.videoSource("dog")) {
                prepend()
            }
        )

        cldAssert("l_video:dog/e_transition,l_video:transition1/fl_layer_apply/fl_layer_apply",
            concatenate(Concatenate.videoSource("dog")) {
                transition(Transition.videoSource("transition1"))
            }
        )

        cldAssert("du_5.0,l_sample/e_transition,l_video:transition1/fl_layer_apply/fl_layer_apply",
            concatenate(Concatenate.imageSource("sample")) {
                transition(Transition.videoSource("transition1"))
                duration(5f)
            }
        )
    }

    @Test
    fun testVolume() {
        cldAssert("e_volume:50", VideoEdit.volume(50))
        cldAssert("e_volume:50", VideoEdit.volume(Volume.byPercent(50)))
        cldAssert("e_volume:10db", VideoEdit.volume(Volume.byDecibels(10)))
        cldAssert("e_volume:mute", VideoEdit.volume(Volume.mute()))
    }

    @Test
    fun testWaveform() {
        cldAssert("f_jpg,fl_waveform", VideoEdit.waveform(Format.jpg()))

        cldAssert(
            "b_white,co_black,f_png,fl_waveform", VideoEdit.waveform(Format.png()) {
                color(Color.BLACK)
                background(Color.WHITE)
            }
        )
    }
}
