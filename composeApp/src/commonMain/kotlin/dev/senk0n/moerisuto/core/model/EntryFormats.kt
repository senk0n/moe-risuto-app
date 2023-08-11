package dev.senk0n.moerisuto.core.model

enum class AnimeFormat : EntryFormat.Anime {
    TV, Movie, OVA, ONA, Special, Music;

    override val text: String = type
}

enum class MangaFormat : EntryFormat.Manga {
    Manga, OneShot, Manhwa, Manhua, Doujinshi, OEL;

    override val text: String = type
}

enum class Novel : EntryFormat.Novel {
    Ranobe, Novel;

    override val text: String = type
}