package dev.senk0n.moerisuto.core.model

import dev.senk0n.moerisuto.core.className

abstract class Service(val url: String) {
    open val name: String get() = className
}

data class Releases(
    val startReleasing: Date?,
    val endReleasing: Date?,
    val nextRelease: Date?,
)

data class Title(
    val title: String, val alternatives: List<Alternative>
) {
    data class Alternative(val title: Title, val lang: String)
}

data class Image(val cover: Cover, val banner: Banner? = null) {
    class Cover(val large: String, val medium: String)
    class Banner(val url: String)
}

enum class Status { NotYetReleased, Releasing, Released, Cancelled, Hiatus }
enum class Rating { G, PG, PG13, R, Rp, Rx }
