package com.cloudinary.transformation

import com.cloudinary.transformation.adjust.Adjust
import com.cloudinary.transformation.delivery.Delivery
import com.cloudinary.transformation.effect.Effect
import com.cloudinary.transformation.layer.Layer
import com.cloudinary.transformation.layer.LayerContainer
import com.cloudinary.transformation.resize.Resize
import com.cloudinary.transformation.video.Video
import com.cloudinary.transformation.warp.Warp

@TransformationDsl
open class Transformation(private val actions: List<Action> = emptyList()) {
    constructor(action: Action) : this(listOf(action))

    // TODO REMOVE THIS!!!
    fun aspectRatio(aspectRatio: Any) = add(GenericAction(aspectRatio.cldAsAspectRatio()))

    override fun toString() = actions.joinToString("/")

    /**
     * Adds (chains) a transformation action.
     *
     * @param action The transformation action to add.
     *
     * @return TODO DOC new transformation
     */
    fun add(action: Action) = Transformation(actions + action)

    /**
     * Adds (chains) a transformation action.
     *
     * @param action The transformation action to add.
     *
     * @return TODO DOC new transformation
     */
    fun add(action: String) = Transformation(actions + RawAction(action))

    /**
     * Trims pixels according to the transparency levels of a given overlay image.
     *
     * Whenever the overlay image is opaque, the original is shown, and wherever the overlay is transparent,
     * the result will be transparent as well.
     *
     * @param cutter The cutter action to add
     * @return TODO DOC new transformation
     */
    fun cutter(cutter: Cutter) = add(cutter)

    /**
     * Trims pixels according to the transparency levels of a given overlay image.
     *
     * Whenever the overlay image is opaque, the original is shown, and wherever the overlay is transparent,
     * the result will be transparent as well.
     *
     * @param source The overlay image to use
     * @param cutter The cutter action to add TODO DOC builder receiver param
     * @return TODO DOC new transformation
     */
    fun cutter(source: Layer, cutter: (Cutter.Builder.() -> Unit)? = null) =
        addWithBuilder(Cutter.Builder(source), cutter)

    // TODO doc
    fun cutout(cutout: Cutout) = add(cutout)

    // TODO doc
    fun cutout(source: Layer, cutout: (Cutout.Builder.() -> Unit)? = null) =
        addWithBuilder(Cutout.Builder(source), cutout)

    /**
     * Add an overlay image blended using the 'anti-removal' blend mode.
     *
     * In this mode, the overlay is slightly distorted to prevent easy removal.
     *
     * @return TODO DOC new transformation
     */
    fun antiRemoval(antiRemoval: AntiRemoval) = add(antiRemoval)

    /**
     * Add an overlay image blended using the 'anti-removal' blend mode.
     *
     * In this mode, the overlay is slightly distorted to prevent easy removal.
     *
     * @param source The overlay image to use
     * @param antiRemoval The anti-removal action to add TODO DOC builder receiver param
     * @return TODO DOC new transformation
     */
    fun antiRemoval(source: Layer, antiRemoval: (AntiRemoval.Builder.() -> Unit)? = null) =
        addWithBuilder(AntiRemoval.Builder(source), antiRemoval)


    /**
     * Trims the pixels of a PSD image according to a Photoshop clipping path that is stored in the image's metadata.
     *
     * @param path The clipping path to use
     * @return TODO DOC new transformation
     */
    fun clip(path: ClippingPath? = null) = add(path ?: clippingPath())

    /**
     * Trims the pixels of a PSD image according to a Photoshop clipping path that is stored in the image's metadata.
     *
     * @param clip The clip action to add TODO DOC builder receiver param
     * @return TODO DOC new transformation
     */
    fun clip(clip: (ClippingPath.Builder.() -> Unit)) = addWithBuilder(ClippingPath.Builder(), clip)

    /**
     * Inject a custom function into the image transformation pipeline.
     *
     * @param customFunction The custom function source
     * @return TODO DOC new transformation
     */
    fun customFunction(customFunction: CustomFunction) = add(customFunction)

    /**
     * Rotates the asset by the given angle. //
     *
     * @param rotate  The rotation object. TODO DOC builder receiver param
     *
     * @return TODO DOC new transformation
     */
    fun rotate(rotate: Rotate) = add(rotate)

    fun rotate(rotate: (Rotate.Builder.() -> Unit)? = null) = addWithBuilder(Rotate.Builder(), rotate)

    fun extract(extract: Extract) = add(extract)

    fun background(background: Background) = add(background)
    fun background(color: ColorValue, background: (Background.Builder.() -> Unit)? = null) =
        addWithBuilder(Background.Builder(color), background)

    fun outline(outline: Outline) = add(outline)
    fun outline(outline: (Outline.Builder.() -> Unit)? = null) = addWithBuilder(Outline.Builder(), outline)

    fun roundCorners(radius: Int) = add(RoundCorners.radius(radius))
    fun roundCorners(radius: RoundCorners) = add(radius)

    fun gradientFade(gradientFade: GradientFade) = add(gradientFade)
    fun gradientFade(gradientFade: (GradientFade.Builder.() -> Unit)? = null) =
        addWithBuilder(GradientFade.Builder(), gradientFade)

