package dev.senk0n.moerisuto.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun MoeRisutoTheme(
    darkThemeOverride: Boolean? = null,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val darkTheme: Boolean = darkThemeOverride ?: isSystemInDarkTheme()
    val colorScheme: ColorScheme = dynamicColorScheme(dynamicColor, darkTheme)
        ?: if (darkTheme) DarkColorScheme else LightColorScheme

    StatusBarColor(colorScheme, darkTheme)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = {
            Surface(
                color = MaterialTheme.colorScheme.background,
                content = content
            )
        }
    )
}

@Composable
internal expect fun StatusBarColor(
    colorScheme: ColorScheme,
    darkTheme: Boolean
)

@Composable
internal expect fun dynamicColorScheme(
    dynamicColor: Boolean,
    darkTheme: Boolean
): ColorScheme?
