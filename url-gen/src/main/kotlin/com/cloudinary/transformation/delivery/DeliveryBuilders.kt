package com.cloudinary.transformation.delivery

import com.cloudinary.transformation.ParamValue
import com.cloudinary.transformation.TransformationComponentBuilder
import com.cloudinary.transformation.cldAsFps

// TODO review api
class FpsBuilder : TransformationComponentBuilder {
    private var fixed: Any? = null
    private var min: Any? = null
    private var max: Any? = null

    internal fun fixed(fps: Float) = apply { this.fixed = fps }
    fun min(min: Float) = apply { this.min = min }
    fun max(max: Float) = apply { this.max = max }
    fun min(min: Int) = apply { this.min = min }
    fun max(max: Int) = apply { this.max = max }
    fun min(min: Any) = apply { this.min = min }
    fun max(max: Any) = apply { this.max = max }

    override fun build(): Delivery {
        val paramValue = ParamValue((if (fixed != null) listOf(fixed) else listOfNotNull(min, max ?: "")), "-")
        return delivery(paramValue.cldAsFps())
    }
}