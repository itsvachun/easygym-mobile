package com.easygym.ui.theme

import androidx.compose.ui.graphics.Color

// -------------------- Color Model --------------------
data class EasyGymColors(
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
)

val Dark = EasyGymColors(
    red = Color(0xFFE8391D),
    redDim = Color(0x23E8391D),

    bg = Color(0xFF0A0A0A),
    surface1 = Color(0xFF1C1C1C),
    surface2 = Color(0xFF3B3B3B),
    surface3 = Color(0xFF8D8D8D),
    surface4 = Color(0xFFC8C8C8),

    text = Color(0xFFF4F3F0),
    textSoft = Color(0xFFE8E7E4),

    gray = Color(0xFF888888),
    graySoft = Color(0xFFBBBBBB),

    green = Color(0xFF22C55E),
    greenDim = Color(0x21228B5C),

    amber = Color(0xFFF59E0B),
    amberDim = Color(0x21F59E0B),

    blue = Color(0xFF3B82F6),
    blueDim = Color(0x213B82F6),

    purple = Color(0xFF8B5CF6),
    purpleDim = Color(0x218B5CF6),
)

val Light = EasyGymColors(
    red = Color(0xFFE8391D),
    redDim = Color(0x23E8391D),

    bg = Color(0xFFF4F3F0),
    surface1 = Color(0xFFF2F2F2),
    surface2 = Color(0xFFE7E0D8),
    surface3 = Color(0xFFB5B5B5),
    surface4 = Color(0xFF797979),

    text = Color(0xFF0A0A0A),
    textSoft = Color(0xFF3A3A3A),

    gray = Color(0xFF6B6B6B),
    graySoft = Color(0xFF9A9A9A),

    green = Color(0xFF22C55E),
    greenDim = Color(0x21228B5C),

    amber = Color(0xFFF59E0B),
    amberDim = Color(0x21F59E0B),

    blue = Color(0xFF3B82F6),
    blueDim = Color(0x213B82F6),

    purple = Color(0xFF8B5CF6),
    purpleDim = Color(0x218B5CF6),
)