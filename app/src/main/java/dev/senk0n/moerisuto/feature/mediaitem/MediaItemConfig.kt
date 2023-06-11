package dev.senk0n.moerisuto.feature.mediaitem

import com.arkivanov.essenty.parcelable.Parcelize
import dev.senk0n.moerisuto.core.navigation.ComponentConfig

@Parcelize
data class MediaItemConfig(
    val entryId: String,
    val serviceName: String
) : ComponentConfig
