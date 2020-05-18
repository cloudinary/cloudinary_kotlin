package com.cloudinary.transformation.layer

import com.cloudinary.transformation.*
import com.cloudinary.transformation.Transformation.Builder

// TODO simplify
class Position(internal val action: ParamsAction) : Action by action {

    class Builder : TransformationComponentBuilder {
        private var gravity: Gravity? = null

        private var x: Any? = null

        private var y: Any? = null

        private var tileMode: TileMode? = null

        private var allowOverflow: Boolean = true

        override fun build() = Position(
            ParamsAction(
                listOfNotNull(
                    gravity,
                    x?.cldAsX(),
                    y?.cldAsY(),
                    tileMode?.asFlag(),
                    if (!allowOverflow) FlagsParam(Flag.NoOverflow()) else null
                )
            )
        )

        fun gravity(gravity: Gravity) = apply {
            this.gravity = gravity
        }

        fun x(x: Int) = apply { this.x = x }

        fun x(x: Any) = apply { this.x = x }

        fun y(y: Int) = apply { this.y = y }

        fun y(y: Any) = apply { this.y = y }

        fun tileMode(tileMode: TileMode) = apply { this.tileMode = tileMode }

        fun allowOverflow(allowOverflow: Boolean) = apply {
            this.allowOverflow = allowOverflow
        }
    }
}

abstract class Layer internal constructor(val values: List<Any>, val params: List<Param> = emptyList()) {
    companion object {
        fun fetch(remoteUrl: String, fetch: (FetchLayer.Builder.() -> Unit)? = null): FetchLayer {
            val builder = FetchLayer.Builder(remoteUrl)
            fetch?.let { builder.fetch() }
            return builder.build()
        }

        fun image(publicId: String, media: (MediaLayer.Builder.() -> Unit)? = null): MediaLayer {
            val builder = MediaLayer.Builder(publicId)
            media?.let { builder.media() }
            return builder.build()
        }

        fun video(publicId: String, media: (MediaLayer.Builder.() -> Unit)? = null): MediaLayer {
            val builder = MediaLayer.Builder(publicId)
            media?.let { builder.media() }
            return builder.resourceType("video").build()
        }

        fun text(
            text: String,
            textLayer: (TextLayer.Builder.() -> Unit)? = null
        ): BaseTextLayer {
            val builder = TextLayer.Builder(text)
            textLayer?.let { builder.it() }
            return builder.build()
        }

        fun subtitles(
            publicId: String,
            subtitles: (SubtitlesLayer.Builder.() -> Unit)? = null
        ): BaseTextLayer {
            val builder = SubtitlesLayer.Builder(publicId)
            subtitles?.let { builder.it() }
            return builder.build()
        }
    }
}

open class LayerAction internal constructor(private val components: LayerComponents) :
    Action {

    companion object {
        internal fun overlay(source: Layer, options: (Builder.() -> Unit)? = null): LayerAction {
            val builder = Builder(source).param("overlay", "l")
            options?.let { builder.options() }
            return builder.build()
        }

        fun underlay(source: Layer, options: (Builder.() -> Unit)? = null): LayerAction {
            val builder = Builder(source).param("underlay", "u")
            options?.let { builder.options() }
            return builder.build()
        }
    }

    override fun toString() = listOfNotNull(
        components.layerParamAction,
        components.transformation,
        components.position
    ).joinToString("/")

    @TransformationDsl
    class Builder(private val source: Layer) : TransformationComponentBuilder, ITransformable<Builder> {
        private var transformation: Transformation? = null
        private var position: Position? = null
        private var blendMode: BlendMode? = null
        private var paramName: String = "layer"
        private var paramKey: String = "l"
        private var extraParams: Collection<Param> = emptyList()
        private var flag: Flag? = null

        override fun add(action: Action) = apply { transformation = (transformation ?: Transformation()).add(action) }

        fun transformation(transformation: Transformation) = apply { this.transformation = transformation }
        fun transformation(transformation: Transformation.Builder.() -> Unit): Builder {
            val builder = Builder()
            builder.transformation()
            transformation(builder.build())
            return this
        }

        fun position(position: Position) = apply { this.position = position }
        fun position(position: (Position.Builder.() -> Unit)? = null): Builder {
            val builder = Position.Builder()
            position?.let { builder.it() }
            position(builder.build())
            return this
        }

        fun blendMode(blendMode: BlendMode) = apply { this.blendMode = blendMode }

        fun param(name: String, key: String) = apply {
            this.paramName = name
            this.paramKey = key
        }

        internal fun extraParams(params: Collection<Param>) = apply { this.extraParams = params }
        internal fun flagKey(flag: Flag) = apply { this.flag = flag }

        override fun build() =
            LayerAction(
                buildLayerComponent(
                    source,
                    transformation,
                    position,
                    blendMode = blendMode,
                    paramName = paramName,
                    paramKey = paramKey,
                    extraParams = extraParams
                )
            )
    }
}

internal fun buildLayerComponent(
    source: Layer,
    transformation: Transformation? = null,
    position: Position? = null,
    blendMode: BlendMode? = null,
    paramName: String,
    paramKey: String,
    extraParams: Collection<Param> = emptyList(),
    flag: Flag? = null
): LayerComponents {
    // start with the layer param itself
    val firstComponentParams =
        ParamsAction((source.params + Param(paramName, paramKey, ParamValue(source.values))).cldToActionMap())

    // layer apply flag + optional flags
    val allParams = mutableListOf<Param>(FlagsParam(Flag.LayerApply()))
    flag?.let { allParams.add(FlagsParam(it)) }
    allParams.addAll(extraParams)

    // optional blend mode
    blendMode?.let { allParams.add(Param("effect", "e", ParamValue(it))) }

    // construct the position component (this needs to include the extra parameters and some of flags):
    val positionAction = (position?.action?.addParams(allParams) ?: ParamsAction(allParams.cldToActionMap()))

    return LayerComponents(firstComponentParams, transformation, positionAction)
}

internal class LayerComponents(
    internal val layerParamAction: Action,
    internal val transformation: Transformation? = null,
    internal val position: Action
)

enum class BlendMode(private val value: String) {
    SCREEN("screen"),
    MULTIPLY("multiply"),
    OVERLAY("overlay");

    override fun toString(): String {
        return value
    }
}

enum class TileMode {
    NONE,
    TILED;

    fun asFlag() = if (this == TILED) FlagsParam(Flag.Tiled()) else null
}