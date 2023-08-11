package dev.senk0n.moerisuto.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.value.Value
import dev.senk0n.moerisuto.core.navigation.ComponentConfig
import dev.senk0n.moerisuto.core.navigation.ComponentFactory
import dev.senk0n.moerisuto.core.navigation.ComponentFactoryDI
import dev.senk0n.moerisuto.core.navigation.ComponentIntent
import dev.senk0n.moerisuto.core.navigation.ComponentView
import dev.senk0n.moerisuto.core.navigation.create
import dev.senk0n.moerisuto.core.navigation.tabs.AppDI
import dev.senk0n.moerisuto.core.navigation.tabs.NavDI
import dev.senk0n.moerisuto.core.navigation.tabs.RootNavigator
import dev.senk0n.moerisuto.core.navigation.tabs.TabsMetadata
import dev.senk0n.moerisuto.core.navigation.tabs.TabsNavigation
import dev.senk0n.moerisuto.core.navigation.tabs.create
import me.tatarka.inject.annotations.Component

interface RootComponent : ComponentView {
    val tabStack: Value<ChildStack<ComponentConfig, ComponentView>>
    val tabsMetadata: Value<TabsMetadata>
}

class RootComponentImpl(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {
    private val tabNavigation = StackNavigation<ComponentConfig>()

    private val rootDI: RootDI
    init {
        val componentFactoryDI = ComponentFactoryDI::class.create()
        val navDI = NavDI::class.create(
            tabNavigation = tabNavigation,
            tabStackProvider = { tabStack },
        )
        val appDI = AppDI::class.create(
            componentFactoryDI = componentFactoryDI,
            navDI = navDI,
        )
        rootDI = RootDI::class.create(
            appDI = appDI,
        )
    }

    override val tabsMetadata: Value<TabsMetadata> = rootDI.tabsNavigation.tabs

    private val appContext: AppContext = AppComponentContextImpl(componentContext, rootDI.appDI)
    override val tabStack = appContext.appChildStack(
        source = tabNavigation,
        initialStack = { tabsMetadata.value.mainTabs.take(1).map { it.config } },
        childFactory = { key, context -> rootDI.componentFactory.create(key, context) }
    )

    override fun send(event: ComponentIntent) {
        when (event) {
            is ClickTab -> {
                rootDI.rootNavigator.navigateThroughTabs { switchTab(event.config) }
            }
        }
    }
}

@Component
abstract class RootDI(
    @Component val appDI: AppDI,
) {
    abstract val rootNavigator: RootNavigator
    abstract val tabsNavigation: TabsNavigation
    abstract val componentFactory: ComponentFactory
}