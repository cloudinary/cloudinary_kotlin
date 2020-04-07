package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.layer.Layer
import com.cloudinary.transformation.layer.Position
import com.cloudinary.transformation.resize.Resize
import org.junit.Test

class DisplaceTest {
    @Test
    fun testDisplace() {
        val source = Layer.image("radialize")
        val t = Transformation().resize(Resize.scale { width(200) })
        val pos = Position.Builder().gravity(Gravity.direction(Direction.NORTH)).x(25).build()
        cldAssert("l_radialize/e_displace,fl_layer_apply", Displace.displace(source))
        cldAssert("l_radialize/c_scale,w_200/e_displace,fl_layer_apply", Displace.displace(source, t))
        cldAssert(
            "l_radialize/c_scale,w_200/e_displace,fl_layer_apply,g_north,x_25",
            Displace.displace(source, t, pos)
        )
    }
}