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

        fun auto() = AutoBackground()

        // auto:border
        fun border(options: (BorderBackground.Builder.() -> Unit)? = null): Background {
            val builder = BorderBackground.Builder()
            options?.let { builder.it() }
            return builder.build()
        }

        // auto:border_gradient
        fun borderGradient(options: (BorderGradientBackground.Builder.() -> Unit)? = null): Background {
            val builder = BorderGradientBackground.Builder()
            options?.let { builder.it() }
            return builder.build()
        }

        // auto:predominant
        fun predominant(options: (PredominantBackground.Builder.() -> Unit)? = null): Background {
            val builder = PredominantBackground.Builder()
            options?.let { builder.it() }
            return builder.build()
        }

        // auto:predominant
        fun predominantGradient(options: (PredominantGradientBackground.Builder.() -> Unit)? = null): Background {
            val builder = PredominantGradientBackground.Builder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun color(color: Color) = ColorBackground(color)

        fun color(color: String) = color(Color.parseString(color))
    }
}