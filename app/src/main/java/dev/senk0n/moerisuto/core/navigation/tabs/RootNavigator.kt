package dev.senk0n.moerisuto.core.navigation.tabs

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
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
        val tabComponent = tabStackProvider().active.instance as? TabComponentView
        tabComponent?.inTabNavigation()
    }
    fun navigateThroughTabs(navigateTabs: TabsNavigation.() -> Unit) {
        navigateTabs(tabsNavigation)
    }
}

fun interface TabStackProvider : () -> Value<ChildStack<ComponentConfig, ComponentView>>

@Inject
class TabsNavigation(
    private val tabNavigation: StackNavigation<ComponentConfig>
) {
    fun switchTab(config: ComponentConfig) {
        tabNavigation.bringToFront(config)
    }
    fun pushTab(config: ComponentConfig) {
        tabNavigation.push(config)
    }
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
    abstract val rootNavigator: RootNavigator
    abstract val tabsNavigation: TabsNavigation

    @get:Provides
    val tabsMetadata: MutableValue<TabsMetadata> = MutableValue(TabsMetadata())
}