package com.cloudinary.transformation

import com.cloudinary.cldAssertEqualsAsString
import com.cloudinary.testParam
import org.junit.Test

class CustomFunctionTest {
    @Test
    fun testWasmFunction() {
        cldAssertEqualsAsString("fn_wasm:my_example.wasm", wasm("my_example.wasm"))
        cldAssertEqualsAsString(
            "fn_wasm:my_example.wasm,test_param",
            wasm("my_example.wasm").add(testParam)
        )
    }

    @Test
    fun testRemoteFunction() {
        cldAssertEqualsAsString(
            "fn_remote:aHR0cHM6Ly9teS5leGFtcGxlLmN1c3RvbS9mdW5jdGlvbg==",
            remote("https://my.example.custom/function")
        )
    }

    @Test
    fun testPreProcessRemoteFunction() {
        cldAssertEqualsAsString(
            "fn_pre:remote:aHR0cHM6Ly9teS5leGFtcGxlLmN1c3RvbS9mdW5jdGlvbg==",
            preProcess("https://my.example.custom/function")
        )
    }
}