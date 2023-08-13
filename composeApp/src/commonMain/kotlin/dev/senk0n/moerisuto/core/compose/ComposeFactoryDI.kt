package dev.senk0n.moerisuto.core.compose

import dev.senk0n.moerisuto.feature.mediaitem.MediaItemDIContent
import dev.senk0n.moerisuto.feature.mylist.MyListDIContent
import dev.senk0n.moerisuto.feature.settings.SettingsDIContent
import me.tatarka.inject.annotations.Component

@Component
abstract class ComposeFactoryDI : ComposeMapProviders {
    abstract val composeFactory: ComposeFactory
}

interface ComposeMapProviders :
    SettingsDIContent,
    MediaItemDIContent,
    MyListDIContent