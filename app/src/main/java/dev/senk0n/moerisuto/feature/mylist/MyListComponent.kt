package dev.senk0n.moerisuto.feature.mylist

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import dev.senk0n.moerisuto.core.model.Entry
import dev.senk0n.moerisuto.core.navigation.ComponentIntent
import dev.senk0n.moerisuto.core.navigation.ComponentView
import dev.senk0n.moerisuto.core.navigation.provideComponent
import dev.senk0n.moerisuto.feature.mediaitem.MediaItemComponentImpl
import dev.senk0n.moerisuto.feature.mediaitem.MediaItemConfig
import me.tatarka.inject.annotations.IntoMap
import me.tatarka.inject.annotations.Provides

interface MyListComponent : ComponentView {
    val state: Value<MyListState>
}

class MyListComponentImpl(
    private val config: MyListConfig,
    private val componentContext: ComponentContext
) : MyListComponent, ComponentContext by componentContext {
    override val state: MutableValue<MyListState> = MutableValue(MyListState(config = config))

    override fun send(event: ComponentIntent) {
        when (event) {
            is OpenEntryDetailsIntent -> {

            }
        }
    }

}

data class MyListState(
    val config: MyListConfig? = null,
    val isLoading: Boolean = true,
    val entriesList: List<Entry> = emptyList(),
)

interface MyListDIComponent {
    @Provides
    @IntoMap
    fun provideMyListComponent() =
        provideComponent<MyListConfig> { MyListComponentImpl(it, this) }
}