package com.easygym.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.easygym.R

// Definiamo i font personalizzati
val dmSansRegular = FontFamily(
    Font(R.font.dm_sans_regular)  // Font regolare DM Sans
)
val dmSansBold = FontFamily(
    Font(R.font.dm_sans_bold)  // Font bold DM Sans
)
val syneRegular = FontFamily(
    Font(R.font.syne_regular)  // Font regolare Syne
)
val syneBold = FontFamily(
    Font(R.font.syne_bold)  // Font bold Syne
)

// La tipografia di Material 3
val typography = Typography(
    // Titolo principale (headlineLarge) - Usando Syne per un impatto maggiore
    headlineLarge = TextStyle(
        fontFamily = syneBold,  // Syne per titolo principale con enfasi
        fontWeight = FontWeight.Bold,  // Enfasi con un peso bold
        fontSize = 36.sp,  // Dimensione grande per il titolo principale
        lineHeight = 44.sp,  // Linea alta per separare bene le righe
        letterSpacing = (-0.5).sp  // Spazio leggermente negativo per accentuare il contrasto
    ),

    // Titolo medio (headlineMedium) - Usando Syne per coerenza
    headlineMedium = TextStyle(
        fontFamily = syneBold,  // Syne per titoli prominenti
        fontWeight = FontWeight.Bold,  // Peso bold per maggiore impatto
        fontSize = 28.sp,  // Titolo medio
        lineHeight = 36.sp,  // Altezza linea per una lettura equilibrata
        letterSpacing = 0.sp  // Spazio normale
    ),

    // Titolo piccolo (headlineSmall) - Usando DM Sans per una lettura fluida
    headlineSmall = TextStyle(
        fontFamily = dmSansBold,  // DM Sans in versione bold per maggiore enfasi
        fontWeight = FontWeight.SemiBold,  // Peso semi-bold per maggiore leggibilità
        fontSize = 24.sp,  // Titolo più piccolo ma comunque prominente
        lineHeight = 32.sp,  // Linea alta per separare le righe
        letterSpacing = 0.sp  // Nessun spazio tra le lettere
    ),

    // Corpo del testo grande (bodyLarge) - DM Sans regolare per una lettura comoda
    bodyLarge = TextStyle(
        fontFamily = dmSansRegular,  // DM Sans regolare per il corpo principale
        fontWeight = FontWeight.Normal,  // Peso normale per una lettura comoda
        fontSize = 16.sp,  // Dimensione comoda per la lettura
        lineHeight = 24.sp,  // Linea compatta per migliorare la leggibilità
        letterSpacing = 0.5.sp  // Leggero spazio tra le lettere
    ),

    // Corpo del testo medio (bodyMedium) - DM Sans regolare per testo chiaro e leggibile
    bodyMedium = TextStyle(
        fontFamily = dmSansRegular,  // DM Sans regolare
        fontWeight = FontWeight.Medium,  // Peso medio per contrasto visivo
        fontSize = 14.sp,  // Testo leggibile, ma più piccolo
        lineHeight = 20.sp,  // Linea compatta
        letterSpacing = 0.25.sp  // Spazio moderato tra le lettere
    ),

    // Etichetta di medio livello (labelMedium) - Usando DM Sans in bold per visibilità
    labelMedium = TextStyle(
        fontFamily = dmSansBold,  // DM Sans in bold per un buon contrasto
        fontWeight = FontWeight.SemiBold,  // Peso semi-bold per le etichette
        fontSize = 12.sp,  // Dimensione piccola ma leggibile per etichette
        lineHeight = 16.sp,  // Linea compatta
        letterSpacing = 0.5.sp  // Maggiore spazio tra le lettere per chiarezza
    ),

    // Titolo medio (titleMedium) - DM Sans regolare per titoli di dimensione media
    titleMedium = TextStyle(
        fontFamily = dmSansRegular,  // DM Sans regolare per titoli di livello medio
        fontWeight = FontWeight.Normal,  // Peso normale
        fontSize = 16.sp,  // Dimensione ideale per titoli medi
        lineHeight = 24.sp,  // Linea compatta
        letterSpacing = 0.sp  // Nessun spazio tra le lettere
    ),

    // Titolo piccolo (titleSmall) - DM Sans regolare per titoli piccoli
    titleSmall = TextStyle(
        fontFamily = dmSansRegular,  // DM Sans regolare
        fontWeight = FontWeight.Normal,  // Peso normale
        fontSize = 14.sp,  // Piccola dimensione per titoli
        lineHeight = 20.sp,  // Linea compatta
        letterSpacing = 0.1.sp  // Leggero spazio tra le lettere
    ),

    // Body small (bodySmall) - Usando DM Sans regolare per il testo più piccolo
    bodySmall = TextStyle(
        fontFamily = dmSansRegular,  // DM Sans regolare
        fontWeight = FontWeight.Normal,  // Peso normale per testo più piccolo
        fontSize = 12.sp,  // Testo molto piccolo
        lineHeight = 16.sp,  // Linea compatta
        letterSpacing = 0.2.sp  // Spazio moderato per maggiore leggibilità
    ),
)