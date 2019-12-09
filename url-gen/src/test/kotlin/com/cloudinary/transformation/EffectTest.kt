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
        cldAssertEqualsAsString("e_sepia:50", Effect.sepia { setLevel(50) })
        cldAssertEqualsAsString("e_sepia:50", Effect.sepia { setLevel(50) })
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
        cldAssertEqualsAsString("e_colorize:80", Effect.colorize { setLevel(80) })
        cldAssertEqualsAsString(
            "co_blue,e_colorize:80",
            Effect.colorize { setLevel(80).setColor(color { named("blue") }) })
    }

    @Test
    fun testAssistColorBlind() {
        cldAssertEqualsAsString("e_assist_colorblind", Effect.assistColorBlind())
        cldAssertEqualsAsString("e_assist_colorblind:8", Effect.assistColorBlind { setStrength(8) })
        cldAssertEqualsAsString(
            "e_assist_colorblind:xray", Effect.assistColorBlind { setType(AssistColorBlindType.XRay()) })

        cldAssertEqualsAsString(
            "e_assist_colorblind:xray",
            Effect.assistColorBlind { setType(AssistColorBlindType.XRay()).setStrength("\$var") })
        cldAssertEqualsAsString(
            "e_assist_colorblind:\$var1",
            Effect.assistColorBlind { setType(AssistColorBlindType.Stripes("\$var1")).setStrength("\$var2") })
    }

    @Test
    fun testSimulateColorblind() {
        cldAssertEqualsAsString("e_simulate_colorblind", Effect.simulateColorblind())
        cldAssertEqualsAsString(
            "e_simulate_colorblind:deuteranopia",
            Effect.simulateColorblind { setCondition(ColorBlindCondition.DEUTERANOPIA) })
    }

    @Test
    fun testVectorize() {
        cldAssertEqualsAsString("e_vectorize", Effect.vectorize())
        cldAssertEqualsAsString(
            "e_vectorize:colors:15:detail:2:despeckle:0.5:paths:4:corners:5",
            Effect.vectorize { setColors(15).setDetail(2).setDespeckle(0.5f).setPaths(4).setCorners(5) })
    }

    @Test
    fun testCartoonify() {
        cldAssertEqualsAsString("e_cartoonify", Effect.cartoonify())
        cldAssertEqualsAsString("e_cartoonify:20", Effect.cartoonify { setLineStrength(20) })
        cldAssertEqualsAsString("e_cartoonify:20:60", Effect.cartoonify { setLineStrength(20).setColorReduction(60) })
        cldAssertEqualsAsString("e_cartoonify:30:bw", Effect.cartoonify { setLineStrength(30).setBlackwhite(true) })
        cldAssertEqualsAsString(
            "e_cartoonify:30:bw",
            Effect.cartoonify { setLineStrength(30).setColorReduction(60).setBlackwhite(true) })
        cldAssertEqualsAsString("e_cartoonify:bw", Effect.cartoonify { setColorReduction(60).setBlackwhite(true) })
    }

    @Test
    fun testArtistic() {
        cldAssertEqualsAsString(
            "e_art:al_dente", Effect.artistic { setFilter(ArtisticFilter.AL_DENTE) })
    }

    @Test
    fun testMakeTransparent() {
        cldAssertEqualsAsString("e_make_transparent", Effect.makeTransparent())
        cldAssertEqualsAsString("e_make_transparent:50", Effect.makeTransparent { setLevel(50) })
    }

    @Test
    fun testTrim() {
        cldAssertEqualsAsString("e_trim", Effect.trim())
        cldAssertEqualsAsString("e_trim:30", Effect.trim { setColorSimilarity(30) })
        cldAssertEqualsAsString("e_trim:white", Effect.trim { setColorOverride(color { named("white") }) })
        cldAssertEqualsAsString(
            "e_trim:30:white",
            Effect.trim { setColorSimilarity(30).setColorOverride(color { named("white") }) })
    }

    @Test
    fun testOilPaint() {
        cldAssertEqualsAsString("e_oil_paint", Effect.oilPaint())
        cldAssertEqualsAsString("e_oil_paint:40", Effect.oilPaint { setLevel(40) })
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
        cldAssertEqualsAsString("e_vignette:30", Effect.vignette { setLevel(30) })
    }

    @Test
    fun testPixelate() {
        cldAssertEqualsAsString("e_pixelate", Effect.pixelate())
        cldAssertEqualsAsString("e_pixelate:3", Effect.pixelate { setSquareSize(3) })
    }

    @Test
    fun testPixelateRegion() {
        cldAssertEqualsAsString(
            "e_pixelate_region:20,h_40,w_40,x_20,y_20",
            Effect.pixelateRegion { setSquareSize(20).setRegion(Region(20, 20, 40, 40)) })
    }

    @Test
    fun testPixelateFaces() {
        cldAssertEqualsAsString("e_pixelate_faces", Effect.pixelateFaces())
        cldAssertEqualsAsString("e_pixelate_faces:7", Effect.pixelateFaces { setSquareSize(7) })
    }

    @Test
    fun testBlur() {
        cldAssertEqualsAsString("e_blur", Effect.blur())
        cldAssertEqualsAsString("e_blur:300", Effect.blur { setStrength(300) })
    }

    @Test
    fun testBlurRegion() {
        cldAssertEqualsAsString(
            "e_blur_region:200,h_40,w_40,x_10,y_20",
            Effect.blurRegion { setStrength(200).setRegion(Region(10, 20, 40, 40)) })
    }

    @Test
    fun testBlurFaces() {
        cldAssertEqualsAsString("e_blur_faces", Effect.blurFaces())
        cldAssertEqualsAsString("e_blur_faces:600", Effect.blurFaces { setStrength(600) })
    }

    @Test
    fun testOrderedDither() {
        cldAssertEqualsAsString(
            "e_ordered_dither:0",
            Effect.orderedDither { setFilter(DitherFilter.THRESHOLD_1X1_NON_DITHER) })
    }
}