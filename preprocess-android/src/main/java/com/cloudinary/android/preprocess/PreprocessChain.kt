package com.cloudinary.android.preprocess

import android.content.Context
import com.cloudinary.http.Payload
import com.cloudinary.upload.request.UploadRequest
import java.util.*

/**
 * A preprocess chain to run on resource before uploading. Pass an instance of a populated chain to [UploadRequest].
 * The processing steps will run by the order in which they were added to the chain. Note: The best practice is to use a concrete subclass
 * rather than extend this class. A chain can be used for manipulating and/or validating resources before starting the upload.
 *
 * @param <T> The type of the resource to execute the processing on (e.g. [android.graphics.Bitmap])
*/
abstract class PreprocessChain<T> {
    private var decoder: ResourceDecoder<T>? = null
    private var encoder: ResourceEncoder<T>? = null
    private val preprocessList: MutableList<Preprocess<T>> = ArrayList()

    protected abstract val defaultEncoder: ResourceEncoder<T>?
    protected abstract val defaultDecoder: ResourceDecoder<T>?

    /**
     * Set a decoder to decode the resource
     *
     * @param decoder The decoder to use
     * @return itself for chaining.
     */
    fun loadWith(decoder: ResourceDecoder<T>?): PreprocessChain<T> {
        this.decoder = decoder
        return this
    }

    /**
     * Set an encoder to encode the resource
     *
     * @param encoder The encoder to use
     * @return itself for chaining.
     */
    fun saveWith(encoder: ResourceEncoder<T>?): PreprocessChain<T> {
        this.encoder = encoder
        return this
    }

    /**
     * Add a preprocessing step to the chain
     *
     * @param step Preprocess step
     * @return itself for chaining.
     */
    fun addStep(step: Preprocess<T>): PreprocessChain<T> {
        preprocessList.add(step)
        return this
    }

    /**
     * Execute the processing chain. this is for INTERNAL use by the upload request itself. Do not call this directly.
     *
     * @param context Android context
     * @param payload Payload to run the chain on
     * @return A filepath of the end result of the chain
     * @throws PayloadNotFoundException if the payload is not found
     * @throws PayloadDecodeException if the payload is found but cannot be decoded
     * @throws ResourceCreationException if the processing is done but the result cannot be saved.
     */
    @Throws(PayloadNotFoundException::class, PreprocessException::class)
    fun execute(context: Context, payload: Payload<*>): String? {
        ensureDecoderAndEncoder()
        var resource = decoder!!.decode(context, payload)
        for (preprocess in preprocessList) {
            resource = preprocess.execute(context, resource)
        }
        return encoder!!.encode(context, resource)
    }

    private fun ensureDecoderAndEncoder() {
        if (encoder == null) {
            encoder = defaultEncoder
        }
        if (decoder == null) {
            decoder = defaultDecoder
        }
    }

    /**
     * Checks if this chain is an empty chain (NOP chain).
     *
     * @return True if it's empty.
     */
    val isEmpty: Boolean
        get() = encoder == null && decoder == null && preprocessList.isEmpty()
}