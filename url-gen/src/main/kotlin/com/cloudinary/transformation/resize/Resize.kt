package com.cloudinary.transformation.resize

import com.cloudinary.transformation.Action
import com.cloudinary.transformation.Flag
import com.cloudinary.transformation.FlagsParam

private const val SCALE = "scale"
private const val CROP = "crop"
private const val FIT = "fit"
private const val LIMIT_FIT = "limit"
private const val MINIMUM_FIT = "mfit"
private const val FILL = "fill"
private const val LIMIT_FILL = "lfill"
private const val IMAGGA_CROP = "imagga_crop"
private const val IMAGGA_SCALE = "imagga_scale"
private const val PAD = "pad"
private const val LIMIT_PAD = "lpad"
private const val MINIMUM_PAD = "mpad"
private const val FILL_PAD = "fill_pad"
private const val THUMB = "thumb"

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

        fun limit(width: Int? = null, height: Int? = null, options: (LimitBuilder.() -> Unit)? = null): Resize {
            val builder = LimitBuilder()
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

        fun thumb(width: Int? = null, height: Int? = null, options: (ThumbBuilder.() -> Unit)? = null): Resize {
            val builder = ThumbBuilder()
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
    }
}

enum class ResizeMode(private val flag: Flag) {
    RELATIVE(Flag.Relative()),
    REGION_RELATIVE(Flag.RegionRelative());

    internal fun asFlag() = FlagsParam(this.flag)
}