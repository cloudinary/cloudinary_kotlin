package com.cloudinary.transformation.layer.position

import com.cloudinary.transformation.Param
import com.cloudinary.transformation.gravity.Gravity

abstract class BaseLayerPosition internal constructor(
    protected val offsetX: Any? = null,
    protected val offsetY: Any? = null,
    protected val gravity: Gravity? = null
) {
    internal open fun asParams(): List<Param> {
        return mutableListOf<Param>().apply {
            if (gravity != null) add(Param("g", gravity))
            if (offsetX != null) add(Param("x", offsetX))
            if (offsetY != null) add(Param("y", offsetY))
        }
    }
}

