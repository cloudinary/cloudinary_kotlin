package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.Transformation.Companion.transformation
import com.cloudinary.transformation.effect.Effect.Companion.grayscale
import com.cloudinary.transformation.effect.Effect.Companion.sepia
import com.cloudinary.transformation.expression.Conditional.Companion.ifCondition
import com.cloudinary.transformation.expression.Expression
import com.cloudinary.transformation.expression.Expression.Companion.aspectRatio
import com.cloudinary.transformation.expression.Expression.Companion.faceCount
import com.cloudinary.transformation.expression.Expression.Companion.height
import com.cloudinary.transformation.expression.Expression.Companion.pageCount
import com.cloudinary.transformation.expression.Expression.Companion.width
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
            "$allOperators/if_end",
            Transformation().conditional(
                ifCondition(
                    Expression("w = 0 && height != 0 || aspectRatio < 0 and pageCount > 0 and faceCount <= 0 and width >= 0"),
                    transformation {
                        effect(
                            grayscale()
                        )
                    })
            )
        )

        val condition = width().eq(0)
            .and(height().ne(0))
            .or(aspectRatio().lt(0))
            .and(pageCount().gt(0))
            .and(faceCount().lte(0))
            .and(width().gte(0))

        val actual =
            transformation {
                conditional(ifCondition(condition,
                    transformation {
                        effect(grayscale())
                    }
                ) {
                    otherwise {
                        effect(sepia())
                    }
                })
            }
        cldAssert(
            "$allOperators/if_else/e_sepia/if_end",
            actual
        )
    }
}