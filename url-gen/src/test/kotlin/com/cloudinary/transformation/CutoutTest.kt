package com.cloudinary.transformation

import com.cloudinary.cldAssertEqualsAsString
import com.cloudinary.transformation.layer.MediaLayerSource
import com.cloudinary.transformation.layer.Position
import com.cloudinary.transformation.resize.Resize
import org.junit.Test

class CutoutTest {
    @Test
    fun testCutout() {
        val pos = Position.Builder().gravity(Gravity.direction(Direction.NORTH)).x(100).build()
        val tr = Transformation().resize(Resize.scale { width(200).height(200) })
            .cornersRadius { max() }

        cldAssertEqualsAsString(
            "l_ring2/e_cut_out,fl_layer_apply", Transformation().cutout(MediaLayerSource("ring2"))
        )

        cldAssertEqualsAsString(
            "l_ring2/c_scale,h_200,w_200/r_max/e_cut_out,fl_layer_apply",
            Transformation().cutout(MediaLayerSource("ring2")) { transformation(tr) }
        )

        cldAssertEqualsAsString(
            "l_ring2/c_scale,h_200,w_200/r_max/e_cut_out,fl_layer_apply,g_north,x_100",
            Transformation().cutout(MediaLayerSource("ring2")) {
                transformation(tr)
                position(pos)
            }
        )
    }
}