package dev.senk0n.moerisuto.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import dev.senk0n.moerisuto.core.navigation.ComponentConfig
import dev.senk0n.moerisuto.core.navigation.ComponentFactory
import dev.senk0n.moerisuto.core.navigation.ComponentFactoryDI
import dev.senk0n.moerisuto.core.navigation.ComponentIntent
import dev.senk0n.moerisuto.core.navigation.ComponentView
import dev.senk0n.moerisuto.core.navigation.create
import dev.senk0n.moerisuto.core.navigation.tabs.RootNavigator
import dev.senk0n.moerisuto.core.navigation.tabs.TabMetadata
import dev.senk0n.moerisuto.core.navigation.tabs.TabNavigationDI
import dev.senk0n.moerisuto.core.navigation.tabs.TabsNavigation
import dev.senk0n.moerisuto.core.navigation.tabs.create
import dev.senk0n.moerisuto.feature.mylist.MyListConfig

interface RootComponent : ComponentView {
    val tabStack: Value<ChildStack<ComponentConfig, ComponentView>>
    val tabsMetadata: Value<List<TabMetadata>>
    val rootNavigator: RootNavigator
}

class RootComponentImpl(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {
    private var componentFactory: ComponentFactory =
        ComponentFactoryDI::class.create().componentFactory

    override val tabsMetadata: MutableValue<List<TabMetadata>> = MutableValue(listOf(
        TabMetadata(MyListConfig("anime", "completed"), "anime"),
        TabMetadata(MyListConfig("manga", "in_progress"), "manga"),
        TabMetadata(MyListConfig("ranobe", "on_hold"), "ranobe"),
    ))
    private val tabNavigation = StackNavigation<ComponentConfig>()

    private val tabNavigatorDI: TabNavigationDI
    override val rootNavigator: RootNavigator
    init {
        val tabsNavigator = TabsNavigation(
            tabNavigation = tabNavigation,
            tabsMetaProvider = tabsMetadata::value
        )
        rootNavigator = RootNavigator(tabsNavigator, tabNavigation)
        tabNavigatorDI = TabNavigationDI::class.create(rootNavigator)
    }

    private val stack: Value<ChildStack<ComponentConfig, ComponentView>> = childStack(
        source = tabNavigation,
        initialStack = { listOf(MyListConfig("anime", "completed")) },
        childFactory = componentFactory::create
    )
    override val tabStack: Value<ChildStack<ComponentConfig, ComponentView>> = stack

    override fun send(event: ComponentIntent) {}
}
