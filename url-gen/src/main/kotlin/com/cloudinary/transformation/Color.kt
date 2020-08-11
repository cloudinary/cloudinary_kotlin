package com.cloudinary.transformation

import com.cloudinary.util.cldRemovePound

sealed class Color(values: List<Any?>) : ParamValue(values.filterNotNull()) {
    constructor(color: String) : this(listOf(color))

    companion object {
        fun parseString(color: String) = when {
            color.startsWith("#") || color.startsWith("rgb:") -> Rgb(color)
            else -> Named(color)
        }
    }

    internal fun withoutRgbPrefix(): Color {
        val valueContent = values.first()
        return if (valueContent is NamedValue && valueContent.name == "rgb") FromValues(
            listOf(SimpleValue(valueContent.value)) + values.subList(
                1,
                values.size
            )
        ) else this
    }

    private class FromValues(values: List<Any>) : Color(values)
    class Rgb(hex: String) : Color(listOf(NamedValue("rgb", hex.cldRemovePound())))
    class Named(name: String) : Color(name)

    /**
     * @suppress
     */
    object SNOW : Color("snow")

    /**
     * @suppress
     */
    object SNOW1 : Color("snow1")

    /**
     * @suppress
     */
    object SNOW2 : Color("snow2")

    /**
     * @suppress
     */
    object ROSYBROWN1 : Color("rosybrown1")

    /**
     * @suppress
     */
    object ROSYBROWN2 : Color("rosybrown2")

    /**
     * @suppress
     */
    object SNOW3 : Color("snow3")

    /**
     * @suppress
     */
    object LIGHTCORAL : Color("lightcoral")

    /**
     * @suppress
     */
    object INDIANRED1 : Color("indianred1")

    /**
     * @suppress
     */
    object ROSYBROWN3 : Color("rosybrown3")

    /**
     * @suppress
     */
    object INDIANRED2 : Color("indianred2")

    /**
     * @suppress
     */
    object ROSYBROWN : Color("rosybrown")

    /**
     * @suppress
     */
    object BROWN1 : Color("brown1")

    /**
     * @suppress
     */
    object FIREBRICK1 : Color("firebrick1")

    /**
     * @suppress
     */
    object BROWN2 : Color("brown2")

    /**
     * @suppress
     */
    object INDIANRED : Color("indianred")

    /**
     * @suppress
     */
    object INDIANRED3 : Color("indianred3")

    /**
     * @suppress
     */
    object FIREBRICK2 : Color("firebrick2")

    /**
     * @suppress
     */
    object SNOW4 : Color("snow4")

    /**
     * @suppress
     */
    object BROWN3 : Color("brown3")

    /**
     * @suppress
     */
    object RED : Color("red")

    /**
     * @suppress
     */
    object RED1 : Color("red1")

    /**
     * @suppress
     */
    object ROSYBROWN4 : Color("rosybrown4")

    /**
     * @suppress
     */
    object FIREBRICK3 : Color("firebrick3")

    /**
     * @suppress
     */
    object RED2 : Color("red2")

    /**
     * @suppress
     */
    object FIREBRICK : Color("firebrick")

    /**
     * @suppress
     */
    object BROWN : Color("brown")

    /**
     * @suppress
     */
    object RED3 : Color("red3")

    /**
     * @suppress
     */
    object INDIANRED4 : Color("indianred4")

    /**
     * @suppress
     */
    object BROWN4 : Color("brown4")

    /**
     * @suppress
     */
    object FIREBRICK4 : Color("firebrick4")

    /**
     * @suppress
     */
    object DARKRED : Color("darkred")

    /**
     * @suppress
     */
    object RED4 : Color("red4")

    /**
     * @suppress
     */
    object LIGHTPINK1 : Color("lightpink1")

    /**
     * @suppress
     */
    object LIGHTPINK3 : Color("lightpink3")

    /**
     * @suppress
     */
    object LIGHTPINK4 : Color("lightpink4")

    /**
     * @suppress
     */
    object LIGHTPINK2 : Color("lightpink2")

    /**
     * @suppress
     */
    object LIGHTPINK : Color("lightpink")

    /**
     * @suppress
     */
    object PINK : Color("pink")

    /**
     * @suppress
     */
    object CRIMSON : Color("crimson")

    /**
     * @suppress
     */
    object PINK1 : Color("pink1")

    /**
     * @suppress
     */
    object PINK2 : Color("pink2")

    /**
     * @suppress
     */
    object PINK3 : Color("pink3")

    /**
     * @suppress
     */
    object PINK4 : Color("pink4")

    /**
     * @suppress
     */
    object PALEVIOLETRED4 : Color("palevioletred4")

    /**
     * @suppress
     */
    object PALEVIOLETRED : Color("palevioletred")

    /**
     * @suppress
     */
    object PALEVIOLETRED2 : Color("palevioletred2")

    /**
     * @suppress
     */
    object PALEVIOLETRED1 : Color("palevioletred1")

    /**
     * @suppress
     */
    object PALEVIOLETRED3 : Color("palevioletred3")

    /**
     * @suppress
     */
    object LAVENDERBLUSH : Color("lavenderblush")

    /**
     * @suppress
     */
    object LAVENDERBLUSH1 : Color("lavenderblush1")

    /**
     * @suppress
     */
    object LAVENDERBLUSH3 : Color("lavenderblush3")

    /**
     * @suppress
     */
    object LAVENDERBLUSH2 : Color("lavenderblush2")

    /**
     * @suppress
     */
    object LAVENDERBLUSH4 : Color("lavenderblush4")

    /**
     * @suppress
     */
    object MAROON : Color("maroon")

    /**
     * @suppress
     */
    object HOTPINK3 : Color("hotpink3")

    /**
     * @suppress
     */
    object VIOLETRED3 : Color("violetred3")

    /**
     * @suppress
     */
    object VIOLETRED1 : Color("violetred1")

    /**
     * @suppress
     */
    object VIOLETRED2 : Color("violetred2")

    /**
     * @suppress
     */
    object VIOLETRED4 : Color("violetred4")

    /**
     * @suppress
     */
    object HOTPINK2 : Color("hotpink2")

    /**
     * @suppress
     */
    object HOTPINK1 : Color("hotpink1")

    /**
     * @suppress
     */
    object HOTPINK4 : Color("hotpink4")

    /**
     * @suppress
     */
    object HOTPINK : Color("hotpink")

    /**
     * @suppress
     */
    object DEEPPINK : Color("deeppink")

    /**
     * @suppress
     */
    object DEEPPINK1 : Color("deeppink1")

    /**
     * @suppress
     */
    object DEEPPINK2 : Color("deeppink2")

    /**
     * @suppress
     */
    object DEEPPINK3 : Color("deeppink3")

    /**
     * @suppress
     */
    object DEEPPINK4 : Color("deeppink4")

    /**
     * @suppress
     */
    object MAROON1 : Color("maroon1")

    /**
     * @suppress
     */
    object MAROON2 : Color("maroon2")

    /**
     * @suppress
     */
    object MAROON3 : Color("maroon3")

    /**
     * @suppress
     */
    object MAROON4 : Color("maroon4")

    /**
     * @suppress
     */
    object MEDIUMVIOLETRED : Color("mediumvioletred")

