package com.cloudinary.transformation.background

import com.cloudinary.transformation.*

class Background(private val action: Action) : Action by action {

    companion object {
        fun auto() = autoBackground()
        fun blurred(options: (BlurredBackgroundBuilder.() -> Unit)? = null): Background {
            val builder = BlurredBackgroundBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun border(options: (BorderBackgroundBuilder.() -> Unit)? = null): Background {
            val builder = BorderBackgroundBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun predominant(options: (PredominantBackgroundBuilder.() -> Unit)? = null): Background {
            val builder = PredominantBackgroundBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun color(color: Color) =
            background(color.cldAsBackground())

        fun color(color: String) = background(
            Color.parseString(color).cldAsBackground()
        )
    }
}

fun background(param: Param) =
    Background(
        ParamsAction(param)
    )

fun autoBackground(vararg values: Any?) = background((listOf("auto") + values).cldAsBackground())

