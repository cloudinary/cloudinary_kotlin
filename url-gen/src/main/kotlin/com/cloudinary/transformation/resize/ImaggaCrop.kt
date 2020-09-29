package com.cloudinary.transformation.resize

class ImaggaCrop(dimensions: Dimensions, mode: ResizeMode? = null, ignoreAspectRatio: Boolean? = null) :
    Resize(dimensions, mode, ignoreAspectRatio) {
    override val actionType = "imagga_crop"

    class Builder : BaseBuilder<Builder>() {
        override fun getThis() = this
        override fun build() = ImaggaCrop(
            Dimensions(width, height, aspectRatio),
            mode,
            ignoreAspectRatio
        )
    }
}