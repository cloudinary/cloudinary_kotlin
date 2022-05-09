package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.Region.Companion.custom
import com.cloudinary.transformation.effect.*
import com.cloudinary.transformation.expression.Expression
import com.cloudinary.transformation.layer.source.Source
import com.cloudinary.transformation.resize.Resize
import org.junit.Test

class EffectTest {
    @Test
    fun accelerate() {
        cldAssert("e_accelerate", Effect.accelerate())
        cldAssert("e_accelerate:100", Effect.accelerate {
            rate(100)
        })
    }

    @Test
    fun testDeshake() {
        cldAssert("e_deshake", Effect.deshake())
        cldAssert("e_deshake:16", Effect.deshake {
            this.shakeStrength(ShakeStrength.pixels16())
        })
    }

    @Test
    fun testNoise() {
        cldAssert("e_noise", Effect.noise())
        cldAssert("e_noise:10", Effect.noise {
            level(10)
        })
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
        cldAssert("e_fade:2000", Effect.fadeIn {
            duration(2000)
        })
        cldAssert("e_fade:-2000", Effect.fadeOut(2000))
    }

    @Test
    fun testLoop() {
        cldAssert("e_loop", Effect.loop())
        cldAssert("e_loop:2", Effect.loop {
            additionalIterations(2)
        })
    }

    @Test
    fun testNegate() {
        cldAssert("e_negate", Effect.negate())
    }

    @Test
    fun testSepia() {
        cldAssert("e_sepia", Effect.sepia())
        cldAssert("e_sepia:50", Effect.sepia {
            level(50)
        })
    }

    @Test
    fun testGrayScale() {
        cldAssert("e_grayscale", Effect.grayscale())
    }

    @Test
    fun testBlackWhite() {
        cldAssert("e_blackwhite", Effect.blackwhite())
    }

    @Test
    fun testColorize() {
        cldAssert("e_colorize", Effect.colorize())
        cldAssert("e_colorize:80", Effect.colorize { level(80) })
        cldAssert(
            "co_blue,e_colorize:80",
            Effect.colorize { level(80).color(Color.BLUE) })
    }

    @Test
    fun testTheme() {
        cldAssert("e_theme:color_black", Effect.theme(Color.BLACK))
        cldAssert("e_theme:color_black:photosensitivity_30", Effect.theme(Color.BLACK) {
            photosensitivity(30)
        })
    }

    @Test
    fun testAssistColorBlind() {
        cldAssert("e_assist_colorblind", Effect.assistColorBlind())
        cldAssert("e_assist_colorblind:8", Effect.assistColorBlind { stripesStrength(8) })
        cldAssert(
            "e_assist_colorblind:xray", Effect.assistColorBlind { xray() })


        cldAssert(
            "e_assist_colorblind:\$var1",
            Effect.assistColorBlind { stripesStrength("\$var1") })
    }

    @Test
    fun testSimulateColorblind() {
        cldAssert("e_simulate_colorblind", Effect.simulateColorBlind())
        cldAssert(
            "e_simulate_colorblind:deuteranopia",
            Effect.simulateColorBlind {
                condition(SimulateColorBlind.deuteranopia())
            }
        )
    }

    @Test
    fun testVectorize() {
        cldAssert("e_vectorize", Effect.vectorize())
        cldAssert(
            "e_vectorize:colors:15:detail:2:despeckle:0.5:paths:4:corners:5",
            Effect.vectorize { numOfColors(15).detailsLevel(2).despeckleLevel(0.5f).paths(4).cornersLevel(5) })
    }

    @Test
    fun testCartoonify() {
        cldAssert("e_cartoonify", Effect.cartoonify())
        cldAssert("e_cartoonify:20", Effect.cartoonify { lineStrength(20) })
        cldAssert("e_cartoonify:20:60", Effect.cartoonify { lineStrength(20).colorReductionLevel(60) })
        cldAssert("e_cartoonify:30:bw", Effect.cartoonify { lineStrength(30).blackwhite() })
        cldAssert(
            "e_cartoonify:30:bw",
            Effect.cartoonify { lineStrength(30).colorReductionLevel(60).blackwhite() })
        cldAssert("e_cartoonify:bw", Effect.cartoonify { colorReductionLevel(60).blackwhite() })
    }

    @Test
    fun testArtistic() {
        cldAssert(
            "e_art:al_dente", Effect.artisticFilter(ArtisticFilter.alDente())
        )
    }

    @Test
    fun testWaveform() {
        cldAssert("f_jpg,fl_waveform", Effect.waveform(Format.jpg()))

        cldAssert(
            "b_white,co_black,f_png,fl_waveform", Effect.waveform(Format.png()) {
                color(Color.BLACK)
                background(Color.WHITE)
            }
        )
    }

    @Test
    fun testMakeTransparent() {
        cldAssert("e_make_transparent", Effect.makeTransparent())
        cldAssert("e_make_transparent:50", Effect.makeTransparent { tolerance(50) })
    }

