package com.cloudinary.android.preprocess

import com.cloudinary.upload.request.UploadRequest

private var preprocessChain: PreprocessChain<*>? = null

fun UploadRequest.preprocess(chain: PreprocessChain<*>): UploadRequest {
    // TODO: Assert not dispatched
    preprocessChain = chain
    return this
}