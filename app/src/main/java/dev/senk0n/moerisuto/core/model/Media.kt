package dev.senk0n.moerisuto.core.model

import kotlin.time.Duration

data class Media(
    val entry: Entry<EntryType.Media>,
    val userStatus: UserStatus,
    val synopsis: String,
    val genres: List<String>,
    val tags: List<String>,
    val episodes: Int,
    val episodesReleased: Int,
    val duration: Duration,
    val status: Status,
    val startReleasing: Date?,
    val endReleasing: Date?,
    val broadcast: Date?,
    val rating: Rating,
    val isNSFW: Boolean,

    val studios: List<Studio>,
    val producers: List<Producer>,
    val pictures: List<Picture>,
    val videos: List<Video>,
    val links: List<ExternalLink>,
) {
    val format: MediaFormat get() = entry.type.format
}

enum class Rating { G, PG, PG13, R, Rp, Rx }
@JvmInline
value class Studio(val name: String)
@JvmInline
value class Producer(val name: String)
data class Picture(val url: String, val description: String = "")
data class Video(val url: String, val isTrailer: Boolean = false)