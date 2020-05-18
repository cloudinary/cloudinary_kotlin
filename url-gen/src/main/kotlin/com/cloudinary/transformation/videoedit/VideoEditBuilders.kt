package com.cloudinary.transformation.videoedit

import com.cloudinary.transformation.*

class TrimBuilder : TransformationComponentBuilder {
    private var startOffset: Any? = null
    private var endOffset: Any? = null
    private var duration: Any? = null

    // TODO what do we accept here? strings with % and replace? strings with `p`? the word 'auto` as string for start offset?
    fun startOffset(offset: Double) = apply { this.startOffset = offset }
    fun startOffset(offset: String) = apply { this.startOffset = offset }
    fun endOffset(offset: Double) = apply { this.endOffset = offset }
    fun endOffset(offset: String) = apply { this.endOffset = offset }
    fun duration(duration: Double) = apply { this.duration = duration }
    fun duration(duration: String) = apply { this.duration = duration }

    // TODO validations? at least one is necessary but three is too much
    override fun build() = VideoEdit(
        CParamsAction(
            startOffset?.cldAsStartOffset(),
            endOffset?.cldAsEndOffset(),
            duration?.cldAsDuration()
        )
    )
}