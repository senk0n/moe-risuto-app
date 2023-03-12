package dev.senk0n.moerisuto.core.model

interface Extra {
    val type: String get() = this.javaClass.simpleName
}

interface TextExtra : Extra {
    val text: String
}

interface LinkExtra : TextExtra {
    val url: String
}

data class ExternalLink(override val url: String, override val text: String = "") : LinkExtra
data class Studio(override val text: String) : TextExtra
data class Publisher(override val text: String) : TextExtra
data class Producer(override val text: String) : TextExtra
data class Staff(override val text: String, val role: String) : TextExtra
data class Tag(override val text: String) : TextExtra
data class Genre(override val text: String) : TextExtra
data class Picture(override val url: String, override val text: String = "") : LinkExtra
data class Video(val url: String, val isTrailer: Boolean = false) : Extra
