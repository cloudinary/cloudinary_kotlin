package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.testParam
import com.cloudinary.transformation.resize.Resize
import org.junit.Test

class ResizeTest {

    @Test
    fun testScale() {

        val resize: Resize = Resize.scale {
            width(100)
            dpr(2.5f)
        }

        Transformation().resize(resize)
        cldAssert("c_scale,test_param,w_100", Resize.scale { width(100) }.add(testParam))
        cldAssert("c_scale,w_100", Resize.scale { width(100) })
        cldAssert("c_scale,w_1.0", Resize.scale { width(1f) })
        cldAssert("c_scale,h_100,w_100", Resize.scale { width(100).height(100) })
        cldAssert("c_scale,h_100,w_100", Resize.scale { width(100).height(100) })
        cldAssert("c_scale,h_1.1,w_0.5", Resize.scale { width(0.5f).height(1.1f) })
        cldAssert("ar_1.5,c_scale,w_100", Resize.scale { width(100).aspectRatio(1.5f) })
        cldAssert(
            "ar_1.5,c_scale,dpr_2.0,h_100",
            Resize.scale { height(100).aspectRatio(1.5f).dpr(2f) }
        )
    }

    @Test
    fun testFit() {
        cldAssert("c_fit,w_100", Resize.fit { width(100) })
        cldAssert("c_fit,w_1.0", Resize.fit { width(1f) })
        cldAssert("c_fit,h_100,w_100", Resize.fit { width(100).height(100) })
        cldAssert("c_fit,h_100,w_100", Resize.fit { width(100).height(100) })
        cldAssert("c_fit,h_1.1,w_0.5", Resize.fit { width(0.5f).height(1.1f) })
        cldAssert("ar_1.5,c_fit,w_100", Resize.fit { width(100).aspectRatio(1.5f) })
        cldAssert(
            "ar_1.5,c_fit,dpr_2.0,h_100",
            Resize.fit { height(100).aspectRatio(1.5f).dpr(2f) }
        )
    }

    @Test
    fun testLimitFit() {
        cldAssert("c_limit,w_100", Resize.limitFit { width(100) })
        cldAssert("c_limit,w_1.0", Resize.limitFit { width(1f) })
        cldAssert("c_limit,h_100,w_100", Resize.limitFit { width(100).height(100) })
        cldAssert("c_limit,h_100,w_100", Resize.limitFit { width(100).height(100) })
        cldAssert("c_limit,h_1.1,w_0.5", Resize.limitFit { width(0.5f).height(1.1f) })
        cldAssert("ar_1.5,c_limit,w_100", Resize.limitFit { width(100).aspectRatio(1.5f) })
        cldAssert(
            "ar_1.5,c_limit,dpr_2.0,h_100",
            Resize.limitFit { height(100).aspectRatio(1.5f).dpr(2f) }
        )
    }

    @Test
    fun testMinimumFit() {
        cldAssert("c_mfit,w_100", Resize.minimumFit { width(100) })
        cldAssert("c_mfit,w_1.0", Resize.minimumFit { width(1f) })
        cldAssert("c_mfit,h_100,w_100", Resize.minimumFit { width(100).height(100) })
        cldAssert("c_mfit,h_100,w_100", Resize.minimumFit { width(100).height(100) })
        cldAssert("c_mfit,h_1.1,w_0.5", Resize.minimumFit { width(0.5f).height(1.1f) })
        cldAssert("ar_1.5,c_mfit,w_100", Resize.minimumFit { width(100).aspectRatio(1.5f) })
        cldAssert(
            "ar_1.5,c_mfit,dpr_2.0,h_100",
            Resize.minimumFit { height(100).aspectRatio(1.5f).dpr(2f) }
        )
    }

    @Test
    fun testFill() {
        cldAssert("c_fill,w_100", Resize.fill { width(100) })
        cldAssert("c_fill,w_1.0", Resize.fill { width(1f) })
        cldAssert("c_fill,h_100,w_100", Resize.fill { width(100).height(100) })
        cldAssert("c_fill,h_100,w_100", Resize.fill { width(100).height(100) })
        cldAssert("c_fill,h_1.1,w_0.5", Resize.fill { width(0.5f).height(1.1f) })
        cldAssert("ar_1.5,c_fill,w_100", Resize.fill { width(100).aspectRatio(1.5f) })
        cldAssert(
            "ar_1.5,c_fill,dpr_2.0,h_100",
            Resize.fill { height(100).aspectRatio(1.5f).dpr(2f) })
        cldAssert(
            "ar_1.5,c_fill,dpr_2.0,g_north,h_100",
            Resize.fill {
                height(100).aspectRatio(1.5f).dpr(2f).gravity(Gravity.direction(Direction.NORTH))
            }
        )
    }

