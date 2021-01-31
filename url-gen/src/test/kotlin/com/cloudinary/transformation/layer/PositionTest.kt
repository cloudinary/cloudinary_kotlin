package com.cloudinary.transformation.layer

import com.cloudinary.transformation.gravity.FocusOn
import com.cloudinary.transformation.gravity.Gravity
import com.cloudinary.transformation.layer.position.Position
import com.cloudinary.transformation.layer.source.Source
import org.junit.Test
import kotlin.test.assertTrue

class PositionTest {
    val layerBuilder = Overlay.Builder(Source.image("sample"))

    @Test
    fun testOnImagePosition() {
        var position = Position.Builder()
            .gravity(Gravity.north())
            .offsetX(10)
            .offsetY(20)
            .allowOverflow(false)
            .build()

        cldAssertPosition("fl_no_overflow,g_north,x_10,y_20", position)

        position = Position.Builder()
            .gravity(Gravity.north())
            .offsetX(10)
            .offsetY(20)
            .allowOverflow()
            .build()
        cldAssertPosition("g_north,x_10,y_20", position)

        position = Position.Builder()
            .gravity(Gravity.focusOn(FocusOn.face()))
            .offsetX(10)
            .offsetY(20)
            .tiled()
            .build()
        cldAssertPosition("fl_tiled,g_face,x_10,y_20", position)
    }

    private fun cldAssertPosition(expected: String, position: Position) {
        assertTrue { layerBuilder.position(position).build().toString().contains(expected) }
    }
}