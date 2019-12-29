package com.cloudinary.transformation.video

import com.cloudinary.transformation.ColorValue
import com.cloudinary.transformation.Param
import com.cloudinary.transformation.ParamValue
import com.cloudinary.transformation.ParamsAction
import com.cloudinary.util.cldToString

open class Video constructor(params: Map<String, Param>) : ParamsAction<Video>(params) {
    constructor(param: Param) : this(mapOf(Pair(param.key, param)))

    override fun create(params: Map<String, Param>) =
        Video(params)

    companion object {
        fun delay(milliseconds: Int) = Delay.Builder(milliseconds).build()
        fun loop() = Loop.Builder().build()
        fun loop(loop: (Loop.Builder.() -> Unit)? = null): Loop {
            val newBuilder = Loop.Builder()
            loop?.let { newBuilder.it() }
            return newBuilder.build()
        }

        fun fade(fade: (Fade.Builder.() -> Unit)? = null): Fade {
            val newBuilder = Fade.Builder()
            fade?.let { newBuilder.it() }
            return newBuilder.build()
        }

        // video from here..?
        fun startOffset(offset: OffsetValue) = StartOffset.Builder(offset).build()

        fun startOffsetAuto() = StartOffsetAuto.Builder().build()

        fun endOffset(offset: OffsetValue) = EndOffset.Builder(offset).build()
        fun duration(duration: OffsetValue) = Duration.Builder(duration).build()
        fun offset(offset: (Offset.Builder.() -> Unit)? = null): Offset {
            val newBuilder = Offset.Builder()
            offset?.let { newBuilder.it() }
            return newBuilder.build()
        }

        fun reverse() = Reverse.Builder().build()
        fun boomerang() = Boomerang.Builder().build()

        fun preview(preview: (Preview.Builder.() -> Unit)? = null): Preview {
            val newBuilder = Preview.Builder()
            preview?.let { newBuilder.it() }
            return newBuilder.build()
        }

        fun noise(noise: (Noise.Builder.() -> Unit)? = null): Noise {
            val newBuilder = Noise.Builder()
            noise?.let { newBuilder.it() }
            return newBuilder.build()
        }

        fun makeTransparent(
            color: ColorValue,
            makeTransparent: (MakeTransparent.Builder.() -> Unit)? = null
        ): MakeTransparent {
            val newBuilder = MakeTransparent.Builder(color)
            makeTransparent?.let { newBuilder.it() }
            return newBuilder.build()
        }

        fun deshake(deshake: (Deshake.Builder.() -> Unit)? = null): Deshake {
            val newBuilder = Deshake.Builder()
            deshake?.let { newBuilder.it() }
            return newBuilder.build()
        }

        fun waveform() = Waveform.Builder().build()

        fun accelerate(accelerate: (Accelerate.Builder.() -> Unit)? = null): Accelerate {
            val newBuilder = Accelerate.Builder()
            accelerate?.let { newBuilder.it() }
            return newBuilder.build()
        }
    }
}

enum class DeShakeFactor(private val factor: Int) {
    PX_16(16),
    PX_32(32),
    PX_48(48),
    PX_64(64);

    override fun toString(): String {
        return factor.cldToString()
    }
}

class OffsetValue private constructor(value: Any) : ParamValue(value) {
    companion object {
        fun percent(percent: Float) = OffsetValue("${percent}p")
        fun seconds(seconds: Float) = OffsetValue(seconds)
    }
}