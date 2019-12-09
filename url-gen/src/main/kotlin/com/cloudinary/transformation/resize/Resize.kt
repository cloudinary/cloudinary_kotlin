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

open class Resize protected constructor(params: Map<String, Param>) : Action<Resize>(params) {
    constructor(params: Collection<Param>) : this(params.cldToActionMap()) {
        if (!(this.params.containsKey("w") || this.params.containsKey("h"))) {
            inputError(this.params)
        }
    }

    override fun create(params: Map<String, Param>) =
        Resize(params)

    companion object {
        fun fillPad(fillPad: (FillPad.Builder.() -> Unit)? = null): FillPad {
            val builder = FillPad.Builder()
            fillPad?.let { builder.it() }
            return builder.build()
        }

        fun limitFill(limitFill: (LimitFill.Builder.() -> Unit)? = null): LimitFill {
            val builder = LimitFill.Builder()
            limitFill?.let { builder.it() }
            return builder.build()
        }

        fun fill(fill: (Fill.Builder.() -> Unit)? = null): Fill {
            val builder = Fill.Builder()
            fill?.let { builder.it() }
            return builder.build()
        }

        fun minimumPad(minimumPad: (MinimumPad.Builder.() -> Unit)? = null): MinimumPad {
            val builder = MinimumPad.Builder()
            minimumPad?.let { builder.it() }
            return builder.build()
        }

        fun limitPad(limitPad: (LimitPad.Builder.() -> Unit)? = null): LimitPad {
            val builder = LimitPad.Builder()
            limitPad?.let { builder.it() }
            return builder.build()
        }

        fun minimumFit(minimumFit: (MinimumFit.Builder.() -> Unit)? = null): MinimumFit {
            val builder = MinimumFit.Builder()
            minimumFit?.let { builder.it() }
            return builder.build()
        }

        fun imaggaCrop(imaggaCrop: (ImaggaCrop.Builder.() -> Unit)? = null): ImaggaCrop {
            val builder = ImaggaCrop.Builder()
            imaggaCrop?.let { builder.it() }
            return builder.build()
        }

        fun limitFit(limitFit: (LimitFit.Builder.() -> Unit)? = null): LimitFit {
            val builder = LimitFit.Builder()
            limitFit?.let { builder.it() }
            return builder.build()
        }

        fun fit(fit: (Fit.Builder.() -> Unit)? = null): Fit {
            val builder = Fit.Builder()
            fit?.let { builder.it() }
            return builder.build()
        }

        fun crop(crop: (Crop.Builder.() -> Unit)? = null): Crop {
            val builder = Crop.Builder()
            crop?.let { builder.it() }
            return builder.build()
        }

        fun thumb(thumb: (Thumb.Builder.() -> Unit)? = null): Thumb {
            val builder = Thumb.Builder()
            thumb?.let { builder.it() }
            return builder.build()
        }

        fun pad(pad: (Pad.Builder.() -> Unit)? = null): Pad {
            val builder = Pad.Builder()
            pad?.let { builder.it() }
            return builder.build()
        }

        fun imaggaScale(imaggaScale: (ImaggaScale.Builder.() -> Unit)? = null):
                ImaggaScale {
            val builder = ImaggaScale.Builder()
            imaggaScale?.let { builder.it() }
            return builder.build()
        }

        fun scale(scale: (Scale.Builder.() -> Unit)? = null): Scale {
            val builder = Scale.Builder()
            scale?.let { builder.it() }
            return builder.build()
        }
    }
}

enum class ResizeMode(private val flagKey: FlagKey) {
    RELATIVE(FlagKey.RELATIVE()),
    REGION_RELATIVE(FlagKey.REGION_RELATIVE());

    internal fun asFlag() = FlagsParam(this.flagKey)
}