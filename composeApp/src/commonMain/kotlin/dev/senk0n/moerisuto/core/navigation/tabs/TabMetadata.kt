package dev.senk0n.moerisuto.core.navigation.tabs

import dev.senk0n.moerisuto.core.navigation.ComponentConfig

data class TabMetadata(
    val config: ComponentConfig,
    val name: String,
    val icon: TabIcon,
)
