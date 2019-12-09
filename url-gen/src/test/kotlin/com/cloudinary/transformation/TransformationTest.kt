package com.cloudinary.transformation

import com.cloudinary.cldAssertEqualsAsString
import com.cloudinary.transformation.Direction.EAST
import com.cloudinary.transformation.Direction.WEST
import com.cloudinary.transformation.adjust.Adjust
import com.cloudinary.transformation.adjust.Adjust.Companion.opacity
import com.cloudinary.transformation.delivery.AudioCodecType
import com.cloudinary.transformation.delivery.Delivery
import com.cloudinary.transformation.delivery.Delivery.Companion.format
import com.cloudinary.transformation.effect.Effect
import com.cloudinary.transformation.layer.BlendMode.SCREEN
import com.cloudinary.transformation.layer.FontHinting
import com.cloudinary.transformation.layer.FontWeight
import com.cloudinary.transformation.layer.Layer.Companion.overlay
import com.cloudinary.transformation.layer.LayerSource
import com.cloudinary.transformation.layer.LayerSource.Companion.text
import com.cloudinary.transformation.layer.Stroke
import com.cloudinary.transformation.resize.Resize.Companion.scale
import com.cloudinary.transformation.video.Video
import com.cloudinary.transformation.warp.Warp.Companion.distort
import org.junit.Test

class TransformationTest {
    private val layer = LayerSource.media("sample")
    private val sepiaTransformation = Transformation().effect(Effect.sepia())
    @Test
    fun testCutter() {
        cldAssertEqualsAsString("l_sample/fl_cutter.layer_apply", Transformation().cutter(layer))
        cldAssertEqualsAsString(
            "l_sample/e_sepia/fl_cutter.layer_apply",
            Transformation().cutter(layer) { setTransformation(sepiaTransformation) })
    }

    @Test
    fun testAntiRemoval() {
        cldAssertEqualsAsString("l_sample/e_anti_removal,fl_layer_apply", Transformation().antiRemoval(layer))
        cldAssertEqualsAsString(
            "l_sample/e_sepia/e_anti_removal,fl_layer_apply",
            Transformation().antiRemoval(layer) { setTransformation(sepiaTransformation) })

    }

    @Test
    fun testCutout() {
        cldAssertEqualsAsString("l_sample/e_cut_out,fl_layer_apply", Transformation().cutout(layer))
        cldAssertEqualsAsString(
            "l_sample/e_cut_out,fl_layer_apply",
            Transformation().cutout(Cutout.Builder(layer).build())
        )
        cldAssertEqualsAsString("l_sample/e_cut_out,fl_layer_apply", Transformation().cutout(layer) {
            setPosition {
                setAllowOverflow(true)
            }
        })
    }

    @Test
    fun testClip() {
        cldAssertEqualsAsString("fl_clip", Transformation().clip())
        cldAssertEqualsAsString("fl_clip_evenodd", Transformation().clip { setEvenOdd(true) })
    }

    @Test
    fun testCustomFunction() {
        cldAssertEqualsAsString("fn_wasm:wasm", Transformation().customFunction(wasm("wasm")))
    }

    @Test
    fun testRotate() {
        cldAssertEqualsAsString("a_50", Transformation().rotate { angle(50) })
    }

    @Test
    fun testExtract() {
        cldAssertEqualsAsString("pg_1", Transformation().extract(pages(1)))
    }

    @Test
    fun testBackground() {
        cldAssertEqualsAsString("b_white", Transformation().background(Background.color { named("white") }))
        cldAssertEqualsAsString("b_white", Transformation().background(color { named("white") }))
    }

    @Test
    fun testOutline() {
        cldAssertEqualsAsString("e_outline", Transformation().outline())
    }

    @Test
    fun testShadow() {
        cldAssertEqualsAsString("e_shadow", Transformation().shadow())
    }

