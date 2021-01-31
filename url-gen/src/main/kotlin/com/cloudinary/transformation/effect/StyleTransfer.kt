package com.cloudinary.transformation.effect

import com.cloudinary.transformation.Param
import com.cloudinary.transformation.TransformationComponentBuilder
import com.cloudinary.transformation.TransformationDsl
import com.cloudinary.transformation.joinWithValues
import com.cloudinary.transformation.layer.buildLayerComponent
import com.cloudinary.transformation.layer.source.FetchSource
import com.cloudinary.transformation.layer.source.ImageSource
import com.cloudinary.transformation.layer.source.Source
import com.cloudinary.transformation.layer.source.TextSource
import com.cloudinary.util.cldRanged

class StyleTransfer private constructor(
    private val source: Source,
    private val strength: Any? = null,
    private val preserveColor: Boolean? = null
) : Effect() {

    init {
        strength?.cldRanged(0, 100)
    }

    companion object {
        fun source(source: ImageSource, options: (Builder.() -> Unit)? = null): StyleTransfer {
            val builder = Builder(source)
            options?.let { builder.it() }
            return builder.build()
        }

        fun source(source: FetchSource, options: (Builder.() -> Unit)? = null): StyleTransfer {
            val builder = Builder(source)
            options?.let { builder.it() }
            return builder.build()
        }

        fun image(options: (ImageBuilder.() -> Unit)? = null): StyleTransfer {
            val builder = ImageBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun fetch(options: (FetchBuilder.() -> Unit)? = null): StyleTransfer {
            val builder = FetchBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun text(options: (TextBuilder.() -> Unit)? = null): StyleTransfer {
            val builder = TextBuilder()
            options?.let { builder.it() }
            return builder.build()
        }
    }

    override fun toString(): String {
        return buildLayerComponent(
            "l",
            source,
            extras = listOfNotNull(
                Param(
                    "e", "style_transfer"
                        .joinWithValues(
                            if (preserveColor == true) "preserve_color" else null,
                            strength?.cldRanged(0, 100)
                        )
                )
            )
        )
    }

    @TransformationDsl
    class Builder(source: Source) : BaseBuilder(), EffectBuilder {
        init {
            this.source = source
        }
    }

    class FetchBuilder : BaseBuilder() {
        fun source(publicId: String, source: (FetchSource.Builder.() -> Unit)? = null) = apply {
            val builder = FetchSource.Builder(publicId)
            source?.let { builder.it() }
            source(builder.build())
        }

        fun source(source: FetchSource) = apply { this.source = source }
    }

    class TextBuilder : BaseBuilder() {
        fun source(publicId: String, source: (TextSource.Builder.() -> Unit)? = null) = apply {
            val builder = TextSource.Builder(publicId)
            source?.let { builder.it() }
            source(builder.build())
        }

        fun source(source: TextSource) = apply { this.source = source }
    }

    class ImageBuilder : BaseBuilder() {
        fun source(publicId: String, source: (ImageSource.Builder.() -> Unit)? = null) = apply {
            val builder = ImageSource.Builder(publicId)
            source?.let { builder.it() }
            source(builder.build())
        }

        fun source(source: ImageSource) = apply { this.source = source }
    }

    abstract class BaseBuilder : TransformationComponentBuilder {
        protected var source: Source? = null
        private var strength: Any? = null
        private var preserveColor: Boolean = false

        fun strength(strength: Int) = apply { this.strength = strength }
        fun strength(strength: Any) = apply { this.strength = strength }

        fun preserveColor(preserveColor: Boolean = true) = apply { this.preserveColor = preserveColor }

        override fun build(): StyleTransfer {
            val safeSource = source
            require(safeSource != null) { "A source must be provided" }
            return StyleTransfer(safeSource, strength, preserveColor)
        }
    }
}