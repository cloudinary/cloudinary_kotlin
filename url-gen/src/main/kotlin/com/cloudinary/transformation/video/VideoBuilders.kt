package com.cloudinary.transformation.video

import com.cloudinary.transformation.*
import com.cloudinary.transformation.effect.Effect
import com.cloudinary.util.cldRanged
import com.cloudinary.util.inputError

class Delay private constructor(milliseconds: Int) : Video(
    Param(
        "delay",
        "dl",
        ParamValue(milliseconds)
    )
) {
    class Builder(private val milliseconds: Int) : TransformationComponentBuilder {
        override fun build() = Delay(milliseconds)
    }
}

class Loop private constructor(additionalLoops: Int? = null) :
    Video(
        Param(
            "loop",
            "e",
            ParamValue(listOfNotNull("loop", additionalLoops))
        )
    ) {
    class Builder : TransformationComponentBuilder {
        private var additionalLoops: Int? = null
        fun additionalLoops(loops: Int) = apply { this.additionalLoops = loops }
        override fun build() = Loop(additionalLoops)
    }
}

class Fade private constructor(milliseconds: Int? = null) : Video(
    Param(
        "fade",
        "e",
        ParamValue(listOfNotNull("fade", milliseconds))
    )
) {
    class Builder : TransformationComponentBuilder {
        private var milliseconds: Int? = null
        fun milliseconds(milliseconds: Int) = apply { this.milliseconds = milliseconds }
        override fun build() = Fade(milliseconds)
    }
}

// video from here..?
class StartOffset private constructor(offset: OffsetValue) :
    Video(
        Param(
            "start_offset",
            "so",
            offset
        )
    ) {
    class Builder(private val offset: OffsetValue) : TransformationComponentBuilder {
        override fun build() = StartOffset(offset)
    }
}

class StartOffsetAuto private constructor() : Video(
    Param(
        "start_offset",
        "so",
        ParamValue("auto")
    )
) {
    class Builder : TransformationComponentBuilder {
        override fun build() = StartOffsetAuto()
    }
}

class EndOffset private constructor(offset: OffsetValue) :
    Video(
        Param(
            "end_offset",
            "eo",
            offset
        )
    ) {
    class Builder(private val offset: OffsetValue) : TransformationComponentBuilder {
        override fun build() = EndOffset(offset)
    }
}

class Duration private constructor(duration: OffsetValue) :
    Video(
        Param(
            "duration",
            "du",
            duration
        )
    ) {
    class Builder(private val offset: OffsetValue) : TransformationComponentBuilder {
        override fun build() = Duration(offset)
    }
}

class Offset private constructor(start: OffsetValue? = null, end: OffsetValue? = null) :
    Video(buildParams(start, end)) {
    companion object {
        private fun buildParams(start: OffsetValue?, end: OffsetValue?): Map<String, Param> {
            if (start == null && end == null) inputError(Pair(start, end))// TODO inputError should be more flexible

            val params = listOfNotNull(start?.let { Param("start_offset", "so", it) },
                end?.let { Param("end_offset", "eo", it) })

            return params.cldToActionMap()
        }
    }

    class Builder : TransformationComponentBuilder {
        private var start: OffsetValue? = null
        private var end: OffsetValue? = null
        fun start(offset: OffsetValue) = apply { this.start = offset }
        fun end(offset: OffsetValue) = apply { this.end = offset }

        override fun build() = Offset(start, end)
    }
}

class Reverse private constructor() : Video(
    Param(
        "reverse",
        "e",
        ParamValue("reverse")
    )
) {
    class Builder : TransformationComponentBuilder {
        override fun build() = Reverse()
    }
}

class Boomerang private constructor() : Video(
    Param(
        "boomerang",
        "e",
        ParamValue("boomerang")
    )
) {
    class Builder : TransformationComponentBuilder {
        override fun build() = Boomerang()
    }
}

class Preview private constructor(seconds: Int? = null) :
    Video(
        Param(
            "preview",
            "e",
            ParamValue(
                listOfNotNull(
                    "preview",
                    seconds?.let { NamedValue("duration", it, "_") })
            )
        )
    ) {
    class Builder : TransformationComponentBuilder {
        private var seconds: Int? = null
        fun seconds(seconds: Int) = apply { this.seconds = seconds }
        override fun build() = Preview(seconds)
    }
}

class Noise private constructor(level: Int? = null) :
    Video(
        Param(
            "noise",
            "e",
            ParamValue(
                listOfNotNull(
                    "noise",
                    level?.cldRanged(0, 100)
                )
            )
        )
    ) {
    class Builder : TransformationComponentBuilder {
        private var level: Int? = null
        fun level(level: Int) = apply { this.level = level }
        override fun build() = Noise(level)
    }
}

class MakeTransparent private constructor(color: ColorValue, level: Int? = null) :
    Effect(
        "make_transparent",
        listOfNotNull(level),
        listOf(ColorParam(color))
    ) {
    class Builder(private val color: ColorValue) : TransformationComponentBuilder {
        private var level: Int? = null
        fun level(level: Int) = apply { this.level = level }
        override fun build() = MakeTransparent(color, level)
    }
}

class Deshake private constructor(factor: DeShakeFactor? = null) :
    Video(
        Param(
            "deshake",
            "e",
            ParamValue(listOfNotNull("deshake", factor))
        )
    ) {
    class Builder : TransformationComponentBuilder {
        private var factor: DeShakeFactor? = null
        fun factor(factor: DeShakeFactor?) = apply { this.factor = factor }
        override fun build() = Deshake(factor)
    }
}

class Waveform private constructor() : Video(
    FlagsParam(FlagKey.WAVEFORM())
) {
    class Builder : TransformationComponentBuilder {
        override fun build() = Waveform()
    }
}

class Accelerate private constructor(percent: Int? = null) :
    Video(
        Param(
            "accelerate",
            "e",
            ParamValue(
                listOfNotNull(
                    "accelerate",
                    percent?.cldRanged(-50, 100)
                )
            )
        )
    ) {
    class Builder : TransformationComponentBuilder {
        private var percent: Int? = null
        fun percent(percent: Int) = apply { this.percent = percent }
        override fun build() = Accelerate(percent)
    }
}