package com.cloudinary.transformation.resize

class Fit(
    dimensions: Dimensions, relative: Boolean? = null,
    regionRelative: Boolean? = null
) :
    Resize(dimensions, relative, regionRelative) {
    override val actionType = "fit"

    class Builder : BaseBuilder<Builder>() {
        override fun getThis() = this
        override fun build() = Fit(
            Dimensions(width, height, aspectRatio),
            relative,
            regionRelative
        )
    }
}