package com.cloudinary.transformation.adjust

import com.cloudinary.transformation.Color
import com.cloudinary.transformation.expression.Expression
import com.cloudinary.transformation.joinWithValues
import com.cloudinary.util.cldEncodePublicId
import com.cloudinary.util.cldRanged

class Opacity (level: Any? = null) : LevelAdjust("opacity", level?.cldRanged(0,100)) {
    class Builder : AdjustBuilder {
        private var level: Any? = null

        fun level(level: Int) = apply { this.level = level }
        fun level(level: Expression) = apply { this.level = level }

        override fun build() = Opacity(level)

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

    class Builder : AdjustBuilder {
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

class Recolor internal constructor(private val colorMatrix: List<List<Float>>) : Adjust() {
    constructor(colorMatrix: FloatArray) : this(buildFromList(colorMatrix))

    init {
        require(isValid(colorMatrix)) { "The color matrix must be 3 by 3 or 4 by 4" }
    }

    private fun isValid(colorMatrix: List<List<Float>>): Boolean {
        return colorMatrix.size == 3 && colorMatrix.all { it.size == 3 } ||
                colorMatrix.size == 4 && colorMatrix.all { it.size == 4 }
    }

    override fun toString(): String {
        return "e_recolor:" + colorMatrix.joinToString(separator = ":") {
            it.joinToString(separator = ":")
        }
    }

    companion object {
        private fun buildFromList(colorMatrix: FloatArray): List<List<Float>> {
            require(colorMatrix.size == 9 || colorMatrix.size == 16) { "The color matrix must consist of 9 or 16 values" }

            return if (colorMatrix.size == 9)
                listOf(
                    colorMatrix.slice(0..2),
                    colorMatrix.slice(3..5),
                    colorMatrix.slice(6..8)
                )
            else
                listOf(
                    colorMatrix.slice(0..3),
                    colorMatrix.slice(4..7),
                    colorMatrix.slice(8..11),
                    colorMatrix.slice(12..15)
                )
        }
    }
}

class Improve(val mode: ImproveMode? = null, val blend: Int? = null) : Adjust() {
    override fun toString(): String {
        return "e_improve".joinWithValues(mode, blend)
    }

    class Builder : AdjustBuilder {
        private var mode: ImproveMode? = null
        private var blend: Int? = null
        fun mode(mode: ImproveMode) = apply { this.mode = mode }
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

    class Builder(private val to: Color) : AdjustBuilder {
        private var from: Color? = null
        private var tolerance: Int? = null

        fun fromColor(from: Color) = apply { this.from = from }
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

    class Builder : AdjustBuilder {
        private var blend: Any? = null
        private var bias: Any? = null

        fun blend(blend: Int) = apply { this.blend = blend }
        fun bias(bias: Int) = apply { this.bias = bias }
        override fun build() = FillLight(blend, bias)
    }
}

// TODO align with spec
class Tint(private val options: String? = null) : Adjust() {
    override fun toString(): String {
        return "e_tint".joinWithValues(options)
    }
}

abstract class LevelAdjust(protected val name: String, protected val level: Any? = null) : Adjust() {
    override fun toString(): String {
        return "e_$name".joinWithValues(level)
    }
}

class Vibrance(level: Any? = null) : LevelAdjust("vibrance", level?.cldRanged(-100, 100)) {
    class Builder : AdjustBuilder {
        private var strength: Any? = null

        fun strength(strength: Int) = apply { this.strength = strength }
        fun strength(strength: Expression) = apply { this.strength = strength }
        fun strength(strength: String) = apply { this.strength = strength }

        override fun build() = Vibrance(strength)
    }

}

class AutoColor(private val blend: Any? = null) : Adjust() {
    init {
        blend?.cldRanged(0, 100)
    }

    override fun toString(): String {
        return "e_auto_color".joinWithValues(blend)
    }

    class Builder : AdjustBuilder {
        private var blend: Any? = null

        fun blend(blend: Int) = apply { this.blend = blend }
        fun blend(blend: Expression) = apply { this.blend = blend }
        fun blend(blend: String) = apply { this.blend = blend }

        override fun build() = AutoColor(blend)
    }
}

class Brightness(level: Any? = null) : LevelAdjust("brightness", level?.cldRanged(-99, 100)) {
    class Builder : AdjustLevelBuilder() {
        override fun build() = Brightness(level)
    }
}

class AutoBrightness(private val blend: Any? = null) : Adjust() {
    init {
        blend?.cldRanged(0, 100)
    }

