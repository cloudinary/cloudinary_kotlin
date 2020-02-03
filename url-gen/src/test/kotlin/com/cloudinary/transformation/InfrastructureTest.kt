package com.cloudinary.transformation

import com.cloudinary.cldAssert
import org.junit.Test

class InfrastructureTest {
    @Test
    fun testRawComponents() {
        cldAssert("raw_component", Transformation().add("raw_component"))
        cldAssert("raw_component", Transformation().add(RawAction("raw_component")))
    }

    @Test
    fun testParamValue() {
        cldAssert("val1", ParamValue("val1"))
        cldAssert("val1:val2", ParamValue(listOf("val1", "val2")))
        cldAssert("val1_val2", ParamValue(listOf("val1", "val2").cldAsParamValueContent(), "_"))
        cldAssert(
            "val1:val2:val3_val4",
            ParamValue(listOf("val1", "val2", ParamValue(listOf("val3", "val4").cldAsParamValueContent(), "_")))
        )
    }

    @Test
    fun testParam() {
        val paramValue = ParamValue(listOf("val1", "val2"))
        cldAssert("t_val1:val2", Param("test", "t", paramValue))
    }

    @Test
    fun testAction() {
        val paramValue = ParamValue(listOf("val1", "val2"))
        val paramValue3 = ParamValue(listOf("val3", "val4"))

        val param = Param("a", "a", paramValue)
        val param2 = FlagsParam(FlagKey.Tiled())
        val param3 = FlagsParam(FlagKey.NoOverflow())
        val param4 = Param("b", "b", paramValue3)

        val baseAction = GenericAction(param)
        val withAdd = baseAction.add(param2).add(param3).add(param4).add(param).flag(FlagKey.Cutter())
        val withAddReversed = baseAction.add(param4).add(param3).flag(FlagKey.Cutter()).add(param).add(param2)
        val withAddedCollection = baseAction.add(listOf(param2, param4, param, param3)).flag(FlagKey.Cutter())

        val correctString = "a_val1:val2,b_val3:val4,fl_cutter.no_overflow.tiled"
        cldAssert(correctString, withAdd)
        cldAssert(correctString, withAddReversed)
        cldAssert(correctString, withAddedCollection)
    }
}