package com.cloudinary.transformation.layer.source

import com.cloudinary.transformation.FormatType
import com.cloudinary.transformation.Param
import com.cloudinary.transformation.TransformationDsl
import com.cloudinary.util.cldJoinWithOrReturnOriginal
import com.cloudinary.util.cldToBase64

class FetchSource internal constructor(
    private val remoteUrl: String,
    private val format: Any? = null
) : LayerSource {
    override fun extraComponents(): List<Param> {
        return emptyList()
    }

    override fun toString(): String {
        return "fetch:${remoteUrl.cldToBase64().cldJoinWithOrReturnOriginal(".", format)}"
    }

    companion object {
        fun url(url: String, options: (Builder.() -> Unit)? = null): FetchSource {
            val builder = Builder(url)
            options?.let { builder.it() }
            return builder.build()
        }
    }

    @TransformationDsl
    class Builder(private val remoteUrl: String) {
        private var format: Any? = null

        fun format(format: FormatType) = apply { this.format = format }
        fun format(format: String) = apply { this.format = format }

        fun build() = FetchSource(remoteUrl, format)
    }
}
