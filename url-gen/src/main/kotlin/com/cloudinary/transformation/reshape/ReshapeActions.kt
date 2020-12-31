package com.cloudinary.transformation.reshape

import com.cloudinary.transformation.Action
import com.cloudinary.transformation.Param
import com.cloudinary.transformation.TransformationComponentBuilder
import com.cloudinary.transformation.joinWithValues
import com.cloudinary.transformation.layer.buildLayerComponent
import com.cloudinary.transformation.layer.position.LayerPosition
import com.cloudinary.transformation.layer.source.FetchSource
import com.cloudinary.transformation.layer.source.ImageSource
import com.cloudinary.transformation.layer.source.LayerSource
import com.cloudinary.transformation.layer.source.TextSource
import com.cloudinary.util.cldRanged

class Shear private constructor(
    private val skewX: Any? = null,
    private val skewY: Any? = null
) : Reshape() {
    override fun toString(): String {
        return "e_shear".joinWithValues(skewX, skewY)
    }

    class Builder : TransformationComponentBuilder {
        private var skewX: Any? = null
        private var skewY: Any? = null

        fun skewX(skewX: Any) = apply { this.skewX = skewX }
        fun skewY(skewY: Any) = apply { this.skewY = skewY }
        fun skewX(skewX: Int) = apply { this.skewX = skewX }
        fun skewY(skewY: Int) = apply { this.skewY = skewY }

        override fun build() = Shear(skewX, skewY)
    }
}

class DistortArc(private val degrees: Any) : Reshape() {
    init {
        degrees.cldRanged(-360, 360)
    }

    override fun toString(): String {
        return "e_distort".joinWithValues("arc", degrees)
    }
}

class Distort internal constructor(private val points: List<Any>) : Reshape() {
    override fun toString(): String {
        return "e_distort:${points.joinToString(separator = ":")}"
    }
}

class CutByImage private constructor(
    private val source: LayerSource,
    private val position: LayerPosition? = null
) : Action {

    companion object {
        fun source(source: LayerSource, options: (Builder.() -> Unit)? = null): CutByImage {
            val builder = Builder(source)
            options?.let { builder.it() }
            return builder.build()
        }

        fun image(options: (ImageBuilder.() -> Unit)? = null): CutByImage {
            val builder = ImageBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun fetch(options: (FetchBuilder.() -> Unit)? = null): CutByImage {
            val builder = FetchBuilder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun text(options: (TextBuilder.() -> Unit)? = null): CutByImage {
            val builder = TextBuilder()
            options?.let { builder.it() }
            return builder.build()
        }
    }

    override fun toString(): String {
        return buildLayerComponent(
            "l",
            source,
            position,
            extras = listOf(Param("fl", "cutter"))
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

    class Builder(source: LayerSource) : BaseBuilder() {
        init {
            this.source = source
        }
    }

    abstract class BaseBuilder : TransformationComponentBuilder {
        protected var position: LayerPosition? = null
        protected var source: LayerSource? = null

        fun position(position: LayerPosition) = apply { this.position = position }
        fun position(position: (LayerPosition.Builder.() -> Unit)? = null) = apply {
            val builder = LayerPosition.Builder()
            position?.let { builder.it() }
            position(builder.build())
        }

        override fun build(): CutByImage {
            val safeSource = source
            require(safeSource != null) { "A source must be provided" }
            return CutByImage(safeSource, position)
        }
    }
}
