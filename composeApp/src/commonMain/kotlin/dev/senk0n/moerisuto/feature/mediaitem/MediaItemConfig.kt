package dev.senk0n.moerisuto.feature.mediaitem

import com.arkivanov.essenty.parcelable.Parcelize
import dev.senk0n.moerisuto.core.navigation.ComponentConfig
import kotlin.random.Random

@Parcelize
data class MediaItemConfig(
    val entryId: String,
    val serviceName: String,
    val seed: Int = Random.nextInt(),
) : ComponentConfig
