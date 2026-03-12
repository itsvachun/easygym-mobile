package com.easygym.ui.theme

import androidx.compose.ui.graphics.Color


// -------------------- Color Structure --------------------
sealed class EasyGymColors(
    val red: Color,
    val redDim: Color,
    val bg: Color,
    val surface1: Color,
    val surface2: Color,
    val surface3: Color,
    val surface4: Color,
    val text: Color,
    val textSoft: Color,
    val gray: Color,
    val graySoft: Color,
    val green: Color,
    val greenDim: Color,
    val amber: Color,
    val amberDim: Color,
    val blue: Color,
    val blueDim: Color,
    val purple: Color,
    val purpleDim: Color
) {

    object Light : EasyGymColors(
        red = LightRed,
        redDim = LightRedDim,
        bg = LightBg,
        surface1 = LightSurface1,
        surface2 = LightSurface2,
        surface3 = LightSurface3,
        surface4 = LightSurface4,
        text = LightText,
        textSoft = LightTextSoft,
        gray = LightGray,
        graySoft = LightGraySoft,
        green = LightGreen,
        greenDim = LightGreenDim,
        amber = LightAmber,
        amberDim = LightAmberDim,
        blue = LightBlue,
        blueDim = LightBlueDim,
        purple = LightPurple,
        purpleDim = LightPurpleDim,
    )

    object Dark : EasyGymColors(
        red = DarkRed,
        redDim = DarkRedDim,
        bg = DarkBg,
        surface1 = DarkSurface1,
        surface2 = DarkSurface2,
        surface3 = DarkSurface3,
        surface4 = DarkSurface4,
        text = DarkText,
        textSoft = DarkTextSoft,
        gray = DarkGray,
        graySoft = DarkGraySoft,
        green = DarkGreen,
        greenDim = DarkGreenDim,
        amber = DarkAmber,
        amberDim = DarkAmberDim,
        blue = DarkBlue,
        blueDim = DarkBlueDim,
        purple = DarkPurple,
        purpleDim = DarkPurpleDim,
    )
}

// -------------------- Dark Theme --------------------
// Primary
val DarkRed = Color(0xFFE8391D)
val DarkRedDim = Color(0x23E8391D)

// Background / surfaces
val DarkBg = Color(0xFF0A0A0A)
val DarkSurface1 = Color(0xFF1C1C1C)
val DarkSurface2 = Color(0xFF3B3B3B)
val DarkSurface3 = Color(0xFF8D8D8D)
val DarkSurface4 = Color(0xFFC8C8C8)

// Text / neutrals
val DarkText = Color(0xFFF4F3F0)
val DarkTextSoft = Color(0xFFE8E7E4)
val DarkGray = Color(0xFF888888)
val DarkGraySoft = Color(0xFFBBBBBB)
val DarkGrayStrong = Color(0xFF5A5A5A)

// Status colors
val DarkGreen = Color(0xFF22C55E)
val DarkGreenDim = Color(0x21228B5C)

val DarkAmber = Color(0xFFF59E0B)
val DarkAmberDim = Color(0x21F59E0B)

val DarkBlue = Color(0xFF3B82F6)
val DarkBlueDim = Color(0x213B82F6)

val DarkPurple = Color(0xFF8B5CF6)
val DarkPurpleDim = Color(0x218B5CF6)


// -------------------- Light Theme --------------------
// Primary
val LightRed = Color(0xFFE8391D)
val LightRedDim = Color(0x23E8391D)

// Background / surfaces
val LightBg = Color(0xFFF4F3F0)
val LightSurface1 = Color(0xFFF2F2F2)
val LightSurface2 = Color(0xFFE7E0D8)
val LightSurface3 = Color(0xFFB5B5B5)
val LightSurface4 = Color(0xFF797979)

// Text / neutrals
val LightText = Color(0xFF0A0A0A)
val LightTextSoft = Color(0xFF3A3A3A)
val LightGray = Color(0xFF6B6B6B)
val LightGraySoft = Color(0xFF9A9A9A)

// Whites
val LightWhite = Color(0xFFFFFFFF)
val LightWhiteSoft = Color(0xFFF8F7F4)

// Status colors
val LightGreen = Color(0xFF22C55E)
val LightGreenDim = Color(0x21228B5C)

val LightAmber = Color(0xFFF59E0B)
val LightAmberDim = Color(0x21F59E0B)

val LightBlue = Color(0xFF3B82F6)
val LightBlueDim = Color(0x213B82F6)

val LightPurple = Color(0xFF8B5CF6)
val LightPurpleDim = Color(0x218B5CF6)


