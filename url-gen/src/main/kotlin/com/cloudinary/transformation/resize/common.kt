package com.cloudinary.transformation.resize

import com.cloudinary.transformation.Action
import com.cloudinary.transformation.Param
import com.cloudinary.transformation.TransformationComponentBuilder
import com.cloudinary.transformation.expression.Expression
import com.cloudinary.transformation.toComponentString

class Dimensions(val width: Any? = null, val height: Any? = null, val aspectRatio: Any? = null)

abstract class Resize(
    val dimensions: Dimensions,
    val mode: ResizeMode?,
    val ignoreAspectRatio: Boolean?
) : Action {

    abstract val actionType: String

    internal open fun params(): Collection<Param?> {
        return listOf(
            Param("c", actionType),
            dimensions.width?.let { Param("w", it) },
            dimensions.height?.let { Param("h", it) },
            dimensions.aspectRatio?.let { Param("ar", it) },
            if (ignoreAspectRatio == true) Param("fl", "ignore_aspect_ratio") else null,
            mode?.let { Param("fl", mode) }
        )
    }

    override fun toString(): String {
        return params().toComponentString()
    }

    companion object {
        fun scale(width: Int? = null, height: Int? = null, options: (Scale.Builder.() -> Unit)? = null): Resize {
            val builder = Scale.Builder()
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun crop(width: Int? = null, height: Int? = null, options: (Crop.Builder.() -> Unit)? = null): Resize {
            val builder = Crop.Builder()
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun fit(width: Int? = null, height: Int? = null, options: (Fit.Builder.() -> Unit)? = null): Resize {
            val builder = Fit.Builder()
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun limitFit(width: Int? = null, height: Int? = null, options: (LimitFit.Builder.() -> Unit)? = null): Resize {
            val builder = LimitFit.Builder()
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun minimumFit(
            width: Int? = null,
            height: Int? = null,
            options: (MinimumFit.Builder.() -> Unit)? = null
        ): Resize {
            val builder = MinimumFit.Builder()
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun fill(width: Int? = null, height: Int? = null, options: (Fill.Builder.() -> Unit)? = null): Resize {
            val builder = Fill.Builder()
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun limitFill(
            width: Int? = null,
            height: Int? = null,
            options: (LimitFill.Builder.() -> Unit)? = null
        ): Resize {
            val builder = LimitFill.Builder()
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun pad(width: Int? = null, height: Int? = null, options: (Pad.Builder.() -> Unit)? = null): Resize {
            val builder = Pad.Builder()
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun limitPad(
            width: Int? = null,
            height: Int? = null,
            options: (LimitPad.Builder.() -> Unit)? = null
        ): Resize {
            val builder = LimitPad.Builder()
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun minimumPad(
            width: Int? = null,
            height: Int? = null,
            options: (MinimumPad.Builder.() -> Unit)? = null
        ): Resize {
            val builder = MinimumPad.Builder()
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun fillPad(
            width: Int? = null,
            height: Int? = null,
            options: (FillPad.Builder.() -> Unit)? = null
        ): Resize {
            val builder = FillPad.Builder()
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun thumbnail(
            width: Int? = null,
            height: Int? = null,
            options: (Thumbnail.Builder.() -> Unit)? = null
        ): Resize {
            val builder = Thumbnail.Builder()
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun imaggaCrop(
            width: Int? = null,
            height: Int? = null,
            options: (ImaggaCrop.Builder.() -> Unit)? = null
        ): Resize {
            val builder = ImaggaCrop.Builder()
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun imaggaScale(
            width: Int? = null,
            height: Int? = null,
            options: (ImaggaScale.Builder.() -> Unit)? = null
        ): Resize {
            val builder = ImaggaScale.Builder()
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun generic(cropMode: String, options: (GenericResize.Builder.() -> Unit)? = null): Resize {
            val builder = GenericResize.Builder(cropMode)
            options?.let { builder.it() }
            return builder.build()
        }
    }
}

abstract class BaseBuilder<B> : TransformationComponentBuilder {
    protected var width: Any? = null
    protected var height: Any? = null
    protected var aspectRatio: Any? = null
    protected var mode: ResizeMode? = null
    protected var ignoreAspectRatio: Boolean? = null

    abstract fun getThis(): B

    private fun width(width: Any): B {
        this.width = width
        return getThis()
    }

    fun width(width: Int) = width(width as Any)
    fun width(width: Float) = width(width as Any)
    fun width(width: String) = width(width as Any)
    fun width(width: Expression) = width(width as Any)

    private fun height(height: Any): B {
        this.height = height
        return getThis()
    }

    fun height(height: String) = height(height as Any)
    fun height(height: Expression) = height(height as Any)
    fun height(height: Int) = height(height as Any)
    fun height(height: Float) = height(height as Any)

    private fun aspectRatio(aspectRatio: Any): B {
        this.aspectRatio = aspectRatio
        return getThis()
    }

    fun aspectRatio(aspectRatio: String) = aspectRatio(aspectRatio as Any)
    fun aspectRatio(aspectRatio: Expression) = aspectRatio(aspectRatio as Any)
    fun aspectRatio(aspectRatio: Int) = aspectRatio(aspectRatio as Any)
    fun aspectRatio(aspectRatio: Float) = aspectRatio(aspectRatio as Any)

    fun resizeMode(mode: ResizeMode): B {
        this.mode = mode
        return getThis()
    }

    fun ignoreAspectRatio(ignoreAspectRatio: Boolean = true): B {
        this.ignoreAspectRatio = ignoreAspectRatio
        return getThis()
    }
}

sealed class ResizeMode(private val value: String) {
    class relative : ResizeMode("relative")
    class regionRelative : ResizeMode("region_relative")

    override fun toString(): String {
        return value
    }
}
