package com.cloudinary.android.preprocess

/**
 * Thrown if a resource cannot be created (saved) after running the preprocessing steps.
 */
class ResourceCreationException(message: String?) : PreprocessException(message)