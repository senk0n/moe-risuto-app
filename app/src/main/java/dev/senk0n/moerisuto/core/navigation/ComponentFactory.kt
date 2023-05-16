package dev.senk0n.moerisuto.core.navigation

class ComponentFactory(
    private val componentProviderMap: Map<ComponentConfig, Lazy<ComponentProvider>>
) {
    fun createView(config: ComponentConfig): ComponentView? {
        val provider = componentProviderMap[config]?.value
        return provider?.provide(config)
    }
}

fun interface ComponentProvider {
    fun provide(config: ComponentConfig): ComponentView
}