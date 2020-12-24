package com.cloudinary.transformation.resize

class Scale(
    dimensions: Dimensions, relative: Boolean? = null,
    regionRelative: Boolean? = null
) :
    Resize(dimensions, relative, regionRelative) {
    override val actionType = "scale"

    class Builder : BaseBuilder<Builder>() {
        override fun getThis() = this
        override fun build() = Scale(
            Dimensions(width, height, aspectRatio),
            relative,
            regionRelative
        )
    }
}



