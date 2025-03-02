package com.example.authentication.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary =  Black,
    secondary = DarkGray,
    secondaryContainer = MoreDarkGray,
    onSecondary = White,
    tertiary = DarkBluish,
    onTertiary = White,
    onError = Red

    )

private val LightColorScheme = lightColorScheme(
    primary =  LightestGrey,
    secondary = White,
    secondaryContainer = DarkGray,
    onSecondary = Black,
    tertiary = Bluish,
    onTertiary = White,
    onError = Red
    )

@Composable
fun AuthenticationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
