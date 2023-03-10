package dev.senk0n.moerisuto.core.model

sealed interface EntryType {
    data class Anime(val format: AnimeFormat) : EntryType
    data class Paper(val format: PaperFormat) : EntryType
}

enum class AnimeFormat { TV, Movie, OVA, ONA, Special, Music }
enum class PaperFormat { Manga, OneShot, Manhwa, Manhua, Doujinshi, Ranobe, Novel, OEL }

fun PaperFormat.toPrecise(): PrecisePaperFormat = when (this) {
    PaperFormat.Manga -> PrecisePaperFormat.Manga(ComicType.Manga, OriginCountry.Japan, true)
    PaperFormat.OneShot -> PrecisePaperFormat.Manga(ComicType.OneShot, OriginCountry.Japan, true)
    PaperFormat.Manhwa -> PrecisePaperFormat.Manga(ComicType.Manga, OriginCountry.SouthKorea, true)
    PaperFormat.Manhua -> PrecisePaperFormat.Manga(ComicType.Manga, null, true)
    PaperFormat.Doujinshi -> PrecisePaperFormat.Manga(ComicType.Manga, OriginCountry.Japan, false)
    PaperFormat.Ranobe -> PrecisePaperFormat.Novel(NovelType.Ranobe,true)
    PaperFormat.Novel -> PrecisePaperFormat.Novel(NovelType.Novel,true)
    PaperFormat.OEL -> PrecisePaperFormat.Manga(ComicType.Manga, OriginCountry.OEL, true)
}

fun PrecisePaperFormat.toSimple(): PaperFormat? = when (this) {
    is PrecisePaperFormat.Manga -> if (!isLicensed) PaperFormat.Doujinshi
    else when (type) {
        ComicType.OneShot -> PaperFormat.OneShot
        ComicType.Manga -> when (country) {
            OriginCountry.Japan -> PaperFormat.Manga
            OriginCountry.SouthKorea -> PaperFormat.Manhwa
            OriginCountry.China -> PaperFormat.Manhua
            OriginCountry.Taiwan -> PaperFormat.Manhua
            OriginCountry.OEL -> PaperFormat.OEL
            else -> null
        }
    }
    is PrecisePaperFormat.Novel -> when (type) {
        NovelType.Ranobe -> PaperFormat.Ranobe
        NovelType.Novel -> PaperFormat.Novel
    }
}

sealed interface PrecisePaperFormat {
    data class Manga(
        val type: ComicType,
        val country: OriginCountry?,
        val isLicensed: Boolean
    ) : PrecisePaperFormat

    data class Novel(
        val type: NovelType,
        val isLicensed: Boolean
    ) : PrecisePaperFormat
}

enum class NovelType { Ranobe, Novel }
enum class ComicType { Manga, OneShot }
enum class OriginCountry { Japan, SouthKorea, China, Taiwan, OEL }


