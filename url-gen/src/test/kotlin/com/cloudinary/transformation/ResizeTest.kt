package com.cloudinary.transformation

import com.cloudinary.cldAssertEqualsAsString
import com.cloudinary.testParam
import com.cloudinary.transformation.resize.*
import org.junit.Test

class ResizeTest {

    @Test
    fun testScale() {
        cldAssertEqualsAsString("c_scale,test_param,w_100", Resize.scale { setWidth(100) }.add(testParam))
        cldAssertEqualsAsString("c_scale,w_100", Resize.scale { setWidth(100) })
        cldAssertEqualsAsString("c_scale,w_1.0", Resize.scale { setWidth(1f) })
        cldAssertEqualsAsString("c_scale,h_100,w_100", Resize.scale { setWidth(100).setHeight(100) })
        cldAssertEqualsAsString("c_scale,h_100,w_100", Resize.scale { setWidth(100).setHeight(100) })
        cldAssertEqualsAsString("c_scale,h_1.1,w_0.5", Resize.scale { setWidth(0.5f).setHeight(1.1f) })
        cldAssertEqualsAsString("ar_1.5,c_scale,w_100", Resize.scale { setWidth(100).setAspectRatio(1.5f) })
        cldAssertEqualsAsString(
            "ar_1.5,c_scale,dpr_2.0,h_100",
            Resize.scale { setHeight(100).setAspectRatio(1.5f).setDpr(2f) }
        )
    }

    @Test
    fun testFit() {
        cldAssertEqualsAsString("c_fit,w_100", Resize.fit { setWidth(100) })
        cldAssertEqualsAsString("c_fit,w_1.0", Resize.fit { setWidth(1f) })
        cldAssertEqualsAsString("c_fit,h_100,w_100", Resize.fit { setWidth(100).setHeight(100) })
        cldAssertEqualsAsString("c_fit,h_100,w_100", Resize.fit { setWidth(100).setHeight(100) })
        cldAssertEqualsAsString("c_fit,h_1.1,w_0.5", Resize.fit { setWidth(0.5f).setHeight(1.1f) })
        cldAssertEqualsAsString("ar_1.5,c_fit,w_100", Resize.fit { setWidth(100).setAspectRatio(1.5f) })
        cldAssertEqualsAsString(
            "ar_1.5,c_fit,dpr_2.0,h_100",
            Resize.fit { setHeight(100).setAspectRatio(1.5f).setDpr(2f) }
        )
    }

    @Test
    fun testLimitFit() {
        cldAssertEqualsAsString("c_limit,w_100", Resize.limitFit { setWidth(100) })
        cldAssertEqualsAsString("c_limit,w_1.0", Resize.limitFit { setWidth(1f) })
        cldAssertEqualsAsString("c_limit,h_100,w_100", Resize.limitFit { setWidth(100).setHeight(100) })
        cldAssertEqualsAsString("c_limit,h_100,w_100", Resize.limitFit { setWidth(100).setHeight(100) })
        cldAssertEqualsAsString("c_limit,h_1.1,w_0.5", Resize.limitFit { setWidth(0.5f).setHeight(1.1f) })
        cldAssertEqualsAsString("ar_1.5,c_limit,w_100", Resize.limitFit { setWidth(100).setAspectRatio(1.5f) })
        cldAssertEqualsAsString(
            "ar_1.5,c_limit,dpr_2.0,h_100",
            Resize.limitFit { setHeight(100).setAspectRatio(1.5f).setDpr(2f) }
        )
    }

    @Test
    fun testMinimumFit() {
        cldAssertEqualsAsString("c_mfit,w_100", Resize.minimumFit { setWidth(100) })
        cldAssertEqualsAsString("c_mfit,w_1.0", Resize.minimumFit { setWidth(1f) })
        cldAssertEqualsAsString("c_mfit,h_100,w_100", Resize.minimumFit { setWidth(100).setHeight(100) })
        cldAssertEqualsAsString("c_mfit,h_100,w_100", Resize.minimumFit { setWidth(100).setHeight(100) })
        cldAssertEqualsAsString("c_mfit,h_1.1,w_0.5", Resize.minimumFit { setWidth(0.5f).setHeight(1.1f) })
        cldAssertEqualsAsString("ar_1.5,c_mfit,w_100", Resize.minimumFit { setWidth(100).setAspectRatio(1.5f) })
        cldAssertEqualsAsString(
            "ar_1.5,c_mfit,dpr_2.0,h_100",
            Resize.minimumFit { setHeight(100).setAspectRatio(1.5f).setDpr(2f) }
        )
    }

