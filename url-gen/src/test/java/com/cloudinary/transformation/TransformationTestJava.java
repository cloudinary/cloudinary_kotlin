package com.cloudinary.transformation;

import com.cloudinary.transformation.adjust.Opacity;
import com.cloudinary.transformation.delivery.Delivery;
import com.cloudinary.transformation.layer.*;
import com.cloudinary.transformation.resize.Scale;
import org.junit.Test;

import static com.cloudinary.TestUtilsKt.cldAssertEqualsAsString;
import static com.cloudinary.transformation.Gravity.Companion;

public class TransformationTestJava {
    @Test
    public void textComplexTransformation() {
        Transformation transformation =
                new Transformation()
                        .gradientFade(new GradientFade.Builder().setStrength(3).build())
                        .adjust(new Opacity.Builder(80).build())
                        .border(new Border.Builder().setWidth(4).setColor(new ColorValue.Builder().named("red").build()).build())
                        .layer(new Layer.Builder(new MediaLayerSource.Builder("sample").build())
                                .setPosition(new Position.Builder()
                                        .setGravity(Companion.direction(Direction.EAST))
                                        .setAllowOverflow(false).build())
                                .setBlendMode(BlendMode.SCREEN)
                                .setTransformation(new Transformation().resize(new Scale.Builder().setWidth(100).build()))
                                .setParam("overlay", "l")
                                .build())
                        .layer(new Layer.Builder(
                                new TextLayerSource.Builder("hello world", "Arial", 21)
                                        .setFontWeight(FontWeight.BOLD)
                                        .setFontHinting(FontHinting.FULL)
                                        .setStroke(Stroke.STROKE)
                                        .setLetterSpacing(12f)
                                        .build())
                                .setPosition(new Position.Builder()
                                        .setGravity(Companion.direction(Direction.WEST)).build())
                                .setParam("overlay", "l")
                                .build())
                        .rotate(new Rotate.Builder().angle(25).build())
                        .delivery(Delivery.Companion.format("png"));

        cldAssertEqualsAsString(
                "e_gradient_fade:3/o_80/bo_4px_solid_red/l_sample/c_scale,w_100/" +
                        "e_screen,fl_layer_apply.no_overflow,g_east/" +
                        "l_text:Arial_21_bold_hinting_full_stroke_letter_spacing_12.0:hello%20world/" +
                        "fl_layer_apply,g_west/a_25/f_png",
                transformation);
    }
}
