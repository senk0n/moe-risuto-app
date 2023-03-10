package dev.senk0n.moerisuto.core.model

data class Entry<Type: EntryType>(
    val id: Int,
    val service: String,
    val malId: Int?,
    val title: String,
    val alternativeTitles: Map<String, String>,
    val cover: Pair<String, String>,
    val type: Type,
    val score: Int,
    val start: Date?,
) {
    val season: Season? get() = start?.toSeason()
}