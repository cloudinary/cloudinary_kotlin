package com.cloudinary.transformation.resize

import com.cloudinary.transformation.*

class Scale internal constructor(params: Map<String, Param>) : Resize(params) {

    class Builder : BaseBuilder<Scale>() {

        fun width(width: Int) = apply { this.width = width }
        fun height(height: Int) = apply { this.height = height }
        fun aspectRatio(aspectRatio: Float) = apply { this.aspectRatio = aspectRatio }
        fun dpr(dpr: Float) = apply { this.dpr = dpr }
        fun width(width: Any) = apply { this.width = width }
        fun height(height: Any) = apply { this.height = height }
        fun aspectRatio(aspectRatio: Any) = apply { this.aspectRatio = aspectRatio }
        fun dpr(dpr: Any) = apply { this.dpr = dpr }
        fun mode(mode: ResizeMode) = apply { this.mode = mode }

        override fun build() = Scale(
            buildParameters(
                cropMode = "scale"
            )
        )
    }
}

class Crop internal constructor(params: Map<String, Param>) : Resize(params) {
    class Builder : BaseBuilder<Crop>() {

        fun width(width: Int) = apply { this.width = width }
        fun height(height: Int) = apply { this.height = height }
        fun aspectRatio(aspectRatio: Float) = apply { this.aspectRatio = aspectRatio }
        fun dpr(dpr: Float) = apply { this.dpr = dpr }
        fun width(width: Any) = apply { this.width = width }
        fun height(height: Any) = apply { this.height = height }
        fun aspectRatio(aspectRatio: Any) = apply { this.aspectRatio = aspectRatio }
        fun dpr(dpr: Any) = apply { this.dpr = dpr }
        fun mode(mode: ResizeMode) = apply { this.mode = mode }
        fun gravity(gravity: Gravity) = apply { this.gravity = gravity }
        fun zoom(zoom: Float) = apply { this.zoom = zoom }
        fun zoom(zoom: Any) = apply { this.zoom = zoom }
        fun x(x: Int) = apply { this.x = x }
        fun x(x: Any) = apply { this.x = x }
        fun x(x: Float) = apply { this.x = x }
        fun y(y: Int) = apply { this.y = y }
        fun y(y: Any) = apply { this.y = y }
        fun y(y: Float) = apply { this.y = y }

        override fun build() = Crop(buildParameters("crop"))
    }
}

class Fit internal constructor(params: Map<String, Param>) : Resize(params) {
    class Builder : BaseBuilder<Fit>() {

        fun width(width: Int) = apply { this.width = width }
        fun height(height: Int) = apply { this.height = height }
        fun aspectRatio(aspectRatio: Float) = apply { this.aspectRatio = aspectRatio }
        fun dpr(dpr: Float) = apply { this.dpr = dpr }
        fun width(width: Any) = apply { this.width = width }
        fun height(height: Any) = apply { this.height = height }
        fun aspectRatio(aspectRatio: Any) = apply { this.aspectRatio = aspectRatio }
        fun dpr(dpr: Any) = apply { this.dpr = dpr }
        fun mode(mode: ResizeMode) = apply { this.mode = mode }

        override fun build() = Fit(buildParameters("fit"))
    }
}

class Limit internal constructor(params: Map<String, Param>) : Resize(params) {
    class Builder : BaseBuilder<Limit>() {

        fun width(width: Int) = apply { this.width = width }
        fun height(height: Int) = apply { this.height = height }
        fun aspectRatio(aspectRatio: Float) = apply { this.aspectRatio = aspectRatio }
        fun dpr(dpr: Float) = apply { this.dpr = dpr }
        fun width(width: Any) = apply { this.width = width }
        fun height(height: Any) = apply { this.height = height }
        fun aspectRatio(aspectRatio: Any) = apply { this.aspectRatio = aspectRatio }
        fun dpr(dpr: Any) = apply { this.dpr = dpr }
        fun mode(mode: ResizeMode) = apply { this.mode = mode }

        override fun build() = Limit(buildParameters("limit"))
    }
}

class MinimumFit internal constructor(params: Map<String, Param>) : Resize(params) {
    class Builder : BaseBuilder<MinimumFit>() {

        fun width(width: Int) = apply { this.width = width }
        fun height(height: Int) = apply { this.height = height }
        fun aspectRatio(aspectRatio: Float) = apply { this.aspectRatio = aspectRatio }
        fun dpr(dpr: Float) = apply { this.dpr = dpr }
        fun width(width: Any) = apply { this.width = width }
        fun height(height: Any) = apply { this.height = height }
        fun aspectRatio(aspectRatio: Any) = apply { this.aspectRatio = aspectRatio }
        fun dpr(dpr: Any) = apply { this.dpr = dpr }
        fun mode(mode: ResizeMode) = apply { this.mode = mode }

        override fun build() = MinimumFit(buildParameters("mfit"))
    }
}

