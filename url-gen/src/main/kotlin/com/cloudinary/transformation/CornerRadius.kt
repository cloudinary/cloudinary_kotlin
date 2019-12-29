package com.cloudinary.transformation

class CornerRadius private constructor(params: Map<String, Param>) :
    ParamsAction<CornerRadius>(params) {

    constructor(values: List<Any>) : this(Param("radius", "r", ParamValue(values)).let { mapOf(Pair(it.key, it)) })

    override fun create(params: Map<String, Param>) = CornerRadius(params)

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


        override fun build() = CornerRadius(corners)
    }
}

