package com.cloudinary.asset

import com.cloudinary.config.CloudConfig
import com.cloudinary.config.UrlConfig
import com.cloudinary.transformation.Action
import com.cloudinary.transformation.Format
import com.cloudinary.transformation.ITransformable
import com.cloudinary.transformation.Transformation
import com.cloudinary.transformation.VideoTransformation

class Video(
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
    private val transformation: VideoTransformation? = null
) : BaseAsset(
    cloudConfig,
    urlConfig,
    version,
    publicId,
    extension,
    urlSuffix,
    ASSET_TYPE_VIDEO,
    deliveryType,
    signature
) {
    class Builder(cloudConfig: CloudConfig, urlConfig: UrlConfig) :
        BaseAssetBuilder(cloudConfig, urlConfig, ASSET_TYPE_VIDEO), ITransformable<Builder> {

        private var transformation: VideoTransformation? = null

        fun transformation(transformation: VideoTransformation) = apply { this.transformation = transformation }
        fun transformation(transform: VideoTransformation.Builder.() -> Unit) = apply {
            val builder = VideoTransformation.Builder()
            builder.transform()
            this.transformation = builder.build()
        }

        override fun add(action: Action) = apply {
            this.transformation = (this.transformation ?: VideoTransformation()).add(action)
        }

        override fun addTransformation(transformation: Transformation)= apply { (this.transformation ?: VideoTransformation()).add(transformation.toString()) }

        fun build() = Video(
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
