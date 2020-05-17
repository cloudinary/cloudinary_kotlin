package com.cloudinary.transformation

import com.cloudinary.util.cldToString

interface ParamValueContent

internal const val DEFAULT_VALUES_SEPARATOR = ":"
private const val PARAM_SEPARATOR = ","
private const val PARAM_VALUE_JOINER = "."

open class ParamValue(value: Any, separator: String = DEFAULT_VALUES_SEPARATOR) {
    internal val values: List<ParamValueContent>
    private val separator: String

    init {
        this.separator = if (value is ParamValue) value.separator else separator
        values = when (value) {
            is ParamValue -> value.values
            is Iterable<*> -> value.filterNotNull().map { if (it is ParamValueContent) it else SimpleValue(it) }
            else -> listOf(SimpleValue(value))
        }

    }

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

    fun asAction() = CParamsAction(this)
    internal open val hashKey get() = key
}

interface Action {
    // This is required for the delegate-by mechanism to work in implementing classes.
    override fun toString(): String
}

@DslMarker
annotation class TransformationDsl

abstract class ParamsAction<T>(internal val params: Map<String, Param>) : Action {

    abstract fun create(params: Map<String, Param>): T

    fun add(param: Param) = create(params + Pair(param.hashKey, param))
    fun add(params: Collection<Param>) = create(this.params + params.cldToActionMap())

    fun flag(flag: Flag) = FlagsParam(flag).let { create(params + Pair(it.hashKey, it)) }

    override fun toString(): String {
        var lastKey = ""
        var first = true

        return buildString {
            params.values.sortedWith(compareBy({ it.key }, { it.value.cldToString() })).forEach { param ->
                if (param.key == lastKey) {
                    append("$PARAM_VALUE_JOINER${param.value}")
                } else {
                    if (!first) append(PARAM_SEPARATOR)
                    append(param)
                }

                lastKey = param.key
                first = false
            }
        }
    }
}

class CParamsAction(internal val params: Map<String, Param>) : Action {
    constructor(params: Collection<Param>) : this(params.cldToActionMap())
    constructor(vararg params: Param?) : this(params.toList().filterNotNull())

    fun addParam(param: Param) = CParamsAction(params + Pair(param.hashKey, param))
    fun addParams(params: Collection<Param>) = CParamsAction(this.params + params.cldToActionMap())

    fun flag(flag: Flag) = FlagsParam(flag).let { CParamsAction(params + Pair(it.hashKey, it)) }

    override fun toString(): String {
        var lastKey = ""
        var first = true

        return buildString {
            params.values.sortedWith(compareBy({ it.key }, { it.value.cldToString() })).forEach { param ->
                if (param.key == lastKey) {
                    append("$PARAM_VALUE_JOINER${param.value}")
                } else {
                    if (!first) append(PARAM_SEPARATOR)
                    append(param)
                }

                lastKey = param.key
                first = false
            }
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
