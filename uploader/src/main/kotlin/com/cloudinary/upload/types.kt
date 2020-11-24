package com.cloudinary.upload

import com.cloudinary.transformation.FormatType
import com.cloudinary.transformation.Transformation

class EagerTransformation(val transformation: Transformation, val extension: String? = null) {
    constructor(transformation: Transformation, extension: FormatType) : this(transformation, extension.toString())
}