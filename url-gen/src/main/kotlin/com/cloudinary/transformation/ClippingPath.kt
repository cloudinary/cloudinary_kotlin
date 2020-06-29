package com.cloudinary.transformation


class ClippingPath(private val action: Action) : Action by action {
    companion object {
        fun number(number: Int, options: (Builder.() -> Unit)? = null): ClippingPath {
            val builder = Builder().number(number)
            options?.let { builder.it() }
            return builder.build()
        }

        fun name(name: String, options: (Builder.() -> Unit)? = null): ClippingPath {
            val builder = Builder().name(name)
            options?.let { builder.it() }
            return builder.build()
        }
    }

    class Builder : TransformationComponentBuilder {
        private var number: Int? = null
        private var name: String? = null
        private var evenOdd: Boolean = false

        fun number(number: Int) = apply { this.number = number }
        fun name(name: String) = apply { this.name = name }
        fun evenOdd(evenOdd: Boolean = true) = apply { this.evenOdd = evenOdd }

        override fun build(): ClippingPath {
            val finalName = name
            val value = number ?: if (finalName != null) NamedValue("name", finalName) else null
            return ClippingPath(buildAction(value, evenOdd))
        }
    }
}


internal fun clippingPath(clippingPath: (ClippingPath.Builder.() -> Unit)? = null): ClippingPath {
    val newBuilder = ClippingPath.Builder()
    if (clippingPath != null) newBuilder.clippingPath()
    return newBuilder.build()
}

private fun buildAction(pageParamValue: Any?, evenOdd: Boolean): ParamsAction {
    val page = pageParamValue?.cldAsPage()
    val clip = FlagsParam(if (evenOdd) Flag.clipEvenOdd() else Flag.clip())

    return ParamsAction(page, clip)
}