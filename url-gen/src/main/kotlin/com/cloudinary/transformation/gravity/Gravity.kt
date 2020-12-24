package com.cloudinary.transformation.gravity

import com.cloudinary.transformation.TransformationDsl
import com.cloudinary.transformation.joinWithValues

// TODO these classes don't yet make enough sense
abstract class Gravity {
    companion object {
        fun south() =
            CompassGravity(Compass.SOUTH)

        fun southEast() =
            CompassGravity(Compass.SOUTH_EAST)

        fun southWest() =
            CompassGravity(Compass.SOUTH_WEST)

        fun north() =
            CompassGravity(Compass.NORTH)

        fun northEast() =
            CompassGravity(Compass.NORTH_EAST)

        fun northWest() =
            CompassGravity(Compass.NORTH_WEST)

        fun east() =
            CompassGravity(Compass.EAST)

        fun west() =
            CompassGravity(Compass.WEST)

        fun center() =
            CompassGravity(Compass.CENTER)

        fun ocr(options: (OcrGravity.Builder.() -> Unit)? = null): OcrGravity {
            val builder = OcrGravity.Builder()
            options?.let { builder.it() }
            return builder.build()
        }

        fun focusOn(
            focusOn: FocusOn,
            vararg focusOnObjects: FocusOn,
            options: (FocusOnGravity.Builder.() -> Unit)? = null
        ): FocusOnGravity {
            val builder = FocusOnGravity.Builder(mutableListOf(focusOn).also { it.addAll(focusOnObjects) })
            options?.let { builder.it() }
            return builder.build()
        }

        fun auto(vararg objects: IAutoGravityObject, options: (AutoGravity.Builder.() -> Unit)? = null): AutoGravity {
            val builder = AutoGravity.Builder()
            builder.autoFocus(*objects)
            options?.let { builder.it() }
            return builder.build()
        }

        fun compass(compass: Compass) = CompassGravity(compass)
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

class OcrGravity(private val avoid: Boolean? = null) : Gravity() {
    override fun toString(): String {
        return "ocr_text".joinWithValues(avoid?.let { "avoid" }, separator = "_")
    }

    class Builder {
        var avoid: Boolean? = null

        fun avoid() = apply { this.avoid = true }

        fun build(): OcrGravity {
            return OcrGravity(avoid)
        }
    }
}

enum class Compass(private val value: String) {
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