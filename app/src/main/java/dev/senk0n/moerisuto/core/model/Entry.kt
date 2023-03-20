package dev.senk0n.moerisuto.core.model

data class Entry(
    val id: EntryId,
    val type: EntryType,
    val title: Title,
    val image: Image,
    val status: Status,
    val progress: Progress?,
    val score: Int,
    val start: Date?,
) {
    val season: Season? get() = start?.toSeason()
}

data class EntryId(val id: String, val service: Service, val extraId: EntryId? = null)

sealed interface EntryType {
    data class Anime(val format: AnimeFormat) : EntryType
    data class Manga(val format: MangaFormat) : EntryType
}

enum class AnimeFormat { TV, Movie, OVA, ONA, Special, Music }
enum class MangaFormat { Manga, OneShot, Manhwa, Manhua, Doujinshi, Ranobe, Novel, OEL }
