package com.cloudinary.transformation

import com.cloudinary.cldAssertEqualsAsString
import com.cloudinary.transformation.adjust.*
import com.cloudinary.transformation.effect.ImproveMode
import org.junit.Test

class AdjustTest {
    @Test
    fun testAutoContrast() {
        cldAssertEqualsAsString("e_auto_contrast", Adjust.autoContrast())
        cldAssertEqualsAsString("e_auto_contrast:50", Adjust.autoContrast { setLevel(50) })
    }

    @Test
    fun testAutoBrightness() {
        cldAssertEqualsAsString("e_auto_brightness", Adjust.autoBrightness())
        cldAssertEqualsAsString("e_auto_brightness:70", Adjust.autoBrightness { setLevel(70) })
    }

    @Test
    fun testBrightness() {
        cldAssertEqualsAsString("e_brightness", Adjust.brightness())
        cldAssertEqualsAsString("e_brightness:70", Adjust.brightness { setLevel(70) })
    }

    @Test
    fun testBrightnessHSB() {
        cldAssertEqualsAsString("e_brightness_hsb", Adjust.brightnessHSB())
        cldAssertEqualsAsString("e_brightness_hsb:70", Adjust.brightnessHSB { setLevel(70) })
    }

    @Test
    fun contrast() {
        cldAssertEqualsAsString("e_contrast", Adjust.contrast())
        cldAssertEqualsAsString("e_contrast:70", Adjust.contrast { setLevel(70) })

    }

    @Test
    fun fillLight() {
        cldAssertEqualsAsString("e_fill_light", Adjust.fillLight())
        cldAssertEqualsAsString("e_fill_light:70", Adjust.fillLight { setBias(70) })
        cldAssertEqualsAsString("e_fill_light:70:20", Adjust.fillLight { setBias(20).setBlend(70) })
    }

    @Test
    fun hue() {
        cldAssertEqualsAsString("e_hue", Adjust.hue())
        cldAssertEqualsAsString("e_hue:40", Adjust.hue { setLevel(40) })
    }

    @Test
    fun saturation() {
        cldAssertEqualsAsString("e_saturation", Adjust.saturation())
        cldAssertEqualsAsString("e_saturation:70", Adjust.saturation { setLevel(70) })
    }

    @Test
    fun vibrance() {
        cldAssertEqualsAsString("e_vibrance", Adjust.vibrance())
        cldAssertEqualsAsString("e_vibrance:70", Adjust.vibrance { setLevel(70) })
    }

    @Test
    fun red() {
        cldAssertEqualsAsString("e_red", Adjust.red())
        cldAssertEqualsAsString("e_red:50", Adjust.red { setLevel(50) })
    }

    @Test
    fun green() {
        cldAssertEqualsAsString("e_green", Adjust.green())
        cldAssertEqualsAsString("e_green:-30", Adjust.green { setLevel(-30) })
    }

    @Test
    fun testBlue() {
        cldAssertEqualsAsString("e_blue", Adjust.blue())
        cldAssertEqualsAsString("e_blue:90", Adjust.blue { setLevel(90) })
    }

    @Test
    fun gamma() {
        cldAssertEqualsAsString("e_gamma", Adjust.gamma())
        cldAssertEqualsAsString("e_gamma:50", Adjust.gamma { setLevel(50) })
    }

    @Test
    fun replaceColor() {
        cldAssertEqualsAsString(
            "e_replace_color:saddlebrown",
            Adjust.replaceColor { setTo(color { named("saddlebrown") }) }
        )
        cldAssertEqualsAsString(
            "e_replace_color:2F4F4F:20",
            Adjust.replaceColor { setTo(color { fromRGB("2F4F4F") }).setTolerance(20) }
        )
        cldAssertEqualsAsString(
            "e_replace_color:silver:50:89b8ed",
            Adjust.replaceColor {
                setTo(color { named("silver") }).setTolerance(50).setFrom(color { fromRGB("#89b8ed") })
            }
        )
    }

    @Test
    fun improve() {
        cldAssertEqualsAsString("e_improve", Adjust.improve())
        cldAssertEqualsAsString("e_improve:50", Adjust.improve { setBlend(50) })
        cldAssertEqualsAsString("e_improve:indoor", Adjust.improve { setMode(ImproveMode.INDOOR) })
        cldAssertEqualsAsString("e_improve:outdoor:30", Adjust.improve { setBlend(30).setMode(ImproveMode.OUTDOOR) })
    }

    @Test
    fun viesusCorrect() {
        cldAssertEqualsAsString("e_viesus_correct", Adjust.viesusCorrect())
    }

    @Test
    fun sharpen() {
        cldAssertEqualsAsString("e_sharpen", Adjust.sharpen())
        cldAssertEqualsAsString("e_sharpen:400", Adjust.sharpen { setLevel(400) })
    }

    @Test
    fun unsharpMask() {
        cldAssertEqualsAsString("e_sharpen", Adjust.sharpen())
        cldAssertEqualsAsString("e_sharpen:400", Adjust.sharpen { setLevel(400) })
    }

    @Test
    fun testUnsharpMask() {
        cldAssertEqualsAsString("e_unsharp_mask", Adjust.unsharpMask())
        cldAssertEqualsAsString("e_unsharp_mask:200", Adjust.unsharpMask { setLevel(200) })
    }

    @Test
    fun testAutoColor() {
        cldAssertEqualsAsString("e_auto_color", Adjust.autoColor())
        cldAssertEqualsAsString("e_auto_color:80", Adjust.autoColor { setLevel(80) })
    }

    @Test
    fun opacityThreshold() {
        cldAssertEqualsAsString("e_opacity_threshold", Adjust.opacityThreshold())
        cldAssertEqualsAsString("e_opacity_threshold:100", Adjust.opacityThreshold { setLevel(100) })
    }

    @Test
    fun opacity() {
        cldAssertEqualsAsString("o_30", Adjust.opacity(30))
    }
}