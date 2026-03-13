package com.easygym.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalColors = staticCompositionLocalOf { Light }

@Composable
fun EasyGymTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) Dark else Light

    val colorScheme = when {
        darkTheme -> darkColorScheme(
            primary = colors.red,
            onPrimary = colors.text,
            primaryContainer = colors.redDim,
            onPrimaryContainer = colors.text,
            secondary = colors.blue,
            onSecondary = colors.text,
            secondaryContainer = colors.blueDim,
            onSecondaryContainer = colors.text,
            tertiary = colors.green,
            onTertiary = colors.text,
            tertiaryContainer = colors.greenDim,
            onTertiaryContainer = colors.text,
            background = colors.bg,
            onBackground = colors.text,
            surface = colors.surface1,
            onSurface = colors.text,
            surfaceVariant = colors.surface2,
            outline = colors.surface3,
            outlineVariant = colors.surface4,
            error = colors.red,
            onError = colors.text,
            errorContainer = colors.redDim,
            onErrorContainer = colors.text,
        )

        else -> lightColorScheme(
            primary = colors.red,
            onPrimary = colors.text,
            primaryContainer = colors.redDim,
            onPrimaryContainer = colors.text,
            secondary = colors.blue,
            onSecondary = colors.text,
            secondaryContainer = colors.blueDim,
            onSecondaryContainer = colors.text,
            tertiary = colors.green,
            onTertiary = colors.text,
            tertiaryContainer = colors.greenDim,
            onTertiaryContainer = colors.text,
            background = colors.bg,
            onBackground = colors.text,
            surface = colors.surface1,
            onSurface = colors.text,
            surfaceVariant = colors.surface2,
            outline = colors.surface3,
            outlineVariant = colors.surface4,
            error = colors.red,
            onError = colors.text,
            errorContainer = colors.redDim,
            onErrorContainer = colors.text,
        )
    }

    CompositionLocalProvider(
        LocalColors provides colors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            content = content
        )
    }
}