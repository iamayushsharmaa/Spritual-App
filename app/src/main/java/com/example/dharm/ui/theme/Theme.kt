package com.example.dharm.ui.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color




private val LightColors = lightColorScheme(
    primary = Color(0xFF000000),
    onPrimary = Color.White,
    secondary = Color(0xFFa0a0a0),
    onSecondary = Color.Black,
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFFFFFFF),
    // Add more colors as needed
)
private val DarkColors = darkColorScheme(
    primary = Color(0xFFFFFFFF),
    onPrimary = Color.Black,
    secondary = Color(0xFF3F3F3F),
    onSecondary = Color.White,
    background = Color(0xFF000000),
    surface = Color(0xFF000000),
)
@Composable
fun SpiritualTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Use system dark mode setting by default
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}