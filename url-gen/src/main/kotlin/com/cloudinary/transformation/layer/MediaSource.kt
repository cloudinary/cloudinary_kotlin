package com.cloudinary.transformation.layer

import com.cloudinary.DEFAULT_DELIVERY_TYPE
import com.cloudinary.DEFAULT_RESOURCE_TYPE
import com.cloudinary.transformation.TransformationDsl
import com.cloudinary.util.cldEncodePublicId
import com.cloudinary.util.cldJoinWithOrReturnOriginal

class MediaSource internal constructor(
    publicId: String,
    resourceType: String? = null,
    deliveryType: String? = null,
    format: String? = null
) :
    Source(
        listOfNotNull(
            if (resourceType != DEFAULT_RESOURCE_TYPE) resourceType else null,
            if (deliveryType != DEFAULT_DELIVERY_TYPE) deliveryType else null,
            publicId.cldEncodePublicId().cldJoinWithOrReturnOriginal(".", format)
        )
    ) {

    @TransformationDsl
    class Builder(private val publicId: String) {
        private var resourceType: String? = null
        private var type: String? = null
        private var format: String? = null

        fun resourceType(resourceType: String) = apply { this.resourceType = resourceType }
        fun type(type: String) = apply { this.type = type }
        fun format(format: String) = apply { this.format = format }

        fun build() = MediaSource(publicId, resourceType, type, format)
    }
}

