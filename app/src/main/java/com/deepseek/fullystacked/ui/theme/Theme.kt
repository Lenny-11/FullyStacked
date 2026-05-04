package com.deepseek.fullystacked.ui.theme

<<<<<<< HEAD
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
=======
import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

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
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
)

@Composable
fun FullyStackedTheme(
<<<<<<< HEAD
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AppDarkColorScheme,
        typography  = Typography,
        content     = content
    )
}
=======
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
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
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
