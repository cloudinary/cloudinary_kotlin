package com.cloudinary.android.preprocess

/**
 * Base exception for exceptions thrown during the preprocessing phase.
 */
open class PreprocessException : Exception {
    constructor()

    constructor(message: String?) : super(message)
}