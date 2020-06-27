package com.cloudinary.transformation.layer

import com.cloudinary.transformation.Param

/**
 * Represents a source for an action that needs another asset as a source (such as overly).
 */
open class Source internal constructor(
    val values: List<Any>,
    val params: List<Param> = emptyList()
) {

    companion object {
        fun fetch(remoteUrl: String, fetch: (FetchSource.Builder.() -> Unit)? = null): FetchSource {
            val builder = FetchSource.Builder(remoteUrl)
            fetch?.let { builder.fetch() }
            return builder.build()
        }

        fun image(publicId: String, media: (MediaSource.Builder.() -> Unit)? = null): MediaSource {
            val builder = MediaSource.Builder(publicId)
            media?.let { builder.media() }
            return builder.build()
        }

        fun video(publicId: String, media: (MediaSource.Builder.() -> Unit)? = null): MediaSource {
            val builder = MediaSource.Builder(publicId)
            media?.let { builder.media() }
            return builder.resourceType("video").build()
        }

        fun text(
            text: String,
            textLayer: (TextSource.Builder.() -> Unit)? = null
        ): BaseTextSource {
            val builder = TextSource.Builder(text)
            textLayer?.let { builder.it() }
            return builder.build()
        }

        fun subtitles(
            publicId: String,
            subtitles: (SubtitlesSource.Builder.() -> Unit)? = null
        ): BaseTextSource {
            val builder = SubtitlesSource.Builder(publicId)
            subtitles?.let { builder.it() }
            return builder.build()
        }

        internal fun lut(publicId: String) = Source(listOf("lut", publicId))
    }
}
