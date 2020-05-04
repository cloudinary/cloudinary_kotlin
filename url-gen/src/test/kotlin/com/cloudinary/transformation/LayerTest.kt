package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.layer.*
import com.cloudinary.transformation.layer.Layer.Companion.subtitles
import com.cloudinary.transformation.layer.LayerContainer.Companion.overlay
import com.cloudinary.transformation.layer.LayerContainer.Companion.underlay
import com.cloudinary.transformation.layer.Stroke.STROKE
import com.cloudinary.transformation.resize.Resize
import org.junit.Test

class LayerTest {
    private val layer = MediaLayer("sample")
    private val pos = Position.Builder().gravity(Gravity.north()).x(25).build()
    private val blendMode = BlendMode.MULTIPLY

    @Test
    fun testLayerPosition() {

        val noOverflow =
            Position.Builder().gravity(Gravity.north()).x(10).y(20).allowOverflow(false).build()
        cldAssert("fl_no_overflow,g_north,x_10,y_20", noOverflow)
        cldAssert(
            "g_north,x_10,y_20",
            Position.Builder().gravity(Gravity.north()).x(10).y(20).allowOverflow(true).build()
        )
        val tiled =
            Position.Builder().gravity(Gravity.north()).x(10).y(20).tileMode(TileMode.TILED).build()
        cldAssert("fl_tiled,g_north,x_10,y_20", tiled)

        cldAssert(
            "g_north,x_10,y_20",
            Position.Builder().gravity(Gravity.north()).x(10).y(20).tileMode(TileMode.NONE).build()
        )

        cldAssert(
            "l_sample/fl_layer_apply.no_overflow,g_north,x_10,y_20",
            overlay(layer) { position(noOverflow) })

        cldAssert(
            "l_sample/fl_layer_apply.tiled,g_north,x_10,y_20",
            overlay(layer) { position(tiled) })
    }

    @Test
    fun testOverlay() {
        cldAssert("l_sample/fl_layer_apply", overlay(layer))
        cldAssert(
            "l_sample/e_multiply,fl_layer_apply",
            overlay(layer) {
                blendMode(blendMode)
            }
        )
        cldAssert(
            "l_sample/c_scale,w_100/fl_layer_apply",
            overlay(layer) {
                resize(Resize.scale { width(100) })
            }
        )
        cldAssert("l_sample/fl_layer_apply,g_north,x_25", overlay(layer) {
            position(pos)
        })
        cldAssert(
            "l_sample/c_scale,w_100/fl_layer_apply,g_north,x_25",
            overlay(layer) {
                resize(Resize.scale { width(100) })
                position(pos)
            }
        )
        cldAssert(
            "l_sample/c_scale,w_100/e_multiply,fl_layer_apply",
            overlay(layer) {
                resize(Resize.scale { width(100) })
                blendMode(blendMode)
            }
        )
        cldAssert(
            "l_sample/e_multiply,fl_layer_apply,g_north,x_25",
            overlay(layer) {
                position(pos)
                blendMode(blendMode)
            }
        )
        cldAssert(
            "l_sample/c_scale,w_100/e_multiply,fl_layer_apply,g_north,x_25",
            overlay(layer) {
                resize(Resize.scale { width(100) })
                position(pos)
                blendMode(blendMode)
            }
        )
    }

    @Test
    fun testUnderlay() {
        cldAssert("u_sample/fl_layer_apply", underlay(layer))
        cldAssert(
            "u_sample/e_multiply,fl_layer_apply",
            underlay(layer) { blendMode(blendMode) }
        )
        cldAssert(
            "u_sample/c_scale,w_100/fl_layer_apply",
            underlay(layer) { resize(Resize.scale { width(100) }) }
        )
        cldAssert("u_sample/fl_layer_apply,g_north,x_25", underlay(layer) { position(pos) })
        cldAssert(
            "u_sample/c_scale,w_100/fl_layer_apply,g_north,x_25",
            underlay(layer) { resize(Resize.scale { width(100) }).position(pos) }
        )
        cldAssert(
            "u_sample/c_scale,w_100/e_multiply,fl_layer_apply",
            underlay(layer) { resize(Resize.scale { width(100) }).blendMode(blendMode) }
        )
        cldAssert(
            "u_sample/e_multiply,fl_layer_apply,g_north,x_25",
            underlay(layer) { position(pos).blendMode(blendMode) }
        )
        cldAssert(
            "u_sample/c_scale,w_100/e_multiply,fl_layer_apply,g_north,x_25",
            underlay(layer) { resize(Resize.scale { width(100) }).position(pos).blendMode(blendMode) }
        )
    }

    @Test
    fun testSubtitlesLayer() {
        cldAssert(
            "b_red,co_blue,l_subtitles:arial_17_antialias_best:sample_sub_en.srt/fl_layer_apply",
            overlay(subtitles("sample_sub_en.srt") {
                fontFamily("arial")
                fontSize(17)
                background(Color.RED)
                textColor(Color.BLUE)
                style {
                    fontAntialias(FontAntialias.BEST)
                }
            })
        )
    }

