package dev.senk0n.moerisuto.feature.settings

import dev.senk0n.moerisuto.core.navigation.ComponentConfig
import kotlinx.parcelize.Parcelize

@Parcelize
data class SettingsConfig(
    val id: String = "",
    val search: String = "",
) : ComponentConfig