    @Test
    fun testCornersRadius() {
        cldAssertEqualsAsString("r_3:56:7:8", Transformation().cornersRadius { pixels(3, 56, 7, 8) })
        cldAssertEqualsAsString("r_max", Transformation().cornersRadius { max() })
    }

    @Test
    fun testGradientFade() {
        cldAssertEqualsAsString("e_gradient_fade:3", Transformation().gradientFade { setStrength(3) })
        cldAssertEqualsAsString("e_gradient_fade", Transformation().gradientFade())
    }

    @Test
    fun testBorder() {
        cldAssertEqualsAsString(
            "bo_4px_solid_black",
            Transformation().border {
                setWidth(4)
                setColor {
                    named("black")
                }
            })
    }

    @Test
    fun testDisplace() {
        cldAssertEqualsAsString(
            "l_radialize/e_displace,fl_layer_apply",
            Transformation().displace(Displace.displace(LayerSource.media("radialize")))
        )

        cldAssertEqualsAsString(
            "l_radialize/e_sepia/e_displace,fl_layer_apply",
            Transformation().displace(LayerSource.media("radialize")) { setTransformation(sepiaTransformation) })
    }

    @Test
    fun testStyleTransfer() {
        cldAssertEqualsAsString(
            "l_lighthouse/e_style_transfer:preserve_color,fl_layer_apply",
            Transformation().styleTransfer(LayerSource.media("lighthouse")) { setPreserveColor(true) }
        )
    }

    @Test
    fun testEffect() {
        Transformation().effect(Effect.colorize { setLevel(50) })
        cldAssertEqualsAsString("e_sepia", Transformation().effect(Effect.sepia()))
    }

    @Test
    fun testResize() {
        cldAssertEqualsAsString("c_scale,w_100", Transformation().resize(scale { setWidth(100) }))
    }

    @Test
    fun testAdjust() {
        cldAssertEqualsAsString("e_gamma:50", Transformation().adjust(Adjust.gamma { setLevel(50) }))
    }

    @Test
    fun testDelivery() {
        cldAssertEqualsAsString("ac_mp3", Transformation().delivery(Delivery.audioCodec(AudioCodecType.MP3)))
    }

    @Test
    fun testVideo() {
        cldAssertEqualsAsString("e_boomerang", Transformation().video(Video.boomerang()))
    }

    @Test
    fun testWarp() {
        cldAssertEqualsAsString("e_distort:arc:10", Transformation().warp(distort(10)))
    }

    @Test
    fun testLayer() {
        cldAssertEqualsAsString("l_sample/fl_layer_apply", Transformation().layer(overlay(layer)))
    }

    @Test
    fun textComplexTransformation() {
        val transformation =
            transformation {
                gradientFade { setStrength(3) }
                adjust(opacity(80))
                border {
                    setWidth(4)
                    setColor {
                        named("red")
                    }
                }
                layer(overlay(layer) {
                    setPosition {
                        setGravity(Gravity.direction(EAST))
                        setAllowOverflow(false)
                    }
                    setBlendMode(SCREEN)
                    setTransformation {
                        resize(scale {
                            setWidth(100)
                        })
                    }
                })
                layer(overlay(
                    text("hello world", "Arial", 21) {
                        setFontWeight(FontWeight.BOLD)
                        setFontHinting(FontHinting.FULL)
                        setStroke(Stroke.STROKE)
                        setLetterSpacing(12f)
                    }
                ) {
                    setPosition {
                        setGravity(Gravity.direction(WEST))
                    }
                })
                rotate { angle(25) }
                delivery(format("png"))
            }

        cldAssertEqualsAsString(
            "e_gradient_fade:3/o_80/bo_4px_solid_red/l_sample/c_scale,w_100/" +
                    "e_screen,fl_layer_apply.no_overflow,g_east/" +
                    "l_text:Arial_21_bold_hinting_full_stroke_letter_spacing_12.0:hello%20world/" +
                    "fl_layer_apply,g_west/a_25/f_png",
            transformation
        )
    }
}