package dev.senk0n.moerisuto.core.model

data class Paper(
    val entry: Entry<EntryType.Paper>,
    val userStatus: UserStatus,
    val synopsis: String,
    val genres: List<String>,
    val tags: List<String>,
    val chapters: Int,
    val volumes: Int,
    val status: Status,
    val startReleasing: Date?,
    val endReleasing: Date?,
    val nextChapter: Date?,
    val rating: Rating,
    val isNSFW: Boolean,

    val links: List<ExternalLink>,
) {
    val format: PaperFormat get() = entry.type.format
}