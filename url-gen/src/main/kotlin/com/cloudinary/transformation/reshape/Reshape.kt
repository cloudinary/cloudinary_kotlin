package com.cloudinary.transformation.reshape

import com.cloudinary.transformation.Action
import com.cloudinary.transformation.layer.source.FetchSource
import com.cloudinary.transformation.layer.source.ImageSource
import com.cloudinary.transformation.layer.source.Source
import com.cloudinary.transformation.layer.source.TextSource
import java.beans.Expression

abstract class Reshape : Action {

    companion object {
        fun shear(skewX: Int, skewY: Int) = shear(skewX as Any, skewY as Any)

        fun shear(skewX: Any, skewY: Any): Shear {
            val builder = Shear.Builder()
            builder.skewX(skewX)
            builder.skewY(skewY)

            return builder.build()
        }

        fun shear(options: Shear.Builder.() -> Unit): Shear {
            val builder = Shear.Builder()
            builder.options()
            return builder.build()
        }

        fun distort(points: List<Int>) = Distort(points)

        fun distortArc(degrees: String) = DistortArc(degrees)
        fun distortArc(degrees: Int) = DistortArc(degrees)
        fun distortArc(degrees: Expression) = DistortArc(degrees)
        fun distortArc(degrees: Float) = DistortArc(degrees)

        private fun cutByImage(source: Source, options: (CutByImage.Builder.() -> Unit)? = null): CutByImage {
            val builder = CutByImage.Builder(source)
            options?.let { builder.it() }
            return builder.build()
        }

        fun cutByImage(source: ImageSource, options: (CutByImage.Builder.() -> Unit)? = null) =
            cutByImage(source as Source, options)

        fun cutByImage(source: TextSource, options: (CutByImage.Builder.() -> Unit)? = null) =
            cutByImage(source as Source, options)

        fun cutByImage(source: FetchSource, options: (CutByImage.Builder.() -> Unit)? = null) =
            cutByImage(source as Source, options)

        fun trim(options: (Trim.Builder.() -> Unit)? = null): Trim {
            val builder = Trim.Builder()
            options?.let { builder.it() }
            return builder.build()
        }
    }
}
