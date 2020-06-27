package com.cloudinary.transformation

class Border(private val action: Action) : Action by action {

    companion object {
        fun solid(border: (Builder.() -> Unit)? = null): Border {
            val builder = Builder()
            border?.let { builder.it() }
            return builder.build()
        }
    }

    class Builder : TransformationComponentBuilder {
        private var width: Any? = null
        private var color: Color? = null
        private var type = "solid"

        fun width(width: Int) = apply { this.width = width }
        fun width(width: Any) = apply { this.width = width }
        fun color(color: String) = apply { this.color = Color.parseString(color) }
        fun color(color: Color) = apply { this.color = color }

        override fun build() =
            Border(
                ParamValue(
                    listOf("${width}px", type, color).cldAsParamValueContent(),
                    "_"
                ).cldAsBorder().asAction()
            )
    }
}

