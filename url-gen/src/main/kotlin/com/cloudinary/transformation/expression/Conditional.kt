package com.cloudinary.transformation.expression

import com.cloudinary.transformation.Action
import com.cloudinary.transformation.Transformation
import com.cloudinary.transformation.TransformationComponentBuilder

class Conditional private constructor(
    private val expression: Any,
    private val transformation: Any,
    private val elseTransformation: Any? = null
) : Action {
    override fun toString(): String {
        val typedExpression = if (expression is Expression) expression else Expression.expression(expression.toString())
        val elseStr = elseTransformation?.let { "/if_else/$it" } ?: ""
        return "if_${typedExpression}/$transformation$elseStr/if_end"
    }

    companion object {
        fun ifCondition(
            expression: String,
            transformation: Transformation,
            options: (Builder.() -> Unit)? = null
        ): Conditional {
            val builder = Builder().expression(expression).transformation(transformation)
            options?.let { builder.it() }
            return builder.build()
        }

        fun ifCondition(
            expression: Expression,
            transformation: Transformation,
            options: (Builder.() -> Unit)? = null
        ): Conditional {
            val builder = Builder().expression(expression).transformation(transformation)
            options?.let { builder.it() }
            return builder.build()
        }

        fun ifCondition(
            expression: String,
            transformation: String,
            options: (Builder.() -> Unit)? = null
        ): Conditional {
            val builder = Builder().expression(expression).transformation(transformation)
            options?.let { builder.it() }
            return builder.build()
        }
    }

    class Builder : TransformationComponentBuilder {
        private var expression: Any? = null
        private var transformation: Any? = null
        private var otherwise: Any? = null

        fun expression(expression: Expression) = apply { this.expression = expression }
        fun expression(expression: String) = apply { this.expression = expression }
        fun transformation(transformation: Transformation) = apply { this.transformation = transformation }
        fun transformation(transformation: String) = apply { this.transformation = transformation }
        fun transformation(transformation: Transformation.Builder.() -> Unit) = apply {
            val builder = Transformation.Builder()
            builder.transformation()
            this.transformation = builder.build()
        }

        fun otherwise(transformation: Transformation) = apply { this.otherwise = transformation }
        fun otherwise(transformation: String) = apply { this.otherwise = transformation }
        fun otherwise(transformation: Transformation.Builder.() -> Unit) = apply {
            val builder = Transformation.Builder()
            builder.transformation()
            this.otherwise = builder.build()
        }

        override fun build(): Conditional {
            val safeTransformation = transformation
            val safeExpression = expression


            require(safeExpression != null) { "An expression is required for an if-condition" }
            require(safeTransformation != null) { "An transformation is required for an if-condition" }

            return Conditional(safeExpression, safeTransformation, otherwise)
        }
    }
}