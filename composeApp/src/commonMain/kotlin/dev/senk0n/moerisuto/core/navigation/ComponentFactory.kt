package dev.senk0n.moerisuto.core.navigation

import com.arkivanov.decompose.ComponentContext
import dev.senk0n.moerisuto.core.navigation.tabs.RootScope
import me.tatarka.inject.annotations.Inject
import kotlin.reflect.KClass

@RootScope
@Inject
class ComponentFactory(
    private val providerMap: Map<KClass<out ComponentConfig>, ComponentProvider>
) {
    fun ComponentView.create(
        cfg: ComponentConfig,
        context: ComponentContext,
        di: ComponentDI,
    ): ComponentView {
        val provider = providerMap[cfg::class]
        val sink = ComponentSink { send(it) }
        return provider?.provide(cfg, context, di, sink)
            ?: providerMap[ComponentConfig::class]?.provide(cfg, context, di, sink)
            ?: error("no applicable ComponentProvider found")
    }
}

fun interface ComponentProvider {
    fun provide(
        config: ComponentConfig,
        context: ComponentContext,
        componentDI: ComponentDI,
        parentSink: ComponentSink,
    ): ComponentView
}

inline fun <reified Config : ComponentConfig, reified ParentDI : ComponentDI> provideComponent(
    crossinline provider: ComponentContext.(cfg: Config, di: ParentDI, ComponentSink) -> ComponentView
): Pair<KClass<out ComponentConfig>, ComponentProvider> =
    Config::class to ComponentProvider { config, context, componentDI, parentSink ->
        val cfg = config as Config
        val di = componentDI as ParentDI
        context.provider(cfg, di, parentSink)
    }