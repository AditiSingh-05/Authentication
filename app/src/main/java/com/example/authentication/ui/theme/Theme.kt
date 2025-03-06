package com.example.authentication.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.authentication.viewmodel.ThemeViewModel

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
    themeViewModel: ThemeViewModel = viewModel(),
    content: @Composable () -> Unit
) {
    val isDarkMode by themeViewModel.isDarkMode.collectAsState()

    val colorScheme = if (isDarkMode) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
