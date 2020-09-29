package com.cloudinary.transformation.resize

class LimitFit(dimensions: Dimensions, mode: ResizeMode? = null, ignoreAspectRatio: Boolean? = null) :
    Resize(dimensions, mode, ignoreAspectRatio) {
    override val actionType = "limit"

    class Builder : BaseBuilder<Builder>() {
        override fun getThis() = this
        override fun build() = LimitFit(
            Dimensions(width, height, aspectRatio),
            mode,
            ignoreAspectRatio
        )
    }
}