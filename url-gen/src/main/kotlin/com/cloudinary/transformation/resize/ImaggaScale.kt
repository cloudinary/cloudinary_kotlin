package com.cloudinary.transformation.resize

class ImaggaScale(
    dimensions: Dimensions, relative: Boolean? = null,
    regionRelative: Boolean? = null
) :
    Resize(dimensions, relative, regionRelative) {
    override val actionType = "imagga_scale"

    class Builder : BaseBuilder<Builder>() {
        override fun getThis() = this
        override fun build() = ImaggaScale(
            Dimensions(width, height, aspectRatio),
            relative,
            regionRelative
        )
    }
}