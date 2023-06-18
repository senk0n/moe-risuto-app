package dev.senk0n.moerisuto.core.navigation

import dev.senk0n.moerisuto.root.AppContext
import me.tatarka.inject.annotations.Inject
import kotlin.reflect.KClass

@Inject
class ComponentFactory(
    private val providerMap: Map<KClass<out ComponentConfig>, ComponentProvider>
) {
    fun create(key: ComponentConfig, context: AppContext): ComponentView {
        val provider = providerMap[key::class]
        return provider?.provide(key, context)
            ?: providerMap[ComponentConfig::class]?.provide(key, context)
            ?: error("no applicable ComponentProvider found")
    }
}

fun interface ComponentProvider {
    fun provide(config: ComponentConfig, context: AppContext): ComponentView
}

inline fun <reified Config : ComponentConfig> provideComponent(
    crossinline provider: AppContext.(Config) -> ComponentView
): Pair<KClass<out ComponentConfig>, ComponentProvider> =
    Config::class to ComponentProvider { config, context ->
        val parameter = config as Config
        context.provider(parameter)
    }