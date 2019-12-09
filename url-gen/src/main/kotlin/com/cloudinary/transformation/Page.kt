package com.cloudinary.transformation

class Page private constructor(params: Map<String, Param>) :
    Action<Page>(params) {
    override fun create(params: Map<String, Param>) = Page(params)

    companion object {
        //
        private fun buildParameters(value: ParamValue) =
            Page(Param("page", "pg", value).let { mapOf(Pair(it.key, it)) })
//
//        fun layers() = LayerNamesBuilder()
//        fun numbers() = PageNumbersBuilder()
//        fun embedded(name: String) = EmbeddedBuilder(name)
//        fun embedded(index: Int) = EmbeddedBuilder(index)
    }

    class LayerNamesBuilder(private vararg val names: String) : TransformationComponentBuilder {
        override fun build() = buildParameters(ParamValue(listOf("name", ParamValue(names.asList(), ";"))))
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

        override fun build() = buildParameters(ParamValue(items, ";"))
    }
}

fun pages(pages: Page.PageNumbersBuilder.() -> Unit): Page {
    val newBuilder = Page.PageNumbersBuilder()
    newBuilder.pages()
    return newBuilder.build()
}

fun pages(vararg pages: Int) = Page.PageNumbersBuilder().apply { pages.forEach { this.page(it) } }.build()

fun layers(vararg names: String) = Page.LayerNamesBuilder(*names).build()

fun embedded(index: Int): Page = Page.EmbeddedBuilder(index).build()

fun embedded(name: String) = Page.EmbeddedBuilder(name).build()

