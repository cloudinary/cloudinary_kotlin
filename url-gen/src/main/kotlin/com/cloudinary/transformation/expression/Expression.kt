package com.cloudinary.transformation.expression

import com.cloudinary.util.cldMergeToSingleUnderscore
import java.util.regex.Pattern

val OPERATORS = mapOf(
    "=" to "eq",
    "!=" to "ne",
    "<" to "lt",
    ">" to "gt",
    "<=" to "lte",
    ">=" to "gte",
    "&&" to "and",
    "||" to "or",
    "*" to "mul",
    "/" to "div",
    "+" to "add",
    "-" to "sub"
)

val PREDEFINED_VARS = mapOf(
    "width" to "w",
    "height" to "h",
    "initial_width" to "iw",
    "initialWidth" to "iw",
    "initialHeight" to "ih",
    "initial_height" to "ih",
    "aspect_ratio" to "ar",
    "initial_aspect_ratio" to "iar",
    "aspectRatio" to "ar",
    "initialAspectRatio" to "iar",
    "page_count" to "pc",
    "pageCount" to "pc",
    "face_count" to "fc",
    "faceCount" to "fc",
    "current_page" to "cp",
    "currentPage" to "cp",
    "tags" to "tags",
    "pageX" to "px",
    "pageY" to "py",
    "duration" to "du",
    "initial_duration" to "idu",
    "initialDuration" to "idu"
)

val PATTERN = getPattern()

class Expression(private val values: List<Any> = listOf()) {
    constructor(value: Any) : this(listOf(value))

    override fun toString(): String {
        return values.joinToString("_", transform = { it.cldNormalize() })
    }

    fun gt(value: Any): Expression {
        return Expression(values + "gt" + value)
    }

    fun and(value: Any): Expression {
        return Expression(values + "and" + value)
    }

    fun or(value: Any): Expression {
        return Expression(values + "or" + value)
    }

    fun eq(value: Any): Expression {
        return Expression(values + "eq" + value)
    }

    fun ne(value: Any): Expression {
        return Expression(values + "ne" + value)
    }

    fun lt(value: Any): Expression {
        return Expression(values + "lt" + value)
    }

    fun lte(value: Any): Expression {
        return Expression(values + "lte" + value)
    }

    fun gte(value: Any): Expression {
        return Expression(values + "gte" + value)
    }

    fun div(value: Any): Expression {
        return Expression(values + "div" + value)
    }

    fun multiply(value: Any): Expression {
        return Expression(values + "mul" + value)
    }

    fun add(value: Any): Expression {
        return Expression(values + "add" + value)
    }

    fun sub(value: Any): Expression {
        return Expression(values + "sub" + value)
    }

    fun value(value: Any): Expression {
        return Expression(values + value.toString())
    }

    companion object {
        fun expression(expression: String): Expression {
            return Expression(expression)
        }

        fun faceCount() = Expression("fc")

        /*
    * @returns a new expression with the predefined variable "width"
     */
        fun width() = Expression("width")

        /*
    * @returns a new expression with the predefined variable "height"
     */
        fun height() = Expression("height")

        /*
    * @returns a new expression with the predefined variable "initialWidth"
     */
        fun initialWidth() = Expression("initialWidth")

        /*
    * @returns a new expression with the predefined variable "initialHeight"
     */
        fun initialHeight() = Expression("initialHeight")

        /*
    * @returns a new expression with the predefined variable "aspectRatio"
     */
        fun aspectRatio() = Expression("aspectRatio")

        /*
    * @returns a new expression with the predefined variable "initialAspectRatio"
     */
        fun initialAspectRatio() = Expression("initialAspectRatio")

        /*
    * @returns a new expression with the predefined variable "pageCount"
     */
        fun pageCount() = Expression("pageCount")

        /*
    * @returns a new expression with the predefined variable "currentPage"
     */
        fun currentPage() = Expression("currentPage")

        /*
    * @returns a new expression with the predefined variable "tags"
     */
        fun tags() = Expression("tags")

        /*
    * @returns a new expression with the predefined variable "pageX"
     */
        fun pageX() = Expression("pageX")

        /*
    * @returns a new expression with the predefined variable "pageY"
     */
        fun pageY() = Expression("pageY")
    }
}

internal fun Any.cldNormalize(): String {
    val expression = this

    var replacement: String
    val conditionStr = expression.toString().cldMergeToSingleUnderscore()
    val matcher = PATTERN.matcher(conditionStr)
    val result = StringBuffer(conditionStr.length)

    while (matcher.find()) {
        replacement = when {
            OPERATORS.containsKey(matcher.group()) -> OPERATORS[matcher.group()] as String
            PREDEFINED_VARS.containsKey(matcher.group()) -> PREDEFINED_VARS[matcher.group()] as String
            else -> matcher.group()
        }
        matcher.appendReplacement(result, replacement)
    }

    matcher.appendTail(result)
    return result.toString()
}

/**
 * @return a regex pattern for operators and predefined vars as /((operators)(?=[ _])|variables)/
 */
private fun getPattern(): Pattern {
    val pattern: String
    val operators = OPERATORS.keys.sortedDescending()
    val sb = StringBuilder("((")
    for (op in operators) {
        sb.append(Pattern.quote(op)).append("|")
    }
    sb.deleteCharAt(sb.length - 1)
    sb.append(")(?=[ _])|").append(PREDEFINED_VARS.keys.map {":${it}|${it}"}.joinToString("|", transform = { "(?<!\\$)$it" })).append(")")
    pattern = sb.toString()
    return Pattern.compile(pattern)
}

internal fun String.asVariableName() = if (startsWith("\$")) this else "\$${this}"