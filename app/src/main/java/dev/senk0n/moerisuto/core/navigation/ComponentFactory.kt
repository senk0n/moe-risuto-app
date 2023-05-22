package dev.senk0n.moerisuto.core.navigation

import dev.senk0n.moerisuto.core.di.ClassKey
import dev.senk0n.moerisuto.core.di.DepProvider
import dev.senk0n.moerisuto.core.di.MultibindingFactory
import me.tatarka.inject.annotations.Inject

@Inject
class ComponentFactory(
    override val providerMap: Map<ClassKey, DepProvider<ComponentConfig, ComponentView>>
) : MultibindingFactory<ComponentConfig, ComponentView>