    /**
     * @suppress
     */
    object VIOLETRED : Color("violetred")

    /**
     * @suppress
     */
    object ORCHID2 : Color("orchid2")

    /**
     * @suppress
     */
    object ORCHID : Color("orchid")

    /**
     * @suppress
     */
    object ORCHID1 : Color("orchid1")

    /**
     * @suppress
     */
    object ORCHID3 : Color("orchid3")

    /**
     * @suppress
     */
    object ORCHID4 : Color("orchid4")

    /**
     * @suppress
     */
    object THISTLE1 : Color("thistle1")

    /**
     * @suppress
     */
    object THISTLE2 : Color("thistle2")

    /**
     * @suppress
     */
    object PLUM1 : Color("plum1")

    /**
     * @suppress
     */
    object PLUM2 : Color("plum2")

    /**
     * @suppress
     */
    object THISTLE : Color("thistle")

    /**
     * @suppress
     */
    object THISTLE3 : Color("thistle3")

    /**
     * @suppress
     */
    object PLUM : Color("plum")

    /**
     * @suppress
     */
    object VIOLET : Color("violet")

    /**
     * @suppress
     */
    object PLUM3 : Color("plum3")

    /**
     * @suppress
     */
    object THISTLE4 : Color("thistle4")

    /**
     * @suppress
     */
    object FUCHSIA : Color("fuchsia")

    /**
     * @suppress
     */
    object MAGENTA : Color("magenta")

    /**
     * @suppress
     */
    object MAGENTA1 : Color("magenta1")

    /**
     * @suppress
     */
    object PLUM4 : Color("plum4")

    /**
     * @suppress
     */
    object MAGENTA2 : Color("magenta2")

    /**
     * @suppress
     */
    object MAGENTA3 : Color("magenta3")

    /**
     * @suppress
     */
    object DARKMAGENTA : Color("darkmagenta")

    /**
     * @suppress
     */
    object MAGENTA4 : Color("magenta4")

    /**
     * @suppress
     */
    object PURPLE : Color("purple")

    /**
     * @suppress
     */
    object MEDIUMORCHID : Color("mediumorchid")

    /**
     * @suppress
     */
    object MEDIUMORCHID1 : Color("mediumorchid1")

    /**
     * @suppress
     */
    object MEDIUMORCHID2 : Color("mediumorchid2")

    /**
     * @suppress
     */
    object MEDIUMORCHID3 : Color("mediumorchid3")

    /**
     * @suppress
     */
    object MEDIUMORCHID4 : Color("mediumorchid4")

    /**
     * @suppress
     */
    object DARKVIOLET : Color("darkviolet")

    /**
     * @suppress
     */
    object DARKORCHID : Color("darkorchid")

    /**
     * @suppress
     */
    object DARKORCHID1 : Color("darkorchid1")

    /**
     * @suppress
     */
    object DARKORCHID3 : Color("darkorchid3")

    /**
     * @suppress
     */
    object DARKORCHID2 : Color("darkorchid2")

    /**
     * @suppress
     */
    object DARKORCHID4 : Color("darkorchid4")

    /**
     * @suppress
     */
    object INDIGO : Color("indigo")

    /**
     * @suppress
     */
    object BLUEVIOLET : Color("blueviolet")

    /**
     * @suppress
     */
    object PURPLE2 : Color("purple2")

    /**
     * @suppress
     */
    object PURPLE3 : Color("purple3")

    /**
     * @suppress
     */
    object PURPLE4 : Color("purple4")

    /**
     * @suppress
     */
    object PURPLE1 : Color("purple1")

    /**
     * @suppress
     */
    object MEDIUMPURPLE : Color("mediumpurple")

    /**
     * @suppress
     */
    object MEDIUMPURPLE1 : Color("mediumpurple1")

    /**
     * @suppress
     */
    object MEDIUMPURPLE2 : Color("mediumpurple2")

    /**
     * @suppress
     */
    object MEDIUMPURPLE3 : Color("mediumpurple3")

    /**
     * @suppress
     */
    object MEDIUMPURPLE4 : Color("mediumpurple4")

    /**
     * @suppress
     */
    object DARKSLATEBLUE : Color("darkslateblue")

    /**
     * @suppress
     */
    object LIGHTSLATEBLUE : Color("lightslateblue")

    /**
     * @suppress
     */
    object MEDIUMSLATEBLUE : Color("mediumslateblue")

    /**
     * @suppress
     */
    object SLATEBLUE : Color("slateblue")

    /**
     * @suppress
     */
    object SLATEBLUE1 : Color("slateblue1")

    /**
     * @suppress
     */
    object SLATEBLUE2 : Color("slateblue2")

    /**
     * @suppress
     */
    object SLATEBLUE3 : Color("slateblue3")

    /**
     * @suppress
     */
    object SLATEBLUE4 : Color("slateblue4")

    /**
     * @suppress
     */
    object GHOSTWHITE : Color("ghostwhite")

    /**
     * @suppress
     */
    object LAVENDER : Color("lavender")

    /**
     * @suppress
     */
    object BLUE : Color("blue")

    /**
     * @suppress
     */
    object BLUE1 : Color("blue1")

    /**
     * @suppress
     */
    object BLUE2 : Color("blue2")

    /**
     * @suppress
     */
    object BLUE3 : Color("blue3")

    /**
     * @suppress
     */
    object MEDIUMBLUE : Color("mediumblue")

    /**
     * @suppress
     */
    object BLUE4 : Color("blue4")

    /**
     * @suppress
     */
    object DARKBLUE : Color("darkblue")

    /**
     * @suppress
     */
    object MIDNIGHTBLUE : Color("midnightblue")

    /**
     * @suppress
     */
    object NAVY : Color("navy")

    /**
     * @suppress
     */
    object NAVYBLUE : Color("navyblue")

    /**
     * @suppress
     */
    object ROYALBLUE : Color("royalblue")

    /**
     * @suppress
     */
    object ROYALBLUE1 : Color("royalblue1")

    /**
     * @suppress
     */
    object ROYALBLUE2 : Color("royalblue2")

    /**
     * @suppress
     */
    object ROYALBLUE3 : Color("royalblue3")

    /**
     * @suppress
     */
    object ROYALBLUE4 : Color("royalblue4")

    /**
     * @suppress
     */
    object CORNFLOWERBLUE : Color("cornflowerblue")

    /**
     * @suppress
     */
    object LIGHTSTEELBLUE : Color("lightsteelblue")

    /**
     * @suppress
     */
    object LIGHTSTEELBLUE1 : Color("lightsteelblue1")

    /**
     * @suppress
     */
    object LIGHTSTEELBLUE2 : Color("lightsteelblue2")

    /**
     * @suppress
     */
    object LIGHTSTEELBLUE3 : Color("lightsteelblue3")

    /**
     * @suppress
     */
    object LIGHTSTEELBLUE4 : Color("lightsteelblue4")

    /**
     * @suppress
     */
    object SLATEGRAY4 : Color("slategray4")

    /**
     * @suppress
     */
    object SLATEGRAY1 : Color("slategray1")

    /**
     * @suppress
     */
    object SLATEGRAY2 : Color("slategray2")

    /**
     * @suppress
     */
    object SLATEGRAY3 : Color("slategray3")

