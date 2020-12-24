package com.cloudinary.transformation.resize

class MinimumFit(
    dimensions: Dimensions, relative: Boolean? = null,
    regionRelative: Boolean? = null
) :
    Resize(dimensions, relative, regionRelative) {
    override val actionType = "mfit"

    class Builder : BaseBuilder<Builder>() {
        override fun getThis() = this
        override fun build() = MinimumFit(
            Dimensions(width, height, aspectRatio),
            relative,
            regionRelative
        )
    }
}