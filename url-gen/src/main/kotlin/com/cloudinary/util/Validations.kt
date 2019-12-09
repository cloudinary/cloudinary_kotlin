package com.cloudinary.util

/**
 * This is used for input validations. Depending on the throw flag, this can either throw an exception or just silently
 * log and move on.
 */
// TODO should behave according to config once the config spec is complete
internal fun <T> inputError(value: T): T {
    val configThrow = true

    if (configThrow) {
        throw Exception("InputError")
    } else {
        print("InputError")
    }

    return value
}
