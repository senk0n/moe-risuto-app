package dev.senk0n.moerisuto.core.navigation.tabs

import dev.senk0n.moerisuto.core.navigation.ComponentConfig
import dev.senk0n.moerisuto.feature.mediaitem.MediaItemConfig
import dev.senk0n.moerisuto.feature.mylist.MyListConfig
import dev.senk0n.moerisuto.feature.settings.SettingsConfig

data class TabMetadata(
    val config: ComponentConfig,
    val name: String,
    val icon: TabIcon,
)

data class TabsMetadata(
    val mainTabs: List<TabMetadata> = listOf(
        TabMetadata(MyListConfig("anime", "completed"), "anime", TabIcon.MyList),
        TabMetadata(SettingsConfig("42", "mushoku"), "settings", TabIcon.Settings),
    ),
    val temporalTabs: List<TabMetadata> = listOf(),
)