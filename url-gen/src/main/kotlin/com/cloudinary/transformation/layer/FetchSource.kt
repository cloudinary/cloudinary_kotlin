package com.cloudinary.transformation.layer

import com.cloudinary.transformation.Format
import com.cloudinary.transformation.TransformationDsl
import com.cloudinary.util.cldJoinWithOrReturnOriginal
import com.cloudinary.util.cldToBase64

class FetchSource internal constructor(
    remoteUrl: String,
    resourceType: String? = null,
    format: Any? = null
) :
    Source(
        listOfNotNull(
            if (resourceType != "image") resourceType else null,
            "fetch",
            remoteUrl.cldToBase64().cldJoinWithOrReturnOriginal(".", format)
        )
    ) {

    @TransformationDsl
    class Builder(private val remoteUrl: String) {
        private var resourceType: String? = null
        private var format: Any? = null

        fun resourceType(resourceType: String) = apply { this.resourceType = resourceType }
        fun format(format: Format) = apply { this.format = format }
        fun format(format: String) = apply { this.format = format }

        fun build() = FetchSource(remoteUrl, resourceType, format)
    }
}

