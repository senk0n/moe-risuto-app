package dev.senk0n.moerisuto.core.model

data class UserStatus(
    val count: Int,
    val volumes: Int?,
    val progress: Progress,
    val repeated: Int,
    val score: Int,
    val started: Date?,
    val finished: Date?,
    val notes: Notes,
)

enum class Progress { Planned, InProgress, Completed, Repeating, Paused, Dropped }
data class Notes(val text: String)