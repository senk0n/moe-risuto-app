package dev.senk0n.moerisuto.core.navigation.tabs

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.StackNavigator
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import dev.senk0n.moerisuto.core.navigation.ComponentConfig
import dev.senk0n.moerisuto.core.navigation.ComponentFactoryDI
import dev.senk0n.moerisuto.core.navigation.ComponentView
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Inject
import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.annotations.Scope

@Inject
class RootNavigator(
    private val tabsNavigation: TabsNavigation,
    private val tabStackProvider: TabStackProvider,
) {
    fun navigateWithinTab(inTabNavigation: TabComponentView.() -> Unit) {
        val tabComponent = tabStackProvider.provide().active.instance as? TabComponentView
        tabComponent?.inTabNavigation()
    }

    fun navigateThroughTabs(navigateTabs: TabsNavigation.() -> Unit) {
        navigateTabs(tabsNavigation)
    }
}

fun interface TabStackProvider {
    fun provide(): Value<ChildStack<ComponentConfig, ComponentView>>
}

@Inject
class TabsNavigation(
    private val tabNavigation: StackNavigation<ComponentConfig>,
    private val tabsMetadata: MutableValue<TabsMetadata>,
    private val tabFactory: TabFactory,
) {
    fun switchTab(config: ComponentConfig) {
        tabNavigation.bringToFront(config)
    }

    fun addTab(config: ComponentConfig) {
        val tab = tabFactory.create(config)
        if (tab != null) {
            tabsMetadata.update {
                it.copy(mainTabs = it.mainTabs + tab)
            }
        }
    }

    fun removeTab(config: ComponentConfig) {
        tabsMetadata.update {
            it.copy(mainTabs = it.mainTabs.filterNot { tab -> tab.config == config })
        }
    }

    val tabs: Value<TabsMetadata> = tabsMetadata
}

@Scope
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER)
annotation class TabsScope

@TabsScope
@Component
abstract class AppDI(
    @Component val componentFactoryDI: ComponentFactoryDI,
    @Component val navDI: NavDI,
)

@Component
abstract class NavDI(
    @get:Provides val tabNavigation: StackNavigation<ComponentConfig>,
    @get:Provides val tabStackProvider: TabStackProvider,
) {
    @get:Provides
    val tabsMetadata: MutableValue<TabsMetadata> = MutableValue(TabsMetadata())
}

fun <C : Any> StackNavigator<C>.bringToFrontEq(configuration: C, onComplete: () -> Unit = {}) {
    navigate(
        transformer = { stack -> stack.filterNot { it == configuration } + configuration },
        onComplete = { _, _ -> onComplete() },
    )
}
