package dev.senk0n.moerisuto.feature.mylist

import com.arkivanov.essenty.parcelable.Parcelize
import dev.senk0n.moerisuto.core.navigation.ComponentConfig

@Parcelize
data class MyListConfig(
    val format: String,
    val progress: String
) : ComponentConfig