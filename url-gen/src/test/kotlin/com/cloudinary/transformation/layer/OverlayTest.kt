package com.cloudinary.transformation.layer

import com.cloudinary.cldAssert
import com.cloudinary.transformation.Color
import com.cloudinary.transformation.gravity.FocusOn
import com.cloudinary.transformation.gravity.Gravity
import com.cloudinary.transformation.gravity.Gravity.Companion.north
import com.cloudinary.transformation.layer.BlendMode.multiply
import com.cloudinary.transformation.layer.position.LayerPosition
import com.cloudinary.transformation.layer.position.VideoPosition
import com.cloudinary.transformation.layer.source.LayerSource.Companion.fetch
import com.cloudinary.transformation.layer.source.LayerSource.Companion.image
import com.cloudinary.transformation.resize.Resize.Companion.scale
import org.junit.Test

class OverlayTest {
    private val position = LayerPosition.Builder().gravity(Gravity.focusOn(FocusOn.CAT)).x(20).build()
    private val videoPosition = VideoPosition.Builder().gravity(north()).x(20).build()

    @Test
    fun testOverlay() {
        var overlay = Overlay.source(image("sample")) {
            blendMode(multiply())
        }

        cldAssert("l_sample/e_multiply,fl_layer_apply", overlay)

        overlay = Overlay.source(image("sample") {
            transformation { resize(scale { width(250) }) }
        }) {
            position(position)
            blendMode(multiply())
        }

        cldAssert("l_sample/c_scale,w_250/e_multiply,fl_layer_apply,g_cat,x_20", overlay)

        overlay = Overlay.source(fetch("https://res.cloudinary.com/demo/image/upload/sample")) {
            position(position)
        }

        cldAssert(
            "l_fetch:aHR0cHM6Ly9yZXMuY2xvdWRpbmFyeS5jb20vZGVtby9pbWFnZS91cGxvYWQvc2FtcGxl/fl_layer_apply,g_cat,x_20",
            overlay
        )

        overlay = Overlay.text {
            source("hello world!") {
                style("Arial", 17)
                textColor(Color.RED)
                backgroundColor(Color.GREEN)
                transformation { resize(scale { width(250) }) }
            }
            position(position)
        }

        cldAssert("b_green,co_red,l_text:Arial_17:hello world!/c_scale,w_250/fl_layer_apply,g_cat,x_20", overlay)
    }
}