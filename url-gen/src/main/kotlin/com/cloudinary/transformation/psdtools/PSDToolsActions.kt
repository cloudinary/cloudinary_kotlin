package com.cloudinary.transformation.psdtools

import com.cloudinary.transformation.TransformationComponentBuilder

class Clip(private val clippingPath: Any?, private val evenOdd: Boolean = false) : PSDTools() {
    override fun toString(): String {
        val evenOdd = if (evenOdd) "_evenodd" else ""
        val path = when (clippingPath) {
            null -> ""
            is Int -> ",pg_$clippingPath"
            else -> ",pg_name:$clippingPath"
        }
        return "fl_clip$evenOdd$path"
    }

    class Builder private constructor(private val clippingPath: Any?) : TransformationComponentBuilder {
        constructor(clippingPath: String) : this(clippingPath as Any)
        constructor(clippingPath: Int? = null) : this(clippingPath as Any?)

        private var evenOdd: Boolean = false

        fun evenOdd(evenOdd: Boolean = true) = apply { this.evenOdd = evenOdd }

        override fun build() = Clip(clippingPath, evenOdd)
    }
}

class SmartObject(private val byIndex: List<Any>? = null, private val byFileName: List<String>? = null) : PSDTools() {
    init {
        require(isValidQualifier(byFileName) || isValidQualifier(byIndex)) { "At least one smart object qualifier is required (either byIndex or byFilename)." }
    }

    private fun isValidQualifier(list: List<*>?): Boolean {
        return list != null && list.isNotEmpty()
    }

    private fun getListAsString(): String {
        return if (isValidQualifier(byIndex))
            byIndex!!.joinToString(";")
        else
            "name:${byFileName!!.joinToString(";")}"
    }

    override fun toString(): String {
        return "pg_embedded:${getListAsString()}"
    }

    class Builder : TransformationComponentBuilder {
        private val byIndex = mutableListOf<Any>()
        private val byFilename = mutableListOf<String>()

        fun byIndex(index: Int) = apply { this.byIndex.add(index) }
        fun byIndex(index: Any) = apply { this.byIndex.add(index) }
        fun byFilename(filename: String) = apply { this.byFilename.add(filename) }

        override fun build(): SmartObject {
            return SmartObject(byIndex, byFilename)
        }
    }
}

class GetLayer private constructor(
    private val numbers: List<Any> = emptyList(),
    private val names: List<Any> = emptyList()
) : PSDTools() {
    init {
        require(numbers.isNotEmpty() || names.isNotEmpty()) { "Either layer names or indices must be populated" }
        require(!(numbers.isNotEmpty() && names.isNotEmpty())) { "The list should contain either only names or indices - not both" }
    }

    override fun toString(): String {

        if (numbers.isNotEmpty()) {
            return "pg_${
                numbers.joinToString(separator = ";", transform = {
                    if (it is Pair<*, *>)
                        ((it.first?.toString() ?: "") + "-" + (it.second?.toString() ?: ""))
                    else
                        it.toString()
                })
            }"
        } else {
            return "pg_name:${
                names.joinToString(separator = ";", transform = {
                    if (it is Pair<*, *>)
                        (it.first?.toString() ?: "" + "-" + it.second?.toString() ?: "")
                    else
                        it.toString()
                })
            }"
        }
    }

    companion object {
        fun byNumber(number: Int, options: (Builder.() -> Unit)? = null) =
            byNumber(number as Any, options)

        fun byNumber(number: Any, options: (Builder.() -> Unit)? = null): GetLayer {
            val builder = Builder().byNumber(number)
            options?.let { builder.it() }
            return builder.build()
        }

        fun byRange(from: Int?, to: Int?, options: (Builder.() -> Unit)? = null) =
            byRange(from as Any, to as Any, options)

        fun byRange(from: Any?, to: Any?, options: (Builder.() -> Unit)? = null): GetLayer {
            val builder = Builder().byRange(from, to)
            options?.let { builder.it() }
            return builder.build()
        }

        fun byName(name: String, options: (Builder.() -> Unit)? = null) =
            byName(name as Any, options)

        fun byName(name: Any, options: (Builder.() -> Unit)? = null): GetLayer {
            val builder = Builder().byName(name)
            options?.let { builder.it() }
            return builder.build()
        }
    }


    class Builder : TransformationComponentBuilder {
        var numbers = mutableListOf<Any>()
        var names = mutableListOf<Any>()

        fun byName(name: String) = apply { names.add(name) }
        fun byName(name: Any) = apply { names.add(name) }
        fun byNumber(number: Int) = apply { numbers.add(number) }
        fun byNumber(number: Any) = apply { numbers.add(number) }

        fun byRange(from: Int?, to: Int?) = apply { numbers.add(Pair(from, to)) }
        fun byRange(from: Any?, to: Any?) = apply { numbers.add(Pair(from, to)) }

        override fun build(): GetLayer {
            return GetLayer(numbers, names)
        }
    }
}