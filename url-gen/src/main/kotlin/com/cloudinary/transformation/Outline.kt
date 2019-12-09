package com.cloudinary.transformation

import com.cloudinary.util.cldRanged

class Outline private constructor(params: Map<String, Param>) :
    Action<Outline>(params) {

    override fun create(params: Map<String, Param>) = Outline(params)

    class Builder private constructor(
        private var mode: OutlineMode? = null,
        private var color: ColorValue? = null,
        private var width: Int? = null,
        private var blur: Int? = null
    ) : TransformationComponentBuilder {
        constructor() : this(null)

        fun setMode(mode: OutlineMode) = apply { this.mode = mode }
        fun setColor(color: ColorValue) = apply { this.color = color }

        fun setColor(color: String) = apply { this.color = ColorValue.parseString(color) }
        fun setWidth(width: Int) = apply { this.width = width }

        fun setBlur(blur: Int) = apply { this.blur = blur }
        override fun build(): Outline {
            val values = listOfNotNull(mode, width?.cldRanged(1, 100), blur?.cldRanged(0, 200))
            val params = listOfNotNull(color?.asParam())
            return Outline(
                (params + Param(
                    "effect",
                    "e",
                    ParamValue(listOf("outline") + values)
                )).cldToActionMap()
            )
        }
    }
}

enum class OutlineMode(internal val value: String) {
    INNER("inner"),
    INNER_FILL("inner_fill"),
    OUTER("outer"),
    FILL("fill"), ;

    override fun toString(): String {
        return value
    }
}