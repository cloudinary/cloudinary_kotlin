package com.cloudinary.transformation.delivery

import com.cloudinary.transformation.Action
import com.cloudinary.transformation.FormatType
import com.cloudinary.transformation.expression.Expression

abstract class Delivery : Action {
    companion object {
        fun defaultImage(publicId: String) = DefaultImage(publicId)
        fun density(density: Int) = Density(density)
        fun density(density: Expression) = Density(density)
        fun density(density: String) = Density(density)
        fun colorSpace(colorSpace: ColorSpaceType) = ColorSpace(colorSpace)
        fun colorSpaceFromIcc(publicId: String) = ColorSpaceFromIcc(publicId)

        fun dpr(dpr: Dpr) = dpr
        fun dpr(dpr: Float) = Dpr.fromFloat(dpr)
        fun dpr(dpr: Expression) = Dpr.fromExpression(dpr)
        fun dpr(dpr: String) = Dpr.fromString(dpr)

        fun quality(quality: Quality) = quality
        fun quality(level: Any, options: (Quality.Builder.() -> Unit)? = null): Quality {
            val builder = Quality.Builder(level)
            options?.let { builder.it() }
            return builder.build()
        }

        fun format(format: Format) = format
        fun format(format: FormatType, options: (Format.Builder.() -> Unit)? = null): Format {
            val builder = Format.Builder(format)
            options?.let { builder.it() }
            return builder.build()
        }
    }
}