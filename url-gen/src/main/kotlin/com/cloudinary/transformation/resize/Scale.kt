package com.cloudinary.transformation.resize

class Scale(dimensions: Dimensions, mode: ResizeMode? = null, ignoreAspectRatio: Boolean? = null) :
    Resize(dimensions, mode, ignoreAspectRatio) {
    override val actionType = "scale"

    class Builder : BaseBuilder<Builder>() {
        override fun getThis() = this
        override fun build() = Scale(
            Dimensions(width, height, aspectRatio),
            mode,
            ignoreAspectRatio
        )
    }
}



