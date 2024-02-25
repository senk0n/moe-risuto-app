package dev.senk0n.moerisuto.core.navigation

import dev.senk0n.moerisuto.feature.mediaitem.MediaItemDIModule
import dev.senk0n.moerisuto.feature.mylist.MyListDIModule
import dev.senk0n.moerisuto.feature.settings.SettingsDIModule
import me.tatarka.inject.annotations.Component

@Component
abstract class ComponentFactoryDI : ComponentsMapProviders

interface ComponentsMapProviders :
    SettingsDIModule,
    MediaItemDIModule,
    MyListDIModule