package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.BackgroundColor.Companion.color
import com.cloudinary.transformation.CustomFunction.Companion.wasm
import com.cloudinary.transformation.Transformation.Companion.transformation
import com.cloudinary.transformation.adjust.Adjust
import com.cloudinary.transformation.adjust.Adjust.Companion.opacity
import com.cloudinary.transformation.delivery.Delivery
import com.cloudinary.transformation.effect.Effect
import com.cloudinary.transformation.effect.Effect.Companion.sepia
import com.cloudinary.transformation.effect.GradientFade
import com.cloudinary.transformation.gravity.Gravity.Companion.east
import com.cloudinary.transformation.gravity.Gravity.Companion.west
import com.cloudinary.transformation.layer.BlendMode.Companion.screen
import com.cloudinary.transformation.layer.FontHinting
import com.cloudinary.transformation.layer.FontWeight
import com.cloudinary.transformation.layer.Source.Companion.image
import com.cloudinary.transformation.layer.Source.Companion.text
import com.cloudinary.transformation.layer.Stroke
import com.cloudinary.transformation.reshape.Reshape.Companion.distortArc
import com.cloudinary.transformation.resize.Resize.Companion.scale
import com.cloudinary.transformation.transcode.AudioCodec
import com.cloudinary.transformation.transcode.Transcode
import com.cloudinary.transformation.videoedit.VideoEdit
import org.junit.Test

class TransformationTest {
    private val layer = image("sample")

    private val sepiaTransformation = Transformation().effect(sepia())

    @Test
    fun testAddAction() {
        cldAssert("custom_action/e_sepia/another_custom",
            transformation {
                add("custom_action")
                effect(sepia())
                add(RawAction("another_custom"))
            })
    }

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
                allowOverflow()
            }
        })
    }

    @Test
    fun testClip() {
        cldAssert("fl_clip", Transformation().clip())
        cldAssert("fl_clip_evenodd", Transformation().clip {
            evenOdd()
        })

        cldAssert("fl_clip,pg_3", Transformation().clip(3))
        cldAssert("fl_clip,pg_name:my_path", Transformation().clip("my_path"))
        cldAssert("fl_clip_evenodd,pg_3", Transformation().clip(3) {
            evenOdd()
        })

        cldAssert("fl_clip_evenodd,pg_name:my_path", Transformation().clip("my_path") {
            evenOdd()
        })
    }

    @Test
    fun testCustomFunction() {
        cldAssert("fn_wasm:wasm", Transformation().customFunction(wasm("wasm")))
    }

    @Test
    fun testRotate() {
        cldAssert("a_50", Transformation().rotate(Rotate.angle(50)))
    }

    @Test
    fun testGetPage() {
        cldAssert("pg_1", Transformation().getPage(1))
    }

    @Test
    fun testGetSmartObject() {
        cldAssert("pg_embedded:3", Transformation().getSmartObject(SmartObject.number(3)))
    }

    @Test
    fun testGetPsdLayer() {
        cldAssert("pg_name:abc", Transformation().getLayer(PsdLayer.names("abc")))
    }

    @Test
    fun testBackground() {
        cldAssert("b_white", Transformation().backgroundColor(color(Color.WHITE)))
        cldAssert("b_white", Transformation().backgroundColor(Color.WHITE))
    }

    @Test
    fun testRoundCorners() {
        cldAssert("r_max", Transformation().roundCorners(RoundCorners.max()))
    }

    @Test
    fun testGradientFade() {
        cldAssert("e_gradient_fade:3", Effect.gradientFade { strength(3) })
        cldAssert("e_gradient_fade:3", Effect.gradientFade(3))
        cldAssert("e_gradient_fade", Effect.gradientFade())
        cldAssert("e_gradient_fade:40,y_0.8", Effect.gradientFade(40) {
            y(0.8)
        })
        cldAssert("e_gradient_fade:symmetric", Effect.gradientFade {
            type(GradientFade.SYMMETRIC)
        })
        cldAssert("e_gradient_fade:symmetric_pad", Effect.gradientFade {
            type(GradientFade.SYMMETRIC_PAD)
        })
        cldAssert("e_gradient_fade:symmetric_pad:50,x_0.2", Effect.gradientFade(50) {
            type(GradientFade.SYMMETRIC_PAD)
            x(0.2)
        })
        cldAssert("e_gradient_fade:symmetric:25,x_0.15,y_0.15", Effect.gradientFade(25) {
            type(GradientFade.SYMMETRIC)
            y(0.15)
            x(0.15)
        })
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
    fun testEffect() {
        Transformation().effect(Effect.colorize { level(50) })
        cldAssert("e_sepia", Transformation().effect(sepia()))
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
    fun testTranscode() {
        cldAssert("ac_mp3", Transformation().transcode(Transcode.audioCodec(AudioCodec.MP3)))
    }

    @Test
    fun testDelivery() {
        cldAssert("d_sample", Transformation().delivery(Delivery.defaultImage("sample")))
    }

    @Test
    fun testVideoEdit() {
        cldAssert("e_volume:3", Transformation().videoEdit(VideoEdit.volume(3)))
    }

    @Test
    fun testReshape() {
        cldAssert("e_distort:arc:10", Transformation().reshape(distortArc(10)))
    }

    @Test
    fun testLayer() {
        cldAssert("l_sample/fl_layer_apply", Transformation().overlay(layer))
        cldAssert("u_sample/fl_layer_apply", Transformation().underlay(layer))
    }

    @Test
    fun testQuality() {
        cldAssert("q_auto", Transformation().quality(Quality.auto()))
    }

    @Test
    fun test3DLut() {
        cldAssert("l_lut:iwltbap_aspen.3dl/fl_layer_apply", Transformation().add3dLut("iwltbap_aspen.3dl"))
    }

    @Test
    fun testPrefix() {
        cldAssert("p_myprefix", Transformation().prefix("myprefix"))
    }

    @Test
    fun testNamedTransformation() {
        cldAssert("t_named", Transformation().namedTransformation("named"))
    }

    @Test
    fun testFormat() {
        cldAssert("f_gif", Transformation().format(Format.gif()))
    }

    @Test
    fun testVariable() {
        cldAssert(
            "\$myvar_cp_gt_10/c_scale,w_\$myvar",
            Transformation()
                .variable("myvar", Expression.currentPage().gt(10))
                .resize(scale { width("\$myvar") })
        )
    }

    @Test
    fun testAddFlag() {
        cldAssert("fl_layer_apply", Transformation().addFlag(Flag.layerApply()))
    }

    @Test
    fun textComplexTransformation() {
        val transformation =
            transformation {
                effect(Effect.gradientFade { strength(3) })
                adjust(opacity(80))
                border {
                    width(4)
                    color(Color.RED)
                }
                overlay(image("sample")) {
                    transformation {
                        resize(scale {
                            width(100)
                        })
                    }
                    position {
                        gravity(east())
                        allowOverflow(false)
                    }
                    blendMode(screen())
                }
                overlay(text("hello world") {
                    fontSize(21)
                    fontFamily("Arial")
                    style {
                        fontWeight(FontWeight.BOLD)
                        fontHinting(FontHinting.FULL)
                        stroke(Stroke.STROKE)
                        letterSpacing(12f)
                    }
                    background(Color.RED)
                    color(Color.BLUE)
                }) {
                    position {
                        x(20)
                        gravity(west())
                    }
                }

                rotate(Rotate.angle(25))
                format(Format.png())
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