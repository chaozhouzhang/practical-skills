package com.example.xinqingwu.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val XinQingWuColorScheme = darkColorScheme(
    primary = Color(0xFF69F1E3),
    secondary = Color(0xFFFFB55E),
    background = Color(0xFF081325),
    surface = Color(0xFF13243B),
    onPrimary = Color(0xFF0B1220),
    onSecondary = Color(0xFF0B1220),
    onBackground = Color(0xFFF7FBFF),
    onSurface = Color(0xFFF7FBFF),
)

@Composable
fun XinQingWuTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = XinQingWuColorScheme,
        content = content,
    )
}
