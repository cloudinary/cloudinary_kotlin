package com.cloudinary.transformation.layer

import com.cloudinary.transformation.*
import com.cloudinary.transformation.Transformation.Builder

open class LayerAction internal constructor(private val components: LayerComponents) :
    Action {

    override fun toString() = listOfNotNull(
        components.layerParamAction,
        components.transformation,
        components.position
    ).joinToString(ACTIONS_SEPARATOR)

    class Builder(private val source: Source) : TransformationComponentBuilder {
        private var transformation: Transformation? = null
        private var position: Position? = null
        private var blendMode: BlendMode? = null
        private var paramName: String = "layer"
        private var paramKey: String = "l"
        private var extraParams: Collection<Param> = emptyList()
        private var flag: Flag? = null

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

        internal fun param(name: String, key: String) = apply {
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
                    blendMode,
                    paramName,
                    paramKey,
                    extraParams,
                    flag
                )
            )
    }
}

internal fun buildLayerComponent(
    source: Source,
    transformation: Transformation?,
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
    val allParams = mutableListOf<Param>(FlagsParam(Flag.layerApply()))
    flag?.let { allParams.add(FlagsParam(it)) }
    allParams.addAll(extraParams)

    // optional blend mode
    blendMode?.let { allParams.add(blendMode.cldAsEffect()) }

    // construct the position component (this needs to include the extra parameters and some of flags):
    val positionAction = (position?.action?.addParams(allParams) ?: ParamsAction(allParams.cldToActionMap()))

    return LayerComponents(firstComponentParams, transformation, positionAction)
}

internal class LayerComponents(
    internal val layerParamAction: Action,
    internal val transformation: Transformation? = null,
    internal val position: Action
)

class BlendMode(value: String) : ParamValue(value) {
    companion object {
        fun screen() = BlendMode("screen")
        fun multiply() = BlendMode("multiply")
        fun overlay() = BlendMode("overlay")
    }
}