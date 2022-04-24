package com.cloudinary.asset

import com.cloudinary.config.CloudConfig
import com.cloudinary.config.UrlConfig
import com.cloudinary.transformation.Action
import com.cloudinary.transformation.Format
import com.cloudinary.transformation.ITransformable
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
    storageType: String? = null,
    private val transformation: VideoTransformation? = null,
    deliveryType: String? = null
) : BaseAsset(
    cloudConfig,
    urlConfig,
    version,
    publicId,
    extension,
    urlSuffix,
    ASSET_TYPE_VIDEO,
    storageType,
    deliveryType
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

        fun build() = Video(
            cloudConfig,
            urlConfig,
            version,
            publicId,
            extension,
            urlSuffix,
            storageType,
            transformation,
            deliveryType
        )
    }

    override fun getTransformationString() = transformation?.toString()
}
