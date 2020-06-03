package com.cloudinary.android.uploader.responsive

import android.util.SparseArray
import android.view.View
import android.view.ViewTreeObserver.OnPreDrawListener
import android.widget.ImageView
import com.cloudinary.Cloudinary
import com.cloudinary.Url
import com.cloudinary.transformation.transformation
import kotlin.math.max
import kotlin.math.min

/**
 * This class is used to generate view-size aware cloudinary Urls. It takes any [View] and
 * a [Url] as input and returns a modified [Url] with the width/height transformation
 * included, according to the chosen parameters.
 * Note: When using this class, it's preferable to not include any cropping/scaling/dpr
 * transformation in the base [Url] used. This can lead to unexpected results.
 *
 * @constructor Create a new responsive url generator instance.
 * @param cloudinary The cloudinary instance to use.
 * @param preset responsive preset
 */
class ResponsiveUrl internal constructor(
    private val cloudinary: Cloudinary,
    private val preset: Preset
) {
    companion object {
        // defaults
        private const val DEFAULT_MIN_DIMENSION = 200
        private const val DEFAULT_MAX_DIMENSION = 1000
        private const val DEFAULT_STEP_SIZE = 200

        // holds url mapped to class instance hashes to make sure we're synced
        private val viewsInProgress = SparseArray<Url>()
    }

    private var stepSize = DEFAULT_STEP_SIZE
    private var maxDimension = DEFAULT_MAX_DIMENSION
    private var minDimension = DEFAULT_MIN_DIMENSION

    /**
     * Step size i pixels. This is used to limit the number of generated transformations.
     * The actual width/height parameter in the constructed url will always be a multiplication of
     * step size and not the exact view width/height.
     * For example, when using `width` with `stepSize` 100 on a view with width between 301 and 400
     * will render as `w_400` in the url.
     *
     * @param stepSize The step size to use, in pixels.
     * @return Itself for chaining.
     */
    fun stepSize(stepSize: Int): ResponsiveUrl = apply { this.stepSize = stepSize }

    /**
     * Limit the minimum allowed dimension, in pixels. If the actual view width or height are
     * larger than the value chosen here, this value will be used instead. This is useful to
     * limit the total number of generated transformations.
     * @param maxDimension The highest allowed dimension, in pixels.
     * @return itself for chaining.
     */
    fun maxDimension(maxDimension: Int): ResponsiveUrl = apply { this.maxDimension = maxDimension }

    /**
     * Limit the minimum allowed dimension, in pixels. If the actual view width or height are
     * smaller than the value chosen here, this value will be used instead. This is useful to
     * limit the total number of generated transformations.
     * @param minDimension The smallest allowed dimension, in pixels.
     * @return itself for chaining.
     */
    fun minDimension(minDimension: Int): ResponsiveUrl = apply { this.minDimension = minDimension }

    /**
     * Generate the modified url.
     *
     * @param publicId The public id of the cloudinary resource
     * @param view     The view to adapt the resource dimensions to.
     * @param callback Callback to called when the modified Url is ready.
     */
    fun generate(publicId: String, view: View, callback: Callback) {
        generate(cloudinary.url { publicId(publicId) }, view, callback)
    }

    /**
     * Generate the modified url.
     *
     * @param baseUrl  A url to be used as a base to the responsive transformation. This url can
     * contain any configurations and transformations. The generated responsive
     * transformation will be chained as the last transformation in the url.
     * Important: When generating using a base url, it's preferable to not include
     * any cropping/scaling in the original transformations.
     * @param view     The view to adapt the resource dimensions to.
     * @param callback Callback to called when the modified Url is ready.
     */
    fun generate(baseUrl: Url, view: View, callback: Callback) {
        assertViewValidForResponsive(view)
        val key = view.hashCode()
        val width = view.width
        val height = view.height
        if (conditionsAreMet(width, height)) {
            // The required dimensions are already known, build url:
            callback.onUrlReady(buildUrl(view, baseUrl))
            viewsInProgress.remove(key)
        } else {
            // save the link between the requested url and the specific view, so that
            // if in the meantime the view is assigned a new item (e.g. recycling views in a list)
            // it won't override the correct data.
            viewsInProgress.put(key, baseUrl)
            view.viewTreeObserver.addOnPreDrawListener(object : OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    view.viewTreeObserver.removeOnPreDrawListener(this)
                    if (baseUrl == viewsInProgress[key]) {
                        callback.onUrlReady(buildUrl(view, baseUrl))
                        viewsInProgress.remove(key)
                    }
                    return true
                }
            })
        }
    }

    /**
     * Verify that the given view is properly configured to handle dynamically sized images.
     *
     * @param view The view to inspect.
     */
    private fun assertViewValidForResponsive(view: View) {
        require(!(view is ImageView && view.adjustViewBounds)) {
            // We can't determine the actual size of a dynamically-sized container, it's a circular
            // dependency.
            "Cannot use responsive Url with AdjustViewBounds"
        }
    }

    private fun conditionsAreMet(width: Int, height: Int): Boolean {
        val widthOk = !preset.autoWidth || width > 0
        val heightOk = !preset.autoHeight || height > 0
        return widthOk && heightOk
    }

    /**
     * Construct the final url with the dimensions included as the last transformation in the url.
     *
     * @param baseUrl   The base cloudinary Url to chain the transformation to.
     * @param width     The width to adapt the image width to.
     * @param height    The height to adapt the image height to.
     * @return The url with the responsive transformation.
     */
    private fun buildUrl(baseUrl: Url, width: Int, height: Int): Url {
        var responsiveHeight: Any? = null
        var responsiveWidth: Any? = null

        if (preset.autoHeight) {
            responsiveHeight = trimAndRoundUp(height)
        }
        if (preset.autoWidth) {
            responsiveWidth = trimAndRoundUp(width)
        }

        return baseUrl.transformation?.let {
            baseUrl.copy(
                transformation = it.resize(
                    preset.resizeAction(
                        responsiveWidth,
                        responsiveHeight
                    )
                )
            )
        } ?: run {
            baseUrl.copy(transformation = transformation {
                resize(preset.resizeAction(responsiveWidth, responsiveHeight))
            })
        }
    }

    /**
     * Construct the final url with the dimensions included as the last transformation in the url.
     *
     * @param view      The view to adapt the image size to.
     * @param baseUrl   The base cloudinary Url to chain the transformation to.
     * @return The url with the responsive transformation.
     */
    private fun buildUrl(view: View, baseUrl: Url): Url {
        val contentWidth = view.width - view.paddingLeft - view.paddingRight
        val contentHeight = view.height - view.paddingTop - view.paddingBottom

        return buildUrl(baseUrl, contentWidth, contentHeight)
    }

    /**
     * Returns the smallest round number (in terms of step size) that is bigger than the requested dimension,
     * trimmed to max bounds.
     *
     * @param dimension The Requested size
     * @return The rounded value
     */
    private fun trimAndRoundUp(dimension: Int): Int {
        val value = ((dimension - 1) / stepSize + 1) * stepSize
        return max(minDimension, min(value, maxDimension))
    }

    /**
     * Callback to send to [ResponsiveUrl.generate]
     */
    interface Callback {
        /**
         * This will be called with the finished url, containing the scaling/cropping transformation
         * based on the actual view dimensions.
         *
         * @param url The finished url. Call [Url.generate] to get the url string
         */
        fun onUrlReady(url: Url?)
    }
}