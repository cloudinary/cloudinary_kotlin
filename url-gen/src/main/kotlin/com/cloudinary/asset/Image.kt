package com.cloudinary.asset

import com.cloudinary.config.CloudConfig
import com.cloudinary.config.UrlConfig
import com.cloudinary.transformation.Action
import com.cloudinary.transformation.Format
import com.cloudinary.transformation.ITransformable
import com.cloudinary.transformation.ImageTransformation
import com.cloudinary.transformation.Transformation


class Image(
    // config
    cloudConfig: CloudConfig,
    urlConfig: UrlConfig,

    // fields
    version: String? = null,
    publicId: String? = null,
    extension: Format? = null,
    urlSuffix: String? = null,
    deliveryType: String? = null,
    signature: String? = null,
    private val transformation: ImageTransformation? = null
) : BaseAsset(
    cloudConfig,
    urlConfig,
    version,
    publicId,
    extension,
    urlSuffix,
    ASSET_TYPE_IMAGE,
    deliveryType,
    signature
) {
    class Builder(cloudConfig: CloudConfig, urlConfig: UrlConfig) :
        BaseAssetBuilder(cloudConfig, urlConfig, ASSET_TYPE_IMAGE), ITransformable<Builder> {

        private var transformation: ImageTransformation? = null

        fun transformation(transformation: ImageTransformation) = apply { this.transformation = transformation }
        fun transformation(transform: ImageTransformation.Builder.() -> Unit) = apply {
            val builder = ImageTransformation.Builder()
            builder.transform()
            this.transformation = builder.build()
        }

        override fun add(action: Action) = apply {
            this.transformation = (this.transformation ?: ImageTransformation()).add(action)
        }

        override fun addTransformation(transformation: Transformation)= apply { (this.transformation ?: ImageTransformation()).add(transformation.toString()) }


        fun build() = Image(
            cloudConfig,
            urlConfig,
            version,
            publicId,
            extension,
            urlSuffix,
            deliveryType,
            signature,
            transformation
        )
    }

    override fun getTransformationString() = transformation?.toString()
}
