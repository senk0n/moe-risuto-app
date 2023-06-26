package dev.senk0n.moerisuto.core.navigation

import dev.senk0n.moerisuto.core.navigation.tabs.TabFactory
import dev.senk0n.moerisuto.feature.mediaitem.MediaItemDIComponent
import dev.senk0n.moerisuto.feature.mylist.MyListDIComponent
import me.tatarka.inject.annotations.Component

@Component
abstract class ComponentFactoryDI : ComponentsMapProviders {
    abstract val componentFactory: ComponentFactory
    abstract val tabFactory: TabFactory
}

interface ComponentsMapProviders :
    MediaItemDIComponent,
    MyListDIComponent