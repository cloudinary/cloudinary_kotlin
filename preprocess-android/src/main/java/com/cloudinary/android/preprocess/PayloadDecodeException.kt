package com.cloudinary.android.preprocess

/**
 * Thrown if a given payload could not be properly decoded
 */
class PayloadDecodeException : Exception {
    constructor()

    constructor(message: String?) : super(message)
}