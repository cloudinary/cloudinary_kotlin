package com.cloudinary.transformation.resize

class ImaggaScale(dimensions: Dimensions, mode: ResizeMode? = null, ignoreAspectRatio: Boolean? = null) :
    Resize(dimensions, mode, ignoreAspectRatio) {
    override val actionType = "imagga_scale"

    class Builder : BaseBuilder<Builder>() {
        override fun getThis() = this
        override fun build() = ImaggaScale(
            Dimensions(width, height, aspectRatio),
            mode,
            ignoreAspectRatio
        )
    }
}