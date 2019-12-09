package com.cloudinary.transformation

import com.cloudinary.util.cldToString

open class ParamValue(internal val values: List<Any>, private val separator: String = ":") : TransformationComponent {
    constructor(value: Any) : this(listOf(value))

    override fun toString(): String {
        // TODO ignore param value 'keys'? (e_preview:duration_20)
        return values.joinToString(separator = separator, transform = { it.cldToString().cldNormalize() })
    }
}

open class Param(private val name: String, internal val key: String, internal val value: ParamValue) {
    constructor(name: String, key: String, value: Any) : this(name, key, ParamValue(value))

    override fun toString(): String {
        return "${key}_$value"
    }

    internal open val hashKey get() = key
}

interface TransformationComponent

@DslMarker
annotation class TransformationDsl

abstract class Action<T>(internal val params: Map<String, Param>) : TransformationComponent {
    abstract fun create(params: Map<String, Param>): T

    fun add(param: Param) = create(params + Pair(param.hashKey, param))
    fun add(params: Collection<Param>) = create(this.params + params.cldToActionMap())

    fun flag(flag: FlagKey) = FlagsParam(flag).let { create(params + Pair(it.hashKey, it)) }

    override fun toString(): String {
        var lastKey = ""
        var first = true

        return buildString {
            params.values.sortedWith(compareBy({ it.key }, { it.value.cldToString() })).forEach { param ->
                if (param.key == lastKey) {
                    append(".${param.value}")
                } else {
                    if (!first) append(",")
                    append(param)
                }

                lastKey = param.key
                first = false
            }


//        // An alternative elegant solution - but probably extremely inefficient. Pending benchmarks
//        return params.values.groupBy { it.key }
//            .map { p -> p.key + "_" + p.value.map { p2 -> p2.value.cldToString() }.sorted().joinToString(".") }
//            .sorted()
//            .joinToString { "," }
        }
    }
}

open class GenericAction(params: Map<String, Param>) : Action<GenericAction>(params) {
    constructor(param: Param) : this(mapOf(Pair(param.key, param)))

    override fun create(params: Map<String, Param>) = GenericAction(params)
}

class StringComponent(private val value: String) : TransformationComponent {
    override fun toString() = value
}

internal fun Collection<Param>.cldToActionMap() = associateBy { it.hashKey }
