package com.cloudinary.transformation.layer

import com.cloudinary.transformation.TransformationComponentBuilder
import com.cloudinary.util.cldEncodePublicId
import com.cloudinary.util.cldJoin

class MediaLayerSource(publicId: String, resourceType: String? = null, type: String? = null, format: String? = null) :
    LayerSource(
        listOfNotNull(
            if (resourceType != "image") resourceType else null,
            if (type != "upload") type else null,
            publicId.cldEncodePublicId().cldJoin(".", format)
        )
    ) {
    class Builder(private val publicId: String) : TransformationComponentBuilder {
        private var resourceType: String? = null
        private var type: String? = null
        private var format: String? = null

        fun setResourceType(resourceType: String) = apply { this.resourceType = resourceType }
        fun setType(type: String) = apply { this.type = type }
        fun setFormat(format: String) = apply { this.format = format }

        override fun build() = MediaLayerSource(publicId, resourceType, type, format)
    }
}

