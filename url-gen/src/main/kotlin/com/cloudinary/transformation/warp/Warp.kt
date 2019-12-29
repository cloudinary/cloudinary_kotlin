package com.cloudinary.transformation.warp

import com.cloudinary.transformation.Param
import com.cloudinary.transformation.ParamValue
import com.cloudinary.transformation.ParamsAction
import com.cloudinary.util.cldRanged

open class Warp private constructor(params: Map<String, Param>) : ParamsAction<Warp>(params) {
    protected constructor(param: Param) : this(mapOf(Pair(param.key, param)))

    override fun create(params: Map<String, Param>) =
        Warp(params)

    companion object {
        fun shear(xDegrees: Int, yDegrees: Int) = shear(xDegrees as Any, yDegrees)

        fun shear(xDegrees: Any, yDegrees: Any) = Warp(
            Param(
                "shear",
                "e",
                ParamValue(
                    listOf(
                        "shear",
                        xDegrees.cldRanged(-359, 359),
                        yDegrees.cldRanged(-359, 359)
                    )
                )
            )
        )

        fun distort(
            topLeftX: Int,
            topLeftY: Int,
            topRightX: Int,
            topRightY: Int,
            bottomRightX: Int,
            bottomRightY: Int,
            bottomLeftX: Int,
            bottomLeftY: Int
        ) = distort(
            topLeftX as Any,
            topLeftY,
            topRightX,
            topRightY,
            bottomRightX,
            bottomRightY,
            bottomLeftX,
            bottomLeftY
        )

        fun distort(
            topLeftX: Any,
            topLeftY: Any,
            topRightX: Any,
            topRightY: Any,
            bottomRightX: Any,
            bottomRightY: Any,
            bottomLeftX: Any,
            bottomLeftY: Any
        ) = distortParam(
            listOf(
                topLeftX,
                topLeftY,
                topRightX,
                topRightY,
                bottomRightX,
                bottomRightY,
                bottomLeftX,
                bottomLeftY
            )
        )


        fun distort(degrees: Int) = distortParam(
            listOf(
                "arc",
                degrees.cldRanged(-360, 360)
            )
        )

        private fun distortParam(value: List<Any>) = Warp(
            Param(
                "distort",
                "e",
                ParamValue(listOf("distort") + value)
            )
        )
    }
}

