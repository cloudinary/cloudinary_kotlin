package com.cloudinary.transformation.doctools

import com.cloudinary.transformation.TransformationComponentBuilder

class GetPage private constructor(private val numbers: List<Any> = emptyList()) : DocTools() {
    init {
        require(numbers.isNotEmpty()) {
            "Pages or ranges must be provided"
        }
    }

    override fun toString(): String {

        return "pg_${
            numbers.joinToString(separator = ";", transform = {
                if (it is Pair<*, *>)
                    (it.first?.toString() ?: "") + "-" + (it.second?.toString() ?: "")
                else
                    it.toString()
            })
        }"
    }

    companion object {
        fun byNumber(number: Int, options: (Builder.() -> Unit)? = null) =
            byNumber(number as Any, options)

        fun byNumber(number: Any, options: (Builder.() -> Unit)? = null): GetPage {
            val builder = Builder().byNumber(number)
            options?.let { builder.it() }
            return builder.build()
        }

        fun byRange(from: Int?, to: Int?, options: (Builder.() -> Unit)? = null) =
            byRange(from as Any, to as Any, options)

        fun byRange(from: Any?, to: Any?, options: (Builder.() -> Unit)? = null): GetPage {
            val builder = Builder().byRange(from, to)
            options?.let { builder.it() }
            return builder.build()
        }
    }


    class Builder : TransformationComponentBuilder {
        var numbers = mutableListOf<Any>()

        fun byNumber(number: Int) = apply { numbers.add(number) }
        fun byNumber(number: Any) = apply { numbers.add(number) }

        fun byRange(from: Int?, to: Int?) = apply { numbers.add(Pair(from, to)) }
        fun byRange(from: Any?, to: Any?) = apply { numbers.add(Pair(from, to)) }

        override fun build(): GetPage {
            return GetPage(numbers)
        }
    }


}