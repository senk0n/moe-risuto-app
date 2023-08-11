package dev.senk0n.moerisuto.core.compose

import androidx.compose.runtime.staticCompositionLocalOf

val LocalComposeFactory = staticCompositionLocalOf<ComposeFactory> {
    error("can't find ComposeFactory to provide")
}