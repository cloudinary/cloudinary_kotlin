package com.cloudinary.transformation.expression

import com.cloudinary.transformation.Action
import com.cloudinary.transformation.Transformation
import com.cloudinary.transformation.TransformationComponentBuilder

class IfCondition private constructor(
    private val expression: Any,
    private val transformation: Transformation,
    private val elseTransformation: Transformation? = null
) : Action {
    override fun toString(): String {
        val elseStr = elseTransformation?.let { "/if_else/$it" } ?: ""
        return "if_${expression}/$transformation$elseStr/if_end"
    }

    companion object {
        fun ifCondition(
            expression: String,
            transformation: Transformation,
            options: (Builder.() -> Unit)? = null
        ): IfCondition {
            val builder = Builder().expression(expression).transformation(transformation)
            options?.let { builder.it() }
            return builder.build()
        }

        fun ifCondition(
            expression: Expression,
            transformation: Transformation,
            options: (Builder.() -> Unit)? = null
        ): IfCondition {
            val builder = Builder().expression(expression).transformation(transformation)
            options?.let { builder.it() }
            return builder.build()
        }
    }

    class Builder : TransformationComponentBuilder {
        private var expression: Any? = null
        private var transformation: Transformation? = null
        private var otherwise: Transformation? = null

        fun expression(expression: Expression) = apply { this.expression = expression }
        fun expression(expression: String) = apply { this.expression = expression }
        fun transformation(transformation: Transformation) = apply { this.transformation = transformation }
        fun transformation(transformation: Transformation.Builder.() -> Unit) = apply {
            val builder = Transformation.Builder()
            builder.transformation()
            this.transformation = builder.build()
        }

        fun otherwise(transformation: Transformation) = apply { this.otherwise = transformation }
        fun otherwise(transformation: Transformation.Builder.() -> Unit) = apply {
            val builder = Transformation.Builder()
            builder.transformation()
            this.otherwise = builder.build()
        }

        override fun build(): IfCondition {
            val safeTransformation = transformation
            val safeExpression = expression


            require(safeExpression != null) { "An expression is required for an if-condition" }
            require(safeTransformation != null) { "An transformation is required for an if-condition" }

            return IfCondition(safeExpression, safeTransformation, otherwise)
        }
    }
}