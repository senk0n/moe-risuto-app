package dev.senk0n.moerisuto.core.model

sealed interface EntryType {
    data class Anime(val animeType: AnimeType) : EntryType
    data class Paper(
        val paperType: PaperType?,
        val simpleType: SimplePaperType?,
    ) : EntryType
}

enum class AnimeType { TV, Movie, OVA, ONA, Special, Music }
enum class SimplePaperType { Manga, OneShot, Manhwa, Manhua, Doujinshi, Ranobe, Novel, OEL }

fun SimplePaperType.toPrecise(): PaperType = when (this) {
    SimplePaperType.Manga -> PaperType.Manga(ComicType.Manga, OriginCountry.Japan, true)
    SimplePaperType.OneShot -> PaperType.Manga(ComicType.OneShot, OriginCountry.Japan, true)
    SimplePaperType.Manhwa -> PaperType.Manga(ComicType.Manga, OriginCountry.SouthKorea, true)
    SimplePaperType.Manhua -> PaperType.Manga(ComicType.Manga, null, true)
    SimplePaperType.Doujinshi -> PaperType.Manga(ComicType.Manga, OriginCountry.Japan, false)
    SimplePaperType.Ranobe -> PaperType.Novel(NovelType.Ranobe,true)
    SimplePaperType.Novel -> PaperType.Novel(NovelType.Novel,true)
    SimplePaperType.OEL -> PaperType.Manga(ComicType.Manga, OriginCountry.OEL, true)
}

fun PaperType.toSimple(): SimplePaperType? = when (this) {
    is PaperType.Manga -> if (!isLicensed) SimplePaperType.Doujinshi
    else when (type) {
        ComicType.OneShot -> SimplePaperType.OneShot
        ComicType.Manga -> when (country) {
            OriginCountry.Japan -> SimplePaperType.Manga
            OriginCountry.SouthKorea -> SimplePaperType.Manhwa
            OriginCountry.China -> SimplePaperType.Manhua
            OriginCountry.Taiwan -> SimplePaperType.Manhua
            OriginCountry.OEL -> SimplePaperType.OEL
            else -> null
        }
    }
    is PaperType.Novel -> when (type) {
        NovelType.Ranobe -> SimplePaperType.Ranobe
        NovelType.Novel -> SimplePaperType.Novel
    }
}

sealed interface PaperType {
    data class Manga(
        val type: ComicType,
        val country: OriginCountry?,
        val isLicensed: Boolean
    ) : PaperType

    data class Novel(
        val type: NovelType,
        val isLicensed: Boolean
    ) : PaperType
}

enum class NovelType { Ranobe, Novel }
enum class ComicType { Manga, OneShot }
enum class OriginCountry { Japan, SouthKorea, China, Taiwan, OEL }


