package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.testParam
import com.cloudinary.transformation.video.DeShakeFactor
import com.cloudinary.transformation.video.OffsetValue.Companion.percent
import com.cloudinary.transformation.video.OffsetValue.Companion.seconds
import com.cloudinary.transformation.video.Video
import org.junit.Test

class VideoTest {
    @Test
    fun testDelay() {
        cldAssert("dl_20,test_param", Video.delay(20).add(testParam))
        cldAssert("dl_20", Video.delay(20))
    }

    @Test
    fun testLoop() {
        cldAssert("e_loop", Video.loop())
        cldAssert("e_loop:2", Video.loop(2))
        cldAssert("e_loop:2", Video.loop { additionalLoops(2) })
    }

    @Test
    fun testFade() {
        cldAssert("e_fade", Video.fade())
        cldAssert("e_fade:2000", Video.fade(2000))
        cldAssert("e_fade:2000", Video.fade { milliseconds(2000) })
    }

    @Test
    fun testStartOffset() {
        cldAssert("so_auto", Video.startOffsetAuto())
        cldAssert("so_2.63", Video.startOffset(seconds(2.63f)))
        cldAssert("so_35.0p", Video.startOffset(percent(35f)))
    }

    @Test
    fun testEndOffset() {
        cldAssert("eo_5.33", Video.endOffset(seconds(5.33f)))
        cldAssert("eo_75.0p", Video.endOffset(percent(75f)))
    }

    @Test
    fun testDuration() {
        cldAssert("du_6.12", Video.duration(seconds(6.12f)))
        cldAssert("du_60.0p", Video.duration(percent(60f)))
    }

    @Test
    fun testOffset() {
        cldAssert("eo_3.21,so_2.66", Video.offset { start(seconds(2.66f)).end(seconds(3.21f)) })
        cldAssert("eo_70.0p,so_35.0p", Video.offset { start(percent(35f)).end(percent(70f)) })
    }

    @Test
    fun testReverse() {
        cldAssert("e_reverse", Video.reverse())
    }

    @Test
    fun testBoomerang() {
        cldAssert("e_boomerang", Video.boomerang())
    }

    @Test
    fun testPreview() {
        cldAssert("e_preview", Video.preview())
        cldAssert("e_preview:duration_12", Video.preview(12))
        cldAssert("e_preview:duration_12", Video.preview { seconds(12) })
    }

    @Test
    fun testNoise() {
        cldAssert("e_noise", Video.noise())
        cldAssert("e_noise:10", Video.noise(10))
        cldAssert("e_noise:10", Video.noise { level(10) })
    }

    @Test
    fun testMakeTransparent() {
        val color = color { named("white") }
        cldAssert("co_white,e_make_transparent", Video.makeTransparent(color))
        cldAssert("co_white,e_make_transparent:10", Video.makeTransparent(color) { level(10) })
    }

    @Test
    fun testDeshake() {
        cldAssert("e_deshake", Video.deshake())
        cldAssert("e_deshake:16", Video.deshake(DeShakeFactor.PX_16))
        cldAssert("e_deshake:16", Video.deshake { factor(DeShakeFactor.PX_16) })
    }

    @Test
    fun testWaveform() {
        cldAssert("fl_waveform", Video.waveform())
    }

    @Test
    fun accelerate() {
        cldAssert("e_accelerate", Video.accelerate())
        cldAssert("e_accelerate:100", Video.accelerate(100))
        cldAssert("e_accelerate:100", Video.accelerate { percent(100) })
    }
}