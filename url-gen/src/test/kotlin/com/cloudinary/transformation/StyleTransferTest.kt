package com.cloudinary.transformation

import com.cloudinary.cldAssertEqualsAsString
import com.cloudinary.transformation.effect.Sepia
import com.cloudinary.transformation.layer.LayerSource
import com.cloudinary.transformation.resize.Scale
import org.junit.Test

class StyleTransferTest {
    @Test
    fun testStyleTransfer() {
        val lighthouse = LayerSource.media("lighthouse")
        cldAssertEqualsAsString(
            "l_lighthouse/e_style_transfer,fl_layer_apply",
            Transformation().styleTransfer(lighthouse)
        )
        cldAssertEqualsAsString(
            "l_lighthouse/e_style_transfer:34:preserve_color,fl_layer_apply",
            Transformation().styleTransfer(lighthouse) { this.setPreserveColor(true).setStrength(34) }
        )

        val t = Transformation().resize(Scale.Builder().setWidth(200).build()).effect(Sepia.Builder().build())
        cldAssertEqualsAsString(
            "l_lighthouse/c_scale,w_200/e_sepia/e_style_transfer:34,fl_layer_apply",
            Transformation().styleTransfer(lighthouse) { setStrength(34).setTransformation(t) })

    }
}