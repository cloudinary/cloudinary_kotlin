package com.cloudinary.transformation

class Background(private val action: Action) : Action by action {

    companion object {
        fun color(color: Color) = background(color.cldAsBackground())
        fun color(color: String) = background(Color.parseString(color).cldAsBackground())
    }
}

fun background(param: Param) = Background(ParamsAction(param))