    @Test
    fun testFill() {
        cldAssertEqualsAsString("c_fill,w_100", Resize.fill { setWidth(100) })
        cldAssertEqualsAsString("c_fill,w_1.0", Resize.fill { setWidth(1f) })
        cldAssertEqualsAsString("c_fill,h_100,w_100", Resize.fill { setWidth(100).setHeight(100) })
        cldAssertEqualsAsString("c_fill,h_100,w_100", Resize.fill { setWidth(100).setHeight(100) })
        cldAssertEqualsAsString("c_fill,h_1.1,w_0.5", Resize.fill { setWidth(0.5f).setHeight(1.1f) })
        cldAssertEqualsAsString("ar_1.5,c_fill,w_100", Resize.fill { setWidth(100).setAspectRatio(1.5f) })
        cldAssertEqualsAsString(
            "ar_1.5,c_fill,dpr_2.0,h_100",
            Resize.fill { setHeight(100).setAspectRatio(1.5f).setDpr(2f) })
        cldAssertEqualsAsString(
            "ar_1.5,c_fill,dpr_2.0,g_north,h_100",
            Resize.fill {
                setHeight(100).setAspectRatio(1.5f).setDpr(2f).setGravity(Gravity.direction(Direction.NORTH))
            }
        )
    }

    @Test
    fun testLimitFill() {
        cldAssertEqualsAsString("c_lfill,w_100", Resize.limitFill { setWidth(100) })
        cldAssertEqualsAsString("c_lfill,w_1.0", Resize.limitFill { setWidth(1f) })
        cldAssertEqualsAsString("c_lfill,h_100,w_100", Resize.limitFill { setWidth(100).setHeight(100) })
        cldAssertEqualsAsString("c_lfill,h_100,w_100", Resize.limitFill { setWidth(100).setHeight(100) })
        cldAssertEqualsAsString("c_lfill,h_1.1,w_0.5", Resize.limitFill { setWidth(0.5f).setHeight(1.1f) })
        cldAssertEqualsAsString("ar_1.5,c_lfill,w_100", Resize.limitFill { setWidth(100).setAspectRatio(1.5f) })
        cldAssertEqualsAsString(
            "ar_1.5,c_lfill,dpr_2.0,h_100",
            Resize.limitFill { setHeight(100).setAspectRatio(1.5f).setDpr(2f) }
        )
        cldAssertEqualsAsString(
            "ar_1.5,c_lfill,dpr_2.0,g_north,h_100",
            Resize.limitFill {
                setHeight(100).setAspectRatio(1.5f).setDpr(2f).setGravity(Gravity.direction(Direction.NORTH))
            }
        )
    }

    @Test
    fun testPad() {
        cldAssertEqualsAsString("c_pad,w_100", Resize.pad { setWidth(100) })
        cldAssertEqualsAsString("c_pad,w_1.0", Resize.pad { setWidth(1f) })
        cldAssertEqualsAsString("c_pad,h_100,w_100", Resize.pad { setWidth(100).setHeight(100) })
        cldAssertEqualsAsString("c_pad,h_100,w_100", Resize.pad { setWidth(100).setHeight(100) })
        cldAssertEqualsAsString("c_pad,h_1.1,w_0.5", Resize.pad { setWidth(0.5f).setHeight(1.1f) })
        cldAssertEqualsAsString("ar_1.5,c_pad,w_100", Resize.pad { setWidth(100).setAspectRatio(1.5f) })
        cldAssertEqualsAsString(
            "ar_1.5,c_pad,dpr_2.0,h_100",
            Resize.pad { setHeight(100).setAspectRatio(1.5f).setDpr(2f) })
        cldAssertEqualsAsString(
            "ar_1.5,c_pad,dpr_2.0,g_north,h_100",
            Resize.pad { setHeight(100).setAspectRatio(1.5f).setDpr(2f).setGravity(Gravity.direction(Direction.NORTH)) }
        )
    }

    @Test
    fun testLimitPad() {
        cldAssertEqualsAsString("c_lpad,w_100", Resize.limitPad { setWidth(100) })
        cldAssertEqualsAsString("c_lpad,w_1.0", Resize.limitPad { setWidth(1f) })
        cldAssertEqualsAsString("c_lpad,h_100,w_100", Resize.limitPad { setWidth(100).setHeight(100) })
        cldAssertEqualsAsString("c_lpad,h_100,w_100", Resize.limitPad { setWidth(100).setHeight(100) })
        cldAssertEqualsAsString("c_lpad,h_1.1,w_0.5", Resize.limitPad { setWidth(0.5f).setHeight(1.1f) })
        cldAssertEqualsAsString("ar_1.5,c_lpad,w_100", Resize.limitPad { setWidth(100).setAspectRatio(1.5f) })
        cldAssertEqualsAsString(
            "ar_1.5,c_lpad,dpr_2.0,h_100",
            Resize.limitPad { setHeight(100).setAspectRatio(1.5f).setDpr(2f) }
        )
        cldAssertEqualsAsString(
            "ar_1.5,c_lpad,dpr_2.0,g_north,h_100",
            Resize.limitPad {
                setHeight(100).setAspectRatio(1.5f).setDpr(2f).setGravity(Gravity.direction(Direction.NORTH))
            }
        )
    }

