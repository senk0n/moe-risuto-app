package dev.senk0n.moerisuto.core.navigation.tabs

import com.arkivanov.decompose.router.stack.StackNavigation
import dev.senk0n.moerisuto.core.navigation.ComponentConfig

interface TabNavigation {
    fun navigateWithinTab(inTabNavigation: StackNavigation<ComponentConfig>.() -> Unit)
    fun navigateThroughTabs(tabsNavigation: TabsNavigation.() -> Unit)
//    fun pushScreen(config: ComponentConfig)
}

interface TabsNavigation {
    val activeTabs: List<TabConfig>
    fun switchTab(config: ComponentConfig)
    fun pushTab(config: ComponentConfig)
    fun editTabDot(config: ComponentConfig, toggle: Boolean)
// TODO: fun openSheet(config: ComponentView)
}