    /**
     * @suppress
     */
    object LIGHTSLATEGRAY : Color("lightslategray")

    /**
     * @suppress
     */
    object LIGHTSLATEGREY : Color("lightslategrey")

    /**
     * @suppress
     */
    object SLATEGRAY : Color("slategray")

    /**
     * @suppress
     */
    object SLATEGREY : Color("slategrey")

    /**
     * @suppress
     */
    object DODGERBLUE : Color("dodgerblue")

    /**
     * @suppress
     */
    object DODGERBLUE1 : Color("dodgerblue1")

    /**
     * @suppress
     */
    object DODGERBLUE2 : Color("dodgerblue2")

    /**
     * @suppress
     */
    object DODGERBLUE4 : Color("dodgerblue4")

    /**
     * @suppress
     */
    object DODGERBLUE3 : Color("dodgerblue3")

    /**
     * @suppress
     */
    object ALICEBLUE : Color("aliceblue")

    /**
     * @suppress
     */
    object STEELBLUE4 : Color("steelblue4")

    /**
     * @suppress
     */
    object STEELBLUE : Color("steelblue")

    /**
     * @suppress
     */
    object STEELBLUE1 : Color("steelblue1")

    /**
     * @suppress
     */
    object STEELBLUE2 : Color("steelblue2")

    /**
     * @suppress
     */
    object STEELBLUE3 : Color("steelblue3")

    /**
     * @suppress
     */
    object SKYBLUE4 : Color("skyblue4")

    /**
     * @suppress
     */
    object SKYBLUE1 : Color("skyblue1")

    /**
     * @suppress
     */
    object SKYBLUE2 : Color("skyblue2")

    /**
     * @suppress
     */
    object SKYBLUE3 : Color("skyblue3")

    /**
     * @suppress
     */
    object LIGHTSKYBLUE : Color("lightskyblue")

    /**
     * @suppress
     */
    object LIGHTSKYBLUE4 : Color("lightskyblue4")

    /**
     * @suppress
     */
    object LIGHTSKYBLUE1 : Color("lightskyblue1")

    /**
     * @suppress
     */
    object LIGHTSKYBLUE2 : Color("lightskyblue2")

    /**
     * @suppress
     */
    object LIGHTSKYBLUE3 : Color("lightskyblue3")

    /**
     * @suppress
     */
    object SKYBLUE : Color("skyblue")

    /**
     * @suppress
     */
    object LIGHTBLUE3 : Color("lightblue3")

    /**
     * @suppress
     */
    object DEEPSKYBLUE : Color("deepskyblue")

    /**
     * @suppress
     */
    object DEEPSKYBLUE1 : Color("deepskyblue1")

    /**
     * @suppress
     */
    object DEEPSKYBLUE2 : Color("deepskyblue2")

    /**
     * @suppress
     */
    object DEEPSKYBLUE4 : Color("deepskyblue4")

    /**
     * @suppress
     */
    object DEEPSKYBLUE3 : Color("deepskyblue3")

    /**
     * @suppress
     */
    object LIGHTBLUE1 : Color("lightblue1")

    /**
     * @suppress
     */
    object LIGHTBLUE2 : Color("lightblue2")

    /**
     * @suppress
     */
    object LIGHTBLUE : Color("lightblue")

    /**
     * @suppress
     */
    object LIGHTBLUE4 : Color("lightblue4")

    /**
     * @suppress
     */
    object POWDERBLUE : Color("powderblue")

    /**
     * @suppress
     */
    object CADETBLUE1 : Color("cadetblue1")

    /**
     * @suppress
     */
    object CADETBLUE2 : Color("cadetblue2")

    /**
     * @suppress
     */
    object CADETBLUE3 : Color("cadetblue3")

    /**
     * @suppress
     */
    object CADETBLUE4 : Color("cadetblue4")

    /**
     * @suppress
     */
    object TURQUOISE1 : Color("turquoise1")

    /**
     * @suppress
     */
    object TURQUOISE2 : Color("turquoise2")

    /**
     * @suppress
     */
    object TURQUOISE3 : Color("turquoise3")

    /**
     * @suppress
     */
    object TURQUOISE4 : Color("turquoise4")

    /**
     * @suppress
     */
    object CADETBLUE : Color("cadetblue")

    /**
     * @suppress
     */
    object DARKTURQUOISE : Color("darkturquoise")

    /**
     * @suppress
     */
    object AZURE : Color("azure")

    /**
     * @suppress
     */
    object AZURE1 : Color("azure1")

    /**
     * @suppress
     */
    object LIGHTCYAN1 : Color("lightcyan1")

    /**
     * @suppress
     */
    object LIGHTCYAN : Color("lightcyan")

    /**
     * @suppress
     */
    object AZURE2 : Color("azure2")

    /**
     * @suppress
     */
    object LIGHTCYAN2 : Color("lightcyan2")

    /**
     * @suppress
     */
    object PALETURQUOISE1 : Color("paleturquoise1")

    /**
     * @suppress
     */
    object PALETURQUOISE : Color("paleturquoise")

    /**
     * @suppress
     */
    object PALETURQUOISE2 : Color("paleturquoise2")

    /**
     * @suppress
     */
    object DARKSLATEGRAY1 : Color("darkslategray1")

    /**
     * @suppress
     */
    object AZURE3 : Color("azure3")

    /**
     * @suppress
     */
    object LIGHTCYAN3 : Color("lightcyan3")

    /**
     * @suppress
     */
    object DARKSLATEGRAY2 : Color("darkslategray2")

    /**
     * @suppress
     */
    object PALETURQUOISE3 : Color("paleturquoise3")

    /**
     * @suppress
     */
    object DARKSLATEGRAY3 : Color("darkslategray3")

    /**
     * @suppress
     */
    object AZURE4 : Color("azure4")

    /**
     * @suppress
     */
    object LIGHTCYAN4 : Color("lightcyan4")

    /**
     * @suppress
     */
    object AQUA : Color("aqua")

    /**
     * @suppress
     */
    object CYAN : Color("cyan")

    /**
     * @suppress
     */
    object CYAN1 : Color("cyan1")

    /**
     * @suppress
     */
    object PALETURQUOISE4 : Color("paleturquoise4")

    /**
     * @suppress
     */
    object CYAN2 : Color("cyan2")

    /**
     * @suppress
     */
    object DARKSLATEGRAY4 : Color("darkslategray4")

    /**
     * @suppress
     */
    object CYAN3 : Color("cyan3")

    /**
     * @suppress
     */
    object CYAN4 : Color("cyan4")

    /**
     * @suppress
     */
    object DARKCYAN : Color("darkcyan")

    /**
     * @suppress
     */
    object TEAL : Color("teal")

    /**
     * @suppress
     */
    object DARKSLATEGRAY : Color("darkslategray")

    /**
     * @suppress
     */
    object DARKSLATEGREY : Color("darkslategrey")

    /**
     * @suppress
     */
    object MEDIUMTURQUOISE : Color("mediumturquoise")

    /**
     * @suppress
     */
    object LIGHTSEAGREEN : Color("lightseagreen")

    /**
     * @suppress
     */
    object TURQUOISE : Color("turquoise")

