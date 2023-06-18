package dev.senk0n.moerisuto.core.navigation.tabs

import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.push
import dev.senk0n.moerisuto.core.navigation.ComponentConfig
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.annotations.Scope

class RootNavigator(
    private val tabsNavigation: TabsNavigation,
    private val tabNavigation: StackNavigation<ComponentConfig>,
) {
    fun navigateWithinTab(inTabNavigation: StackNavigation<ComponentConfig>.() -> Unit) {
        tabNavigation.inTabNavigation()
    }
    fun navigateThroughTabs(navigateTabs: TabsNavigation.() -> Unit) {
        tabsNavigation.navigateTabs()
    }
}

class TabsNavigation(
    private val tabNavigation: StackNavigation<ComponentConfig>,
    private val tabsMetaProvider: () -> List<TabMetadata>
) {
    val activeTabs: List<TabMetadata> get() = tabsMetaProvider()
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
    @get:TabsScope @get:Provides val rootNavigator: RootNavigator
)