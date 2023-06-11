package dev.senk0n.moerisuto.core.compose

import dev.senk0n.moerisuto.feature.mediaitem.MediaItemDIContent
import me.tatarka.inject.annotations.Component

@Component
abstract class ComposeFactoryDI : ComposeMapProviders {
    abstract val composeFactory: ComposeFactory
}

interface ComposeMapProviders :
    MediaItemDIContent