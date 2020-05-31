package com.cloudinary.android.preprocess.video

/**
 * Video transcoding parameters
 */
data class Parameters (
    var requestId: String,
    var width: Int,
    var height: Int,
    var targetVideoBitrateKbps: Int,
    var keyFramesInterval: Int,
    var frameRate: Int,
    var targetAudioBitrateKbps: Int
)