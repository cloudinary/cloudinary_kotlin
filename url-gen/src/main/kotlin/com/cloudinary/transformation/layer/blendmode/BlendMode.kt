package com.cloudinary.transformation.layer

import com.cloudinary.transformation.TransformationDsl
import com.cloudinary.transformation.expression.Expression
import com.cloudinary.transformation.joinWithValues
import com.cloudinary.transformation.layer.blendmode.AntiRemoval

open class BlendMode(private val value: String) {
    companion object {
        private val screen = BlendMode("screen")
        fun screen() = screen
        private val multiply = BlendMode("multiply")
        fun multiply() = multiply
        private val overlay = BlendMode("overlay")
        fun overlay() = overlay
        private val mask = BlendMode("mask")
        fun mask() = mask
        private val antiRemoval = BlendMode("anti_removal")
        fun antiRemoval(options: (AntiRemoval.Builder.() -> Unit)? = null) = buildBlendMode(
            AntiRemoval.Builder(), options)
    }

    override fun toString(): String {
        return value
    }

}
interface OverlayBlendModeBuilder : BlendModeComponentBuilder

private fun <T : OverlayBlendModeBuilder> buildBlendMode(builder: T, options: (T.() -> Unit)?): BlendMode {
    options?.let { builder.it() }
    return builder.build()
}

@TransformationDsl
interface BlendModeComponentBuilder {
    fun build(): BlendMode
}

abstract class BlendModeLevelBuilder : OverlayBlendModeBuilder {
    protected var level: Any? = null

    fun level(level: Int) = apply { this.level = level }
    fun level(level: Expression) = apply { this.level = level }
    fun level(level: String) = apply { this.level = level }
}

abstract class LevelBlendMode(protected val name: String, protected val level: Any? = null) : BlendMode(name) {
    override fun toString(): String {
        return "$name".joinWithValues(level)
    }
}