class Fill internal constructor(params: Map<String, Param>) : Resize(params) {
    class Builder : BaseBuilder<Fill>() {

        fun width(width: Int) = apply { this.width = width }
        fun height(height: Int) = apply { this.height = height }
        fun aspectRatio(aspectRatio: Float) = apply { this.aspectRatio = aspectRatio }
        fun dpr(dpr: Float) = apply { this.dpr = dpr }
        fun width(width: Any) = apply { this.width = width }
        fun height(height: Any) = apply { this.height = height }
        fun aspectRatio(aspectRatio: Any) = apply { this.aspectRatio = aspectRatio }
        fun dpr(dpr: Any) = apply { this.dpr = dpr }
        fun mode(mode: ResizeMode) = apply { this.mode = mode }
        fun gravity(gravity: Gravity) = apply { this.gravity = gravity }

        override fun build() = Fill(buildParameters("fill"))
    }
}

class LimitFill internal constructor(params: Map<String, Param>) : Resize(params) {
    class Builder : BaseBuilder<LimitFill>() {

        fun width(width: Int) = apply { this.width = width }
        fun height(height: Int) = apply { this.height = height }
        fun aspectRatio(aspectRatio: Float) = apply { this.aspectRatio = aspectRatio }
        fun dpr(dpr: Float) = apply { this.dpr = dpr }
        fun width(width: Any) = apply { this.width = width }
        fun height(height: Any) = apply { this.height = height }
        fun aspectRatio(aspectRatio: Any) = apply { this.aspectRatio = aspectRatio }
        fun dpr(dpr: Any) = apply { this.dpr = dpr }
        fun mode(mode: ResizeMode) = apply { this.mode = mode }
        fun gravity(gravity: Gravity) = apply { this.gravity = gravity }

        override fun build() = LimitFill(buildParameters("lfill"))
    }
}

class Pad internal constructor(params: Map<String, Param>) : Resize(params) {
    class Builder : BaseBuilder<Pad>() {

        fun width(width: Int) = apply { this.width = width }
        fun height(height: Int) = apply { this.height = height }
        fun aspectRatio(aspectRatio: Float) = apply { this.aspectRatio = aspectRatio }
        fun dpr(dpr: Float) = apply { this.dpr = dpr }
        fun width(width: Any) = apply { this.width = width }
        fun height(height: Any) = apply { this.height = height }
        fun aspectRatio(aspectRatio: Any) = apply { this.aspectRatio = aspectRatio }
        fun dpr(dpr: Any) = apply { this.dpr = dpr }
        fun mode(mode: ResizeMode) = apply { this.mode = mode }
        fun gravity(gravity: Gravity) = apply { this.gravity = gravity }
        fun x(x: Int) = apply { this.x = x }
        fun x(x: Any) = apply { this.x = x }
        fun x(x: Float) = apply { this.x = x }
        fun y(y: Int) = apply { this.y = y }
        fun y(y: Any) = apply { this.y = y }
        fun y(y: Float) = apply { this.y = y }

        override fun build() = Pad(buildParameters("pad"))
    }
}

class LimitPad internal constructor(params: Map<String, Param>) : Resize(params) {
    class Builder : BaseBuilder<LimitPad>() {

        fun width(width: Int) = apply { this.width = width }
        fun height(height: Int) = apply { this.height = height }
        fun aspectRatio(aspectRatio: Float) = apply { this.aspectRatio = aspectRatio }
        fun dpr(dpr: Float) = apply { this.dpr = dpr }
        fun width(width: Any) = apply { this.width = width }
        fun height(height: Any) = apply { this.height = height }
        fun aspectRatio(aspectRatio: Any) = apply { this.aspectRatio = aspectRatio }
        fun dpr(dpr: Any) = apply { this.dpr = dpr }
        fun mode(mode: ResizeMode) = apply { this.mode = mode }
        fun gravity(gravity: Gravity) = apply { this.gravity = gravity }
        fun x(x: Int) = apply { this.x = x }
        fun x(x: Any) = apply { this.x = x }
        fun x(x: Float) = apply { this.x = x }
        fun y(y: Int) = apply { this.y = y }
        fun y(y: Any) = apply { this.y = y }
        fun y(y: Float) = apply { this.y = y }

        override fun build() = LimitPad(buildParameters("lpad"))
    }
}

class MinimumPad internal constructor(params: Map<String, Param>) : Resize(params) {
    class Builder : BaseBuilder<MinimumPad>() {

