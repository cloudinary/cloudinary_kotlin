package com.cloudinary.transformation


class ClippingPath(private val action: Action) : Action by action {

    class Builder : TransformationComponentBuilder {
        private var index: Int? = null
        private var path: String? = null
        private var evenOdd: Boolean = false

        fun index(index: Int) = apply { this.index = index }
        fun path(path: String) = apply { this.path = path }
        fun evenOdd(evenOdd: Boolean) = apply { this.evenOdd = evenOdd }

        override fun build() = ClippingPath(buildAction(index ?: path, evenOdd))
    }
}

fun clippingPath(clippingPath: (ClippingPath.Builder.() -> Unit)? = null): ClippingPath {
    val newBuilder = ClippingPath.Builder()
    if (clippingPath != null) newBuilder.clippingPath()
    return newBuilder.build()
}

// TODO simplify
private fun buildAction(pageParamValue: Any?, evenOdd: Boolean): ParamsAction {
    val page = pageParamValue?.cldAsPage()
    val clip = FlagsParam(if (evenOdd) Flag.ClipEvenOdd() else Flag.Clip())
    val clipPair = Pair(clip.key, clip)

    return ParamsAction(page, clip)
}