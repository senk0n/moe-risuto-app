package dev.senk0n.moerisuto.core.navigation

import com.arkivanov.essenty.parcelable.Parcelable

interface ComponentView {
    fun send(event: ComponentIntent)
}

interface ComponentConfig : Parcelable
interface ComponentIntent