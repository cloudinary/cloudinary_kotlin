package com.cloudinary.transformation

import com.cloudinary.transformation.effect.innerEffectAction
import com.cloudinary.util.cldRanged

class GradientFade(private val action: Action) : Action by action {

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

        // TODO simplify
        override fun build(): GradientFade {
            val values = listOfNotNull(if (symmetric) "symmetric" else null, strength?.cldRanged(0, 100))
            val params = listOfNotNull(x?.cldAsX(), y?.cldAsY())


            return GradientFade(innerEffectAction("gradient_fade", *((values + params).toTypedArray())))
        }
    }
}

