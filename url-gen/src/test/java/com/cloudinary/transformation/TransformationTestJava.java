package com.cloudinary.transformation;

import org.junit.Test;

public class TransformationTestJava {
    @Test
    public void textComplexTransformation() {
        // TODO disable java tests until API is finalized.
//        Transformation t = new Transformation()
//                .gradientFade(new GradientFade.Builder().strength(3).build())
//                .adjust(new Opacity.Builder(80).build())
//                .border(new Border.Builder().width(4).color(new ColorValue.Builder().named("red").build()).build());
//
//        Transformation transformation =
//                new Transformation()
//                        .gradientFade(new GradientFade.Builder().strength(3).build())
//                        .adjust(new Opacity.Builder(80).build())
//                        .border(new Border.Builder().width(4).color(new ColorValue.Builder().named("red").build()).build())
//                        .layer(new LayerContainer.Builder(new MediaLayer.Builder("sample").build())
//                                .position(new Position.Builder()
//                                        .gravity(Companion.direction(Direction.EAST))
//                                        .allowOverflow(false).build())
//                                .blendMode(BlendMode.SCREEN)
//                                .transformation(new Transformation().resize(new Scale.Builder().width(100).build()))
//                                .param("overlay", "l")
//                                .build())
//                        .layer(new LayerContainer.Builder(
//                                new TextLayer("hello world", "Arial", 21, new TextStyle.Builder()
//                                        .fontWeight(FontWeight.BOLD)
//                                        .fontHinting(FontHinting.FULL)
//                                        .stroke(Stroke.STROKE)
//                                        .letterSpacing(12f)
//                                        .build(), null, null))
//                                .position(new Position.Builder()
//                                        .gravity(Companion.direction(Direction.WEST)).build())
//                                .param("overlay", "l")
//                                .build())
//                        .rotate(new Rotate.Builder().angle(25).build())
//                        .delivery(Delivery.Companion.format("png"));
//
//        cldAssert(
//                "e_gradient_fade:3/o_80/bo_4px_solid_red/l_sample/c_scale,w_100/" +
//                        "e_screen,fl_layer_apply.no_overflow,g_east/" +
//                        "l_text:Arial_21_bold_hinting_full_stroke_letter_spacing_12.0:hello%20world/" +
//                        "fl_layer_apply,g_west/a_25/f_png",
//                transformation);
    }
}
