package com.cloudinary.transformation.resize

import com.cloudinary.transformation.*

class Dimensions(val width: Any? = null, val height: Any? = null, val aspectRatio: Any? = null)

abstract class Resize(
    val dimensions: Dimensions,
    val mode: ResizeMode?,
    val ignoreAspectRatio: Boolean?
) : Action {

    abstract val actionType: String

    open fun params(): Collection<Param?> {
        return listOf(
            actionType.cldAsCropMode(),
            dimensions.width?.cldAsWidth(),
            dimensions.height?.cldAsHeight(),
            dimensions.aspectRatio?.cldAsAspectRatio(),
            if (ignoreAspectRatio == true) Flag.ignoreAspectRatio().cldAsFlag() else null,
            mode?.asFlagParam()
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


class ResizeMode(private val flag: Flag) {
    companion object {
        fun relative() = ResizeMode(Flag.relative())
        fun regionRelative() = ResizeMode(Flag.regionRelative())
    }

    internal fun asFlagParam() = FlagsParam(this.flag)
}
