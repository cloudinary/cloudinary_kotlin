package com.cloudinary.transformation.reshape

import com.cloudinary.transformation.Action
import com.cloudinary.transformation.effect.effectAction
import com.cloudinary.util.cldRanged

class Reshape(private val action: Action) : Action by action {

    companion object {

        fun shear(options: ShearBuilder.() -> Unit): Reshape {
            val builder = ShearBuilder()
            builder.options()
            return builder.build()
        }

        fun distort(options: DistortBuilder.() -> Unit): Reshape {
            val builder = DistortBuilder()
            builder.options()
            return builder.build()
        }

        fun distortArc(degrees: Int) = reshape("distort", "arc", degrees.cldRanged(-360, 360))
    }

}

internal fun reshape(name: String, vararg values: Any?) = Reshape(effectAction(name, *values))

