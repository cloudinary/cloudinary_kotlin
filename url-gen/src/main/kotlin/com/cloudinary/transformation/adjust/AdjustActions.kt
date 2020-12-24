package com.cloudinary.transformation.adjust

import com.cloudinary.transformation.Color
import com.cloudinary.transformation.TransformationComponentBuilder
import com.cloudinary.transformation.joinWithValues
import com.cloudinary.util.cldEncodePublicId
import com.cloudinary.util.cldRanged

class Opacity internal constructor(val level: Any) : Adjust() {
    init {
        level.cldRanged(0, 100)
    }

    override fun toString(): String {
        return "o_$level"
    }
}

class ViesusCorrect internal constructor(
    private val noRedEye: Boolean?,
    private val skinSaturation: Boolean? = null,
    private val skinSaturationLevel: Int?
) : Adjust() {
    override fun toString(): String {
        val redEyeStr = if (noRedEye == true) "no_redeye" else null

        val skinSaturationStr = if (skinSaturation == true) {
            "skin_saturation".joinWithValues(skinSaturationLevel, separator = "_")
        } else {
            null
        }

        return "e_viesus_correct".joinWithValues(redEyeStr, skinSaturationStr)
    }

    class Builder : TransformationComponentBuilder {
        private var noRedEye: Boolean? = null
        private var skinSaturation: Boolean? = null
        private var skinSaturationLevel: Int? = null

        fun skinSaturation(level: Int? = null) = apply {
            this.skinSaturation = true
            this.skinSaturationLevel = level
        }

        fun noRedEye() = apply { this.noRedEye = true }

        override fun build(): ViesusCorrect {
            return ViesusCorrect(noRedEye, skinSaturation, skinSaturationLevel)
        }
    }
}

class Recolor internal constructor(private val colorMatrix: Array<FloatArray>) : Adjust() {
    init {
        require(isValid(colorMatrix)) { "The color matrix must be 3 by 3 or 4 by 4" }
    }

    fun isValid(colorMatrix: Array<FloatArray>): Boolean {
        return colorMatrix.size == 3 && colorMatrix.all { it.size == 3 } ||
                colorMatrix.size == 4 && colorMatrix.all { it.size == 4 }
    }

    override fun toString(): String {
        return "e_recolor:" + colorMatrix.joinToString(separator = ":") {
            it.joinToString(separator = ":")
        }
    }
}

class Improve(val mode: ImproveModeType? = null, val blend: Int? = null) : Adjust() {
    override fun toString(): String {
        return "e_improve".joinWithValues(mode, blend)
    }

    class Builder : TransformationComponentBuilder {
        private var mode: ImproveModeType? = null
        private var blend: Int? = null
        fun mode(mode: ImproveModeType) = apply { this.mode = mode }
        fun blend(blend: Int) = apply { this.blend = blend }

        override fun build() = Improve(mode, blend)
    }
}

class ReplaceColor(
    private val toColor: Color,
    private val tolerance: Int? = null,
    private val fromColor: Color? = null
) : Adjust() {
    init {
        tolerance?.cldRanged(0, 100)
    }

    override fun toString(): String {
        return "e_replace_color".joinWithValues(toColor.toString(false), tolerance, fromColor?.toString(false))
    }

    class Builder(private val to: Color) : TransformationComponentBuilder {
        private var from: Color? = null
        private var tolerance: Int? = null

        fun from(from: Color) = apply { this.from = from }
        fun tolerance(tolerance: Int) = apply { this.tolerance = tolerance }

        override fun build() = ReplaceColor(to, tolerance, from)
    }
}

class FillLight(private val blend: Any? = null, private val bias: Any? = null) : Adjust() {

    init {
        blend?.cldRanged(0, 100)
        bias?.cldRanged(-100, 100)
    }

    override fun toString(): String {
        return "e_fill_light".joinWithValues(
            blend?.cldRanged(0, 100),
            bias?.cldRanged(-100, 100)
        )
    }

    class Builder : TransformationComponentBuilder {
        private var blend: Any? = null
        private var bias: Any? = null

        fun blend(blend: Int) = apply { this.blend = blend }
        fun bias(bias: Int) = apply { this.bias = bias }
        override fun build() = FillLight(blend, bias)
    }
}

// TODO align with spec
class Tint(vararg val values: Any?) : Adjust() {
    override fun toString(): String {
        return "e_tint".joinWithValues(*values)
    }
}

abstract class LevelAdjust(protected val name: String, protected val level: Any? = null) : Adjust() {
    override fun toString(): String {
        return "e_$name".joinWithValues(level)
    }
}

class Vibrance(level: Int? = null) : LevelAdjust("vibrance", level?.cldRanged(-100, 100))
class AutoColor(level: Int? = null) : LevelAdjust("auto_color", level?.cldRanged(0, 100))
class Brightness(level: Int? = null) : LevelAdjust("brightness", level?.cldRanged(-99, 100))
class AutoBrightness(level: Int? = null) : LevelAdjust("auto_brightness", level?.cldRanged(0, 100))
class BrightnessHSB(level: Int? = null) : LevelAdjust("brightness_hsb", level?.cldRanged(-99, 100))
class AutoContrast(level: Int? = null) : LevelAdjust("auto_contrast", level?.cldRanged(0, 100))
class UnsharpMask(strength: Int? = null) : LevelAdjust("unsharp_mask", strength?.cldRanged(1, 2000))
class Hue(level: Int? = null) : LevelAdjust("hue", level?.cldRanged(-100, 100))
class Gamma(level: Int? = null) : LevelAdjust("gamma", level.cldRanged(-50, 150))
class Contrast(level: Int? = null) : LevelAdjust("contrast", level?.cldRanged(-100, 100))
class Blue(level: Int? = null) : LevelAdjust("blue", level?.cldRanged(-100, 100))
class Green(level: Int? = null) : LevelAdjust("green", level?.cldRanged(-100, 100))
class Red(level: Int? = null) : LevelAdjust("red", level?.cldRanged(-100, 100))
class OpacityThreshold(level: Int? = null) : LevelAdjust("opacity_threshold", level?.cldRanged(1, 100))
class Saturation(level: Int? = null) : LevelAdjust("saturation", level?.cldRanged(-100, 100))
class By3DLut(private val publicId: String) : Adjust() {
    override fun toString(): String {
        return "l_lut:${publicId.cldEncodePublicId()}"
    }
}

class Sharpen(private val strength: Int?) : Adjust() {
    init {
        strength?.cldRanged(1, 2000)
    }

    override fun toString(): String {
        return "e_sharpen".joinWithValues(strength)
    }
}

enum class ImproveModeType(internal val value: String) {
    OUTDOOR("outdoor"),
    INDOOR("indoor");

    override fun toString(): String {
        return value
    }
}