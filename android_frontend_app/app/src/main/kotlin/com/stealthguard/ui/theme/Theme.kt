package com.stealthguard.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

/**
 * PUBLIC_INTERFACE
 * Neon Cyber theme wrapper for Compose UI.
 */
@Composable
fun StealthGuardTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors: ColorScheme = darkColorScheme(
        primary = NeonPrimary,
        secondary = NeonSecondary,
        background = Bg,
        surface = Surface,
        error = NeonError,
        onPrimary = TextPrimary,
        onSecondary = TextPrimary,
        onBackground = TextPrimary,
        onSurface = TextPrimary,
        onError = TextPrimary,
    )
    val typography = Typography(
        bodyLarge = TextStyle(fontSize = 16.sp),
        titleLarge = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold),
        labelLarge = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium)
    )
    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        content = content
    )
}
