package dev.senk0n.moerisuto.synchronization

import dev.senk0n.moerisuto.core.model.ShortEntry
import dev.senk0n.moerisuto.core.model.UserStatus

interface ChangeUserStatusUseCase {
    suspend fun change(shortEntry: ShortEntry, userStatus: UserStatus): SyncStatus
}