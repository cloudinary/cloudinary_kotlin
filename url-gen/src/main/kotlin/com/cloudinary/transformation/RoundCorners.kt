package com.cloudinary.transformation

class RoundCorners(private val action: Action) : Action by action {
    companion object {
        fun radius(vararg pixels: Int) = roundCorners(pixels.toList())
        fun radius(vararg pixels: Any) = roundCorners(pixels.toList())
        fun max() = roundCorners(listOf("max"))
    }
}

fun roundCorners(values: List<Any>): RoundCorners {
    require(values.size in 1..4) { "The number of values must be between 1 and 4" }
    return RoundCorners(ParamValue(values).cldAsRadius().asAction())
}

