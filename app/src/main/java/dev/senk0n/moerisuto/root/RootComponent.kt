package dev.senk0n.moerisuto.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.StackNavigationSource
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import dev.senk0n.moerisuto.core.navigation.ComponentConfig
import dev.senk0n.moerisuto.core.navigation.ComponentFactory
import dev.senk0n.moerisuto.core.navigation.ComponentFactoryDI
import dev.senk0n.moerisuto.core.navigation.ComponentIntent
import dev.senk0n.moerisuto.core.navigation.ComponentView
import dev.senk0n.moerisuto.core.navigation.create
import dev.senk0n.moerisuto.core.navigation.tabs.RootNavigator
import dev.senk0n.moerisuto.core.navigation.tabs.TabMetadata
import dev.senk0n.moerisuto.core.navigation.tabs.AppDI
import dev.senk0n.moerisuto.core.navigation.tabs.TabsNavigation
import dev.senk0n.moerisuto.core.navigation.tabs.create
import dev.senk0n.moerisuto.feature.mylist.MyListConfig

interface RootComponent : ComponentView {
    val tabStack: Value<ChildStack<ComponentConfig, ComponentView>>
    val tabsMetadata: Value<List<TabMetadata>>
}

class RootComponentImpl(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    override val tabsMetadata: MutableValue<List<TabMetadata>> = MutableValue(listOf(
        TabMetadata(MyListConfig("anime", "completed"), "anime"),
        TabMetadata(MyListConfig("manga", "in_progress"), "manga"),
        TabMetadata(MyListConfig("ranobe", "on_hold"), "ranobe"),
    ))
    private val tabNavigation = StackNavigation<ComponentConfig>()

    private val appDI: AppDI
    init {
        val tabsNavigator = TabsNavigation(
            tabNavigation = tabNavigation,
            tabsMetaProvider = tabsMetadata::value
        )
        val rootNavigator = RootNavigator(tabsNavigator, tabNavigation)
        appDI = AppDI::class.create(rootNavigator)
    }

    private var componentFactoryDI: ComponentFactoryDI = ComponentFactoryDI::class.create(appDI)
    private var componentFactory: ComponentFactory = componentFactoryDI.componentFactory

    private val appContext: AppContext = AppComponentContextImpl(componentContext, appDI)
    override val tabStack = appContext.appChildStack(
        source = tabNavigation,
        initialStack = { listOf(MyListConfig("anime", "completed")) },
        childFactory = componentFactory::create
    )

    override fun send(event: ComponentIntent) {
        when (event) {
            is ClickTab -> {
                appDI.rootNavigator.navigateThroughTabs { switchTab(event.config) }
            }
        }
    }
}
