package com.cloudinary.android.preprocess.image

import android.graphics.Bitmap
import com.cloudinary.android.preprocess.PreprocessChain
import com.cloudinary.android.preprocess.ResourceDecoder
import com.cloudinary.android.preprocess.ResourceEncoder

/**
 * A preprocess chain to run on images before uploading. Pass an instance of a populated chain to [UploadRequest#preprocess].
 * The processing steps will run by the order in which they were added to the chain. This chain uses the default Bitmap encoder/decoder, however custom implementations
 * can be supplied using [PreprocessChain#loadWith] and [PreprocessChain.saveWith].
 */
class ImagePreprocessChain : PreprocessChain<Bitmap>() {
    override val defaultEncoder: ResourceEncoder<Bitmap>?
        get() = BitmapEncoder()

    override val defaultDecoder: ResourceDecoder<Bitmap>?
        get() = BitmapDecoder()

    companion object {
        /**
         * Convenience method for building an efficient dimension limiting chain using [BitmapDecoder] and [Limit].
         * Use this in [UploadRequest#preprocess].
         * The scaling retains the original aspect ratio while guaranteeing the height and width are within the requested
         * maximum bounds. Note: If the image is already smaller it will be returned unchanged.
         *
         * @param maxWidth  The maximum width allowed. If the width of the image is greater, the image will be resized accordingly.
         * @param maxHeight The maximum height allowed. If the height of the image is greater, the image will be resized accordingly.
         * @return The prepared chain to pass on to [UploadRequest#preprocess]
         */
        fun limitDimensionsChain(maxWidth: Int, maxHeight: Int): ImagePreprocessChain {
            return ImagePreprocessChain()
                .loadWith(BitmapDecoder(maxWidth, maxHeight))
                .addStep(Limit(maxWidth, maxHeight)) as ImagePreprocessChain
        }
    }

}