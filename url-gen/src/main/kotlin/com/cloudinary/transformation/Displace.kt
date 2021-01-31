package com.cloudinary.transformation

import com.cloudinary.transformation.layer.buildLayerComponent
import com.cloudinary.transformation.layer.source.FetchSource
import com.cloudinary.transformation.layer.source.ImageSource
import com.cloudinary.transformation.layer.source.Source
import com.cloudinary.transformation.layer.source.TextSource

class Displace private constructor(
    private val source: Source,
    private val x: Any? = null,
    private val y: Any? = null
) : Action {

    companion object {
        fun source(source: Source, options: (Builder.() -> Unit)? = null): Displace {
            val builder = Builder(source)
            options?.let { builder.it() }
            return builder.build()
        }

        fun image(options: (ImageBuilder.() -> Unit)? = null): Displace {
            val builder = ImageBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun fetch(options: (FetchBuilder.() -> Unit)? = null): Displace {
            val builder = FetchBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun text(options: (TextBuilder.() -> Unit)? = null): Displace {
            val builder = TextBuilder()
            options?.let { builder.it() }
            return builder.build()
        }
    }

    override fun toString(): String {
        return buildLayerComponent(
            "l",
            source,
            extras = listOfNotNull(Param("e", "displace"), x?.let { Param("x", it) }, y?.let { Param("y", it) })
        )
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

    class Builder(source: Source) : BaseBuilder() {
        init {
            this.source = source
        }
    }

    abstract class BaseBuilder : TransformationComponentBuilder {
        protected var source: Source? = null
        protected var x: Any? = null
        protected var y: Any? = null

        fun x(x: Int) = x(x as Any)
        fun x(x: Float) = x(x as Any)
        fun x(x: Any) = apply { this.x = x }

        fun y(y: Int) = y(y as Any)
        fun y(y: Float) = y(y as Any)
        fun y(y: Any) = apply { this.y = y }

        override fun build(): Displace {
            val safeSource = source
            require(safeSource != null) { "A source must be provided" }
            require(x != null || y != null) { "Displace requires either X or y (or both) to be provided" }
            return Displace(safeSource, x, y)
        }
    }
}
