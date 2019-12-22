package com.cloudinary.transformation

import com.cloudinary.cldAssertEqualsAsString
import com.cloudinary.transformation.effect.*
import org.junit.Test

class EffectTest {
    @Test
    fun testNegate() {
        cldAssertEqualsAsString("e_negate", Effect.negate())
    }

    @Test
    fun testSepia() {
        cldAssertEqualsAsString("e_sepia", Effect.sepia())
        cldAssertEqualsAsString("e_sepia:50", Effect.sepia { level(50) })
        cldAssertEqualsAsString("e_sepia:50", Effect.sepia { level(50) })
    }

    @Test
    fun testGrayScale() {
        cldAssertEqualsAsString("e_grayscale", Effect.grayScale())
    }

    @Test
    fun testBlackWhite() {
        cldAssertEqualsAsString("e_blackwhite", Effect.blackWhite())
    }

    @Test
    fun testColorize() {
        Effect.colorize()
        cldAssertEqualsAsString("e_colorize", Effect.colorize())
        cldAssertEqualsAsString("e_colorize:80", Effect.colorize { level(80) })
        cldAssertEqualsAsString(
            "co_blue,e_colorize:80",
            Effect.colorize { level(80).color(color { named("blue") }) })
    }

    @Test
    fun testAssistColorBlind() {
        cldAssertEqualsAsString("e_assist_colorblind", Effect.assistColorBlind())
        cldAssertEqualsAsString("e_assist_colorblind:8", Effect.assistColorBlind { strength(8) })
        cldAssertEqualsAsString(
            "e_assist_colorblind:xray", Effect.assistColorBlind { type(AssistColorBlindType.XRay()) })

        cldAssertEqualsAsString(
            "e_assist_colorblind:xray",
            Effect.assistColorBlind { type(AssistColorBlindType.XRay()).strength("\$var") })
        cldAssertEqualsAsString(
            "e_assist_colorblind:\$var1",
            Effect.assistColorBlind { type(AssistColorBlindType.Stripes("\$var1")).strength("\$var2") })
    }

    @Test
    fun testSimulateColorblind() {
        cldAssertEqualsAsString("e_simulate_colorblind", Effect.simulateColorblind())
        cldAssertEqualsAsString(
            "e_simulate_colorblind:deuteranopia",
            Effect.simulateColorblind { condition(ColorBlindCondition.DEUTERANOPIA) })
    }

    @Test
    fun testVectorize() {
        cldAssertEqualsAsString("e_vectorize", Effect.vectorize())
        cldAssertEqualsAsString(
            "e_vectorize:colors:15:detail:2:despeckle:0.5:paths:4:corners:5",
            Effect.vectorize { colors(15).detail(2).despeckle(0.5f).paths(4).corners(5) })
    }

    @Test
    fun testCartoonify() {
        cldAssertEqualsAsString("e_cartoonify", Effect.cartoonify())
        cldAssertEqualsAsString("e_cartoonify:20", Effect.cartoonify { lineStrength(20) })
        cldAssertEqualsAsString("e_cartoonify:20:60", Effect.cartoonify { lineStrength(20).colorReduction(60) })
        cldAssertEqualsAsString("e_cartoonify:30:bw", Effect.cartoonify { lineStrength(30).blackwhite(true) })
        cldAssertEqualsAsString(
            "e_cartoonify:30:bw",
            Effect.cartoonify { lineStrength(30).colorReduction(60).blackwhite(true) })
        cldAssertEqualsAsString("e_cartoonify:bw", Effect.cartoonify { colorReduction(60).blackwhite(true) })
    }

    @Test
    fun testArtistic() {
        cldAssertEqualsAsString(
            "e_art:al_dente", Effect.artistic { filter(ArtisticFilter.AL_DENTE) })
    }

    @Test
    fun testMakeTransparent() {
        cldAssertEqualsAsString("e_make_transparent", Effect.makeTransparent())
        cldAssertEqualsAsString("e_make_transparent:50", Effect.makeTransparent { level(50) })
    }

    @Test
    fun testTrim() {
        cldAssertEqualsAsString("e_trim", Effect.trim())
        cldAssertEqualsAsString("e_trim:30", Effect.trim { colorSimilarity(30) })
        cldAssertEqualsAsString("e_trim:white", Effect.trim { colorOverride(color { named("white") }) })
        cldAssertEqualsAsString(
            "e_trim:30:white",
            Effect.trim { colorSimilarity(30).colorOverride(color { named("white") }) })
    }

    @Test
    fun testOilPaint() {
        cldAssertEqualsAsString("e_oil_paint", Effect.oilPaint())
        cldAssertEqualsAsString("e_oil_paint:40", Effect.oilPaint { level(40) })
    }

    @Test
    fun testRedEye() {
        cldAssertEqualsAsString("e_redeye", Effect.redEye())
    }

    @Test
    fun testAdvRedEye() {
        cldAssertEqualsAsString("e_adv_redeye", Effect.advRedEye())
    }

    @Test
    fun testVignette() {
        cldAssertEqualsAsString("e_vignette", Effect.vignette())
        cldAssertEqualsAsString("e_vignette:30", Effect.vignette { level(30) })
    }

    @Test
    fun testPixelate() {
        cldAssertEqualsAsString("e_pixelate", Effect.pixelate())
        cldAssertEqualsAsString("e_pixelate:3", Effect.pixelate { squareSize(3) })
    }

    @Test
    fun testPixelateRegion() {
        cldAssertEqualsAsString(
            "e_pixelate_region:20,h_40,w_40,x_20,y_20",
            Effect.pixelateRegion { squareSize(20).region(Region(20, 20, 40, 40)) })
    }

    @Test
    fun testPixelateFaces() {
        cldAssertEqualsAsString("e_pixelate_faces", Effect.pixelateFaces())
        cldAssertEqualsAsString("e_pixelate_faces:7", Effect.pixelateFaces { squareSize(7) })
    }

    @Test
    fun testBlur() {
        cldAssertEqualsAsString("e_blur", Effect.blur())
        cldAssertEqualsAsString("e_blur:300", Effect.blur { strength(300) })
    }

    @Test
    fun testBlurRegion() {
        cldAssertEqualsAsString(
            "e_blur_region:200,h_40,w_40,x_10,y_20",
            Effect.blurRegion { strength(200).region(Region(10, 20, 40, 40)) })
    }

    @Test
    fun testBlurFaces() {
        cldAssertEqualsAsString("e_blur_faces", Effect.blurFaces())
        cldAssertEqualsAsString("e_blur_faces:600", Effect.blurFaces { strength(600) })
    }

    @Test
    fun testOrderedDither() {
        cldAssertEqualsAsString(
            "e_ordered_dither:0",
            Effect.orderedDither { filter(DitherFilter.THRESHOLD_1X1_NON_DITHER) })
    }
}