package com.cloudinary.transformation

class Border private constructor(params: Map<String, Param>) :
    ParamsAction<Border>(params) {
    override fun create(params: Map<String, Param>) = Border(params)

    class Builder : TransformationComponentBuilder {
        private var width: Any? = null
        private var color: ColorValue? = null
        private var type = "solid"

        fun width(width: Int) = apply { this.width = width }
        fun width(width: Any) = apply { this.width = width }
        fun color(color: ColorValue) = apply { this.color = color }
        fun color(color: ColorValue.Builder.() -> Unit) = apply {
            val builder = ColorValue.Builder()
            builder.color()
            color(builder.build())
        }

        override fun build() = Border(
            Param(
                "border",
                "bo",
                ParamValue(listOf("${width}px", type, color).cldAsParamValueContent(), "_")
            ).let { mapOf(Pair(it.key, it)) })

    }
}

