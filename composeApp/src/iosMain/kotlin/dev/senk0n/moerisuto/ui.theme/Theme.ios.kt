package dev.senk0n.moerisuto.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

@Composable
internal actual fun StatusBarColor(
    colorScheme: ColorScheme,
    darkTheme: Boolean
) = Unit

@Composable
internal actual fun dynamicColorScheme(
    dynamicColor: Boolean,
    darkTheme: Boolean
): ColorScheme? = null
