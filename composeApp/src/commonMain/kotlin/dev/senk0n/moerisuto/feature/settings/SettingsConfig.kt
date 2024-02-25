package dev.senk0n.moerisuto.feature.settings

import dev.senk0n.moerisuto.core.navigation.ComponentConfig
import kotlinx.serialization.Serializable

@Serializable
data class SettingsConfig(
    val id: String = "",
    val search: String = "",
) : ComponentConfig
