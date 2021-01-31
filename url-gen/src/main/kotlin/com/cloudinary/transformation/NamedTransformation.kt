package com.cloudinary.transformation

import java.beans.Expression

class NamedTransformation private constructor(private val name: Any) : Action {
    companion object {
        fun name(name: String) = NamedTransformation(name)
        fun name(name: Expression) = NamedTransformation(name)
    }

    override fun toString(): String {
        return "t_$name"
    }
}