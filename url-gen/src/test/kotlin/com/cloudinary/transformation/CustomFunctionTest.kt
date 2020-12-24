package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.CustomFunction.Companion.remote
import com.cloudinary.transformation.CustomFunction.Companion.wasm
import org.junit.Test

class CustomFunctionTest {
    @Test
    fun testWasmFunction() {
        cldAssert("fn_wasm:my_example.wasm", wasm("my_example.wasm"))
    }

    @Test
    fun testRemoteFunction() {
        cldAssert(
            "fn_remote:aHR0cHM6Ly9teS5leGFtcGxlLmN1c3RvbS9mdW5jdGlvbg==",
            remote("https://my.example.custom/function")
        )
    }

    @Test
    fun testPreProcessRemoteFunction() {
        cldAssert(
            "fn_pre:remote:aHR0cHM6Ly9teS5leGFtcGxlLmN1c3RvbS9mdW5jdGlvbg==",
            remote("https://my.example.custom/function") {
                preprocess()
            }
        )
    }
}