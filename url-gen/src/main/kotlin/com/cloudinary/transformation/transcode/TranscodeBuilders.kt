package com.cloudinary.transformation.transcode

import com.cloudinary.transformation.ParamsAction
import com.cloudinary.transformation.TransformationComponentBuilder
import com.cloudinary.transformation.cldAsDelay
import com.cloudinary.transformation.cldAsVideoSampling

class AnimatedBuilder : TransformationComponentBuilder {
    private var videoSampling: Any? = null
    private var delay: Any? = null

    fun videoSampling(videoSampling: Int) = apply { this.videoSampling = videoSampling }
    fun videoSampling(videoSampling: String) = apply { this.videoSampling = videoSampling }
    fun delay(delay: Int) = apply { this.delay = delay }

    override fun build() = Transcode(
        ParamsAction(
            videoSampling?.cldAsVideoSampling(),
            delay?.cldAsDelay()
        )
    )
}
