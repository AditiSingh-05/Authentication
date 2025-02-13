package com.example.authentication.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Dark Theme Colors
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF800080),
    primaryContainer = Color(0xFF700070),
    secondary = Color(0xFF600060),
    tertiary = Color(0xFF500050),
    background = Color(0xFF100010),
    surface = Color(0xFF1B001B),
    onPrimary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFFF6EDF6),
    onBackground = Color(0xFFEDDDDD),
    onSurface = Color(0xFFCA97CA)
)

// Light Theme Colors
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF800080),
    primaryContainer = Color(0xFF993399),
    secondary = Color(0xFFAD5CAD),
    tertiary = Color(0xFFBD7DBD),
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFF6EDF6),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFF200020),
    onBackground = Color(0xFF400040),
    onSurface = Color(0xFF600060)
)

@Composable
fun AuthenticationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
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
