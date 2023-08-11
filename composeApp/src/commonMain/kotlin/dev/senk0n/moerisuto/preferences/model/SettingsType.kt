package dev.senk0n.moerisuto.preferences.model

import dev.senk0n.moerisuto.core.navigation.ComponentConfig

sealed interface SettingsType {
    interface Toggle : SettingsPreference<Boolean>
    interface Dropdown : SettingsPreference<String>
    data class CustomEmbedded(val config: ComponentConfig)
    data class CustomFullscreen(val config: ComponentConfig)
}