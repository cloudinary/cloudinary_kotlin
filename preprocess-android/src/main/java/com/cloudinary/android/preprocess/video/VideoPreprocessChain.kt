package com.cloudinary.android.preprocess.video

import android.net.Uri
import com.cloudinary.android.preprocess.PreprocessChain
import com.cloudinary.android.preprocess.ResourceDecoder
import com.cloudinary.android.preprocess.ResourceEncoder
import com.cloudinary.upload.request.UploadRequest
import com.cloudinary.android.preprocess.preprocess

/**
 * A preprocess chain to run on videos before uploading. Pass an instance of a populated chain to [UploadRequest.preprocess].
 * The processing steps will run by the order in which they were added to the chain. This chain uses the default Video encoder/decoder, however custom implementations
 * can be supplied using [PreprocessChain.loadWith] and [PreprocessChain.saveWith].
 */
class VideoPreprocessChain : PreprocessChain<Uri>() {
    override val defaultEncoder: ResourceEncoder<Uri>
        get() = VideoEncoder()

    override val defaultDecoder: ResourceDecoder<Uri>
        get() = VideoDecoder()

    companion object {
        /**
         * Convenience method for building an efficient video transcoding chain using [Transcode].
         * Use this in [UploadRequest.preprocess].
         *
         * @param parameters Transcoding parameters.
         * @return The prepared chain to pass on to [UploadRequest.preprocess]
         */
        fun videoTranscodingChain(parameters: Parameters): VideoPreprocessChain {
            return VideoPreprocessChain()
                .addStep(Transcode(parameters)) as VideoPreprocessChain
        }
    }
}