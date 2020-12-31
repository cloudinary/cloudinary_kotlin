package com.cloudinary.transformation.background

import com.cloudinary.transformation.Color

class ColorBackground(private val color: Color) : Background() {
    override fun getValues() = color.toString()
}