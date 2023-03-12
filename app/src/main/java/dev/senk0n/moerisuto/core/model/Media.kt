package dev.senk0n.moerisuto.core.model

data class Media(
    val entry: Entry,
    val userStatus: UserStatus,
    val synopsis: String,
    val source: Source,
    val issues: Issues,
    val releases: Releases,
    val rating: Rating,
    val isAdult: Boolean,
    val links: List<ExternalLink>,
    val extras: List<Extra>,
)