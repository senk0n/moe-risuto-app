package dev.senk0n.moerisuto.preferences.model

import dev.senk0n.moerisuto.core.model.Service

interface MutablePreference<T> : ObservablePreference<T> {
    fun set(value: T)
    fun getOptions(): List<T> = emptyList()
}

interface SettingsPreference<T> : MutablePreference<T> {
    val title: String
    val description: String?
}

interface ServicePreference<T> : SettingsPreference<T> {
    val service: Service
    val orderWeight: Int
}