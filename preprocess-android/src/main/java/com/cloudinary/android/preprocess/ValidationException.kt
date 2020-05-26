package com.cloudinary.android.preprocess

/**
 * Thrown inside implementations of [Preprocess#execute] if the resource fails validation.
 */
class ValidationException(message: String?) : PreprocessException(message)