package com.cloudinary.util

import com.cloudinary.Cloudinary


/**
 * This is used for input validations. Depending on the throw flag, this can either throw an exception or just silently
 * log and move on.
 */
// TODO should behave according to config once the config spec is complete
internal fun <T> illegalArgument(value: T, reason: String? = null): T {
    val message = "Illegal value: $value." + (reason?.let { " reason: $reason" } ?: "")

    if (Cloudinary.throwOnInvalidTransformations) {
        throw IllegalArgumentException(message)
    } else {
        print(message)
    }

    return value
}

internal fun <T> rangeInputError(min: Int?, max: Int?, value: T): T {
    return illegalArgument(value, "Not in range ($min-$max)")
}

/**
 * Validates the number is in range. The behaviour for out of range values is determined by the inputError function
 */
internal fun <T> T.cldRanged(min: Int? = null, max: Int? = null): T {
    return if ((this is Int) && ((min != null && this < min) || (max != null && this > max))) rangeInputError(
        min,
        max,
        this
    ) else this
}


/**
 * Validates this float is greater than zero.
 */
internal fun Float.cldRealPositive() = if (this <= 0) illegalArgument(this, "Not a real positive number") else this

internal fun Long.cldPositiveNumber() = if (this <= 0) illegalArgument(this, "Not a positive number") else this

internal fun Long.cldNegativeNumber() = if (this >= 0) {
    illegalArgument(this, "Not a negative number")
} else this

internal fun validateAllNotNull(vararg values: Any?) {
    if (values.filterNotNull().size != values.size) illegalArgument(values, "Arguments cannot be null")
}
