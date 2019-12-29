package com.cloudinary.transformation

import com.cloudinary.util.cldRanged

class GradientFade private constructor(params: Map<String, Param>) :
    ParamsAction<GradientFade>(params) {
    override fun create(params: Map<String, Param>) = GradientFade(params)

    class Builder : TransformationComponentBuilder {
        private var strength: Any? = null
        private var symmetric = false
        private var x: Any? = null
        private var y: Any? = null

        fun strength(strength: Any) = apply { this.strength = strength }
        fun strength(strength: Int) = apply { this.strength = strength }
        fun symmetric(symmetric: Boolean) = apply { this.symmetric = symmetric }
        fun x(x: Any) = apply { this.x = x }
        fun y(y: Any) = apply { this.y = y }
        fun x(x: Int) = apply { this.x = x }
        fun y(y: Int) = apply { this.y = y }
        fun x(x: Float) = apply { this.x = x }
        fun y(y: Float) = apply { this.y = y }

        override fun build(): GradientFade {
            val values = listOfNotNull(if (symmetric) "symmetric" else null, strength?.cldRanged(0, 100))
            val params = listOfNotNull(xParam(x), yParam(y))

            return GradientFade(
                (params + Param(
                    "effect",
                    "e",
                    ParamValue(listOf("gradient_fade") + values)
                )).cldToActionMap()
            )
        }
    }
}