    fun border(border: Border) = add(border)
    fun border(border: Border.Builder.() -> Unit) = addWithBuilder(Border.Builder(), border)

    fun displace(displace: Displace) = add(displace)
    fun displace(source: Layer, displace: (Displace.Builder.() -> Unit)? = null) =
        addWithBuilder(Displace.Builder(source), displace)


    fun styleTransfer(styleTransfer: StyleTransfer) = add(styleTransfer)
    fun styleTransfer(source: Layer, styleTransfer: (StyleTransfer.Builder.() -> Unit)? = null) =
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
    fun overlay(layer: Layer) = add(LayerContainer.Builder(layer).param("overlay", "l").build())

    fun underlay(layer: Layer) = add(LayerContainer.Builder(layer).param("underlay", "u").build())

    fun namedTransformation(name: String) = add(GenericAction(NamedTransformationParam(name)))

    // variables
    fun variable(name: String, value: Expression) = add(GenericAction(Param(name, name, value)))

    fun variable(name: String, value: Any) = add(GenericAction(Param(name, name, value)))

    // conditions
    fun ifCondition(expression: Expression) = add(GenericAction(Param("if", "if", expression)))

    fun ifElse() = add(GenericAction(Param("if", "if", ParamValue("else"))))

    fun endIfCondition() = add(GenericAction(Param("if", "if", ParamValue("end"))))

    fun format(format: Format) = add(format)

    fun quality(quality: Quality) = add(quality)

    fun quality(level: Int) = add(Quality.fixed(level))

    fun dpr(dpr: Dpr) = add(dpr)

    fun dpr(dpr: Number) = add(Dpr.fixed(dpr))

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
        fun merge(transformation: Transformation) = apply { components.addAll(transformation.actions) }

        fun cutter(cutter: Cutter) = add(cutter)
        fun cutter(layer: Layer, cutter: (Cutter.Builder.() -> Unit)? = null) =
            addWithBuilder(Cutter.Builder(layer), cutter)

        fun cutout(cutout: Cutout) = add(cutout)
        fun cutout(layer: Layer, cutout: (Cutout.Builder.() -> Unit)? = null) =
            addWithBuilder(Cutout.Builder(layer), cutout)

        fun clip(path: ClippingPath? = null) = add(path ?: clippingPath())
        fun clip(clip: (ClippingPath.Builder.() -> Unit)) = addWithBuilder(ClippingPath.Builder(), clip)

        fun customFunction(customFunction: CustomFunction) = add(customFunction)

        fun rotate(rotate: Rotate) = add(rotate)
        fun rotate(rotate: (Rotate.Builder.() -> Unit)? = null) = addWithBuilder(Rotate.Builder(), rotate)

        fun extract(extract: Extract) = add(extract)

        fun background(background: Background) = add(background)
        fun background(color: ColorValue, background: (Background.Builder.() -> Unit)? = null) =
            addWithBuilder(Background.Builder(color), background)

        fun outline(outline: Outline) = add(outline)
        fun outline(outline: (Outline.Builder.() -> Unit)? = null) = addWithBuilder(Outline.Builder(), outline)

        fun roundCorners(radius: Int) = add(RoundCorners.radius(radius))
        fun roundCorners(radius: RoundCorners) = add(radius)

        fun gradientFade(gradientFade: GradientFade) = add(gradientFade)
        fun gradientFade(gradientFade: (GradientFade.Builder.() -> Unit)? = null) =
            addWithBuilder(GradientFade.Builder(), gradientFade)

        fun border(border: Border) = add(border)

        fun displace(displace: Displace) = add(displace)
        fun displace(source: Layer, displace: (Displace.Builder.() -> Unit)? = null) =
            addWithBuilder(Displace.Builder(source), displace)


        fun styleTransfer(styleTransfer: StyleTransfer) = add(styleTransfer)
        fun styleTransfer(source: Layer, styleTransfer: (StyleTransfer.Builder.() -> Unit)? = null) =
            addWithBuilder(StyleTransfer.Builder(source), styleTransfer)

        fun effect(effect: Effect) = add(effect)

        fun resize(resize: Resize) = add(resize)

        fun adjust(adjust: Adjust) = add(adjust)

        fun delivery(delivery: Delivery) = add(delivery)

        fun video(video: Video) = add(video)

        fun warp(warp: Warp) = add(warp)

        fun overlay(layer: Layer) = add(LayerContainer.Builder(layer).param("overlay", "l").build())

        fun underlay(layer: Layer) = add(LayerContainer.Builder(layer).param("underlay", "u").build())

        fun namedTransformation(name: String) = add(GenericAction(NamedTransformationParam(name)))

        fun variable(name: String, value: Expression) = add(GenericAction(Param(name, name, value)))

        fun variable(name: String, value: String) = add(GenericAction(Param(name, name, value)))

        fun ifCondition(expression: Expression) = add(GenericAction(Param("if", "if", expression)))

        fun ifElse() = add(GenericAction(Param("if", "if", ParamValue("else"))))

        fun endIfCondition() = add(GenericAction(Param("if", "if", ParamValue("end"))))

        fun format(format: Format) = add(format)

        fun quality(quality: Quality) = add(quality)

        fun dpr(dpr: Dpr) = add(dpr)

        fun dpr(dpr: Number) = add(GenericAction(dpr.cldAsDpr()))

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