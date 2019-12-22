package com.cloudinary.transformation


class ClippingPath private constructor(params: Map<String, Param>) :
    Action<ClippingPath>(params) {
    private constructor(page: Param? = null, evenOdd: Boolean = false) : this(buildParams(page, evenOdd))

    override fun create(params: Map<String, Param>) = ClippingPath(params)

    class Builder : TransformationComponentBuilder {
        private var page: Int? = null
        private var path: String? = null
        private var evenOdd: Boolean = false

        fun page(page: Int) = apply { this.page = page }
        fun path(path: String) = apply { this.path = path }
        fun evenOdd(evenOdd: Boolean) = apply { this.evenOdd = evenOdd }

        override fun build() = ClippingPath(buildParams(page ?: path, evenOdd))
    }
}

fun clippingPath(clippingPath: (ClippingPath.Builder.() -> Unit)? = null): ClippingPath {
    val newBuilder = ClippingPath.Builder()
    if (clippingPath != null) newBuilder.clippingPath()
    return newBuilder.build()
}

private fun buildParams(pageParamValue: Any?, evenOdd: Boolean): Map<String, Param> {
    val page = pageParamValue?.cldAsPageParam()
    val clip = FlagsParam(if (evenOdd) FlagKey.CLIP_EVENODD() else FlagKey.CLIP())
    val clipPair = Pair(clip.key, clip)

    return if (page != null) {
        mapOf(clipPair, Pair(page.key, page))
    } else {
        mapOf(clipPair)
    }
}