    override fun toString(): String {
        return "e_auto_brightness".joinWithValues(blend)
    }

    class Builder : AdjustBuilder {
        private var blend: Any? = null

        fun blend(blend: Int) = apply { this.blend = blend }
        fun blend(blend: Expression) = apply { this.blend = blend }
        fun blend(blend: String) = apply { this.blend = blend }

        override fun build() = AutoBrightness(blend)
    }
}

class BrightnessHSB(level: Any? = null) : LevelAdjust("brightness_hsb", level?.cldRanged(-99, 100)) {
    class Builder : AdjustLevelBuilder() {
        override fun build() = BrightnessHSB(level)
    }
}

class AutoContrast(private val blend: Any? = null) : Adjust() {
    init {
        blend?.cldRanged(0, 100)
    }

    override fun toString(): String {
        return "e_auto_contrast".joinWithValues(blend)
    }

    class Builder : AdjustBuilder {
        private var blend: Any? = null

        fun blend(blend: Int) = apply { this.blend = blend }
        fun blend(blend: Expression) = apply { this.blend = blend }
        fun blend(blend: String) = apply { this.blend = blend }

        override fun build() = AutoContrast(blend)
    }
}

class UnsharpMask(strength: Any? = null) : LevelAdjust("unsharp_mask", strength?.cldRanged(1, 2000)) {
    class Builder : AdjustBuilder {
        private var strength: Any? = null

        fun strength(strength: Int) = apply { this.strength = strength }
        fun strength(strength: Expression) = apply { this.strength = strength }
        fun strength(strength: String) = apply { this.strength = strength }

        override fun build() = UnsharpMask(strength)
    }
}

class Hue(level: Any? = null) : LevelAdjust("hue", level?.cldRanged(-100, 100)) {
    class Builder : AdjustLevelBuilder() {
        override fun build() = Hue(level)
    }
}

class Gamma(level: Any? = null) : LevelAdjust("gamma", level.cldRanged(-50, 150)) {
    class Builder : AdjustLevelBuilder() {
        override fun build() = Gamma(level)
    }
}

class Contrast(level: Any? = null) : LevelAdjust("contrast", level?.cldRanged(-100, 100)) {
    class Builder : AdjustLevelBuilder() {
        override fun build() = Contrast(level)
    }
}

class Blue(level: Any? = null) : LevelAdjust("blue", level?.cldRanged(-100, 100)) {
    class Builder : AdjustLevelBuilder() {
        override fun build() = Blue(level)
    }
}

class Green(level: Any? = null) : LevelAdjust("green", level?.cldRanged(-100, 100)) {
    class Builder : AdjustLevelBuilder() {
        override fun build() = Green(level)
    }
}

class Red(level: Any? = null) : LevelAdjust("red", level?.cldRanged(-100, 100)) {
    class Builder : AdjustLevelBuilder() {
        override fun build() = Red(level)
    }
}

class OpacityThreshold(level: Any? = null) : LevelAdjust("opacity_threshold", level?.cldRanged(1, 100)) {
    class Builder : AdjustLevelBuilder() {
        override fun build() = OpacityThreshold(level)
    }
}

class Saturation(level: Any? = null) : LevelAdjust("saturation", level?.cldRanged(-100, 100)) {
    class Builder : AdjustLevelBuilder() {
        override fun build() = Saturation(level)
    }
}

class By3dLut(private val publicId: String) : Adjust() {
    override fun toString(): String {
        return "l_lut:${publicId.cldEncodePublicId()}"
    }
}

class Sharpen(private val strength: Any?) : Adjust() {
    init {
        strength?.cldRanged(1, 2000)
    }

    class Builder : AdjustBuilder {
        private var strength: Any? = null

        fun strength(strength: Int) = apply { this.strength = strength }
        fun strength(strength: Expression) = apply { this.strength = strength }
        fun strength(strength: String) = apply { this.strength = strength }

        override fun build() = Sharpen(strength)
    }

    override fun toString(): String {
        return "e_sharpen".joinWithValues(strength)
    }
}

class ImproveMode(internal val value: String) {
    companion object {
        private val outdoor = ImproveMode("outdoor")
        fun outdoor() = outdoor
        private val indoor = ImproveMode("indoor")
        fun indoor() = indoor
    }

    override fun toString(): String {
        return value
    }
}

abstract class AdjustLevelBuilder : AdjustBuilder {
    protected var level: Any? = null

    fun level(level: Int) = apply { this.level = level }
    fun level(level: Expression) = apply { this.level = level }
    fun level(level: String) = apply { this.level = level }
}