package com.cloudinary.transformation

import com.cloudinary.util.cldEncodePublicId

class Add3DLut(private val lutFilePublicId: String) : Action {
    override fun toString(): String {
        return "l_lut:${lutFilePublicId.cldEncodePublicId()}"
    }
}