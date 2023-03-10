package dev.senk0n.moerisuto.core.model

enum class Status { NotYetReleased, Releasing, Released, Cancelled, Hiatus }
data class Picture(val url: String, val description: String = "")
data class ExternalLink(val url: String, val name: String = "")
