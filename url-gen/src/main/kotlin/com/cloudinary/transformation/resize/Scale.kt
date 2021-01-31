package com.cloudinary.transformation.resize

import com.cloudinary.transformation.Param

class Scale(
    dimensions: Dimensions, relative: Boolean? = null,
    regionRelative: Boolean? = null, private val liquidRescaling: Boolean? = null
) :
    Resize(dimensions, relative, regionRelative) {
    override val actionType = "scale"

    override fun params(): Collection<Param?> {
        return super.params() +
                if (liquidRescaling == true) Param("g", "liquid") else null
    }

    class Builder : BaseBuilder<Builder>() {
        private var liquidRescaling: Boolean? = null

        fun liquidRescaling(liquidRescaling: Boolean = true) = apply { this.liquidRescaling = liquidRescaling }
        override fun getThis() = this
        override fun build() = Scale(
            Dimensions(width, height, aspectRatio),
            relative,
            regionRelative,
            liquidRescaling
        )
    }
}



