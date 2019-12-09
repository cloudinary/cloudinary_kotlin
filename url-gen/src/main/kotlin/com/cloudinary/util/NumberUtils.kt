package com.cloudinary.util

/**
 * Validates the number is in range. The behaviour for out of range values is determined by the inputError function
 */
internal fun <T> T.cldRanged(min: Int? = null, max: Int? = null): T {
    return if ((this is Int) && ((min != null && this < min) || (max != null && this > max))) inputError(this) else this
}

/**
 * Validates this float is greater than zero.
 */
internal fun Float.cldRealPositive() = if (this <= 0) inputError(this) else this