    /**
     * @suppress
     */
    object AQUAMARINE4 : Color("aquamarine4")

    /**
     * @suppress
     */
    object AQUAMARINE : Color("aquamarine")

    /**
     * @suppress
     */
    object AQUAMARINE1 : Color("aquamarine1")

    /**
     * @suppress
     */
    object AQUAMARINE2 : Color("aquamarine2")

    /**
     * @suppress
     */
    object AQUAMARINE3 : Color("aquamarine3")

    /**
     * @suppress
     */
    object MEDIUMAQUAMARINE : Color("mediumaquamarine")

    /**
     * @suppress
     */
    object MEDIUMSPRINGGREEN : Color("mediumspringgreen")

    /**
     * @suppress
     */
    object MINTCREAM : Color("mintcream")

    /**
     * @suppress
     */
    object SPRINGGREEN : Color("springgreen")

    /**
     * @suppress
     */
    object SPRINGGREEN1 : Color("springgreen1")

    /**
     * @suppress
     */
    object SPRINGGREEN2 : Color("springgreen2")

    /**
     * @suppress
     */
    object SPRINGGREEN3 : Color("springgreen3")

    /**
     * @suppress
     */
    object SPRINGGREEN4 : Color("springgreen4")

    /**
     * @suppress
     */
    object MEDIUMSEAGREEN : Color("mediumseagreen")

    /**
     * @suppress
     */
    object SEAGREEN : Color("seagreen")

    /**
     * @suppress
     */
    object SEAGREEN3 : Color("seagreen3")

    /**
     * @suppress
     */
    object SEAGREEN1 : Color("seagreen1")

    /**
     * @suppress
     */
    object SEAGREEN4 : Color("seagreen4")

    /**
     * @suppress
     */
    object SEAGREEN2 : Color("seagreen2")

    /**
     * @suppress
     */
    object MEDIUMFORESTGREEN : Color("mediumforestgreen")

    /**
     * @suppress
     */
    object HONEYDEW : Color("honeydew")

    /**
     * @suppress
     */
    object HONEYDEW1 : Color("honeydew1")

    /**
     * @suppress
     */
    object HONEYDEW2 : Color("honeydew2")

    /**
     * @suppress
     */
    object DARKSEAGREEN1 : Color("darkseagreen1")

    /**
     * @suppress
     */
    object DARKSEAGREEN2 : Color("darkseagreen2")

    /**
     * @suppress
     */
    object PALEGREEN1 : Color("palegreen1")

    /**
     * @suppress
     */
    object PALEGREEN : Color("palegreen")

    /**
     * @suppress
     */
    object HONEYDEW3 : Color("honeydew3")

    /**
     * @suppress
     */
    object LIGHTGREEN : Color("lightgreen")

    /**
     * @suppress
     */
    object PALEGREEN2 : Color("palegreen2")

    /**
     * @suppress
     */
    object DARKSEAGREEN3 : Color("darkseagreen3")

    /**
     * @suppress
     */
    object DARKSEAGREEN : Color("darkseagreen")

    /**
     * @suppress
     */
    object PALEGREEN3 : Color("palegreen3")

    /**
     * @suppress
     */
    object HONEYDEW4 : Color("honeydew4")

    /**
     * @suppress
     */
    object GREEN1 : Color("green1")

    /**
     * @suppress
     */
    object LIME : Color("lime")

    /**
     * @suppress
     */
    object LIMEGREEN : Color("limegreen")

    /**
     * @suppress
     */
    object DARKSEAGREEN4 : Color("darkseagreen4")

    /**
     * @suppress
     */
    object GREEN2 : Color("green2")

    /**
     * @suppress
     */
    object PALEGREEN4 : Color("palegreen4")

    /**
     * @suppress
     */
    object GREEN3 : Color("green3")

    /**
     * @suppress
     */
    object FORESTGREEN : Color("forestgreen")

    /**
     * @suppress
     */
    object GREEN4 : Color("green4")

    /**
     * @suppress
     */
    object GREEN : Color("green")

    /**
     * @suppress
     */
    object DARKGREEN : Color("darkgreen")

    /**
     * @suppress
     */
    object LAWNGREEN : Color("lawngreen")

    /**
     * @suppress
     */
    object CHARTREUSE : Color("chartreuse")

    /**
     * @suppress
     */
    object CHARTREUSE1 : Color("chartreuse1")

    /**
     * @suppress
     */
    object CHARTREUSE2 : Color("chartreuse2")

    /**
     * @suppress
     */
    object CHARTREUSE3 : Color("chartreuse3")

    /**
     * @suppress
     */
    object CHARTREUSE4 : Color("chartreuse4")

    /**
     * @suppress
     */
    object GREENYELLOW : Color("greenyellow")

    /**
     * @suppress
     */
    object DARKOLIVEGREEN3 : Color("darkolivegreen3")

    /**
     * @suppress
     */
    object DARKOLIVEGREEN1 : Color("darkolivegreen1")

    /**
     * @suppress
     */
    object DARKOLIVEGREEN2 : Color("darkolivegreen2")

    /**
     * @suppress
     */
    object DARKOLIVEGREEN4 : Color("darkolivegreen4")

    /**
     * @suppress
     */
    object DARKOLIVEGREEN : Color("darkolivegreen")

    /**
     * @suppress
     */
    object OLIVEDRAB : Color("olivedrab")

    /**
     * @suppress
     */
    object OLIVEDRAB1 : Color("olivedrab1")

    /**
     * @suppress
     */
    object OLIVEDRAB2 : Color("olivedrab2")

    /**
     * @suppress
     */
    object OLIVEDRAB3 : Color("olivedrab3")

    /**
     * @suppress
     */
    object YELLOWGREEN : Color("yellowgreen")

    /**
     * @suppress
     */
    object OLIVEDRAB4 : Color("olivedrab4")

    /**
     * @suppress
     */
    object IVORY : Color("ivory")

    /**
     * @suppress
     */
    object IVORY1 : Color("ivory1")

    /**
     * @suppress
     */
    object LIGHTYELLOW : Color("lightyellow")

    /**
     * @suppress
     */
    object LIGHTYELLOW1 : Color("lightyellow1")

    /**
     * @suppress
     */
    object BEIGE : Color("beige")

    /**
     * @suppress
     */
    object IVORY2 : Color("ivory2")

    /**
     * @suppress
     */
    object LIGHTGOLDENRODYELLOW : Color("lightgoldenrodyellow")

    /**
     * @suppress
     */
    object LIGHTYELLOW2 : Color("lightyellow2")

    /**
     * @suppress
     */
    object IVORY3 : Color("ivory3")

    /**
     * @suppress
     */
    object LIGHTYELLOW3 : Color("lightyellow3")

    /**
     * @suppress
     */
    object IVORY4 : Color("ivory4")

    /**
     * @suppress
     */
    object LIGHTYELLOW4 : Color("lightyellow4")

    /**
     * @suppress
     */
    object YELLOW : Color("yellow")

    /**
     * @suppress
     */
    object YELLOW1 : Color("yellow1")

    /**
     * @suppress
     */
    object YELLOW2 : Color("yellow2")

    /**
     * @suppress
     */
    object YELLOW3 : Color("yellow3")

    /**
     * @suppress
     */
    object YELLOW4 : Color("yellow4")

