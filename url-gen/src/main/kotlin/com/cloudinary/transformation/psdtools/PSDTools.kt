package com.cloudinary.transformation.psdtools

import com.cloudinary.transformation.Action

abstract class PSDTools : Action {
    companion object {
        fun clip(options: (Clip.Builder.() -> Unit)? = null): Clip {
            val builder = Clip.Builder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun smartObject(options: (SmartObject.Builder.() -> Unit)?): SmartObject {
            val builder = SmartObject.Builder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun getLayer(options: (GetLayer.Builder.() -> Unit)): GetLayer {
            val builder = GetLayer.Builder()
            builder.options()
            return builder.build()
        }
    }
}
