package com.cloudinary.transformation

import com.cloudinary.util.cldToString

interface ParamValueContent

internal const val DEFAULT_VALUES_SEPARATOR = ":"

open class ParamValue(
    internal val values: List<ParamValueContent>,
    private val separator: String = DEFAULT_VALUES_SEPARATOR
) {
    constructor(value: Any) : this(listOf(SimpleValue(value)))
    constructor(values: List<Any>) : this(values.map { if (it is ParamValueContent) it else SimpleValue(it) })

    override fun toString(): String {
        return values.joinToString(separator = separator, transform = { it.toString() })
    }
}

class NamedValue(
    internal val name: String,
    internal val value: Any,
    private val separator: String = DEFAULT_VALUES_SEPARATOR
) :
    ParamValueContent {

    override fun toString() = "$name$separator${value.cldToString().cldNormalize()}"
}

class SimpleValue(internal val value: Any) : ParamValueContent {
    override fun toString() = value.cldToString().cldNormalize()
}

internal fun List<*>.cldAsParamValueContent() =
    this.filterNotNull().map { if (it is ParamValueContent) it else SimpleValue(it) }

open class Param(private val name: String, internal val key: String, internal val value: ParamValue) {
    constructor(name: String, key: String, value: Any) : this(name, key, ParamValue(value))

    override fun toString(): String {
        return "${key}_$value"
    }

    internal open val hashKey get() = key
}

interface Action

@DslMarker
annotation class TransformationDsl

abstract class ParamsAction<T>(internal val params: Map<String, Param>) : Action {
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

open class GenericAction(params: Map<String, Param>) : ParamsAction<GenericAction>(params) {
    constructor(param: Param) : this(mapOf(Pair(param.key, param)))

    override fun create(params: Map<String, Param>) = GenericAction(params)
}

class RawAction(private val value: String) : Action {
    override fun toString() = value
}

internal fun Collection<Param>.cldToActionMap() = associateBy { it.hashKey }
