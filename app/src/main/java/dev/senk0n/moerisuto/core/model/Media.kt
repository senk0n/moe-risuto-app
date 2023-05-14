package dev.senk0n.moerisuto.core.model

import kotlin.time.Duration

data class Media(
    val entry: Entry,
    val userStatus: UserStatus,
    val synopsis: String,
    val source: Source?,
    val count: Int?,
    val volumes: Int?,
    val duration: Duration?,
    val releases: Releases,
    val rating: Rating,
    val isAdult: Boolean,
    val links: List<ExternalLink>,
    val extras: List<Extra>,
)