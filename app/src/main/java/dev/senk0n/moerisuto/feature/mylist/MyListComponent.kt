package dev.senk0n.moerisuto.feature.mylist

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import dev.senk0n.moerisuto.core.model.Entry
import dev.senk0n.moerisuto.core.navigation.ComponentConfig
import dev.senk0n.moerisuto.core.navigation.ComponentIntent
import dev.senk0n.moerisuto.core.navigation.ComponentView
import dev.senk0n.moerisuto.core.navigation.provideComponent
import dev.senk0n.moerisuto.core.navigation.tabs.RootNavigator
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
    private val appContext: AppContext,
    private val rootNavigator: RootNavigator,
) : MyListComponent, AppContext by appContext {
    override val state: MutableValue<MyListState> = MutableValue(MyListState(config = config))

    override val navigation = StackNavigation<ComponentConfig>()
    override val childStack: Value<ChildStack<ComponentConfig, ComponentView>> =
        appChildStack(
            source = navigation,
            initialStack = { listOf(MediaItemConfig("anime", "completed")) },
            childFactory = appDI.componentFactoryDI.componentFactory::create
        )

    override fun send(event: ComponentIntent) {
        when (event) {
            is OpenEntryDetailsIntent -> {

            }

            else -> rootNavigator.navigateWithinTab {
                val config = MediaItemConfig("oengrerg", "FFFFFFF")
                navigation.push(config)
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
        provideComponent<MyListConfig> {
            MyListComponentImpl(it, this, appDI.rootNavigator)
        }

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