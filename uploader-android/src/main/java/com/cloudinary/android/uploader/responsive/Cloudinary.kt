package com.cloudinary.android.uploader.responsive

import com.cloudinary.Cloudinary

/**
 * Create a responsive url
 * @param preset The preset to create the [ResponsiveUrl] with.
 */
fun Cloudinary.responsive(preset: Preset): ResponsiveUrl {
    return preset.get(this)
}