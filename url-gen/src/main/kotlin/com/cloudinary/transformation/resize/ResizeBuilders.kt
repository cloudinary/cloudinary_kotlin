package com.cloudinary.transformation.resize

import com.cloudinary.transformation.*

class Scale internal constructor(params: Map<String, Param>) : Resize(params) {

    class Builder : BaseBuilder<Scale>() {

        fun setWidth(width: Int) = apply { this.width = width }
        fun setHeight(height: Int) = apply { this.height = height }
        fun setAspectRatio(aspectRatio: Float) = apply { this.aspectRatio = aspectRatio }
        fun setDpr(dpr: Float) = apply { this.dpr = dpr }
        fun setWidth(width: Any) = apply { this.width = width }
        fun setHeight(height: Any) = apply { this.height = height }
        fun setAspectRatio(aspectRatio: Any) = apply { this.aspectRatio = aspectRatio }
        fun setDpr(dpr: Any) = apply { this.dpr = dpr }
        fun setMode(mode: ResizeMode) = apply { this.mode = mode }

        override fun build() = Scale(
            buildParameters(
                cropMode = "scale"
            )
        )
    }
}

class Crop internal constructor(params: Map<String, Param>) : Resize(params) {
    class Builder : BaseBuilder<Crop>() {

        fun setWidth(width: Int) = apply { this.width = width }
        fun setHeight(height: Int) = apply { this.height = height }
        fun setAspectRatio(aspectRatio: Float) = apply { this.aspectRatio = aspectRatio }
        fun setDpr(dpr: Float) = apply { this.dpr = dpr }
        fun setWidth(width: Any) = apply { this.width = width }
        fun setHeight(height: Any) = apply { this.height = height }
        fun setAspectRatio(aspectRatio: Any) = apply { this.aspectRatio = aspectRatio }
        fun setDpr(dpr: Any) = apply { this.dpr = dpr }
        fun setMode(mode: ResizeMode) = apply { this.mode = mode }
        fun setGravity(gravity: Gravity) = apply { this.gravity = gravity }
        fun setZoom(zoom: Float) = apply { this.zoom = zoom }
        fun setZoom(zoom: Any) = apply { this.zoom = zoom }
        fun setX(x: Int) = apply { this.x = x }
        fun setX(x: Any) = apply { this.x = x }
        fun setX(x: Float) = apply { this.x = x }
        fun setY(y: Int) = apply { this.y = y }
        fun setY(y: Any) = apply { this.y = y }
        fun setY(y: Float) = apply { this.y = y }

        override fun build() = Crop(buildParameters("crop"))
    }
}

class Fit internal constructor(params: Map<String, Param>) : Resize(params) {
    class Builder : BaseBuilder<Fit>() {

        fun setWidth(width: Int) = apply { this.width = width }
        fun setHeight(height: Int) = apply { this.height = height }
        fun setAspectRatio(aspectRatio: Float) = apply { this.aspectRatio = aspectRatio }
        fun setDpr(dpr: Float) = apply { this.dpr = dpr }
        fun setWidth(width: Any) = apply { this.width = width }
        fun setHeight(height: Any) = apply { this.height = height }
        fun setAspectRatio(aspectRatio: Any) = apply { this.aspectRatio = aspectRatio }
        fun setDpr(dpr: Any) = apply { this.dpr = dpr }
        fun setMode(mode: ResizeMode) = apply { this.mode = mode }

        override fun build() = Fit(buildParameters("fit"))
    }
}

class LimitFit internal constructor(params: Map<String, Param>) : Resize(params) {
    class Builder : BaseBuilder<LimitFit>() {

        fun setWidth(width: Int) = apply { this.width = width }
        fun setHeight(height: Int) = apply { this.height = height }
        fun setAspectRatio(aspectRatio: Float) = apply { this.aspectRatio = aspectRatio }
        fun setDpr(dpr: Float) = apply { this.dpr = dpr }
        fun setWidth(width: Any) = apply { this.width = width }
        fun setHeight(height: Any) = apply { this.height = height }
        fun setAspectRatio(aspectRatio: Any) = apply { this.aspectRatio = aspectRatio }
        fun setDpr(dpr: Any) = apply { this.dpr = dpr }
        fun setMode(mode: ResizeMode) = apply { this.mode = mode }

