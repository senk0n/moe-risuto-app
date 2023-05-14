package dev.senk0n.moerisuto.preferences.model

import dev.senk0n.moerisuto.core.model.Service
import kotlinx.coroutines.flow.MutableStateFlow

interface MutablePreference<T> {
    val value: MutableStateFlow<T>
    fun getOptions(): List<T> = emptyList()
}

interface SettingsPreference<T> : MutablePreference<T> {
    val title: String
    val description: String?
}

interface AppObservablePreference<T> : ObservablePreference<T>, MutablePreference<T>

interface ServiceSinglePreference<T> : ObservablePreference<T>, SettingsPreference<T> {
    val service: Service
    val orderWeight: Int
}