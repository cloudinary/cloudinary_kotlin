package com.cloudinary.transformation

@TransformationDsl
class Transformation(private val actions: List<Action> = emptyList()) : ITransformable<Transformation> {

    constructor(action: Action) : this(listOf(action))

    override fun toString() = actions.joinToString("/")

    override fun add(action: Action) = Transformation(actions + action)

    fun addFlag(flag: Flag) = Transformation(actions + ParamsAction(flag.cldAsFlag()))

    class Builder(private val components: MutableList<Action> = mutableListOf()) : ITransformable<Builder> {
        override fun add(action: Action) = apply { components.add(action) }
        fun build() = Transformation(components)
    }
}

@TransformationDsl
interface TransformationComponentBuilder {
    fun build(): Action
}

fun transformation(t: Transformation.Builder.() -> Unit): Transformation {
    val builder = Transformation.Builder()
    builder.t()
    return builder.build()
}