package dev.senk0n.moerisuto.core.compose

import androidx.compose.runtime.compositionLocalOf

val LocalComposeFactory = compositionLocalOf<ComposeFactory> {
    error("can't find ComposeFactory to provide")
}