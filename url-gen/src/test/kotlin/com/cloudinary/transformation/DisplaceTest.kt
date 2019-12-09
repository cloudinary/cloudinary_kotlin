package com.cloudinary.transformation

import com.cloudinary.cldAssertEqualsAsString
import com.cloudinary.transformation.layer.LayerSource
import com.cloudinary.transformation.layer.Position
import com.cloudinary.transformation.resize.Resize
import org.junit.Test

class DisplaceTest {
    @Test
    fun testDisplace() {
        val source = LayerSource.media("radialize")
        val t = Transformation().resize(Resize.scale { setWidth(200) })
        val pos = Position.Builder().setGravity(Gravity.direction(Direction.NORTH)).setX(25).build()
        cldAssertEqualsAsString("l_radialize/e_displace,fl_layer_apply", Displace.displace(source))
        cldAssertEqualsAsString("l_radialize/c_scale,w_200/e_displace,fl_layer_apply", Displace.displace(source, t))
        cldAssertEqualsAsString(
            "l_radialize/c_scale,w_200/e_displace,fl_layer_apply,g_north,x_25",
            Displace.displace(source, t, pos)
        )
    }
}