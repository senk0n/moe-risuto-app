package dev.senk0n.moerisuto.core.navigation

import me.tatarka.inject.annotations.Component

@Component
abstract class ComponentFactoryDI : ComponentsMapProviders {
    abstract val componentFactory: ComponentFactory
}

interface ComponentsMapProviders