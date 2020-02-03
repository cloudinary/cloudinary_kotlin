package com.cloudinary.upload.request.params

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Coordinates(val coordinates: List<Rectangle>) {
    constructor(rect: IntArray) : this(mutableListOf(Rectangle(rect[0], rect[1], rect[2], rect[3])))
    constructor(vararg rects: Rectangle) : this(rects.toMutableList())
    constructor(stringCoords: String) : this(parseString(stringCoords))

    companion object {
        @Throws(IllegalArgumentException::class)
        fun parse(coordinates: Any): Coordinates {
            return when (coordinates) {
                is Coordinates -> coordinates
                is IntArray -> Coordinates(coordinates)
                is Rectangle -> Coordinates(coordinates)
                else -> Coordinates(coordinates.toString())
            }
        }
    }
}

private fun parseString(stringCoords: String): MutableList<Rectangle> {
    val coordinates = mutableListOf<Rectangle>()
    for (stringRect in stringCoords.split("\\|").toTypedArray()) {
        if (stringRect.isEmpty()) continue
        val elements = stringRect.split(",").toTypedArray()
        require(elements.size == 4) {
            String.format(
                "Must supply exactly 4 values for coordinates (x,y,width,height) %d supplied: %s",
                elements.size, stringRect
            )
        }
        coordinates.add(
            Rectangle(
                elements[0].toInt(),
                elements[1].toInt(),
                elements[2].toInt(),
                elements[3].toInt()
            )
        )
    }

    return coordinates
}

@JsonClass(generateAdapter = true)
data class Rectangle(val x: Int, val y: Int, val width: Int, val height: Int)