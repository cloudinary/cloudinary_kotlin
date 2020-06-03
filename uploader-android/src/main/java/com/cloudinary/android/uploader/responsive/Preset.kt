package com.cloudinary.android.uploader.responsive

import android.widget.ImageView
import com.cloudinary.Cloudinary
import com.cloudinary.transformation.Gravity
import com.cloudinary.transformation.resize.Resize

typealias ResizeAction = (width: Any?, height: Any?) -> Resize

/**
 * Responsive preset the url is adjusted by.
 *
 * @param autoHeight Specifying true will adjust the image width to the view height
 * @param autoWidth Specifying true will adjust the image width to the view width
 * @param resizeAction Resize action that is used to generate the responsive url
 */
sealed class Preset(
    val autoWidth: Boolean,
    val autoHeight: Boolean,
    val resizeAction: ResizeAction
) {
    /**
     * Adjusts both height and width of the image, retaining the aspect-ratio, to fill the
     * ImageView, using automatic gravity to determine which part of the image is visible.
     * Some cropping may occur. Similar to [ImageView.ScaleType.CENTER_CROP]
     */
    object AutoFill : Preset(autoWidth = true, autoHeight = true, resizeAction = { width, height ->
        Resize.fill {
            width?.let { width(it) }
            height?.let { height(it) }
            gravity(Gravity.auto())
        }
    })

    /**
     * Adjusts both height and width of the image, retaining the aspect-ratio, to completely fit
     * within the bounds of the ImageView. The whole image will be shown, however some blank
     * space may be visible (letterbox). Similar [ImageView.ScaleType.CENTER_INSIDE]
     */
    object Fit : Preset(autoWidth = true, autoHeight = true, resizeAction = { width, height ->
        Resize.fit {
            width?.let { width(it) }
            height?.let { height(it) }
            gravity = Gravity.center()
        }
    })

    /**
     * Custom preset to adjust both height and width of the image.
     */
    class Custom(
        autoWidth: Boolean,
        autoHeight: Boolean,
        resizeAction: ResizeAction
    ) : Preset(autoWidth, autoHeight, resizeAction)

    /**
     * Build an instance of [ResponsiveUrl] pre-configured according to the preset.
     *
     * @param cloudinary Cloudinary instance to use.
     * @return The [ResponsiveUrl] instance
     */
    fun get(cloudinary: Cloudinary): ResponsiveUrl {
        return ResponsiveUrl(cloudinary, this)
    }
}