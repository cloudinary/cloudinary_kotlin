package com.cloudinary.transformation.layer

import com.cloudinary.transformation.DEFAULT_COMPONENT_SEPARATOR
import com.cloudinary.transformation.Param
import com.cloudinary.transformation.joinWithValues
import com.cloudinary.transformation.layer.position.BaseLayerPosition
import com.cloudinary.transformation.layer.position.TimelinePosition
import com.cloudinary.transformation.layer.source.LayerSource
import com.cloudinary.transformation.toComponentString

internal fun buildLayerComponent(
    sourceParamKey: String,
    source: LayerSource,
    position: BaseLayerPosition? = null,
    blendMode: BlendMode? = null,
    timelinePosition: TimelinePosition? = null,
    extras: Collection<Param> = emptyList()
): String {
    val sourceComponent = (source.extraComponents() + Param(sourceParamKey, source)).toComponentString()

    // The extra params, alongside position and timeline-position, plus fl_layer_apply - all go in the last component:
    val lastComponent = mutableListOf<Param>().apply {
        addAll(extras)
        add(Param("fl", "layer_apply"))
        position?.let { pos ->
            addAll(pos.asParams())
        }

        timelinePosition?.let { pos ->
            if (pos.startOffset != null) add(Param("so", pos.startOffset))
            if (pos.duration != null) add(Param("so", pos.duration))
        }

        if (blendMode != null) add(Param("e", blendMode))
    }.toComponentString()

    return sourceComponent.joinWithValues(source.transformation, lastComponent, separator = DEFAULT_COMPONENT_SEPARATOR)
}

sealed class BlendMode(private val value: String) {
    class screen : BlendMode("screen")
    class multiply : BlendMode("multiply")
    class overlay : BlendMode("overlay")
    class mask : BlendMode("mask")
    class antiRemoval : BlendMode("anti_removal")

    override fun toString(): String {
        return value
    }
}