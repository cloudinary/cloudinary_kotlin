package com.cloudinary.transformation.background

import com.cloudinary.transformation.Color
import com.cloudinary.transformation.TransformationDsl

abstract class Background {

    override fun toString(): String {
        return getValues()
    }

    protected abstract fun getValues(): String

    companion object {

        fun blurred(options: (BlurredBackground.Builder.() -> Unit)? = null) =
            buildBackground(BlurredBackground.Builder(), options)

        fun auto() = AutoBackground()

        // auto:border
        fun border(options: (BorderBackground.Builder.() -> Unit)? = null) =
            buildBackground(BorderBackground.Builder(), options)

        // auto:border_gradient
        fun borderGradient(options: (BorderGradientBackground.Builder.() -> Unit)? = null) =
            buildBackground(BorderGradientBackground.Builder(), options)

        // auto:predominant
        fun predominant(options: (PredominantBackground.Builder.() -> Unit)? = null) =
            buildBackground(PredominantBackground.Builder(), options)

        // auto:predominant
        fun predominantGradient(options: (PredominantGradientBackground.Builder.() -> Unit)? = null) =
            buildBackground(PredominantGradientBackground.Builder(), options)

        fun color(color: Color) = ColorBackground(color)

        fun color(color: String) = color(Color.parseString(color))
    }
}

@TransformationDsl
interface BackgroundBuilder<T : Background> {
    fun build(): T
}

private fun <P : Background, T : BackgroundBuilder<P>> buildBackground(
    builder: T,
    options: (T.() -> Unit)?
): Background {
    options?.let { builder.it() }
    return builder.build()
}