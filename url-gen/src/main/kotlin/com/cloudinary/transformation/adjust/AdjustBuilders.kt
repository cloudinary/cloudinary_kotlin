package com.cloudinary.transformation.adjust

import com.cloudinary.transformation.ColorValue
import com.cloudinary.transformation.Param
import com.cloudinary.transformation.ParamValue
import com.cloudinary.transformation.TransformationComponentBuilder
import com.cloudinary.transformation.effect.Effect
import com.cloudinary.transformation.effect.ImproveMode
import com.cloudinary.util.cldRanged

class Brightness private constructor(level: Any? = null) :
    Adjust("brightness", level?.cldRanged(-99, 100)) {
    class Builder : TransformationComponentBuilder {
        private var level: Any? = null

        fun level(level: Int) = apply { this.level = level }
        override fun build() = Brightness(level)
    }
}

class AutoBrightness private constructor(level: Any? = null) :
    Adjust("auto_brightness", level?.cldRanged(0, 100)) {
    class Builder : TransformationComponentBuilder {
        private var level: Any? = null

        fun level(level: Int) = apply { this.level = level }
        override fun build() = AutoBrightness(level)
    }
}

class BrightnessHSB private constructor(level: Any? = null) :
    Adjust("brightness_hsb", level?.cldRanged(-99, 100)) {
    class Builder : TransformationComponentBuilder {
        private var level: Any? = null
        fun level(level: Int) = apply { this.level = level }
        override fun build() = BrightnessHSB(level)
    }
}

class Contrast private constructor(level: Any? = null) :
    Adjust("contrast", level?.cldRanged(-100, 100)) {
    class Builder : TransformationComponentBuilder {
        private var level: Any? = null


        fun level(level: Int) = apply { this.level = level }
        override fun build() = Contrast(level)
    }
}

class FillLight private constructor(blend: Any?, bias: Any? = null) :
    Adjust(
        "fill_light",
        listOfNotNull(blend.cldRanged(0, 100), bias?.cldRanged(-100, 100))
    ) {
    class Builder : TransformationComponentBuilder {
        private var blend: Any? = null
        private var bias: Any? = null

        fun blend(blend: Int) = apply { this.blend = blend }
        fun bias(bias: Int) = apply { this.bias = bias }
        override fun build() = FillLight(blend, bias)
    }
}

class Hue private constructor(level: Any? = null) :
    Adjust("hue", level?.cldRanged(-100, 100)) {
    class Builder : TransformationComponentBuilder {
        private var level: Any? = null

        fun level(level: Int) = apply { this.level = level }
        override fun build() = Hue(level)
    }
}

class Saturation private constructor(level: Any? = null) :
    Adjust("saturation", level?.cldRanged(-100, 100)) {
    class Builder : TransformationComponentBuilder {
        private var level: Any? = null

        fun level(level: Int) = apply { this.level = level }
        override fun build() = Saturation(level)
    }
}

class Vibrance private constructor(level: Any? = null) :
    Adjust("vibrance", level?.cldRanged(-100, 100)) {
    class Builder : TransformationComponentBuilder {
        private var level: Any? = null
        fun level(level: Int) = apply { this.level = level }
        override fun build() = Vibrance(level)
    }
}

class Red private constructor(level: Any? = null) :
    Adjust("red", level?.cldRanged(-100, 100)) {
    class Builder : TransformationComponentBuilder {
        private var level: Any? = null

        fun level(level: Int) = apply { this.level = level }
        override fun build() = Red(level)
    }
}

class Green private constructor(level: Any? = null) :
    Adjust("green", level?.cldRanged(-100, 100)) {
    class Builder : TransformationComponentBuilder {
        private var level: Any? = null

        fun level(level: Int) = apply { this.level = level }
        override fun build() = Green(level)
    }
}

class Blue private constructor(level: Any? = null) :
    Adjust("blue", level?.cldRanged(-100, 100)) {
    class Builder : TransformationComponentBuilder {
        private var level: Any? = null

        fun level(level: Int) = apply { this.level = level }
        override fun build() = Blue(level)
    }
}

class Gamma private constructor(level: Any? = null) : Adjust("gamma", level) {
    class Builder : TransformationComponentBuilder {
        private var level: Any? = null

        fun level(level: Int) = apply { this.level = level }
        override fun build() = Gamma(level)
    }
}

class ReplaceColor private constructor(to: ColorValue?, from: ColorValue? = null, tolerance: Int? = null) :
    Adjust(
        "replace_color",

        listOfNotNull(to?.withoutRgbPrefix(), tolerance?.cldRanged(0, 100), from?.withoutRgbPrefix())
    ) {
    class Builder : TransformationComponentBuilder {
        private var to: ColorValue? = null
        private var from: ColorValue? = null
        private var tolerance: Int? = null

        fun to(to: ColorValue) = apply { this.to = to }
        fun from(from: ColorValue) = apply { this.from = from }
        fun tolerance(tolerance: Int) = apply { this.tolerance = tolerance }

        override fun build() = ReplaceColor(to, from, tolerance)
    }
}

class Improve private constructor(mode: ImproveMode? = null, blend: Int? = null) :
    Adjust(
        "improve",
        listOfNotNull(mode?.value, blend?.cldRanged(0, 100))
    ) {
    class Builder : TransformationComponentBuilder {
        private var mode: ImproveMode? = null
        private var blend: Int? = null
        fun mode(mode: ImproveMode) = apply { this.mode = mode }
        fun blend(blend: Int) = apply { this.blend = blend }

        override fun build() = Improve(mode, blend)
    }
}


class Sharpen private constructor(strength: Any? = null) :
    Adjust("sharpen", strength?.cldRanged(1, 2000)) {
    class Builder : TransformationComponentBuilder {
        private var strength: Any? = null

        fun strength(strength: Int) = apply { this.strength = strength }
        override fun build() = Sharpen(strength)
    }
}

class UnsharpMask private constructor(strength: Any? = null) :
    Adjust("unsharp_mask", strength?.cldRanged(1, 2000)) {
    class Builder : TransformationComponentBuilder {
        private var strength: Any? = null

        fun level(strength: Int) = apply { this.strength = strength }
        override fun build() = UnsharpMask(strength)
    }
}

class AutoContrast private constructor(level: Any? = null) :
    Adjust("auto_contrast", level?.cldRanged(0, 100)) {
    class Builder : TransformationComponentBuilder {
        private var level: Any? = null


        fun level(level: Int) = apply { this.level = level }
        override fun build() = AutoContrast(level)
    }
}

class AutoColor private constructor(level: Any? = null) :
    Adjust("auto_color", level?.cldRanged(0, 100)) {
    class Builder : TransformationComponentBuilder {
        private var level: Any? = null

        fun level(level: Int) = apply { this.level = level }
        override fun build() = AutoColor(level)
    }
}

class OpacityThreshold private constructor(level: Any? = null) : Adjust(
    "opacity_threshold",
    level?.cldRanged(1, 100)
) {
    class Builder : TransformationComponentBuilder {
        private var level: Any? = null

        fun level(level: Int) = apply { this.level = level }
        override fun build() = OpacityThreshold(level)
    }
}

class Opacity private constructor(level: Any) : Adjust(
    Param(
        "opacity",
        "o",
        ParamValue(level.cldRanged(0, 100))
    )
) {
    class Builder(private var level: Any) : TransformationComponentBuilder {

        fun level(level: Int) = apply { this.level = level }
        override fun build() = Opacity(level)
    }
}

class ViesusCorrect : Adjust("viesus_correct")
