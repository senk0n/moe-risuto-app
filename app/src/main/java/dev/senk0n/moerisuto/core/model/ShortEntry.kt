package dev.senk0n.moerisuto.core.model

data class ShortEntry(
    val id: EntryId,
    val type: EntryType,
)

fun Entry.toIdentifier() = ShortEntry(id = this.id, type = this.type)