    @Test
    fun testTextLayer() {
        cldAssert("l_text:arial_20:test/fl_layer_apply", overlay(Layer.text("test") {
            fontFamily("arial")
            fontSize(20)
        }))
        cldAssert("l_text:arial_20:t%20est/fl_layer_apply", overlay(Layer.text("t est") {
            fontFamily("arial")
            fontSize(20)
        }))
        cldAssert(
            "l_text:arial_20:test\$(var)/fl_layer_apply",
            overlay(Layer.text("test\$(var)") {
                fontFamily("arial")
                fontSize(20)
            })
        )

        cldAssert(
            "l_text:arial_20_bold_italic_antialias_best_hinting_full_underline_left_stroke_letter_spacing_12.0_line_spacing_2.0:test/fl_layer_apply",
            overlay(Layer.text(
                "test"
            ) {
                fontFamily("arial")
                fontSize(20)
                style {
                    fontWeight(FontWeight.BOLD)
                    fontStyle(FontStyle.ITALIC)
                    fontAntialias(FontAntialias.BEST)
                    fontHinting(FontHinting.FULL)
                    textDecoration(FontDecoration.UNDERLINE)
                    textAlign(TextAlign.LEFT)
                    stroke(STROKE)
                    letterSpacing(12f)
                    lineSpacing(2f)
                }
            }
            ))


        cldAssert("l_text:arial_20:test/fl_layer_apply", overlay(Layer.text("test") {
            fontFamily("arial")
            fontSize(20)
        }))

        cldAssert("l_text:arial_20:t%20est/fl_layer_apply", overlay(Layer.text("t est") {
            fontFamily("arial")
            fontSize(20)
        }))

        cldAssert(
            "l_text:arial_20:test\$(var)/fl_layer_apply",
            overlay(Layer.text("test\$(var)") {
                fontFamily("arial")
                fontSize(20)
            })
        )

        cldAssert(
            "l_text:arial_20_bold_italic_antialias_best_hinting_full_underline_left_stroke_letter_spacing_12_line_spacing_2:test/fl_layer_apply",
            overlay(
                Layer.text(
                    "test"
                ) {

                    fontFamily("arial")
                    fontSize(20)

                    style {
                        fontWeight(FontWeight.BOLD)
                        fontStyle(FontStyle.ITALIC)
                        fontAntialias(FontAntialias.BEST)
                        fontHinting(FontHinting.FULL)
                        textDecoration(FontDecoration.UNDERLINE)
                        textAlign(TextAlign.LEFT)
                        stroke(STROKE)
                        letterSpacing(12)
                        lineSpacing(2)
                    }
                }
            ))

    }

    @Test
    fun testFetchLayer() {
        cldAssert(
            "l_fetch:aHR0cHM6Ly9yZXMuY2xvdWRpbmFyeS5jb20vZGVtby9pbWFnZS91cGxvYWQvc2FtcGxl.png/fl_layer_apply",
            Transformation().overlay(
                Layer.fetch("https://res.cloudinary.com/demo/image/upload/sample") { format("png") })
        )

        cldAssert(
            "l_video:fetch:aHR0cHM6Ly9yZXMuY2xvdWRpbmFyeS5jb20vZGVtby92aWRlby91cGxvYWQvZG9n/fl_layer_apply",
            Transformation().overlay(Layer.fetch("https://res.cloudinary.com/demo/video/upload/dog") {
                resourceType(
                    "video"
                )
            })
        )


        cldAssert(
            "l_fetch:aHR0cHM6Ly9yZXMuY2xvdWRpbmFyeS5jb20vZGVtby9pbWFnZS91cGxvYWQvc2FtcGxl.png/fl_layer_apply",
            Transformation().overlay(
                FetchLayer(
                    "https://res.cloudinary.com/demo/image/upload/sample",
                    format = "png"
                )
            )
        )


        cldAssert(
            "l_video:fetch:aHR0cHM6Ly9yZXMuY2xvdWRpbmFyeS5jb20vZGVtby92aWRlby91cGxvYWQvZG9n/fl_layer_apply",
            Transformation()
                .overlay(
                    FetchLayer(
                        "https://res.cloudinary.com/demo/video/upload/dog",
                        "video"
                    )
                )
        )
    }

    @Test
    fun testMediaLayer() {
        cldAssert(
            "l_video:dog.mp4/fl_layer_apply",
            Transformation().overlay((Layer.image("dog") {
                format("mp4").resourceType("video").type("upload")
            }))
        )

        cldAssert("l_sample/fl_layer_apply", overlay(Layer.image("sample")))
        cldAssert(
            "l_sample.png/fl_layer_apply",
            overlay(Layer.image("sample") {
                format("png").resourceType("image").type("upload")
            })
        )

        cldAssert(
            "l_video:dog.mp4/fl_layer_apply",
            overlay(MediaLayer("dog", "video", "upload", "mp4"))
        )
        cldAssert("l_sample/fl_layer_apply", overlay(MediaLayer("sample")))
        cldAssert(
            "l_sample.png/fl_layer_apply",
            overlay(MediaLayer("sample", "image", "upload", "png"))
        )
    }
}