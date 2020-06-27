package com.cloudinary.upload

import com.cloudinary.Extension
import com.cloudinary.transformation.Transformation

class EagerTransformation(val transformation: Transformation, val extension: String? = null) {
    constructor(transformation: Transformation, extension: Extension) : this(transformation, extension.toString())
}