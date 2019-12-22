package com.cloudinary.transformation

import com.cloudinary.cldAssertEqualsAsString
import com.cloudinary.transformation.layer.MediaLayerSource
import com.cloudinary.transformation.layer.Position
import com.cloudinary.transformation.resize.Resize
import org.junit.Test

class AntiRemovalTest {
    private val layer = MediaLayerSource("sample")
    private val t = Transformation().resize(Resize.scale { width(100) })
    private val pos = Position.Builder().gravity(Gravity.direction(Direction.NORTH)).x(25).build()

    @Test
    fun testAntiRemoval() {
        cldAssertEqualsAsString("l_sample/e_anti_removal,fl_layer_apply", AntiRemoval.Builder(layer).build())
        cldAssertEqualsAsString(
            "l_sample/e_anti_removal:50,fl_layer_apply",
            AntiRemoval.Builder(layer).level(50).build()
        )
        cldAssertEqualsAsString(
            "l_sample/c_scale,w_100/e_anti_removal,fl_layer_apply",
            AntiRemoval.Builder(layer).transformation(t).build()
        )
        cldAssertEqualsAsString(
            "l_sample/e_anti_removal,fl_layer_apply,g_north,x_25",
            AntiRemoval.Builder(layer).position(pos).build()
        )
        cldAssertEqualsAsString(
            "l_sample/c_scale,w_100/e_anti_removal,fl_layer_apply,g_north,x_25",
            AntiRemoval.Builder(layer).transformation(t).position(pos).build()
        )
    }
}