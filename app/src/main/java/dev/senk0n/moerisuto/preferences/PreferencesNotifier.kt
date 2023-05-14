package dev.senk0n.moerisuto.preferences

import dev.senk0n.moerisuto.preferences.model.PreferenceTag
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

object PreferencesNotifier {
    private val changesHolder: MutableSharedFlow<PreferenceTag> = MutableSharedFlow(extraBufferCapacity = 5)
    val changes: SharedFlow<PreferenceTag> = changesHolder
    fun notify(pref: PreferenceTag) = changesHolder.tryEmit(pref)
}