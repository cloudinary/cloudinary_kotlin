package com.cloudinary.transformation.resize

import com.cloudinary.transformation.*
import com.cloudinary.transformation.background.Background
import com.cloudinary.transformation.gravity.Gravity
import com.cloudinary.transformation.resize.CropMode.*


internal enum class CropMode(private val value: String) {
    SCALE("scale"),
    CROP("crop"),
    FIT("fit"),
    LIMIT_FIT("limit"),
    MINIMUM_FIT("mfit"),
    FILL("fill"),
    LIMIT_FILL("lfill"),
    IMAGGA_CROP("imagga_crop"),
    IMAGGA_SCALE("imagga_scale"),
    PAD("pad"),
    LIMIT_PAD("lpad"),
    MINIMUM_PAD("mpad"),
    FILL_PAD("fill_pad"),
    THUMBNAIL("thumb");

    override fun toString() = value
}

class ScaleBuilder : BaseBuilder(SCALE)
class CropBuilder : BaseBuilder(CROP), HasGravity, HasZoom, HasXY
class FitBuilder : BaseBuilder(FIT)
class LimitFitBuilder : BaseBuilder(LIMIT_FIT)
class MinimumFitBuilder : BaseBuilder(MINIMUM_FIT)
class FillBuilder : BaseBuilder(FILL), HasGravity
class LimitFillBuilder : BaseBuilder(LIMIT_FILL), HasGravity
class PadBuilder : BaseBuilder(PAD), HasGravity, HasXY, HasBackground
class LimitPadBuilder : BaseBuilder(LIMIT_PAD), HasGravity, HasXY, HasBackground
class MinimumPadBuilder : BaseBuilder(MINIMUM_PAD), HasGravity, HasXY, HasBackground
class FillPadBuilder : BaseBuilder(FILL_PAD), HasGravity, HasXY, HasBackground
class ThumbnailBuilder : BaseBuilder(THUMBNAIL), HasGravity, HasXY, HasZoom
class ImaggaCropBuilder : BaseBuilder(IMAGGA_CROP)
class ImaggaScaleBuilder : BaseBuilder(IMAGGA_SCALE)
class GenericResizeBuilder(cropMode: String) : BaseBuilder(cropMode), HasGravity, HasXY, HasBackground, HasZoom

open class BaseBuilder internal constructor(cropMode: String) : ResizeCommon {
    internal constructor(cropMode: CropMode) : this(cropMode.toString())

    override var params = mutableListOf(cropMode.cldAsCropMode())
    override fun add(param: Param) {
        params.add(param)
    }

    fun build() = Resize(ParamsAction(params))
}

interface HasWidth : HasResizeAttribute {
    fun width(width: Any) = add(width.cldAsWidth())
    fun width(width: Int) = width(width as Any)
    fun width(width: Float) = width(width as Any)
}

interface HasHeight : HasResizeAttribute {
    fun height(height: Any) = add(height.cldAsHeight())
    fun height(height: Int) = height(height as Any)
    fun height(height: Float) = height(height as Any)
}

interface HasAspectRatio : HasResizeAttribute {
    fun aspectRatio(aspectRatio: Any) = add(aspectRatio.cldAsAspectRatio())
    fun aspectRatio(aspectRatio: Int) = aspectRatio(aspectRatio as Any)
    fun aspectRatio(aspectRatio: Float) = aspectRatio(aspectRatio as Any)
}

interface HasDpr : HasResizeAttribute {
    fun dpr(dpr: Any) = add(dpr.cldAsDpr())
    fun dpr(dpr: Float) = dpr(dpr as Any)
}

interface HasGravity : HasResizeAttribute {
    fun gravity(gravity: Gravity) = add(gravity)
}

interface HasXY : HasResizeAttribute {
    fun x(x: Any) = add(x.cldAsX())
    fun x(x: Int) = x(x as Any)
    fun x(x: Float) = x(x as Any)

    fun y(y: Any) = add(y.cldAsY())
    fun y(y: Int) = y(y as Any)
    fun y(y: Float) = y(y as Any)
}

interface HasZoom : HasResizeAttribute {
    fun zoom(zoom: Any) = add(zoom.cldAsZoom())
    fun zoom(zoom: Int) = zoom(zoom as Any)
    fun zoom(zoom: Float) = zoom(zoom as Any)
}

interface HasMode : HasResizeAttribute {
    fun resizeMode(mode: ResizeMode) = add(mode.asFlag())
}

interface HasBackground : HasResizeAttribute {
    fun background(color: Color) = add(color.cldAsBackground())

    fun background(background: Background) {
        background.getParams().forEach { add(it.value) }
    }
}

interface HasIgnoreAspectRatio : HasResizeAttribute {
    fun ignoreAspectRatio() = add(Flag.ignoreAspectRatio().cldAsFlag())
}

interface ResizeCommon : HasWidth, HasHeight, HasAspectRatio, HasDpr, HasMode, HasIgnoreAspectRatio

interface HasResizeAttribute {
    var params: MutableList<Param>
    fun add(param: Param)
}