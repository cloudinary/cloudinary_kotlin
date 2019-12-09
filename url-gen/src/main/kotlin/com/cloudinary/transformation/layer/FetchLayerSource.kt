package com.cloudinary.transformation.layer

import com.cloudinary.transformation.TransformationComponentBuilder
import com.cloudinary.util.cldJoin
import com.cloudinary.util.cldToBase64

class FetchLayerSource(remoteUrl: String, resourceType: String? = null, format: String? = null) :
    LayerSource(
        listOfNotNull(
            if (resourceType != "image") resourceType else null,
            "fetch",
            remoteUrl.cldToBase64().cldJoin(".", format)
        )
    ) {
    class Builder(private val remoteUrl: String) : TransformationComponentBuilder {
        private var resourceType: String? = null
        private var format: String? = null

        fun setResourceType(resourceType: String) = apply { this.resourceType = resourceType }
        fun setFormat(format: String) = apply { this.format = format }

        override fun build() = FetchLayerSource(remoteUrl, resourceType, format)
    }
}

