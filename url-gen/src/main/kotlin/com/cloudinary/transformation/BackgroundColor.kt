package com.cloudinary.transformation

class BackgroundColor(private val color: Color) : Action {

    companion object {
        fun color(color: Color) = BackgroundColor(color)
        fun color(color: String) = color(Color.parseString(color))
    }

    override fun toString(): String {
        return "b_$color"
    }
}