    /**
     * @suppress
     */
    object OLIVE : Color("olive")

    /**
     * @suppress
     */
    object DARKKHAKI : Color("darkkhaki")

    /**
     * @suppress
     */
    object KHAKI2 : Color("khaki2")

    /**
     * @suppress
     */
    object LEMONCHIFFON4 : Color("lemonchiffon4")

    /**
     * @suppress
     */
    object KHAKI1 : Color("khaki1")

    /**
     * @suppress
     */
    object KHAKI3 : Color("khaki3")

    /**
     * @suppress
     */
    object KHAKI4 : Color("khaki4")

    /**
     * @suppress
     */
    object PALEGOLDENROD : Color("palegoldenrod")

    /**
     * @suppress
     */
    object LEMONCHIFFON : Color("lemonchiffon")

    /**
     * @suppress
     */
    object LEMONCHIFFON1 : Color("lemonchiffon1")

    /**
     * @suppress
     */
    object KHAKI : Color("khaki")

    /**
     * @suppress
     */
    object LEMONCHIFFON3 : Color("lemonchiffon3")

    /**
     * @suppress
     */
    object LEMONCHIFFON2 : Color("lemonchiffon2")

    /**
     * @suppress
     */
    object MEDIUMGOLDENROD : Color("mediumgoldenrod")

    /**
     * @suppress
     */
    object CORNSILK4 : Color("cornsilk4")

    /**
     * @suppress
     */
    object GOLD : Color("gold")

    /**
     * @suppress
     */
    object GOLD1 : Color("gold1")

    /**
     * @suppress
     */
    object GOLD2 : Color("gold2")

    /**
     * @suppress
     */
    object GOLD3 : Color("gold3")

    /**
     * @suppress
     */
    object GOLD4 : Color("gold4")

    /**
     * @suppress
     */
    object LIGHTGOLDENROD : Color("lightgoldenrod")

    /**
     * @suppress
     */
    object LIGHTGOLDENROD4 : Color("lightgoldenrod4")

    /**
     * @suppress
     */
    object LIGHTGOLDENROD1 : Color("lightgoldenrod1")

    /**
     * @suppress
     */
    object LIGHTGOLDENROD3 : Color("lightgoldenrod3")

    /**
     * @suppress
     */
    object LIGHTGOLDENROD2 : Color("lightgoldenrod2")

    /**
     * @suppress
     */
    object CORNSILK3 : Color("cornsilk3")

    /**
     * @suppress
     */
    object CORNSILK2 : Color("cornsilk2")

    /**
     * @suppress
     */
    object CORNSILK : Color("cornsilk")

    /**
     * @suppress
     */
    object CORNSILK1 : Color("cornsilk1")

    /**
     * @suppress
     */
    object GOLDENROD : Color("goldenrod")

    /**
     * @suppress
     */
    object GOLDENROD1 : Color("goldenrod1")

    /**
     * @suppress
     */
    object GOLDENROD2 : Color("goldenrod2")

    /**
     * @suppress
     */
    object GOLDENROD3 : Color("goldenrod3")

    /**
     * @suppress
     */
    object GOLDENROD4 : Color("goldenrod4")

    /**
     * @suppress
     */
    object DARKGOLDENROD : Color("darkgoldenrod")

    /**
     * @suppress
     */
    object DARKGOLDENROD1 : Color("darkgoldenrod1")

    /**
     * @suppress
     */
    object DARKGOLDENROD2 : Color("darkgoldenrod2")

    /**
     * @suppress
     */
    object DARKGOLDENROD3 : Color("darkgoldenrod3")

    /**
     * @suppress
     */
    object DARKGOLDENROD4 : Color("darkgoldenrod4")

    /**
     * @suppress
     */
    object FLORALWHITE : Color("floralwhite")

    /**
     * @suppress
     */
    object WHEAT2 : Color("wheat2")

    /**
     * @suppress
     */
    object OLDLACE : Color("oldlace")

    /**
     * @suppress
     */
    object WHEAT : Color("wheat")

    /**
     * @suppress
     */
    object WHEAT1 : Color("wheat1")

    /**
     * @suppress
     */
    object WHEAT3 : Color("wheat3")

    /**
     * @suppress
     */
    object ORANGE : Color("orange")

    /**
     * @suppress
     */
    object ORANGE1 : Color("orange1")

    /**
     * @suppress
     */
    object ORANGE2 : Color("orange2")

    /**
     * @suppress
     */
    object ORANGE3 : Color("orange3")

    /**
     * @suppress
     */
    object ORANGE4 : Color("orange4")

    /**
     * @suppress
     */
    object WHEAT4 : Color("wheat4")

    /**
     * @suppress
     */
    object MOCCASIN : Color("moccasin")

    /**
     * @suppress
     */
    object PAPAYAWHIP : Color("papayawhip")

    /**
     * @suppress
     */
    object NAVAJOWHITE3 : Color("navajowhite3")

    /**
     * @suppress
     */
    object BLANCHEDALMOND : Color("blanchedalmond")

    /**
     * @suppress
     */
    object NAVAJOWHITE : Color("navajowhite")

    /**
     * @suppress
     */
    object NAVAJOWHITE1 : Color("navajowhite1")

    /**
     * @suppress
     */
    object NAVAJOWHITE2 : Color("navajowhite2")

    /**
     * @suppress
     */
    object NAVAJOWHITE4 : Color("navajowhite4")

    /**
     * @suppress
     */
    object ANTIQUEWHITE4 : Color("antiquewhite4")

    /**
     * @suppress
     */
    object ANTIQUEWHITE : Color("antiquewhite")

    /**
     * @suppress
     */
    object TAN : Color("tan")

    /**
     * @suppress
     */
    object BISQUE4 : Color("bisque4")

    /**
     * @suppress
     */
    object BURLYWOOD : Color("burlywood")

    /**
     * @suppress
     */
    object ANTIQUEWHITE2 : Color("antiquewhite2")

    /**
     * @suppress
     */
    object BURLYWOOD1 : Color("burlywood1")

    /**
     * @suppress
     */
    object BURLYWOOD3 : Color("burlywood3")

    /**
     * @suppress
     */
    object BURLYWOOD2 : Color("burlywood2")

    /**
     * @suppress
     */
    object ANTIQUEWHITE1 : Color("antiquewhite1")

    /**
     * @suppress
     */
    object BURLYWOOD4 : Color("burlywood4")

    /**
     * @suppress
     */
    object ANTIQUEWHITE3 : Color("antiquewhite3")

    /**
     * @suppress
     */
    object DARKORANGE : Color("darkorange")

    /**
     * @suppress
     */
    object BISQUE2 : Color("bisque2")

    /**
     * @suppress
     */
    object BISQUE : Color("bisque")

    /**
     * @suppress
     */
    object BISQUE1 : Color("bisque1")

    /**
     * @suppress
     */
    object BISQUE3 : Color("bisque3")

    /**
     * @suppress
     */
    object DARKORANGE1 : Color("darkorange1")

    /**
     * @suppress
     */
    object LINEN : Color("linen")

    /**
     * @suppress
     */
    object DARKORANGE2 : Color("darkorange2")

    /**
     * @suppress
     */
    object DARKORANGE3 : Color("darkorange3")

