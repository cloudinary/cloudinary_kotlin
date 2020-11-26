package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.layer.position.LayerPosition
import com.cloudinary.transformation.layer.position.TileMode
import com.cloudinary.transformation.layer.source.LayerSource
import com.cloudinary.transformation.resize.Resize
import com.cloudinary.transformation.resize.ResizeMode
import org.junit.Test

class CutterTest {
    @Test
    fun testCutter() {
        var expected = "l_hexagon_sample/c_scale,fl_relative,h_1.0,w_1.0/fl_cutter,fl_layer_apply"

        val scale = Resize.scale {
            width(1f)
            height(1f)
            resizeMode(ResizeMode.relative())
        }

        val source = LayerSource.image("hexagon_sample") {
            transformation {
                resize(scale)
            }
        }

        var actual = Transformation().cutter(source)

        cldAssert(expected, actual)

        val position = LayerPosition.Builder().x(50).tileMode(TileMode.TILED).build()
        val crop = Resize.crop {
            width(100)
            height(50)
        }

        actual = Transformation().cutter(LayerSource.image("hexagon_sample") {
            transformation {
                resize(crop)
            }
        }) {
            position(position)
        }

        expected = "l_hexagon_sample/c_crop,h_50,w_100/fl_cutter,fl_layer_apply,fl_tiled,x_50"
        cldAssert(expected, actual)
    }
}
