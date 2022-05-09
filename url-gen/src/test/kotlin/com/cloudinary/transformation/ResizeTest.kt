package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.background.Background
import com.cloudinary.transformation.expression.Expression
import com.cloudinary.transformation.gravity.Gravity
import com.cloudinary.transformation.resize.AspectRatio
import com.cloudinary.transformation.resize.Resize
import com.cloudinary.transformation.resize.Resize.Companion.scale
import org.junit.Test

class ResizeTest {

    @Test
    fun testScale() {
        cldAssert("c_scale,w_100", scale {
            width(100)
        })
        cldAssert("c_scale,w_100", scale(100))

        cldAssert("c_scale,w_1.0", scale {
            width(1f)
        })
        cldAssert("c_scale,h_100,w_100", scale {
            width(100)
            height(100)
        })
        cldAssert("c_scale,h_100,w_100", scale {
            width(100)
            height(100)
        })
        cldAssert("c_scale,h_1.1,w_0.5", scale {
            width(0.5f)
            height(1.1f)
        })
        cldAssert("ar_1.5,c_scale,w_100", scale {
            width(100)
            aspectRatio(1.5f)
        })
        cldAssert(
            "ar_1.5,c_scale,h_100",
            scale {
                height(100)
                aspectRatio(1.5f)
            }
        )

        cldAssert("c_scale,w_100", scale {
            width(100)
            liquidRescaling(false)
        })

        cldAssert("c_scale,g_liquid,w_100", scale {
            width(100)
            liquidRescaling(true)
        })
        cldAssert("ar_w_mul_2,c_scale", Resize.scale {
            aspectRatio(Expression("width * 2"))
        })
        cldAssert("c_scale,h_w_mul_2", Resize.scale {
            height(Expression("width * 2"))
        })
        cldAssert("c_scale,w_w_mul_2", Resize.scale {
            width(Expression("width * 2"))
        })
        cldAssert("c_crop,x_w_mul_2", Resize.crop {
            x(Expression("width * 2"))
        })
        cldAssert("c_crop,y_w_mul_2", Resize.crop {
            y(Expression("width * 2"))
        })
        cldAssert("c_crop,z_w_mul_2", Resize.crop {
            zoom(Expression("width * 2"))
        })
    }

    @Test
    fun testFit() {
        cldAssert("c_fit,w_100", Resize.fit { width(100) })
        cldAssert("c_fit,w_1.0", Resize.fit { width(1f) })
        cldAssert("c_fit,h_100,w_100", Resize.fit {
            width(100)
            height(100)
        })
        cldAssert("c_fit,h_100,w_100", Resize.fit {
            width(100)
            height(100)
        })
        cldAssert("c_fit,h_1.1,w_0.5", Resize.fit {
            width(0.5f)
            height(1.1f)
        })
        cldAssert("ar_1.5,c_fit,w_100", Resize.fit {
            width(100)
            aspectRatio(1.5f)
        })
        cldAssert(
            "ar_1.5,c_fit,h_100",
            Resize.fit {
                height(100)
                aspectRatio(1.5f)
            }
        )
    }

    @Test
    fun testLimitFit() {
        cldAssert("c_limit,w_100", Resize.limitFit { width(100) })
        cldAssert("c_limit,w_1.0", Resize.limitFit { width(1f) })
        cldAssert("c_limit,h_100,w_100", Resize.limitFit {
            width(100)
            height(100)
        })
        cldAssert("c_limit,h_100,w_100", Resize.limitFit {
            width(100)
            height(100)
        })
        cldAssert("c_limit,h_1.1,w_0.5", Resize.limitFit {
            width(0.5f)
            height(1.1f)
        })
        cldAssert("ar_1.5,c_limit,w_100", Resize.limitFit {
            width(100)
            aspectRatio(1.5f)
        })
        cldAssert(
            "ar_1.5,c_limit,h_100",
            Resize.limitFit {
                height(100)
                aspectRatio(1.5f)
            }
        )
    }

    @Test
    fun testMinimumFit() {
        cldAssert("c_mfit,w_100", Resize.minimumFit { width(100) })
        cldAssert("c_mfit,w_1.0", Resize.minimumFit { width(1f) })
        cldAssert("c_mfit,h_100,w_100", Resize.minimumFit {
            width(100)
            height(100)
        })
        cldAssert("c_mfit,h_100,w_100", Resize.minimumFit {
            width(100)
            height(100)
        })
        cldAssert("c_mfit,h_1.1,w_0.5", Resize.minimumFit {
            width(0.5f)
            height(1.1f)
        })
        cldAssert("ar_1.5,c_mfit,w_100", Resize.minimumFit {
            width(100)
            aspectRatio(1.5f)
        })
        cldAssert(
            "ar_1.5,c_mfit,h_100",
            Resize.minimumFit {
                height(100)
                aspectRatio(1.5f)
            }
        )
    }

