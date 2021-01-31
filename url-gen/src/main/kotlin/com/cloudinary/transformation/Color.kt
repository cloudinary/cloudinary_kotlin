package com.cloudinary.transformation


sealed class Color(private val value: String, private val prefix: String? = null) {

    override fun toString(): String {
        return if (prefix != null) {
            "$prefix:$value"
        } else {
            value
        }
    }

    companion object {
        fun parseString(color: String) = when {
            color.startsWith("#") || color.startsWith("rgb:") -> rgb(color)
            else -> Named(color)
        }
    }

    fun toString(includePrefix: Boolean): String {
        return if (includePrefix) toString() else value
    }

    class rgb(hex: String) : Color(hex.cldRemoveColorPrefixes(), "rgb")
    class Named(name: String) : Color(name)

    /**
     * @suppress
     */
    object SNOW : Color("snow")

    /**
     * @suppress
     */
    object LIGHTCORAL : Color("lightcoral")

    /**
     * @suppress
     */
    object ROSYBROWN : Color("rosybrown")

    /**
     * @suppress
     */
    object INDIANRED : Color("indianred")

    /**
     * @suppress
     */
    object RED : Color("red")

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
    object DARKRED : Color("darkred")

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
    object PALEVIOLETRED : Color("palevioletred")

    /**
     * @suppress
     */
    object LAVENDERBLUSH : Color("lavenderblush")

    /**
     * @suppress
     */
    object MAROON : Color("maroon")

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
    object MEDIUMVIOLETRED : Color("mediumvioletred")

    /**
     * @suppress
     */
    object VIOLETRED : Color("violetred")

    /**
     * @suppress
     */
    object ORCHID : Color("orchid")

    /**
     * @suppress
     */
    object PLUM : Color("plum")

    /**
     * @suppress
     */
    object THISTLE : Color("thistle")

    /**
     * @suppress
     */
    object VIOLET : Color("violet")

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
    object DARKMAGENTA : Color("darkmagenta")

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
    object DARKVIOLET : Color("darkviolet")

    /**
     * @suppress
     */
    object DARKORCHID : Color("darkorchid")

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
    object MEDIUMPURPLE : Color("mediumpurple")

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
    object MEDIUMBLUE : Color("mediumblue")

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
    object CORNFLOWERBLUE : Color("cornflowerblue")

    /**
     * @suppress
     */
    object LIGHTSTEELBLUE : Color("lightsteelblue")

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
    object ALICEBLUE : Color("aliceblue")

    /**
     * @suppress
     */
    object STEELBLUE : Color("steelblue")

    /**
     * @suppress
     */
    object LIGHTSKYBLUE : Color("lightskyblue")

    /**
     * @suppress
     */
    object SKYBLUE : Color("skyblue")

    /**
     * @suppress
     */
    object DEEPSKYBLUE : Color("deepskyblue")

    /**
     * @suppress
     */
    object LIGHTBLUE : Color("lightblue")

    /**
     * @suppress
     */
    object POWDERBLUE : Color("powderblue")

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
    object LIGHTCYAN : Color("lightcyan")

    /**
     * @suppress
     */
    object PALETURQUOISE : Color("paleturquoise")

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
    object AQUAMARINE : Color("aquamarine")

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
    object MEDIUMSEAGREEN : Color("mediumseagreen")

    /**
     * @suppress
     */
    object SEAGREEN : Color("seagreen")

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
    object PALEGREEN : Color("palegreen")

    /**
     * @suppress
     */
    object DARKSEAGREEN : Color("darkseagreen")

    /**
     * @suppress
     */
    object LIGHTGREEN : Color("lightgreen")

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
    object GREENYELLOW : Color("greenyellow")

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
    object YELLOWGREEN : Color("yellowgreen")

    /**
     * @suppress
     */
    object IVORY : Color("ivory")

    /**
     * @suppress
     */
    object LIGHTYELLOW : Color("lightyellow")

    /**
     * @suppress
     */
    object BEIGE : Color("beige")

    /**
     * @suppress
     */
    object YELLOW : Color("yellow")

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
    object PALEGOLDENROD : Color("palegoldenrod")

    /**
     * @suppress
     */
    object LEMONCHIFFON : Color("lemonchiffon")

    /**
     * @suppress
     */
    object KHAKI : Color("khaki")

    /**
     * @suppress
     */
    object MEDIUMGOLDENROD : Color("mediumgoldenrod")

    /**
     * @suppress
     */
    object GOLD : Color("gold")

    /**
     * @suppress
     */
    object CORNSILK : Color("cornsilk")

    /**
     * @suppress
     */
    object LIGHTGOLDENROD : Color("lightgoldenrod")

    /**
     * @suppress
     */
    object GOLDENROD : Color("goldenrod")

    /**
     * @suppress
     */
    object DARKGOLDENROD : Color("darkgoldenrod")

    /**
     * @suppress
     */
    object FLORALWHITE : Color("floralwhite")

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
    object ORANGE : Color("orange")

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
    object BLANCHEDALMOND : Color("blanchedalmond")

    /**
     * @suppress
     */
    object NAVAJOWHITE : Color("navajowhite")

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
    object BURLYWOOD : Color("burlywood")

    /**
     * @suppress
     */
    object DARKORANGE : Color("darkorange")

    /**
     * @suppress
     */
    object BISQUE : Color("bisque")

    /**
     * @suppress
     */
    object LINEN : Color("linen")

    /**
     * @suppress
     */
    object PERU : Color("peru")

    /**
     * @suppress
     */
    object PEACHPUFF : Color("peachpuff")

    /**
     * @suppress
     */
    object SANDYBROWN : Color("sandybrown")

    /**
     * @suppress
     */
    object CHOCOLATE : Color("chocolate")

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
    object SIENNA : Color("sienna")

    /**
     * @suppress
     */
    object LIGHTSALMON : Color("lightsalmon")

    /**
     * @suppress
     */
    object CORAL : Color("coral")

    /**
     * @suppress
     */
    object DARKSALMON : Color("darksalmon")

    /**
     * @suppress
     */
    object ORANGERED : Color("orangered")

    /**
     * @suppress
     */
    object TOMATO : Color("tomato")

    /**
     * @suppress
     */
    object MISTYROSE : Color("mistyrose")

    /**
     * @suppress
     */
    object SALMON : Color("salmon")

    /**
     * @suppress
     */
    object WHITE : Color("white")

    /**
     * @suppress
     */
    object WHITESMOKE : Color("whitesmoke")

    /**
     * @suppress
     */
    object GAINSBORO : Color("gainsboro")

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
    object SILVER : Color("silver")

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
    object FRACTAL : Color("fractal")

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
    object DIMGRAY : Color("dimgray")

    /**
     * @suppress
     */
    object DIMGREY : Color("dimgrey")

    /**
     * @suppress
     */
    object OPAQUE : Color("opaque")

    /**
     * @suppress
     */
    object BLACK : Color("black")

    /**
     * @suppress
     */
    object NONE : Color("none")

    /**
     * @suppress
     */
    object TRANSPARENT : Color("transparent")
}

internal fun String.cldRemoveColorPrefixes() = replaceFirst("#", "").replaceFirst("rgb", "").replaceFirst(":", "")
