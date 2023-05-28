package dev.senk0n.moerisuto.core.navigation.tabs

import dev.senk0n.moerisuto.core.navigation.ComponentConfig
import me.tatarka.inject.annotations.Inject
import kotlin.reflect.KClass

@Inject
class TabProvider(
    private val tabs: Map<KClass<out ComponentConfig>, TabMetaProvider>,
)

fun interface TabMetaProvider {
    fun provide(): TabMetadata
}