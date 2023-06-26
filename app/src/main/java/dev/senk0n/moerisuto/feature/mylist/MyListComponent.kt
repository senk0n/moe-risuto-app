package dev.senk0n.moerisuto.feature.mylist

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import dev.senk0n.moerisuto.core.className
import dev.senk0n.moerisuto.core.model.Entry
import dev.senk0n.moerisuto.core.model.EntryFormat
import dev.senk0n.moerisuto.core.model.Progress
import dev.senk0n.moerisuto.core.navigation.ComponentConfig
import dev.senk0n.moerisuto.core.navigation.ComponentIntent
import dev.senk0n.moerisuto.core.navigation.ComponentView
import dev.senk0n.moerisuto.core.navigation.provideComponent
import dev.senk0n.moerisuto.core.navigation.tabs.TabComponentView
import dev.senk0n.moerisuto.core.navigation.tabs.TabMetadata
import dev.senk0n.moerisuto.core.navigation.tabs.provideTab
import dev.senk0n.moerisuto.feature.mediaitem.MediaItemConfig
import dev.senk0n.moerisuto.root.AppContext
import dev.senk0n.moerisuto.root.appChildStack
import me.tatarka.inject.annotations.IntoMap
import me.tatarka.inject.annotations.Provides

interface MyListComponent : TabComponentView {
    val state: Value<MyListState>
}

class MyListComponentImpl(
    private val config: MyListConfig,
    private val appContext: AppContext
) : MyListComponent, AppContext by appContext {
    override val state: MutableValue<MyListState> = MutableValue(MyListState(config = config))

    override val navigation = StackNavigation<ComponentConfig>()
    override val childStack: Value<ChildStack<ComponentConfig, ComponentView>> =
        appContext.appChildStack(
            source = navigation,
            initialStack = { listOf() },
            childFactory = appDI.componentFactoryDI.componentFactory::create
        )

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

    @Provides
    @IntoMap
    fun provideMyListTab() =
        provideTab<MyListConfig> {
            TabMetadata(
                config = it,
                name = "My List"
            )
        }
}