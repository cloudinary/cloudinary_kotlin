package com.cloudinary.transformation.extract

import com.cloudinary.transformation.TransformationComponentBuilder

class GetFrame private constructor(
    private val numbers: List<Any> = emptyList()
) : Extract() {
    init {
        require(numbers.isNotEmpty()) { "At least one range or page must be provided" }
    }

    override fun toString(): String {
        return "pg_${
            numbers.joinToString(separator = ";", transform = {
                if (it is Pair<*, *>)
                    ((it.first?.toString() ?: "") + "-" + (it.second?.toString() ?: ""))
                else
                    it.toString()
            })
        }"
    }

    class Builder : TransformationComponentBuilder {
        var numbers = mutableListOf<Any>()

        fun byNumber(number: Int) = apply { numbers.add(number) }
        fun byNumber(number: Any) = apply { numbers.add(number) }

        fun byRange(from: Int?, to: Int?) = apply { numbers.add(Pair(from, to)) }
        fun byRange(from: Any?, to: Any?) = apply { numbers.add(Pair(from, to)) }

        override fun build(): GetFrame {
            return GetFrame(numbers)
        }
    }
}

class GetPage private constructor(private val numbers: List<Any> = emptyList()) : Extract() {
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