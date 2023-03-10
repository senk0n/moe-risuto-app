package dev.senk0n.moerisuto.core.model

sealed interface EntryType {
    data class Anime(val format: AnimeFormat) : EntryType
    data class Paper(val format: PaperFormat) : EntryType
}

enum class AnimeFormat { TV, Movie, OVA, ONA, Special, Music }
enum class PaperFormat { Manga, OneShot, Manhwa, Manhua, Doujinshi, Ranobe, Novel, OEL }
