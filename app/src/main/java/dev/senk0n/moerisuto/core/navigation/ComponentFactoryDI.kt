package dev.senk0n.moerisuto.core.navigation

import dev.senk0n.moerisuto.core.navigation.tabs.AppDI
import dev.senk0n.moerisuto.core.navigation.tabs.TabProvider
import dev.senk0n.moerisuto.feature.mediaitem.MediaItemDIComponent
import dev.senk0n.moerisuto.feature.mylist.MyListDIComponent
import me.tatarka.inject.annotations.Component

@Component
abstract class ComponentFactoryDI(
    @Component val appDI: AppDI
) : ComponentsMapProviders {
    abstract val componentFactory: ComponentFactory
    abstract val tabProvider: TabProvider
}

interface ComponentsMapProviders :
    MediaItemDIComponent,
    MyListDIComponent