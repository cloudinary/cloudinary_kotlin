package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.CustomFunction.Companion.wasm
import com.cloudinary.transformation.Extract.Companion.pages
import com.cloudinary.transformation.adjust.Adjust
import com.cloudinary.transformation.adjust.Adjust.Companion.opacity
import com.cloudinary.transformation.delivery.AudioCodec
import com.cloudinary.transformation.delivery.Delivery
import com.cloudinary.transformation.effect.Effect
import com.cloudinary.transformation.layer.BlendMode.SCREEN
import com.cloudinary.transformation.layer.FontHinting
import com.cloudinary.transformation.layer.FontWeight
import com.cloudinary.transformation.layer.Layer.Companion.image
import com.cloudinary.transformation.layer.Layer.Companion.text
import com.cloudinary.transformation.layer.Stroke
import com.cloudinary.transformation.reshape.Reshape.Companion.distortArc
import com.cloudinary.transformation.resize.Resize.Companion.scale
import org.junit.Test

class TransformationTest {
    private val layer = image("sample")

    private val sepiaTransformation = Transformation().effect(Effect.sepia())

    @Test
    fun testCutter() {

        cldAssert("l_sample/fl_cutter.layer_apply", Transformation().cutter(layer))
        cldAssert(
            "l_sample/e_sepia/fl_cutter.layer_apply",
            Transformation().cutter(layer) { transformation(sepiaTransformation) })
    }

    @Test
    fun testAntiRemoval() {
        cldAssert("l_sample/e_anti_removal,fl_layer_apply", Transformation().antiRemoval(layer))
        cldAssert(
            "l_sample/e_sepia/e_anti_removal,fl_layer_apply",
            Transformation().antiRemoval(layer) {
                transformation(sepiaTransformation)
            })
    }

    @Test
    fun testCutout() {
        cldAssert("l_sample/e_cut_out,fl_layer_apply", Transformation().cutout(layer))
        cldAssert(
            "l_sample/e_cut_out,fl_layer_apply",
            Transformation().cutout(Cutout.Builder(layer).build())
        )
        cldAssert("l_sample/e_cut_out,fl_layer_apply", Transformation().cutout(layer) {
            position {
                allowOverflow(true)
            }
        })
    }

    @Test
    fun testClip() {
        cldAssert("fl_clip", Transformation().clip())
        cldAssert("fl_clip_evenodd", Transformation().clip { evenOdd(true) })
    }

    @Test
    fun testCustomFunction() {
        cldAssert("fn_wasm:wasm", Transformation().customFunction(wasm("wasm")))
    }

    @Test
    fun testRotate() {
        cldAssert("a_50", Transformation().rotate { angle(50) })
    }

    @Test
    fun testExtract() {
        cldAssert("pg_1", Transformation().extract(pages(1)))
    }

    @Test
    fun testBackground() {
        cldAssert("b_white", Transformation().background(Background.color(Color.WHITE)))
        cldAssert("b_white", Transformation().background(Color.WHITE))
    }

    @Test
    fun testOutline() {
        cldAssert("e_outline", Transformation().outline())
    }

    @Test
    fun testCornersRadius() {
        // TODO
//        cldAssert("r_3:56:7:8", Transformation().roundCorners {  pixels(3, 56, 7, 8) })
        cldAssert("r_max", Transformation().roundCorners(RoundCorners.max()))
    }

    @Test
    fun testGradientFade() {
        cldAssert("e_gradient_fade:3", Transformation().gradientFade { strength(3) })
        cldAssert("e_gradient_fade", Transformation().gradientFade())
    }

    @Test
    fun testBorder() {
        cldAssert(
            "bo_4px_solid_black",
            Transformation().border {
                width(4)
                color(Color.BLACK)
            })
    }

    @Test
    fun testDisplace() {
        cldAssert(
            "l_radialize/e_displace,fl_layer_apply",
            Transformation().displace(Displace.displace(image("radialize")))
        )

        cldAssert(
            "l_radialize/e_sepia/e_displace,fl_layer_apply",
            Transformation().displace(image("radialize")) { transformation(sepiaTransformation) })
    }

    @Test
    fun testStyleTransfer() {
        cldAssert(
            "l_lighthouse/e_style_transfer:preserve_color,fl_layer_apply",
            Transformation().styleTransfer(image("lighthouse")) { preserveColor(true) }
        )
    }

    @Test
    fun testEffect() {
        Transformation().effect(Effect.colorize { level(50) })
        cldAssert("e_sepia", Transformation().effect(Effect.sepia()))
    }

    @Test
    fun testResize() {
        cldAssert("c_scale,w_100", Transformation().resize(scale { width(100) }))
    }

    @Test
    fun testAdjust() {
        cldAssert("e_gamma:50", Transformation().adjust(Adjust.gamma(50)))
    }

    @Test
    fun testDelivery() {
        cldAssert("ac_mp3", Transformation().delivery(Delivery.audioCodec(AudioCodec.MP3)))
    }

    @Test
    fun testVideo() {
        // TODO
    }

    @Test
    fun testReshape() {
        cldAssert("e_distort:arc:10", Transformation().reshape(distortArc(10)))
    }

    @Test
    fun testLayer() {
        cldAssert("l_sample/fl_layer_apply", Transformation().overlay(layer))
    }

    @Test
    fun testDelay() {
        cldAssert("dl_20", Transformation().delay(20))
    }


    @Test
    fun textComplexTransformation() {
        val transformation =
            transformation {
                gradientFade { strength(3) }
                adjust(opacity(80))
                border(Border.solid {
                    width(4)
                    color(Color.RED)
                })
                overlay(image("sample")) {
                    position {
                        gravity(Gravity.east())
                        allowOverflow(false)
                    }
                    blendMode(SCREEN)
                    resize(scale {
                        width(100)
                    })
                }
                overlay(
                    text("hello world") {
                        fontSize(21)
                        fontFamily("Arial")
                        style {
                            fontWeight(FontWeight.BOLD)
                            fontHinting(FontHinting.FULL)
                            stroke(Stroke.STROKE)
                            letterSpacing(12f)
                        }
                        background(Color.RED)
                        textColor(Color.BLUE)
                    }
                ) {
                    position {
                        x(20)
                        gravity(Gravity.west())
                    }
                }
                rotate { angle(25) }
                fetchFormat(Format.png())
            }

        cldAssert(
            "e_gradient_fade:3/o_80/bo_4px_solid_red/l_sample/c_scale,w_100/" +
                    "e_screen,fl_layer_apply.no_overflow,g_east/" +
                    "b_red,co_blue,l_text:Arial_21_bold_hinting_full_stroke_letter_spacing_12.0:hello%20world/" +
                    "fl_layer_apply,g_west,x_20/a_25/f_png",
            transformation
        )
    }
}