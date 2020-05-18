package com.cloudinary.transformation


class ClippingPath private constructor(params: Map<String, Param>) :
    ParamsAction<ClippingPath>(params) {
    private constructor(page: Param? = null, evenOdd: Boolean = false) : this(buildParams(page, evenOdd))

    override fun create(params: Map<String, Param>) = ClippingPath(params)

    class Builder : TransformationComponentBuilder {
        private var index: Int? = null
        private var path: String? = null
        private var evenOdd: Boolean = false

        fun index(index: Int) = apply { this.index = index }
        fun path(path: String) = apply { this.path = path }
        fun evenOdd(evenOdd: Boolean) = apply { this.evenOdd = evenOdd }

        override fun build() = ClippingPath(buildParams(index ?: path, evenOdd))
    }
}

fun clippingPath(clippingPath: (ClippingPath.Builder.() -> Unit)? = null): ClippingPath {
    val newBuilder = ClippingPath.Builder()
    if (clippingPath != null) newBuilder.clippingPath()
    return newBuilder.build()
}

private fun buildParams(pageParamValue: Any?, evenOdd: Boolean): Map<String, Param> {
    val page = pageParamValue?.cldAsPage()
    val clip = FlagsParam(if (evenOdd) Flag.ClipEvenOdd() else Flag.Clip())
    val clipPair = Pair(clip.key, clip)

    return if (page != null) {
        mapOf(clipPair, Pair(page.key, page))
    } else {
        mapOf(clipPair)
    }
}