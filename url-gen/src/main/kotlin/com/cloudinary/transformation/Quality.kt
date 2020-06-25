package com.cloudinary.transformation

import com.cloudinary.util.cldRanged

enum class ChromaSubSampling(private val value: String) {
    C_444("444"),
    C_420("420");

    override fun toString() = value
}

enum class JpegMini(private val level: Int) {
    HIGH(1),
    MEDIUM(2),
    BEST(0), ;

    override fun toString() = level.toString()
}

class Quality(private val action: Action) : Action by action {

    companion object {
        fun auto(options: (AutoBuilder.() -> Unit)? = null) = fromAutoBuilder(options = options)
        fun best(options: (AutoBuilder.() -> Unit)? = null) = fromAutoBuilder("best", options)
        fun eco(options: (AutoBuilder.() -> Unit)? = null) = fromAutoBuilder("eco", options)
        fun good(options: (AutoBuilder.() -> Unit)? = null) = fromAutoBuilder("good", options)
        fun low(options: (AutoBuilder.() -> Unit)? = null) = fromAutoBuilder("low", options)

        fun level(level: Int, quality: (LevelBuilder.() -> Unit)? = null): Quality {
            val builder = LevelBuilder(level = level.cldRanged(1, 100))
            quality?.let { builder.it() }
            return builder.build()
        }

        fun jpegMini(level: JpegMini? = null) = quality("jpegmini", level?.toString())
    }

    class AutoBuilder internal constructor(private val autoQuality: String? = null) : TransformationComponentBuilder {
        private var anyformat: Boolean = false

        fun anyFormat() = apply { this.anyformat = true }

        override fun build() = Quality(
            ParamsAction(
                qualityParam("auto", autoQuality),
                if (anyformat) Flag.anyFormat().cldAsFlag() else null
            )
        )
    }

    class LevelBuilder internal constructor(private val level: Any? = null) :
        TransformationComponentBuilder {

        private var quantization: Any? = null // Int
        private var chromaSubSampling: ChromaSubSampling? = null

        fun quantization(quantization: Any) =
            apply { this.quantization = quantization.cldRanged(1, 100) }

        fun quantization(quantization: Int) = quantization(quantization as Any)

        fun chromaSubSampling(chromaSubSampling: ChromaSubSampling) =
            apply { this.chromaSubSampling = chromaSubSampling }

        override fun build() = quality(
            level,
            quantization?.let { NamedValue("qmax", quantization!!, "_") },
            chromaSubSampling
        )
    }
}

private fun fromAutoBuilder(autoQuality: String? = null, options: (Quality.AutoBuilder.() -> Unit)?): Quality {
    val builder = Quality.AutoBuilder(autoQuality)
    options?.let { builder.it() }
    return builder.build()
}

private fun qualityParam(vararg values: Any?) = ParamValue(values.filterNotNull()).cldAsQuality()

private fun quality(vararg params: Param) = Quality(ParamsAction(params.toList()))
private fun quality(vararg values: Any?) = quality(qualityParam(*values))