    @Test
    fun testFill() {
        cldAssert("c_fill,g_xy_center,x_100,y_100", Resize.fill {
            gravity(Gravity.xyCenter())
            offsetX(100)
            offsetY(100)})
        cldAssert("c_fill,g_xy_center,x_100,y_100", Resize.fill {
            gravity(Gravity.xyCenter())
            x(100)
            y(100)})
        cldAssert("c_fill,w_100", Resize.fill { width(100) })
        cldAssert("c_fill,w_1.0", Resize.fill { width(1f) })
        cldAssert("c_fill,h_100,w_100", Resize.fill {
            width(100)
            height(100)
        })
        cldAssert("c_fill,h_100,w_100", Resize.fill {
            width(100)
            height(100)
        })
        cldAssert("c_fill,h_1.1,w_0.5", Resize.fill {
            width(0.5f)
            height(1.1f)
        })
        cldAssert("ar_1.5,c_fill,w_100", Resize.fill {
            width(100)
            aspectRatio(1.5f)
        })
        cldAssert(
            "ar_1.5,c_fill,h_100",
            Resize.fill {
                height(100)
                aspectRatio(1.5f)
            })
        cldAssert(
            "ar_1.5,c_fill,g_north,h_100",
            Resize.fill {
                height(100)
                aspectRatio(1.5f)
                gravity(Gravity.north())
            }
        )
    }

    @Test
    fun testLimitFill() {
        cldAssert("c_lfill,w_100", Resize.limitFill { width(100) })
        cldAssert("c_lfill,w_1.0", Resize.limitFill { width(1f) })
        cldAssert("c_lfill,h_100,w_100", Resize.limitFill {
            width(100)
            height(100)
        })
        cldAssert("c_lfill,h_100,w_100", Resize.limitFill {
            width(100)
            height(100)
        })
        cldAssert("c_lfill,h_1.1,w_0.5", Resize.limitFill {
            width(0.5f)
            height(1.1f)
        })
        cldAssert("ar_1.5,c_lfill,w_100", Resize.limitFill {
            width(100)
            aspectRatio(1.5f)
        })
        cldAssert(
            "ar_1.5,c_lfill,h_100",
            Resize.limitFill {
                height(100)
                aspectRatio(1.5f)
            }
        )
        cldAssert(
            "ar_1.5,c_lfill,g_north,h_100",
            Resize.limitFill {
                height(100)
                aspectRatio(1.5f)
                gravity(Gravity.north())
            }
        )
    }

    @Test
    fun testPad() {
        cldAssert("c_pad,w_100", Resize.pad { width(100) })
        cldAssert("c_pad,w_1.0", Resize.pad { width(1f) })
        cldAssert("c_pad,h_100,w_100", Resize.pad {
            width(100)
            height(100)
        })
        cldAssert("c_pad,h_100,w_100", Resize.pad {
            width(100)
            height(100)
        })
        cldAssert("c_pad,h_1.1,w_0.5", Resize.pad {
            width(0.5f)
            height(1.1f)
        })
        cldAssert("ar_1.5,c_pad,w_100", Resize.pad {
            width(100)
            aspectRatio(1.5f)
        })
        cldAssert(
            "ar_1.5,c_pad,h_100",
            Resize.pad {
                height(100)
                aspectRatio(1.5f)
            })
        cldAssert(
            "ar_1.5,c_pad,g_north,h_100",
            Resize.pad {
                height(100)
                aspectRatio(1.5f)
                gravity(Gravity.north())
            }
        )

        cldAssert(
            "ar_1.5,b_black,c_pad,h_100",
            Resize.pad {
                height(100)
                aspectRatio(1.5f)
                background(Background.color(Color.BLACK))
            }
        )

        cldAssert(
            "ar_1.5,b_auto:predominant,c_pad,h_100",
            Resize.pad {
                height(100)
                aspectRatio(1.5f)
                background(Background.predominant())
            }
        )

        cldAssert(
            "ar_1.5,b_auto:border_contrast,c_pad,h_100",
            Resize.pad {
                height(100)
                aspectRatio(1.5f)
                background(Background.border {
                    contrast()
                })
            }
        )
    }

