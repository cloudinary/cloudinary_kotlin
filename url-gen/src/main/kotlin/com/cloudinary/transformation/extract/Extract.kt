package com.cloudinary.transformation.extract

import com.cloudinary.transformation.Action

abstract class Extract : Action {
    companion object {
        fun getFrame(options: GetFrame.Builder.() -> Unit): GetFrame {
            val builder = GetFrame.Builder()
            builder.options()
            return builder.build()
        }

        fun getPage(options: (GetPage.Builder.() -> Unit)): GetPage {
            val builder = GetPage.Builder()
            builder.options()
            return builder.build()
        }
    }
}

