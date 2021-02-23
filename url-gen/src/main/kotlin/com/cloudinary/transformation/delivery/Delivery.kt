package com.cloudinary.transformation.delivery

import com.cloudinary.transformation.Action
import com.cloudinary.transformation.Format
import com.cloudinary.transformation.expression.Expression

abstract class Delivery : Action {
    companion object {
        fun defaultImage(publicIdWithExtension: String) = DefaultImage(publicIdWithExtension)
        fun density(density: Int) = Density(density)
        fun density(density: Expression) = Density(density)
        fun density(density: String) = Density(density)
        fun colorSpace(colorSpace: ColorSpace) = ColorSpaceAction(colorSpace)
        fun colorSpaceFromICC(publicId: String) = ColorSpaceFromICC(publicId)

        fun dpr(dpr: Dpr) = dpr
        fun dpr(dpr: Float) = Dpr.fromFloat(dpr)
        fun dpr(dpr: Expression) = Dpr.fromExpression(dpr)
        fun dpr(dpr: String) = Dpr.fromString(dpr)

        fun quality(quality: QualityAction) = quality
        fun quality(level: Any, options: (QualityAction.Builder.() -> Unit)? = null): QualityAction {
            val builder = QualityAction.Builder(level)
            options?.let { builder.it() }
            return builder.build()
        }

        fun format(format: DeliveryFormat) = format
        fun format(format: Format, options: (DeliveryFormat.Builder.() -> Unit)? = null): DeliveryFormat {
            val builder = DeliveryFormat.Builder(format)
            options?.let { builder.it() }
            return builder.build()
        }
    }
}