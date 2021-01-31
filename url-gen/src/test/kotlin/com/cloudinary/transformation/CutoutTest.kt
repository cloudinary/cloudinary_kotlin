package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.gravity.Gravity
import com.cloudinary.transformation.layer.position.Position
import com.cloudinary.transformation.layer.source.ImageSource
import com.cloudinary.transformation.resize.Resize
import org.junit.Test

class CutoutTest {
    @Test
    fun testCutout() {
        val pos = Position.Builder().gravity(Gravity.north()).offsetX(100).build()
        val tr = Transformation().resize(Resize.scale {
            width(200)
            height(200)
        }).roundCorners(RoundCorners.max())

        cldAssert(
            "l_ring2/e_cut_out,fl_layer_apply", Transformation().cutout(ImageSource("ring2"))
        )

        cldAssert(
            "l_ring2/c_scale,h_200,w_200/r_max/e_cut_out,fl_layer_apply",
            Transformation().cutout(ImageSource("ring2", transformation = tr))
        )

        cldAssert(
            "l_ring2/c_scale,h_200,w_200/r_max/e_cut_out,fl_layer_apply,g_north,x_100",
            Transformation().cutout(ImageSource("ring2", transformation = tr)) {
                position(pos)
            }
        )
    }
}