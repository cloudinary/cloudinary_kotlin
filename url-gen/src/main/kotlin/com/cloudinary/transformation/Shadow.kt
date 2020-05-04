package com.cloudinary.transformation

import com.cloudinary.util.cldRanged

class Shadow private constructor(params: Map<String, Param>) : ParamsAction<Shadow>(params) {
    override fun create(params: Map<String, Param>) = Shadow(params)

    class Builder : TransformationComponentBuilder {
        private var strength: Any? = null
        private var color: Color? = null
        private var x: Any? = null
        private var y: Any? = null

        fun strength(strength: Any) = apply { this.strength = strength }
        fun strength(strength: Int) = apply { this.strength = strength }
        fun color(color: Color?) = apply { this.color = color }
        fun x(x: Any) = apply { this.x = x }
        fun y(y: Any) = apply { this.y = y }
        fun x(x: Int) = apply { this.x = x }
        fun y(y: Int) = apply { this.y = y }

        override fun build(): Shadow {
            val values = listOfNotNull(strength?.cldRanged(0, 100))
            val params = listOfNotNull(color?.asParam(), x?.cldAsX(), y?.cldAsY())
            return Shadow(
                (params + Param(
                    "effect",
                    "e",
                    ParamValue(listOf("shadow") + values)
                )).cldToActionMap()
            )
        }
    }
}
