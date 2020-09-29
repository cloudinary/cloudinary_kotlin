package com.cloudinary.transformation

import com.cloudinary.transformation.adjust.Adjust
import com.cloudinary.transformation.delivery.Delivery
import com.cloudinary.transformation.effect.Effect
import com.cloudinary.transformation.layer.LayerAction
import com.cloudinary.transformation.layer.Source
import com.cloudinary.transformation.reshape.Reshape
import com.cloudinary.transformation.resize.Resize
import com.cloudinary.transformation.transcode.Transcode
import com.cloudinary.transformation.videoedit.VideoEdit


@TransformationDsl
interface ITransformable<T> {
    /**
     * Adds (chains) a transformation action.
     *
     * @param action The transformation action to add.
     *
     * @return A new instance with the added action.
     */
    fun add(action: Action): T

    /**
     * Adds (chains) a transformation action.
     *
     * @param action The transformation action to add.
     *
     * @return A new instance with the added action.
     */
    fun add(action: String) = add(RawAction(action))

    private fun <K : TransformationComponentBuilder> addWithBuilder(
        builder: K,
        builderAction: (K.() -> Unit)? = null
    ): T {
        builderAction?.let { builder.builderAction() }
        return add(builder.build())
    }


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
    fun cutter(source: Source, cutter: (Cutter.Builder.() -> Unit)? = null) =
        addWithBuilder(Cutter.Builder(source), cutter)

    fun cutout(cutout: Cutout) = add(cutout)

    fun cutout(source: Source, cutout: (Cutout.Builder.() -> Unit)? = null) =
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
    fun antiRemoval(source: Source, antiRemoval: (AntiRemoval.Builder.() -> Unit)? = null) =
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
     * @param options The clip options to add TODO DOC builder receiver param
     * @return TODO DOC new transformation
     */
    fun clip(options: (ClippingPath.Builder.() -> Unit)) = addWithBuilder(ClippingPath.Builder(), options)
    fun clip(number: Int, options: (ClippingPath.Builder.() -> Unit)? = null) =
        addWithBuilder(ClippingPath.Builder().number(number), options)

    fun clip(name: String, options: (ClippingPath.Builder.() -> Unit)? = null) =
        addWithBuilder(ClippingPath.Builder().name(name), options)

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

    fun getPage(pagesDescriptor: String) = add(pagesDescriptor.cldAsPage().asAction())
    fun getPage(page: Int) = add(page.cldAsPage().asAction())
    fun getSmartObject(smartObject: SmartObject) = add(smartObject.cldAsPage().asAction())
    fun getLayer(layer: PsdLayer) = add(layer.cldAsPage().asAction())

    fun backgroundColor(backgroundColor: BackgroundColor) = add(backgroundColor)
    fun backgroundColor(color: Color) = backgroundColor(BackgroundColor(color))

    fun roundCorners(vararg radius: Int) = add(RoundCorners.radius(*radius))
    fun roundCorners(radius: RoundCorners) = add(radius)

    fun border(border: Border) = add(border)
    fun border(border: Border.Builder.() -> Unit) = addWithBuilder(Border.Builder(), border)

    fun displace(displace: Displace) = add(displace)
    fun displace(source: Source, displace: (Displace.Builder.() -> Unit)? = null) =
        addWithBuilder(Displace.Builder(source), displace)

    // effects
    fun effect(effect: Effect) = add(effect)

    // resize
    fun resize(resize: Resize) = add(resize)

    // adjust
    fun adjust(adjust: Adjust) = add(adjust)

    // delivery
    fun delivery(delivery: Delivery) = add(delivery)

    // video
    fun videoEdit(videoEdit: VideoEdit) = add(videoEdit)

    // reshape
    fun reshape(reshape: Reshape) = add(reshape)

    // transcode
    fun transcode(transcode: Transcode) = add(transcode)

    // layer
    fun overlay(source: Source, options: (LayerAction.Builder.() -> Unit)? = null) =
        addWithBuilder(LayerAction.Builder(source).param("overlay", "l"), options)

    // layer
    fun underlay(source: Source, options: (LayerAction.Builder.() -> Unit)? = null) =
        addWithBuilder(LayerAction.Builder(source).param("underlay", "u"), options)

    fun add3dLut(publicId: String) = add(LayerAction.Builder(Source.lut(publicId)).build())

    fun namedTransformation(name: String) = add(name.cldAsNamedTransformation().asAction())

    // variables
    fun variable(name: String, value: Expression) = variable(name, value as Any)

    fun variable(name: String, value: Any) =
        add(ParamsAction(Param(name.asVariableName(), name.asVariableName(), ParamValue(value, normalize = true))))

    // conditions
    fun ifCondition(expression: Expression) = add(ParamsAction(Param("if", "if", expression)))

    fun ifCondition(expression: String) = add(ParamsAction(Param("if", "if", expression)))

    fun ifElse() = add(ParamsAction(Param("if", "if", ParamValue("else"))))

    fun endIfCondition() = add(ParamsAction(Param("if", "if", ParamValue("end"))))

    fun format(format: Format) = add(format)

    fun quality(quality: Quality) = add(quality)

    fun prefix(prefix: String) = add(prefix.cldAsPrefix().asAction())

    fun quality(level: Int) = add(Quality.level(level))

    fun addFlag(flag: Flag) = add(flag.cldAsFlag().asAction())
}
