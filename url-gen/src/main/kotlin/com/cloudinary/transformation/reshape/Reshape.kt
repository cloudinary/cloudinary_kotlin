package com.cloudinary.transformation.reshape

import com.cloudinary.transformation.Action

abstract class Reshape : Action {

    companion object {

        fun shear(options: Shear.Builder.() -> Unit): Shear {
            val builder = Shear.Builder()
            builder.options()
            return builder.build()
        }

        fun distort(options: Distort.Builder.() -> Unit): Distort {
            val builder = Distort.Builder()
            builder.options()
            return builder.build()
        }

        fun distortArc(degrees: Int) = DistortArc(degrees)
    }
}
