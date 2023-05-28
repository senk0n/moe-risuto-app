package dev.senk0n.moerisuto.core.navigation

import com.arkivanov.essenty.parcelable.Parcelable

interface ComponentView {
    fun produce(event: ComponentEvent)
}

interface ComponentConfig : Parcelable
interface ComponentEvent