package com.cloudinary.transformation

@TransformationDsl
class Transformation(internal val actions: List<Action> = emptyList()) : ITransformable<Transformation> {
    constructor(options: Builder.() -> Unit) : this(fromBuilder(options))

    constructor(action: Action) : this(listOf(action))

    override fun toString() = actions.joinToString(DEFAULT_COMPONENT_SEPARATOR)

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

private fun fromBuilder(options: Transformation.Builder.() -> Unit): List<Action> {
    val builder = Transformation.Builder()
    builder.options()
    return builder.build().actions
}

private fun fromImageBuilder(options: ImageTransformation.Builder.() -> Unit): List<Action> {
    val builder = ImageTransformation.Builder()
    builder.options()
    return builder.build().actions
}

private fun fromVideoBuilder(options: VideoTransformation.Builder.() -> Unit): List<Action> {
    val builder = VideoTransformation.Builder()
    builder.options()
    return builder.build().actions
}

@TransformationDsl
class ImageTransformation(internal val actions: List<Action> = emptyList()) : ITransformableImage<ImageTransformation> {

    constructor(action: Action) : this(listOf(action))
    constructor(options: Builder.() -> Unit) : this(fromImageBuilder(options))

    override fun toString() = actions.joinToString(DEFAULT_COMPONENT_SEPARATOR)

    override fun add(action: Action) = ImageTransformation(actions + action)

    class Builder(private val components: MutableList<Action> = mutableListOf()) : ITransformableImage<Builder> {
        override fun add(action: Action) = apply { components.add(action) }
        fun build() = ImageTransformation(components)
    }
}

@TransformationDsl
class VideoTransformation(internal val actions: List<Action> = emptyList()) : ITransformableVideo<VideoTransformation> {

    constructor(action: Action) : this(listOf(action))
    constructor(options: Builder.() -> Unit) : this(fromVideoBuilder(options))

    override fun toString() = actions.joinToString(DEFAULT_COMPONENT_SEPARATOR)

    override fun add(action: Action) = VideoTransformation(actions + action)

    class Builder(private val components: MutableList<Action> = mutableListOf()) : ITransformableVideo<Builder> {
        override fun add(action: Action) = apply { components.add(action) }
        fun build() = VideoTransformation(components)
    }
}

