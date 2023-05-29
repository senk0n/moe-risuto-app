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
    fun create(key: ComponentView): (@Composable (ComponentView, Modifier) -> Unit)? {
        val provider = providerMap[key::class]
        return provider?.provide()
    }
}

fun interface ComposeProvider {
    fun provide(): @Composable (ComponentView, Modifier) -> Unit
}

inline fun <reified Key : ComponentView> provideCompose(
    crossinline provider: () -> @Composable (ComponentView, Modifier) -> Unit
): Pair<KClass<out ComponentView>, ComposeProvider> = Key::class to ComposeProvider {
    provider()
}