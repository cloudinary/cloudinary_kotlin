package com.cloudinary.transformation.layer.blendmode

import com.cloudinary.transformation.TransformationComponentBuilder
import com.cloudinary.transformation.expression.Expression
import com.cloudinary.transformation.layer.BlendModeLevelBuilder
import com.cloudinary.transformation.layer.LevelBlendMode
import com.cloudinary.util.cldRanged

class AntiRemoval(level: Any? = null) : LevelBlendMode("anti_removal", level?.cldRanged(0, 100)) {
    class Builder : BlendModeLevelBuilder() {
        override fun build() = AntiRemoval(level)
    }
}

abstract class BaseBuilder : TransformationComponentBuilder {
    protected var level: Any? = null

    fun level(level: Int) = apply { this.level = level }
    fun level(level: Expression) = apply { this.level = level }
    fun level(level: String) = apply { this.level = level }
}