        override fun build() = LimitFit(buildParameters("limit"))
    }
}

class MinimumFit internal constructor(params: Map<String, Param>) : Resize(params) {
    class Builder : BaseBuilder<MinimumFit>() {

        fun setWidth(width: Int) = apply { this.width = width }
        fun setHeight(height: Int) = apply { this.height = height }
        fun setAspectRatio(aspectRatio: Float) = apply { this.aspectRatio = aspectRatio }
        fun setDpr(dpr: Float) = apply { this.dpr = dpr }
        fun setWidth(width: Any) = apply { this.width = width }
        fun setHeight(height: Any) = apply { this.height = height }
        fun setAspectRatio(aspectRatio: Any) = apply { this.aspectRatio = aspectRatio }
        fun setDpr(dpr: Any) = apply { this.dpr = dpr }
        fun setMode(mode: ResizeMode) = apply { this.mode = mode }

        override fun build() = MinimumFit(buildParameters("mfit"))
    }
}

class Fill internal constructor(params: Map<String, Param>) : Resize(params) {
    class Builder : BaseBuilder<Fill>() {

        fun setWidth(width: Int) = apply { this.width = width }
        fun setHeight(height: Int) = apply { this.height = height }
        fun setAspectRatio(aspectRatio: Float) = apply { this.aspectRatio = aspectRatio }
        fun setDpr(dpr: Float) = apply { this.dpr = dpr }
        fun setWidth(width: Any) = apply { this.width = width }
        fun setHeight(height: Any) = apply { this.height = height }
        fun setAspectRatio(aspectRatio: Any) = apply { this.aspectRatio = aspectRatio }
        fun setDpr(dpr: Any) = apply { this.dpr = dpr }
        fun setMode(mode: ResizeMode) = apply { this.mode = mode }
        fun setGravity(gravity: Gravity) = apply { this.gravity = gravity }

        override fun build() = Fill(buildParameters("fill"))
    }
}

class LimitFill internal constructor(params: Map<String, Param>) : Resize(params) {
    class Builder : BaseBuilder<LimitFill>() {

        fun setWidth(width: Int) = apply { this.width = width }
        fun setHeight(height: Int) = apply { this.height = height }
        fun setAspectRatio(aspectRatio: Float) = apply { this.aspectRatio = aspectRatio }
        fun setDpr(dpr: Float) = apply { this.dpr = dpr }
        fun setWidth(width: Any) = apply { this.width = width }
        fun setHeight(height: Any) = apply { this.height = height }
        fun setAspectRatio(aspectRatio: Any) = apply { this.aspectRatio = aspectRatio }
        fun setDpr(dpr: Any) = apply { this.dpr = dpr }
        fun setMode(mode: ResizeMode) = apply { this.mode = mode }
        fun setGravity(gravity: Gravity) = apply { this.gravity = gravity }

        override fun build() = LimitFill(buildParameters("lfill"))
    }
}

class Pad internal constructor(params: Map<String, Param>) : Resize(params) {
    class Builder : BaseBuilder<Pad>() {

        fun setWidth(width: Int) = apply { this.width = width }
        fun setHeight(height: Int) = apply { this.height = height }
        fun setAspectRatio(aspectRatio: Float) = apply { this.aspectRatio = aspectRatio }
        fun setDpr(dpr: Float) = apply { this.dpr = dpr }
        fun setWidth(width: Any) = apply { this.width = width }
        fun setHeight(height: Any) = apply { this.height = height }
        fun setAspectRatio(aspectRatio: Any) = apply { this.aspectRatio = aspectRatio }
        fun setDpr(dpr: Any) = apply { this.dpr = dpr }
        fun setMode(mode: ResizeMode) = apply { this.mode = mode }
        fun setGravity(gravity: Gravity) = apply { this.gravity = gravity }
        fun setX(x: Int) = apply { this.x = x }
        fun setX(x: Any) = apply { this.x = x }
        fun setX(x: Float) = apply { this.x = x }
        fun setY(y: Int) = apply { this.y = y }
        fun setY(y: Any) = apply { this.y = y }
        fun setY(y: Float) = apply { this.y = y }

