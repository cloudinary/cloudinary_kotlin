package com.cloudinary.transformation

import com.cloudinary.transformation.adjust.Adjust
import com.cloudinary.transformation.delivery.Delivery
import com.cloudinary.transformation.effect.Effect
import com.cloudinary.transformation.layer.Layer
import com.cloudinary.transformation.layer.LayerSource
import com.cloudinary.transformation.resize.Resize
import com.cloudinary.transformation.video.Video
import com.cloudinary.transformation.warp.Warp

open class Transformation(private val components: List<Action> = emptyList()) {
    constructor(component: Action) : this(listOf(component))

    override fun toString() = components.joinToString("/")

    fun add(component: Action) = Transformation(components + component)

    fun add(component: String) = Transformation(components + RawAction(component))

    fun cutter(cutter: Cutter) = add(cutter)
    fun cutter(source: LayerSource, cutter: (Cutter.Builder.() -> Unit)? = null) =
        addWithBuilder(Cutter.Builder(source), cutter)

    fun cutout(cutout: Cutout) = add(cutout)
    fun cutout(source: LayerSource, cutout: (Cutout.Builder.() -> Unit)? = null) =
        addWithBuilder(Cutout.Builder(source), cutout)

    // TODO add tests
    fun antiRemoval(antiRemoval: AntiRemoval) = add(antiRemoval)

    // TODO add tests
    fun antiRemoval(source: LayerSource, antiRemoval: (AntiRemoval.Builder.() -> Unit)? = null) =
        addWithBuilder(AntiRemoval.Builder(source), antiRemoval)

    fun clip(path: ClippingPath? = null) = add(path ?: clippingPath())
    fun clip(clip: (ClippingPath.Builder.() -> Unit)) = addWithBuilder(ClippingPath.Builder(), clip)

    fun customFunction(customFunction: CustomFunction) = add(customFunction)

    fun rotate(rotate: Rotate) = add(rotate)
    fun rotate(rotate: (Rotate.Builder.() -> Unit)? = null) = addWithBuilder(Rotate.Builder(), rotate)

    fun extract(page: Page) = add(page)

    fun background(background: Background) = add(background)
    fun background(color: ColorValue, background: (Background.Builder.() -> Unit)? = null) =
        addWithBuilder(Background.Builder(color), background)

    fun outline(outline: Outline) = add(outline)
    fun outline(outline: (Outline.Builder.() -> Unit)? = null) = addWithBuilder(Outline.Builder(), outline)

    fun shadow(shadow: Shadow) = add(shadow)
    fun shadow(shadow: (Shadow.Builder.() -> Unit)? = null) = addWithBuilder(Shadow.Builder(), shadow)

    fun cornersRadius(radius: CornerRadius) = add(radius)
    fun cornersRadius(radius: CornerRadius.Builder.() -> Unit) = addWithBuilder(CornerRadius.Builder(), radius)

    fun gradientFade(gradientFade: GradientFade) = add(gradientFade)
    fun gradientFade(gradientFade: (GradientFade.Builder.() -> Unit)? = null) =
        addWithBuilder(GradientFade.Builder(), gradientFade)

    fun border(border: Border) = add(border)
    fun border(border: Border.Builder.() -> Unit) = addWithBuilder(Border.Builder(), border)

    fun displace(displace: Displace) = add(displace)
    fun displace(source: LayerSource, displace: (Displace.Builder.() -> Unit)? = null) =
        addWithBuilder(Displace.Builder(source), displace)


    fun styleTransfer(styleTransfer: StyleTransfer) = add(styleTransfer)
    fun styleTransfer(source: LayerSource, styleTransfer: (StyleTransfer.Builder.() -> Unit)? = null) =
        addWithBuilder(StyleTransfer.Builder(source), styleTransfer)


    // effects
    fun effect(effect: Effect) = add(effect)

    // resize
    fun resize(resize: Resize) = add(resize)

    // adjust
    fun adjust(adjust: Adjust) = add(adjust)

    // delivery
    fun delivery(delivery: Delivery) = add(delivery)

    // video
    fun video(video: Video) = add(video)

    // warp
    fun warp(warp: Warp) = add(warp)

    // layer
    fun layer(layer: Layer) = add(layer)

    fun named(name: String) = add(GenericAction(NamedTransformationParam(name)))

    // variables
    fun variable(name: String, value: Expression) = add(GenericAction(Param(name, name, value)))

    // conditions
    fun ifCondition(expression: Expression) = add(GenericAction(Param("if", "if", expression)))

    fun ifElse() = add(GenericAction(Param("if", "if", ParamValue("else"))))

