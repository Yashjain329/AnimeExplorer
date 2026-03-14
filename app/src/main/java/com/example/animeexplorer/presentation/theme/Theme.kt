package com.example.animeexplorer.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Teal300,
    secondary = Amber400,
    tertiary = Teal400,
    surface = Charcoal800,
    background = Charcoal700,
    error = Red400,
    onError = Charcoal800,
    onBackground = White,
    onSurface = White,
    onPrimary = Charcoal800,
    onSecondary = Charcoal800,
    surfaceVariant = Slate900,
    onSurfaceVariant = Gray300
)

private val LightColorScheme = lightColorScheme(
    primary = Teal500,
    secondary = Amber500,
    tertiary = Teal600,
    surface = White,
    background = Cream50,
    error = Red500,
    onError = White,
    onBackground = Slate900,
    onSurface = Slate900,
    onPrimary = White,
    onSecondary = White,
    surfaceVariant = Cream100,
    onSurfaceVariant = Gray400
)

@Composable
fun AnimeExplorerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(androidx.compose.ui.platform.LocalContext.current)
            else dynamicLightColorScheme(androidx.compose.ui.platform.LocalContext.current)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}