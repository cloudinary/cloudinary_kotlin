package com.cloudinary.transformation.background

import com.cloudinary.transformation.ParamValue
import com.cloudinary.transformation.TransformationComponentBuilder
import com.cloudinary.transformation.cldAsBackground
import com.cloudinary.util.cldRanged

// TODO gradient
class BlurredBackgroundBuilder : TransformationComponentBuilder {
    private var intensity: Any? = null
    private var brightness: Any? = null

    fun intensity(intensity: Int) = apply { this.intensity = intensity }
    fun brightness(brightness: Int) = apply { this.brightness = brightness }

    override fun build(): Background {
        val value = ParamValue(listOfNotNull("blur", intensity?.cldRanged(1, 2000), brightness?.cldRanged(-300, 100)))
        return background(value.cldAsBackground())
    }
}

class BorderBackgroundBuilder : TransformationComponentBuilder {
    private var contrast: Boolean = false
//    private var gradient: Gradient? = null

    fun contrast(contrast: Boolean) = apply { this.contrast = contrast }
//    fun gradient(gradient: Gradient) = apply { this.gradient = gradient }

    override fun build(): Background {
        return autoBackground(
            when {
//                gradient != null -> gradient
                contrast -> "border_contrast"
                else -> "border"
            }
        )
    }
}

class PredominantBackgroundBuilder : TransformationComponentBuilder {
    private var contrast: Boolean = false
    private var gradient: Any? = null

    fun contrast(contrast: Boolean) = apply { this.contrast = contrast }
//    fun gradient(options: (GradientBuilder.()->Unit)? = null){
//        val builder = GradientBuilder()
//        options?.let { builder.it() }
//        this.gradient = builder.build()
//    }

    override fun build(): Background {
        return autoBackground(
            when {
                gradient != null -> gradient
                contrast -> "predominant_contrast"
                else -> "predominant"
            }
        )
    }
}

// TODO enum should be in Adjust file, not in the adjuserBuilders file.
//enum class GradientDirection{
//    HORIZONTAL
//}

//class GradientBuilder() {
//    private var palette: Any? = null
//    private var colors:Int? = null
//    private var direction: GradientDirection? = null
//
//    fun palette(vararg colors: String) = apply { this.palette = colors }
//    fun direction(direction: GradientDirection) = apply { this.direction = direction }
//    fun colors(colors: Int) = apply { this.colors = colors }
//
//    fun build(){}
//}