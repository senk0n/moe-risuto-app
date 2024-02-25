package dev.senk0n.moerisuto.feature.mylist

import dev.senk0n.moerisuto.core.navigation.ComponentConfig
import kotlinx.serialization.Serializable

@Serializable
data class MyListConfig(
    val format: String = "",
    val progress: String = "",
    val service: String = "",
) : ComponentConfig