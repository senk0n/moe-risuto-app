package dev.senk0n.moerisuto.core.navigation.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.senk0n.moerisuto.core.navigation.ComponentView
import me.tatarka.inject.annotations.Inject
import kotlin.reflect.KClass

@Inject
class ComposeFactory(
    private val providerMap: Map<KClass<out ComponentView>, ComposeProvider>
) {
    @Composable
    fun Display(key: ComponentView, modifier: Modifier = Modifier, error: @Composable () -> Unit) {
        val provider = providerMap[key::class]
        provider?.Provide(key, modifier) ?: error()
    }
}

fun interface ComposeProvider {
    @Composable
    fun Provide(component: ComponentView, modifier: Modifier)
}

inline fun <reified Component : ComponentView> provideCompose(
    crossinline provider: @Composable (Component, Modifier) -> Unit
): Pair<KClass<out ComponentView>, ComposeProvider> =
    Component::class to ComposeProvider { component, modifier ->
        val parameter = component as Component
        provider(parameter, modifier)
    }