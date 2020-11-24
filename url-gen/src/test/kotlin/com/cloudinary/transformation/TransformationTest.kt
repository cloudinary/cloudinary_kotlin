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
import com.cloudinary.transformation.effect.GradientFade.GradientFadeType
import com.cloudinary.transformation.expression.Expression
import com.cloudinary.transformation.gravity.Gravity.Companion.east
import com.cloudinary.transformation.gravity.Gravity.Companion.west
import com.cloudinary.transformation.layer.BlendMode
import com.cloudinary.transformation.layer.Overlay
import com.cloudinary.transformation.layer.Overlay.Companion.imageOnImage
import com.cloudinary.transformation.layer.Underlay
import com.cloudinary.transformation.layer.source.*
import com.cloudinary.transformation.layer.source.LayerSource.Companion.text
import com.cloudinary.transformation.reshape.Reshape.Companion.distortArc
import com.cloudinary.transformation.resize.Resize.Companion.scale
import com.cloudinary.transformation.transcode.AudioCodecType
import com.cloudinary.transformation.transcode.Transcode
import com.cloudinary.transformation.videoedit.VideoEdit
import org.junit.Test

class TransformationTest {
    private val imageSource = ImageSource("sample")

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
        cldAssert("l_sample/fl_cutter,fl_layer_apply", Transformation().cutter(imageSource))
        cldAssert(
            "l_sample/e_sepia/fl_cutter,fl_layer_apply",
            Transformation().cutter(imageSource) { transformation(sepiaTransformation) })
    }

    @Test
    fun testAntiRemoval() {
        cldAssert("l_sample/e_anti_removal,fl_layer_apply", Transformation().antiRemoval(imageSource))
        cldAssert(
            "l_sample/e_sepia/e_anti_removal,fl_layer_apply",
            Transformation().antiRemoval(imageSource) {
                transformation(sepiaTransformation)
            })
    }

    @Test
    fun testCutout() {
        cldAssert("l_sample/e_cut_out,fl_layer_apply", Transformation().cutout(imageSource))
        cldAssert(
            "l_sample/e_cut_out,fl_layer_apply",
            Transformation().cutout(Cutout.Builder(imageSource).build())
        )
        cldAssert("l_sample/e_cut_out,fl_layer_apply", Transformation().cutout(imageSource) {
            position {
                allowOverflow()
            }
        })
    }

    @Test
    fun testCustomFunction() {
        cldAssert("fn_wasm:wasm", Transformation().customFunction(wasm("wasm")))
    }

    @Test
    fun testRotate() {
        cldAssert("a_50", Transformation().rotate(Rotate.byAngle(50)))
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
        cldAssert("e_gradient_fade", Effect.gradientFade())
        cldAssert("e_gradient_fade:40,y_0.8", Effect.gradientFade {
            strength(40)
            y(0.8)
        })
        cldAssert("e_gradient_fade:symmetric", Effect.gradientFade {
            type(GradientFadeType.SYMMETRIC)
        })
        cldAssert("e_gradient_fade:symmetric_pad:50,x_0.2", Effect.gradientFade {
            strength(50)
            type(GradientFadeType.SYMMETRIC_PAD)
            x(0.2)
        })
        cldAssert("e_gradient_fade:symmetric:25,x_0.15,y_0.15", Effect.gradientFade {
            strength(25)
            type(GradientFadeType.SYMMETRIC)
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
            Transformation().displace(LayerSource.image("radialize"))
        )
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
        cldAssert("ac_mp3", Transformation().transcode(Transcode.audioCodec(AudioCodecType.MP3)))
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
        cldAssert("l_sample/fl_layer_apply", Transformation().overlay(imageOnImage(imageSource)))
        cldAssert("u_sample/fl_layer_apply", Transformation().underlay(Underlay.image(imageSource)))
    }

    @Test
    fun test3DLut() {
        cldAssert("l_lut:iwltbap_aspen.3dl", Transformation().add3dLut("iwltbap_aspen.3dl"))
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
                overlay(imageOnImage("sample") {
                    transformation {
                        resize(scale {
                            width(100)
                        })
                    }
                    position {
                        gravity(east())
                        allowOverflow(false)
                    }
                    blendMode(BlendMode.screen())
                })
                overlay(Overlay.textOnImage(
                    text("hello world") {
                        style {
                            fontSize(21)
                            fontFamily("Arial")
                            fontWeight(FontWeight.BOLD)
                            fontHinting(FontHinting.FULL)
                            stroke(Stroke.STROKE)
                            letterSpacing(12f)
                        }
                        backgroundColor(Color.RED)
                        textColor(Color.BLUE)
                    }) {
                    position {
                        x(20)
                        gravity(west())
                    }
                })
                rotate(Rotate.byAngle(25))
                delivery(Delivery.format(FormatType.png()))
            }

        cldAssert(
            "e_gradient_fade:3/o_80/bo_4px_solid_red/l_sample/c_scale,w_100/" +
                    "e_screen,fl_layer_apply,fl_no_overflow,g_east/" +
                    "b_red,co_blue,l_text:Arial_21_bold_hinting_full_stroke_letter_spacing_12.0:hello world/" +
                    "fl_layer_apply,g_west,x_20/a_25/f_png",
            transformation
        )
    }
}