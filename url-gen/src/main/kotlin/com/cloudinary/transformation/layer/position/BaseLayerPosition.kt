package com.cloudinary.transformation.layer.position

import com.cloudinary.transformation.Param
import com.cloudinary.transformation.gravity.Gravity

abstract class BaseLayerPosition internal constructor(
    private val x: Any? = null,
    private val y: Any? = null,
    private val gravity: Gravity? = null
) {
    internal open fun asParams(): List<Param> {
        return mutableListOf<Param>().apply {
            if (gravity != null) add(Param("g", gravity))
            if (x != null) add(Param("x", x))
            if (y != null) add(Param("y", y))
        }
    }
}

