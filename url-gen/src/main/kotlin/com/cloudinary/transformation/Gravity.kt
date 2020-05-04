package com.cloudinary.transformation

open class Gravity protected constructor(value: ParamValue) : Param("gravity", "g", value) {
    private constructor(direction: Direction) : this(ParamValue(direction))
    private constructor(focus: FocalPoint) : this(ParamValue(focus))

    companion object {
        fun south() = Gravity(Direction.SOUTH)
        fun southEast() = Gravity(Direction.SOUTH_EAST)
        fun southWest() = Gravity(Direction.SOUTH_WEST)
        fun north() = Gravity(Direction.NORTH)
        fun northEast() = Gravity(Direction.NORTH_EAST)
        fun northWest() = Gravity(Direction.NORTH_WEST)
        fun east() = Gravity(Direction.EAST)
        fun west() = Gravity(Direction.WEST)
        fun center() = Gravity(Direction.CENTER)
        fun advancedFace() = Gravity(FocalPoint.ADVANCED_FACE)
        fun advancedFaces() = Gravity(FocalPoint.ADVANCED_FACES)
        fun advancedEyes() = Gravity(FocalPoint.ADVANCED_EYES)
        fun body() = Gravity(FocalPoint.BODY)
        fun face() = Gravity(FocalPoint.FACE)
        fun faces() = Gravity(FocalPoint.FACES)
        fun noFaces() = Gravity(FocalPoint.NO_FACES)
        fun customNoOverride() = Gravity(FocalPoint.CUSTOM_NO_OVERRIDE)

        fun auto(focalPoint: FocalPoint? = null) = AutoGravity(focalPoint)

    }
}

class AutoGravity(focalPoint: FocalPoint?) : Gravity(ParamValue(listOfNotNull("auto", focalPoint)))

private enum class Direction(private val value: String) {
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

enum class FocalPoint(private val value: String) {
    ADVANCED_FACE("adv_face"),
    ADVANCED_FACES("adv_faces"),
    ADVANCED_EYES("adv_eyes"),
    BODY("body"),
    FACE("face"),
    FACES("faces"),
    NO_FACES("no_faces"),
    CUSTOM_NO_OVERRIDE("custom_no_override"),
    NONE("none");

    override fun toString(): String {
        return value
    }
}

