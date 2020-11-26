package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.effect.*
import com.cloudinary.transformation.layer.source.LayerSource
import com.cloudinary.transformation.resize.Resize
import org.junit.Test

class EffectTest {
    @Test
    fun accelerate() {
        cldAssert("e_accelerate", Effect.accelerate())
        cldAssert("e_accelerate:100", Effect.accelerate(100))
    }

    @Test
    fun testDeshake() {
        cldAssert("e_deshake", Effect.deshake())
        cldAssert("e_deshake:16", Effect.deshake(Deshake.DeshakeFactor.FACTOR16))
    }

    @Test
    fun testNoise() {
        cldAssert("e_noise", Effect.noise())
        cldAssert("e_noise:10", Effect.noise(10))
    }

    @Test
    fun testPreview() {
        cldAssert("e_preview:duration_12:max_seg_3:min_seg_dur_3", Transformation().effect(Effect.preview {
            duration(12)
            maximumSegments(3)
            minimumSegmentDuration(3)
        }))
    }

    @Test
    fun testBoomerang() {
        cldAssert("e_boomerang", Effect.boomerang())
    }

    @Test
    fun testReverse() {
        cldAssert("e_reverse", Effect.reverse())
    }

    @Test
    fun testFade() {
        cldAssert("e_fade", Effect.fadeIn())
        cldAssert("e_fade:2000", Effect.fadeIn(2000))
        cldAssert("e_fade:-2000", Effect.fadeOut(2000))
    }

    @Test
    fun testLoop() {
        cldAssert("e_loop", Effect.loop())
        cldAssert("e_loop:2", Effect.loop(2))
    }

    @Test
    fun testNegate() {
        cldAssert("e_negate", Effect.negate())
    }

    @Test
    fun testSepia() {
        cldAssert("e_sepia", Effect.sepia())
        cldAssert("e_sepia:50", Effect.sepia(50))
    }

    @Test
    fun testGrayScale() {
        cldAssert("e_grayscale", Effect.grayscale())
    }

    @Test
    fun testBlackWhite() {
        cldAssert("e_blackwhite", Effect.blackWhite())
    }

    @Test
    fun testColorize() {
        Effect.colorize()
        cldAssert("e_colorize", Effect.colorize())
        cldAssert("e_colorize:80", Effect.colorize { level(80) })
        cldAssert(
            "co_blue,e_colorize:80",
            Effect.colorize { level(80).color(Color.BLUE) })
    }

    @Test
    fun testAssistColorBlind() {
        cldAssert("e_assist_colorblind", Effect.assistColorBlind())
        cldAssert("e_assist_colorblind:8", Effect.assistColorBlind { stripes(8) })
        cldAssert(
            "e_assist_colorblind:xray", Effect.assistColorBlind { xRay() })


        cldAssert(
            "e_assist_colorblind:\$var1",
            Effect.assistColorBlind { stripes("\$var1") })
    }

    @Test
    fun testSimulateColorblind() {
        cldAssert("e_simulate_colorblind", Effect.simulateColorBlind())
        cldAssert(
            "e_simulate_colorblind:deuteranopia",
            Effect.simulateColorBlind(SimulateColorBlind.Type.DEUTERANOPIA)
        )
    }

    @Test
    fun testVectorize() {
        cldAssert("e_vectorize", Effect.vectorize())
        cldAssert(
            "e_vectorize:colors:15:detail:2:despeckle:0.5:paths:4:corners:5",
            Effect.vectorize { colors(15).detail(2).despeckle(0.5f).paths(4).corners(5) })
    }

    @Test
    fun testCartoonify() {
        cldAssert("e_cartoonify", Effect.cartoonify())
        cldAssert("e_cartoonify:20", Effect.cartoonify { lineStrength(20) })
        cldAssert("e_cartoonify:20:60", Effect.cartoonify { lineStrength(20).colorReduction(60) })
        cldAssert("e_cartoonify:30:bw", Effect.cartoonify { lineStrength(30).blackwhite() })
        cldAssert(
            "e_cartoonify:30:bw",
            Effect.cartoonify { lineStrength(30).colorReduction(60).blackwhite() })
        cldAssert("e_cartoonify:bw", Effect.cartoonify { colorReduction(60).blackwhite() })
    }

    @Test
    fun testArtistic() {
        cldAssert(
            "e_art:al_dente", Effect.artisticFilter(Artistic.ArtisticFilter.AL_DENTE)
        )
    }

    @Test
    fun testWaveform() {
        cldAssert("fl_waveform", Effect.waveform())

        cldAssert(
            "b_white,co_black,fl_waveform", Effect.waveform {
                color(Color.BLACK)
                background(Color.WHITE)
            }
        )
    }

    @Test
    fun testMakeTransparent() {
        cldAssert("e_make_transparent", Effect.makeTransparent())
        cldAssert("e_make_transparent:50", Effect.makeTransparent { level(50) })
    }

    @Test
    fun testMakeTransparentVideo() {
        cldAssert("co_rgb:0e80d8,e_make_transparent", Effect.makeTransparent {
            color(Color.Rgb("0e80d8"))
        })
        cldAssert("co_rgb:0e80d8,e_make_transparent:10", Effect.makeTransparent {
            color(Color.Rgb("0e80d8"))
            level(10)
        })
    }

