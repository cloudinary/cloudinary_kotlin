package com.cloudinary.transformation

import com.cloudinary.transformation.gravity.Gravity

sealed class Region {
    class faces : Region()
    class ocr : Region()

    companion object {
        fun custom(options: Custom.Builder.() -> Unit): Custom {
            val builder = Custom.Builder()
            builder.options()
            return builder.build()
        }
    }
}

class Custom(val width: Any?, val height: Any?, val x: Any?, val y: Any?) : Region() {
    @TransformationDsl
    class Builder {
        private var x: Any? = null
        private var y: Any? = null
        private var width: Any? = null
        private var height: Any? = null
        private var gravity: Gravity? = null

        fun x(x: Any) = apply { this.x = x }
        fun y(y: Any) = apply { this.y = y }
        fun x(x: Int) = apply { this.x = x }
        fun y(y: Int) = apply { this.y = y }

        fun width(width: Any) = apply { this.width = width }
        fun height(height: Any) = apply { this.height = height }
        fun width(width: Int) = apply { this.width = width }
        fun height(height: Int) = apply { this.height = height }

        fun build() = Custom(width, height, x, y)
    }
}