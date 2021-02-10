package com.cloudinary.transformation

import com.cloudinary.transformation.transcode.Transcode
import com.cloudinary.transformation.videoedit.VideoEdit

interface ITransformableVideo<T> : IBaseTransformable<T> {

    // transcode
    fun transcode(transcode: Transcode) = add(transcode)

    // video
    fun videoEdit(videoEdit: VideoEdit) = add(videoEdit)
}