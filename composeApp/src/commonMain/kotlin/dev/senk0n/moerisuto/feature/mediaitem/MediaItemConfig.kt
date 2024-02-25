package dev.senk0n.moerisuto.feature.mediaitem

import dev.senk0n.moerisuto.core.navigation.ComponentConfig
import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data class MediaItemConfig(
    val entryId: String,
    val serviceName: String,
    val seed: Int = Random.nextInt(),
) : ComponentConfig
