package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.gravity.Gravity
import com.cloudinary.transformation.layer.*
import com.cloudinary.transformation.layer.Source.Companion.subtitles

import com.cloudinary.transformation.layer.Stroke.STROKE
import com.cloudinary.transformation.resize.Resize
import org.junit.Test

class LayerTest {
    private val source = MediaSource("sample")
    private val pos = Position.position {
        gravity(Gravity.north())
        x(25)
    }
    private val blendMode = BlendMode.multiply()

    @Test
    fun testLayerPosition() {

        val noOverflow = Position.position {
            gravity(Gravity.north())
            x(10)
            y(20)
            allowOverflow(false)
        }

        cldAssert("fl_no_overflow,g_north,x_10,y_20", noOverflow)
        cldAssert(
            "g_north,x_10,y_20",
            Position.position {
                gravity(Gravity.north())
                x(10)
                y(20)
                allowOverflow(true)
            }
        )
        val tiled =
            Position.position {
                gravity(Gravity.north())
                x(10)
                y(20)
                tileMode(TileMode.TILED)
            }
        cldAssert("fl_tiled,g_north,x_10,y_20", tiled)

        cldAssert(
            "g_north,x_10,y_20",
            Position.position {
                gravity(Gravity.north())
                x(10)
                y(20)
                tileMode(TileMode.NONE)
            }
        )

        cldAssert(
            "l_sample/fl_layer_apply.no_overflow,g_north,x_10,y_20",
            Transformation().overlay(source) {
                position(noOverflow)
            }
        )

        cldAssert(
            "l_sample/fl_layer_apply.tiled,g_north,x_10,y_20",
            Transformation().overlay(source) {
                position(tiled)
            }
        )
    }

    @Test
    fun testOverlay() {
        cldAssert("l_sample/fl_layer_apply", Transformation().overlay(source))
        cldAssert(
            "l_sample/e_multiply,fl_layer_apply",
            Transformation().overlay(source) {
                blendMode(blendMode)
            }
        )
        cldAssert(
            "l_sample/c_scale,w_100/fl_layer_apply",
            Transformation().overlay(Source.image("sample")) {
                transformation {
                    resize(Resize.scale(100))
                }
            })

        cldAssert(
            "l_sample/fl_layer_apply,g_north,x_25", Transformation().overlay(source) {
                position(pos)
            })

        cldAssert(
            "l_sample/c_scale,w_100/fl_layer_apply,g_north,x_25",
            Transformation().overlay(Source.image("sample")) {
                position(pos)
                transformation {
                    resize(Resize.scale(100))

                }
            }
        )

        cldAssert(
            "l_sample/c_scale,w_100/e_multiply,fl_layer_apply",
            Transformation().overlay(Source.image("sample")) {
                transformation {
                    resize(Resize.scale(100))

                }
                blendMode(blendMode)
            }
        )

        cldAssert(
            "l_sample/e_multiply,fl_layer_apply,g_north,x_25",
            Transformation().overlay(source) {
                position(pos)
                blendMode(blendMode)
            }
        )
        cldAssert(
            "l_sample/c_scale,w_100/e_multiply,fl_layer_apply,g_north,x_25",
            Transformation().overlay(source) {
                transformation {
                    resize(Resize.scale(100))
                }
                position(pos)
                blendMode(blendMode)
            }
        )
    }

    @Test
    fun testUnderlay() {
        cldAssert("u_sample/fl_layer_apply", Transformation().underlay(source))
        cldAssert(
            "u_sample/e_multiply,fl_layer_apply",
            Transformation().underlay(source) {
                blendMode(blendMode)
            }
        )
        cldAssert(
            "u_sample/c_scale,w_100/fl_layer_apply",
            Transformation().underlay(Source.image("sample")) {
                transformation {
                    resize(Resize.scale(100))
                }
            })

        cldAssert("u_sample/fl_layer_apply,g_north,x_25", Transformation().underlay(source) {
            position(pos)
        })

        cldAssert(
            "u_sample/c_scale,w_100/fl_layer_apply,g_north,x_25",
            Transformation().underlay(Source.image("sample")) {
                transformation {
                    resize(Resize.scale(100))
                }
                position(pos)
            })

        cldAssert(
            "u_sample/c_scale,w_100/e_multiply,fl_layer_apply",
            Transformation().underlay(Source.image("sample")) {
                transformation {
                    resize(Resize.scale(100))
                }
                blendMode(blendMode)
            })

        cldAssert(
            "u_sample/e_multiply,fl_layer_apply,g_north,x_25",
            Transformation().underlay(source) {
                position(pos)
                blendMode(blendMode)
            })

        cldAssert(
            "u_sample/c_scale,w_100/e_multiply,fl_layer_apply,g_north,x_25",
            Transformation().underlay(Source.image("sample")) {
                transformation {
                    resize(Resize.scale(100))
                }
                position(pos)
                blendMode(blendMode)
            })
    }

