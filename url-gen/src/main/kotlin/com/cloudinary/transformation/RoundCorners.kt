package com.cloudinary.transformation

// TODO sipmlify
class RoundCorners(private val action: Action) : Action by action {

    companion object {
        fun radius(pixels: Int) = roundCorners(listOf(pixels))
        fun max() = roundCorners(listOf("max"))
    }

    // TODO - is this even really needed now?
    class Builder : TransformationComponentBuilder {
        private var corners = mutableListOf<Any>()

        fun pixels(vararg radius: Int) = apply {
            corners.addAll(radius.toList())
        }

        fun pixels(vararg radius: Any) = apply {
            corners.addAll(radius.toList())
        }

        fun max() = apply {
            corners.add("max")
        }

        override fun build() = roundCorners(corners)
    }

}

fun roundCorners(values: List<Any>): RoundCorners {
    return RoundCorners(ParamsAction(Param("radius", "r", ParamValue(values))))
}

