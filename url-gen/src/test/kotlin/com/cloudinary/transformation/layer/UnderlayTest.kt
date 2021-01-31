package com.cloudinary.transformation.layer

import com.cloudinary.cldAssert
import com.cloudinary.transformation.Color
import com.cloudinary.transformation.gravity.FocusOn
import com.cloudinary.transformation.gravity.Gravity
import com.cloudinary.transformation.layer.position.Position
import com.cloudinary.transformation.layer.source.Source.Companion.fetch
import com.cloudinary.transformation.layer.source.Source.Companion.image
import com.cloudinary.transformation.resize.Resize
import org.junit.Test

class UnderlayTest {
    private val position = Position.Builder().gravity(Gravity.focusOn(FocusOn.cat())).offsetX(20).build()

    @Test
    fun testUnderlay() {
        var underlay = Underlay.source(image("sample")) {
            blendMode(BlendMode.multiply())
        }

        cldAssert("u_sample/e_multiply,fl_layer_apply", underlay)

        underlay = Underlay.image {
            source("sample") {
                transformation { resize(Resize.scale { width(250) }) }
            }
            position(position)
            blendMode(BlendMode.multiply())
        }

        cldAssert("u_sample/c_scale,w_250/e_multiply,fl_layer_apply,g_cat,x_20", underlay)

        underlay = Underlay.source(fetch("https://res.cloudinary.com/demo/image/upload/sample")) {
            position(position)
        }

        cldAssert(
            "u_fetch:aHR0cHM6Ly9yZXMuY2xvdWRpbmFyeS5jb20vZGVtby9pbWFnZS91cGxvYWQvc2FtcGxl/fl_layer_apply,g_cat,x_20",
            underlay
        )

        underlay = Underlay.text {
            source("hello world!") {
                style("Arial", 17)
                textColor(Color.RED)
                backgroundColor(Color.GREEN)
                transformation { resize(Resize.scale { width(250) }) }
            }
            position(position)
        }

        cldAssert("b_green,co_red,u_text:Arial_17:hello world!/c_scale,w_250/fl_layer_apply,g_cat,x_20", underlay)
    }
}