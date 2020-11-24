package com.cloudinary.transformation.background

import com.cloudinary.transformation.Color

abstract class Background {

    override fun toString(): String {
        return getValues()
    }

    protected abstract fun getValues(): String

    companion object {

        fun blurred(options: (BlurredBackground.Builder.() -> Unit)? = null): Background {
            val builder = BlurredBackground.Builder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun auto() = AutoBackground.Builder().build()

        // auto:border
        fun border(options: (AutoBackground.Builder.() -> Unit)? = null): Background {
            val builder = AutoBackground.Builder("border")
            options?.let { builder.it() }
            return builder.build()
        }

        // auto:predominant
        fun predominant(options: (AutoBackground.Builder.() -> Unit)? = null): Background {
            val builder = AutoBackground.Builder("predominant")
            options?.let { builder.it() }
            return builder.build()
        }

        fun color(color: Color) = ColorBackground(color)

        fun color(color: String) = color(Color.parseString(color))
    }
}