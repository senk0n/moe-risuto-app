package dev.senk0n.moerisuto.feature.mylist

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import dev.senk0n.moerisuto.core.model.Entry
import dev.senk0n.moerisuto.core.navigation.ComponentConfig
import dev.senk0n.moerisuto.core.navigation.ComponentFactory
import dev.senk0n.moerisuto.core.navigation.ComponentIntent
import dev.senk0n.moerisuto.core.navigation.ComponentView
import dev.senk0n.moerisuto.core.navigation.provideComponent
import dev.senk0n.moerisuto.core.navigation.tabs.AppDI
import dev.senk0n.moerisuto.core.navigation.tabs.RootNavigator
import dev.senk0n.moerisuto.core.navigation.tabs.TabComponentView
import dev.senk0n.moerisuto.core.navigation.tabs.TabMetadata
import dev.senk0n.moerisuto.core.navigation.tabs.bringToFrontEq
import dev.senk0n.moerisuto.core.navigation.tabs.provideTab
import dev.senk0n.moerisuto.feature.mediaitem.MediaItemConfig
import dev.senk0n.moerisuto.root.AppContext
import dev.senk0n.moerisuto.root.appChildStack
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Inject
import me.tatarka.inject.annotations.IntoMap
import me.tatarka.inject.annotations.Provides

interface MyListComponent : TabComponentView {
    val state: Value<MyListState>
}

@Inject
class MyListComponentImpl(
    @Assisted private val appContext: AppContext,
    @Assisted private val config: MyListConfig,
    private val componentFactory: ComponentFactory,
) : MyListComponent, AppContext by appContext {
    override val state: MutableValue<MyListState> = MutableValue(MyListState(config = config))

    override val navigation = StackNavigation<ComponentConfig>()
    override val childStack: Value<ChildStack<ComponentConfig, ComponentView>> =
        appChildStack(
            source = navigation,
            initialStack = { listOf(MediaItemConfig("anime", "completed")) },
            childFactory = { key, context -> componentFactory.create(key, context) }
        )

    override fun send(event: ComponentIntent) {
        when (event) {
            is OpenEntryDetailsIntent -> {
                navigation.bringToFrontEq(config)
            }
        }
    }

}

data class MyListState(
    val config: MyListConfig? = null,
    val isLoading: Boolean = true,
    val entriesList: List<Entry> = emptyList(),
)

interface MyListDIModule {
    @Provides
    @IntoMap
    fun provideMyListComponent() =
        provideComponent<MyListConfig> {
            MyListComponentDI::class.create(appDI).creator(this, it)
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

@Component
abstract class MyListComponentDI(
    @Component val appDI: AppDI,
) {
    @Provides
    fun MyListComponentImpl.bind(): MyListComponent = this
    abstract val creator: (AppContext, MyListConfig) -> MyListComponent
}