    /**
     * @suppress
     */
    object DARKORANGE4 : Color("darkorange4")

    /**
     * @suppress
     */
    object PERU : Color("peru")

    /**
     * @suppress
     */
    object TAN1 : Color("tan1")

    /**
     * @suppress
     */
    object TAN2 : Color("tan2")

    /**
     * @suppress
     */
    object TAN3 : Color("tan3")

    /**
     * @suppress
     */
    object TAN4 : Color("tan4")

    /**
     * @suppress
     */
    object PEACHPUFF : Color("peachpuff")

    /**
     * @suppress
     */
    object PEACHPUFF1 : Color("peachpuff1")

    /**
     * @suppress
     */
    object PEACHPUFF4 : Color("peachpuff4")

    /**
     * @suppress
     */
    object PEACHPUFF2 : Color("peachpuff2")

    /**
     * @suppress
     */
    object PEACHPUFF3 : Color("peachpuff3")

    /**
     * @suppress
     */
    object SANDYBROWN : Color("sandybrown")

    /**
     * @suppress
     */
    object SEASHELL4 : Color("seashell4")

    /**
     * @suppress
     */
    object SEASHELL2 : Color("seashell2")

    /**
     * @suppress
     */
    object SEASHELL3 : Color("seashell3")

    /**
     * @suppress
     */
    object CHOCOLATE : Color("chocolate")

    /**
     * @suppress
     */
    object CHOCOLATE1 : Color("chocolate1")

    /**
     * @suppress
     */
    object CHOCOLATE2 : Color("chocolate2")

    /**
     * @suppress
     */
    object CHOCOLATE3 : Color("chocolate3")

    /**
     * @suppress
     */
    object CHOCOLATE4 : Color("chocolate4")

    /**
     * @suppress
     */
    object SADDLEBROWN : Color("saddlebrown")

    /**
     * @suppress
     */
    object SEASHELL : Color("seashell")

    /**
     * @suppress
     */
    object SEASHELL1 : Color("seashell1")

    /**
     * @suppress
     */
    object SIENNA4 : Color("sienna4")

    /**
     * @suppress
     */
    object SIENNA : Color("sienna")

    /**
     * @suppress
     */
    object SIENNA1 : Color("sienna1")

    /**
     * @suppress
     */
    object SIENNA2 : Color("sienna2")

    /**
     * @suppress
     */
    object SIENNA3 : Color("sienna3")

    /**
     * @suppress
     */
    object LIGHTSALMON3 : Color("lightsalmon3")

    /**
     * @suppress
     */
    object LIGHTSALMON : Color("lightsalmon")

    /**
     * @suppress
     */
    object LIGHTSALMON1 : Color("lightsalmon1")

    /**
     * @suppress
     */
    object LIGHTSALMON4 : Color("lightsalmon4")

    /**
     * @suppress
     */
    object LIGHTSALMON2 : Color("lightsalmon2")

    /**
     * @suppress
     */
    object CORAL : Color("coral")

    /**
     * @suppress
     */
    object ORANGERED : Color("orangered")

    /**
     * @suppress
     */
    object ORANGERED1 : Color("orangered1")

    /**
     * @suppress
     */
    object ORANGERED2 : Color("orangered2")

    /**
     * @suppress
     */
    object ORANGERED3 : Color("orangered3")

    /**
     * @suppress
     */
    object ORANGERED4 : Color("orangered4")

    /**
     * @suppress
     */
    object DARKSALMON : Color("darksalmon")

    /**
     * @suppress
     */
    object SALMON1 : Color("salmon1")

    /**
     * @suppress
     */
    object SALMON2 : Color("salmon2")

    /**
     * @suppress
     */
    object SALMON3 : Color("salmon3")

    /**
     * @suppress
     */
    object SALMON4 : Color("salmon4")

    /**
     * @suppress
     */
    object CORAL1 : Color("coral1")

    /**
     * @suppress
     */
    object CORAL2 : Color("coral2")

    /**
     * @suppress
     */
    object CORAL3 : Color("coral3")

    /**
     * @suppress
     */
    object CORAL4 : Color("coral4")

    /**
     * @suppress
     */
    object TOMATO4 : Color("tomato4")

    /**
     * @suppress
     */
    object TOMATO : Color("tomato")

    /**
     * @suppress
     */
    object TOMATO1 : Color("tomato1")

    /**
     * @suppress
     */
    object TOMATO2 : Color("tomato2")

    /**
     * @suppress
     */
    object TOMATO3 : Color("tomato3")

    /**
     * @suppress
     */
    object MISTYROSE4 : Color("mistyrose4")

    /**
     * @suppress
     */
    object MISTYROSE2 : Color("mistyrose2")

    /**
     * @suppress
     */
    object MISTYROSE : Color("mistyrose")

    /**
     * @suppress
     */
    object MISTYROSE1 : Color("mistyrose1")

    /**
     * @suppress
     */
    object SALMON : Color("salmon")

    /**
     * @suppress
     */
    object MISTYROSE3 : Color("mistyrose3")

    /**
     * @suppress
     */
    object WHITE : Color("white")

    /**
     * @suppress
     */
    object GRAY100 : Color("gray100")

    /**
     * @suppress
     */
    object GREY100 : Color("grey100")

    /**
     * @suppress
     */
    object GRAY99 : Color("gray99")

    /**
     * @suppress
     */
    object GREY99 : Color("grey99")

    /**
     * @suppress
     */
    object GRAY98 : Color("gray98")

    /**
     * @suppress
     */
    object GREY98 : Color("grey98")

    /**
     * @suppress
     */
    object GRAY97 : Color("gray97")

    /**
     * @suppress
     */
    object GREY97 : Color("grey97")

    /**
     * @suppress
     */
    object GRAY96 : Color("gray96")

    /**
     * @suppress
     */
    object GREY96 : Color("grey96")

    /**
     * @suppress
     */
    object WHITESMOKE : Color("whitesmoke")

    /**
     * @suppress
     */
    object GRAY95 : Color("gray95")

    /**
     * @suppress
     */
    object GREY95 : Color("grey95")

    /**
     * @suppress
     */
    object GRAY94 : Color("gray94")

    /**
     * @suppress
     */
    object GREY94 : Color("grey94")

    /**
     * @suppress
     */
    object GRAY93 : Color("gray93")

    /**
     * @suppress
     */
    object GREY93 : Color("grey93")

    /**
     * @suppress
     */
    object GRAY92 : Color("gray92")

    /**
     * @suppress
     */
    object GREY92 : Color("grey92")

    /**
     * @suppress
     */
    object GRAY91 : Color("gray91")

    /**
     * @suppress
     */
    object GREY91 : Color("grey91")

    /**
     * @suppress
     */
    object GRAY90 : Color("gray90")

    /**
     * @suppress
     */
    object GREY90 : Color("grey90")

    /**
     * @suppress
     */
    object GRAY89 : Color("gray89")

    /**
     * @suppress
     */
    object GREY89 : Color("grey89")

    /**
     * @suppress
     */
    object GRAY88 : Color("gray88")

    /**
     * @suppress
     */
    object GREY88 : Color("grey88")

    /**
     * @suppress
     */
    object GRAY87 : Color("gray87")

