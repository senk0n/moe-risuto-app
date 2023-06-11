package dev.senk0n.moerisuto.core.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import dev.senk0n.moerisuto.core.navigation.ComponentView
import me.tatarka.inject.annotations.Inject
import kotlin.reflect.KClass

@Inject
class ComposeFactory(
    private val providerMap: Map<KClass<out ComponentView>, ComposeProvider>
) {
    fun create(key: ComponentView): ComposeProvider? {
        return providerMap[key::class]
    }
}

fun interface ComposeProvider {
    @Composable
    fun Display(component: ComponentView, modifier: Modifier)
}

inline fun <reified Component : ComponentView> provideCompose(
    crossinline provider: @Composable (Component, Modifier) -> Unit
): Pair<KClass<out ComponentView>, ComposeProvider> =
    Component::class to ComposeProvider { component, modifier ->
        provider.invoke(component as Component, modifier)
    }