package com.cloudinary.transformation

import com.cloudinary.cldAssert
import com.cloudinary.transformation.psdtools.PSDTools
import org.junit.Test

class PSDToolsTest {
    @Test
    fun testClip() {
        cldAssert("fl_clip", PSDTools.clip())
        cldAssert("fl_clip_evenodd", PSDTools.clip {
            evenOdd()
        })

        cldAssert("fl_clip,pg_3", PSDTools.clip {
            byIndex(3)
        })
        cldAssert("fl_clip,pg_name:my_path", PSDTools.clip {
            byName("my_path")
        })
        cldAssert("fl_clip_evenodd,pg_3", PSDTools.clip {
            byIndex(3)
            evenOdd()
        })

        cldAssert("fl_clip_evenodd,pg_name:my_path", PSDTools.clip {
            byName("my_path")
            evenOdd()
        })
    }

    @Test
    fun testGetSmartObject() {
        cldAssert("pg_embedded:3", PSDTools.smartObject { this.byIndex(3) })
        cldAssert("pg_embedded:name:foo;bar", PSDTools.smartObject { this.byLayerName("foo").byLayerName("bar") })
    }

    @Test
    fun testGetPsdLayer() {
        cldAssert("pg_name:abc", PSDTools.getLayer { byName("abc") })
        cldAssert("pg_name:abc;def", PSDTools.getLayer { byName("abc").byName("def") })

        cldAssert("pg_3", PSDTools.getLayer { byIndex(3) })
        cldAssert("pg_1-3", PSDTools.getLayer { byRange(1, 3) })
        cldAssert("pg_1-3;5;7-", PSDTools.getLayer { byRange(1, 3).byIndex(5).byRange(7, null) })
    }
}