    @Test
    fun testMinimumPad() {
        cldAssertEqualsAsString("c_mpad,w_100", Resize.minimumPad { setWidth(100) })
        cldAssertEqualsAsString("c_mpad,w_1.0", Resize.minimumPad { setWidth(1f) })
        cldAssertEqualsAsString("c_mpad,h_100,w_100", Resize.minimumPad { setWidth(100).setHeight(100) })
        cldAssertEqualsAsString("c_mpad,h_100,w_100", Resize.minimumPad { setWidth(100).setHeight(100) })
        cldAssertEqualsAsString("c_mpad,h_1.1,w_0.5", Resize.minimumPad { setWidth(0.5f).setHeight(1.1f) })
        cldAssertEqualsAsString("ar_1.5,c_mpad,w_100", Resize.minimumPad { setWidth(100).setAspectRatio(1.5f) })
        cldAssertEqualsAsString(
            "ar_1.5,c_mpad,dpr_2.0,h_100",
            Resize.minimumPad { setHeight(100).setAspectRatio(1.5f).setDpr(2f) }
        )
        cldAssertEqualsAsString(
            "ar_1.5,c_mpad,dpr_2.0,g_north,h_100",
            Resize.minimumPad {
                setHeight(100).setAspectRatio(1.5f).setDpr(2f).setGravity(Gravity.direction(Direction.NORTH))
            }
        )
    }

    @Test
    fun testFillPad() {
        cldAssertEqualsAsString("c_fill_pad,g_auto,w_100", Resize.fillPad { setWidth(100).setGravity(Gravity.auto()) })
        cldAssertEqualsAsString("c_fill_pad,g_auto,w_1.0", Resize.fillPad { setWidth(1f).setGravity(Gravity.auto()) })
        cldAssertEqualsAsString(
            "c_fill_pad,g_auto,h_100,w_100",
            Resize.fillPad { setWidth(100).setHeight(100).setGravity(Gravity.auto()) }
        )
        cldAssertEqualsAsString(
            "c_fill_pad,g_auto,h_100,w_100",
            Resize.fillPad { setWidth(100).setHeight(100).setGravity(Gravity.auto()) }
        )
        cldAssertEqualsAsString(
            "c_fill_pad,g_auto,h_1.1,w_0.5",
            Resize.fillPad { setWidth(0.5f).setHeight(1.1f).setGravity(Gravity.auto()) }
        )
        cldAssertEqualsAsString(
            "ar_1.5,c_fill_pad,g_auto,w_100",
            Resize.fillPad { setWidth(100).setAspectRatio(1.5f).setGravity(Gravity.auto()) }
        )
        cldAssertEqualsAsString(
            "ar_1.5,c_fill_pad,dpr_2.0,g_auto,h_100",
            Resize.fillPad { setHeight(100).setGravity(Gravity.auto()).setAspectRatio(1.5f).setDpr(2f) }
        )
        cldAssertEqualsAsString(
            "ar_1.5,c_fill_pad,dpr_2.0,g_auto,h_100",
            Resize.fillPad { setHeight(100).setAspectRatio(1.5f).setDpr(2f).setGravity(Gravity.auto()) })
    }

    @Test
    fun testCrop() {
        cldAssertEqualsAsString("c_crop,w_100", Resize.crop { setWidth(100) })
        cldAssertEqualsAsString("c_crop,w_1.0", Resize.crop { setWidth(1f) })
        cldAssertEqualsAsString("c_crop,h_100,w_100", Resize.crop { setWidth(100).setHeight(100) })
        cldAssertEqualsAsString("c_crop,h_100,w_100", Resize.crop { setWidth(100).setHeight(100) })
        cldAssertEqualsAsString("c_crop,h_1.1,w_0.5", Resize.crop { setWidth(0.5f).setHeight(1.1f) })
        cldAssertEqualsAsString("ar_1.5,c_crop,w_100", Resize.crop { setWidth(100).setAspectRatio(1.5f) })
        cldAssertEqualsAsString(
            "ar_1.5,c_crop,dpr_2.0,h_100",
            Resize.crop { setHeight(100).setAspectRatio(1.5f).setDpr(2f) })
        cldAssertEqualsAsString(
            "ar_1.5,c_crop,dpr_2.0,g_north,h_100",
            Resize.crop {
                setHeight(100).setAspectRatio(1.5f).setDpr(2f).setGravity(Gravity.direction(Direction.NORTH))
            }
        )
        cldAssertEqualsAsString(
            "ar_1.5,c_crop,dpr_2.0,g_north,h_100,z_1.5",
            Resize.crop {
                setHeight(100).setAspectRatio(1.5f).setDpr(2f).setGravity(Gravity.direction(Direction.NORTH))
                    .setZoom(1.5f)
            }
        )
    }

