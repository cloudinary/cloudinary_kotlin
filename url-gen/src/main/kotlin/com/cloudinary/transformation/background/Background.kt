package com.cloudinary.transformation.background

import com.cloudinary.transformation.*

class PadBackground(action: ParamsAction) : Background(action) {
    companion object {

        fun blurred(options: (BlurredBackgroundBuilder.() -> Unit)? = null): PadBackground {
            val builder = BlurredBackgroundBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun auto() = AutoBackgroundBuilder().build()

        // auto:border
        fun border(options: (AutoBackgroundBuilder.() -> Unit)? = null): PadBackground {
            val builder = AutoBackgroundBuilder("border")
            options?.let { builder.it() }
            return builder.build()
        }

        // auto:predominant
        fun predominant(options: (AutoBackgroundBuilder.() -> Unit)? = null): PadBackground {
            val builder = AutoBackgroundBuilder("predominant")
            options?.let { builder.it() }
            return builder.build()
        }

        fun color(color: Color) = Background.color(color)

        fun color(color: String) = Background.color(color)
    }
}

open class Background(private val action: ParamsAction) : Action by action {
    internal fun getParams() = action.params

    companion object {
        fun color(color: Color) = Background(color.cldAsBackground().asAction())
        fun color(color: String) = color(Color.parseString(color))
    }
}