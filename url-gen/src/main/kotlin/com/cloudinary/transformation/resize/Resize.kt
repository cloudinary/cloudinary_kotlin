package com.cloudinary.transformation.resize

import com.cloudinary.transformation.*
import com.cloudinary.util.inputError

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

open class Resize protected constructor(params: Map<String, Param>) : ParamsAction<Resize>(params) {
    constructor(params: Collection<Param>) : this(params.cldToActionMap()) {
        if (!(this.params.containsKey("w") || this.params.containsKey("h"))) {
            inputError(this.params)
        }
    }

    override fun create(params: Map<String, Param>) =
        Resize(params)

    companion object {
        fun fillPad(width: Any? = null, height: Any? = null, fillPad: (FillPad.Builder.() -> Unit)? = null): FillPad {
            val builder = FillPad.Builder()
            fillPad?.let { builder.it() }
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            return builder.build()
        }

        fun limitFill(
            width: Any? = null,
            height: Any? = null,
            limitFill: (LimitFill.Builder.() -> Unit)? = null
        ): LimitFill {
            val builder = LimitFill.Builder()
            limitFill?.let { builder.it() }
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            return builder.build()
        }

        fun fill(width: Any? = null, height: Any? = null, fill: (Fill.Builder.() -> Unit)? = null): Fill {
            val builder = Fill.Builder()
            fill?.let { builder.it() }
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            return builder.build()
        }

        fun minimumPad(
            width: Any? = null,
            height: Any? = null,
            minimumPad: (MinimumPad.Builder.() -> Unit)? = null
        ): MinimumPad {
            val builder = MinimumPad.Builder()
            minimumPad?.let { builder.it() }
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            return builder.build()
        }

        fun limitPad(
            width: Any? = null,
            height: Any? = null,
            limitPad: (LimitPad.Builder.() -> Unit)? = null
        ): LimitPad {
            val builder = LimitPad.Builder()
            limitPad?.let { builder.it() }
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            return builder.build()
        }

        fun minimumFit(
            width: Any? = null,
            height: Any? = null,
            minimumFit: (MinimumFit.Builder.() -> Unit)? = null
        ): MinimumFit {
            val builder = MinimumFit.Builder()
            minimumFit?.let { builder.it() }
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            return builder.build()
        }

        fun imaggaCrop(
            width: Any? = null,
            height: Any? = null,
            imaggaCrop: (ImaggaCrop.Builder.() -> Unit)? = null
        ): ImaggaCrop {
            val builder = ImaggaCrop.Builder()
            imaggaCrop?.let { builder.it() }
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            return builder.build()
        }

        fun limit(width: Any? = null, height: Any? = null, limit: (Limit.Builder.() -> Unit)? = null): Limit {
            val builder = Limit.Builder()
            limit?.let { builder.it() }
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            return builder.build()
        }

        fun fit(width: Any? = null, height: Any? = null, fit: (Fit.Builder.() -> Unit)? = null): Fit {
            val builder = Fit.Builder()
            fit?.let { builder.it() }
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            return builder.build()
        }

        fun crop(width: Any? = null, height: Any? = null, crop: (Crop.Builder.() -> Unit)? = null): Crop {
            val builder = Crop.Builder()
            crop?.let { builder.it() }
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            return builder.build()
        }

        fun thumb(width: Any? = null, height: Any? = null, thumb: (Thumb.Builder.() -> Unit)? = null): Thumb {
            val builder = Thumb.Builder()
            thumb?.let { builder.it() }
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            return builder.build()
        }

        fun pad(width: Any? = null, height: Any? = null, pad: (Pad.Builder.() -> Unit)? = null): Pad {
            val builder = Pad.Builder()
            pad?.let { builder.it() }
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            return builder.build()
        }

        fun imaggaScale(width: Any? = null, height: Any? = null, imaggaScale: (ImaggaScale.Builder.() -> Unit)? = null):
                ImaggaScale {
            val builder = ImaggaScale.Builder()
            imaggaScale?.let { builder.it() }
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            return builder.build()
        }

        fun scale(width: Any? = null, height: Any? = null, scale: (Scale.Builder.() -> Unit)? = null): Scale {
            val builder = Scale.Builder()
            scale?.let { builder.it() }
            width?.let { builder.width(width) }
            height?.let { builder.height(height) }
            return builder.build()
        }
    }
}

enum class ResizeMode(private val flagKey: FlagKey) {
    RELATIVE(FlagKey.Relative()),
    REGION_RELATIVE(FlagKey.RegionRelative());

    internal fun asFlag() = FlagsParam(this.flagKey)
}