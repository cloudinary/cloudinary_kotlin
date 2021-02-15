package com.cloudinary.transformation.expression

import com.cloudinary.transformation.Action
import com.cloudinary.transformation.TransformationComponentBuilder

class Variable private constructor(
    private val name: String,
    private val value: Any,
    private val convertTo: ConvertTo? = null,
    private val prefix: String? = null

) : Action {
    override fun toString(): String {
        val prefixStr = prefix?.let { "$prefix:" } ?: ""
        val valueStr = when (value) {
            is Array<*> -> "!${value.filterNotNull().joinToString(":", transform = { encode(it) })}!" // "!a:b:c!"
            is Iterable<*> -> "!${value.filterNotNull().joinToString(":", transform = { encode(it) })}!" // "!a:b:c!"
            is String -> "!${encode(value)}!" // "!a!"
            else -> value.toString()
        }

        val convertToValue = convertTo?.let { "_to_$it" } ?: ""

        return "\$${name}_$prefixStr$valueStr$convertToValue"
    }

    companion object {
        fun set(name: String, value: Any, options: (Builder.() -> Unit)? = null) = build(name, value, options)
        fun set(name: String, value: String, options: (Builder.() -> Unit)? = null) = build(name, value, options)
        fun set(name: String, value: Int, options: (Builder.() -> Unit)? = null) = build(name, value, options)
        fun set(name: String, value: Float, options: (Builder.() -> Unit)? = null) = build(name, value, options)
        fun set(name: String, value: Expression, options: (Builder.() -> Unit)? = null) = build(name, value, options)

        fun build(name: String, value: Any, options: (Builder.() -> Unit)? = null): Variable {
            val builder = Builder(name, value)
            options?.let { builder.it() }
            return builder.build()
        }

        fun setAssetReference(name: String, value: String): Variable = Builder(name, value, "ref").build()

        fun setFromContext(name: String, value: String, options: (Builder.() -> Unit)? = null): Variable {
            val builder = Builder(name, value, "ctx")
            options?.let { builder.it() }
            return builder.build()
        }

        fun setFromMetadata(name: String, value: String, options: (Builder.() -> Unit)? = null): Variable {
            val builder = Builder(name, value, "md")
            options?.let { builder.it() }
            return builder.build()
        }

    }

    class Builder internal constructor(
        private val name: String,
        private val value: Any,
        private val prefix: String? = null
    ) :
        TransformationComponentBuilder {
        constructor(name: String, value: Any) : this(name, value, null)

        private var convertTo: ConvertTo? = null

        fun asFloat() = apply { this.convertTo = ConvertTo.FLOAT }
        fun asInteger() = apply { this.convertTo = ConvertTo.INTEGER }

        override fun build(): Variable {
            return Variable(name, value, convertTo, prefix)
        }
    }

    private enum class ConvertTo(private val value: String) {
        FLOAT("f"),
        INTEGER("i");

        override fun toString(): String {
            return value
        }
    }
}

/**
 * If the value is a string this methods encodes it s a variable value, otherwise returns the toString() value without
 * further processing.
 */
private fun encode(value: Any) = if (value is String)
    value.replace(",", "%2c").replace("/", "%2f").replace("!", "%21")
else
    value.toString()