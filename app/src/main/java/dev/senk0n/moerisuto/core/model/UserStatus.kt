package dev.senk0n.moerisuto.core.model

data class UserStatus(
    val count: Int,
    val progress: Progress,
    val repeated: Int,
    val score: Int,
    val started: Date?,
    val finished: Date?,
    val notes: Notes,
)

enum class Progress { Planned, Watching, Completed, Rewatching, Paused, Dropped }
data class Notes(val text: String)