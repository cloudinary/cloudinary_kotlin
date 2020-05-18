package com.cloudinary.transformation

class RoundCorners private constructor(params: Map<String, Param>) :
    ParamsAction<RoundCorners>(params) {

    constructor(values: List<Any>) : this(Param("radius", "r", ParamValue(values)).let { mapOf(Pair(it.key, it)) })

    override fun create(params: Map<String, Param>) = RoundCorners(params)

    companion object {
        fun radius(pixels: Int) = RoundCorners(listOf(pixels))
        fun max() = RoundCorners(listOf("max"))
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

        override fun build() = RoundCorners(corners)
    }
}

