package com.cloudinary.transformation

class Border private constructor(params: Map<String, Param>) :
    Action<Border>(params) {
    override fun create(params: Map<String, Param>) = Border(params)

    class Builder : TransformationComponentBuilder {
        private var width: Any? = null
        private var color: ColorValue? = null
        private var type = "solid"

        fun setWidth(width: Int) = apply { this.width = width }
        fun setWidth(width: Any) = apply { this.width = width }
        fun setColor(color: ColorValue) = apply { this.color = color }
        fun setColor(color: ColorValue.Builder.() -> Unit) = apply {
            val builder = ColorValue.Builder()
            builder.color()
            setColor(builder.build())
        }

        override fun build() = Border(
            Param(
                "border",
                "bo",
                ParamValue(listOfNotNull("${width}px", type, color), "_")
            ).let { mapOf(Pair(it.key, it)) })

    }
}

