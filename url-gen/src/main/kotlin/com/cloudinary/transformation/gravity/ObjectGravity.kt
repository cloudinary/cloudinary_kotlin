package com.cloudinary.transformation.gravity

sealed class ObjectGravity(private val value: String) : IObjectGravity {
    object CAT : ObjectGravity("cat")
    object DOG : ObjectGravity("dog")
    object BIRD : ObjectGravity("dog")
    object MICROWAVE : ObjectGravity("microwave")
    object REFRIGERATOR : ObjectGravity("refrigerator")
    object SINK : ObjectGravity("sink")
    object SKATEBOARD : ObjectGravity("skateboard")
    object BOTTLE : ObjectGravity("bottle")
}

interface IObjectGravity