package dev.senk0n.moerisuto.core.navigation

import dev.senk0n.moerisuto.core.di.DepProvider
import dev.senk0n.moerisuto.core.di.NavigationFactory
import me.tatarka.inject.annotations.Inject
import kotlin.reflect.KClass

@Inject
class ComponentFactory(
    override val providerMap: Map<KClass<out ComponentConfig>, DepProvider<ComponentConfig, ComponentView>>
) : NavigationFactory<ComponentConfig, ComponentView>
