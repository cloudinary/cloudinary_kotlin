package com.cloudinary.transformation

import com.cloudinary.cldAssertEqualsAsString
import com.cloudinary.transformation.Direction.NORTH
import com.cloudinary.transformation.Gravity.Companion.direction
import com.cloudinary.transformation.layer.*
import com.cloudinary.transformation.resize.Resize
import org.junit.Test

class LayerTest {
    private val layer = MediaLayerSource("sample")
    private val t = Transformation().resize(Resize.scale { setWidth(100) })
    private val pos = Position.Builder().setGravity(direction(NORTH)).setX(25).build()
    private val blendMode = BlendMode.MULTIPLY

    @Test
    fun testLayerPosition() {
        val noOverflow =
            Position.Builder().setGravity(direction(NORTH)).setX(10).setY(20).setAllowOverflow(false).build()
        cldAssertEqualsAsString("fl_no_overflow,g_north,x_10,y_20", noOverflow)
        cldAssertEqualsAsString(
            "g_north,x_10,y_20",
            Position.Builder().setGravity(direction(NORTH)).setX(10).setY(20).setAllowOverflow(true).build()
        )
        val tiled =
            Position.Builder().setGravity(direction(NORTH)).setX(10).setY(20).setTileMode(TileMode.TILED).build()
        cldAssertEqualsAsString("fl_tiled,g_north,x_10,y_20", tiled)

        cldAssertEqualsAsString(
            "g_north,x_10,y_20",
            Position.Builder().setGravity(direction(NORTH)).setX(10).setY(20).setTileMode(TileMode.NONE).build()
        )

        cldAssertEqualsAsString(
            "l_sample/fl_layer_apply.no_overflow,g_north,x_10,y_20",
            Layer.overlay(layer) { setPosition(noOverflow) })

        cldAssertEqualsAsString(
            "l_sample/fl_layer_apply.tiled,g_north,x_10,y_20",
            Layer.overlay(layer) { setPosition(tiled) })
    }

    @Test
    fun testOverlay() {
        cldAssertEqualsAsString("l_sample/fl_layer_apply", Layer.overlay(layer))
        cldAssertEqualsAsString(
            "l_sample/e_multiply,fl_layer_apply",
            Layer.overlay(layer) { setBlendMode(blendMode) }
        )
        cldAssertEqualsAsString(
            "l_sample/c_scale,w_100/fl_layer_apply",
            Layer.overlay(layer) { setTransformation(t) }
        )
        cldAssertEqualsAsString("l_sample/fl_layer_apply,g_north,x_25", Layer.overlay(layer) { setPosition(pos) })
        cldAssertEqualsAsString(
            "l_sample/c_scale,w_100/fl_layer_apply,g_north,x_25",
            Layer.overlay(layer) { setTransformation(t).setPosition(pos) }
        )
        cldAssertEqualsAsString(
            "l_sample/c_scale,w_100/e_multiply,fl_layer_apply",
            Layer.overlay(layer) { setTransformation(t).setBlendMode(blendMode) }
        )
        cldAssertEqualsAsString(
            "l_sample/e_multiply,fl_layer_apply,g_north,x_25",
            Layer.overlay(layer) { setPosition(pos).setBlendMode(blendMode) }
        )
        cldAssertEqualsAsString(
            "l_sample/c_scale,w_100/e_multiply,fl_layer_apply,g_north,x_25",
            Layer.overlay(layer) { setTransformation(t).setPosition(pos).setBlendMode(blendMode) }
        )
    }

    @Test
    fun testUnderlay() {
        cldAssertEqualsAsString("u_sample/fl_layer_apply", Layer.underlay(layer))
        cldAssertEqualsAsString(
            "u_sample/e_multiply,fl_layer_apply",
            Layer.underlay(layer) { setBlendMode(blendMode) }
        )
        cldAssertEqualsAsString(
            "u_sample/c_scale,w_100/fl_layer_apply",
            Layer.underlay(layer) { setTransformation(t) }
        )
        cldAssertEqualsAsString("u_sample/fl_layer_apply,g_north,x_25", Layer.underlay(layer) { setPosition(pos) })
        cldAssertEqualsAsString(
            "u_sample/c_scale,w_100/fl_layer_apply,g_north,x_25",
            Layer.underlay(layer) { setTransformation(t).setPosition(pos) }
        )
        cldAssertEqualsAsString(
            "u_sample/c_scale,w_100/e_multiply,fl_layer_apply",
            Layer.underlay(layer) { setTransformation(t).setBlendMode(blendMode) }
        )
        cldAssertEqualsAsString(
            "u_sample/e_multiply,fl_layer_apply,g_north,x_25",
            Layer.underlay(layer) { setPosition(pos).setBlendMode(blendMode) }
        )
        cldAssertEqualsAsString(
            "u_sample/c_scale,w_100/e_multiply,fl_layer_apply,g_north,x_25",
            Layer.underlay(layer) { setTransformation(t).setPosition(pos).setBlendMode(blendMode) }
        )
    }

