package com.cloudinary.transformation

import com.cloudinary.transformation.expression.Expression

class Rotate private constructor(private val rotation: Any) : Action {

    override fun toString(): String {
        return "a_$rotation"
    }

    companion object {
        fun byAngle(angle: Int) = Rotate(angle)
        fun byAngle(angle: Expression) = Rotate(angle)
        fun byAngle(angle: String) = Rotate(angle)
        fun mode(mode: Mode) = Rotate(mode)
    }
}

enum class Mode(private val value: String) {
    AUTO_RIGHT("auto_right"),
    AUTO_LEFT("auto_left"),
    IGNORE("ignore"),
    VERTICAL_FLIP("vflip"),
    HORIZONTAL_FLIP("hflip");

    override fun toString(): String {
        return value
    }
}
