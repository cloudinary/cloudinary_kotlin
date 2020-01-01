package com.cloudinary.transformation

class Page private constructor(params: Map<String, Param>) :
    ParamsAction<Page>(params) {
    override fun create(params: Map<String, Param>) = Page(params)

    companion object {
        fun pages(pages: PageNumbersBuilder.() -> Unit): Page {
            val newBuilder = PageNumbersBuilder()
            newBuilder.pages()
            return newBuilder.build()
        }

        fun pages(vararg pages: Int) = PageNumbersBuilder().apply { pages.forEach { this.page(it) } }.build()

        fun layers(vararg names: String) = LayerNamesBuilder(*names).build()

        fun embedded(index: Int): Page = EmbeddedBuilder(index).build()

        fun embedded(name: String) = EmbeddedBuilder(name).build()

        private fun buildParameters(value: ParamValue) =
            Page(Param("page", "pg", value).let { mapOf(Pair(it.key, it)) })
    }

    class LayerNamesBuilder(private vararg val names: String) : TransformationComponentBuilder {
        override fun build(): Action {
            val value =
                ParamValue(listOf(NamedValue("name", ParamValue(names.asList().cldAsParamValueContent(), ";"))))
            return buildParameters(value)
        }
    }

    class EmbeddedBuilder private constructor(private val item: Any) : TransformationComponentBuilder {
        constructor(index: Int) : this(index as Any)
        constructor(name: String) : this(name as Any)

        override fun build() =
            buildParameters(ParamValue(listOfNotNull("embedded", if (item is String) "name" else null, item)))
    }

    class PageNumbersBuilder : TransformationComponentBuilder {
        private var items = mutableListOf<Any>()
        fun page(value: Any) = apply { items.add(value) }
        fun page(page: Int) = apply { items.add(page) }
        fun page(from: Int?, to: Int?) = apply { items.add(Range(from, to)) }

        override fun build() = buildParameters(ParamValue(items.cldAsParamValueContent(), ";"))
    }
}


