package com.cloudinary.transformation.layer

import com.cloudinary.util.cldJoin
import com.cloudinary.util.cldToBase64

class FetchLayer(remoteUrl: String, resourceType: String? = null, format: String? = null) :
    Layer(
        listOfNotNull(
            if (resourceType != "image") resourceType else null,
            "fetch",
            remoteUrl.cldToBase64().cldJoin(".", format)
        )
    ) {
    class Builder(private val remoteUrl: String) {
        private var resourceType: String? = null
        private var format: String? = null

        fun resourceType(resourceType: String) = apply { this.resourceType = resourceType }
        fun format(format: String) = apply { this.format = format }

        fun build() = FetchLayer(remoteUrl, resourceType, format)
    }
}

