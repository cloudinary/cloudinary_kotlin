package com.cloudinary.transformation.adjust

import com.cloudinary.transformation.Color
import com.cloudinary.transformation.TransformationComponentBuilder
import com.cloudinary.transformation.effect.ImproveMode
import com.cloudinary.util.cldRanged


class FillLightBuilder : TransformationComponentBuilder {
    private var blend: Any? = null
    private var bias: Any? = null

    fun blend(blend: Int) = apply { this.blend = blend }
    fun bias(bias: Int) = apply { this.bias = bias }
    override fun build() = adjustEffect(
        "fill_light",
        blend.cldRanged(0, 100),
        bias?.cldRanged(-100, 100)
    )
}

class ReplaceColorBuilder(private val to: Color) : TransformationComponentBuilder {
    private var from: Color? = null
    private var tolerance: Int? = null

    fun from(from: Color) = apply { this.from = from }
    fun tolerance(tolerance: Int) = apply { this.tolerance = tolerance }

    override fun build() = adjustEffect(
        "replace_color",
        to.withoutRgbPrefix(),
        tolerance?.cldRanged(0, 100),
        from?.withoutRgbPrefix()
    )
}

class ImproveBuilder : TransformationComponentBuilder {
    private var mode: ImproveMode? = null
    private var blend: Int? = null
    fun mode(mode: ImproveMode) = apply { this.mode = mode }
    fun blend(blend: Int) = apply { this.blend = blend }

    override fun build() = adjustEffect("improve", mode, blend?.cldRanged(0, 100))
}