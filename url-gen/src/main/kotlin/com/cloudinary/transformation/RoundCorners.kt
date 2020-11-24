package com.cloudinary.transformation

class RoundCorners(private val values: List<Any>) : Action {
    init {
        require(values.size in 1..4) { "The values must be either 'max' literal or 1 to 4 radius values." }
    }

    companion object {
        fun byRadius(vararg pixels: Int) = RoundCorners(pixels.toList())
        fun byRadius(vararg pixels: Any) = RoundCorners(pixels.toList())
        fun max() = RoundCorners(listOf("max"))
    }

    override fun toString(): String {
        return "r_${values.joinToString(":")}"
    }
}
