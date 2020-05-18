package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.effect.Effect
import com.cloudinary.transformation.layer.Layer
import com.cloudinary.transformation.resize.Resize
import org.junit.Test

class StyleTransferTest {
    @Test
    fun testStyleTransfer() {
        val lighthouse = Layer.image("lighthouse")
        cldAssert(
            "l_lighthouse/e_style_transfer,fl_layer_apply",
            Transformation().styleTransfer(lighthouse)
        )
        cldAssert(
            "l_lighthouse/e_style_transfer:34:preserve_color,fl_layer_apply",
            Transformation().styleTransfer(lighthouse) {
                this.preserveColor(true).strength(34)
            }
        )

        val t = Transformation().resize(Resize.scale(200)).effect(Effect.sepia())
        cldAssert(
            "l_lighthouse/c_scale,w_200/e_sepia/e_style_transfer:34,fl_layer_apply",
            Transformation().styleTransfer(lighthouse) { strength(34).transformation(t) })

    }
}