    @Test
    fun testSubtitlesLayer() {
        cldAssert(
            "b_red,co_blue,l_subtitles:arial_17_antialias_best:sample_sub_en.srt/fl_layer_apply",
            Transformation().overlay(subtitles("sample_sub_en.srt") {
                fontFamily("arial")
                fontSize(17)
                background(Color.RED)
                color(Color.BLUE)
                style {
                    fontAntialias(FontAntialias.BEST)
                }
            })
        )
    }

    @Test
    fun testTextLayer() {
        cldAssert("l_text:arial_20:test/fl_layer_apply", Transformation().overlay(Source.text("test") {
            fontFamily("arial")
            fontSize(20)
        }))
        cldAssert("l_text:arial_20:t%20est/fl_layer_apply", Transformation().overlay(Source.text("t est") {
            fontFamily("arial")
            fontSize(20)
        }))
        cldAssert(
            "l_text:arial_20:test\$(var)/fl_layer_apply",
            Transformation().overlay(Source.text("test\$(var)") {
                fontFamily("arial")
                fontSize(20)
            })
        )

        cldAssert(
            "l_text:arial_20_bold_italic_antialias_best_hinting_full_underline_left_stroke_letter_spacing_12.0_line_spacing_2.0:test/fl_layer_apply",
            Transformation().overlay(
                Source.text(
                    "test"
                ) {
                    fontFamily("arial")
                    fontSize(20)
                    style {
                        fontWeight(FontWeight.BOLD)
                        fontStyle(FontStyle.ITALIC)
                        fontAntialias(FontAntialias.BEST)
                        fontHinting(FontHinting.FULL)
                        textDecoration(TextDecoration.UNDERLINE)
                        textAlignment(TextAlignment.LEFT)
                        stroke(STROKE)
                        letterSpacing(12f)
                        lineSpacing(2f)
                    }
                }
            ))


        cldAssert("l_text:arial_20:test/fl_layer_apply", Transformation().overlay(Source.text("test") {
            fontFamily("arial")
            fontSize(20)
        }))

        cldAssert("l_text:arial_20:t%20est/fl_layer_apply", Transformation().overlay(Source.text("t est") {
            fontFamily("arial")
            fontSize(20)
        }))

        cldAssert(
            "l_text:arial_20:test\$(var)/fl_layer_apply",
            Transformation().overlay(Source.text("test\$(var)") {
                fontFamily("arial")
                fontSize(20)
            })
        )

        cldAssert(
            "l_text:arial_20_bold_italic_antialias_best_hinting_full_underline_left_stroke_letter_spacing_12_line_spacing_2:test/fl_layer_apply",
            Transformation().overlay(
                Source.text(
                    "test"
                ) {

                    fontFamily("arial")
                    fontSize(20)

                    style {
                        fontWeight(FontWeight.BOLD)
                        fontStyle(FontStyle.ITALIC)
                        fontAntialias(FontAntialias.BEST)
                        fontHinting(FontHinting.FULL)
                        textDecoration(TextDecoration.UNDERLINE)
                        textAlignment(TextAlignment.LEFT)
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
                Source.fetch("https://res.cloudinary.com/demo/image/upload/sample") {
                    format("png")
                })
        )

        cldAssert(
            "l_video:fetch:aHR0cHM6Ly9yZXMuY2xvdWRpbmFyeS5jb20vZGVtby92aWRlby91cGxvYWQvZG9n/fl_layer_apply",
            Transformation().overlay(Source.fetch("https://res.cloudinary.com/demo/video/upload/dog") {
                resourceType(
                    "video"
                )
            })
        )


        cldAssert(
            "l_fetch:aHR0cHM6Ly9yZXMuY2xvdWRpbmFyeS5jb20vZGVtby9pbWFnZS91cGxvYWQvc2FtcGxl.png/fl_layer_apply",
            Transformation().overlay(
                FetchSource(
                    "https://res.cloudinary.com/demo/image/upload/sample",
                    format = "png"
                )
            )
        )


        cldAssert(
            "l_video:fetch:aHR0cHM6Ly9yZXMuY2xvdWRpbmFyeS5jb20vZGVtby92aWRlby91cGxvYWQvZG9n/fl_layer_apply",
            Transformation()
                .overlay(
                    FetchSource(
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
            Transformation().overlay((Source.image("dog") {
                format("mp4").resourceType("video").type("upload")
            }))
        )

        cldAssert("l_sample/fl_layer_apply", Transformation().overlay(Source.image("sample")))
        cldAssert(
            "l_sample.png/fl_layer_apply",
            Transformation().overlay(Source.image("sample") {
                format("png").resourceType("image").type("upload")
            })
        )

        cldAssert(
            "l_video:dog.mp4/fl_layer_apply",
            Transformation().overlay(MediaSource("dog", "video", "upload", "mp4"))
        )
        cldAssert("l_sample/fl_layer_apply", Transformation().overlay(MediaSource("sample")))
        cldAssert(
            "l_sample.png/fl_layer_apply",
            Transformation().overlay(MediaSource("sample", "image", "upload", "png"))
        )
    }
}