        fun width(width: Int) = apply { this.width = width }
        fun height(height: Int) = apply { this.height = height }
        fun aspectRatio(aspectRatio: Float) = apply { this.aspectRatio = aspectRatio }
        fun dpr(dpr: Float) = apply { this.dpr = dpr }
        fun width(width: Any) = apply { this.width = width }
        fun height(height: Any) = apply { this.height = height }
        fun aspectRatio(aspectRatio: Any) = apply { this.aspectRatio = aspectRatio }
        fun dpr(dpr: Any) = apply { this.dpr = dpr }
        fun mode(mode: ResizeMode) = apply { this.mode = mode }
        fun gravity(gravity: Gravity) = apply { this.gravity = gravity }
        fun x(x: Int) = apply { this.x = x }
        fun x(x: Any) = apply { this.x = x }
        fun x(x: Float) = apply { this.x = x }
        fun y(y: Int) = apply { this.y = y }
        fun y(y: Any) = apply { this.y = y }
        fun y(y: Float) = apply { this.y = y }

        override fun build() = MinimumPad(buildParameters("mpad"))
    }
}

class FillPad internal constructor(params: Map<String, Param>) : Resize(params) {
    class Builder : BaseBuilder<FillPad>() {

        fun width(width: Int) = apply { this.width = width }
        fun height(height: Int) = apply { this.height = height }
        fun aspectRatio(aspectRatio: Float) = apply { this.aspectRatio = aspectRatio }
        fun dpr(dpr: Float) = apply { this.dpr = dpr }
        fun width(width: Any) = apply { this.width = width }
        fun height(height: Any) = apply { this.height = height }
        fun aspectRatio(aspectRatio: Any) = apply { this.aspectRatio = aspectRatio }
        fun dpr(dpr: Any) = apply { this.dpr = dpr }
        fun mode(mode: ResizeMode) = apply { this.mode = mode }
        fun gravity(gravity: Gravity) = apply { this.gravity = gravity }
        fun x(x: Int) = apply { this.x = x }
        fun x(x: Any) = apply { this.x = x }
        fun x(x: Float) = apply { this.x = x }
        fun y(y: Int) = apply { this.y = y }
        fun y(y: Any) = apply { this.y = y }
        fun y(y: Float) = apply { this.y = y }

        override fun build() = FillPad(buildParameters("fill_pad"))
    }
}

class Thumb internal constructor(params: Map<String, Param>) : Resize(params) {
    class Builder : BaseBuilder<Thumb>() {

        fun width(width: Int) = apply { this.width = width }
        fun height(height: Int) = apply { this.height = height }
        fun aspectRatio(aspectRatio: Float) = apply { this.aspectRatio = aspectRatio }
        fun dpr(dpr: Float) = apply { this.dpr = dpr }
        fun width(width: Any) = apply { this.width = width }
        fun height(height: Any) = apply { this.height = height }
        fun aspectRatio(aspectRatio: Any) = apply { this.aspectRatio = aspectRatio }
        fun dpr(dpr: Any) = apply { this.dpr = dpr }
        fun mode(mode: ResizeMode) = apply { this.mode = mode }
        fun gravity(gravity: Gravity) = apply { this.gravity = gravity }
        fun x(x: Int) = apply { this.x = x }
        fun x(x: Any) = apply { this.x = x }
        fun x(x: Float) = apply { this.x = x }
        fun y(y: Int) = apply { this.y = y }
        fun y(y: Any) = apply { this.y = y }
        fun y(y: Float) = apply { this.y = y }
        fun zoom(zoom: Float) = apply { this.zoom = zoom }
        fun zoom(zoom: Any) = apply { this.zoom = zoom }

        override fun build() = Thumb(buildParameters("thumb"))
    }
}

class ImaggaCrop internal constructor(params: Map<String, Param>) : Resize(params) {

    class Builder : BaseBuilder<ImaggaCrop>() {

        fun width(width: Int) = apply { this.width = width }
        fun height(height: Int) = apply { this.height = height }
        fun aspectRatio(aspectRatio: Float) = apply { this.aspectRatio = aspectRatio }
        fun dpr(dpr: Float) = apply { this.dpr = dpr }
        fun width(width: Any) = apply { this.width = width }
        fun height(height: Any) = apply { this.height = height }
        fun aspectRatio(aspectRatio: Any) = apply { this.aspectRatio = aspectRatio }
        fun dpr(dpr: Any) = apply { this.dpr = dpr }
        fun mode(mode: ResizeMode) = apply { this.mode = mode }

        override fun build() = ImaggaCrop(buildParameters("imagga_crop"))
    }
}

class ImaggaScale internal constructor(params: Map<String, Param>) : Resize(params) {

    class Builder : BaseBuilder<ImaggaScale>() {

        fun width(width: Int) = apply { this.width = width }
        fun height(height: Int) = apply { this.height = height }
        fun aspectRatio(aspectRatio: Float) = apply { this.aspectRatio = aspectRatio }
        fun dpr(dpr: Float) = apply { this.dpr = dpr }
        fun width(width: Any) = apply { this.width = width }
        fun height(height: Any) = apply { this.height = height }
        fun aspectRatio(aspectRatio: Any) = apply { this.aspectRatio = aspectRatio }
        fun dpr(dpr: Any) = apply { this.dpr = dpr }
        fun mode(mode: ResizeMode) = apply { this.mode = mode }

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

