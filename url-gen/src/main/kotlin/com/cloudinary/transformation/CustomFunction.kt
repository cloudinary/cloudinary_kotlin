package com.cloudinary.transformation

import com.cloudinary.util.cldEncodePublicId
import com.cloudinary.util.cldToUrlSafeBase64

class CustomFunction(private val action: Action) : Action by action {

    companion object {
        fun wasm(publicId: String) = Builder(publicId).type(Type.WASM).build()
        fun remote(url: String) = Builder(url).type(Type.REMOTE).build()
        fun preprocessRemote(url: String) = Builder(url).type(Type.PRE_PROCESS).build()
    }

    private class Builder(private val source: String) : TransformationComponentBuilder {
        private var type: Type = Type.WASM

        fun type(type: Type) = apply { this.type = type }

        override fun build() = when (type) {
            Type.PRE_PROCESS -> buildAction(listOf("pre", "remote", source.cldToUrlSafeBase64()))
            Type.REMOTE -> buildAction(listOf("remote", source.cldToUrlSafeBase64()))
            Type.WASM -> buildAction(listOf("wasm", source.cldEncodePublicId()))
        }

        private fun buildAction(values: List<Any>) =
            CustomFunction(ParamsAction(Param("custom_function", "fn", ParamValue(values))))
    }

    enum class Type {
        PRE_PROCESS,
        REMOTE,
        WASM
    }
}