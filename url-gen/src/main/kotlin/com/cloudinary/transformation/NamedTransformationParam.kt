package com.cloudinary.transformation

class NamedTransformationParam(name: String) : Param("named_transformation", "t", ParamValue(name)) {
    // flag param is allowed to appear several time per action, so the hashing key includes the flag value itself.
    private val _hashKey: String = "t_$name"

    override val hashKey = _hashKey
}