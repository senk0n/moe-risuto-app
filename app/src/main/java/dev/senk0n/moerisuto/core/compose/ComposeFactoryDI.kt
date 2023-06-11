package dev.senk0n.moerisuto.core.compose

import me.tatarka.inject.annotations.Component

@Component
abstract class ComposeFactoryDI : ComposeMapProviders {
    abstract val composeFactory: ComposeFactory
}

interface ComposeMapProviders