        override fun build() = Pad(buildParameters("pad"))
    }
}

class LimitPad internal constructor(params: Map<String, Param>) : Resize(params) {
    class Builder : BaseBuilder<LimitPad>() {

        fun setWidth(width: Int) = apply { this.width = width }
        fun setHeight(height: Int) = apply { this.height = height }
        fun setAspectRatio(aspectRatio: Float) = apply { this.aspectRatio = aspectRatio }
        fun setDpr(dpr: Float) = apply { this.dpr = dpr }
        fun setWidth(width: Any) = apply { this.width = width }
        fun setHeight(height: Any) = apply { this.height = height }
        fun setAspectRatio(aspectRatio: Any) = apply { this.aspectRatio = aspectRatio }
        fun setDpr(dpr: Any) = apply { this.dpr = dpr }
        fun setMode(mode: ResizeMode) = apply { this.mode = mode }
        fun setGravity(gravity: Gravity) = apply { this.gravity = gravity }
        fun setX(x: Int) = apply { this.x = x }
        fun setX(x: Any) = apply { this.x = x }
        fun setX(x: Float) = apply { this.x = x }
        fun setY(y: Int) = apply { this.y = y }
        fun setY(y: Any) = apply { this.y = y }
        fun setY(y: Float) = apply { this.y = y }

        override fun build() = LimitPad(buildParameters("lpad"))
    }
}

class MinimumPad internal constructor(params: Map<String, Param>) : Resize(params) {
    class Builder : BaseBuilder<MinimumPad>() {

        fun setWidth(width: Int) = apply { this.width = width }
        fun setHeight(height: Int) = apply { this.height = height }
        fun setAspectRatio(aspectRatio: Float) = apply { this.aspectRatio = aspectRatio }
        fun setDpr(dpr: Float) = apply { this.dpr = dpr }
        fun setWidth(width: Any) = apply { this.width = width }
        fun setHeight(height: Any) = apply { this.height = height }
        fun setAspectRatio(aspectRatio: Any) = apply { this.aspectRatio = aspectRatio }
        fun setDpr(dpr: Any) = apply { this.dpr = dpr }
        fun setMode(mode: ResizeMode) = apply { this.mode = mode }
        fun setGravity(gravity: Gravity) = apply { this.gravity = gravity }
        fun setX(x: Int) = apply { this.x = x }
        fun setX(x: Any) = apply { this.x = x }
        fun setX(x: Float) = apply { this.x = x }
        fun setY(y: Int) = apply { this.y = y }
        fun setY(y: Any) = apply { this.y = y }
        fun setY(y: Float) = apply { this.y = y }

        override fun build() = MinimumPad(buildParameters("mpad"))
    }
}

class FillPad internal constructor(params: Map<String, Param>) : Resize(params) {
    class Builder : BaseBuilder<FillPad>() {

        fun setWidth(width: Int) = apply { this.width = width }
        fun setHeight(height: Int) = apply { this.height = height }
        fun setAspectRatio(aspectRatio: Float) = apply { this.aspectRatio = aspectRatio }
        fun setDpr(dpr: Float) = apply { this.dpr = dpr }
        fun setWidth(width: Any) = apply { this.width = width }
        fun setHeight(height: Any) = apply { this.height = height }
        fun setAspectRatio(aspectRatio: Any) = apply { this.aspectRatio = aspectRatio }
        fun setDpr(dpr: Any) = apply { this.dpr = dpr }
        fun setMode(mode: ResizeMode) = apply { this.mode = mode }
        fun setGravity(gravity: Gravity) = apply { this.gravity = gravity }
        fun setX(x: Int) = apply { this.x = x }
        fun setX(x: Any) = apply { this.x = x }
        fun setX(x: Float) = apply { this.x = x }
        fun setY(y: Int) = apply { this.y = y }
        fun setY(y: Any) = apply { this.y = y }
        fun setY(y: Float) = apply { this.y = y }

