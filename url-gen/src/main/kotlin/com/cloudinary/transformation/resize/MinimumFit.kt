package com.cloudinary.transformation.resize

class MinimumFit(dimensions: Dimensions, mode: ResizeMode? = null, ignoreAspectRatio: Boolean? = null) :
    Resize(dimensions, mode, ignoreAspectRatio) {
    override val actionType = "mfit"

    class Builder : BaseBuilder<Builder>() {
        override fun getThis() = this
        override fun build() = MinimumFit(
            Dimensions(width, height, aspectRatio),
            mode,
            ignoreAspectRatio
        )
    }
}