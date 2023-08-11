package dev.senk0n.moerisuto.preferences.model

import dev.senk0n.moerisuto.core.className

interface PreferenceGroup {
    val name: String get() = className
    val group: List<SettingsType>
    val orderWeight: Int
}

