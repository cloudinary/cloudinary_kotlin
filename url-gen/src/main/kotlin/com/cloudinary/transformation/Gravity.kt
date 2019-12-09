package com.cloudinary.transformation

open class Gravity protected constructor(value: ParamValue) : Param("gravity", "g", value) {
    companion object {
        fun auto(focalPoint: FocalPoint? = null) = AutoGravity(focalPoint)
        fun direction(direction: Direction) = Gravity(ParamValue(listOf(direction)))
    }
}

class AutoGravity(focalPoint: FocalPoint?) : Gravity(ParamValue(listOfNotNull("auto", focalPoint)))

enum class Direction(private val value: String) {
    NORTH("north"),
    EAST("east"),
    SOUTH("south"),
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

