package com.cloudinary.transformation

class Rotate(private val action: Action) : Action by action {

    companion object {
        fun angle(degrees: Int, add: (Builder.() -> Unit)? = null) = withBuilder(degrees, add)
        fun angle(degrees: Any, add: (Builder.() -> Unit)? = null) = withBuilder(degrees, add)
        fun horizontalFlip(add: (Builder.() -> Unit)? = null) = withBuilder(Mode.HFLIP, add)
        fun verticalFlip(add: (Builder.() -> Unit)? = null) = withBuilder(Mode.VFLIP, add)
        fun autoLeft(add: (Builder.() -> Unit)? = null) = withBuilder(Mode.AUTO_LEFT, add)
        fun autoRight(add: (Builder.() -> Unit)? = null) = withBuilder(Mode.AUTO_RIGHT, add)
        fun ignore(add: (Builder.() -> Unit)? = null) = withBuilder(Mode.IGNORE, add)
    }

    private enum class Mode(private val value: String) {
        AUTO_RIGHT("auto_right"),
        AUTO_LEFT("auto_left"),
        IGNORE("ignore"),
        VFLIP("vflip"),
        HFLIP("hflip");

        override fun toString(): String {
            return value
        }
    }

    class Builder internal constructor(firstAngle: Any) : TransformationComponentBuilder {
        private var angles = mutableListOf(firstAngle)

        fun angle(degrees: Int) = angles.add(degrees)
        fun angle(degrees: Any) = angles.add(degrees)
        fun horizontalFlip() = angles.add(Mode.HFLIP)
        fun verticalFlip() = angles.add(Mode.VFLIP)
        fun autoLeft() = angles.add(Mode.AUTO_LEFT)
        fun autoRight() = angles.add(Mode.AUTO_RIGHT)
        fun ignore() = angles.add(Mode.IGNORE)

        override fun build() = buildParameters(angles)

        private fun buildParameters(values: List<Any>) =
            Rotate(ParamValue(values.cldAsParamValueContent(), ".").cldAsAngle().asAction())
    }
}

private fun withBuilder(first: Any, add: (Rotate.Builder.() -> Unit)?): Rotate {
    val builder = Rotate.Builder(first)
    add?.let { builder.it() }
    return builder.build()
}