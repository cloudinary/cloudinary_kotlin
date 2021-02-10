package com.cloudinary.transformation

import com.cloudinary.transformation.psdtools.PSDTools

interface ITransformableImage<T> : IBaseTransformable<T> {
    fun psdTools(psdTools: PSDTools) = add(psdTools)
    fun backgroundColor(backgroundColor: BackgroundColor) = add(backgroundColor)
    fun backgroundColor(color: Color) = backgroundColor(BackgroundColor(color))
}