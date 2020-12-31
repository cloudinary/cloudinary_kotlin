package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.gravity.Gravity
import com.cloudinary.transformation.layer.position.LayerPosition
import com.cloudinary.transformation.layer.source.ImageSource
import com.cloudinary.transformation.resize.Resize
import org.junit.Test

class AntiRemovalTest {
    private val t = Transformation().resize(Resize.scale { width(100) })
    private val layer = ImageSource("sample", transformation = t)
    private val pos = LayerPosition.Builder().gravity(Gravity.north()).x(25).build()

    @Test
    fun testAntiRemoval() {
        cldAssert("l_sample/e_anti_removal,fl_layer_apply", AntiRemoval.Builder(ImageSource("sample")).build())
        cldAssert(
            "l_sample/c_scale,w_100/e_anti_removal:50,fl_layer_apply",
            AntiRemoval.Builder(layer).level(50).build()
        )
        cldAssert(
            "l_sample/c_scale,w_100/e_anti_removal,fl_layer_apply",
            AntiRemoval.Builder(layer).build()
        )
        cldAssert(
            "l_sample/c_scale,w_100/e_anti_removal,fl_layer_apply,g_north,x_25",
            AntiRemoval.Builder(layer).position(pos).build()
        )
        cldAssert(
            "l_sample/c_scale,w_100/e_anti_removal,fl_layer_apply,g_north,x_25",
            AntiRemoval.Builder(layer).position(pos).build()
        )
    }
}