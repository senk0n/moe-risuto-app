package dev.senk0n.moerisuto.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
internal actual fun StatusBarColor(
    colorScheme: ColorScheme,
    darkTheme: Boolean
) {
//    val view = LocalView.current
//    if (!view.isInEditMode) {
//        SideEffect {
//            val window = (view.context as Activity).window
//            window.statusBarColor = colorScheme.primary.toArgb()
//            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
//        }
//    }
}

@Composable
internal actual fun dynamicColorScheme(
    dynamicColor: Boolean,
    darkTheme: Boolean
): ColorScheme? = if (dynamicColor && dynamicColorSupport()) {
    val context = LocalContext.current
    if (darkTheme) dynamicDarkColorScheme(context)
    else dynamicLightColorScheme(context)
} else null

@Composable
private fun dynamicColorSupport(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S