package com.cloudinary.transformation

class Background private constructor(params: Map<String, Param>) :
    ParamsAction<Background>(params) {
    override fun create(params: Map<String, Param>) = Background(params)

    companion object {
        fun color(color: Color) = Builder(color).build()
        fun color(color: String) = Builder(Color.parseString(color)).build()
    }

    class Builder(private val color: Color) : TransformationComponentBuilder {
        override fun build() = buildParameters(color)
        private fun buildParameters(value: ParamValue) =
            Background(
                value.cldAsBackground().let { mapOf(Pair(it.key, it)) }
            )
    }
}