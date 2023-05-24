package dev.senk0n.moerisuto.core.navigation.tabs

import dev.senk0n.moerisuto.core.di.DepProvider
import dev.senk0n.moerisuto.core.di.MultibindingFactory
import me.tatarka.inject.annotations.Inject
import kotlin.reflect.KClass

@Inject
class TabFactory(
    override val providerMap: Map<KClass<out TabConfig>, DepProvider<TabConfig, TabComponentView>>
) : MultibindingFactory<TabConfig, TabComponentView>