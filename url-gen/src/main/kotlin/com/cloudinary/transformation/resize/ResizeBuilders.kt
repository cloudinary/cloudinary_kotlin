package com.cloudinary.transformation.resize

import com.cloudinary.transformation.*

class ScaleBuilder : BaseBuilder("scale"), ResizeCommon
class CropBuilder : BaseBuilder("crop"), ResizeCommon, HasGravity, HasZoom, HasXY
class FitBuilder : BaseBuilder("fit"), ResizeCommon
class LimitBuilder : BaseBuilder("limit"), ResizeCommon
class MinimumFitBuilder : BaseBuilder("mfit"), ResizeCommon
class FillBuilder : BaseBuilder("fill"), ResizeCommon, HasGravity
class LimitFillBuilder : BaseBuilder("lfill"), ResizeCommon, HasGravity
class PadBuilder : BaseBuilder("pad"), ResizeCommon, HasGravity, HasXY, HasBackground
class LimitPadBuilder : BaseBuilder("lpad"), ResizeCommon, HasGravity, HasXY, HasBackground
class MinimumPadBuilder : BaseBuilder("mpad"), ResizeCommon, HasGravity, HasXY, HasBackground
class FillPadBuilder : BaseBuilder("fill_pad"), ResizeCommon, HasGravity, HasXY, HasBackground
class ThumbBuilder : BaseBuilder("thumb"), ResizeCommon, HasGravity, HasXY, HasZoom
class ImaggaCropBuilder : BaseBuilder("imagga_crop"), ResizeCommon
class ImaggaScaleBuilder : BaseBuilder("imagga_scale"), ResizeCommon

open class BaseBuilder(private val cropMode: String) {

    var width: Any? = null
    var height: Any? = null
    var aspectRatio: Any? = null
    var dpr: Any? = null
    var mode: ResizeMode? = null
    var gravity: Gravity? = null
    var zoom: Any? = null
    var x: Any? = null
    var y: Any? = null
    var background: Color? = null

    fun build() = Resize(
        ParamsAction(
            listOfNotNull(
                cropMode.cldAsCrop(),
                width?.cldAsWidth(),
                height?.cldAsHeight(),
                aspectRatio?.cldAsAspectRatio(),
                dpr?.cldAsDpr(),
                gravity,
                x?.cldAsX(),
                y?.cldAsY(),
                zoom?.cldAsZoom(),
                mode?.asFlag(),
                background?.cldAsColor()
            )
        )
    )
}

interface HasWidth {
    var width: Any?
    fun width(width: Any) = apply { this.width = width }
    fun width(width: Int) = width(width as Any)
    fun width(width: Float) = width(width as Any)
}

interface HasHeight {
    var height: Any?
    fun height(height: Any) = apply { this.height = height }
    fun height(height: Int) = height(height as Any)
    fun height(height: Float) = height(height as Any)
}

interface HasAspectRatio {
    var aspectRatio: Any?

    fun aspectRatio(aspectRatio: Any) = apply { this.aspectRatio = aspectRatio }
    fun aspectRatio(aspectRatio: Int) = aspectRatio(aspectRatio as Any)
    fun aspectRatio(aspectRatio: Float) = aspectRatio(aspectRatio as Any)
}

interface HasDpr {
    var dpr: Any?

    fun dpr(dpr: Any) = apply { this.dpr = dpr }
    fun dpr(dpr: Float) = dpr(dpr as Any)
}

interface HasGravity {
    var gravity: Gravity?

    fun gravity(gravity: Gravity) = apply { this.gravity = gravity }
}

interface HasXY {
    var x: Any?
    var y: Any?

    fun x(x: Any) = apply { this.x = x }
    fun x(x: Int) = x(x as Any)
    fun x(x: Float) = x(x as Any)

    fun y(y: Any) = apply { this.y = y }
    fun y(y: Int) = y(y as Any)
    fun y(y: Float) = y(y as Any)
}

interface HasY {
    var y: Any?

    fun y(y: Any) = apply { this.y = y }
    fun y(y: Int) = y(y as Any)
    fun y(y: Float) = y(y as Any)
}

interface HasZoom {
    var zoom: Any?

    fun zoom(zoom: Any) = apply { this.zoom = zoom }
    fun zoom(zoom: Int) = zoom(zoom as Any)
    fun zoom(zoom: Float) = zoom(zoom as Any)
}

interface HasMode {
    var mode: ResizeMode?

    fun mode(mode: ResizeMode) = apply { this.mode = mode }
}

interface HasBackground {
    var background: Color?

    fun background(color: Color) = apply { this.background = color }
}

interface ResizeCommon : HasWidth, HasHeight, HasAspectRatio, HasDpr, HasMode