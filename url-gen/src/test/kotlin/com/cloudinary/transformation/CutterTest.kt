package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.layer.Layer
import com.cloudinary.transformation.layer.Position
import com.cloudinary.transformation.layer.TileMode
import com.cloudinary.transformation.resize.Resize
import com.cloudinary.transformation.resize.ResizeMode
import org.junit.Test

class CutterTest {
    @Test
    fun testCutter() {
        var expected = "l_hexagon_sample/c_scale,fl_relative,h_1.0,w_1.0/fl_cutter.layer_apply"

        val layer = Layer.image("hexagon_sample")
        val scale = Resize.scale {
            width(1f)
            height(1f)
            mode(ResizeMode.RELATIVE)
        }
        var actualFromBuilder = Cutter.Builder(layer)
            .transformation(
                Transformation().resize(scale)

            ).build()

        var actualFromDsl = Transformation().cutter(layer) {
            transformation(Transformation().resize(scale))
        }

        cldAssert(expected, actualFromBuilder)
        cldAssert(expected, actualFromDsl)

        val position = Position.Builder().x(50).tileMode(TileMode.TILED).build()
        val crop = Resize.crop {
            width(100)
            height(50)
        }
        actualFromBuilder = Cutter.Builder(layer)
            .transformation(Transformation().resize(crop))
            .position(position)
            .build()

        actualFromDsl = Transformation().cutter(layer) {
            transformation(Transformation().resize(crop))
            position(position)
        }

        expected = "l_hexagon_sample/c_crop,h_50,w_100/fl_cutter.layer_apply.tiled,x_50"
        cldAssert(expected, actualFromBuilder)
        cldAssert(expected, actualFromDsl)
    }
}
