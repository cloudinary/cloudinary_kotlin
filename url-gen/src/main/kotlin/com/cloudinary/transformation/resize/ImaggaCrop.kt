package com.cloudinary.transformation.resize

class ImaggaCrop(
    dimensions: Dimensions, relative: Boolean? = null,
    regionRelative: Boolean? = null
) :
    Resize(dimensions, relative, regionRelative) {
    override val actionType = "imagga_crop"

    class Builder : BaseBuilder<Builder>() {
        override fun getThis() = this
        override fun build() = ImaggaCrop(
            Dimensions(width, height, aspectRatio),
            relative,
            regionRelative
        )
    }
}