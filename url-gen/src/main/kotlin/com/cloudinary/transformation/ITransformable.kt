package com.cloudinary.transformation

import com.cloudinary.transformation.adjust.Adjust
import com.cloudinary.transformation.delivery.Delivery
import com.cloudinary.transformation.doctools.DocTools
import com.cloudinary.transformation.effect.Effect
import com.cloudinary.transformation.expression.IfCondition
import com.cloudinary.transformation.expression.Variable
import com.cloudinary.transformation.layer.Overlay
import com.cloudinary.transformation.layer.Underlay
import com.cloudinary.transformation.layer.source.LayerSource
import com.cloudinary.transformation.psdtools.PSDTools
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
    fun cutter(source: LayerSource, cutter: (Cutter.Builder.() -> Unit)? = null) =
        addWithBuilder(Cutter.Builder(source), cutter)

    fun cutout(cutout: Cutout) = add(cutout)

    fun cutout(source: LayerSource, cutout: (Cutout.Builder.() -> Unit)? = null) =
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
    fun antiRemoval(source: LayerSource, antiRemoval: (AntiRemoval.Builder.() -> Unit)? = null) =
        addWithBuilder(AntiRemoval.Builder(source), antiRemoval)

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
    fun rotate(rotate: Int) = add(Rotate.byAngle(rotate))

    fun backgroundColor(backgroundColor: BackgroundColor) = add(backgroundColor)
    fun backgroundColor(color: Color) = backgroundColor(BackgroundColor(color))

    fun roundCorners(vararg radius: Int) = add(RoundCorners.byRadius(*radius))
    fun roundCorners(radius: RoundCorners) = add(radius)

    fun border(border: Border) = add(border)
    fun border(border: Border.Builder.() -> Unit) = addWithBuilder(Border.Builder(), border)

    fun displace(displace: Displace) = add(displace)
    fun displace(source: LayerSource, options: (Displace.Builder.() -> Unit)? = null) =
        addWithBuilder(Displace.Builder(source), options)

    // effects
    fun effect(effect: Effect) = add(effect)

    // resize
    fun resize(resize: Resize) = add(resize)

    // adjust
    fun adjust(adjust: Adjust) = add(adjust)

    // delivery
    fun delivery(delivery: Delivery) = add(delivery)

    fun docTools(docTools: DocTools) = add(docTools)

    fun psdTools(psdTools: PSDTools) = add(psdTools)

    // video
    fun videoEdit(videoEdit: VideoEdit) = add(videoEdit)

    // reshape
    fun reshape(reshape: Reshape) = add(reshape)

    // transcode
    fun transcode(transcode: Transcode) = add(transcode)

    // layer
    fun overlay(overlay: Overlay) = add(overlay)

    // layer
    fun underlay(underlay: Underlay) = add(underlay)

    fun add3dLut(lutFilePublicId: String) = add(Add3DLut(lutFilePublicId))

    // TODO encode? create class for this?
    fun namedTransformation(name: String) = add("t_$name")

    // variables
    fun variable(variable: Variable) = add(variable)

    fun variable(name: String, value: Any) = variable(Variable.set(name, value))

    fun ifCondition(condition: IfCondition) = add(condition)

    // TODO what is this - create class?
    fun prefix(prefix: String) = add("p_$prefix")

    fun addFlag(flag: Flag) = add(FlagAction(flag))
}
