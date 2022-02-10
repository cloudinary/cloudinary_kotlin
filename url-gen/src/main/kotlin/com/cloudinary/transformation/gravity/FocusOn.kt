package com.cloudinary.transformation.gravity

import com.cloudinary.transformation.TransformationDsl
import com.cloudinary.transformation.joinWithValues

class FocusOn(private val value: String) : IGravityObject, IAutoGravityObject {

    companion object {
        private val ocr = FocusOn("ocr_text")
        fun ocr() = ocr
        private val bicycle = FocusOn("bicycle")
        fun bicycle() = bicycle
        private val cat = FocusOn("cat")
        fun cat() = cat
        private val dog = FocusOn("dog")
        fun dog() = dog
        private val bird = FocusOn("bird")
        fun bird() = bird
        private val microwave = FocusOn("microwave")
        fun microwave() = microwave
        private val refrigerator = FocusOn("refrigerator")
        fun refrigerator() = refrigerator
        private val sink = FocusOn("sink")
        fun sink() = sink
        private val skateboard = FocusOn("skateboard")
        fun skateboard() = skateboard
        private val bottle = FocusOn("bottle")
        fun bottle() = bottle
        private val advancedFace = FocusOn("adv_face")
        fun advancedFace() = advancedFace
        private val advancedFaces = FocusOn("adv_faces")
        fun advancedFaces() = advancedFaces
        private val advancedEyes = FocusOn("adv_eyes")
        fun advancedEyes() = advancedEyes
        private val body = FocusOn("body")
        fun body() = body
        private val face = FocusOn("face")
        fun face() = face
        private val faces = FocusOn("faces")
        fun faces() = faces
        private val noFaces = FocusOn("no_faces")
        fun noFaces() = noFaces
        private val customNoOverride = FocusOn("custom_no_override")
        fun customNoOverride() = customNoOverride
        private val person = FocusOn("person")
        fun person() = person

        private val classic = FocusOn("classic")
        fun classic() = classic

        private val subject = FocusOn("subject")
        fun subject() = subject
    }

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