    @Test
    fun testMakeTransparentVideo() {
        cldAssert("co_rgb:0e80d8,e_make_transparent", Effect.makeTransparent {
            colorToReplace(Color.rgb("0e80d8"))
        })
        cldAssert("co_rgb:0e80d8,e_make_transparent:10", Effect.makeTransparent {
            colorToReplace(Color.rgb("0e80d8"))
            tolerance(10)
        })
    }

    @Test
    fun testOilPaint() {
        cldAssert("e_oil_paint", Effect.oilPaint())
        cldAssert("e_oil_paint:40", Effect.oilPaint {
            strength(40)
        })
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
        cldAssert("e_vignette:30", Effect.vignette { strength(30) })
    }

    @Test
    fun testPixelate() {
        cldAssert("e_pixelate", Effect.pixelate())
        cldAssert("e_pixelate:3", Effect.pixelate {
            squareSize(3)
        })

        cldAssert(
            "e_pixelate_region:20,h_40,w_40,x_20,y_20",
            Effect.pixelate {
                squareSize(20)
                region(custom {
                    x(20)
                    y(20)
                    width(40)
                    height(40)
                })
            }
        )

        cldAssert("e_pixelate_faces", Effect.pixelate {
            region(Region.faces())
        })

        cldAssert("e_pixelate_faces:7", Effect.pixelate {
            squareSize(7)
            region(Region.faces())
        })

        cldAssert("e_pixelate_region:7,g_ocr_text", Effect.pixelate {
            squareSize(7)
            region(Region.ocr())
        })
    }

    @Test
    fun testBlur() {
        cldAssert("e_blur", Effect.blur())
        cldAssert("e_blur:100_div_2", Effect.blur { strength(Expression("100 / 2")) })
        cldAssert("e_blur:300", Effect.blur {
            strength(300)
        })

        cldAssert(
            "e_blur_region:200,h_40,w_40,x_10,y_20",
            Effect.blur {
                strength(200)
                region(custom {
                    x(10)
                    y(20)
                    width(40)
                    height(40)
                })
            }
        )

        cldAssert("e_blur_faces", Effect.blur {
            region(Region.faces())
        })

        cldAssert(
            "e_blur_faces:600", Effect.blur {
                region(Region.faces())
                strength(600)
            })


        cldAssert("e_blur_region:600,g_ocr_text", Effect.blur {
            region(Region.ocr())
            strength(600)
        })
    }

    @Test
    fun testOrderedDither() {
        cldAssert(
            "e_ordered_dither:0",
            Effect.dither {
                type(Dither.threshold1x1NonDither())
            }
        )
    }

    @Test
    fun testShadow() {
        cldAssert("e_shadow", Effect.shadow())
        cldAssert("e_shadow:50", Effect.shadow { strength(50) })
        cldAssert("e_shadow:50", Effect.shadow {
            strength(50)
        })
        cldAssert(
            "co_green,e_shadow:50",
            Effect.shadow { strength(50).color(Color.GREEN) })
        cldAssert(
            "co_green,e_shadow:50,x_20,y_-20",
            Effect.shadow { strength(50).color(Color.GREEN).offsetX(20).offsetY(-20) }
        )
    }

    @Test
    fun testOutline() {
        cldAssert("e_outline", Effect.outline())
        cldAssert("e_outline:inner_fill", Effect.outline {
            mode(OutlineMode.innerFill())
        })

        cldAssert(
            "co_red,e_outline:inner_fill:5",
            Effect.outline {
                mode(OutlineMode.innerFill())
                width(5)
                color(Color.RED)
            })

        cldAssert(
            "co_red,e_outline:inner_fill:5:200",
            Effect.outline {
                mode(OutlineMode.innerFill())
                blurLevel(200)
                width(5)
                color(Color.RED)
            })

        cldAssert(
            "co_red,e_outline:inner_fill:5:200",
            Effect.outline {
                mode(OutlineMode.innerFill())
                blurLevel(200)
                width(5)
                color("red")
            })
    }

    @Test
    fun testStyleTransfer() {
        val lighthouse = Source.image("lighthouse")
        cldAssert(
            "l_lighthouse/e_style_transfer,fl_layer_apply",
            Effect.styleTransfer(lighthouse)
        )
        cldAssert(
            "l_lighthouse/e_style_transfer:preserve_color:34,fl_layer_apply",
            Effect.styleTransfer(lighthouse) {
                this.preserveColor().strength(34)
            }
        )

        cldAssert(
            "l_lighthouse/c_scale,w_200/e_sepia/e_style_transfer:34,fl_layer_apply",
            Effect.styleTransfer(Source.image("lighthouse") {
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
            Effect.styleTransfer(Source.image("lighthouse")) { preserveColor() }
        )
    }

    @Test
    fun testRemoveBackground() {
        cldAssert(
            "e_bgremoval",
            Effect.removeBackground()
        )

        cldAssert(
            "e_bgremoval:screen",
            Effect.removeBackground { screen() }
        )

        cldAssert(
            "e_bgremoval:red",
            Effect.removeBackground { colorToRemove(Color.RED) }
        )

        cldAssert(
            "e_bgremoval:screen:red",
            Effect.removeBackground {
                colorToRemove(Color.RED)
                screen()
            }
        )
    }
}
