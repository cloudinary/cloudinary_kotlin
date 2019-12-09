package com.cloudinary.transformation

import com.cloudinary.cldAssertEqualsAsString
import com.cloudinary.testParam
import com.cloudinary.transformation.warp.Warp
import org.junit.Test

class WarpTest {
    @Test
    fun testShear() {
        cldAssertEqualsAsString("e_shear:20:0", Warp.shear(20, 0))
    }

    @Test
    fun testDistort() {
        cldAssertEqualsAsString("e_distort:arc:180,test_param", Warp.distort(180).add(testParam))
        cldAssertEqualsAsString("e_distort:arc:180", Warp.distort(180))
        cldAssertEqualsAsString("e_distort:5:34:70:10:70:75:5:55", Warp.distort(5, 34, 70, 10, 70, 75, 5, 55))
    }
}