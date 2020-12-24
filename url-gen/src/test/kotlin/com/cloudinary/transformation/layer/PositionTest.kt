package com.cloudinary.transformation.layer

import com.cloudinary.transformation.gravity.FocusOn
import com.cloudinary.transformation.gravity.Gravity
import com.cloudinary.transformation.layer.position.LayerPosition
import com.cloudinary.transformation.layer.source.LayerSource
import org.junit.Test
import kotlin.test.assertTrue

class PositionTest {
    val layerBuilder = Overlay.Builder(LayerSource.image("sample"))

    @Test
    fun testOnImagePosition() {
        var position = LayerPosition.Builder()
            .gravity(Gravity.north())
            .x(10)
            .y(20)
            .allowOverflow(false)
            .build()

        cldAssertPosition("fl_no_overflow,g_north,x_10,y_20", position)

        position = LayerPosition.Builder()
            .gravity(Gravity.north())
            .x(10)
            .y(20)
            .allowOverflow()
            .build()
        cldAssertPosition("g_north,x_10,y_20", position)

        position = LayerPosition.Builder()
            .gravity(Gravity.focusOn(FocusOn.FACE))
            .x(10)
            .y(20)
            .tiled()
            .build()
        cldAssertPosition("fl_tiled,g_face,x_10,y_20", position)
    }

    private fun cldAssertPosition(expected: String, position: LayerPosition) {
        assertTrue { layerBuilder.position(position).build().toString().contains(expected) }
    }
}