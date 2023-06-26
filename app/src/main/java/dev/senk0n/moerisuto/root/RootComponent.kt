package dev.senk0n.moerisuto.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import dev.senk0n.moerisuto.core.navigation.ComponentConfig
import dev.senk0n.moerisuto.core.navigation.ComponentFactory
import dev.senk0n.moerisuto.core.navigation.ComponentFactoryDI
import dev.senk0n.moerisuto.core.navigation.ComponentIntent
import dev.senk0n.moerisuto.core.navigation.ComponentView
import dev.senk0n.moerisuto.core.navigation.create
import dev.senk0n.moerisuto.core.navigation.tabs.TabMetadata
import dev.senk0n.moerisuto.core.navigation.tabs.AppDI
import dev.senk0n.moerisuto.core.navigation.tabs.NavDI
import dev.senk0n.moerisuto.core.navigation.tabs.TabsMetadata
import dev.senk0n.moerisuto.core.navigation.tabs.create
import dev.senk0n.moerisuto.feature.mylist.MyListConfig

interface RootComponent : ComponentView {
    val tabStack: Value<ChildStack<ComponentConfig, ComponentView>>
    val tabsMetadata: Value<TabsMetadata>
}

class RootComponentImpl(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {
    private var componentFactoryDI: ComponentFactoryDI = ComponentFactoryDI::class.create()
    private var componentFactory: ComponentFactory = componentFactoryDI.componentFactory

    private val tabNavigation = StackNavigation<ComponentConfig>()

    private val appDI: AppDI
    private val navDI: NavDI
    init {
        navDI = NavDI::class.create(
            tabNavigation = tabNavigation,
            tabStackProvider = { tabStack },
        )
        appDI = AppDI::class.create(
            componentFactoryDI = componentFactoryDI,
            navDI = navDI,
        )
    }

    override val tabsMetadata: MutableValue<TabsMetadata> = navDI.tabsMetadata

    private val appContext: AppContext = AppComponentContextImpl(componentContext, appDI)
    override val tabStack = appContext.appChildStack(
        source = tabNavigation,
        initialStack = { listOf(MyListConfig("anime", "completed")) },
        childFactory = componentFactory::create
    )

    override fun send(event: ComponentIntent) {
        when (event) {
            is ClickTab -> {
                appDI.navDI.rootNavigator.navigateThroughTabs { switchTab(event.config) }
            }
        }
    }
}
