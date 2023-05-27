package dev.senk0n.moerisuto.core.search

sealed interface Parameter {

    sealed interface Group {
        val name: String
    }

    interface ItemList : Group {
        val list: List<ParameterItem>
    }

    interface SelectList : Group {
        val list: List<ParameterItem.Toggle>
    }

    interface Single : Parameter {
        val parameter: ParameterItem
        val list: Group?
    }
}

sealed interface ParameterItem {
    val name: String
    val description: String?

    interface Toggle : ParameterItem {
        val value: Boolean
    }

    interface Triple : ParameterItem {
        val value: Value

        sealed interface Value
        object Off : Value
        object Inc : Value
        object Exc : Value
    }

    interface Range : ParameterItem {
        val range: ClosedRange<Int>
        val bounds: ClosedRange<Int>
    }

    interface Slider : ParameterItem {
        val value: Int
        val bounds: ClosedRange<Int>
    }
}

