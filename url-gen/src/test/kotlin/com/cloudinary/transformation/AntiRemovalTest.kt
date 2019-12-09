package com.cloudinary.transformation

import com.cloudinary.cldAssertEqualsAsString
import com.cloudinary.transformation.layer.MediaLayerSource
import com.cloudinary.transformation.layer.Position
import com.cloudinary.transformation.resize.Resize
import org.junit.Test

class AntiRemovalTest {
    private val layer = MediaLayerSource("sample")
    private val t = Transformation().resize(Resize.scale { setWidth(100) })
    private val pos = Position.Builder().setGravity(Gravity.direction(Direction.NORTH)).setX(25).build()

    @Test
    fun testAntiRemoval() {
        cldAssertEqualsAsString("l_sample/e_anti_removal,fl_layer_apply", AntiRemoval.Builder(layer).build())
        cldAssertEqualsAsString(
            "l_sample/e_anti_removal:50,fl_layer_apply",
            AntiRemoval.Builder(layer).setLevel(50).build()
        )
        cldAssertEqualsAsString(
            "l_sample/c_scale,w_100/e_anti_removal,fl_layer_apply",
            AntiRemoval.Builder(layer).setTransformation(t).build()
        )
        cldAssertEqualsAsString(
            "l_sample/e_anti_removal,fl_layer_apply,g_north,x_25",
            AntiRemoval.Builder(layer).setPosition(pos).build()
        )
        cldAssertEqualsAsString(
            "l_sample/c_scale,w_100/e_anti_removal,fl_layer_apply,g_north,x_25",
            AntiRemoval.Builder(layer).setTransformation(t).setPosition(pos).build()
        )
    }
}