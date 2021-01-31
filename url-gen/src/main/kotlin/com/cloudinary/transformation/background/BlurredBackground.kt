package com.cloudinary.transformation.background

import com.cloudinary.transformation.joinWithValues
import com.cloudinary.util.cldRanged

class BlurredBackground(private val intensity: Int?, private val brightness: Int?) : Background() {

    override fun getValues() = "blurred".joinWithValues(intensity, brightness)

    class Builder : BackgroundBuilder<BlurredBackground> {
        private var intensity: Int? = null
        private var brightness: Int? = null

        init {
            intensity?.cldRanged(1, 2000)
            brightness?.cldRanged(-300, 100)
        }

        fun intensity(intensity: Int) = apply { this.intensity = intensity }
        fun brightness(brightness: Int) = apply { this.brightness = brightness }

        override fun build() = BlurredBackground(intensity, brightness)
    }
}
