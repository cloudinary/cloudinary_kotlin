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
        fun mode(mode: RotationMode) = Rotate(mode)
    }
}

class RotationMode(private val value: String) {
    companion object {
        private val autoRight = RotationMode("auto_right")
        fun autoRight() = autoRight
        private val autoLeft = RotationMode("auto_left")
        fun autoLeft() = autoLeft
        private val ignore = RotationMode("ignore")
        fun ignore() = ignore
        private val verticalFlip = RotationMode("vflip")
        fun verticalFlip() = verticalFlip
        private val horizontalFlip = RotationMode("hflip")
        fun horizontalFlip() = horizontalFlip
    }

    override fun toString(): String {
        return value
    }
}
