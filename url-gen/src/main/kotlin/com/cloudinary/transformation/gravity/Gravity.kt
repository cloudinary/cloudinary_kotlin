package com.cloudinary.transformation.gravity

import com.cloudinary.transformation.joinWithValues

// TODO these classes don't yet make enough sense
abstract class Gravity {
    companion object {
        fun south() =
            GravityByDirection(Direction.SOUTH)

        fun southEast() =
            GravityByDirection(Direction.SOUTH_EAST)

        fun southWest() =
            GravityByDirection(Direction.SOUTH_WEST)

        fun north() =
            GravityByDirection(Direction.NORTH)

        fun northEast() =
            GravityByDirection(Direction.NORTH_EAST)

        fun northWest() =
            GravityByDirection(Direction.NORTH_WEST)

        fun east() =
            GravityByDirection(Direction.EAST)

        fun west() =
            GravityByDirection(Direction.WEST)

        fun center() =
            GravityByDirection(Direction.CENTER)

        fun ocrText() = GravityByOcr()

        fun objects(objectGravity: IGravityObject, vararg objects: IGravityObject) =
            GravityByObjects(listOf(objectGravity) + objects)

        fun auto(vararg objects: IAutoGravityObject) = GravityByAutoAlgorithm(objects.toList())
    }
}

class GravityByDirection internal constructor(private val direction: Direction) : Gravity() {
    override fun toString(): String {
        return direction.toString()
    }
}

class GravityByObjects internal constructor(private val objects: List<IGravityObject>) : Gravity() {
    override fun toString(): String {
        return objects.joinToString(":")
    }
}

class GravityByAutoAlgorithm internal constructor(private val objects: List<IAutoGravityObject>) : Gravity() {
    override fun toString(): String {
        return "auto".joinWithValues(*objects.toTypedArray())
    }
}

class GravityByOcr : Gravity() {
    override fun toString(): String {
        return "ocr_text"
    }
}

internal enum class Direction(private val value: String) {
    NORTH("north"),
    NORTH_EAST("north_east"),
    NORTH_WEST("north_west"),
    EAST("east"),
    SOUTH_EAST("south_east"),
    SOUTH("south"),
    SOUTH_WEST("south_west"),
    WEST("west"),
    CENTER("center");

    override fun toString(): String {
        return value
    }
}