    @Test
    fun testLimitFill() {
        cldAssert("c_lfill,w_100", Resize.limitFill { width(100) })
        cldAssert("c_lfill,w_1.0", Resize.limitFill { width(1f) })
        cldAssert("c_lfill,h_100,w_100", Resize.limitFill { width(100).height(100) })
        cldAssert("c_lfill,h_100,w_100", Resize.limitFill { width(100).height(100) })
        cldAssert("c_lfill,h_1.1,w_0.5", Resize.limitFill { width(0.5f).height(1.1f) })
        cldAssert("ar_1.5,c_lfill,w_100", Resize.limitFill { width(100).aspectRatio(1.5f) })
        cldAssert(
            "ar_1.5,c_lfill,dpr_2.0,h_100",
            Resize.limitFill { height(100).aspectRatio(1.5f).dpr(2f) }
        )
        cldAssert(
            "ar_1.5,c_lfill,dpr_2.0,g_north,h_100",
            Resize.limitFill {
                height(100).aspectRatio(1.5f).dpr(2f).gravity(Gravity.direction(Direction.NORTH))
            }
        )
    }

    @Test
    fun testPad() {
        cldAssert("c_pad,w_100", Resize.pad { width(100) })
        cldAssert("c_pad,w_1.0", Resize.pad { width(1f) })
        cldAssert("c_pad,h_100,w_100", Resize.pad { width(100).height(100) })
        cldAssert("c_pad,h_100,w_100", Resize.pad { width(100).height(100) })
        cldAssert("c_pad,h_1.1,w_0.5", Resize.pad { width(0.5f).height(1.1f) })
        cldAssert("ar_1.5,c_pad,w_100", Resize.pad { width(100).aspectRatio(1.5f) })
        cldAssert(
            "ar_1.5,c_pad,dpr_2.0,h_100",
            Resize.pad { height(100).aspectRatio(1.5f).dpr(2f) })
        cldAssert(
            "ar_1.5,c_pad,dpr_2.0,g_north,h_100",
            Resize.pad { height(100).aspectRatio(1.5f).dpr(2f).gravity(Gravity.direction(Direction.NORTH)) }
        )
    }

    @Test
    fun testLimitPad() {
        cldAssert("c_lpad,w_100", Resize.limitPad { width(100) })
        cldAssert("c_lpad,w_1.0", Resize.limitPad { width(1f) })
        cldAssert("c_lpad,h_100,w_100", Resize.limitPad { width(100).height(100) })
        cldAssert("c_lpad,h_100,w_100", Resize.limitPad { width(100).height(100) })
        cldAssert("c_lpad,h_1.1,w_0.5", Resize.limitPad { width(0.5f).height(1.1f) })
        cldAssert("ar_1.5,c_lpad,w_100", Resize.limitPad { width(100).aspectRatio(1.5f) })
        cldAssert(
            "ar_1.5,c_lpad,dpr_2.0,h_100",
            Resize.limitPad { height(100).aspectRatio(1.5f).dpr(2f) }
        )
        cldAssert(
            "ar_1.5,c_lpad,dpr_2.0,g_north,h_100",
            Resize.limitPad {
                height(100).aspectRatio(1.5f).dpr(2f).gravity(Gravity.direction(Direction.NORTH))
            }
        )
    }

    @Test
    fun testMinimumPad() {
        cldAssert("c_mpad,w_100", Resize.minimumPad { width(100) })
        cldAssert("c_mpad,w_1.0", Resize.minimumPad { width(1f) })
        cldAssert("c_mpad,h_100,w_100", Resize.minimumPad { width(100).height(100) })
        cldAssert("c_mpad,h_100,w_100", Resize.minimumPad { width(100).height(100) })
        cldAssert("c_mpad,h_1.1,w_0.5", Resize.minimumPad { width(0.5f).height(1.1f) })
        cldAssert("ar_1.5,c_mpad,w_100", Resize.minimumPad { width(100).aspectRatio(1.5f) })
        cldAssert(
            "ar_1.5,c_mpad,dpr_2.0,h_100",
            Resize.minimumPad { height(100).aspectRatio(1.5f).dpr(2f) }
        )
        cldAssert(
            "ar_1.5,c_mpad,dpr_2.0,g_north,h_100",
            Resize.minimumPad {
                height(100).aspectRatio(1.5f).dpr(2f).gravity(Gravity.direction(Direction.NORTH))
            }
        )
    }

    @Test
    fun testFillPad() {
        cldAssert("c_fill_pad,g_auto,w_100", Resize.fillPad { width(100).gravity(Gravity.auto()) })
        cldAssert("c_fill_pad,g_auto,w_1.0", Resize.fillPad { width(1f).gravity(Gravity.auto()) })
        cldAssert(
            "c_fill_pad,g_auto,h_100,w_100",
            Resize.fillPad { width(100).height(100).gravity(Gravity.auto()) }
        )
        cldAssert(
            "c_fill_pad,g_auto,h_100,w_100",
            Resize.fillPad { width(100).height(100).gravity(Gravity.auto()) }
        )
        cldAssert(
            "c_fill_pad,g_auto,h_1.1,w_0.5",
            Resize.fillPad { width(0.5f).height(1.1f).gravity(Gravity.auto()) }
        )
        cldAssert(
            "ar_1.5,c_fill_pad,g_auto,w_100",
            Resize.fillPad { width(100).aspectRatio(1.5f).gravity(Gravity.auto()) }
        )
        cldAssert(
            "ar_1.5,c_fill_pad,dpr_2.0,g_auto,h_100",
            Resize.fillPad { height(100).gravity(Gravity.auto()).aspectRatio(1.5f).dpr(2f) }
        )
        cldAssert(
            "ar_1.5,c_fill_pad,dpr_2.0,g_auto,h_100",
            Resize.fillPad { height(100).aspectRatio(1.5f).dpr(2f).gravity(Gravity.auto()) })
    }

