package com.cloudinary.transformation

import com.cloudinary.util.cldEncodePublicId
import com.cloudinary.util.cldToUrlSafeBase64

abstract class CustomFunction : Action {

    companion object {
        fun wasm(publicId: String) = WasmCustomFunction(publicId)
        fun remote(url: String) = RemoteCustomFunction(url)
        fun preprocessRemote(url: String) = PreprocessCustomFunction(url)
    }
}

class RemoteCustomFunction(private val url: String) : CustomFunction() {
    override fun toString(): String {
        return "fn_remote:${url.cldToUrlSafeBase64()}"
    }
}

class PreprocessCustomFunction(private val url: String) : CustomFunction() {
    override fun toString(): String {
        return "fn_pre:remote:${url.cldToUrlSafeBase64()}"
    }
}

class WasmCustomFunction(private val wasmPublicId: String) : CustomFunction() {
    override fun toString(): String {
        return "fn_wasm:${wasmPublicId.cldEncodePublicId()}"
    }
}