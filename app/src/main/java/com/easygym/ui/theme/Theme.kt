package com.easygym.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun EasyGymTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> darkColorScheme(
            primary = DarkRed, onPrimary = DarkText,
            secondary = DarkBlue, onSecondary = DarkText,
            background = DarkBg, onBackground = DarkText,
            surface = DarkSurface2, onSurface = DarkText,
            surfaceVariant = DarkSurface3, outline = DarkSurface4,
            error = DarkRed,
        )

        else -> lightColorScheme(
            primary = LightRed, onPrimary = LightText,
            secondary = LightBlue, onSecondary = LightText,
            background = LightBg, onBackground = LightText,
            surface = LightSurface2, onSurface = LightText,
            surfaceVariant = LightSurface3, outline = LightSurface4,
            error = LightRed,
        )
    }

    // Usa la tipografia importata dal file Typography.kt
    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,  // Qui stai usando la tipografia definita in Typography.kt
        content = content
    )
}