package com.cloudinary.transformation.resize

class LimitFit(
    dimensions: Dimensions, relative: Boolean? = null,
    regionRelative: Boolean? = null
) :
    Resize(dimensions, relative, regionRelative) {
    override val actionType = "limit"

    class Builder : BaseBuilder<Builder>() {
        override fun getThis() = this
        override fun build() = LimitFit(
            Dimensions(width, height, aspectRatio),
            relative,
            regionRelative
        )
    }
}