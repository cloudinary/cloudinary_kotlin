package com.cloudinary.transformation

class Format(private val action: Action) : Action by action {

    companion object {
        fun auto(options: (FormatBuilder.() -> Unit)? = null) = buildFormat(options, "auto")
        fun jpg(options: (FormatBuilder.() -> Unit)? = null) = buildFormat(options, "jpg")
        fun webp(options: (FormatBuilder.() -> Unit)? = null) = buildFormat(options, "webp")
        fun jp2(options: (FormatBuilder.() -> Unit)? = null) = buildFormat(options, "jp2")
        fun png(options: (FormatBuilder.() -> Unit)? = null) = buildFormat(options, "png")
        fun webm(options: (FormatBuilder.() -> Unit)? = null) = buildFormat(options, "webm")
        fun mp4(options: (FormatBuilder.() -> Unit)? = null) = buildFormat(options, "mp4")
        fun gif(options: (FormatBuilder.() -> Unit)? = null) = buildFormat(options, "gif")
    }
}

internal fun buildFormat(
    options: (FormatBuilder.() -> Unit)?,
    format: String
): Format {
    val builder = FormatBuilder(format)
    options?.let { builder.options() }
    return builder.build()
}

class FormatBuilder(private val format: String) : TransformationComponentBuilder {
    private var lossy: Boolean = false

    fun lossy(lossy: Boolean) = apply { this.lossy = lossy }

    override fun build() = Format(
        ParamsAction(
            listOfNotNull(
                format.cldAsFormat(),
                if (lossy) Flag.lossy().cldAsFlag() else null
            )
        )
    )
}