    @Test
    fun testTrim() {
        cldAssert("e_trim", Effect.trim())
        cldAssert("e_trim:30", Effect.trim { colorSimilarity(30) })
        cldAssert("e_trim:white", Effect.trim { colorOverride(Color.WHITE) })
        cldAssert(
            "e_trim:30:white",
            Effect.trim { colorSimilarity(30).colorOverride(Color.WHITE) })
    }

    @Test
    fun testOilPaint() {
        cldAssert("e_oil_paint", Effect.oilPaint())
        cldAssert("e_oil_paint:40", Effect.oilPaint(40))
    }

    @Test
    fun testRedEye() {
        cldAssert("e_redeye", Effect.redEye())
    }

    @Test
    fun testAdvRedEye() {
        cldAssert("e_adv_redeye", Effect.advancedRedEye())
    }

    @Test
    fun testVignette() {
        cldAssert("e_vignette", Effect.vignette())
        cldAssert("e_vignette:30", Effect.vignette(30))
    }

    @Test
    fun testPixelate() {
        cldAssert("e_pixelate", Effect.pixelate())
        cldAssert("e_pixelate:3", Effect.pixelate(3))

        cldAssert(
            "e_pixelate_region:20,h_40,w_40,x_20,y_20",
            Effect.pixelate(20, Region.custom {
                x(20)
                y(20)
                width(40)
                height(40)
            })
        )

        cldAssert("e_pixelate_faces", Effect.pixelate(region = Region.Faces()))
        cldAssert("e_pixelate_faces:7", Effect.pixelate(7, Region.Faces()))
    }

    @Test
    fun testBlur() {
        cldAssert("e_blur", Effect.blur())
        cldAssert("e_blur:300", Effect.blur(300))

        cldAssert(
            "e_blur_region:200,h_40,w_40,x_10,y_20",
            Effect.blur(200, Region.custom {
                x(10)
                y(20)
                width(40)
                height(40)
            })
        )


        cldAssert("e_blur_faces", Effect.blur(region = Region.Faces()))
        cldAssert("e_blur_faces:600", Effect.blur(600, Region.Faces()))
    }

    @Test
    fun testOrderedDither() {
        cldAssert(
            "e_ordered_dither:0",
            Effect.orderedDither(OrderedDither.DitherFilter.THRESHOLD_1X1_NON_DITHER)
        )
    }

    @Test
    fun testShadow() {
        cldAssert("e_shadow", Effect.shadow())
        cldAssert("e_shadow:50", Effect.shadow { strength(50) })
        cldAssert("e_shadow:50", Effect.shadow(50))
        cldAssert(
            "co_green,e_shadow:50",
            Effect.shadow { strength(50).color(Color.GREEN) })
        cldAssert(
            "co_green,e_shadow:50,x_20,y_-20",
            Effect.shadow { strength(50).color(Color.GREEN).x(20).y(-20) }
        )
    }

    @Test
    fun testOutline() {
        cldAssert("e_outline", Effect.outline())
        cldAssert("e_outline:inner_fill", Effect.outline {
            mode(Outline.Mode.INNER_FILL)
        })

        cldAssert(
            "co_red,e_outline:inner_fill:5",
            Effect.outline {
                mode(Outline.Mode.INNER_FILL)
                width(5)
                color(Color.RED)
            })

        cldAssert(
            "co_red,e_outline:inner_fill:5:200",
            Effect.outline {
                mode(Outline.Mode.INNER_FILL)
                blur(200)
                width(5)
                color(Color.RED)
            })

        cldAssert(
            "co_red,e_outline:inner_fill:5:200",
            Effect.outline {
                mode(Outline.Mode.INNER_FILL)
                blur(200)
                width(5)
                color("red")
            })
    }

    @Test
    fun testStyleTransfer() {
        val lighthouse = LayerSource.image("lighthouse")
        cldAssert(
            "l_lighthouse/e_style_transfer,fl_layer_apply",
            Effect.styleTransfer(lighthouse)
        )
        cldAssert(
            "l_lighthouse/e_style_transfer:34:preserve_color,fl_layer_apply",
            Effect.styleTransfer(lighthouse) {
                this.preserveColor().strength(34)
            }
        )

        cldAssert(
            "l_lighthouse/c_scale,w_200/e_sepia/e_style_transfer:34,fl_layer_apply",
            Effect.styleTransfer(LayerSource.image("lighthouse") {
                transformation {
                    resize(Resize.scale(200))
                    effect(Effect.sepia())
                }
            }) {
                strength(34)
            })

        cldAssert(
            "l_lighthouse/c_scale,w_200/e_sepia/e_style_transfer:34,fl_layer_apply",
            Effect.styleTransfer(StyleTransfer.image {
                source("lighthouse") {
                    transformation {
                        resize(Resize.scale(200))
                        effect(Effect.sepia())
                    }
                }
                strength(34)
            })
        )

        cldAssert(
            "l_lighthouse/e_style_transfer:preserve_color,fl_layer_apply",
            Effect.styleTransfer(LayerSource.image("lighthouse")) { preserveColor() }
        )
    }
}