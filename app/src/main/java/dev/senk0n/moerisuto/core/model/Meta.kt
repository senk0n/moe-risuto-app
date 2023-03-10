package dev.senk0n.moerisuto.core.model

enum class Status { NotYetReleased, Releasing, Released, Cancelled, Hiatus }
data class ExternalLink(val url: String, val name: String = "")
data class Staff(val name: String, val role: String)
