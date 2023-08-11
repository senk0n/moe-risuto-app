package dev.senk0n.moerisuto.feature.settings

import com.arkivanov.essenty.parcelable.Parcelize
import dev.senk0n.moerisuto.core.navigation.ComponentConfig

@Parcelize
data class SettingsConfig(
    val id: String = "",
    val search: String = "",
) : ComponentConfig
