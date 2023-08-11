package dev.senk0n.moerisuto.core.model

import dev.senk0n.moerisuto.core.className

interface Extra {
    val type: String get() = className
}

interface NumberExtra : Extra {
    val number: Int
}

interface TextExtra : Extra {
    val text: String
}

interface LinkExtra : Extra {
    val url: String
}

data class Source(override val text: String) : TextExtra
data class ExternalLink(
    override val url: String,
    override val text: String = ""
) : LinkExtra, TextExtra

data class Studio(override val text: String) : TextExtra
data class Publisher(override val text: String) : TextExtra
data class Producer(override val text: String) : TextExtra
data class Staff(override val text: String, val role: String) : TextExtra
data class Tag(override val text: String) : TextExtra
data class Genre(override val text: String) : TextExtra
data class Picture(override val url: String, override val text: String = "") : LinkExtra, TextExtra
data class Video(
    override val url: String,
    val isTrailer: Boolean = false,
    override val text: String = ""
) : LinkExtra, TextExtra
