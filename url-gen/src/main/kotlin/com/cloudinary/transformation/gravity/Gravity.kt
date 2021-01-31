package com.cloudinary.transformation.gravity

import com.cloudinary.transformation.TransformationDsl
import com.cloudinary.transformation.joinWithValues

// TODO these classes don't yet make enough sense
abstract class Gravity {
    companion object {
        fun south() =
            CompassGravity(Compass.south())

        fun southEast() =
            CompassGravity(Compass.southEast())

        fun southWest() =
            CompassGravity(Compass.southWest())

        fun north() =
            CompassGravity(Compass.north())

        fun northEast() =
            CompassGravity(Compass.northEast())

        fun northWest() =
            CompassGravity(Compass.northWest())

        fun east() =
            CompassGravity(Compass.east())

        fun west() =
            CompassGravity(Compass.west())

        fun center() =
            CompassGravity(Compass.center())

        fun focusOn(
            focusOn: FocusOn,
            vararg focusOnObjects: FocusOn,
            options: (FocusOnGravity.Builder.() -> Unit)? = null
        ): FocusOnGravity {
            val builder = FocusOnGravity.Builder(mutableListOf(focusOn).also { it.addAll(focusOnObjects) })
            options?.let { builder.it() }
            return builder.build()
        }

        fun autoGravity(
            vararg objects: IAutoGravityObject,
            options: (AutoGravity.Builder.() -> Unit)? = null
        ): AutoGravity {
            val builder = AutoGravity.Builder()
            builder.autoFocus(*objects)
            options?.let { builder.it() }
            return builder.build()
        }

        fun compass(compass: Compass) = CompassGravity(compass)

        fun xyCenter() = XYCenterGravity()
    }
}

class CompassGravity internal constructor(private val compass: Compass) : Gravity() {
    override fun toString(): String {
        return compass.toString()
    }
}

class FocusOnGravity internal constructor(
    private val focusOnObjects: List<FocusOn>,
    private val fallbackGravity: AutoGravity? = null
) : Gravity() {
    override fun toString(): String {
        return focusOnObjects.joinToString(":").joinWithValues(fallbackGravity)
    }

    @TransformationDsl
    class Builder(private val objects: MutableList<FocusOn>) {
        private var fallbackGravity: AutoGravity? = null

        fun fallbackGravity(gravity: AutoGravity) = apply { this.fallbackGravity = gravity }
        fun build(): FocusOnGravity {
            return FocusOnGravity(objects, fallbackGravity)
        }
    }
}

class AutoGravity internal constructor(private val objects: List<IAutoGravityObject>) : Gravity() {
    override fun toString(): String {
        return "auto".joinWithValues(*objects.toTypedArray())
    }

    @TransformationDsl
    class Builder {
        val objects = mutableListOf<IAutoGravityObject>()

        fun autoFocus(vararg objects: IAutoGravityObject) = apply {
            this.objects.addAll(objects)
        }

        fun build(): AutoGravity {
            return AutoGravity(objects)
        }
    }
}

class AutoFocus {

    companion object {
        fun focusOn(focus: FocusOn, options: (AutoGravityObject.Builder.() -> Unit)? = null): AutoGravityObject {
            val builder = AutoGravityObject.Builder(focus)
            options?.let { builder.it() }
            return builder.build()
        }
    }
}

class Compass(private val value: String) {
    companion object {
        private val north = Compass("north")
        fun north() = north
        private val northEast = Compass("north_east")
        fun northEast() = northEast
        private val northWest = Compass("north_west")
        fun northWest() = northWest
        private val east = Compass("east")
        fun east() = east
        private val southEast = Compass("south_east")
        fun southEast() = southEast
        private val south = Compass("south")
        fun south() = south
        private val southWest = Compass("south_west")
        fun southWest() = southWest
        private val west = Compass("west")
        fun west() = west
        private val center = Compass("center")
        fun center() = center
    }

    override fun toString(): String {
        return value
    }
}

class XYCenterGravity : Gravity() {
    override fun toString() = "xy_center"
}