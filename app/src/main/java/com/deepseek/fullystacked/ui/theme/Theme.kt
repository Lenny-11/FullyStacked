package com.deepseek.fullystacked.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ── Always-dark colour scheme ─────────────────────────────────────────────────
private val AppDarkColorScheme = darkColorScheme(
    primary          = Purple500,
    onPrimary        = OnDarkPrimary,
    secondary        = Teal500Dark,
    onSecondary      = Color.White,
    tertiary         = Color(0xFFEF9F27),
    background       = DarkBackground,
    onBackground     = Color(0xFFEAE8FF),
    surface          = DarkSurface,
    onSurface        = Color(0xFFE4E1F5),
    surfaceVariant   = DarkSurfaceVar,
    onSurfaceVariant = OnDarkSecondary,
    outline          = DarkOutline,
    outlineVariant   = Color(0xFF2C2A45),
)

@Composable
fun FullyStackedTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AppDarkColorScheme,
        typography  = Typography,
        content     = content
    )
}
