package com.cloudinary.transformation.layer

import com.cloudinary.cldAssert
import com.cloudinary.transformation.Color
import com.cloudinary.transformation.gravity.Gravity
import com.cloudinary.transformation.gravity.GravityObject
import com.cloudinary.transformation.layer.position.LayerPosition
import com.cloudinary.transformation.layer.source.LayerSource
import com.cloudinary.transformation.resize.Resize
import org.junit.Test

class UnderlayTest {
    private val position = LayerPosition.Builder().gravity(Gravity.objects(GravityObject.CAT)).x(20).build()

    @Test
    fun testUnderlay() {
        var underlay = Underlay.image("sample") {
            blendMode(BlendMode.multiply())
        }

        cldAssert("u_sample/e_multiply,fl_layer_apply", underlay)

        underlay = Underlay.image("sample") {
            position(position)
            blendMode(BlendMode.multiply())
            transformation { resize(Resize.scale { width(250) }) }
        }

        cldAssert("u_sample/c_scale,w_250/e_multiply,fl_layer_apply,g_cat,x_20", underlay)

        underlay = Underlay.image("sample") {
            position(position)
            blendMode(BlendMode.multiply())
            transformation { resize(Resize.scale { width(250) }) }
        }

        cldAssert("u_sample/c_scale,w_250/e_multiply,fl_layer_apply,g_cat,x_20", underlay)

        underlay = Underlay.image("sample") {
            position(position)
            transformation { resize(Resize.scale { width(250) }) }
        }

        cldAssert("u_sample/c_scale,w_250/fl_layer_apply,g_cat,x_20", underlay)

        underlay = Underlay.fetch("https://res.cloudinary.com/demo/image/upload/sample") {
            position(position)
        }

        cldAssert(
            "u_fetch:aHR0cHM6Ly9yZXMuY2xvdWRpbmFyeS5jb20vZGVtby9pbWFnZS91cGxvYWQvc2FtcGxl/fl_layer_apply,g_cat,x_20",
            underlay
        )

        underlay = Underlay.text(LayerSource.text("hello world!") {
            style {
                fontFamily("Arial")
                fontSize(17)
            }
            textColor(Color.RED)
            backgroundColor(Color.GREEN)
        }) {
            position(position)
            transformation { resize(Resize.scale { width(250) }) }
        }

        cldAssert("b_green,co_red,u_text:Arial_17:hello world!/c_scale,w_250/fl_layer_apply,g_cat,x_20", underlay)
    }
}