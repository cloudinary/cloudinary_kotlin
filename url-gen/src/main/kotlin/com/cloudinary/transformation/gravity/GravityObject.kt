package com.cloudinary.transformation.gravity

import com.cloudinary.transformation.joinWithValues

sealed class GravityObject(private val value: String) : IGravityObject, IAutoGravityObject {
    object CAT : GravityObject("cat")
    object DOG : GravityObject("dog")
    object BIRD : GravityObject("bird")
    object MICROWAVE : GravityObject("microwave")
    object REFRIGERATOR : GravityObject("refrigerator")
    object SINK : GravityObject("sink")
    object SKATEBOARD : GravityObject("skateboard")
    object BOTTLE : GravityObject("bottle")
    object ADVANCED_FACE : GravityObject("adv_face")
    object ADVANCED_FACES : GravityObject("adv_faces")
    object ADVANCED_EYES : GravityObject("adv_eyes")
    object BODY : GravityObject("body")
    object FACE : GravityObject("face")
    object FACES : GravityObject("faces")
    object NO_FACES : GravityObject("no_faces")
    object CUSTOM_NO_OVERRIDE : GravityObject("custom_no_override")

    override fun toString() = value
}

fun IGravityObject.avoid(): IAutoGravityObject {
    return AutoGravityObject(this, listOf("avoid"))
}

fun IGravityObject.weight(weight: Int): IAutoGravityObject {
    return AutoGravityObject(this, listOf(weight))
}

class AutoGravityObject internal constructor(
    private val gravityObject: IGravityObject,
    private val modifiers: List<Any>
) : IAutoGravityObject {
    override fun toString(): String {
        return "$gravityObject".joinWithValues(*(modifiers.toTypedArray()), separator = "_")
    }
}

interface IGravityObject
interface IAutoGravityObject