    /**
     * @suppress
     */
    object GREY87 : Color("grey87")

    /**
     * @suppress
     */
    object GAINSBORO : Color("gainsboro")

    /**
     * @suppress
     */
    object GRAY86 : Color("gray86")

    /**
     * @suppress
     */
    object GREY86 : Color("grey86")

    /**
     * @suppress
     */
    object GRAY85 : Color("gray85")

    /**
     * @suppress
     */
    object GREY85 : Color("grey85")

    /**
     * @suppress
     */
    object GRAY84 : Color("gray84")

    /**
     * @suppress
     */
    object GREY84 : Color("grey84")

    /**
     * @suppress
     */
    object GRAY83 : Color("gray83")

    /**
     * @suppress
     */
    object GREY83 : Color("grey83")

    /**
     * @suppress
     */
    object LIGHTGRAY : Color("lightgray")

    /**
     * @suppress
     */
    object LIGHTGREY : Color("lightgrey")

    /**
     * @suppress
     */
    object GRAY82 : Color("gray82")

    /**
     * @suppress
     */
    object GREY82 : Color("grey82")

    /**
     * @suppress
     */
    object GRAY81 : Color("gray81")

    /**
     * @suppress
     */
    object GREY81 : Color("grey81")

    /**
     * @suppress
     */
    object GRAY80 : Color("gray80")

    /**
     * @suppress
     */
    object GREY80 : Color("grey80")

    /**
     * @suppress
     */
    object GRAY79 : Color("gray79")

    /**
     * @suppress
     */
    object GREY79 : Color("grey79")

    /**
     * @suppress
     */
    object GRAY78 : Color("gray78")

    /**
     * @suppress
     */
    object GREY78 : Color("grey78")

    /**
     * @suppress
     */
    object GRAY77 : Color("gray77")

    /**
     * @suppress
     */
    object GREY77 : Color("grey77")

    /**
     * @suppress
     */
    object GRAY76 : Color("gray76")

    /**
     * @suppress
     */
    object GREY76 : Color("grey76")

    /**
     * @suppress
     */
    object SILVER : Color("silver")

    /**
     * @suppress
     */
    object GRAY75 : Color("gray75")

    /**
     * @suppress
     */
    object GREY75 : Color("grey75")

    /**
     * @suppress
     */
    object GRAY74 : Color("gray74")

    /**
     * @suppress
     */
    object GREY74 : Color("grey74")

    /**
     * @suppress
     */
    object GRAY73 : Color("gray73")

    /**
     * @suppress
     */
    object GREY73 : Color("grey73")

    /**
     * @suppress
     */
    object GRAY72 : Color("gray72")

    /**
     * @suppress
     */
    object GREY72 : Color("grey72")

    /**
     * @suppress
     */
    object GRAY71 : Color("gray71")

    /**
     * @suppress
     */
    object GREY71 : Color("grey71")

    /**
     * @suppress
     */
    object GRAY70 : Color("gray70")

    /**
     * @suppress
     */
    object GREY70 : Color("grey70")

    /**
     * @suppress
     */
    object GRAY69 : Color("gray69")

    /**
     * @suppress
     */
    object GREY69 : Color("grey69")

    /**
     * @suppress
     */
    object GRAY68 : Color("gray68")

    /**
     * @suppress
     */
    object GREY68 : Color("grey68")

    /**
     * @suppress
     */
    object GRAY67 : Color("gray67")

    /**
     * @suppress
     */
    object GREY67 : Color("grey67")

    /**
     * @suppress
     */
    object DARKGRAY : Color("darkgray")

    /**
     * @suppress
     */
    object DARKGREY : Color("darkgrey")

    /**
     * @suppress
     */
    object GRAY66 : Color("gray66")

    /**
     * @suppress
     */
    object GREY66 : Color("grey66")

    /**
     * @suppress
     */
    object GRAY65 : Color("gray65")

    /**
     * @suppress
     */
    object GREY65 : Color("grey65")

    /**
     * @suppress
     */
    object GRAY64 : Color("gray64")

    /**
     * @suppress
     */
    object GREY64 : Color("grey64")

    /**
     * @suppress
     */
    object GRAY63 : Color("gray63")

    /**
     * @suppress
     */
    object GREY63 : Color("grey63")

    /**
     * @suppress
     */
    object GRAY62 : Color("gray62")

    /**
     * @suppress
     */
    object GREY62 : Color("grey62")

    /**
     * @suppress
     */
    object GRAY61 : Color("gray61")

    /**
     * @suppress
     */
    object GREY61 : Color("grey61")

    /**
     * @suppress
     */
    object GRAY60 : Color("gray60")

    /**
     * @suppress
     */
    object GREY60 : Color("grey60")

    /**
     * @suppress
     */
    object GRAY59 : Color("gray59")

    /**
     * @suppress
     */
    object GREY59 : Color("grey59")

    /**
     * @suppress
     */
    object GRAY58 : Color("gray58")

    /**
     * @suppress
     */
    object GREY58 : Color("grey58")

    /**
     * @suppress
     */
    object GRAY57 : Color("gray57")

    /**
     * @suppress
     */
    object GREY57 : Color("grey57")

    /**
     * @suppress
     */
    object GRAY56 : Color("gray56")

    /**
     * @suppress
     */
    object GREY56 : Color("grey56")

    /**
     * @suppress
     */
    object GRAY55 : Color("gray55")

    /**
     * @suppress
     */
    object GREY55 : Color("grey55")

    /**
     * @suppress
     */
    object GRAY54 : Color("gray54")

    /**
     * @suppress
     */
    object GREY54 : Color("grey54")

    /**
     * @suppress
     */
    object GRAY53 : Color("gray53")

    /**
     * @suppress
     */
    object GREY53 : Color("grey53")

    /**
     * @suppress
     */
    object GRAY52 : Color("gray52")

    /**
     * @suppress
     */
    object GREY52 : Color("grey52")

    /**
     * @suppress
     */
    object GRAY51 : Color("gray51")

    /**
     * @suppress
     */
    object GREY51 : Color("grey51")

    /**
     * @suppress
     */
    object FRACTAL : Color("fractal")

    /**
     * @suppress
     */
    object GRAY50 : Color("gray50")

    /**
     * @suppress
     */
    object GREY50 : Color("grey50")

    /**
     * @suppress
     */
    object GRAY : Color("gray")

    /**
     * @suppress
     */
    object GREY : Color("grey")

    /**
     * @suppress
     */
    object GRAY49 : Color("gray49")

    /**
     * @suppress
     */
    object GREY49 : Color("grey49")

    /**
     * @suppress
     */
    object GRAY48 : Color("gray48")

    /**
     * @suppress
     */
    object GREY48 : Color("grey48")

    /**
     * @suppress
     */
    object GRAY47 : Color("gray47")

    /**
     * @suppress
     */
    object GREY47 : Color("grey47")

    /**
     * @suppress
     */
    object GRAY46 : Color("gray46")

    /**
     * @suppress
     */
    object GREY46 : Color("grey46")

    /**
     * @suppress
     */
    object GRAY45 : Color("gray45")

    /**
     * @suppress
     */
    object GREY45 : Color("grey45")

    /**
     * @suppress
     */
    object GRAY44 : Color("gray44")

