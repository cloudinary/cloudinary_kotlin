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
import com.cloudinary.transformation.expression.Expression
import com.cloudinary.transformation.expression.Variable
import com.cloudinary.transformation.gravity.Gravity.Companion.east
import com.cloudinary.transformation.gravity.Gravity.Companion.west
import com.cloudinary.transformation.layer.BlendMode
import com.cloudinary.transformation.layer.Overlay
import com.cloudinary.transformation.layer.Overlay.Companion.source
import com.cloudinary.transformation.layer.Underlay
import com.cloudinary.transformation.layer.source.FontHinting
import com.cloudinary.transformation.layer.source.FontWeight
import com.cloudinary.transformation.layer.source.ImageSource
import com.cloudinary.transformation.layer.source.Source.Companion.image
import com.cloudinary.transformation.layer.source.Source.Companion.text
import com.cloudinary.transformation.reshape.Reshape.Companion.distortArc
import com.cloudinary.transformation.resize.Resize.Companion.scale
import com.cloudinary.transformation.transcode.AudioCodec
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
            verticalStartPoint(0.8)
        })
        cldAssert("e_gradient_fade:symmetric", Effect.gradientFade {
            type(GradientFade.symmetric())
        })
        cldAssert("e_gradient_fade:symmetric_pad:50,x_0.2", Effect.gradientFade {
            strength(50)
            type(GradientFade.symmetricPad())
            horizontalStartPoint(0.2)
        })
        cldAssert("e_gradient_fade:symmetric:25,x_0.15,y_0.15", Effect.gradientFade {
            strength(25)
            type(GradientFade.symmetric())
            verticalStartPoint(0.15)
            horizontalStartPoint(0.15)
        })
    }

    @Test
    fun testDisplace() {
        cldAssert(
            "l_radialize/e_displace,fl_layer_apply,x_50",
            Transformation().displace(Displace.image {
                source("radialize")
                x(50)
            })
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
        cldAssert("e_gamma:50", Transformation().adjust(Adjust.gamma {
            level(50)
        }))
    }

    @Test
    fun testTranscode() {
        cldAssert("ac_mp3", Transformation().transcode(Transcode.audioCodec(AudioCodec.mp3())))
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
        cldAssert("l_sample/fl_layer_apply", Transformation().overlay(source(imageSource)))
        cldAssert("u_sample/fl_layer_apply", Transformation().underlay(Underlay.source(imageSource)))
    }

    @Test
    fun testPrefix() {
        cldAssert("p_myprefix", Transformation().prefix("myprefix"))
    }

    @Test
    fun testNamedTransformation() {
        cldAssert("t_named", Transformation().namedTransformation("named"))
        cldAssert("t_named", Transformation().namedTransformation(NamedTransformation.Companion.name("named")))
    }

    @Test
    fun testVariable() {
        // TODO known issue
//        cldAssert(
//            "\$mainvideowidth_500/c_scale,w_\$mainvideowidth",
//            Transformation {
//                addVariable(Variable.set("mainvideo_width", 500))
//                resize(scale {
//                    width(Expression.expression("\$mainvideo_width"))
//                })
//            })
        cldAssert(
            "\$myvar_cp_gt_10/\$myvar2_20/\$myvar3_30_to_f/\$myvar4_40_to_i/c_scale,w_\$myvar",
            Transformation()
                .addVariable("myvar", Expression.currentPage().gt(10))
                .addVariable("myvar2", 20)
                .addVariable(Variable.set("myvar3", 30) {
                    asFloat()
                })
                .addVariable(Variable.set("myvar4", 40) {
                    asInteger()
                })
                .resize(scale { width("\$myvar") })
        )

        cldAssert(
            "\$myvar_cp_gt_10/\$myvar2_20/\$myvar3_30_to_f/\$myvar4_40_to_i/c_scale,w_\$myvar",
            Transformation()
                .addVariable("myvar", Expression.currentPage().gt(10))
                .addVariable("myvar2", 20)
                .addVariable(Variable.set("myvar3", 30) {
                    asFloat()
                })
                .addVariable(Variable.set("myvar4", 40) {
                    asInteger()
                })
                .resize(scale { width("\$myvar") })


        )

        cldAssert(
            "\$myvar_ref:!public_id!",
            Transformation()
                .addVariable(Variable.setAssetReference("myvar", "public_id"))
        )

        cldAssert(
            "\$myvar_ctx:!public_id!",
            Transformation()
                .addVariable(Variable.setFromContext("myvar", "public_id"))
        )

        cldAssert(
            "\$myvar_ctx:!public_id!_to_f",
            Transformation()
                .addVariable(Variable.setFromContext("myvar", "public_id") {
                    asFloat()
                })
        )

        cldAssert(
            "\$myvar_md:!public_id!",
            Transformation()
                .addVariable(Variable.setFromMetadata("myvar", "public_id"))
        )

        cldAssert(
            "\$myvar_md:!public_id!_to_f",
            Transformation()
                .addVariable(Variable.setFromMetadata("myvar", "public_id") {
                    asFloat()
                })
        )

    }

    @Test
    fun testAddFlag() {
        cldAssert("fl_layer_apply", Transformation().addFlag(Flag.layerApply()))
    }

    @Test
    fun testAntiRemovalWithLevel() {
        val transformation =
            transformation {
                overlay(Overlay.image {
                    source("sample") {

                    }.blendMode(BlendMode.antiRemoval { level(15) })
                })

            }
        cldAssert("l_sample/e_anti_removal:15,fl_layer_apply",transformation)
    }

    @Test
    fun testAntiRemovalWithoutLevel() {
        val transformation =
            transformation {
                overlay(Overlay.image {
                    source("sample") {

                    }.blendMode(BlendMode.antiRemoval())
                })

            }
        cldAssert("l_sample/e_anti_removal,fl_layer_apply",transformation)
    }


    @Test
    fun textComplexTransformation() {
        val transformation =
            transformation {
                effect(Effect.gradientFade { strength(3) })
                adjust(opacity(80))
                border(Border.solid(4, Color.RED))
                overlay(Overlay.image {
                    source("sample") {
                        transformation {
                            resize(scale {
                                width(100)
                            })

                        }
                    }
                    position {
                        gravity(east())
                        allowOverflow(false)
                    }
                    blendMode(BlendMode.screen())
                })
                overlay(
                    source(
                        text("hello world") {
                            style("Arial", 21) {
                                fontWeight(FontWeight.bold())
                                fontHinting(FontHinting.full())
                                stroke()
                                letterSpacing(12f)
                            }
                            backgroundColor(Color.RED)
                            textColor(Color.BLUE)
                        }) {
                        position {
                            offsetX(20)
                            gravity(west())
                        }
                    })
                rotate(Rotate.byAngle(25))
                delivery(Delivery.format(Format.png()))
            }

        cldAssert(
            "e_gradient_fade:3/o_80/bo_4px_solid_red/l_sample/c_scale,w_100/" +
                    "e_screen,fl_layer_apply,fl_no_overflow,g_east/" +
                    "b_red,co_blue,l_text:Arial_21_bold_hinting_full_letter_spacing_12.0_stroke:hello world/" +
                    "fl_layer_apply,g_west,x_20/a_25/f_png",
            transformation
        )
    }
}