    @Test
    fun testLimitPad() {
        cldAssert("c_lpad,w_100", Resize.limitPad { width(100) })
        cldAssert("c_lpad,w_1.0", Resize.limitPad { width(1f) })
        cldAssert("c_lpad,h_100,w_100", Resize.limitPad {
            width(100)
            height(100)
        })
        cldAssert("c_lpad,h_100,w_100", Resize.limitPad {
            width(100)
            height(100)
        })
        cldAssert("c_lpad,h_1.1,w_0.5", Resize.limitPad {
            width(0.5f)
            height(1.1f)
        })
        cldAssert("ar_1.5,c_lpad,w_100", Resize.limitPad {
            width(100)
            aspectRatio(1.5f)
        })
        cldAssert(
            "ar_1.5,c_lpad,h_100",
            Resize.limitPad {
                height(100)
                aspectRatio(1.5f)
            }
        )
        cldAssert(
            "ar_1.5,c_lpad,g_north,h_100",
            Resize.limitPad {
                height(100)
                aspectRatio(1.5f)
                gravity(Gravity.north())
            }
        )
    }

    @Test
    fun testMinimumPad() {
        cldAssert("c_mpad,w_100", Resize.minimumPad { width(100) })
        cldAssert("c_mpad,w_1.0", Resize.minimumPad { width(1f) })
        cldAssert("c_mpad,h_100,w_100", Resize.minimumPad {
            width(100)
            height(100)
        })
        cldAssert("c_mpad,h_100,w_100", Resize.minimumPad {
            width(100)
            height(100)
        })
        cldAssert("c_mpad,h_1.1,w_0.5", Resize.minimumPad {
            width(0.5f)
            height(1.1f)
        })
        cldAssert("ar_1.5,c_mpad,w_100", Resize.minimumPad {
            width(100)
            aspectRatio(1.5f)
        })
        cldAssert(
            "ar_1.5,c_mpad,h_100",
            Resize.minimumPad {
                height(100)
                aspectRatio(1.5f)
            }
        )
        cldAssert(
            "ar_1.5,c_mpad,g_north,h_100",
            Resize.minimumPad {
                height(100)
                aspectRatio(1.5f)
                gravity(Gravity.north())
            }
        )
    }

    @Test
    fun testFillPad() {
        cldAssert("c_fill_pad,g_auto,w_100", Resize.fillPad {
            width(100)
            gravity(Gravity.autoGravity())
        })
        cldAssert("c_fill_pad,g_auto,w_1.0", Resize.fillPad {
            width(1f)
            gravity(Gravity.autoGravity())
        })
        cldAssert(
            "c_fill_pad,g_auto,h_100,w_100",
            Resize.fillPad {
                width(100)
                height(100)
                gravity(Gravity.autoGravity())
            }
        )
        cldAssert(
            "c_fill_pad,g_auto,h_100,w_100",
            Resize.fillPad {
                width(100)
                height(100)
                gravity(Gravity.autoGravity())
            }
        )
        cldAssert(
            "c_fill_pad,g_auto,h_1.1,w_0.5",
            Resize.fillPad {
                width(0.5f)
                height(1.1f)
                gravity(Gravity.autoGravity())
            }
        )
        cldAssert(
            "ar_1.5,c_fill_pad,g_auto,w_100",
            Resize.fillPad {
                width(100)
                aspectRatio(1.5f)
                gravity(Gravity.autoGravity())
            }
        )
        cldAssert(
            "ar_1.5,c_fill_pad,g_auto,h_100",
            Resize.fillPad {
                height(100)
                gravity(Gravity.autoGravity())
                aspectRatio(1.5f)
            }
        )
        cldAssert(
            "ar_1.5,c_fill_pad,g_auto,h_100",
            Resize.fillPad {
                height(100)
                aspectRatio(1.5f)
                gravity(Gravity.autoGravity())
            })
    }

    @Test
    fun testCrop() {
        cldAssert("c_crop,w_100", Resize.crop { width(100) })
        cldAssert("c_crop,w_1.0", Resize.crop { width(1f) })
        cldAssert("c_crop,h_100,w_100", Resize.crop {
            width(100)
            height(100)
        })
        cldAssert("c_crop,h_100,w_100", Resize.crop {
            width(100)
            height(100)
        })
        cldAssert("c_crop,h_1.1,w_0.5", Resize.crop {
            width(0.5f)
            height(1.1f)
        })
        cldAssert("ar_1.5,c_crop,w_100", Resize.crop {
            width(100)
            aspectRatio(1.5f)
        })
        cldAssert(
            "ar_1.5,c_crop,h_100",
            Resize.crop {
                height(100)
                aspectRatio(1.5f)
            })
        cldAssert(
            "ar_1.5,c_crop,g_north,h_100",
            Resize.crop {
                height(100)
                aspectRatio(1.5f)
                gravity(Gravity.north())
            }
        )
        cldAssert(
            "ar_1.5,c_crop,g_north,h_100,z_1.5",
            Resize.crop {
                height(100)
                aspectRatio(1.5f)
                gravity(Gravity.north())
                zoom(1.5f)
            }
        )
    }

