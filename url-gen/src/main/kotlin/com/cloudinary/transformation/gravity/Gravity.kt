package com.cloudinary.transformation.gravity

import com.cloudinary.transformation.Param
import com.cloudinary.transformation.ParamValue

// TODO these classes don't yet make enough sense
open class Gravity internal constructor(value: ParamValue) : Param("gravity", "g", value) {
    internal constructor(direction: Direction) : this(
        ParamValue(direction)
    )

    constructor(focus: FocalPoint) : this(
        ParamValue(
            focus
        )
    )

    companion object {
        fun gravityObject(vararg objects: IObjectGravity) = Gravity(ParamValue(objects))

        fun south() =
            Gravity(Direction.SOUTH)

        fun southEast() =
            Gravity(Direction.SOUTH_EAST)

        fun southWest() =
            Gravity(Direction.SOUTH_WEST)

        fun north() =
            Gravity(Direction.NORTH)

        fun northEast() =
            Gravity(Direction.NORTH_EAST)

        fun northWest() =
            Gravity(Direction.NORTH_WEST)

        fun east() =
            Gravity(Direction.EAST)

        fun west() =
            Gravity(Direction.WEST)

        fun center() =
            Gravity(Direction.CENTER)

        fun advancedFace() =
            Gravity(FocalPoint.ADVANCED_FACE)

        fun advancedFaces() =
            Gravity(FocalPoint.ADVANCED_FACES)

        fun advancedEyes() =
            Gravity(FocalPoint.ADVANCED_EYES)

        fun ocrText() = Gravity(ParamValue("ocr_text"))

        fun body() =
            Gravity(FocalPoint.BODY)

        fun face() =
            Gravity(FocalPoint.FACE)

        fun faces() =
            Gravity(FocalPoint.FACES)

        fun noFaces() =
            Gravity(FocalPoint.NO_FACES)

        fun customNoOverride() =
            Gravity(FocalPoint.CUSTOM_NO_OVERRIDE)

        fun auto(focalPoint: FocalPoint? = null) =
            Gravity(ParamValue(listOfNotNull("auto", focalPoint)))

        fun auto(gravity: AutoGravity, options: (AutoGravityBuilder.() -> Unit)? = null): Gravity {
            val builder = AutoGravityBuilder(gravity)
            options?.let { builder.it() }
            return builder.build()
        }

        fun auto(gravityObject: ObjectGravity, vararg objects: ObjectGravity) =
            Gravity(ParamValue(listOf("auto", gravityObject) + objects.asList()))
    }
}

class AutoGravity internal constructor(objects: Any?) : Gravity(ParamValue(listOfNotNull("auto", objects))) {
    companion object {
        fun classic() = AutoGravity("classic")
        fun gravityObject(gravityObject: IObjectGravity): IObjectGravity = AutoGravityObject(gravityObject)
    }
}

class AutoGravityBuilder(private val gravity: AutoGravity) {
    private var avoid: Boolean = false

    fun avoid() = apply { this.avoid = true }

    fun build() = if (avoid) AutoGravity(ParamValue(listOf(gravity, "avoid"), "_")) else gravity
}

private class AutoGravityObject(gravityObject: IObjectGravity) : ParamValue(listOf("auto", gravityObject)),
    IObjectGravity

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

