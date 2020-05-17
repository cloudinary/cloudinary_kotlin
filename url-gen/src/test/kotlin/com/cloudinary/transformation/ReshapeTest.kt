package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.reshape.Reshape
import org.junit.Test

class ReshapeTest {
    @Test
    fun testShear() {
        cldAssert("e_shear:20:0", Reshape.shear {
            skewX(20)
            skewY(0)
        })
    }

    @Test
    fun testDistort() {
        cldAssert("e_distort:5:34:70:10:70:75:5:55", Reshape.distort {
            topLeft(5, 34)
            topRight(70, 10)
            bottomRight(70, 75)
            bottomLeft(5, 55)
        })
    }

    @Test
    fun testDistortArc() {
        cldAssert("e_distort:arc:180", Reshape.distortArc(180))
    }
}