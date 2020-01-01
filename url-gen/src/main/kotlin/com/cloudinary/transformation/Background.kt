package com.cloudinary.transformation

class Background private constructor(params: Map<String, Param>) :
    ParamsAction<Background>(params) {
    override fun create(params: Map<String, Param>) = Background(params)

    companion object {
        fun color(color: ColorValue) = Builder(color).build()
        fun color(color: ColorValue.Builder.() -> Unit): Background {
            val builder = ColorValue.Builder()
            builder.color()
            return Builder(builder.build()).build()
        }
    }

    class Builder(private val color: ColorValue) : TransformationComponentBuilder {
        override fun build() = buildParameters(color)
        private fun buildParameters(value: ParamValue) =
            Background(
                backgroundParam(value).let { mapOf(Pair(it.key, it)) }
            )
    }
}