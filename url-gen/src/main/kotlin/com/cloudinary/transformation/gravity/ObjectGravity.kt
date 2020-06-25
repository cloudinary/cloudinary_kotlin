package com.cloudinary.transformation.gravity

import com.cloudinary.transformation.ParamValue

sealed class ObjectGravity(private val value: String) : IObjectGravity, IAutoObjectGravity {
    object CAT : ObjectGravity("cat")
    object DOG : ObjectGravity("dog")
    object BIRD : ObjectGravity("bird")
    object MICROWAVE : ObjectGravity("microwave")
    object REFRIGERATOR : ObjectGravity("refrigerator")
    object SINK : ObjectGravity("sink")
    object SKATEBOARD : ObjectGravity("skateboard")
    object BOTTLE : ObjectGravity("bottle")

    override fun toString() = value
}

fun IObjectGravity.avoid(): AutoObjectGravity {
    return AutoObjectGravity(this, "avoid")
}

fun IObjectGravity.weight(weight: Int): AutoObjectGravity {
    return AutoObjectGravity(this, weight)
}

class AutoObjectGravity internal constructor(objectGravity: IObjectGravity, vararg modifiers: Any) :
    ParamValue(listOf(objectGravity) + ParamValue(modifiers), "_"), IAutoObjectGravity {

    override fun toString(): String {
        return super.toString()
    }
}

interface IObjectGravity
interface IAutoObjectGravity