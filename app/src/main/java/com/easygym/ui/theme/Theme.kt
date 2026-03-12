package com.easygym.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalColors = staticCompositionLocalOf<EasyGymColors> { EasyGymColors.Light }

@Composable
fun EasyGymTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> darkColorScheme(
            primary = LocalColors.current.red,
            onPrimary = LocalColors.current.text,
            primaryContainer = LocalColors.current.redDim,
            onPrimaryContainer = LocalColors.current.text,
            secondary = LocalColors.current.blue,
            onSecondary = LocalColors.current.text,
            secondaryContainer = LocalColors.current.blueDim,
            onSecondaryContainer = LocalColors.current.text,
            tertiary = LocalColors.current.green,
            onTertiary = LocalColors.current.text,
            tertiaryContainer = LocalColors.current.greenDim,
            onTertiaryContainer = LocalColors.current.text,
            background = LocalColors.current.bg,
            onBackground = LocalColors.current.text,
            surface = LocalColors.current.surface1,
            onSurface = LocalColors.current.text,
            surfaceVariant = LocalColors.current.surface2,
            outline = LocalColors.current.surface3,
            outlineVariant = LocalColors.current.surface4,
            error = LocalColors.current.red,
            onError = LocalColors.current.text,
            errorContainer = LocalColors.current.redDim,
            onErrorContainer = LocalColors.current.text,
        )

        else -> lightColorScheme(
            primary = LocalColors.current.red,
            onPrimary = LocalColors.current.text,
            primaryContainer = LocalColors.current.redDim,
            onPrimaryContainer = LocalColors.current.text,
            secondary = LocalColors.current.blue,
            onSecondary = LocalColors.current.text,
            secondaryContainer = LocalColors.current.blueDim,
            onSecondaryContainer = LocalColors.current.text,
            tertiary = LocalColors.current.green,
            onTertiary = LocalColors.current.text,
            tertiaryContainer = LocalColors.current.greenDim,
            onTertiaryContainer = LocalColors.current.text,
            background = LocalColors.current.bg,
            onBackground = LocalColors.current.text,
            surface = LocalColors.current.surface1,
            onSurface = LocalColors.current.text,
            surfaceVariant = LocalColors.current.surface2,
            outline = LocalColors.current.surface3,
            outlineVariant = LocalColors.current.surface4,
            error = LocalColors.current.red,
            onError = LocalColors.current.text,
            errorContainer = LocalColors.current.redDim,
            onErrorContainer = LocalColors.current.text,
        )
    }

    CompositionLocalProvider(
        value = LocalColors provides
                if (darkTheme) EasyGymColors.Dark
                else EasyGymColors.Light
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            content = content
        )
    }
}