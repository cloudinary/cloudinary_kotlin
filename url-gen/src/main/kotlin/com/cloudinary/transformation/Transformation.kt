package com.cloudinary.transformation

const val ACTIONS_SEPARATOR = "/"

@TransformationDsl
class Transformation(private val actions: List<Action> = emptyList()) : ITransformable<Transformation> {

    constructor(action: Action) : this(listOf(action))

    override fun toString() = actions.joinToString(ACTIONS_SEPARATOR)

    override fun add(action: Action) = Transformation(actions + action)

    class Builder(private val components: MutableList<Action> = mutableListOf()) : ITransformable<Builder> {
        override fun add(action: Action) = apply { components.add(action) }
        fun build() = Transformation(components)
    }

    companion object {
        fun transformation(t: Builder.() -> Unit): Transformation {
            val builder = Builder()
            builder.t()
            return builder.build()
        }
    }
}

@TransformationDsl
interface TransformationComponentBuilder {
    fun build(): Action
}

