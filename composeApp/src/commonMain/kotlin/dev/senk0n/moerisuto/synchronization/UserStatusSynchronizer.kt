package dev.senk0n.moerisuto.synchronization

import dev.senk0n.moerisuto.core.model.ShortEntry

interface UserStatusSynchronizer {
    fun sync(shortEntry: ShortEntry): SyncStatus
}

enum class SyncStatus {
    Success, Error, NeedComparing
}

enum class SyncMode {
    CloudFirst, LocalFirst, // TODO: SmartCompare, ManualCompare
}