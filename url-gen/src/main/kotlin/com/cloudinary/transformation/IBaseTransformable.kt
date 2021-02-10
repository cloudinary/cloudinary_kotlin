package com.cloudinary.transformation

import com.cloudinary.transformation.adjust.Adjust
import com.cloudinary.transformation.delivery.Delivery
import com.cloudinary.transformation.effect.Effect
import com.cloudinary.transformation.expression.Conditional
import com.cloudinary.transformation.expression.Variable
import com.cloudinary.transformation.extract.Extract
import com.cloudinary.transformation.layer.Overlay
import com.cloudinary.transformation.layer.Underlay
import com.cloudinary.transformation.layer.source.Source
import com.cloudinary.transformation.reshape.Reshape
import com.cloudinary.transformation.resize.Resize

interface IBaseTransformable<T> {
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

    fun roundCorners(vararg radius: Int) = add(RoundCorners.byRadius(*radius))
    fun roundCorners(radius: RoundCorners) = add(radius)

    fun border(border: Border) = add(border)
    fun border(border: Border.Builder.() -> Unit) = addWithBuilder(Border.Builder(), border)

    fun displace(displace: Displace) = add(displace)
    fun displace(source: Source, options: (Displace.Builder.() -> Unit)? = null) =
        addWithBuilder(Displace.Builder(source), options)

    // effects
    fun effect(effect: Effect) = add(effect)

    // resize
    fun resize(resize: Resize) = add(resize)

    // adjust
    fun adjust(adjust: Adjust) = add(adjust)

    // delivery
    fun delivery(delivery: Delivery) = add(delivery)

    fun extract(extract: Extract) = add(extract)

    // reshape
    fun reshape(reshape: Reshape) = add(reshape)

    // layer
    fun overlay(overlay: Overlay) = add(overlay)

    // layer
    fun underlay(underlay: Underlay) = add(underlay)

    fun namedTransformation(namedTransformation: NamedTransformation) = add(namedTransformation)
    fun namedTransformation(name: String) = add(NamedTransformation.name(name))

    // variables
    fun addVariable(variable: Variable) = add(variable)

    fun addVariable(name: String, value: Any) = addVariable(Variable.set(name, value))

    fun conditional(condition: Conditional) = add(condition)

    // TODO what is this - create class?
    fun prefix(prefix: String) = add("p_$prefix")

    fun addFlag(flag: Flag) = add(FlagAction(flag))

    fun addFlag(flag: String) = add(FlagAction(flag))

}