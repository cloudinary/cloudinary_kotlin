package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.Expression.Companion.aspectRatio
import com.cloudinary.transformation.Expression.Companion.faceCount
import com.cloudinary.transformation.Expression.Companion.height
import com.cloudinary.transformation.Expression.Companion.pageCount
import com.cloudinary.transformation.Expression.Companion.width
import com.cloudinary.transformation.effect.Effect
import org.junit.Test

class ConditionsTest {
    @Test
    fun testIf() {
        val allOperators = "if_" +
                "w_eq_0_and" +
                "_h_ne_0_or" +
                "_ar_lt_0_and" +
                "_pc_gt_0_and" +
                "_fc_lte_0_and" +
                "_w_gte_0" +
                "/e_grayscale"

        cldAssert(
            allOperators,
            Transformation().ifCondition(Expression("w = 0 && height != 0 || aspectRatio < 0 and pageCount > 0 and faceCount <= 0 and width >= 0"))
                .effect(Effect.grayscale())
        )

        cldAssert(
            allOperators,
            Transformation().ifCondition(Expression("w = 0 && height != 0 || aspectRatio < 0 and pageCount > 0 and faceCount <= 0 and width >= 0"))
                .effect(Effect.grayscale())
        )

        cldAssert(
            "$allOperators/if_else/e_sepia:30",
            Transformation().ifCondition(
                width().eq(0)
                    .and(height().ne(0))
                    .or(aspectRatio().lt(0))
                    .and(pageCount().gt(0))
                    .and(faceCount().lte(0))
                    .and(width().gte(0))
            )
                .effect(Effect.grayscale())
                .ifElse()
                .effect(Effect.sepia { level(30) })
        )
    }
}