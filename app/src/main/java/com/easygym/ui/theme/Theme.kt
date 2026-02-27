package com.easygym.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun EasyGymTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    Color(0xFF8B5CF6)            // Cornfield Blue
    Color(0xFFA78BFF)   // Cornfield Blue backgorund

    Color(0xFF3B82F6)               // French Rose
    Color(0xFF64AFFF)      // French Rose background

    Color(0xFFEC4899)          // French Rose
    Color(0xFFF76CBF) // French Rose background

    val colorScheme = when {
        darkTheme -> darkColorScheme(
            // Primary = Red
            primary = red_dark,
            onPrimary = text_dark,
            primaryContainer = redContainer_dark,
            onPrimaryContainer = text_dark,

            // Secondary = Yellow
            secondary = yellow_dark,
            onSecondary = text_dark,
            secondaryContainer = yellowContainer_dark,
            onSecondaryContainer = text_dark,

            // Tertiary = Green
            tertiary = green_dark,
            onTertiary = text_dark,
            tertiaryContainer = greenContainer_dark,
            onTertiaryContainer = text_dark,

            // Error
            error = red_dark,
            onError = text_dark,
            errorContainer = redContainer_dark,
            onErrorContainer = text_dark,

            // Background and Surface
            background = background_dark,
            onBackground = text_dark,
            surface = surface_dark,
            surfaceVariant = surfaceVariant_dark,
            onSurface = text_dark,
            outline = outline_dark,
            outlineVariant = outlineVariant_dark,
        )

        else -> lightColorScheme(
            // Primary = Red
            primary = red,
            onPrimary = text_dark,
            primaryContainer = redContainer,
            onPrimaryContainer = text_dark,

            // Secondary = Yellow
            secondary = yellow,
            onSecondary = text_dark,
            secondaryContainer = yellowContainer,
            onSecondaryContainer = text_dark,

            // Tertiary = Green
            tertiary = green,
            onTertiary = text_dark,
            tertiaryContainer = greenContainer,
            onTertiaryContainer = text_dark,

            // Error
            error = red,
            onError = text,
            errorContainer = redContainer,
            onErrorContainer = text,

            // Background and Surface
            background = background,
            onBackground = text,
            surface = surface,
            surfaceVariant = surfaceVariant,
            onSurface = text,
            outline = outline,
            outlineVariant = outlineVariant
        )
    }


    // Usa la tipografia importata dal file Typography.kt
    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,  // Qui stai usando la tipografia definita in Typography.kt
        content = content
    )
}