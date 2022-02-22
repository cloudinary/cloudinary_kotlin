package com.cloudinary.transformation.layer

import com.cloudinary.transformation.*
import com.cloudinary.transformation.DEFAULT_COMPONENT_SEPARATOR
import com.cloudinary.transformation.joinWithValues
import com.cloudinary.transformation.layer.position.BaseLayerPosition
import com.cloudinary.transformation.layer.position.Timeline
import com.cloudinary.transformation.layer.source.Source
import com.cloudinary.transformation.toComponentString

internal fun buildLayerComponent(
    sourceParamKey: String,
    source: Source,
    position: BaseLayerPosition? = null,
    blendMode: BlendMode? = null,
    timeline: Timeline? = null,
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

        timeline?.let { pos ->
            if (pos.startOffset != null) add(Param("so", pos.startOffset))
            if (pos.duration != null) add(Param("so", pos.duration))
            if (pos.endOffset != null) add(Param("eo", pos.endOffset))
        }

        if (blendMode != null) add(Param("e", blendMode))
    }.toComponentString()

    return sourceComponent.joinWithValues(source.transformation, lastComponent, separator = DEFAULT_COMPONENT_SEPARATOR)
}
