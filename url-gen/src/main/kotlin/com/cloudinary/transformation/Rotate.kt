package com.cloudinary.transformation

class Rotate private constructor(params: Map<String, Param>) :
    Action<Rotate>(params) {
    override fun create(params: Map<String, Param>) = Rotate(params)

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
                Param("angle", "a", ParamValue(values.cldAsNonNullSimpleValues(), ".")).let { mapOf(Pair(it.key, it)) }
            )
    }
}

//fun rotate(angle: (Rotate.Builder.() -> Unit)? = null): Rotate {
//    val newBuilder = Rotate.Builder()
//    if (angle != null) newBuilder.angle()
//    return newBuilder.build()
//}