    @Test
    fun testTextLayer() {
        cldAssertEqualsAsString("text:arial_20:test", LayerSource.text("test", "arial", 20))
        cldAssertEqualsAsString("text:arial_20:t%20est", LayerSource.text("t est", "arial", 20))
        cldAssertEqualsAsString("text:arial_20:test\$(var)", LayerSource.text("test\$(var)", "arial", 20))
        cldAssertEqualsAsString(
            "text:arial_20_bold_italic_antialias_best_hinting_full_underline_left_stroke_letter_spacing_12.0_line_spacing_2.0:test",
            LayerSource.text(
                "test",
                "arial",
                20
            ) {
                setFontWeight(FontWeight.BOLD)
                setFontStyle(FontStyle.ITALIC)
                setFontAntialias(FontAntialias.BEST)
                setFontHinting(FontHinting.FULL)
                setTextDecoration(FontDecoration.UNDERLINE)
                setTextAlign(TextAlign.LEFT)
                setStroke(Stroke.STROKE)
                setLetterSpacing(12f)
                setLineSpacing(2f)
            }
        )


        cldAssertEqualsAsString("text:arial_20:test", TextLayerSource("test", "arial", 20))
        cldAssertEqualsAsString("text:arial_20:t%20est", TextLayerSource("t est", "arial", 20))
        cldAssertEqualsAsString("text:arial_20:test\$(var)", TextLayerSource("test\$(var)", "arial", 20))
        cldAssertEqualsAsString(
            "text:arial_20_bold_italic_antialias_best_hinting_full_underline_left_stroke_letter_spacing_12.0_line_spacing_2.0:test",
            TextLayerSource(
                "test",
                "arial",
                20,
                FontWeight.BOLD,
                FontStyle.ITALIC,
                FontAntialias.BEST,
                FontHinting.FULL,
                FontDecoration.UNDERLINE,
                TextAlign.LEFT,
                Stroke.STROKE,
                12f,
                2f
            )
        )
    }

    @Test
    fun testFetchLayer() {
        cldAssertEqualsAsString(
            "fetch:aHR0cHM6Ly9yZXMuY2xvdWRpbmFyeS5jb20vZGVtby9pbWFnZS91cGxvYWQvc2FtcGxl.png",
            LayerSource.fetch("https://res.cloudinary.com/demo/image/upload/sample") { setFormat("png") }
        )
        cldAssertEqualsAsString(
            "video:fetch:aHR0cHM6Ly9yZXMuY2xvdWRpbmFyeS5jb20vZGVtby92aWRlby91cGxvYWQvZG9n",
            LayerSource.fetch("https://res.cloudinary.com/demo/video/upload/dog") { setResourceType("video") }
        )

        cldAssertEqualsAsString(
            "fetch:aHR0cHM6Ly9yZXMuY2xvdWRpbmFyeS5jb20vZGVtby9pbWFnZS91cGxvYWQvc2FtcGxl.png",
            FetchLayerSource("https://res.cloudinary.com/demo/image/upload/sample", format = "png")
        )
        cldAssertEqualsAsString(
            "video:fetch:aHR0cHM6Ly9yZXMuY2xvdWRpbmFyeS5jb20vZGVtby92aWRlby91cGxvYWQvZG9n",
            FetchLayerSource("https://res.cloudinary.com/demo/video/upload/dog", "video")
        )
    }

    @Test
    fun testMediaLayer() {
        cldAssertEqualsAsString(
            "video:dog.mp4",
            LayerSource.media("dog") { setFormat("mp4").setResourceType("video").setType("upload") })
        cldAssertEqualsAsString("sample", LayerSource.media("sample"))
        cldAssertEqualsAsString(
            "sample.png",
            LayerSource.media("sample") { setFormat("png").setResourceType("image").setType("upload") })

        cldAssertEqualsAsString("video:dog.mp4", MediaLayerSource("dog", "video", "upload", "mp4"))
        cldAssertEqualsAsString("sample", MediaLayerSource("sample"))
        cldAssertEqualsAsString("sample.png", MediaLayerSource("sample", "image", "upload", "png"))
    }
}