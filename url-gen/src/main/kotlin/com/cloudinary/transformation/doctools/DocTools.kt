package com.cloudinary.transformation.doctools

import com.cloudinary.transformation.Action

abstract class DocTools : Action {
    companion object {
        fun getPage(options: (GetPage.Builder.() -> Unit)): GetPage {
            val builder = GetPage.Builder()
            builder.options()
            return builder.build()
        }
    }
}