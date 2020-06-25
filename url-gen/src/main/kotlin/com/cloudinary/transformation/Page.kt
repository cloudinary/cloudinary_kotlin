package com.cloudinary.transformation

class SmartObject(vararg values: Any) : ParamValue(listOfNotNull("embedded", *values)) {
    companion object {
        fun number(number: Int) = SmartObject(number)
        fun names(vararg names: String) = SmartObject(NamedValue("name", ParamValue(names, ";")))
    }
}

class PsdLayer(value: Any) : ParamValue(value) {
    companion object {
        fun names(vararg names: String) = PsdLayer(NamedValue("name", ParamValue(names, ";")))
    }
}