    @Test
    fun testThumb() {
        cldAssert("c_thumb,w_100", Resize.thumbnail { width(100) })
        cldAssert("c_thumb,w_1.0", Resize.thumbnail { width(1f) })
        cldAssert("c_thumb,h_100,w_100", Resize.thumbnail {
            width(100)
            height(100)
        })
        cldAssert("c_thumb,h_100,w_100", Resize.thumbnail {
            width(100)
            height(100)
        })
        cldAssert("c_thumb,h_1.1,w_0.5", Resize.thumbnail {
            width(0.5f)
            height(1.1f)
        })
        cldAssert("ar_1.5,c_thumb,w_100", Resize.thumbnail {
            width(100)
            aspectRatio(1.5f)
        })
        cldAssert(
            "ar_1.5,c_thumb,h_100",
            Resize.thumbnail {
                height(100)
                aspectRatio(1.5f)
            }
        )
        cldAssert(
            "ar_1.5,c_thumb,g_north,h_100",
            Resize.thumbnail {
                height(100)
                aspectRatio(1.5f)
                gravity(Gravity.north())
            }
        )
        cldAssert(
            "ar_1.5,c_thumb,g_north,h_100,z_1.5",
            Resize.thumbnail {
                height(100)
                aspectRatio(1.5f)
                gravity(Gravity.north())
                zoom(1.5f)
            }
        )
    }

    @Test
    fun testImaggaScale() {
        cldAssert("c_imagga_scale,w_100", Resize.imaggaScale { width(100) })
        cldAssert("c_imagga_scale,w_1.0", Resize.imaggaScale { width(1f) })
        cldAssert("c_imagga_scale,h_100,w_100", Resize.imaggaScale {
            width(100)
            height(100)
        })
        cldAssert("c_imagga_scale,h_100,w_100", Resize.imaggaScale {
            width(100)
            height(100)
        })
        cldAssert("c_imagga_scale,h_1.1,w_0.5", Resize.imaggaScale {
            width(0.5f)
            height(1.1f)
        })
        cldAssert(
            "ar_1.5,c_imagga_scale,w_100",
            Resize.imaggaScale {
                width(100)
                aspectRatio(1.5f)
            })
        cldAssert(
            "ar_1.5,c_imagga_scale,h_100",
            Resize.imaggaScale {
                height(100)
                aspectRatio(1.5f)
            }
        )
    }

    @Test
    fun testImaggaCrop() {
        cldAssert("c_imagga_crop,w_100", Resize.imaggaCrop { width(100) })
        cldAssert("c_imagga_crop,w_1.0", Resize.imaggaCrop { width(1f) })
        cldAssert("c_imagga_crop,h_100,w_100", Resize.imaggaCrop {
            width(100)
            height(100)
        })
        cldAssert("c_imagga_crop,h_100,w_100", Resize.imaggaCrop {
            width(100)
            height(100)
        })
        cldAssert("c_imagga_crop,h_1.1,w_0.5", Resize.imaggaCrop {
            width(0.5f)
            height(1.1f)
        })
        cldAssert("ar_1.5,c_imagga_crop,w_100", Resize.imaggaCrop {
            width(100)
            aspectRatio(1.5f)
        })
        cldAssert(
            "ar_1.5,c_imagga_crop,h_100",
            Resize.imaggaCrop {
                height(100)
                aspectRatio(1.5f)
            }
        )
    }

    @Test
    fun testGenericResize() {
        cldAssert(
            "c_mycrop,fl_ignore_aspect_ratio,g_west,h_2.5,w_1.5,x_4.5,y_5.5,z_3.5",
            Resize.generic("mycrop") {
                width(1.5f)
                height(2.5f)
                zoom(3.5f)
                gravity(Gravity.west())
                aspectRatio(AspectRatio.ignoreInitialAspectRatio())
                x(4.5f)
                y(5.5f)
            }
        )
    }
}