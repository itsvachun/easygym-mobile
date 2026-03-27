package com.easygym.domain.model

import java.time.LocalDate

enum class MedicalStatus(
    val label: String,
) {
    VALID("Valido"),
    EXPIRING("in Scadenza"),
    EXPIRED("Scaduto")
}

data class Athlete(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val enabled: Boolean,
    val birthDate: LocalDate,
    val taxCode: String,
    val address: String,
    val city: String,
    val postalCode: String,
    val medicalExpDate: LocalDate,
    val medicalStatus: MedicalStatus
)