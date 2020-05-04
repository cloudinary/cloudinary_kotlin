package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.testParam
import com.cloudinary.transformation.effect.*
import org.junit.Test

class EffectTest {
    @Test
    fun testNegate() {
        cldAssert("e_negate", Effect.negate())
    }

    @Test
    fun testSepia() {
        cldAssert("e_sepia", Effect.sepia())
        cldAssert("e_sepia:50", Effect.sepia(50))
        cldAssert("e_sepia:50", Effect.sepia { level(50) })
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
        cldAssert("e_assist_colorblind", Effect.assistColorblind())
        cldAssert("e_assist_colorblind:8", Effect.assistColorblind { strength(8) })
        cldAssert(
            "e_assist_colorblind:xray", Effect.assistColorblind { type(AssistColorBlindType.XRay()) })

        cldAssert(
            "e_assist_colorblind:xray",
            Effect.assistColorblind { type(AssistColorBlindType.XRay()).strength("\$var") })
        cldAssert(
            "e_assist_colorblind:\$var1",
            Effect.assistColorblind { type(AssistColorBlindType.Stripes("\$var1")).strength("\$var2") })
    }

    @Test
    fun testSimulateColorblind() {
        cldAssert("e_simulate_colorblind", Effect.simulateColorblind())
        cldAssert(
            "e_simulate_colorblind:deuteranopia",
            Effect.simulateColorblind { condition(ColorBlindCondition.DEUTERANOPIA) })
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
        cldAssert("e_cartoonify:30:bw", Effect.cartoonify { lineStrength(30).blackwhite(true) })
        cldAssert(
            "e_cartoonify:30:bw",
            Effect.cartoonify { lineStrength(30).colorReduction(60).blackwhite(true) })
        cldAssert("e_cartoonify:bw", Effect.cartoonify { colorReduction(60).blackwhite(true) })
    }

    @Test
    fun testArtistic() {
        cldAssert(
            "e_art:al_dente", Effect.artistic { filter(ArtisticFilter.AL_DENTE) })
    }

    @Test
    fun testMakeTransparent() {
        cldAssert("e_make_transparent", Effect.makeTransparent())
        cldAssert("e_make_transparent:50", Effect.makeTransparent { level(50) })
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
        cldAssert("e_oil_paint:40", Effect.oilPaint { level(40) })
    }

    @Test
    fun testRedEye() {
        cldAssert("e_redeye", Effect.redeye())
    }

    @Test
    fun testAdvRedEye() {
        cldAssert("e_adv_redeye", Effect.advRedeye())
    }

    @Test
    fun testVignette() {
        cldAssert("e_vignette", Effect.vignette())
        cldAssert("e_vignette:30", Effect.vignette(30))
        cldAssert("e_vignette:30", Effect.vignette { level(30) })
    }

    @Test
    fun testPixelate() {
        cldAssert("e_pixelate", Effect.pixelate())
        cldAssert("e_pixelate:3", Effect.pixelate { squareSize(3) })
    }

    @Test
    fun testPixelateRegion() {
        cldAssert(
            "e_pixelate_region:20,h_40,w_40,x_20,y_20",
            Effect.pixelateRegion { squareSize(20).region(Region(20, 20, 40, 40)) })
    }

    @Test
    fun testPixelateFaces() {
        cldAssert("e_pixelate_faces", Effect.pixelateFaces())
        cldAssert("e_pixelate_faces:7", Effect.pixelateFaces { squareSize(7) })
    }

    @Test
    fun testBlur() {
        cldAssert("e_blur", Effect.blur())
        cldAssert("e_blur:300", Effect.blur(300))
        cldAssert("e_blur:300", Effect.blur { level(300) })
    }

    @Test
    fun testBlurRegion() {
        cldAssert(
            "e_blur_region:200,h_40,w_40,x_10,y_20",
            Effect.blurRegion { strength(200).region(Region(10, 20, 40, 40)) })
    }

    @Test
    fun testBlurFaces() {
        cldAssert("e_blur_faces", Effect.blurFaces())
        cldAssert("e_blur_faces:600", Effect.blurFaces { strength(600) })
    }

    @Test
    fun testOrderedDither() {
        cldAssert(
            "e_ordered_dither:0",
            Effect.orderedDither { filter(DitherFilter.THRESHOLD_1X1_NON_DITHER) })
    }

    @Test
    fun testShadow() {
        cldAssert("e_shadow,test_param", Shadow.Builder().build().add(testParam))
        cldAssert("e_shadow", Effect.shadow())
        cldAssert("e_shadow:50", Effect.shadow { strength(50) })
        cldAssert(
            "co_green,e_shadow:50",
            Effect.shadow { strength(50).color(Color.GREEN) })
        cldAssert(
            "co_green,e_shadow:50,x_20,y_-20",
            Effect.shadow { strength(50).color(Color.GREEN).x(20).y(-20) }
        )
    }
}