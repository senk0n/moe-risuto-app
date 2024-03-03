package dev.senk0n.moerisuto.feature.tabholder

import dev.senk0n.moerisuto.core.navigation.ComponentConfig
import kotlinx.serialization.Serializable

@Serializable
data class TabHolderConfig(
    val rootConfig: ComponentConfig
) : ComponentConfig
