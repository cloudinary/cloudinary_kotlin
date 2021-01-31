package com.cloudinary.transformation.reshape

import com.cloudinary.transformation.Color
import com.cloudinary.transformation.Param
import com.cloudinary.transformation.TransformationComponentBuilder
import com.cloudinary.transformation.effect.EffectBuilder
import com.cloudinary.transformation.joinWithValues
import com.cloudinary.transformation.layer.buildLayerComponent
import com.cloudinary.transformation.layer.position.Position
import com.cloudinary.transformation.layer.source.FetchSource
import com.cloudinary.transformation.layer.source.ImageSource
import com.cloudinary.transformation.layer.source.Source
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
    private val source: Source,
    private val position: Position? = null
) : Reshape() {

    companion object {
        fun source(source: Source, options: (Builder.() -> Unit)? = null): CutByImage {
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

    class Builder(source: Source) : BaseBuilder() {
        init {
            this.source = source
        }
    }

    abstract class BaseBuilder : TransformationComponentBuilder {
        protected var position: Position? = null
        protected var source: Source? = null

        fun position(position: Position) = apply { this.position = position }
        fun position(position: (Position.Builder.() -> Unit)? = null) = apply {
            val builder = Position.Builder()
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

class Trim private constructor(
    private val colorSimilarity: Any?,
    private val colorOverride: Any?
) : Reshape() {
    override fun toString(): String {
        return "e_trim".joinWithValues(colorSimilarity, colorOverride)
    }

    class Builder : EffectBuilder {
        private var colorSimilarity: Any? = null
        private var colorOverride: Any? = null


        fun colorSimilarity(colorSimilarity: Int) = apply { this.colorSimilarity = colorSimilarity }

        fun colorSimilarity(colorSimilarity: Any) = apply { this.colorSimilarity = colorSimilarity }

        fun colorOverride(colorOverride: Color) = apply { this.colorOverride = colorOverride }

        override fun build() = Trim(colorSimilarity, colorOverride)
    }
}
