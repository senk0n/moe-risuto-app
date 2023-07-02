package dev.senk0n.moerisuto.core.navigation.tabs

import dev.senk0n.moerisuto.core.navigation.ComponentConfig
import dev.senk0n.moerisuto.feature.mediaitem.MediaItemConfig
import dev.senk0n.moerisuto.feature.mylist.MyListConfig

data class TabMetadata(
    val config: ComponentConfig,
    val name: String,
    // TODO: val icon
)

data class TabsMetadata(
    val mainTabs: List<TabMetadata> = listOf(
        TabMetadata(MyListConfig("anime", "completed"), "anime"),
        TabMetadata(MediaItemConfig("manga", "in_progress"), "manga"),
    ),
    val temporalTabs: List<TabMetadata> = listOf(),
)