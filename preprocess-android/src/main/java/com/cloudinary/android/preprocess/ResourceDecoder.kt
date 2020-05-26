package com.cloudinary.android.preprocess

import android.content.Context
import com.cloudinary.http.Payload

/**
 * Implement this interface to pass to [PreprocessChain.loadWith] for custom resource loading behavior.
 *
 * @param <T> The concrete type this decoder needs to decode
*/
interface ResourceDecoder<T> {
    /**
     * Prepare the resource for preprocessing. This method should extract a concrete resource with type T from the generic payload.
     * T is later passed on to [Preprocess.execute] down the chain.
     *
     * @param context Android context.
     * @param payload Payload to extract the resource from
     * @return The extract concrete resource of type T
     * @throws PayloadNotFoundException If the given payload could not be found.
     * @throws PayloadDecodeException If the given payload could not be properly decoded.
     */
    @Throws(PayloadNotFoundException::class, PayloadDecodeException::class)
    fun decode(context: Context, payload: Payload<*>): T
}