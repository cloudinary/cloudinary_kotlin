package com.cloudinary.transformation.gravity

import com.cloudinary.transformation.TransformationDsl
import com.cloudinary.transformation.joinWithValues

sealed class FocusOn(private val value: String) : IGravityObject, IAutoGravityObject {
    object CAT : FocusOn("cat")
    object DOG : FocusOn("dog")
    object BIRD : FocusOn("bird")
    object MICROWAVE : FocusOn("microwave")
    object REFRIGERATOR : FocusOn("refrigerator")
    object SINK : FocusOn("sink")
    object SKATEBOARD : FocusOn("skateboard")
    object BOTTLE : FocusOn("bottle")
    object ADVANCED_FACE : FocusOn("adv_face")
    object ADVANCED_FACES : FocusOn("adv_faces")
    object ADVANCED_EYES : FocusOn("adv_eyes")
    object BODY : FocusOn("body")
    object FACE : FocusOn("face")
    object FACES : FocusOn("faces")
    object NO_FACES : FocusOn("no_faces")
    object CUSTOM_NO_OVERRIDE : FocusOn("custom_no_override")

    override fun toString() = value
}

fun IGravityObject.avoid(): IAutoGravityObject {
    return AutoGravityObject(this, avoid = true)
}

fun IGravityObject.weight(weight: Int): IAutoGravityObject {
    return AutoGravityObject(this, weight = weight)
}

class AutoGravityObject internal constructor(
    private val gravityObject: IGravityObject,
    private val weight: Int? = null,
    private val avoid: Boolean? = null
) : IAutoGravityObject {
    override fun toString(): String {
        val weightStr = if (avoid == true) "avoid" else (weight?.toString())
        return "$gravityObject".joinWithValues(weightStr, separator = "_")
    }

    @TransformationDsl
    class Builder(private val focusOn: FocusOn) {

        private var weight: Int? = null
        private var avoid: Boolean? = null

        fun weight(weight: Int) = apply { this.weight = weight }
        fun avoid() = apply { this.avoid = true }

        fun build(): AutoGravityObject {
            return AutoGravityObject(focusOn, weight, avoid)
        }
    }
}

interface IGravityObject
interface IAutoGravityObject