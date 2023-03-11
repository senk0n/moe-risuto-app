package dev.senk0n.moerisuto.core.model

sealed interface EntryType {
    data class Media(val format: MediaFormat) : EntryType
    data class Paper(val format: PaperFormat) : EntryType
}

enum class MediaFormat { TV, Movie, OVA, ONA, Special, Music }
enum class PaperFormat { Manga, OneShot, Manhwa, Manhua, Doujinshi, Ranobe, Novel, OEL }
