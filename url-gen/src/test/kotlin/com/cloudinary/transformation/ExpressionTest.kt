package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.expression.Expression
import org.junit.Test

class ExpressionTest {
    @Test
    fun testNormalizeExpression() {
        var result = Expression("\$width_100")
        cldAssert("\$width_100", result.toString())
    }

    @Test
    fun normalize_number_number() {
        val result = Expression(10)
        cldAssert("10", result.toString())
    }

    @Test
    fun normalize_emptyString_emptyString() {
        val result = Expression("")
        cldAssert("", result.toString())
    }

    @Test
    fun normalize_singleSpace_underscore() {
        val result = Expression(" ")
        cldAssert("_", result.toString())
    }

    @Test
    fun normalize_blankString_underscore() {
        val result = Expression("   ")
        cldAssert("_", result.toString())
    }

    @Test
    fun normalize_underscore_underscore() {
        val result = Expression("_")
        cldAssert("_", result.toString())
    }

    @Test
    fun normalize_underscores_underscore() {
        val result = Expression("___")
        cldAssert("_", result.toString())
    }

    @Test
    fun normalize_underscoresAndSpaces_underscore() {
        val result = Expression(" _ __  _")
        cldAssert("_", result.toString())
    }

    @Test
    fun normalize_arbitraryText_isNotAffected() {
        val result = Expression("foobar")
        cldAssert("foobar", result.toString())
    }

    @Test
    fun normalize_doubleAmpersand_replacedWithAndOperator() {
        val result = Expression("foo && bar")
        cldAssert("foo_and_bar", result.toString())
    }

    @Test
    fun normalize_doubleAmpersandWithNoSpaceAtEnd_isNotAffected() {
        val result = Expression("foo&&bar")
        cldAssert("foo&&bar", result.toString())
    }

    @Test
    fun normalize_width_recognizedAsVariableAndReplacedWithW() {
        val result = Expression("width")
        cldAssert("w", result.toString())
    }

    @Test
    fun normalize_initialAspectRatio_recognizedAsVariableAndReplacedWithW() {
        val result = Expression("initial_aspect_ratio")
        cldAssert("iar", result.toString())
    }

    @Test
    fun normalize_dollarWidth_recognizedAsUserVariableAndNotAffected() {
        val result = Expression("\$width")
        cldAssert("\$width", result.toString())
    }

    @Test
    fun normalize_dollarInitialAspectRatio_recognizedAsUserVariableAndAsVariableReplacedWithAr() {
        val result = Expression("\$initial_aspect_ratio")
        cldAssert("\$initial_ar", result.toString())
    }

    @Test
    fun normalize_dollarMyWidth_recognizedAsUserVariableAndNotAffected() {
        val result = Expression("\$mywidth")
        cldAssert("\$mywidth", result.toString())
    }

    @Test
    fun normalize_dollarWidthWidth_recognizedAsUserVariableAndNotAffected() {
        val result = Expression("\$widthwidth")
        cldAssert("\$widthwidth", result.toString())
    }

    @Test
    fun normalize_dollarUnderscoreWidth_recognizedAsUserVariableAndNotAffected() {
        val result = Expression("\$_width")
        cldAssert("\$_width", result.toString())
    }

    @Test
    fun normalize_dollarUnderscoreX2Width_recognizedAsUserVariableAndNotAffected() {
        val result = Expression("\$__width")
        cldAssert("\$_width", result.toString())
    }

    @Test
    fun normalize_dollarX2Width_recognizedAsUserVariableAndNotAffected() {
        val result = Expression("$\$width")
        cldAssert("$\$width", result.toString())
    }

    @Test
    fun normalize_doesntReplaceVariable_1() {
        val actual = Expression("\$height_100")
        cldAssert("\$height_100", actual)
    }

    @Test
    fun normalize_doesntReplaceVariable_2() {
        val actual = Expression("\$heightt_100")
        cldAssert("\$heightt_100", actual)
    }

    @Test
    fun normalize_doesntReplaceVariable_3() {
        val actual = Expression("$\$height_100")
        cldAssert("$\$height_100", actual)
    }

    @Test
    fun normalize_doesntReplaceVariable_4() {
        val actual = Expression("\$heightmy_100")
        cldAssert("\$heightmy_100", actual)
    }

    @Test
    fun normalize_doesntReplaceVariable_5() {
        val actual = Expression("\$myheight_100")
        cldAssert("\$myheight_100", actual)
    }

    @Test
    fun normalize_doesntReplaceVariable_6() {
        val actual = Expression("\$heightheight_100")
        cldAssert("\$heightheight_100", actual)
    }

    @Test
    fun normalize_doesntReplaceVariable_7() {
        val actual = Expression("\$theheight_100")
        cldAssert("\$theheight_100", actual)
    }

    @Test
    fun normalize_doesntReplaceVariable_8() {
        val actual = Expression("\$__height_100")
        cldAssert("\$_height_100", actual)
    }
}