    @Test
    fun testCrop() {
        cldAssert("c_crop,w_100", Resize.crop { width(100) })
        cldAssert("c_crop,w_1.0", Resize.crop { width(1f) })
        cldAssert("c_crop,h_100,w_100", Resize.crop { width(100).height(100) })
        cldAssert("c_crop,h_100,w_100", Resize.crop { width(100).height(100) })
        cldAssert("c_crop,h_1.1,w_0.5", Resize.crop { width(0.5f).height(1.1f) })
        cldAssert("ar_1.5,c_crop,w_100", Resize.crop { width(100).aspectRatio(1.5f) })
        cldAssert(
            "ar_1.5,c_crop,dpr_2.0,h_100",
            Resize.crop { height(100).aspectRatio(1.5f).dpr(2f) })
        cldAssert(
            "ar_1.5,c_crop,dpr_2.0,g_north,h_100",
            Resize.crop {
                height(100).aspectRatio(1.5f).dpr(2f).gravity(Gravity.direction(Direction.NORTH))
            }
        )
        cldAssert(
            "ar_1.5,c_crop,dpr_2.0,g_north,h_100,z_1.5",
            Resize.crop {
                height(100).aspectRatio(1.5f).dpr(2f).gravity(Gravity.direction(Direction.NORTH))
                    .zoom(1.5f)
            }
        )
    }

    @Test
    fun testThumb() {
        cldAssert("c_thumb,w_100", Resize.thumb { width(100) })
        cldAssert("c_thumb,w_1.0", Resize.thumb { width(1f) })
        cldAssert("c_thumb,h_100,w_100", Resize.thumb { width(100).height(100) })
        cldAssert("c_thumb,h_100,w_100", Resize.thumb { width(100).height(100) })
        cldAssert("c_thumb,h_1.1,w_0.5", Resize.thumb { width(0.5f).height(1.1f) })
        cldAssert("ar_1.5,c_thumb,w_100", Resize.thumb { width(100).aspectRatio(1.5f) })
        cldAssert(
            "ar_1.5,c_thumb,dpr_2.0,h_100",
            Resize.thumb { height(100).aspectRatio(1.5f).dpr(2f) }
        )
        cldAssert(
            "ar_1.5,c_thumb,dpr_2.0,g_north,h_100",
            Resize.thumb {
                height(100).aspectRatio(1.5f).dpr(2f).gravity(Gravity.direction(Direction.NORTH))
            }
        )
        cldAssert(
            "ar_1.5,c_thumb,dpr_2.0,g_north,h_100,z_1.5",
            Resize.thumb {
                height(100).aspectRatio(1.5f).dpr(2f).gravity(Gravity.direction(Direction.NORTH))
                    .zoom(1.5f)
            }
        )
    }

    @Test
    fun testImaggaScale() {
        cldAssert("c_imagga_scale,w_100", Resize.imaggaScale { width(100) })
        cldAssert("c_imagga_scale,w_1.0", Resize.imaggaScale { width(1f) })
        cldAssert("c_imagga_scale,h_100,w_100", Resize.imaggaScale { width(100).height(100) })
        cldAssert("c_imagga_scale,h_100,w_100", Resize.imaggaScale { width(100).height(100) })
        cldAssert("c_imagga_scale,h_1.1,w_0.5", Resize.imaggaScale { width(0.5f).height(1.1f) })
        cldAssert(
            "ar_1.5,c_imagga_scale,w_100",
            Resize.imaggaScale { width(100).aspectRatio(1.5f) })
        cldAssert(
            "ar_1.5,c_imagga_scale,dpr_2.0,h_100",
            Resize.imaggaScale { height(100).aspectRatio(1.5f).dpr(2f) }
        )
    }

    @Test
    fun testImaggaCrop() {
        cldAssert("c_imagga_crop,w_100", Resize.imaggaCrop { width(100) })
        cldAssert("c_imagga_crop,w_1.0", Resize.imaggaCrop { width(1f) })
        cldAssert("c_imagga_crop,h_100,w_100", Resize.imaggaCrop { width(100).height(100) })
        cldAssert("c_imagga_crop,h_100,w_100", Resize.imaggaCrop { width(100).height(100) })
        cldAssert("c_imagga_crop,h_1.1,w_0.5", Resize.imaggaCrop { width(0.5f).height(1.1f) })
        cldAssert("ar_1.5,c_imagga_crop,w_100", Resize.imaggaCrop { width(100).aspectRatio(1.5f) })
        cldAssert(
            "ar_1.5,c_imagga_crop,dpr_2.0,h_100",
            Resize.imaggaCrop { height(100).aspectRatio(1.5f).dpr(2f) }
        )
    }
}