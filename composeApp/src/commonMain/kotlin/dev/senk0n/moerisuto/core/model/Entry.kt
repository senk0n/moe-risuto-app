package dev.senk0n.moerisuto.core.model

data class Entry(
    override val id: EntryId,
    override val format: EntryFormat,
    val title: Title,
    val image: Image,
    val status: Status,
    val progress: Progress?,
    val score: Int,
    val start: Date?,
) : ShortEntry {
    val season: Season? get() = start?.toSeason()
}

interface ShortEntry {
    val id: EntryId
    val format: EntryFormat
}

data class EntryId(
    val id: String,
    val service: Service,
    val extraIds: List<EntryId> = emptyList()
)

sealed interface EntryFormat : TextExtra {
    interface Anime : EntryFormat
    interface Manga : EntryFormat
    interface Novel : EntryFormat
}
