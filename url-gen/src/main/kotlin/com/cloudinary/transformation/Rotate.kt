package com.cloudinary.transformation

// TODO simplify
class Rotate(private val action: Action) : Action by action {

    companion object {
        fun angle(degrees: Int) = Builder().angle(degrees).build()
    }

    enum class Mode(private val value: String) {
        AUTO_RIGHT("auto_right"),
        AUTO_LEFT("auto_left"),
        IGNORE("ignore"),
        VFLIP("vflip"),
        HFLIP("hflip");

        override fun toString(): String {
            return value
        }
    }

    class Builder : TransformationComponentBuilder {
        private var angles = mutableListOf<Any>()

        fun angle(angle: Int) = apply { angles.add(angle) }
        fun mode(mode: Mode) = apply { angles.add(mode) }

        override fun build() = buildParameters(angles)

        private fun buildParameters(values: List<Any>) =
            Rotate(
                ParamsAction(
                    Param(
                        "angle",
                        "a",
                        ParamValue(values.cldAsParamValueContent(), ".")
                    )
                )
            )
    }
}