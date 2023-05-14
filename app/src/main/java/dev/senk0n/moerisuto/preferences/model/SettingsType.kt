package dev.senk0n.moerisuto.preferences.model

import dev.senk0n.moerisuto.preferences.model.SettingsPreference

sealed interface SettingsType {
    interface Toggle : SettingsPreference<Boolean>
    interface Dropdown : SettingsPreference<String>
    interface CustomSmall
    interface CustomFull
}