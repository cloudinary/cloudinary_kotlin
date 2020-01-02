package com.cloudinary.transformation

class Extract private constructor(params: Map<String, Param>) :
    ParamsAction<Extract>(params) {
    override fun create(params: Map<String, Param>) = Extract(params)

    companion object {
        fun pages(pages: PageNumbersBuilder.() -> Unit): Extract {
            val newBuilder = PageNumbersBuilder()
            newBuilder.pages()
            return newBuilder.build()
        }

        fun pages(vararg pages: Int) = PageNumbersBuilder().apply { pages.forEach { this.page(it) } }.build()

        fun frame(frame: Int) = pages(frame)

        fun layers(vararg names: String) = LayerNamesBuilder(names).build()

        fun smartObject(index: Int): Extract = EmbeddedBuilder(index).build()

        fun smartObject(vararg names: String) = EmbeddedBuilder(names).build()

        private fun buildParameters(value: ParamValue) =
            Extract(Param("page", "pg", value).let { mapOf(Pair(it.key, it)) })
    }


    class LayerNamesBuilder(private val names: Array<out String>) : TransformationComponentBuilder {
        override fun build(): Action {
            val value = ParamValue(listOf(buildValueOfNames(names.asList())))
            return buildParameters(value)
        }

    }

    class EmbeddedBuilder private constructor(private val items: List<Any>) : TransformationComponentBuilder {
        internal constructor(index: Int) : this(listOf(index))
        internal constructor(names: Array<out String>) : this(names.toList())

        override fun build(): Extract {
            val value = if (items.first() is String)
                buildValueOfNames(items)
            else
                SimpleValue(items.first()) // TODO only one page is possible using embedded?

            return buildParameters(ParamValue(listOfNotNull("embedded", value)))

        }
    }

    class PageNumbersBuilder : TransformationComponentBuilder {
        private var items = mutableListOf<Any>()
        fun page(value: Any) = apply { items.add(value) }
        fun page(page: Int) = apply { items.add(page) }
        fun page(from: Int?, to: Int?) = apply { items.add(Range(from, to)) }

        override fun build() = buildParameters(ParamValue(items.cldAsParamValueContent(), ";"))
    }
}

fun buildValueOfNames(names: List<Any>) = NamedValue("name", ParamValue(names.cldAsParamValueContent(), ";"))