    /**
     * @suppress
     */
    object GREY44 : Color("grey44")

    /**
     * @suppress
     */
    object GRAY43 : Color("gray43")

    /**
     * @suppress
     */
    object GREY43 : Color("grey43")

    /**
     * @suppress
     */
    object GRAY42 : Color("gray42")

    /**
     * @suppress
     */
    object GREY42 : Color("grey42")

    /**
     * @suppress
     */
    object DIMGRAY : Color("dimgray")

    /**
     * @suppress
     */
    object DIMGREY : Color("dimgrey")

    /**
     * @suppress
     */
    object GRAY41 : Color("gray41")

    /**
     * @suppress
     */
    object GREY41 : Color("grey41")

    /**
     * @suppress
     */
    object GRAY40 : Color("gray40")

    /**
     * @suppress
     */
    object GREY40 : Color("grey40")

    /**
     * @suppress
     */
    object GRAY39 : Color("gray39")

    /**
     * @suppress
     */
    object GREY39 : Color("grey39")

    /**
     * @suppress
     */
    object GRAY38 : Color("gray38")

    /**
     * @suppress
     */
    object GREY38 : Color("grey38")

    /**
     * @suppress
     */
    object GRAY37 : Color("gray37")

    /**
     * @suppress
     */
    object GREY37 : Color("grey37")

    /**
     * @suppress
     */
    object GRAY36 : Color("gray36")

    /**
     * @suppress
     */
    object GREY36 : Color("grey36")

    /**
     * @suppress
     */
    object GRAY35 : Color("gray35")

    /**
     * @suppress
     */
    object GREY35 : Color("grey35")

    /**
     * @suppress
     */
    object GRAY34 : Color("gray34")

    /**
     * @suppress
     */
    object GREY34 : Color("grey34")

    /**
     * @suppress
     */
    object GRAY33 : Color("gray33")

    /**
     * @suppress
     */
    object GREY33 : Color("grey33")

    /**
     * @suppress
     */
    object GRAY32 : Color("gray32")

    /**
     * @suppress
     */
    object GREY32 : Color("grey32")

    /**
     * @suppress
     */
    object GRAY31 : Color("gray31")

    /**
     * @suppress
     */
    object GREY31 : Color("grey31")

    /**
     * @suppress
     */
    object GRAY30 : Color("gray30")

    /**
     * @suppress
     */
    object GREY30 : Color("grey30")

    /**
     * @suppress
     */
    object GRAY29 : Color("gray29")

    /**
     * @suppress
     */
    object GREY29 : Color("grey29")

    /**
     * @suppress
     */
    object GRAY28 : Color("gray28")

    /**
     * @suppress
     */
    object GREY28 : Color("grey28")

    /**
     * @suppress
     */
    object GRAY27 : Color("gray27")

    /**
     * @suppress
     */
    object GREY27 : Color("grey27")

    /**
     * @suppress
     */
    object GRAY26 : Color("gray26")

    /**
     * @suppress
     */
    object GREY26 : Color("grey26")

    /**
     * @suppress
     */
    object GRAY25 : Color("gray25")

    /**
     * @suppress
     */
    object GREY25 : Color("grey25")

    /**
     * @suppress
     */
    object GRAY24 : Color("gray24")

    /**
     * @suppress
     */
    object GREY24 : Color("grey24")

    /**
     * @suppress
     */
    object GRAY23 : Color("gray23")

    /**
     * @suppress
     */
    object GREY23 : Color("grey23")

    /**
     * @suppress
     */
    object GRAY22 : Color("gray22")

    /**
     * @suppress
     */
    object GREY22 : Color("grey22")

    /**
     * @suppress
     */
    object GRAY21 : Color("gray21")

    /**
     * @suppress
     */
    object GREY21 : Color("grey21")

    /**
     * @suppress
     */
    object GRAY20 : Color("gray20")

    /**
     * @suppress
     */
    object GREY20 : Color("grey20")

    /**
     * @suppress
     */
    object GRAY19 : Color("gray19")

    /**
     * @suppress
     */
    object GREY19 : Color("grey19")

    /**
     * @suppress
     */
    object GRAY18 : Color("gray18")

    /**
     * @suppress
     */
    object GREY18 : Color("grey18")

    /**
     * @suppress
     */
    object GRAY17 : Color("gray17")

    /**
     * @suppress
     */
    object GREY17 : Color("grey17")

    /**
     * @suppress
     */
    object GRAY16 : Color("gray16")

    /**
     * @suppress
     */
    object GREY16 : Color("grey16")

    /**
     * @suppress
     */
    object GRAY15 : Color("gray15")

    /**
     * @suppress
     */
    object GREY15 : Color("grey15")

    /**
     * @suppress
     */
    object GRAY14 : Color("gray14")

    /**
     * @suppress
     */
    object GREY14 : Color("grey14")

    /**
     * @suppress
     */
    object GRAY13 : Color("gray13")

    /**
     * @suppress
     */
    object GREY13 : Color("grey13")

    /**
     * @suppress
     */
    object GRAY12 : Color("gray12")

    /**
     * @suppress
     */
    object GREY12 : Color("grey12")

    /**
     * @suppress
     */
    object GRAY11 : Color("gray11")

    /**
     * @suppress
     */
    object GREY11 : Color("grey11")

    /**
     * @suppress
     */
    object GRAY10 : Color("gray10")

    /**
     * @suppress
     */
    object GREY10 : Color("grey10")

    /**
     * @suppress
     */
    object GRAY9 : Color("gray9")

    /**
     * @suppress
     */
    object GREY9 : Color("grey9")

    /**
     * @suppress
     */
    object GRAY8 : Color("gray8")

    /**
     * @suppress
     */
    object GREY8 : Color("grey8")

    /**
     * @suppress
     */
    object GRAY7 : Color("gray7")

    /**
     * @suppress
     */
    object GREY7 : Color("grey7")

    /**
     * @suppress
     */
    object GRAY6 : Color("gray6")

    /**
     * @suppress
     */
    object GREY6 : Color("grey6")

    /**
     * @suppress
     */
    object GRAY5 : Color("gray5")

    /**
     * @suppress
     */
    object GREY5 : Color("grey5")

    /**
     * @suppress
     */
    object GRAY4 : Color("gray4")

    /**
     * @suppress
     */
    object GREY4 : Color("grey4")

    /**
     * @suppress
     */
    object GRAY3 : Color("gray3")

    /**
     * @suppress
     */
    object GREY3 : Color("grey3")

    /**
     * @suppress
     */
    object GRAY2 : Color("gray2")

    /**
     * @suppress
     */
    object GREY2 : Color("grey2")

    /**
     * @suppress
     */
    object GRAY1 : Color("gray1")

    /**
     * @suppress
     */
    object GREY1 : Color("grey1")

    /**
     * @suppress
     */
    object BLACK : Color("black")

    /**
     * @suppress
     */
    object GRAY0 : Color("gray0")

    /**
     * @suppress
     */
    object GREY0 : Color("grey0")

    /**
     * @suppress
     */
    object OPAQUE : Color("opaque")

    /**
     * @suppress
     */
    object NONE : Color("none")

    /**
     * @suppress
     */
    object TRANSPARENT : Color("transparent")
}
