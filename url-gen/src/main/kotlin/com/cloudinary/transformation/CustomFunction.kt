package com.cloudinary.transformation

import com.cloudinary.util.cldEncodePublicId
import com.cloudinary.util.cldToUrlSafeBase64

abstract class CustomFunction : Action {

    companion object {
        fun wasm(publicId: String) = WasmCustomFunction(publicId)

        fun remote(url: String, options: (RemoteCustomFunction.Builder.() -> Unit)? = null): RemoteCustomFunction {
            val builder = RemoteCustomFunction.Builder(url)
            options?.let { builder.it() }
            return builder.build()
        }
    }
}

class RemoteCustomFunction(private val url: String, private val preprocess: Boolean? = null) : CustomFunction() {
    override fun toString(): String {
        val base64Url = url.cldToUrlSafeBase64()
        return if (preprocess == true) {
            return "fn_pre:remote:$base64Url"
        } else {
            "fn_remote:$base64Url"
        }
    }

    class Builder(val url: String) : TransformationComponentBuilder {
        private var preprocess: Boolean? = null

        fun preprocess() = apply { this.preprocess = true }
        override fun build(): RemoteCustomFunction {
            return RemoteCustomFunction(url, preprocess)
        }
    }
}

class WasmCustomFunction(private val wasmPublicId: String) : CustomFunction() {
    override fun toString(): String {
        return "fn_wasm:${wasmPublicId.cldEncodePublicId()}"
    }
}