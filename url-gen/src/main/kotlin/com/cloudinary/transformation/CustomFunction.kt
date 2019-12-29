package com.cloudinary.transformation

import com.cloudinary.transformation.CustomFunction.Type.*
import com.cloudinary.util.cldEncodePublicId
import com.cloudinary.util.cldToUrlSafeBase64

class CustomFunction private constructor(params: Map<String, Param>) :
    ParamsAction<CustomFunction>(params) {
    override fun create(params: Map<String, Param>) = CustomFunction(params)

    class Builder(private val source: String) : TransformationComponentBuilder {
        private var type: Type = WASM

        fun type(type: Type) = apply { this.type = type }

        override fun build() = when (type) {
            PRE_PROCESS -> buildParameters(listOf("pre", "remote", source.cldToUrlSafeBase64()))
            REMOTE -> buildParameters(listOf("remote", source.cldToUrlSafeBase64()))
            WASM -> buildParameters(listOf("wasm", source.cldEncodePublicId()))
        }

        private fun buildParameters(values: List<Any>) =
            CustomFunction(
                Param("custom_function", "fn", ParamValue(values)).let { mapOf(Pair(it.key, it)) }
            )
    }

    enum class Type {
        PRE_PROCESS,
        REMOTE,
        WASM
    }
}

fun wasm(publicId: String) = CustomFunction.Builder(publicId).type(WASM).build()
fun remote(url: String) = CustomFunction.Builder(url).type(REMOTE).build()
fun preProcess(url: String) = CustomFunction.Builder(url).type(PRE_PROCESS).build()