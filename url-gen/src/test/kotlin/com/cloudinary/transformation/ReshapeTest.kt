package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.layer.position.LayerPosition
import com.cloudinary.transformation.layer.source.LayerSource
import com.cloudinary.transformation.reshape.Reshape
import com.cloudinary.transformation.resize.Resize
import org.junit.Test

class ReshapeTest {
    @Test
    fun testShear() {
        cldAssert("e_shear:20:0", Reshape.shear {
            skewX(20)
            skewY(0)
        })

        cldAssert("e_shear:20:0", Reshape.shear(20, 0))
    }

    @Test
    fun testDistort() {
        cldAssert(
            "e_distort:5:34:70:10:70:75:5:55", Reshape.distort(
                listOf(5, 34, 70, 10, 70, 75, 5, 55)
            )
        )
    }

    @Test
    fun testDistortArc() {
        cldAssert("e_distort:arc:180", Reshape.distortArc(180))
    }

    @Test
    fun testCutByImage() {
        var expected = "l_hexagon_sample/c_scale,fl_relative,h_1.0,w_1.0/fl_cutter,fl_layer_apply"

        val scale = Resize.scale {
            width(1f)
            height(1f)
            relative()
        }

        val source = LayerSource.image("hexagon_sample") {
            transformation {
                resize(scale)
            }
        }

        var actual = Reshape.cutByImage(source)

        cldAssert(expected, actual)

        val position = LayerPosition.Builder().x(50).tiled().build()
        val crop = Resize.crop {
            width(100)
            height(50)
        }

        actual = Reshape.cutByImage(LayerSource.image("hexagon_sample") {
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