package com.cloudinary.transformation

internal const val PARAM_KEY_VALUE_SEPARATOR = "_"
internal const val DEFAULT_VALUES_SEPARATOR = ":"
internal const val DEFAULT_COMPONENT_SEPARATOR = "/"
private const val PARAM_SEPARATOR = ","
private const val PARAM_VALUE_JOINER = "."

/**
 * returns a valid component string generated from the given params collection:
 * 1. Sort by key
 * 2. Merge params with identical keys (e.g. fl_a,fl_b turns to fl_a.b)
 * 3. Join to string with a comma separator
 */
internal fun Collection<Param?>.toComponentString(): String = asComponentString(*(this.toTypedArray()))

internal fun asComponentString(vararg values: Any?): String {
    return values.filterNotNull().sortedBy { it.toString() }.joinToString(PARAM_SEPARATOR)
}

class Param(val key: String, val value: Any) {
    override fun toString(): String {
        return "${key}_$value"
    }
}

internal fun asComposnentString(vararg params: Param?): String {
    var lastKey = ""
    var first = true

    return buildString {
        params.filterNotNull().sortedWith(compareBy({ it.key }, { it.value.toString() })).forEach { param ->
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

/**
 * Joins the given string with the values using the given separator (or used default  - colon)
 */
internal fun Any.joinWithValues(
    vararg values: Any?,
    separator: String = DEFAULT_VALUES_SEPARATOR
): String {
    val nonNull = values.filterNotNull()
    if (nonNull.isEmpty()) return this.toString()

    return "$this$separator${nonNull.joinToString(separator = separator)}"
}

/**
 * Marker interface to represent anything that can be used in a transformation.
 */
interface Action {
    // This is required for the delegate-by mechanism to work in implementing classes.
    override fun toString(): String
}

/**
 *  @suppress
 */
@DslMarker
annotation class TransformationDsl

/**
 * This class is used for actions that are pre-serialized. They are used in a transformation as-is without any
 * change.
 */
class RawAction(private val value: String) : Action {
    override fun toString() = value
}