        override fun build() = FillPad(buildParameters("fill_pad"))
    }
}

class Thumb internal constructor(params: Map<String, Param>) : Resize(params) {
    class Builder : BaseBuilder<Thumb>() {

        fun setWidth(width: Int) = apply { this.width = width }
        fun setHeight(height: Int) = apply { this.height = height }
        fun setAspectRatio(aspectRatio: Float) = apply { this.aspectRatio = aspectRatio }
        fun setDpr(dpr: Float) = apply { this.dpr = dpr }
        fun setWidth(width: Any) = apply { this.width = width }
        fun setHeight(height: Any) = apply { this.height = height }
        fun setAspectRatio(aspectRatio: Any) = apply { this.aspectRatio = aspectRatio }
        fun setDpr(dpr: Any) = apply { this.dpr = dpr }
        fun setMode(mode: ResizeMode) = apply { this.mode = mode }
        fun setGravity(gravity: Gravity) = apply { this.gravity = gravity }
        fun setX(x: Int) = apply { this.x = x }
        fun setX(x: Any) = apply { this.x = x }
        fun setX(x: Float) = apply { this.x = x }
        fun setY(y: Int) = apply { this.y = y }
        fun setY(y: Any) = apply { this.y = y }
        fun setY(y: Float) = apply { this.y = y }
        fun setZoom(zoom: Float) = apply { this.zoom = zoom }
        fun setZoom(zoom: Any) = apply { this.zoom = zoom }

        override fun build() = Thumb(buildParameters("thumb"))
    }
}

class ImaggaCrop internal constructor(params: Map<String, Param>) : Resize(params) {

    class Builder : BaseBuilder<ImaggaCrop>() {

        fun setWidth(width: Int) = apply { this.width = width }
        fun setHeight(height: Int) = apply { this.height = height }
        fun setAspectRatio(aspectRatio: Float) = apply { this.aspectRatio = aspectRatio }
        fun setDpr(dpr: Float) = apply { this.dpr = dpr }
        fun setWidth(width: Any) = apply { this.width = width }
        fun setHeight(height: Any) = apply { this.height = height }
        fun setAspectRatio(aspectRatio: Any) = apply { this.aspectRatio = aspectRatio }
        fun setDpr(dpr: Any) = apply { this.dpr = dpr }
        fun setMode(mode: ResizeMode) = apply { this.mode = mode }

        override fun build() = ImaggaCrop(buildParameters("imagga_crop"))
    }
}

class ImaggaScale internal constructor(params: Map<String, Param>) : Resize(params) {

    class Builder : BaseBuilder<ImaggaScale>() {

        fun setWidth(width: Int) = apply { this.width = width }
        fun setHeight(height: Int) = apply { this.height = height }
        fun setAspectRatio(aspectRatio: Float) = apply { this.aspectRatio = aspectRatio }
        fun setDpr(dpr: Float) = apply { this.dpr = dpr }
        fun setWidth(width: Any) = apply { this.width = width }
        fun setHeight(height: Any) = apply { this.height = height }
        fun setAspectRatio(aspectRatio: Any) = apply { this.aspectRatio = aspectRatio }
        fun setDpr(dpr: Any) = apply { this.dpr = dpr }
        fun setMode(mode: ResizeMode) = apply { this.mode = mode }

        override fun build() = ImaggaScale(buildParameters("imagga_scale"))
    }
}

abstract class BaseBuilder<T> {
    protected var width: Any? = null
    protected var height: Any? = null
    internal var aspectRatio: Any? = null
    protected var dpr: Any? = null
    protected var mode: ResizeMode? = null
    protected var gravity: Gravity? = null
    protected var zoom: Any? = null
    protected var x: Any? = null
    protected var y: Any? = null

    abstract fun build(): T

    protected fun buildParameters(cropMode: String) =
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
            mode?.asFlag()
        ).cldToActionMap()
}

