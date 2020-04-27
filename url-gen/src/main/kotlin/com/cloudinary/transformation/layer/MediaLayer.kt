package com.cloudinary.transformation.layer

import com.cloudinary.util.cldEncodePublicId
import com.cloudinary.util.cldJoin

class MediaLayer(publicId: String, resourceType: String? = null, type: String? = null, format: String? = null) :
    Layer(
        listOfNotNull(
            if (resourceType != "image") resourceType else null,
            if (type != "upload") type else null,
            publicId.cldEncodePublicId().cldJoin(".", format)
        )
    ) {
    class Builder(private val publicId: String) {
        private var resourceType: String? = null
        private var type: String? = null
        private var format: String? = null

        fun resourceType(resourceType: String) = apply { this.resourceType = resourceType }
        fun type(type: String) = apply { this.type = type }
        fun format(format: String) = apply { this.format = format }

        fun build() = MediaLayer(publicId, resourceType, type, format)
    }
}

