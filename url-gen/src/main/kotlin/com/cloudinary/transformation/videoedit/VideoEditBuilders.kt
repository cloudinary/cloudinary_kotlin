package com.cloudinary.transformation.videoedit

import com.cloudinary.transformation.*

class TrimBuilder : TransformationComponentBuilder {
    private var startOffset: Any? = null
    private var endOffset: Any? = null
    private var duration: Any? = null

    // TODO what do we accept here? strings with % and replace? strings with `p`? the word 'auto` as string for start offset?
    fun startOffset(offset: Float) = apply { this.startOffset = offset }
    fun startOffset(offset: Int) = apply { this.startOffset = offset }
    fun startOffset(offset: String) = apply { this.startOffset = offset }
    fun endOffset(offset: Float) = apply { this.endOffset = offset }
    fun endOffset(offset: Int) = apply { this.endOffset = offset }
    fun endOffset(offset: String) = apply { this.endOffset = offset }
    fun duration(duration: Float) = apply { this.duration = duration }
    fun duration(duration: Int) = apply { this.duration = duration }
    fun duration(duration: String) = apply { this.duration = duration }

    // TODO validations?
    override fun build(): VideoEdit {
        require(listOfNotNull(startOffset, endOffset, duration).size in 1..2)

        return VideoEdit(
            ParamsAction(
                startOffset?.cldAsStartOffset(),
                endOffset?.cldAsEndOffset(),
                duration?.cldAsDuration()
            )
        )
    }
}