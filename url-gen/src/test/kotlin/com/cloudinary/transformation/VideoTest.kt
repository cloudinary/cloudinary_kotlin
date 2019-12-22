package com.cloudinary.transformation

import com.cloudinary.cldAssertEqualsAsString
import com.cloudinary.testParam
import com.cloudinary.transformation.video.DeShakeFactor
import com.cloudinary.transformation.video.OffsetValue.Companion.percent
import com.cloudinary.transformation.video.OffsetValue.Companion.seconds
import com.cloudinary.transformation.video.Video
import org.junit.Test

class VideoTest {
    @Test
    fun testDelay() {
        cldAssertEqualsAsString("dl_20,test_param", Video.delay(20).add(testParam))
        cldAssertEqualsAsString("dl_20", Video.delay(20))
    }

    @Test
    fun testLoop() {
        cldAssertEqualsAsString("e_loop", Video.loop())
        cldAssertEqualsAsString("e_loop:2", Video.loop { additionalLoops(2) })
    }

    @Test
    fun testFade() {
        cldAssertEqualsAsString("e_fade", Video.fade())
        cldAssertEqualsAsString("e_fade:2000", Video.fade { milliseconds(2000) })
    }

    @Test
    fun testStartOffset() {
        cldAssertEqualsAsString("so_auto", Video.startOffsetAuto())
        cldAssertEqualsAsString("so_2.63", Video.startOffset(seconds(2.63f)))
        cldAssertEqualsAsString("so_35.0p", Video.startOffset(percent(35f)))
    }

    @Test
    fun testEndOffset() {
        cldAssertEqualsAsString("eo_5.33", Video.endOffset(seconds(5.33f)))
        cldAssertEqualsAsString("eo_75.0p", Video.endOffset(percent(75f)))
    }

    @Test
    fun testDuration() {
        cldAssertEqualsAsString("du_6.12", Video.duration(seconds(6.12f)))
        cldAssertEqualsAsString("du_60.0p", Video.duration(percent(60f)))
    }

    @Test
    fun testOffset() {
        cldAssertEqualsAsString("eo_3.21,so_2.66", Video.offset { start(seconds(2.66f)).end(seconds(3.21f)) })
        cldAssertEqualsAsString("eo_70.0p,so_35.0p", Video.offset { start(percent(35f)).end(percent(70f)) })
    }

    @Test
    fun testReverse() {
        cldAssertEqualsAsString("e_reverse", Video.reverse())
    }

    @Test
    fun testBoomerang() {
        cldAssertEqualsAsString("e_boomerang", Video.boomerang())
    }

    @Test
    fun testPreview() {
        cldAssertEqualsAsString("e_preview", Video.preview())
        cldAssertEqualsAsString("e_preview:duration_12", Video.preview { seconds(12) })
    }

    @Test
    fun testNoise() {
        cldAssertEqualsAsString("e_noise", Video.noise())
        cldAssertEqualsAsString("e_noise:10", Video.noise { level(10) })
    }

    @Test
    fun testMakeTransparent() {
        val color = color { named("white") }
        cldAssertEqualsAsString("co_white,e_make_transparent", Video.makeTransparent(color))
        cldAssertEqualsAsString("co_white,e_make_transparent:10", Video.makeTransparent(color) { level(10) })
    }

    @Test
    fun testDeshake() {
        cldAssertEqualsAsString("e_deshake", Video.deshake())
        cldAssertEqualsAsString("e_deshake:16", Video.deshake { factor(DeShakeFactor.PX_16) })
    }

    @Test
    fun testWaveform() {
        cldAssertEqualsAsString("fl_waveform", Video.waveform())
    }

    @Test
    fun accelerate() {
        cldAssertEqualsAsString("e_accelerate", Video.accelerate())
        cldAssertEqualsAsString("e_accelerate:100", Video.accelerate { percent(100) })
    }
}