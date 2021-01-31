package com.cloudinary.upload

import com.cloudinary.transformation.Format
import com.cloudinary.transformation.Transformation

class EagerTransformation(val transformation: Transformation, val extension: String? = null) {
    constructor(transformation: Transformation, extension: Format) : this(transformation, extension.toString())
}