    @Test
    fun testThumb() {
        cldAssertEqualsAsString("c_thumb,w_100", Resize.thumb { setWidth(100) })
        cldAssertEqualsAsString("c_thumb,w_1.0", Resize.thumb { setWidth(1f) })
        cldAssertEqualsAsString("c_thumb,h_100,w_100", Resize.thumb { setWidth(100).setHeight(100) })
        cldAssertEqualsAsString("c_thumb,h_100,w_100", Resize.thumb { setWidth(100).setHeight(100) })
        cldAssertEqualsAsString("c_thumb,h_1.1,w_0.5", Resize.thumb { setWidth(0.5f).setHeight(1.1f) })
        cldAssertEqualsAsString("ar_1.5,c_thumb,w_100", Resize.thumb { setWidth(100).setAspectRatio(1.5f) })
        cldAssertEqualsAsString(
            "ar_1.5,c_thumb,dpr_2.0,h_100",
            Resize.thumb { setHeight(100).setAspectRatio(1.5f).setDpr(2f) }
        )
        cldAssertEqualsAsString(
            "ar_1.5,c_thumb,dpr_2.0,g_north,h_100",
            Resize.thumb {
                setHeight(100).setAspectRatio(1.5f).setDpr(2f).setGravity(Gravity.direction(Direction.NORTH))
            }
        )
        cldAssertEqualsAsString(
            "ar_1.5,c_thumb,dpr_2.0,g_north,h_100,z_1.5",
            Resize.thumb {
                setHeight(100).setAspectRatio(1.5f).setDpr(2f).setGravity(Gravity.direction(Direction.NORTH))
                    .setZoom(1.5f)
            }
        )
    }

    @Test
    fun testImaggaScale() {
        cldAssertEqualsAsString("c_imagga_scale,w_100", Resize.imaggaScale { setWidth(100) })
        cldAssertEqualsAsString("c_imagga_scale,w_1.0", Resize.imaggaScale { setWidth(1f) })
        cldAssertEqualsAsString("c_imagga_scale,h_100,w_100", Resize.imaggaScale { setWidth(100).setHeight(100) })
        cldAssertEqualsAsString("c_imagga_scale,h_100,w_100", Resize.imaggaScale { setWidth(100).setHeight(100) })
        cldAssertEqualsAsString("c_imagga_scale,h_1.1,w_0.5", Resize.imaggaScale { setWidth(0.5f).setHeight(1.1f) })
        cldAssertEqualsAsString(
            "ar_1.5,c_imagga_scale,w_100",
            Resize.imaggaScale { setWidth(100).setAspectRatio(1.5f) })
        cldAssertEqualsAsString(
            "ar_1.5,c_imagga_scale,dpr_2.0,h_100",
            Resize.imaggaScale { setHeight(100).setAspectRatio(1.5f).setDpr(2f) }
        )
    }

    @Test
    fun testImaggaCrop() {
        cldAssertEqualsAsString("c_imagga_crop,w_100", Resize.imaggaCrop { setWidth(100) })
        cldAssertEqualsAsString("c_imagga_crop,w_1.0", Resize.imaggaCrop { setWidth(1f) })
        cldAssertEqualsAsString("c_imagga_crop,h_100,w_100", Resize.imaggaCrop { setWidth(100).setHeight(100) })
        cldAssertEqualsAsString("c_imagga_crop,h_100,w_100", Resize.imaggaCrop { setWidth(100).setHeight(100) })
        cldAssertEqualsAsString("c_imagga_crop,h_1.1,w_0.5", Resize.imaggaCrop { setWidth(0.5f).setHeight(1.1f) })
        cldAssertEqualsAsString("ar_1.5,c_imagga_crop,w_100", Resize.imaggaCrop { setWidth(100).setAspectRatio(1.5f) })
        cldAssertEqualsAsString(
            "ar_1.5,c_imagga_crop,dpr_2.0,h_100",
            Resize.imaggaCrop { setHeight(100).setAspectRatio(1.5f).setDpr(2f) }
        )
    }
}