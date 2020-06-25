package com.cloudinary.transformation.resize

import com.cloudinary.transformation.Action
import com.cloudinary.transformation.Flag
import com.cloudinary.transformation.FlagsParam

class Resize(private val action: Action) : Action by action {

    companion object {
        fun scale(width: Int? = null, height: Int? = null, options: (ScaleBuilder.() -> Unit)? = null): Resize {
            val builder = ScaleBuilder()
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun crop(width: Int? = null, height: Int? = null, options: (CropBuilder.() -> Unit)? = null): Resize {
            val builder = CropBuilder()
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun fit(width: Int? = null, height: Int? = null, options: (FitBuilder.() -> Unit)? = null): Resize {
            val builder = FitBuilder()
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun limitFit(width: Int? = null, height: Int? = null, options: (LimitFitBuilder.() -> Unit)? = null): Resize {
            val builder = LimitFitBuilder()
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun minimumFit(
            width: Int? = null,
            height: Int? = null,
            options: (MinimumFitBuilder.() -> Unit)? = null
        ): Resize {
            val builder = MinimumFitBuilder()
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun fill(width: Int? = null, height: Int? = null, options: (FillBuilder.() -> Unit)? = null): Resize {
            val builder = FillBuilder()
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun limitFill(
            width: Int? = null,
            height: Int? = null,
            options: (LimitFillBuilder.() -> Unit)? = null
        ): Resize {
            val builder = LimitFillBuilder()
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun pad(width: Int? = null, height: Int? = null, options: (PadBuilder.() -> Unit)? = null): Resize {
            val builder = PadBuilder()
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun limitPad(
            width: Int? = null,
            height: Int? = null,
            options: (LimitPadBuilder.() -> Unit)? = null
        ): Resize {
            val builder = LimitPadBuilder()
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun minimumPad(
            width: Int? = null,
            height: Int? = null,
            options: (MinimumPadBuilder.() -> Unit)? = null
        ): Resize {
            val builder = MinimumPadBuilder()
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun fillPad(
            width: Int? = null,
            height: Int? = null,
            options: (FillPadBuilder.() -> Unit)? = null
        ): Resize {
            val builder = FillPadBuilder()
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun thumbnail(width: Int? = null, height: Int? = null, options: (ThumbnailBuilder.() -> Unit)? = null): Resize {
            val builder = ThumbnailBuilder()
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun imaggaCrop(
            width: Int? = null,
            height: Int? = null,
            options: (ImaggaCropBuilder.() -> Unit)? = null
        ): Resize {
            val builder = ImaggaCropBuilder()
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun imaggaScale(
            width: Int? = null,
            height: Int? = null,
            options: (ImaggaScaleBuilder.() -> Unit)? = null
        ): Resize {
            val builder = ImaggaScaleBuilder()
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            options?.let { builder.it() }
            return builder.build()
        }

        fun generic(cropMode: String, options: (GenericResizeBuilder.() -> Unit)? = null): Resize {
            val builder = GenericResizeBuilder(cropMode)
            options?.let { builder.it() }
            return builder.build()
        }
    }
}

class ResizeMode(private val flag: Flag) {
    companion object {
        fun relative() = ResizeMode(Flag.relative())
        fun regionRelative() = ResizeMode(Flag.regionRelative())
    }

    internal fun asFlag() = FlagsParam(this.flag)
}