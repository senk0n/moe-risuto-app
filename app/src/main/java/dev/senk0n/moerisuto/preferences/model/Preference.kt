package dev.senk0n.moerisuto.preferences.model

import dev.senk0n.moerisuto.core.model.Service
import dev.senk0n.moerisuto.core.className
import dev.senk0n.moerisuto.core.classNameOf
import kotlinx.coroutines.flow.Flow

interface Preference {
    val tag: PreferenceTag get() = PreferenceTag(className)
}

data class PreferenceTag(val tag: String)

interface SinglePreference<T> : Preference {
    val value: T
    val default: T
}

interface ObservablePreference<T> : Preference {
    fun valueFlow(): Flow<T>
    val default: T
}

interface MutablePreference<T> {
    fun set(value: T)
    fun getOptions(): List<T> = emptyList()
}

interface SettingsPreference<T> : MutablePreference<T> {
    val title: String
    val description: String?
}

interface AppSinglePreference<T> : SinglePreference<T>, MutablePreference<T>
interface AppObservablePreference<T> : ObservablePreference<T>, MutablePreference<T>

interface ServiceSinglePreference<T> : SinglePreference<T>, SettingsPreference<T> {
    val service: Service
    val orderWeight: Int
}

inline fun <reified T : Preference> preferenceTagOf(): PreferenceTag =
    PreferenceTag(classNameOf<T>())