package com.cloudinary.transformation.videoedit

import com.cloudinary.transformation.Action

class VideoEdit(action: Action) : Action by action {
    companion object {

        fun trim(options: TrimBuilder.() -> Unit): VideoEdit {
            val builder = TrimBuilder()
            builder.options()
            return builder.build()
        }
    }
}

