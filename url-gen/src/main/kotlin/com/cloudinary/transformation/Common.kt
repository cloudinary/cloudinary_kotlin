package com.cloudinary.transformation

/**
 * Marker interface for values to be used in ParamValue instances.
 */
interface ParamValueContent {
    fun normalizedString(): String
}

internal const val PARAM_KEY_VALUE_SEPARATOR = "_"
internal const val DEFAULT_VALUES_SEPARATOR = ":"
private const val PARAM_SEPARATOR = ","
private const val PARAM_VALUE_JOINER = "."

/**
 * This class is a wrapper that represents the value of parameter. The value can be either a named value or a
 * simple value. In both case the value itself can be a complex object or a primitive.
 * In case a ParamValue is passed as to the constructor the new instance becomes a shallow clone of that ParamValue.
 *
 */
open class ParamValue(
    value: Any,
    private val separator: String = if (value is ParamValue) value.separator else DEFAULT_VALUES_SEPARATOR,
    private val normalize: Boolean = false
) {
    internal val values: List<ParamValueContent>

    init {
        values = when (value) {
            is ParamValue -> value.values
            is Iterable<*> -> value.filterNotNull().map { if (it is ParamValueContent) it else SimpleValue(it) }
            is Array<*> -> value.filterNotNull().map { if (it is ParamValueContent) it else SimpleValue(it) }
            else -> listOf(SimpleValue(value))
        }
    }

    override fun toString(): String {
        return values.joinToString(separator = separator, transform = {
            if (normalize) it.normalizedString() else it.toString()
        })
    }
}

/**
 * This class represents a named value to be used as the value in a ParamValue class.
 * It is used where the param values consists of key-value pairs, and it is serialized as
 * "name:value" when using the default separator.
 */
class NamedValue(
    internal val name: String,
    internal val value: Any,
    private val separator: String = DEFAULT_VALUES_SEPARATOR
) :
    ParamValueContent {

    override fun toString() = "$name$separator$value"
    override fun normalizedString() = "$name$separator${value.toString().cldNormalize()}"
}

/**
 * This class represents a simple value to be used as the value in a ParamValue class. Not the simple here denotes
 * 'non-named' - it can be a complex object with more nested values.
 */

class SimpleValue(internal val value: Any) : ParamValueContent {
    override fun toString() = value.toString()
    override fun normalizedString() = value.toString().cldNormalize()
}

/**
 * Turns a list of any type to a param value content, to be used in a ParamValue instance.
 */
internal fun List<*>.cldAsParamValueContent() =
    this.filterNotNull().map { if (it is ParamValueContent) it else SimpleValue(it) }

open class Param(private val name: String, internal val key: String, internal val value: ParamValue) {
    constructor(name: String, key: String, value: Any) : this(name, key, ParamValue(value))

    override fun toString(): String {
        return "${key}$PARAM_KEY_VALUE_SEPARATOR$value"
    }

    internal open val hashKey get() = key
}

fun Param.asAction() = ParamsAction(this)

/**
 * Marker interface to represent anything that can be used in a transformation.
 */
interface Action {
    // This is required for the delegate-by mechanism to work in implementing classes.
    override fun toString(): String
}

@DslMarker
annotation class TransformationDsl

/**
 * This class is used for all actions that are based on a list of parameters.
 */
class ParamsAction(internal val params: Map<String, Param>) : Action {
    constructor(params: Collection<Param>) : this(params.cldToActionMap())
    constructor(vararg params: Param?) : this(params.toList().filterNotNull())

    fun addParam(param: Param) = ParamsAction(params + Pair(param.hashKey, param))
    fun addParams(params: Collection<Param>) = ParamsAction(this.params + params.cldToActionMap())

    fun flag(flag: Flag) = FlagsParam(flag).let { ParamsAction(params + Pair(it.hashKey, it)) }

    override fun toString(): String {
        var lastKey = ""
        var first = true

        return buildString {
            params.values.sortedWith(compareBy({ it.key }, { it.value.toString() })).forEach { param ->
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

/**
 * This class is used for actions that are pre-serialized. They are used in a transformation as-is without any
 * change.
 */
class RawAction(private val value: String) : Action {
    override fun toString() = value
}

/**
 * Convers a collection of parameters to a map of param_key to param - to be used in ParamsAction, mainly.
 */
internal fun Collection<Param>.cldToActionMap() = associateBy { it.hashKey }