    fun endIfCondition() = add(GenericAction(Param("if", "if", ParamValue("end"))))

    private fun <T : TransformationComponentBuilder> addWithBuilder(
        builder: T,
        builderAction: (T.() -> Unit)? = null
    ): Transformation {
        builderAction?.let { builder.builderAction() }
        return add(builder.build())
    }

    class Builder(private val components: MutableList<Action> = mutableListOf()) {
        fun add(component: Action) = apply { components.add(component) }
        fun add(component: String) = add(RawAction(component))
        fun merge(transformation: Transformation) = apply { components.addAll(transformation.components) }

        fun cutter(cutter: Cutter) = add(cutter)
        fun cutter(layerSource: LayerSource, cutter: (Cutter.Builder.() -> Unit)? = null) =
            addWithBuilder(Cutter.Builder(layerSource), cutter)

        fun cutout(cutout: Cutout) = add(cutout)
        fun cutout(layerSource: LayerSource, cutout: (Cutout.Builder.() -> Unit)? = null) =
            addWithBuilder(Cutout.Builder(layerSource), cutout)

        fun clip(path: ClippingPath? = null) = add(path ?: clippingPath())
        fun clip(clip: (ClippingPath.Builder.() -> Unit)) = addWithBuilder(ClippingPath.Builder(), clip)

        fun customFunction(customFunction: CustomFunction) = add(customFunction)

        fun rotate(rotate: Rotate) = add(rotate)
        fun rotate(rotate: (Rotate.Builder.() -> Unit)? = null) = addWithBuilder(Rotate.Builder(), rotate)

        fun extract(page: Page) = add(page)

        fun background(background: Background) = add(background)
        fun background(color: ColorValue, background: (Background.Builder.() -> Unit)? = null) =
            addWithBuilder(Background.Builder(color), background)

        fun outline(outline: Outline) = add(outline)
        fun outline(outline: (Outline.Builder.() -> Unit)? = null) = addWithBuilder(Outline.Builder(), outline)

        fun shadow(shadow: Shadow) = add(shadow)
        fun shadow(shadow: (Shadow.Builder.() -> Unit)? = null) = addWithBuilder(Shadow.Builder(), shadow)

        fun cornersRadius(radius: CornerRadius) = add(radius)
        fun cornersRadius(radius: CornerRadius.Builder.() -> Unit) = addWithBuilder(CornerRadius.Builder(), radius)

        fun gradientFade(gradientFade: GradientFade) = add(gradientFade)
        fun gradientFade(gradientFade: (GradientFade.Builder.() -> Unit)? = null) =
            addWithBuilder(GradientFade.Builder(), gradientFade)

        fun border(border: Border) = add(border)
        fun border(border: Border.Builder.() -> Unit) = addWithBuilder(Border.Builder(), border)

        fun displace(displace: Displace) = add(displace)
        fun displace(source: LayerSource, displace: (Displace.Builder.() -> Unit)? = null) =
            addWithBuilder(Displace.Builder(source), displace)


        fun styleTransfer(styleTransfer: StyleTransfer) = add(styleTransfer)
        fun styleTransfer(source: LayerSource, styleTransfer: (StyleTransfer.Builder.() -> Unit)? = null) =
            addWithBuilder(StyleTransfer.Builder(source), styleTransfer)

        fun effect(effect: Effect) = add(effect)

        fun resize(resize: Resize) = add(resize)

        fun adjust(adjust: Adjust) = add(adjust)

        fun delivery(delivery: Delivery) = add(delivery)

        fun video(video: Video) = add(video)

        fun warp(warp: Warp) = add(warp)

        fun layer(layer: Layer) = add(layer)

        fun named(name: String) = add(GenericAction(NamedTransformationParam(name)))

        fun variable(name: String, value: Expression) = add(GenericAction(Param(name, name, value)))

        fun ifCondition(expression: Expression) = add(GenericAction(Param("if", "if", expression)))

        fun ifElse() = add(GenericAction(Param("if", "if", ParamValue("else"))))

        fun endIfCondition() = add(GenericAction(Param("if", "if", ParamValue("end"))))

        private fun <T : TransformationComponentBuilder> addWithBuilder(
            builder: T,
            builderAction: (T.() -> Unit)? = null
        ): Builder {
            builderAction?.let { builder.builderAction() }
            return add(builder.build())
        }

        fun build() = Transformation(components)
    }
}

@TransformationDsl
interface TransformationComponentBuilder {
    fun build(): Action
}

fun transformation(t: Transformation.Builder.() -> Unit): Transformation {
    val builder = Transformation.Builder()
    builder.t()
    return builder.build()
}