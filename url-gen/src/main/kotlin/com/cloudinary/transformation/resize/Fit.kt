package com.cloudinary.transformation.resize

class Fit(dimensions: Dimensions, mode: ResizeMode? = null, ignoreAspectRatio: Boolean? = null) :
    Resize(dimensions, mode, ignoreAspectRatio) {
    override val actionType = "fit"

    class Builder : BaseBuilder<Builder>() {
        override fun getThis() = this
        override fun build() = Fit(
            Dimensions(width, height, aspectRatio),
            mode,
            ignoreAspectRatio
        )
    }
}