package dev.senk0n.moerisuto.preferences.model

import dev.senk0n.moerisuto.core.className
import dev.senk0n.moerisuto.core.classNameOf
import kotlinx.coroutines.flow.StateFlow

interface Preference {
    val tag: PreferenceTag get() = PreferenceTag(className)
}

data class PreferenceTag(val tag: String)

interface SinglePreference<T> : Preference {
    val value: T
    val default: T
}

interface ObservablePreference<T> : Preference {
    fun get(): T?
    val value: StateFlow<T>
    val default: T
}

inline fun <reified T : Preference> preferenceTagOf(): PreferenceTag =
    PreferenceTag(classNameOf<T>())