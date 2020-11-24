package com.cloudinary.transformation.layer

import com.cloudinary.cldAssert
import com.cloudinary.transformation.Color
import com.cloudinary.transformation.gravity.Gravity
import com.cloudinary.transformation.gravity.Gravity.Companion.north
import com.cloudinary.transformation.gravity.GravityObject
import com.cloudinary.transformation.layer.BlendMode.multiply
import com.cloudinary.transformation.layer.position.LayerPosition
import com.cloudinary.transformation.layer.position.VideoPosition
import com.cloudinary.transformation.layer.source.LayerSource
import com.cloudinary.transformation.resize.Resize.Companion.scale
import org.junit.Test

class OverlayTest {
    private val position = LayerPosition.Builder().gravity(Gravity.objects(GravityObject.CAT)).x(20).build()
    private val videoPosition = VideoPosition.Builder().gravity(north()).x(20).build()

    @Test
    fun testOnImage() {
        var overlay = Overlay.imageOnImage("sample") {
            blendMode(multiply())
        }

        cldAssert("l_sample/e_multiply,fl_layer_apply", overlay)

        overlay = Overlay.imageOnImage("sample") {
            position(position)
            blendMode(multiply())
            transformation { resize(scale { width(250) }) }
        }

        cldAssert("l_sample/c_scale,w_250/e_multiply,fl_layer_apply,g_cat,x_20", overlay)

        overlay = Overlay.imageOnImage("sample") {
            position(position)
            blendMode(multiply())
            transformation { resize(scale { width(250) }) }
        }

        cldAssert("l_sample/c_scale,w_250/e_multiply,fl_layer_apply,g_cat,x_20", overlay)

        overlay = Overlay.imageOnImage("sample") {
            position(position)
            transformation { resize(scale { width(250) }) }
        }

        cldAssert("l_sample/c_scale,w_250/fl_layer_apply,g_cat,x_20", overlay)

        overlay = Overlay.fetchOnImage("https://res.cloudinary.com/demo/image/upload/sample") {
            position(position)
        }

        cldAssert(
            "l_fetch:aHR0cHM6Ly9yZXMuY2xvdWRpbmFyeS5jb20vZGVtby9pbWFnZS91cGxvYWQvc2FtcGxl/fl_layer_apply,g_cat,x_20",
            overlay
        )

        overlay = Overlay.textOnImage(LayerSource.text("hello world!") {
            style {
                fontFamily("Arial")
                fontSize(17)
            }
            textColor(Color.RED)
            backgroundColor(Color.GREEN)
        }) {
            position(position)
            transformation { resize(scale { width(250) }) }
        }

        cldAssert("b_green,co_red,l_text:Arial_17:hello world!/c_scale,w_250/fl_layer_apply,g_cat,x_20", overlay)
    }

    @Test
    fun testOnVideo() {
        var overlay = Overlay.imageOnVideo("sample") {
            position(videoPosition)
        }

        cldAssert("l_sample/fl_layer_apply,g_north,x_20", overlay)

        overlay = Overlay.fetchOnVideo("https://res.cloudinary.com/demo/image/upload/sample") {
            position(videoPosition)
        }

        cldAssert(
            "l_fetch:aHR0cHM6Ly9yZXMuY2xvdWRpbmFyeS5jb20vZGVtby9pbWFnZS91cGxvYWQvc2FtcGxl/fl_layer_apply,g_north,x_20",
            overlay
        )

        overlay = Overlay.imageOnVideo("sample") {
            position(videoPosition)
            transformation {
                resize(scale { width(250) })
            }
        }

        cldAssert("l_sample/c_scale,w_250/fl_layer_apply,g_north,x_20", overlay)

        overlay = Overlay.textOnVideo(LayerSource.text("hello world!") {
            style {
                fontFamily("Arial")
                fontSize(17)
            }
            textColor(Color.RED)
            backgroundColor(Color.GREEN)
        }) {
            position(videoPosition)
            transformation { resize(scale { width(250) }) }
        }

        cldAssert("b_green,co_red,l_text:Arial_17:hello world!/c_scale,w_250/fl_layer_apply,g_north,x_20", overlay)
    }

    @Test
    fun testVideoOnVideo() {
        var overlay = Overlay.video("dog") {
            position(videoPosition)
        }

        cldAssert("l_video:dog/fl_layer_apply,g_north,x_20", overlay)

        overlay = Overlay.video("dog") {
            position(videoPosition)
            transformation { resize(scale { width(250) }) }
        }

        cldAssert("l_video:dog/c_scale,w_250/fl_layer_apply,g_north,x_20", overlay)

        overlay = Overlay.subtitles(LayerSource.subtitles("sub_file.srt") {
            style {
                fontFamily("Arial")
                fontSize(17)
            }
            textColor(Color.RED)
            backgroundColor(Color.GREEN)
        }) {
            position(videoPosition)
            transformation { resize(scale { width(250) }) }
        }

        cldAssert("b_green,co_red,l_subtitles:Arial_17:sub_file.srt/c_scale,w_250/fl_layer_apply,g_north,x_20", overlay)
    }
}